package me.galkin.eclipse.php.ui;

import me.galkin.eclipse.php.PHPUnitPlugin;
import me.galkin.eclipse.php.domain.IResultsComposite;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

class ResultsLabelProvider extends BaseLabelProvider implements ILabelProvider {

	@Override
	public Image getImage(Object element) {
		return PHPUnitPlugin.getDefault().getImageRegistry().get(
			((IResultsComposite)element).getImageName()
		);
	}

	@Override
	public String getText(Object element) {
		IResultsComposite obj = (IResultsComposite)element;
		
		return null == obj ? "" : obj.getName();
	}
}
