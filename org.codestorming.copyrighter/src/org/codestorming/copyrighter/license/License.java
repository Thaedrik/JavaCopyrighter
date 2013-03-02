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
public interface License {

	/**
	 * Name of this {@code License}.
	 * 
	 * @return the name of this {@code License}.
	 */
	public String getName();

	/**
	 * Header of this {@code License}.
	 * 
	 * @return the header of this {@code License}.
	 */
	public String getHeader();
	
	/**
	 * Full content of this {@code License}.
	 * 
	 * @return the full content of this {@code License}.
	 */
	public String getContent();
}
