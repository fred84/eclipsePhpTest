package me.galkin.eclipse.php.domain;

public class TestSuites extends ResultComposite {

	public void add(TestSuite suite) {
		suite.setParent(this);
		children.add(suite);
	}
	
	@Override
	public String getName() {
		return "suite";
	}
	
	public String toString() {
		return children.toString();
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof TestSuites)) {
			return false;
		}
		
		return children.equals(((TestSuites)other).children);
	}
}