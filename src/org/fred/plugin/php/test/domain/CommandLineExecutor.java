package org.fred.plugin.php.test.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CommandLineExecutor {

	private static class OutputBuffer extends Thread {
		private InputStream stream;
		private StringBuffer result = new StringBuffer();

		OutputBuffer(final InputStream is) {
			stream = is;
		}

		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));

				String line;
				while ((line = br.readLine()) != null) {
					result.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String getOutput() {
			return result.toString();
		}
	}

	String customCommand(String command) throws IOException, InterruptedException {
		return run(command);
	}
	
	String getVersion() throws IOException, InterruptedException {
		String command = "phpunit --version"; 
		
		Pattern replace = Pattern.compile("(\\d+\\.\\d+\\.\\d+)");
		Matcher matcher = replace.matcher(run(command));
		
		if (!matcher.find()) {
			throw new IOException("PHPUnit not found");
		}
		
		return matcher.group();
	}
	
	private String run(String command) throws IOException, InterruptedException {
		// TODO add working directory as last parameter
		Process process = Runtime.getRuntime().exec(command);

		OutputBuffer stderr = new OutputBuffer(process.getErrorStream());         
		OutputBuffer stdout = new OutputBuffer(process.getInputStream());       

		stderr.start();
		stdout.start();
		
		process.waitFor();

		if (!stderr.getOutput().equals("")) {
			throw new IOException(stderr.getOutput());
		}
		
		return stdout.getOutput();
	}
}