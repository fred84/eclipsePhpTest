package me.galkin.eclipse.php.domain;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;

public abstract class TestCommand {

	protected String executable;
	protected IScriptProject project;
	protected IModelElement unit;
	
	public TestCommand(IModelElement unit, IScriptProject project, String executable) {
		this.unit = unit;
		this.project = project;
		this.executable = executable;
	}
	
	public File getWorkingDirectory() {
		return getWorkingPath().toFile();
	}
	
	public void cleanUp() {
		
	}
	
	abstract public String[] toCommand() throws IOException;
	
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