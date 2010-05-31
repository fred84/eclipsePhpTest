package org.fred.plugin.php.test.domain;

import java.util.ArrayList;
import java.util.List;

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
}
