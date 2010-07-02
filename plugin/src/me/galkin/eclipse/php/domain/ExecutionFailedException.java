package me.galkin.eclipse.php.domain;

public class ExecutionFailedException extends Exception {

	private static final long serialVersionUID = -7243267553653528507L;

	public ExecutionFailedException(String error) {
		super(error);
	}

	public ExecutionFailedException(String message, Exception e) {
		super(message, e);
	}

}
