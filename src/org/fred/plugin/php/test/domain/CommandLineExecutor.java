package org.fred.plugin.php.test.domain;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineExecutor {

	public void run() throws IOException {
		// TODO add working directory as last param
		Process process = Runtime.getRuntime().exec("phpunit"); 
		
		BufferedInputStream out = new BufferedInputStream(process.getInputStream());
		
		BufferedReader commandResult = new BufferedReader(new InputStreamReader(out));
		
	
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		commandResult.close();
		
		
	}
	
	
}
