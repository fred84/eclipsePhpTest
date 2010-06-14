package me.galkin.eclipse.php.domain;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;


import org.xml.sax.SAXException;

public class Runner {

	private CommandLineExecutor exec = new CommandLineExecutor();
	
	public TestSuites run(PHPUnitCommand command) throws ProjectNotFoundException, IOException, InterruptedException, SAXException, ParserConfigurationException, ResultsNotFoundException, XPathExpressionException, ExecutionFailedException {
		File report = File.createTempFile("php-test-report", "xml");

		IExecAnalyzer execResult = exec.customCommand(command.toCommand(report), command.getWorkingDirectory());
		
		if (!execResult.isSuccessful()) {
			throw new ExecutionFailedException(execResult.getError());
		}
		
		String xml = readFileAsString(new File(report.getAbsolutePath()));
		
		return new XmlReportParser().parse(xml);
	}
	
	private static String readFileAsString(File file) throws java.io.IOException{
	    byte[] buffer = new byte[(int)file.length()];
	    BufferedInputStream f = new BufferedInputStream(new FileInputStream(file));
	    f.read(buffer);
	    return new String(buffer);
	}
	
}
