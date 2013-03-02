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
package org.codestorming.copyrighter.preferences;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.codestorming.copyrighter.CopyrighterActivator;
import org.codestorming.copyrighter.license.License;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Provides the Java Copyrighter plug-in global preferences.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class CopyrighterPreferences {

	/**
	 * Returns the registered {@link License licenses}.
	 * 
	 * @return the registered {@link License licenses}.
	 */
	public Set<License> getLicences() {
		// TODO Method implementation
		return new LinkedHashSet<License>(0);
	}

	/**
	 * Returns the registered contributors.
	 * 
	 * @return the registered contributors.
	 */
	public Set<String> getContributors() {
		// TODO Method implementation
		return new LinkedHashSet<String>(0);
	}

	/**
	 * Returns the Copyrighter plug-in preference store.
	 * 
	 * @return the Copyrighter plug-in preference store.
	 */
	protected IPreferenceStore getStore() {
		return CopyrighterActivator.getDefault().getPreferenceStore();
	}

	/**
	 * Defines the lastly used license preset in the preferences.
	 * 
	 * @param licenseName
	 */
	public void setLastLicensePreset(String licenseName) {
		// TODO Method implementation
	}

	/**
	 * Defines the contributors used in copyrights.
	 * 
	 * @param contributors
	 */
	public void setContributors(Collection<String> contributors) {
		// TODO Method implementation
	}
}
