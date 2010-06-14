package me.galkin.eclipse.php.domain;

@SuppressWarnings("serial")
public class ExecutionFailedException extends Exception {

	public ExecutionFailedException(String error) {
		super(error);
	}

}
