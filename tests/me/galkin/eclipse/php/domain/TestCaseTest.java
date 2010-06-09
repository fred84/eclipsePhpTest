package me.galkin.eclipse.php.domain;

import static org.junit.Assert.assertEquals;

import me.galkin.eclipse.php.domain.TestCase;
import me.galkin.eclipse.php.domain.TestSuite;

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
