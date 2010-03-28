package org.fred.plugin.php.test.resource;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ContentProvider implements IStructuredContentProvider {
	
	private static ContentProvider instance;
	
	private ArrayList<String> elements = new ArrayList<String>();
	
	static public synchronized ContentProvider getInstance() {
		if (null == instance) {
			instance = new ContentProvider();
		}
		
		return instance;
	}
	
	private ContentProvider() {}	
	
	public void add(String str) {
		elements.add(str);
	}
	
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {}
	
	public void dispose() {}
	
	public Object[] getElements(Object parent) {
		return elements.toArray();
	}
}
