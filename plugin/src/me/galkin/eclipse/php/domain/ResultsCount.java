package me.galkin.eclipse.php.domain;

public class ResultsCount {

	private final int total;
	private final int executed;
	
	static public ResultsCount failure() {
		return new ResultsCount(0, 0);
	}
	
	static public ResultsCount completed(int count) {
		return new ResultsCount(count, count);
	}

	static public ResultsCount started() {
		return new ResultsCount(0, 0);
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
	
	public double getPercentage() {
		if (0 == total) {
			return 0;
		}
		
		return (double)executed / (double)total;
	}
	
	public String toString() {
		return "ResultsCount: " + executed + " / " + total;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof ResultsCount)) {
			return false;
		}
		
		ResultsCount other = (ResultsCount)obj;
		
		if (total != other.total) {
			return false;
		}
		
		return executed == other.executed;
	}
}
