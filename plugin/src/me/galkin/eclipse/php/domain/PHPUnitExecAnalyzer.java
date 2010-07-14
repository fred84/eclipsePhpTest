package me.galkin.eclipse.php.domain;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PHPUnitExecAnalyzer implements IExecAnalyzer {

	private final Pattern progress = Pattern.compile("(\\d+)\\s\\/\\s(\\d+)$");
	
	public IResultsComposite getResults(TestCommand command, String out, String err) throws ExecutionFailedException {
		String errorMsg = getError(err, out);
		
		if (!errorMsg.equals("")) {
			throw new ExecutionFailedException(errorMsg);
		}
		
		String xml;
		try {
			xml = readFileAsString(new File(((PHPUnitCommand)command).getReport().getAbsolutePath()));
		} catch (IOException e) {
			throw new ExecutionFailedException("Error reading results file");
		}
		try {
			return new XmlReportParser().parse(xml);
		} catch (ResultsNotFoundException e) {
			throw new ExecutionFailedException("Parsing failed", e);
		}
	}
	
	private String getError(String err, String out) {
		if (!err.equals("")) {
			return err;
		}
		
		String[] lines = out.split("\n");
		
		if (lines.length < 3) {
			return out;
		}
		
		if (lines[lines.length - 1].startsWith("OK (")) {
			return "";
		}
		
		if (lines[lines.length - 2].equals("FAILURES!")) {
			return "";
		}
	
		return lines[lines.length - 1];
	}
	
	private static String readFileAsString(File file) throws java.io.IOException{
	    byte[] buffer = new byte[(int)file.length()];
	    BufferedInputStream f = new BufferedInputStream(new FileInputStream(file));
	    f.read(buffer);
	    return new String(buffer);
	}

	public ResultsCount parseString(String line) {
		Matcher matcher = progress.matcher(line);
		
		if (matcher.find()) {
			return ResultsCount.running(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
		} 
		
		return null;
	}
}