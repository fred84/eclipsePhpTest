package me.galkin.eclipse.php.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import me.galkin.eclipse.php.domain.FailedTestCase;
import me.galkin.eclipse.php.domain.IResultsComposite;
import me.galkin.eclipse.php.domain.TestCase;
import me.galkin.eclipse.php.domain.TestSuite;
import me.galkin.eclipse.php.domain.TestSuites;
import me.galkin.eclipse.php.utils.Images;

import org.junit.Test;


public class TestSuiteTest {

	TestCase failed = new FailedTestCase("failedTest", 1, "fail");
	TestCase passed = new TestCase("passedTest", 2);
	
	@Test
	public void getChildren() {
		TestSuite suite = new TestSuite("name", "path");
		suite.add(failed);
		suite.add(passed);
		
		List<IResultsComposite> expected = new ArrayList<IResultsComposite>();
		expected.add(failed);
		expected.add(passed);

		assertEquals(expected, suite.getChilden());
	}
	
	@Test
	public void isFailed_true() {
		TestSuite suite = new TestSuite("name", "path");
		suite.add(failed);
		suite.add(passed);
		
		assertTrue(suite.isFailed());
	}
	
	@Test
	public void getParent() {
		TestSuite suite = new TestSuite("name", "path");
		TestSuites suites = new TestSuites();
		suites.add(suite);
		
		assertEquals(suite.getParent(), suites);
	}

	@Test
	public void isFailed_false() {
		TestSuite suite = new TestSuite("name", "path");
		suite.add(passed);
		
		assertFalse(suite.isFailed());
	}
	
	@Test
	public void getImageName_failed() {
		TestSuite suite = new TestSuite("name", "path");
		suite.add(failed);
		
		assertEquals(Images.IMAGE_FAIL, suite.getImageName());
	}

	@Test
	public void getImageName_passed() {
		TestSuite suite = new TestSuite("name", "path");
		suite.add(passed);
		
		assertEquals(Images.IMAGE_PASS, suite.getImageName());
	}
	
	@Test
	public void getFailedChildren() {
		TestSuite suite = new TestSuite("name", "path");
		suite.add(failed);
		suite.add(passed);
		
		List<IResultsComposite> expected = new ArrayList<IResultsComposite>();
		expected.add(failed);

		assertEquals(expected, suite.getFailedChildren());
	}
}