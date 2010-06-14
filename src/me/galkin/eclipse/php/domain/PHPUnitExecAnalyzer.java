package me.galkin.eclipse.php.domain;

import java.util.regex.Pattern;

public class PHPUnitExecAnalyzer implements IExecAnalyzer {

	private String out;
	private String err;
	
	public PHPUnitExecAnalyzer(String stdout, String stderr) {
		out = stdout;
		err = stderr;
	}
	
	public boolean isSuccessful() {
		if (!err.equals("")) {
			return false;
		}
		
		Pattern regex = Pattern.compile(".*1\\.\\.\\d+$", Pattern.MULTILINE | Pattern.DOTALL);
		
		return regex.matcher(out).matches();
	}
	
	public String getError() {
		if (!err.equals("")) {
			return err;
		}
		
		if (isSuccessful()) {
			return "";
		}
	
		int index = out.lastIndexOf("\n");
		
		if (-1 == index) {
			return out.replace("TAP version 13", "");
		}
		
		return out.substring(index + 1).replace("TAP version 13", "");
	} 
}