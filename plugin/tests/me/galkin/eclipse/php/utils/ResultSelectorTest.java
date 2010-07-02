package me.galkin.eclipse.php.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import me.galkin.eclipse.php.domain.*;

public class ResultSelectorTest {

	TestCase failed = new FailedTestCase("failedTest", 1, "fail");
	TestCase passed = new TestCase("passedTest", 2);
	
	@Test
	public void firstFailed_null() {
		TestSuites suites = new TestSuites();
		suites.add(withPassedCases());
		
		assertEquals(suites, ResultSelector.firstFailed(suites));
	}
	
	@Test
	public void firstFailed_failed() {
		TestSuites suites = new TestSuites();
		suites.add(withFailedCases());
		
		assertEquals(failed, ResultSelector.firstFailed(suites));
	}
	
	TestSuite withPassedCases() {
		TestSuite suite = new TestSuite("name", "path");
		suite.add(passed);
		return suite;
	}
	
	TestSuite withFailedCases() {
		TestSuite suite = new TestSuite("name", "path");
		suite.add(failed);
		return suite;
	}
	
}