package me.galkin.eclipse.php.domain;

public class ResultsCount {

	private final int total;
	private final int executed;
	
	static public ResultsCount failure() {
		return new ResultsCount(1, 1);
	}
	
	static public ResultsCount completed() {
		return new ResultsCount(1, 1);
	}

	static public ResultsCount started() {
		return new ResultsCount(0, 1);
	}
	
	static public ResultsCount running(int executed, int total) {
		return new ResultsCount(executed, total);
	}
	
	private ResultsCount(int executed, int total) {
		this.total = total;
		this.executed = executed;
	}
	
	public int getTotal() {
		return total;
	}
	
	public int getExecuted() {
		return executed;
	}
}
