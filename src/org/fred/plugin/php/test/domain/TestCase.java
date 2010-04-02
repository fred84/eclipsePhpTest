package org.fred.plugin.php.test.domain;

public class TestCase {

	protected String name;
	protected String className;
	protected String file;
	protected int line;
	
	public TestCase(String name, String className, String file, int line) {
		this.name = name;
		this.className = className;
		this.file = file;
		this.line = line;
	}
	
	
	
	public String getName() {
		return name;
	}

	public String getClassName() {
		return className;
	}

	public String getFile() {
		return file;
	}

	public int getLine() {
		return line;
	}

	public boolean equals(Object other) {
		if (!(other instanceof TestCase)) {
			return false;
		}
		
		TestCase obj = (TestCase)other;
		
		if (!name.equals(obj.getName())) {
			return false;
		}
		
		if (!className.equals(obj.getClassName())) {
			return false;
		}
		
		if (!file.equals(obj.getFile())) {
			return false;
		}
		
		if (line != obj.getLine()) {
			return false;
		}
		
		return true;
	}
	
	public String toString() {
		return name + " at " + line;
	}
}