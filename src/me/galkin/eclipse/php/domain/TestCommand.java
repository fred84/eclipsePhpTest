package me.galkin.eclipse.php.domain;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;

public abstract class TestCommand {

	protected String executable;
	protected IScriptProject project;
	protected IModelElement unit;
	
	public TestCommand(IModelElement unit, IScriptProject project, String executable) {
		if (!(unit instanceof ISourceModule || unit instanceof IScriptFolder)) {
			throw new RuntimeException("invalid argument");
		}
		
		this.unit = unit;
		this.project = project;
		this.executable = executable;
	}
	
	public File getWorkingDirectory() {
		return getWorkingPath().toFile();
	}
	
	protected IPath getWorkingPath() {
		return project.getResource().getLocation();
	}
	
	protected IPath getTestPath() {
		return unit.getResource().getLocation().makeRelativeTo(getWorkingPath());
	}
	
	protected IPath getBootstrap() {
		try {
			String bootstrapStr = project.getProject().getPersistentProperty(new QualifiedName("BOOTSTRAP", "BOOTSTRAP"));
			
			if (null == bootstrapStr) {
				return null;
			}
			
			IPath bootstrap = new Path(bootstrapStr);
			
			if (bootstrap.isEmpty()) {
				return null;
			}
			
			return bootstrap.makeRelativeTo(getWorkingPath());
			
		} catch (CoreException e) {
			return null;
		}
	}
}