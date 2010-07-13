package me.galkin.eclipse.php.handler;

import me.galkin.eclipse.php.PHPUnitPlugin;
import me.galkin.eclipse.php.domain.IExecAnalyzer;
import me.galkin.eclipse.php.domain.PHPUnitCommand;
import me.galkin.eclipse.php.domain.PHPUnitExecAnalyzer;
import me.galkin.eclipse.php.domain.ProjectFinder;
import me.galkin.eclipse.php.ui.ResultView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

@SuppressWarnings("restriction")
public class Handler extends AbstractHandler {

	private final IExecAnalyzer analyzer = new PHPUnitExecAnalyzer();
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			assertIsNotAlreadyRunning();
	
			IModelElement unit = getModelElement(event);

			new PHPTestJob(
					getResultView(event), 
					new PHPUnitCommand(unit, ProjectFinder.getProject(unit), getExecutable()),
					analyzer
			).schedule();

		} catch (Throwable e) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Information", e.getMessage());
		} 

		return null;
	}

	private ResultView getResultView(ExecutionEvent event) throws UnableToHandleException {
		try {
			return (ResultView) HandlerUtil.getActiveWorkbenchWindow(event)
					.getActivePage().showView(ResultView.ID, null,
							IWorkbenchPage.VIEW_VISIBLE);

		} catch (PartInitException e) {
			throw new UnableToHandleException("Error opening Result View " + e.getMessage());
		}
	}

	private void assertIsNotAlreadyRunning() throws UnableToHandleException {
		if (Job.getJobManager().find(PHPTestJob.FAMILY).length > 0) {
			throw new UnableToHandleException("PHPUnit tests is already running");
		}
	}
	
	private String getExecutable() throws UnableToHandleException {
		String executable = PHPUnitPlugin.getDefault().getPreferenceStore().getString(
				PHPUnitPlugin.EXECUTABLE);
		
		if (executable.equals("") || null == executable) {
			throw new UnableToHandleException("Please setup phpunit path in plugin preferences");
		}
		
		return executable;
	}
	
	private IModelElement getModelElement(ExecutionEvent event) throws UnableToHandleException {
		try {
			return getFromSelection(event);
		} catch (Throwable e) {
			try {
				return getFromActiveEditor(event);
			} catch (Throwable e2) {
				throw new UnableToHandleException("Unable to get selection: "  + e.getMessage());
			}		
		}
	}
	
	private IModelElement getFromSelection(ExecutionEvent event) throws UnableToHandleException {
		Object element = getSelection(event).getFirstElement(); 
		
		if (null == element) {
			throw new UnableToHandleException("Not found in selection");
		}
		
		return (IModelElement)element;
	}
	
	private IModelElement getFromActiveEditor(ExecutionEvent event) throws UnableToHandleException {
		return getActiveEditor(event).getModelElement();
	}
	
	private PHPStructuredEditor getActiveEditor(ExecutionEvent event) throws UnableToHandleException {
		IEditorPart editor = PHPUnitPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		try {
			return (PHPStructuredEditor)editor;
		} catch (ClassCastException e) {
			throw new UnableToHandleException("non-php active editor");
		}
	}
	
	private IStructuredSelection getSelection(ExecutionEvent event) {
		return (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
	}
}