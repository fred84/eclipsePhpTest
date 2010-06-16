package me.galkin.eclipse.php.ui;

import me.galkin.eclipse.php.domain.IResultsComposite;
import me.galkin.eclipse.php.utils.ResultSelector;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;


public class ResultView extends ViewPart {

	private static final int LABEL_STYLE = SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY | SWT.SHADOW_ETCHED_IN;
	
	public static final String ID = "me.galkin.eclipse.php.ui.ResultView";

	private TreeViewer viewer;
	
	private Label description;
	private Label failedCount;
	private Label totalCount;
	
	private TreeContentProvider provider = new TreeContentProvider();
	
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite statusPanel = getStatusPanel(parent);
		Composite resultPanel = getResultPanel(parent);
		

		
		viewer = new TreeViewer(resultPanel, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.SHADOW_ETCHED_IN);
		viewer.setContentProvider(provider);
		viewer.setLabelProvider(new ResultsLabelProvider());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
		
		description = new Label(resultPanel, LABEL_STYLE);
		
		totalCount = new Label(statusPanel, LABEL_STYLE);
		failedCount = new Label(statusPanel, LABEL_STYLE);
		totalCount.setText("Tests: 0");
		failedCount.setText("Failed: 0");
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					description.setText("");
					return;
				}
				
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();			
				String descr = ((IResultsComposite)selection.getFirstElement()).getDescription();
				
				description.setText(null == descr ? "" : descr);
			}
		});
	}

	public void setFocus() {}
	
	public void notifyFailure(String failure) {
		totalCount.setText("Tests: 0");
		failedCount.setText("Failed: 0");
		
		provider.clear();
		viewer.refresh();
		description.setText(failure);
	}
	
	public void notifyChange(IResultsComposite suites) {
		provider.clear();
		description.setText("");
		
		for(IResultsComposite suite: suites.getChilden()) {
			provider.add(suite);
		}
		
		totalCount.setText("Tests: " + String.valueOf(suites.getResultsCount()));
		failedCount.setText("Failed: " + String.valueOf(suites.getFailedResultsCount()));
		
		viewer.refresh();

		if (suites.isFailed()) {
			viewer.setExpandedElements(suites.getFailedChildren().toArray());
			viewer.setSelection(new StructuredSelection(ResultSelector.firstFailed(suites)));
		}
	}
	
	private Composite getStatusPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.BORDER);
		panel.setLayout(new FillLayout(SWT.HORIZONTAL));
	    panel.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING));
	    return panel; 
	}
	
	private Composite getResultPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.BORDER);
		panel.setLayout(new FillLayout(SWT.HORIZONTAL));
		panel.setLayoutData(new GridData(GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		return panel; 	
	}
}