package me.galkin.eclipse.php.handler;

import java.util.List;

import me.galkin.eclipse.php.domain.PHPUnitCommand;
import me.galkin.eclipse.php.domain.ProjectFinder;
import me.galkin.eclipse.php.domain.ProjectNotFoundException;
import me.galkin.eclipse.php.domain.Runner;
import me.galkin.eclipse.php.ui.ResultView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.documentModel.dom.IImplForPhp;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

public class Handler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ResultView view = getResultView(event);

		if (null == view) {
			return null;
		}

		IModelElement unit = getSelection(event);

		if (null == unit) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", "Please select a PHP source file");
			return null;
		}

		String phpunit = Platform.getPreferencesService().getString(
				"PHPUnit_plugin", "executable", null, null);

		if (null == phpunit) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information",
					"Please setup phpunit path in plugin preferences");
			return null;
		}

		try {
			runTests(new PHPUnitCommand(unit, ProjectFinder.getProject(unit),
					phpunit), view, event);
		} catch (ProjectNotFoundException e) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", e.getMessage());
			return null;
		}

		return null;
	}

	private void runTests(PHPUnitCommand command, ResultView view,
			ExecutionEvent event) {
		try {
			view.notifyChange(new Runner().run(command));
		} catch (Exception e) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", e.getClass().toString() + ": "
							+ e.getMessage() + "; "
							+ e.getStackTrace().toString());
		}
	}

	private ResultView getResultView(ExecutionEvent event) {
		try {
			return (ResultView) HandlerUtil.getActiveWorkbenchWindow(event)
					.getActivePage().showView(ResultView.ID, null, IWorkbenchPage.VIEW_VISIBLE);

		} catch (PartInitException e) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event), "Error",
					"Error opening Result View " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private IModelElement getSelection(ExecutionEvent event) {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);

		try {
			Object elem = null;

			if (null != selection) {
				elem = selection.getFirstElement();
			}

			if (null == selection
					&& event.getApplicationContext() instanceof IEvaluationContext) {
				IEvaluationContext ctx = (IEvaluationContext) event
						.getApplicationContext();
				
				List defaultVariables = (List)ctx.getDefaultVariable();
				elem = defaultVariables.get(0);
			}

			// dependency on internal pdt class
			if (elem instanceof IImplForPhp) {
				return ((IImplForPhp) elem).getModelElement();
			}

			return (IModelElement) elem;
		} catch (Exception e) {
			// TODO notify user about bad selection
			return null;
		}
	}
}