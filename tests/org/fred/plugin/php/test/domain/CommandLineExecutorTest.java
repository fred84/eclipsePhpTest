package org.fred.plugin.php.test.domain;

import static org.junit.Assert.*;

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
	
	@Test
	public void getVersion() throws IOException, InterruptedException {
		assertEquals("3.4.13", executor.getVersion());
	}
}