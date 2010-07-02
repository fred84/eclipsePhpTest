package me.galkin.eclipse.php.utils;

import me.galkin.eclipse.php.domain.IResultsComposite;

public class ResultSelector {

	static public IResultsComposite firstFailed(IResultsComposite composite) {
		if (!composite.isFailed() && !composite.isError()) {
			return composite;
		}

		for (IResultsComposite child : composite.getFailedChildren()) {
			return firstFailed(child);
		}
		
		for (IResultsComposite child : composite.getErrorChildren()) {
			return firstFailed(child);
		}
		
		return composite;
	}
}