package org.fred.plugin.php.test.domain;

public class TestSuites extends ResultComposite {

	void addSuite(TestSuite suite) {
		children.add(suite);
	}
	
	public String toString() {
		return children.toString();
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof TestSuites)) {
			return false;
		}
		
		return children.equals(((TestSuites)other).children);
	}
}