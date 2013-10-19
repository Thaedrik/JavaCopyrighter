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
package org.codestorming.copyrighter.internal.license;

import java.util.List;

import org.codestorming.copyrighter.license.Copyright;
import org.codestorming.copyrighter.license.License;

/**
 * Represents a Copyright to apply to a file or project.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public final class CopyrightImpl implements Copyright {

	/**
	 * Header of the copyright to put above the license header
	 */
	private final String header;

	/**
	 * License of the copyright.
	 */
	private final License license;

	/**
	 * List of the contributors to put in the file copyright header.
	 */
	private final List<String> contributors;

	/**
	 * Creates a new {@code Copyright}.
	 * 
	 * @param header
	 * @param license
	 * @param contributors
	 */
	public CopyrightImpl(String header, License license, List<String> contributors) {
		this.header = header;
		this.license = license;
		this.contributors = contributors;
	}

	@Override
	public String getHeader() {
		return header;
	}

	@Override
	public License getLicense() {
		return license;
	}

	@Override
	public List<String> getContributors() {
		return contributors;
	}
}
