package me.galkin.eclipse.php.domain;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;

public class ProjectFinder {

	static public IScriptProject getProject(IModelElement element) throws ProjectNotFoundException {
		if (element instanceof IScriptProject) {
			return (IScriptProject)element;
		}
		
		if (null == element) {
			throw new ProjectNotFoundException();
		}
		
		return getProject(element.getParent());
	}
	
}