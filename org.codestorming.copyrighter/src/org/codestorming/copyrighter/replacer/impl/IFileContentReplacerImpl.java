/*
 * IFileContentReplacerImpl.java                                        24 févr. 2013
 *
 * Copyright (c) 2013 Codestorming.org
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package org.codestorming.copyrighter.replacer.impl;

import java.io.File;

import org.codestorming.copyrighter.replacer.IFileContentReplacer;
import org.codestorming.copyrighter.replacer.ReplacementException;
import org.codestorming.string.FileString;
import org.eclipse.core.resources.IFile;

/**
 * XXX Comment role class
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class IFileContentReplacerImpl extends ReplacerImpl implements IFileContentReplacer {

	/**
	 * The {@link IFile file} containing the substring to replace.
	 */
	protected IFile file;
	
	protected FileString fileString;

	/**
	 * Creates a new {@link IFileContentReplacer}.
	 * 
	 * @param file
	 * @param startPosition
	 * @param endPosition
	 */
	public IFileContentReplacerImpl(IFile file, int startPosition, int endPosition) {
		super(null, startPosition, endPosition);
		this.file = file;
	}

	@Override
	public IFile getIFile() throws ReplacementException {
		return file;
	}

	@Override
	public FileString getContent() throws ReplacementException {
		if (fileString == null) {
			IFile file = getIFile();
			checkFile(file);
	
			// Creating a file from the IFile
			String path = file.getFullPath().toOSString();
			File f = new File(path);
			fileString = new FileString(f);
		}
		return fileString;
	}

	/**
	 * Checks the validity of the given {@link IFile file}.
	 * 
	 * @param file The {@link IFile} to check.
	 * @throws ReplacementException if the given {@link IFile file} is invalid
	 */
	protected void checkFile(IFile file) throws ReplacementException {
		String message = null;
		if (file == null) {
			message = "The given file is null.";
		} else if (!file.exists()) {
			message = "The given file does not exist.";
		}

		// Raise exception if the file is invalid
		if (message != null) {
			throw new ReplacementException(message);
		}
	}

	/**
	 * Internal replace using the {@link FileString#replace(int, int, String)} method.
	 * 
	 * @param replacement The replacement string.
	 * @return The {@link FileString} used for replacing the substring.
	 */
	protected FileString _replace(String replacement) throws ReplacementException {
		getContent().replace(getStartPosition(), getEndPosition(), replacement);
		return getContent();
	}
}
