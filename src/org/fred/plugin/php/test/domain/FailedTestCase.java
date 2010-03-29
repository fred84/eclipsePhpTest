package org.fred.plugin.php.test.domain;

public class FailedTestCase extends TestCase {

	protected String description;
	
	public FailedTestCase(String name, String className, String file, int line, String description) {
		super(name, className, file, line);
		this.description = description;
	}
}