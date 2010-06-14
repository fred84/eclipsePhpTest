package me.galkin.eclipse.php.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	
	IExecAnalyzer customCommand(String[] command, File dir) throws IOException, InterruptedException {
		return run(command, dir);
	}
	
	private IExecAnalyzer run(Process process) throws IOException, InterruptedException {
		OutputBuffer stderr = new OutputBuffer(process.getErrorStream());         
		OutputBuffer stdout = new OutputBuffer(process.getInputStream());       

		stderr.start();
		stdout.start();
		
		process.waitFor();

		return new PHPUnitExecAnalyzer(stdout.getOutput(), stderr.getOutput());
	}
	
	private IExecAnalyzer run(String[] command, File dir) throws IOException, InterruptedException {
		return run(Runtime.getRuntime().exec(command, new String[0], dir));
	}
}