package org.fred.plugin.php.test.domain;

import java.util.List;

import org.fred.plugin.php.test.Activator;

class TestCase implements IResultsComposite {

	protected String name;
	protected int line;
	protected IResultsComposite parent;
	
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

	@Override
	public String getImageName() {
		return Activator.IMAGE_PASS;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public IResultsComposite getParent() {
		return parent;
	}
	
	void setParent(IResultsComposite parent) {
		this.parent = parent;
	}
}