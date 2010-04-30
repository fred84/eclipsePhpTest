package org.fred.plugin.php.test.views;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;

import org.fred.plugin.php.test.domain.TestCase;
import org.fred.plugin.php.test.domain.TestSuite;
import org.fred.plugin.php.test.resource.ContentProvider;

public class ResultView extends ViewPart {

	public static final String ID = "org.fred.plugin.php.test.views.ResultView";

	private TableViewer viewer;

	private ContentProvider provider = new ContentProvider();
	
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(provider);
		viewer.setLabelProvider(new LabelProvider());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void notifyChange(List<TestSuite> suites) {
		provider.clear();
		
		for(TestSuite suite: suites) {
			for (TestCase c: suite.getCases()) {
				provider.add(c.toString());
			}
		}
		
		viewer.refresh();
	}
}