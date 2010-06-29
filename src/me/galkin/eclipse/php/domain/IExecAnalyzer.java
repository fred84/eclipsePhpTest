package me.galkin.eclipse.php.domain;

public interface IExecAnalyzer {

	public IResultsComposite getResults(TestCommand command, String out, String err) throws ExecutionFailedException;
	
}