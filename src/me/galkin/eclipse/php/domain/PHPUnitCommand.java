package me.galkin.eclipse.php.domain;

import java.io.File;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;

public class PHPUnitCommand extends TestCommand {

	public PHPUnitCommand(IModelElement unit, IScriptProject project, String executable) {
		super(unit, project, executable);
	}
	
	public String[] toCommand(File report) {
		if (null != getBootstrap()) {
			return new String[] {
				executable, 
				"--bootstrap=" + getBootstrap().toOSString(),
				"--log-junit=" + report.getAbsolutePath(),
				"--tap", 
				getTestPath().toOSString()
			};
		} else {
			return new String[] {
				executable, 
				"--log-junit=" + report.getAbsolutePath(),
				"--tap", 
				getTestPath().toOSString()
			};
		}
	}
	
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
	
}