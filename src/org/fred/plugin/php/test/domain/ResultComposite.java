package org.fred.plugin.php.test.domain;

import java.util.ArrayList;
import java.util.List;

import org.fred.plugin.php.test.Activator;

public abstract class ResultComposite implements IResultsComposite {

	protected List<IResultsComposite> children = new ArrayList<IResultsComposite>();
	
	public List<IResultsComposite> getChilden() {
		return children;
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
		return isFailed() ? Activator.IMAGE_FAIL : Activator.IMAGE_PASS;
	}
}
