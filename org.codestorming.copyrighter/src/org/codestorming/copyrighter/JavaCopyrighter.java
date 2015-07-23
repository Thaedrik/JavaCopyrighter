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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codestorming.copyrighter.license.Copyright;
import org.codestorming.copyrighter.replacer.IFileContentReplacer;
import org.codestorming.copyrighter.replacer.ReplacementException;
import org.codestorming.copyrighter.replacer.impl.IFileContentReplacerImpl;
import org.codestorming.util.io.FileString;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

/**
 * This class is an utility for inserting copyright header on each java files of an
 * Eclipse project.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class JavaCopyrighter {

	/**
	 * Compatible extensions of files that can contain a copyright.
	 * 
	 * @since 0.2
	 */
	public static final String[] compatibleExtensions = { "java", "xtend" };

	protected static Pattern packagePattern = Pattern.compile("package[\\.\\s\\w]+;?");

	/**
	 * The project to copyright.
	 */
	protected IProject project;

	/**
	 * The copyright to put into the projects' java files.
	 */
	private Copyright copyright;

	private Stack<FutureTask<Void>> fileTasks;

	/**
	 * Creates a new {@code Copyrighter} for the given {@link IProject project}.
	 * 
	 * @param project The project to copyright.
	 * @throws IllegalArgumentException if the given project is {@code null}.
	 */
	public JavaCopyrighter(IProject project) {
		if (project == null) {
			throw new IllegalArgumentException("The project cannot be null.");
		}// else

		this.project = project;
		fileTasks = new Stack<FutureTask<Void>>();
	}

	/**
	 * Set the copyright header to put into the projects' java files.
	 * 
	 * @param copyright The copyright text.
	 * @since 1.0
	 */
	public void setCopyright(Copyright copyright) {
		this.copyright = copyright;
	}

	/**
	 * Launch the operation to put the defined copyright header into all the java files of
	 * the defined projects.
	 * 
	 * @throws IllegalStateException if no copyright has been defined.
	 */
	public void copyright() {
		if (copyright == null) {
			throw new IllegalStateException("No copyright has been defined");
		}// else

		for (IFileIterator fileIterator = new IFileIterator(project); fileIterator.hasNext();) {
			final IFile file = fileIterator.next();
			if (!file.isDerived()) {
				copyrightIFile(file);
			}
		}
		createLicenseFile();
		waitForFilesTasks();
		try {
			// refresh project content
			project.refreshLocal(IProject.DEPTH_INFINITE, null);
			fileTasks.clear();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Waiting for files tasks to finish.
	 */
	private void waitForFilesTasks() {
		for (FutureTask<Void> task : fileTasks) {
			try {
				task.get();// Wait for task to finish
			} catch (InterruptedException e) {
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Insert the copyright into the given {@link IFile file}.
	 * 
	 * @param file The file to copyright.
	 */
	protected void copyrightIFile(final IFile file) {
		FutureTask<Void> fileTask = new FutureTask<Void>(new Runnable() {
			@Override
			public void run() {
				if (!file.exists() || !extensionIsCompatible(file)) {
					return;// Doesn't exist or it's not a compatible file.
				}// else

				FileString fileString = new FileString(createFile(file));
				Matcher m = packagePattern.matcher(fileString);
				if (m.find()) {
					IFileContentReplacer replacer = new IFileContentReplacerImpl(fileString, 0, m.start());
					try {
						replacer.replace(createCopyrightFileHeader());
						fileString.flush();
					} catch (ReplacementException e) {
						CopyrighterActivator.getDefault().log(e);
					} catch (IOException e) {
						CopyrighterActivator.getDefault().log(e);
					}
				} else {
					// TODO Put the file in a list of none copyrighted files
				}
			}
		}, null);
		fileTasks.push(fileTask);
		fileTask.run();
	}

	/**
	 * Create or update the license file at the project's root.
	 * 
	 * @since 1.0
	 */
	protected void createLicenseFile() {
		FutureTask<Void> task = new FutureTask<Void>(new Runnable() {
			@Override
			public void run() {
				final String content = copyright.getLicense().getContent();
				final IFile file = project.getFile("LICENCE.txt");
				try {
					if (content != null && content.length() > 0) {
						final ByteArrayInputStream source = new ByteArrayInputStream(content.getBytes());
						if (!file.exists()) {
							file.create(source, true, null);
						} else {
							file.setContents(source, IFile.FORCE | IFile.KEEP_HISTORY, null);
						}
					} else if (file.exists()) {
						file.delete(true, null);
					}
				} catch (CoreException e) {
					CopyrighterActivator.getDefault().log(e);
				}
			}
		}, null);
		fileTasks.push(task);
		task.run();
	}

	/**
	 * Creates an IO {@link File file} with the given {@link IFile}.
	 * 
	 * @param iFile {@code IFile} to convert.
	 * @return the created {@code File}.
	 */
	protected File createFile(IFile iFile) {
		return new File(iFile.getLocation().toOSString());
	}

	/**
	 * Indicates if the given file has one of the compatible
	 * {@link JavaCopyrighter#compatibleExtensions extensions}.
	 * 
	 * @since 0.2
	 */
	protected boolean extensionIsCompatible(IFile file) {
		final String fileExtension = file.getFileExtension();
		for (final String extension : compatibleExtensions) {
			if (extension.equalsIgnoreCase(fileExtension)) {
				return true;
			}
		}// else
		return false;
	}

	private String createCopyrightFileHeader() {
		StringBuilder header = new StringBuilder();
		header.append("/*"); //$NON-NLS-1$
		header.append('\n');
		header.append(" * "); //$NON-NLS-1$
		header.append(copyright.getHeader().replaceAll("\n", "\n * "));//$NON-NLS-1$ //$NON-NLS-2$
		header.append('\n');
		header.append(" * ").append('\n'); //$NON-NLS-1$
		String licenseHeader = copyright.getLicense().getHeader();
		if (licenseHeader.length() > 0) {
			if (!licenseHeader.endsWith("\n")) { //$NON-NLS-1$
				licenseHeader += "\n"; //$NON-NLS-1$
			}
			header.append(" * "); //$NON-NLS-1$
			header.append(licenseHeader.replaceAll("\n", "\n * ")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (copyright.getContributors().size() > 0) {
			header.append("\n * Contributors:\n"); //$NON-NLS-1$
			for (String contributor : copyright.getContributors()) {
				header.append(" *     ").append(contributor).append('\n'); //$NON-NLS-1$
			}
			header.append(" */"); //$NON-NLS-1$
		} else {
			header.deleteCharAt(header.length() - 1).append('/');
		}
		header.append('\n');
		return header.toString();
	}
}
