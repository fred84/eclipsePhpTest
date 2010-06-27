package me.galkin.eclipse.php.domain;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class PHPUnitExecAnalyzer implements IExecAnalyzer {

	private Pattern regex = Pattern.compile(".*1\\.\\.\\d+$", Pattern.MULTILINE | Pattern.DOTALL);
	
	public IResultsComposite getResults(TestCommand command, String out, String err) throws ResultsNotFoundException, ExecutionFailedException {
		if (!isSuccessful(err, out)) {
			throw new ExecutionFailedException(getError(err, out));
		}
		
		String xml;
		try {
			xml = readFileAsString(new File(((PHPUnitCommand)command).getReport().getAbsolutePath()));
		} catch (IOException e) {
			throw new ExecutionFailedException("Error reading results file");
		}
		
		return new XmlReportParser().parse(xml);
	}
	
	private boolean isSuccessful(String err, String out) {
		if (!err.equals("")) {
			return false;
		}

		return regex.matcher(out).matches();
	}
	
	private String getError(String err, String out) {
		if (!err.equals("")) {
			return err;
		}
		
		if (isSuccessful(err, out)) {
			return "";
		}
	
		int index = out.lastIndexOf("\n");
		
		if (-1 == index) {
			return out.replace("TAP version 13", "");
		}
		
		return out.substring(index + 1).replace("TAP version 13", "");
	}
	
	private static String readFileAsString(File file) throws java.io.IOException{
	    byte[] buffer = new byte[(int)file.length()];
	    BufferedInputStream f = new BufferedInputStream(new FileInputStream(file));
	    f.read(buffer);
	    return new String(buffer);
	}

}