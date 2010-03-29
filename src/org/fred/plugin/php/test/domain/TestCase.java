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
}