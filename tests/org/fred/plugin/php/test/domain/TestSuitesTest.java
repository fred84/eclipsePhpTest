package org.fred.plugin.php.test.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class TestSuitesTest {

	TestCase failed = new FailedTestCase("failedTest", 1, "fail");
	TestCase passed = new TestCase("passedTest", 2);
	
	@Test
	public void getChildren() {
		TestSuites suites = new TestSuites();
		suites.addSuite(withFailedCases());
		suites.addSuite(withPassedCases());
		
		List<IResultsComposite> expected = new ArrayList<IResultsComposite>();
		expected.add(withFailedCases());
		expected.add(withPassedCases());

		assertEquals(expected, suites.getChilden());
	}
	
	@Test
	public void isFailed_true() {
		TestSuites suites = new TestSuites();
		suites.addSuite(withFailedCases());
		
		assertTrue(suites.isFailed());
	}

	@Test
	public void isFailed_false() {
		TestSuites suites = new TestSuites();
		suites.addSuite(withPassedCases());
		
		assertFalse(suites.isFailed());
	}
	
	TestSuite withPassedCases() {
		TestSuite suite = new TestSuite("name", "path");
		suite.addCase(passed);
		return suite;
	}
	
	TestSuite withFailedCases() {
		TestSuite suite = new TestSuite("name", "path");
		suite.addCase(failed);
		return suite;
	}
}