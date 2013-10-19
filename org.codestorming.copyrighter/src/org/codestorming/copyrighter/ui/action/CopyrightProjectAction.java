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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.codestorming.copyrighter.CopyrighterActivator;
import org.codestorming.copyrighter.internal.license.CopyrightImpl;
import org.codestorming.copyrighter.ui.dialog.CopyrightChooserDialog;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * Action for setting copyrights on project's java files.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class CopyrightProjectAction implements IObjectActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		Set<IProject> projects = getProjectsFromSelection();
		if (!projects.isEmpty()) {
			openCopyrighterDialog(projects);
		}
	}

	/**
	 * Returns the selected projects.
	 * 
	 * @return the selected projects.
	 */
	private Set<IProject> getProjectsFromSelection() {
		Set<IProject> projects = new HashSet<IProject>(3);
		if (selection instanceof IStructuredSelection) {
			for (Iterator<?> iter = ((IStructuredSelection) selection).iterator(); iter.hasNext();) {
				Object selected = iter.next();
				if (selected instanceof IProject) {
					projects.add((IProject) selected);
				}
			}
		}
		return projects;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {}

	/**
	 * Open the Copyrighter dialog
	 * 
	 * @param projects
	 */
	private void openCopyrighterDialog(Set<IProject> projects) {
		Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		if (activeShell == null) {
			CopyrighterActivator.log("Shell is null.", IStatus.ERROR);
			return;
		}// else
		CopyrightChooserDialog dialog = new CopyrightChooserDialog(activeShell);
		if (dialog.open() == CopyrightChooserDialog.OK) {
			doCopyright(projects, dialog.getCopyright());
		}
	}

	/**
	 * Copyrights all the given projects' java files.
	 * 
	 * @param projects
	 * @param copyright
	 */
	private void doCopyright(Set<IProject> projects, CopyrightImpl copyright) {
		// Schedule of the copyright job
		new CopyrightJob(projects, copyright).schedule();
	}

}
