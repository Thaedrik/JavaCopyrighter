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
package org.codestorming.copyrighter;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;

/**
 * Iterates over {@link IFile}s on a specified {@link IContainer container}.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class IFileIterator extends ResourceIterator<IFile> {

	/**
	 * Creates a new {@code IFileIterator} for the given {@link IContainer container}.
	 * 
	 * @param container
	 */
	public IFileIterator(IContainer container) {
		super(container, IFile.class);
	}
}
