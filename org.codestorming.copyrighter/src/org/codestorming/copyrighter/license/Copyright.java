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

import java.util.List;

/**
 * Represents a Copyright to apply to a file or project.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 * @since 1.0
 */
public interface Copyright {

	/**
	 * Returns the value of {@code header}.
	 * 
	 * @return the value of {@code header}.
	 */
	public String getHeader();

	/**
	 * Returns the value of {@code license}.
	 * 
	 * @return the value of {@code license}.
	 */
	public License getLicense();

	/**
	 * Returns the value of {@code contributors}.
	 * 
	 * @return the value of {@code contributors}.
	 */
	public List<String> getContributors();
}