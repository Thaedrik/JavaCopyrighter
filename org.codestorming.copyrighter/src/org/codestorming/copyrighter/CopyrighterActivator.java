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

/**
 * The activator class controls the plug-in life cycle
 */
public class CopyrighterActivator extends UIPluginWithLog {
    
	@Override
	public String getPluginID() {
		return ICopyrighterConstants.PLUGIN_ID;
	}

}
