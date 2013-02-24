/*
 * IFileContentReplacer.java                                        24 févr. 2013
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

import org.eclipse.core.resources.IFile;

/**
 * {@link Replacer} for {@link IFile IFiles}.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public interface IFileContentReplacer extends Replacer {

	/**
	 * Returns the {@link IFile file} containing the substring to replace.
	 * 
	 * @return the {@link IFile file} containing the substring to replace.
	 * @throws ReplacementException if an error occured when getting the file.
	 */
	public IFile getIFile() throws ReplacementException;
}
