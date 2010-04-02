package org.fred.plugin.php.test.domain;

public class FailedTestCase extends TestCase {

	protected String description;
	
	public FailedTestCase(String name, String className, String file, int line, String description) {
		super(name, className, file, line);
		this.description = description;
	}
	
	public boolean equals(Object other) {
		if (!super.equals(other)) {
			return false;
		}
		
		if (!(other instanceof FailedTestCase)) {
			return false;
		}
		
		FailedTestCase obj = (FailedTestCase)other;
		
		return description.equals(obj.getDescription());
	}
		
	
	public String getDescription() {
		return description;
	}

	public String toString() {
		return super.toString() + " '" + description + "'";
	}
}