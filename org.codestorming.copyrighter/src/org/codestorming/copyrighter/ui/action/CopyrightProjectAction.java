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

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.codestorming.copyrighter.JavaCopyrighter;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

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
		if (projects.isEmpty()) {
			noProjectSelected();
		} else {
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
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * What to do if there is no selected project.
	 */
	private void noProjectSelected() {
		// TODO Method implementation

	}

	/**
	 * Open the Copyrighter dialog
	 * 
	 * @param projects
	 */
	private void openCopyrighterDialog(Set<IProject> projects) {
		// TODO Method implementation
		doTheStuff(projects);
	}

	// FIXME Temporary code
	@SuppressWarnings("deprecation")
	private void doTheStuff(Set<IProject> projects) {
		StringBuilder copyright = new StringBuilder();
		copyright.append("/***************************************************************************").append('\n');
		copyright.append(" * Copyright (c) ").append(new Date().getYear() + 1900);
		copyright.append(" Codestorming.org.").append('\n');
		copyright.append(" * ").append('\n');
		copyright.append(" * All rights reserved. This program and the accompanying materials").append('\n');
		copyright.append(" * are made available under the terms of the Eclipse Public License v1.0").append('\n');
		copyright.append(" * which accompanies this distribution, and is available at").append('\n');
		copyright.append(" * http://www.eclipse.org/legal/epl-v10.html").append('\n');
		copyright.append(" * ").append('\n');
		copyright.append(" * Contributors:\n");
		copyright.append(" *     Codestorming - initial API and implementation").append('\n');
		copyright.append(" ****************************************************************************/").append('\n');
		String c = copyright.toString();

		// Copyright all the projects
		for (IProject project : projects) {
			JavaCopyrighter copyrighter = new JavaCopyrighter(project);
			copyrighter.setCopyright(c);
			copyrighter.copyright();
		}
	}

}
