package me.galkin.eclipse.php.ui;

import me.galkin.eclipse.php.PHPUnitPlugin;
import me.galkin.eclipse.php.domain.IResultsComposite;
import me.galkin.eclipse.php.utils.Images;
import me.galkin.eclipse.php.utils.ResultSelector;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
	private Label errorCount;
	private Label status;
	
	private TreeContentProvider provider = new TreeContentProvider();
	
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(7, false));
		
		createLabel(parent, "Runs:");
		totalCount = createLabel(parent, "0");
		createLabel(parent, "Errors:", PHPUnitPlugin.getDefault().getImageRegistry().get(Images.IMAGE_ERRORS));
		errorCount = createLabel(parent, "0");
		createLabel(parent, "Failures:", PHPUnitPlugin.getDefault().getImageRegistry().get(Images.IMAGE_FAILURES));
		failedCount = createLabel(parent, "0");
		status = createLabel(parent, "");
		
		viewer = new TreeViewer(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER | SWT.SHADOW_ETCHED_IN);
		viewer.setContentProvider(provider);
		viewer.setLabelProvider(new ResultsLabelProvider());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
		
		GridData treeGrid = new GridData(GridData.FILL, GridData.FILL, true, true);
		treeGrid.horizontalSpan = 6;
		
		viewer.getControl().setLayoutData(treeGrid);
		
		GridData descrGrid = new GridData(GridData.FILL, GridData.FILL, true, true);
		
		description = new Label(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER | SWT.SHADOW_ETCHED_IN | SWT.WRAP);
		description.setLayoutData(descrGrid);
		
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
	
	public void notifyRunning() {
		status.setText("running");
	}
	
	public void notifyFailure(String failure) {
		totalCount.setText("0");
		failedCount.setText("0");
		errorCount.setText("0");
		status.setText("");
		
		provider.clear();
		viewer.refresh();
		description.setText(failure);
	}
	
	public void notifyChange(IResultsComposite suites) {
		provider.clear();
		description.setText("");
		status.setText("");
		
		for(IResultsComposite suite: suites.getChilden()) {
			provider.add(suite);
		}
		
		totalCount.setText(String.valueOf(suites.getResultsCount()));
		failedCount.setText(String.valueOf(suites.getFailedResultsCount()));
		errorCount.setText(String.valueOf(suites.getErrorResultsCount()));
		
		viewer.refresh();

		if (suites.isFailed() || suites.isError()) {
			viewer.setExpandedElements(suites.getFailedChildren().toArray());
			viewer.setSelection(new StructuredSelection(ResultSelector.firstFailed(suites)));
		}
	}
	
	private Label createLabel(Composite parent, String text) {
		Label lbl = new Label(parent, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		lbl.setText(text);

		return lbl;
	}
	
	private CLabel createLabel(Composite parent, String text, Image image) {
		CLabel lbl = new CLabel(parent, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		lbl.setImage(image);
		lbl.setText(text);
		return lbl;
	}
}