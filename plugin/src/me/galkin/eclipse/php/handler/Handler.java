package me.galkin.eclipse.php.handler;

import java.util.List;

import me.galkin.eclipse.php.PHPUnitPlugin;
import me.galkin.eclipse.php.domain.PHPUnitCommand;
import me.galkin.eclipse.php.domain.PHPUnitExecAnalyzer;
import me.galkin.eclipse.php.domain.ProjectFinder;
import me.galkin.eclipse.php.domain.ProjectNotFoundException;
import me.galkin.eclipse.php.ui.ResultView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.jobs.Job;
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
		Job[] jobs = Job.getJobManager().find(PHPTestJob.FAMILY);
		
		if (jobs.length > 0) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", "Another test already running");
			return null;
		}
		
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

		String phpunit = PHPUnitPlugin.getDefault().getPreferenceStore().getString(
				PHPUnitPlugin.EXECUTABLE);

		if (null == phpunit || phpunit.equals("")) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information",
					"Please setup phpunit path in plugin preferences");
			return null;
		}

		try {
			Job testJob = new PHPTestJob(
					view, 
					new PHPUnitCommand(unit, ProjectFinder.getProject(unit),phpunit),
					new PHPUnitExecAnalyzer()	
			);
			
			testJob.schedule();
		} catch (ProjectNotFoundException e) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", e.getMessage());
			return null;
		}

		return null;
	}

	private ResultView getResultView(ExecutionEvent event) {
		try {
			return (ResultView) HandlerUtil.getActiveWorkbenchWindow(event)
					.getActivePage().showView(ResultView.ID, null,
							IWorkbenchPage.VIEW_VISIBLE);

		} catch (PartInitException e) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event), "Error",
					"Error opening Result View " + e.getMessage());
			return null;
		}
	}

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

				Object defaultVariables = ctx.getDefaultVariable();
				elem = ((List<?>)defaultVariables).get(0);
			}

			// dependency on internal PDT class
			if (elem instanceof IImplForPhp) {
				return ((IImplForPhp) elem).getModelElement();
			}

			return (IModelElement) elem;
		} catch (Exception e) {
			return null;
		}
	}
}