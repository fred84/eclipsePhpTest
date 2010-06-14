package me.galkin.eclipse.php.domain;

import static org.junit.Assert.*;

import org.junit.Test;


public class PHPUnitExecAnalyzerTest {

	@Test
	public void isSuccessful() {
		IExecAnalyzer analyzer = new PHPUnitExecAnalyzer(getSuccessfulOutput(), getEmptyErrorOutput());
		
		assertTrue(analyzer.isSuccessful());
	}
	
	@Test
	public void isSuccessful_fail() {
		IExecAnalyzer analyzer = new PHPUnitExecAnalyzer(getFailedOutput(), getEmptyErrorOutput());
		
		assertFalse(analyzer.isSuccessful());
	}	
	
	@Test
	public void getError() {
		IExecAnalyzer analyzer = new PHPUnitExecAnalyzer(getFailedOutput(), getEmptyErrorOutput());
		
		assertEquals(getExpectedError(), analyzer.getError());
	}	
	
	private String getSuccessfulOutput() {
		return "TAP version 13"
			+ "\nok 1 - MineTest::testWillPass"
			+ "\n1..1";
	}

	private String getFailedOutput() {
		return "TAP version 13"
			+ "\nok 1 - MineTest::testWillPass"
			+ "\n" + getExpectedError();
	}
	
	private String getExpectedError() {
		return "Fatal error: Class 'MyClass' not found in /OtherTest.php on line 13";
	}
	
	private String getEmptyErrorOutput() {
		return "";
	}
}
