package me.galkin.eclipse.php.domain;

import java.util.ArrayList;
import java.util.List;

import me.galkin.eclipse.php.utils.Images;

public class TestCase implements IResultsComposite {

	protected String name;
	protected int line;
	protected IResultsComposite parent;
	
	public TestCase(String name, int line) {
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
		return new ArrayList<IResultsComposite>();
	}

	@Override
	public boolean isFailed() {
		return false;
	}

	@Override
	public String getImageName() {
		return Images.IMAGE_PASS;
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
	
	@Override
	public String getDescription() {
		return null;
	}
	
	void setParent(IResultsComposite parent) {
		this.parent = parent;
	}

	public List<IResultsComposite> getFailedChildren() {
		return new ArrayList<IResultsComposite>();
	}

	public int getFailedResultsCount() {
		return 0;
	}

	public int getResultsCount() {
		return 1;
	}
}