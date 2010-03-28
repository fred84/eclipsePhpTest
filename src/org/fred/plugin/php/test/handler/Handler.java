package org.fred.plugin.php.test.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import org.fred.plugin.php.test.resource.ContentProvider;
import org.fred.plugin.php.test.views.ResultView;

public class Handler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ResultView view = getResultView(event);
		
		if (null == view) {
			
			return null;
		}
		
		IModelElement unit = getSelection(event);
		
		if (null == unit) {
			MessageDialog.openInformation(
				HandlerUtil.getActiveShell(event),
				"Information", 
				"Please select a PHP source file"
			);
			return null;
		}
		
		runTests(unit, view);
		
		return null;
	}
	
	private void runTests(IModelElement unit, ResultView view) {
		ContentProvider.getInstance().add(unit.getResource().getLocation().toOSString());
		
		view.notifyChange();
	}
	
	private ResultView getResultView(ExecutionEvent event) {
		try {
			return (ResultView)HandlerUtil
				.getActiveWorkbenchWindow(event)
				.getActivePage()
				.showView(ResultView.ID);
				
		} catch (PartInitException e) {
			MessageDialog.openError(
					HandlerUtil.getActiveShell(event), 
					"Error", 
					"Error opening Result View " + e.getMessage()
				);
			return null;
		}
	}

	private IModelElement getSelection(ExecutionEvent event) {
		IStructuredSelection selection = 
			(IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
	
		try {
			return (IModelElement)selection.getFirstElement();
		} catch (Exception e) {
			// TODO notify user about bad selection
			return null;
		} 
	}
}