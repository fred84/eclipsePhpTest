package me.galkin.eclipse.php.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import me.galkin.eclipse.php.PHPUnitPlugin;
import me.galkin.eclipse.php.domain.ExecutionFailedException;
import me.galkin.eclipse.php.domain.IExecAnalyzer;
import me.galkin.eclipse.php.domain.IResultsComposite;
import me.galkin.eclipse.php.domain.TestCommand;
import me.galkin.eclipse.php.ui.ResultView;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

public class PHPTestJob extends Job {

	public static final String FAMILY = "PHP.Test.Job";
	
	private static class OutputBuffer extends Thread {
		private InputStream stream;
		private StringBuffer result = new StringBuffer();

		OutputBuffer(final InputStream is) {
			stream = is;
		}

		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));
				
				String line;
				while ((line = br.readLine()) != null) {
			
					result.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String getOutput() {
			return result.toString();
		}
	}
	
	private ResultView view;
	private TestCommand command;
	private IExecAnalyzer analyzer;

	public PHPTestJob(ResultView view, TestCommand command, IExecAnalyzer analyzer) {
		super(FAMILY);
		this.view = view;
		this.command = command;
		this.analyzer = analyzer;
	}
	
	public boolean belongsTo(Object family) {
		return family.toString().equals(FAMILY);
	}

	protected IStatus run(IProgressMonitor monitor) {
		try {
			notifyRunning();
			
			Process process = Runtime.getRuntime().exec(command.toCommand(), new String[0], command.getWorkingDirectory());
			
			OutputBuffer stderr = new OutputBuffer(process.getErrorStream());         
			OutputBuffer stdout = new OutputBuffer(process.getInputStream());       

			stderr.start();
			stdout.start();
			
			process.waitFor();

			final IResultsComposite result = analyzer.getResults(command, stdout.getOutput(), stderr.getOutput()); 
				
			notifyCompleted(result);
		} catch (final ExecutionFailedException e) {
			notifyFailure(e);
		} catch (Exception e) {
			return new Status(IStatus.ERROR, PHPUnitPlugin.PLUGIN_ID, IStatus.ERROR, "error", e);
		} finally {
			command.cleanUp();
		}
		
		return Status.OK_STATUS;
	}

	private void notifyCount(final int count, final int total) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				view.notifyCount(count, total);
			}
		});
	}
	
	private void notifyFailure(final ExecutionFailedException e) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				view.notifyFailure(e.getMessage());
			}
		});
	}

	private void notifyCompleted(final IResultsComposite result) {
		Display.getDefault().syncExec(new Runnable() {
				public void run() {
					view.notifyChange(result);
				}
		});
	}
	
	private void notifyRunning() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				view.notifyRunning();
			}
		});	
	}
}
