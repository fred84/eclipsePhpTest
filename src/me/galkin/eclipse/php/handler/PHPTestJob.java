package me.galkin.eclipse.php.handler;

import me.galkin.eclipse.php.PHPUnitPlugin;
import me.galkin.eclipse.php.domain.ExecutionFailedException;
import me.galkin.eclipse.php.domain.PHPUnitCommand;
import me.galkin.eclipse.php.domain.Runner;
import me.galkin.eclipse.php.ui.ResultView;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class PHPTestJob extends Job {

	private ResultView view;
	private PHPUnitCommand command;
	
	public PHPTestJob(ResultView view, PHPUnitCommand command) {
		super("PHP Test job");
		this.view = view;
		this.command = command;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			view.notifyChange(new Runner().run(command));
		} catch (ExecutionFailedException e) {
			view.notifyFailure(e.getMessage());
		} catch (Exception e) {
			return new Status(IStatus.ERROR, PHPUnitPlugin.PLUGIN_ID, IStatus.ERROR, "error", e);
		}
		
		return Status.OK_STATUS;
	}

}
