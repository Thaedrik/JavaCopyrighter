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

/**
 * Java Copyrighter constants.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public interface ICopyrighterConstants {

	/**
	 * The Java Copyrighter plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.codestorming.copyrighter"; //$NON-NLS-1$

	/*
	 * Preferences constants
	 */

	/**
	 * Key for the preferences node containing the registered licenses.
	 */
	public static final String PREF_LICENSES = "registeredLicenses"; //$NON-NLS-1$

	/**
	 * Key for the preferences node containing the lastly used License preset.
	 */
	public static final String PREF_LAST_LICENSE = "lastLicensePreset"; //$NON-NLS-1$
	
	/**
	 * Key for the preferences node containing the registered licenses content.
	 */
	public static final String PREF_LICENSES_CONTENT = "licensesContent";

	/**
	 * Key for the preferences node containing the last copyright header.
	 */
	public static final String PREF_LAST_COPYRIGHT_HEADER = "lastCopyHeader"; //$NON-NLS-1$

	/**
	 * Key for the preferences node containing the defined contributors.
	 */
	public static final String PREF_CONTRIBUTORS = "contributors"; //$NON-NLS-1$
}
