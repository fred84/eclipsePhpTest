package org.fred.plugin.php.test.domain;

class FailedTestCase extends TestCase {

	private String description;
	
	public FailedTestCase(String name, int line, String description) {
		super(name, line);
		this.description = description;
	}
	
	public boolean equals(Object other) {
		if (!super.equals(other)) {
			return false;
		}
		
		if (!(other instanceof FailedTestCase)) {
			return false;
		}

		return description.equals(((FailedTestCase)other).description);
	}

	public String toString() {
		return super.toString() + " '" + description + "'";
	}
}