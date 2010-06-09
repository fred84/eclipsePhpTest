package me.galkin.eclipse.php;

import me.galkin.eclipse.php.utils.Images;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;


public class PHPUnitPlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "me.galkin.eclipse.php";

	private static AbstractUIPlugin plugin;
	
	private Bundle bundle;

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		bundle = context.getBundle();
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static AbstractUIPlugin getDefault() {
		return plugin;
	}
	
	protected void initializeImageRegistry(ImageRegistry registry) {
		registry.put(Images.IMAGE_PASS, ImageDescriptor.createFromURL(bundle.getEntry("icons/pass.gif")));
		registry.put(Images.IMAGE_FAIL, ImageDescriptor.createFromURL(bundle.getEntry("icons/fail.gif")));
	}
}
