package org.fred.plugin.php.test.domain;

public class TestSuite extends ResultComposite {

	private String name;
	private String path;
	
	TestSuite(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	TestSuite(String name) {
		this.name = name;
	}
	
	void addCase(TestCase testCase) {
		children.add(testCase);
	}
	
	public String toString() {
		return name + " with " + children;
	}

	
	public boolean equals(Object other) {
		if (!(other instanceof TestSuite)) {
			return false;
		}
		
		TestSuite obj = (TestSuite)other;
		
		if (!name.equals(obj.name)) {
			return false;
		}
		
		if (null == path && null == obj.path) {
			return true;
		}
		
		if (!path.equals(obj.path)) {
			return false;
		}
		
		return children.equals(obj.children);
	}
}
