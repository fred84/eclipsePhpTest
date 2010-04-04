package org.fred.plugin.php.test.domain;

import java.io.IOException;

import org.junit.Test;


public class CommandLineExecutorTest {

	@Test
	public void run() throws IOException {
		CommandLineExecutor executor = new CommandLineExecutor();
		executor.run();
	}
	
}
