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

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.codestorming.copyrighter.CopyrighterActivator;
import org.codestorming.copyrighter.ICopyrighterConstants;
import org.codestorming.copyrighter.license.License;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Provides the Java Copyrighter plug-in global preferences.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class CopyrighterPreferences {

	private static final String SEPARATOR = "%";

	/**
	 * Returns the Copyrighter plug-in preference store.
	 * 
	 * @return the Copyrighter plug-in preference store.
	 */
	protected IPreferenceStore getStore() {
		return CopyrighterActivator.getDefault().getPreferenceStore();
	}

	/**
	 * Returns the registered {@link License licenses}.
	 * 
	 * @return the registered {@link License licenses}.
	 */
	@SuppressWarnings("unchecked")
	public Set<License> getLicences() {
		Set<License> licenses = null;
		final String serializedLicenses = getStore().getString(ICopyrighterConstants.PREF_LICENSES_CONTENT);
		final ByteArrayInputStream bais = new ByteArrayInputStream(serializedLicenses.getBytes());
		final XMLDecoder xmlDecoder = new XMLDecoder(bais);
		try {
			final Object readObject = xmlDecoder.readObject();
			if (readObject instanceof Set) {
				licenses = (Set<License>) readObject;
			}
		} catch (Exception e) {
		}
		return licenses == null ? new LinkedHashSet<License>() : licenses;
	}

	/**
	 * Returns the lastly used copyright header.
	 * 
	 * @return the lastly used copyright header.
	 */
	public String getLastCopyrightHeader() {
		return getStore().getString(ICopyrighterConstants.PREF_LAST_COPYRIGHT_HEADER);
	}

	/**
	 * Returns the lastly used license preset in the preferences.
	 * 
	 * @return the lastly used license preset in the preferences.
	 */
	public String getLastLicensePreset() {
		return getStore().getString(ICopyrighterConstants.PREF_LAST_LICENSE);
	}

	/**
	 * Returns the registered contributors.
	 * 
	 * @return the registered contributors.
	 */
	public Set<String> getContributors() {
		String contributors = getStore().getString(ICopyrighterConstants.PREF_CONTRIBUTORS);
		Set<String> contributorsSet = new LinkedHashSet<String>();
		if (contributors.length() > 0) {
			for (String contributor : contributors.split(SEPARATOR)) {
				contributorsSet.add(contributor);
			}
		}
		return contributorsSet;
	}

	/**
	 * Defines the lastly used copyright header.
	 * 
	 * @param header the lastly used copyright header.
	 */
	public void setLastCopyrightHeader(String header) {
		getStore().setValue(ICopyrighterConstants.PREF_LAST_COPYRIGHT_HEADER, header);
	}

	/**
	 * Defines the lastly used license preset in the preferences.
	 * 
	 * @param licenseName The name of the lastly used license.
	 */
	public void setLastLicensePreset(String licenseName) {
		getStore().setValue(ICopyrighterConstants.PREF_LAST_LICENSE, licenseName);
	}

	/**
	 * Defines the contributors used in copyrights.
	 * 
	 * @param contributors The contributors to set.
	 */
	public void setContributors(Set<String> contributors) {
		StringBuilder contributorStr = new StringBuilder();
		if (contributors.size() > 0) {
			for (String contributor : contributors) {
				contributorStr.append(contributor);
				contributorStr.append(SEPARATOR);
			}
			contributorStr.deleteCharAt(contributorStr.length() - 1);
		}
		getStore().setValue(ICopyrighterConstants.PREF_CONTRIBUTORS, contributorStr.toString());
	}

	/**
	 * Registers the given {@link License license}.
	 * <p>
	 * Does nothing if the license already exists.
	 * 
	 * @param license The license to register
	 * @throws IOException If an error occurs when saving the license.
	 * @throws SecurityException If the current user have not enough rights to use the
	 *         file system at the bundle data location.
	 */
	public void addLicense(License license) {
		final Set<License> licenses = getLicences();
		if (license != null) {
			// Add/Replace the license
			licenses.remove(license);
			licenses.add(license);
		}
		
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		final XMLEncoder xmlEncoder = new XMLEncoder(baos);
		xmlEncoder.writeObject(licenses);
		xmlEncoder.close();
		getStore().setValue(ICopyrighterConstants.PREF_LICENSES_CONTENT, baos.toString());
	}

	/**
	 * Removes the given {@link License license}.
	 * 
	 * @param license The license to remove.
	 * @throws SecurityException If the current user have not enough rights to use the
	 *         file system at the bundle data location.
	 */
	public void removeLicense(License license) throws SecurityException {
		final Set<License> licenses = getLicences();
		licenses.remove(license);
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		final XMLEncoder xmlEncoder = new XMLEncoder(baos);
		xmlEncoder.writeObject(licenses);
		xmlEncoder.close();
		getStore().setValue(ICopyrighterConstants.PREF_LICENSES_CONTENT, baos.toString());
	}

}
