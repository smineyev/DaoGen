package com.mvwsolutions.android.daogen.plugin;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.mvwsolutions.daogen.SQLiteGen";

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	public static IStatus createStatus( String message, Throwable ex, int severity, int code)
	{
		return new Status(severity, PLUGIN_ID, code, message, ex);
	}
	
	public static IStatus createStatus( Throwable ex)
	{
		return createStatus( ex.getLocalizedMessage(), ex, IStatus.ERROR, IStatus.OK);
	}
	
	class LogRunner implements Runnable
	{
		IStatus status;
		LogRunner( IStatus to_log)
		{
			status=to_log;
		}
		public void run() {
			getLog().log(status);
		}	
	}

	public void asyncLog(IStatus status)
	{
		Display.getDefault().asyncExec(new LogRunner(status));
	}
	
	public void asyncLog(Throwable ex)
	{
		asyncLog( createStatus(ex));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
