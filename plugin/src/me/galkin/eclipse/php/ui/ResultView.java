package me.galkin.eclipse.php.ui;

import me.galkin.eclipse.php.PHPUnitPlugin;
import me.galkin.eclipse.php.domain.IResultsComposite;
import me.galkin.eclipse.php.domain.ResultsCount;
import me.galkin.eclipse.php.utils.Images;
import me.galkin.eclipse.php.utils.ResultSelector;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;

public class ResultView extends ViewPart {

	public static final String ID = "me.galkin.eclipse.php.ui.ResultView";

	private final GridData singleElementData = new GridData(SWT.FILL,
			SWT.CENTER, true, false);

	private TreeViewer viewer;

	private Text description;
	private Label failedCount;
	private Label totalCount;
	private Label errorCount;
	private ResultBar bar;

	private TreeContentProvider provider = new TreeContentProvider();

	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(12, true));

		createLabel(parent, "Runs:");
		totalCount = createLabel(parent, "0");
		createLabel(parent, "Errors:", getImage(Images.IMAGE_ERRORS));
		errorCount = createLabel(parent, "0");
		createLabel(parent, "Failures:", getImage(Images.IMAGE_FAILURES));
		failedCount = createLabel(parent, "0");

		createBar(parent);
		createViewer(parent);
		createDescription(parent);

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					description.setText("");
					return;
				}

				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				String descr = ((IResultsComposite) selection.getFirstElement())
						.getDescription();

				description.setText(null == descr ? "" : descr);
			}
		});
	}

	public void setFocus() {
	}

	public void notifyRunning() {
		bar.update(ResultsCount.started(), ResultBar.OK);
	}

	public void notifyCount(ResultsCount count) {
		totalCount.setText(String.valueOf(count.getTotal()));
		bar.update(count, ResultBar.OK);
	}

	public void notifyFailure(String failure) {
		totalCount.setText("0");
		failedCount.setText("0");
		errorCount.setText("0");

		bar.update(ResultsCount.failure(), ResultBar.FAIL);

		provider.clear();
		viewer.refresh();
		description.setText(failure);
	}

	public void notifyChange(IResultsComposite suites) {
		provider.clear();
		description.setText("");

		if (suites.getFailedResultsCount() > 0
				|| suites.getErrorResultsCount() > 0) {
			bar.update(ResultsCount.completed(), ResultBar.FAIL);
		} else {
			bar.update(ResultsCount.completed(), ResultBar.OK);
		}

		for (IResultsComposite suite : suites.getChilden()) {
			provider.add(suite);
		}

		totalCount.setText(String.valueOf(suites.getResultsCount()));
		failedCount.setText(String.valueOf(suites.getFailedResultsCount()));
		errorCount.setText(String.valueOf(suites.getErrorResultsCount()));

		viewer.refresh();

		if (suites.isFailed() || suites.isError()) {
			viewer.setExpandedElements(suites.getFailedChildren().toArray());
			viewer.setSelection(new StructuredSelection(ResultSelector
					.firstFailed(suites)));
		}
	}

	private Image getImage(String name) {
		return PHPUnitPlugin.getDefault().getImageRegistry().get(name);
	}

	private Label createLabel(Composite parent, String text) {
		Label lbl = new Label(parent, SWT.NONE);
		lbl.setLayoutData(singleElementData);
		lbl.setText(text);

		return lbl;
	}

	private CLabel createLabel(Composite parent, String text, Image image) {
		CLabel lbl = new CLabel(parent, SWT.NONE);
		lbl.setLayoutData(singleElementData);
		lbl.setImage(image);
		lbl.setText(text);
		return lbl;
	}

	private void createBar(Composite parent) {
		bar = new ResultBar(parent, SWT.NONE);
	}

	private void createViewer(Composite parent) {
		GridData treeGrid = new GridData(GridData.FILL, GridData.FILL, true,
				true);
		treeGrid.horizontalSpan = 6;

		viewer = new TreeViewer(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER
				| SWT.SHADOW_ETCHED_IN);
		viewer.setContentProvider(provider);
		viewer.setLabelProvider(new ResultsLabelProvider());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
		viewer.getControl().setLayoutData(treeGrid);
	}

	private void createDescription(Composite parent) {
		GridData grid = new GridData(GridData.FILL, GridData.FILL,
				true, true);
		
		grid.horizontalSpan = 6;
		
		description = new Text(parent, SWT.MULTI | SWT.READ_ONLY | SWT.BORDER
				| SWT.SHADOW_ETCHED_IN | SWT.H_SCROLL | SWT.V_SCROLL);
		description.setLayoutData(grid);
	}
}