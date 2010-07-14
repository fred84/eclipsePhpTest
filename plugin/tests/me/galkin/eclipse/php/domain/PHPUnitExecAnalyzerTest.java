package me.galkin.eclipse.php.domain;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


public class PHPUnitExecAnalyzerTest {

	private static class MockCommand extends PHPUnitCommand {

		public MockCommand() {
			super(null, null, null);
		}

		public String[] toCommand() throws IOException {
			return null;
		}
	}
	
	private IExecAnalyzer analyzer;
	
	@Before
	public void setUp() {
		analyzer = new PHPUnitExecAnalyzer();
	}

	
	@Test
	public void fatal() throws ResultsNotFoundException, ExecutionFailedException {
		try {
			analyzer.getResults(new MockCommand(), "insufficientdata", getFatalOutput());
			fail();
		} catch (ExecutionFailedException e) {
			assertEquals(getFatalOutput(), e.getMessage());
		}
	}
	
	@Test
	public void classNotFound() throws ResultsNotFoundException, ExecutionFailedException {
		try {
			analyzer.getResults(new MockCommand(), getTestClassNotFoundOutput(), "");
			fail();
		} catch (ExecutionFailedException e) {
			assertEquals(getTestClassNotFoundError(), e.getMessage());
		}
	}
	
	@Test
	public void reportsNotFound() throws ResultsNotFoundException, ExecutionFailedException {
		try {
			analyzer.getResults(new MockCommand(), getSuccessfulOutput(), "");
			fail();
		} catch (ExecutionFailedException e) {
			assertEquals("Parsing failed", e.getMessage());
		}
	}
	
	@Test
	public void reportsNotFound_forFailedOutput() throws ResultsNotFoundException, ExecutionFailedException {
		try {
			analyzer.getResults(new MockCommand(), getFailedOutput(), "");
			fail();
		} catch (ExecutionFailedException e) {
			assertEquals("Parsing failed", e.getMessage());
		}
	}
	
	@Test
	public void parseLine_progress() {
		assertEquals(
				ResultsCount.running(180, 540), 
				analyzer.parseString(getProgressOutput())
		);	
	}
	
	@Test
	public void parseLine_nonProgressLine() {
		assertNull(analyzer.parseString(getNonProgressOutput()));
	}
	
	private String getProgressOutput() {
		return "............................................................ 180 / 540";
	}
	
	private String getNonProgressOutput() {
		return "PHPUnit 3.4.14 by Sebastian Bergmann.";
	}
	
	private String getSuccessfulOutput() {
		return "PHPUnit 3.4.14 by Sebastian Bergmann."
		 + "\n"
		 + "\n."
		 + "\n Time: 0 seconds, Memory: 6.50Mb"
		 + "\n"
		 + "\nOK (1 test, 1 assertion)";
	}

	private String getFailedOutput() {
		return "PHPUnit 3.4.14 by Sebastian Bergmann."
		 + "\n"
		 + "\n.......EF"
		 + "\n Time: 0 seconds, Memory: 6.50Mb"
		 + "\n"
		 + "\nFAILURES!"
		 + "\nTests: 9, Assertions: 8, Failures: 1, Errors: 1.";
	}
	
	private String getTestClassNotFoundOutput() {
		return "PHPUnit 3.4.14 by Sebastian Bergmann."
			+ "\n"
			+ "\n" + getTestClassNotFoundError();
	}
	
	private String getTestClassNotFoundError() {
		return "Class testcases/NonTest could not be found in pathpath";
	}

	private String getFatalOutput() {
		return "PHP Fatal error:  Call to undefined function asdfasd() in asdfsadf";
	}
}
