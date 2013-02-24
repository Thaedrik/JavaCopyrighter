/*
 * Replacer.java                                        24 févr. 2013
 *
 * Copyright (c) 2013 Codestorming.org
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package org.codestorming.copyrighter.replacer;

/**
 * A class implementing this interface will be able to replace a substring by a given
 * replacement string.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public interface Replacer {

	/**
	 * Returns the index of the first character of the substring to replace.
	 * 
	 * @return the index of the first character of the substring to replace.
	 * @throws ReplacementException if an error occured when getting the start position.
	 */
	public int getStartPosition() throws ReplacementException;

	/**
	 * Returns the end position of the substring to replace.
	 * 
	 * @return the end position of the substring to replace.
	 * @throws ReplacementException if an error occured when getting the end position.
	 */
	public int getEndPosition() throws ReplacementException;

	/**
	 * Returns the character sequence containing the substring to replace.
	 * 
	 * @return the character sequence containing the substring to replace.
	 * @throws ReplacementException if an error occured when getting the content.
	 */
	public CharSequence getContent() throws ReplacementException;

	/**
	 * Replaces the substring in {@link #getContent()} by the given {@code replacement}
	 * string.
	 * <p>
	 * The substring begins at the index {@link #getStartPosition()} and extends to
	 * {@link #getEndPosition()} - 1.
	 * 
	 * @param replacement The replacement string.
	 * @return A new string with the replaced substring.
	 * @throws NullPointerException if {@code replacement} is {@code null}.
	 * @throws ReplacementException if an error occured during the replacement operation.
	 */
	public String replace(String replacement) throws ReplacementException;
}
