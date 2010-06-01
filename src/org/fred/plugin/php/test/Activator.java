package org.fred.plugin.php.test;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;


public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.fred.plugin.php.test";

	public static final String IMAGE_PASS = "pass";
	public static final String IMAGE_FAIL = "fail";
	
	// The shared instance
	private static Activator plugin;
	
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

	public static Activator getDefault() {
		return plugin;
	}
	
	protected void initializeImageRegistry(ImageRegistry registry) {
		registry.put(IMAGE_PASS, ImageDescriptor.createFromURL(bundle.getEntry("icons/pass.gif")));
		registry.put(IMAGE_FAIL, ImageDescriptor.createFromURL(bundle.getEntry("icons/fail.gif")));
	}
}
