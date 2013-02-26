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
package org.codestorming.copyrighter.replacer.impl;

import org.codestorming.copyrighter.replacer.ReplacementException;
import org.codestorming.copyrighter.replacer.Replacer;

/**
 * Default implementation of the {@link Replacer} interface.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class ReplacerImpl implements Replacer {

	/**
	 * The index of the first character of the substring to replace.
	 */
	protected int startPosition;

	/**
	 * The end position of the substring to replace.
	 */
	protected int endPosition;

	/**
	 * The character sequence containing the substring to replace.
	 */
	private CharSequence content;

	/**
	 * Creates a new {@code Replacer}.
	 * 
	 * @param content The character sequence containing the substring to replace.
	 * @param startPosition The index of the first character of the substring to replace.
	 * @param endPosition The end position of the substring to replace.
	 */
	public ReplacerImpl(CharSequence content, int startPosition, int endPosition) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.content = content;
	}

	@Override
	public int getStartPosition() throws ReplacementException {
		return startPosition;
	}

	@Override
	public int getEndPosition() throws ReplacementException {
		return endPosition;
	}

	@Override
	public CharSequence getContent() throws ReplacementException {
		return content;
	}

	@Override
	public String replace(String replacement) throws ReplacementException {
		return _replace(replacement).toString();
	}

	/**
	 * Internal replace using the {@link StringBuffer#replace(int, int, String)} method.
	 * 
	 * @param replacement The replacement string.
	 * @return The {@link StringBuffer} used for replacing the substring.
	 */
	protected CharSequence _replace(String replacement) throws ReplacementException {
		StringBuffer buffer = new StringBuffer(getContent());
		return buffer.replace(getStartPosition(), getEndPosition(), replacement);
	}
}
