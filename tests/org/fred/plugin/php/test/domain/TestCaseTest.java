package org.fred.plugin.php.test.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestCaseTest {

	@Test
	public void getParent() {
		TestSuite suite = new TestSuite("name", "path");
		TestCase testCase = new TestCase("name", 999);
		suite.add(testCase);
		
		assertEquals(testCase.getParent(), suite);
	}
	
}
