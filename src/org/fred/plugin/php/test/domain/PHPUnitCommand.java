package org.fred.plugin.php.test.domain;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;

public class PHPUnitCommand {

	private String executable;
	private IScriptProject project;
	private IModelElement unit;
	
	public PHPUnitCommand(IModelElement unit, IScriptProject project, String executable) {
		if (!(unit instanceof ISourceModule || unit instanceof IScriptFolder)) {
			throw new RuntimeException("invalid argument");
		}
		
		this.unit = unit;
		this.project = project;
		this.executable = executable;
	}
	
	public String[] toCommand(File report) {
		if (null != getBootstrap()) {
			return new String[] {
				executable, 
				//"--bootstrap=" + getBootstrap().toOSString(),
				"--log-junit=" + report.getAbsolutePath(),
				getTestPath().toOSString()
			};
		} else {
			return new String[] {
				executable, 
				"--log-junit=" + report.getAbsolutePath(),
				getTestPath().toOSString()
			};
		}
	}
	
	public File getWorkingDirectory() {
		return getWorkingPath().toFile();
	}
	
	private IPath getWorkingPath() {
		return project.getResource().getLocation();
	}
	
	private IPath getTestPath() {
		return unit.getResource().getLocation().makeRelativeTo(getWorkingPath());
	}
	
	private IPath getBootstrap() {
		try {
			String bootstrapStr = project.getProject().getPersistentProperty(new QualifiedName("BOOTSTRAP", "BOOTSTRAP"));
			
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