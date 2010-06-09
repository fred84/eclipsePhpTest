package me.galkin.eclipse.php.domain;

import me.galkin.eclipse.php.utils.Images;

class FailedTestCase extends TestCase {

	private String description;
	
	FailedTestCase(String name, int line, String descr) {
		super(name, line);
		description = descr;
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
	
	
	@Override
	public boolean isFailed() {
		return true;
	}
	
	@Override
	public String getImageName() {
		return Images.IMAGE_FAIL;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
}