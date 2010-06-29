package me.galkin.eclipse.php.domain;

import java.io.File;
import java.io.IOException;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;

public class PHPUnitCommand extends TestCommand {

	private File report;
	
	public PHPUnitCommand(IModelElement unit, IScriptProject project, String executable) {
		super(unit, project, executable);
	}
	
	public String[] toCommand() throws IOException {
		if (null != getBootstrap()) {
			return new String[] {
				executable,
				"--bootstrap=" + getBootstrap().toOSString(),
				"--log-junit=" + getReport().getAbsolutePath(),
				getTestPath().toOSString()
			};
		} else {
			return new String[] {
				executable, 
				"--log-junit=" + getReport().getAbsolutePath(),
				getTestPath().toOSString()
			};
		}
	}
	
	public File getReport() throws IOException {
		if (null == report) {
			report = File.createTempFile("php-test-report", "xml");
		}
		return report;
	}
	
	public void cleanUp() {
		try {
			getReport().delete();
		} catch (Throwable e) {
			/* ^_^ */
		}
	}
}