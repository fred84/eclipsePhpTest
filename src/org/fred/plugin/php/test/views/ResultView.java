package org.fred.plugin.php.test.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;

import org.fred.plugin.php.test.domain.IResultsComposite;
import org.fred.plugin.php.test.domain.TestSuites;

public class ResultView extends ViewPart {

	public static final String ID = "org.fred.plugin.php.test.views.ResultView";

	private TreeViewer viewer;

	private TreeContentProvider provider = new TreeContentProvider();
	
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(provider);
		viewer.setLabelProvider(new ResultsLabelProvider());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void notifyChange(TestSuites suites) {
		provider.clear();
		
		for(IResultsComposite suite: suites.getChilden()) {
			provider.add(suite);
		}
		
		viewer.refresh();
	}
}