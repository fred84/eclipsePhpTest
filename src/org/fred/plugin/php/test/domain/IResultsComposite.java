package org.fred.plugin.php.test.domain;

import java.util.List;

public interface IResultsComposite {

	public boolean isFailed();
	
	public List<IResultsComposite> getChilden();
	
	public boolean hasChildren();
	
	public String getImageName();
	
	public String getName();
	
	public IResultsComposite getParent();
}