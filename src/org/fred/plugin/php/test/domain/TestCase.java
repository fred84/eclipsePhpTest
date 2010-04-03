package org.fred.plugin.php.test.domain;

class TestCase {

	protected String name;
	protected String className;
	protected int line;
	
	public TestCase(String name, int line) {
		this.name = name;
		this.line = line;
	}

	public boolean equals(Object other) {
		if (!(other instanceof TestCase)) {
			return false;
		}
		
		TestCase obj = (TestCase)other;
		
		if (!name.equals(obj.name)) {
			return false;
		}
		
		if (line != obj.line) {
			return false;
		}
		
		return true;
	}
	
	public String toString() {
		return name + " at " + line;
	}
}