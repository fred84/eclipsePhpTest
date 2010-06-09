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

	public boolean isFailed() {
		for(IResultsComposite child: children) {
			if (child.isFailed()) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String getImageName() {
		return isFailed() ? Images.IMAGE_FAIL : Images.IMAGE_PASS;
	}
	
	@Override
	public boolean hasChildren() {
		return children.size() > 0;
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
}