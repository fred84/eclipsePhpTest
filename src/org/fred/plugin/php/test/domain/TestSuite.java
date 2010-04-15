package org.fred.plugin.php.test.domain;

import java.util.ArrayList;
import java.util.List;

class TestSuite {

	private String name;
	private String path;
	private List<TestCase> cases = new ArrayList<TestCase>();
	
	TestSuite(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	void addCase(TestCase testCase) {
		cases.add(testCase);
	}
	
	public String toString() {
		return name + " with " + cases;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof TestSuite)) {
			return false;
		}
		
		TestSuite obj = (TestSuite)other;
		
		if (!name.equals(obj.name)) {
			return false;
		}
		
		if (!path.equals(obj.path)) {
			return false;
		}
		
		return cases.equals(obj.cases);
	}

	List<FailedTestCase> getFailedTestCases() {
		List <FailedTestCase> failed = new ArrayList<FailedTestCase>();
		
		for (TestCase testCase: cases) {
			if (testCase instanceof FailedTestCase) {
				failed.add((FailedTestCase)testCase);
			}
		}
		return failed;
	}
}
