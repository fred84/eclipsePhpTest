package me.galkin.eclipse.php.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import me.galkin.eclipse.php.domain.FailedTestCase;
import me.galkin.eclipse.php.domain.IResultsComposite;
import me.galkin.eclipse.php.domain.TestCase;
import me.galkin.eclipse.php.domain.TestSuite;
import me.galkin.eclipse.php.domain.TestSuites;

import org.junit.Test;


public class TestSuitesTest {

	TestCase failed = new FailedTestCase("failedTest", 1, "fail");
	TestCase passed = new TestCase("passedTest", 2);
	TestCase error = new ErrorTestCase("errorTest", 2, "error");
	
	@Test
	public void getChildren() {
		TestSuites suites = new TestSuites();
		suites.add(withFailedCases());
		suites.add(withPassedCases());
		
		List<IResultsComposite> expected = new ArrayList<IResultsComposite>();
		expected.add(withFailedCases());
		expected.add(withPassedCases());

		assertEquals(expected, suites.getChilden());
	}
	
	@Test
	public void isFailed_true() {
		TestSuites suites = new TestSuites();
		suites.add(withFailedCases());
		
		assertTrue(suites.isFailed());
	}

	@Test
	public void isFailed_false() {
		TestSuites suites = new TestSuites();
		suites.add(withPassedCases());
		
		assertFalse(suites.isFailed());
	}
	
	@Test
	public void getTestsCount() {
		TestSuites suites = new TestSuites();
		suites.add(withPassedCases());
		suites.add(withFailedCases());
		suites.add(withErrorCases());
		
		assertEquals(4, suites.getResultsCount());
	}
	
	@Test
	public void getFailedTestsCount() {
		TestSuites suites = new TestSuites();
		suites.add(withPassedCases());
		suites.add(withFailedCases());
		suites.add(withErrorCases());
		
		assertEquals(2, suites.getFailedResultsCount());
	}
	
	@Test
	public void getErrorTestsCount() {
		TestSuites suites = new TestSuites();
		suites.add(withPassedCases());
		suites.add(withFailedCases());
		suites.add(withErrorCases());
		
		assertEquals(1, suites.getErrorResultsCount());
	}
	
	TestSuite withPassedCases() {
		TestSuite suite = new TestSuite("name", "path");
		suite.add(passed);
		return suite;
	}
	
	TestSuite withErrorCases() {
		TestSuite suite = new TestSuite("name", "path");
		suite.add(error);
		return suite;
	}
	
	TestSuite withFailedCases() {
		TestSuite suite = new TestSuite("name", "path");
		suite.add(failed);
		suite.add(failed);
		return suite;
	}
}