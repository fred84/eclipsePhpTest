package me.galkin.eclipse.php.domain;

public class ResultsNotFoundException extends Exception {

	private static final long serialVersionUID = -6575954069844839885L;

	public ResultsNotFoundException(String message) {
		super(message);
	}

	public ResultsNotFoundException(Exception e) {
		super(e);
	}
	
}