package org.fred.plugin.php.test.domain;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Runner {

	private CommandLineExecutor exec = new CommandLineExecutor();
	
	public List<TestSuite> run(PHPUnitCommand command) throws ProjectNotFoundException, IOException, InterruptedException, SAXException, ParserConfigurationException, ResultsNotFoundException {
		File report = File.createTempFile("php-test-report", "xml");
	
		exec.customCommand(command.toCommand(report), command.getWorkingDirectory());
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
