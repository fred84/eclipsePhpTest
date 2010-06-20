package me.galkin.eclipse.php.handler;

import me.galkin.eclipse.php.PHPUnitPlugin;
import me.galkin.eclipse.php.domain.ExecutionFailedException;
import me.galkin.eclipse.php.domain.IResultsComposite;
import me.galkin.eclipse.php.domain.PHPUnitCommand;
import me.galkin.eclipse.php.domain.Runner;
import me.galkin.eclipse.php.ui.ResultView;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

public class PHPTestJob extends Job {

	public static final String FAMILY = "PHP.Test.Job";
	
	private ResultView view;
	private PHPUnitCommand command;
	
	public PHPTestJob(ResultView view, PHPUnitCommand command) {
		super(FAMILY);
		this.view = view;
		this.command = command;
	}
	
	public boolean belongsTo(Object family) {
		return family.toString().equals(FAMILY);
	}

	protected IStatus run(IProgressMonitor monitor) {
		try {
			final IResultsComposite result = new Runner().run(command); 
				
			Display.getDefault().syncExec(new Runnable() {
					public void run() {
						view.notifyChange(result);
					}
			});
		} catch (final ExecutionFailedException e) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					view.notifyFailure(e.getMessage());
				}
			});
		
		
		} catch (Exception e) {
			return new Status(IStatus.ERROR, PHPUnitPlugin.PLUGIN_ID, IStatus.ERROR, "error", e);
		}
		
		return Status.OK_STATUS;
	}

}
