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

/**
 * Description of a software license.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class License {

	private String name;
	private String header;
	private String content;

	/**
	 * Name of this {@code License}.
	 * 
	 * @return the name of this {@code License}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Header of this {@code License}.
	 * 
	 * @return the header of this {@code License}.
	 */
	public String getHeader() {
		return header;
	}
	
	/**
	 * Full content of this {@code License}.
	 * 
	 * @return the full content of this {@code License}.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Defines the name of this {@code License}.
	 * 
	 * @param name the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Defines the header of this {@code License}.
	 * 
	 * @param header the header to set.
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	
	/**
	 * Defines the full content of this {@code License}.
	 * <p>
	 * Generally, this is the content of the file LICENSE.txt at the base
	 * 
	 * @param content the full content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}// else
		if (obj == null) {
			return false;
		}// else
		if (!(obj instanceof License)) {
			return false;
		}// else
		final License other = (License) obj;
		if (name == null) {
			return other.name == null;
		}// else
		return name.equals(other.name);
	}
	
	@Override
	public int hashCode() {
		return name == null ? 0 : name.hashCode();
	}
}
