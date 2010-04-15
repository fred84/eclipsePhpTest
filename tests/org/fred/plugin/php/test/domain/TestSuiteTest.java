package org.fred.plugin.php.test.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class TestSuiteTest {

	@Test
	public void getFailedTests() {
		// fixture
		FailedTestCase failed = new FailedTestCase("failedTest", 1, "fail");
		TestCase passed = new TestCase("passedTest", 2);
		
		TestSuite suite = new TestSuite("name", "path");
		suite.addCase(failed);
		suite.addCase(passed);

		List<FailedTestCase> expected = new ArrayList<FailedTestCase>();
		expected.add(failed);
		
		// verify
		assertEquals(expected, suite.getFailedTestCases());
	}

}