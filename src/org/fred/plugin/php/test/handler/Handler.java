package org.fred.plugin.php.test.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.fred.plugin.php.test.domain.PHPUnitCommand;
import org.fred.plugin.php.test.domain.ProjectFinder;
import org.fred.plugin.php.test.domain.ProjectNotFoundException;
import org.fred.plugin.php.test.domain.Runner;
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

		String phpunit = Platform.getPreferencesService().getString("PHPUnit_plugin", "executable", null, null);
		
		if (null == phpunit) {
			MessageDialog.openInformation(
				HandlerUtil.getActiveShell(event),
					"Information", 
					"Please setup phpunit path in plugin preferences"
			);
			return null;
		}
		
		try {
			runTests(new PHPUnitCommand(unit, ProjectFinder.getProject(unit), phpunit), view, event);
		} catch (ProjectNotFoundException e) {
			MessageDialog.openInformation(
				HandlerUtil.getActiveShell(event),
					"Information", 
					e.getMessage()
			);
			return null;
		}
		
		return null;
	}
	
	private void runTests(PHPUnitCommand command, ResultView view, ExecutionEvent event) {
		try {
			view.notifyChange(new Runner().run(command));
		} catch (Exception e) {
			MessageDialog.openInformation(
					HandlerUtil.getActiveShell(event),
					"Information", 
					e.getClass().toString() + ": "  + e.getMessage() + "; " + e.getStackTrace().toString()
			);
		}
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