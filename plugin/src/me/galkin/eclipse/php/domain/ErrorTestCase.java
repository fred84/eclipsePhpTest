package me.galkin.eclipse.php.domain;

import me.galkin.eclipse.php.utils.Images;

public class ErrorTestCase extends TestCase {

	private String error;
	
	public ErrorTestCase(String name, int line, String err) {
		super(name, line);
		error = err;
	}
	
	public boolean equals(Object other) {
		if (!super.equals(other)) {
			return false;
		}
		
		if (!(other instanceof ErrorTestCase)) {
			return false;
		}

		return error.equals(((ErrorTestCase)other).error);
	}

	public String toString() {
		return super.toString() + " '" + error + "'";
	}
	
	
	@Override
	public boolean isFailed() {
		return false;
	}
	
	@Override
	public String getImageName() {
		return Images.IMAGE_ERROR;
	}
	
	@Override
	public String getDescription() {
		return error;
	}
	
	public boolean isError() {
		return true;
	}
	
	public int getErrorResultsCount() {
		return 1;
	}
}