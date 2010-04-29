package org.fred.plugin.php.test.domain;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.xml.sax.SAXException;


public class Runner {
	
	private CommandLineExecutor exec = new CommandLineExecutor();
	
	public List<TestSuite> run(IModelElement unit) throws ProjectNotFoundException, IOException, InterruptedException, SAXException, ParserConfigurationException, ResultsNotFoundException {
		if (!(unit instanceof ISourceModule || unit instanceof IScriptFolder)) {
			throw new RuntimeException("invalid argument");
		}
		
		IScriptProject project = ProjectFinder.getProject(unit);
		
		IPath projectDir = project.getResource().getLocation();
		IPath testDir = unit.getResource().getLocation().makeRelativeTo(projectDir);
		
		File report = File.createTempFile("php-test-report", "xml");
		
		
		
		String[] commands = {
				"phpunit", 
				"--log-junit=" + report.getAbsolutePath(),
				testDir.toOSString()
		};
		exec.customCommand(commands, projectDir.toFile());
		
		String xml = readFileAsString(new File(report.getAbsolutePath()));
		
		
		
		report.delete();
		
		
		return new XmlReportParser().parse(xml);
	}
	
	private static String readFileAsString(File file) throws java.io.IOException{
	    byte[] buffer = new byte[(int)file.length()];
	    BufferedInputStream f = new BufferedInputStream(new FileInputStream(file));
	    f.read(buffer);
	    return new String(buffer);
	}
	
}
