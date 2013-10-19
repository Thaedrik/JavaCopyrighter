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
package org.codestorming.copyrighter.ui.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.codestorming.copyrighter.JavaCopyrighter;
import org.codestorming.copyrighter.license.Copyright;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * {@link WorkspaceModifyOperation} for inserting the specified copyright into the java
 * files of the specified {@link IProject projects}.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class CopyrightOperation extends WorkspaceModifyOperation {

	protected Collection<IProject> projects;
	protected Copyright copyright;

	/**
	 * Creates a new {@code CopyrightOperation}.
	 * 
	 * @param projects
	 * @param copyright
	 * @since 1.0
	 */
	public CopyrightOperation(Collection<IProject> projects, Copyright copyright) {
		this.projects = projects;
		this.copyright = copyright;
	}

	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException,
			InterruptedException {
		monitor.beginTask("Copyrighting projects", projects.size());
		if (projects != null && copyright != null) {
			for (IProject project : projects) {
				JavaCopyrighter copyrighter = new JavaCopyrighter(project);
				copyrighter.setCopyright(copyright);
				copyrighter.copyright();
				monitor.worked(1);
			}
		}
		monitor.done();
	}

}
