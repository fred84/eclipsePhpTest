package me.galkin.eclipse.php.domain;

import java.util.List;

public interface IResultsComposite {

	public boolean isFailed();
	
	public boolean isError();
	
	public List<IResultsComposite> getChilden();
	
	public boolean hasChildren();
	
	public String getImageName();
	
	public String getName();
	
	public IResultsComposite getParent();
	
	public String getDescription();
	
	public List<IResultsComposite> getFailedChildren();
	
	public List<IResultsComposite> getErrorChildren();
	
	public int getResultsCount();
	
	public int getFailedResultsCount();
	
	public int getErrorResultsCount();
}