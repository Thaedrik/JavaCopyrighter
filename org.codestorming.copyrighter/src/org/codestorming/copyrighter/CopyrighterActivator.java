/***************************************************************************
 * Copyright (c) 2013 Codestorming.org.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Codestorming - initial API and implementation
 ****************************************************************************/
package org.codestorming.copyrighter;

import org.codestorming.eclipse.util.pde.UIPluginWithLog;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class CopyrighterActivator extends UIPluginWithLog {

	// The shared instance
	private static CopyrighterActivator plugin;
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	@Override
	public String getPluginID() {
		return ICopyrighterConstants.PLUGIN_ID;
	}

	/**
	 * Returns the shared {@link CopyrighterActivator} instance.
	 * 
	 * @return the shared {@link CopyrighterActivator} instance.
	 * @since 1.1
	 */
	public static CopyrighterActivator getDefault() {
		return plugin;
	}

}
