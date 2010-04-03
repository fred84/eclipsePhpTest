package org.fred.plugin.php.test.domain;

class TestCase {

	protected String name;
	protected String className;
	protected String file;
	protected int line;
	
	public TestCase(String name, String file, int line) {
		this.name = name;
		this.file = file;
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
		
		if (!file.equals(obj.file)) {
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