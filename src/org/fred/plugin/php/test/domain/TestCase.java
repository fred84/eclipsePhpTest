package org.fred.plugin.php.test.domain;

import java.util.List;

public class TestCase implements IResultsComposite {

	protected String name;
	protected int line;
	
	TestCase(String name, int line) {
		this.name = name;
		this.line = line;
	}

	public boolean equals(Object other) {
		if (!(other instanceof TestCase)) {
			return false;
		}
		
		TestCase obj = (TestCase)other;
		
		if (!name.equals(obj.name)) {
			return false;
		}
		
		return line == obj.line;
	}
	
	public String toString() {
		return name + " at " + line;
	}

	@Override
	public List<IResultsComposite> getChilden() {
		return null;
	}

	@Override
	public boolean isFailed() {
		return false;
	}
}