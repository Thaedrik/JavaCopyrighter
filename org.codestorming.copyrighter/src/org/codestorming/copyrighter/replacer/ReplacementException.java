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
package org.codestorming.copyrighter.replacer;

/**
 * Exception indicating that an error occured during a replcement operation.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class ReplacementException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * XXX Comment constructor
	 */
	public ReplacementException() {
		super();
	}

	/**
	 * XXX Comment constructor
	 * 
	 * @param message
	 */
	public ReplacementException(String message) {
		super(message);
	}

	/**
	 * XXX Comment constructor
	 * 
	 * @param throwable
	 */
	public ReplacementException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * XXX Comment constructor
	 * 
	 * @param message
	 * @param throwable
	 */
	public ReplacementException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
