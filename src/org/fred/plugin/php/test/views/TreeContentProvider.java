package org.fred.plugin.php.test.views;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.fred.plugin.php.test.domain.IResultsComposite;

public class TreeContentProvider implements ITreeContentProvider {

	private ArrayList<IResultsComposite> elements = new ArrayList<IResultsComposite>();
	
	public void add(IResultsComposite composite) {
		elements.add(composite);
	}
	
	public void clear() {
		elements.clear();
	}
	
	@Override
	public Object[] getChildren(Object parentElement) {
		return ((IResultsComposite)parentElement).getChilden().toArray();
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	@Override
	public Object[] getElements(Object parent) {
		return elements.toArray();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
