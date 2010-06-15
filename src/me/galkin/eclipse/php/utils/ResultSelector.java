package me.galkin.eclipse.php.utils;

import me.galkin.eclipse.php.domain.IResultsComposite;

public class ResultSelector {

	static public IResultsComposite firstFailed(IResultsComposite composite) {
		if (!composite.isFailed()) {
			return null;
		}

		for (IResultsComposite child : composite.getFailedChildren()) {
			return firstFailed(child);
		}
		
		return null;
	}
}