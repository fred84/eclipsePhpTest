package me.galkin.eclipse.php.domain;

import java.io.IOException;


import org.junit.Before;
import org.junit.Test;

public class CommandLineExecutorTest {

	private CommandLineExecutor executor;
	
	@Before
	public void setUp() {
		executor = new CommandLineExecutor(); 
	}
	
	@Test(expected = IOException.class)
	public void customCommand_incorrect() throws IOException, InterruptedException {
		String[] command = {"phpunit12345"};
		executor.customCommand(command);
	}
}