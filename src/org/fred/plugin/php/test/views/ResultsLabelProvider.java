package org.fred.plugin.php.test.views;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.fred.plugin.php.test.Activator;
import org.fred.plugin.php.test.domain.IResultsComposite;

public class ResultsLabelProvider extends BaseLabelProvider implements ILabelProvider {

	@Override
	public Image getImage(Object element) {
		return Activator.getDefault().getImageRegistry().get(
			((IResultsComposite)element).getImageName()
		);
	}

	@Override
	public String getText(Object element) {
		IResultsComposite obj = (IResultsComposite)element;
		
		return null == obj ? "" : obj.getName();
	}
}
