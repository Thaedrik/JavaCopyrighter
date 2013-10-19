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

import org.codestorming.copyrighter.ICopyrighterConstants;
import org.codestorming.copyrighter.license.Copyright;
import org.codestorming.copyrighter.ui.L;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Job launching the {@link CopyrightOperation} for the specified projects and copyright.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class CopyrightJob extends Job {

	protected Collection<IProject> projects;
	protected Copyright copyright;

	/**
	 * Creates a new {@code CopyrightJob}.
	 * 
	 * @param name
	 * @param projects
	 * @param copyright
	 * @since 1.0
	 */
	public CopyrightJob(Collection<IProject> projects, Copyright copyright) {
		super(L.lbl_copyrightJob);
		this.projects = projects;
		this.copyright = copyright;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		IStatus status;
		CopyrightOperation operation = new CopyrightOperation(projects, copyright);
		try {
			operation.execute(monitor);
			status = new Status(IStatus.OK, ICopyrighterConstants.PLUGIN_ID, L.msg_projectsCopyrighted);
		} catch (InvocationTargetException e) {
			status = new Status(IStatus.ERROR, ICopyrighterConstants.PLUGIN_ID, L.error_copyrightOperation, e);
		} catch (CoreException e) {
			status = new Status(IStatus.ERROR, ICopyrighterConstants.PLUGIN_ID, L.error_copyrightOperation, e);
		} catch (InterruptedException e) {
			status = new Status(IStatus.ERROR, ICopyrighterConstants.PLUGIN_ID, L.error_copyrightOperation, e);
		}
		return status;
	}

}
