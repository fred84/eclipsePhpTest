package org.fred.plugin.php.test.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class TestSuiteTest {

	TestCase failed = new FailedTestCase("failedTest", 1, "fail");
	TestCase passed = new TestCase("passedTest", 2);
	
	@Test
	public void getChildren() {
		TestSuite suite = new TestSuite("name", "path");
		suite.addCase(failed);
		suite.addCase(passed);
		
		List<IResultsComposite> expected = new ArrayList<IResultsComposite>();
		expected.add(failed);
		expected.add(passed);

		assertEquals(expected, suite.getChilden());
	}
	
	@Test
	public void isFailed_true() {
		TestSuite suite = new TestSuite("name", "path");
		suite.addCase(failed);
		suite.addCase(passed);
		
		assertTrue(suite.isFailed());
	}

	@Test
	public void isFailed_false() {
		TestSuite suite = new TestSuite("name", "path");
		suite.addCase(passed);
		
		assertFalse(suite.isFailed());
	}
}