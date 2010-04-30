package org.fred.plugin.php.test.resource;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ContentProvider implements IStructuredContentProvider {
	
	private ArrayList<String> elements = new ArrayList<String>();
	
	public void add(String str) {
		elements.add(str);
	}
	
	public void clear() {
		elements.clear();
	}
	
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {}
	
	public void dispose() {}
	
	public Object[] getElements(Object parent) {
		return elements.toArray();
	}
}
