package me.galkin.eclipse.php.ui;

import me.galkin.eclipse.php.domain.IResultsComposite;
import me.galkin.eclipse.php.utils.ResultSelector;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;


public class ResultView extends ViewPart {

	public static final String ID = "me.galkin.eclipse.php.ui.ResultView";

	private TreeViewer viewer;
	
	private Label description;
	private Label failedCount;
	private Label totalCount;
	
	private TreeContentProvider provider = new TreeContentProvider();
	
	
	public void createPartControl(Composite parent) {
		//parent.setLayout(new RowLayout());
		
		//Composite toolbar = new Composite(parent, SWT.MULTI);
		
		//failedCount = new Label(toolbar, SWT.READ_ONLY);
		//totalCount = new Label(toolbar, SWT.READ_ONLY);
		
		//Composite pane = new Composite(parent, SWT.MULTI);
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.V_SCROLL);
		viewer.setContentProvider(provider);
		viewer.setLabelProvider(new ResultsLabelProvider());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
		
		description = new Label(parent, SWT.READ_ONLY);
		
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
		//totalCount.setText("0");
		//failedCount.setText("0");
		
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
		
		viewer.refresh();
		
		viewer.setExpandedElements(suites.getFailedChildren().toArray());

		if (suites.isFailed() && ResultSelector.firstFailed(suites) instanceof IResultsComposite) {
			viewer.setSelection(new StructuredSelection(ResultSelector.firstFailed(suites)));
		}
	}
}