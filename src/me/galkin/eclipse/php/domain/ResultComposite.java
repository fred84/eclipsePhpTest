package me.galkin.eclipse.php.domain;

import java.util.ArrayList;
import java.util.List;

import me.galkin.eclipse.php.utils.Images;

abstract class ResultComposite implements IResultsComposite {

	protected List<IResultsComposite> children = new ArrayList<IResultsComposite>();
	
	protected IResultsComposite parent;
	
	public List<IResultsComposite> getChilden() {
		return children;
	}
	
	public List<IResultsComposite> getFailedChildren() {
		List<IResultsComposite> result = new ArrayList<IResultsComposite>();
		for (IResultsComposite child : children) {
			if (child.isFailed()) {
				result.add(child);
			}
		}
		
		return result;
	}
	
	public List<IResultsComposite> getErrorChildren() {
		List<IResultsComposite> result = new ArrayList<IResultsComposite>();
		for (IResultsComposite child : children) {
			if (child.isError()) {
				result.add(child);
			}
		}
		
		return result;
	}

	public boolean isFailed() {
		for(IResultsComposite child: children) {
			if (child.isFailed()) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isError() {
		for(IResultsComposite child: children) {
			if (child.isError()) {
				return true;
			}
		}
		
		return false;
	}
	
	public int getFailedResultsCount() {
		int result = 0;	
		for (IResultsComposite child : getFailedChildren()) {
			result += child.getFailedResultsCount();
		}
		return result;
	}
	
	public int getErrorResultsCount() {
		int result = 0;	
		for (IResultsComposite child : getErrorChildren()) {
			result += child.getErrorResultsCount();
		}
		return result;
	}

	public int getResultsCount() {
		int result = 0;	
		for (IResultsComposite child : children) {
			result += child.getResultsCount();
		}
		return result;
	}

	public String getImageName() {
		if (isFailed()) {
			return Images.IMAGE_FAIL;
		}
		
		return isError() ? Images.IMAGE_ERROR : Images.IMAGE_PASS;
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}

	public IResultsComposite getParent() {
		return parent;
	}

	public String getDescription() {
		return null;
	}
	
	void setParent(IResultsComposite parent) {
		this.parent = parent;
	}
}