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
package org.codestorming.copyrighter.license;

import java.io.Serializable;

/**
 * {@link Serializable} {@link License} object.
 *
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class LicenseImpl implements License, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String header;
	private String content;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getHeader() {
		return header;
	}

	@Override
	public String getContent() {
		return content;
	}

	/**
	 * Sets the given {@code name} to this {@code LicenseImpl}.
	 *
	 * @param name the value to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the given {@code header} to this {@code LicenseImpl}.
	 *
	 * @param header the value to set.
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * Sets the given {@code content} to this {@code LicenseImpl}.
	 *
	 * @param content the value to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}// else
		if (obj == null) {
			return false;
		}// else
		if (!(obj instanceof License)) {
			return false;
		}// else
		
		License otherLicense = (License) obj;
		if (name == null) {
			return otherLicense.getName() == null;
		}// else
		
		return name.equals(otherLicense.getName());
	}

}
