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
package org.codestorming.copyrighter.ui;

import org.eclipse.osgi.util.NLS;

/**
 * Message bundle class for the Java Copyrighter plug-in.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class L extends NLS {
	private static final String BUNDLE_NAME = "org.codestorming.copyrighter.ui.lbl"; //$NON-NLS-1$
	public static String btn_addContributor;
	public static String btn_cancel;
	public static String btn_ok;
	public static String btn_removeContributor;
	public static String emptyString;
	public static String lbl_copyright;
	public static String lbl_license;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, L.class);
	}

	private L() {
	}
}
