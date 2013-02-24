/*
 * Copyrighter.java                                        24 févr. 2013
 *
 * Copyright (c) 2013 Codestorming.org
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package org.codestorming.copyrighter;

import org.eclipse.core.resources.IProject;

/**
 * This class is an utility for inserting copyright header on each java files of an
 * Eclipse project.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class Copyrighter {

	/**
	 * The project to copyright.
	 */
	protected IProject project;

	/**
	 * The copyright header to put into the projects' java files.
	 */
	private String copyright;

	/**
	 * Creates a new {@code Copyrighter} for the given {@link IProject project}.
	 * 
	 * @param project The project to copyright.
	 * @throws IllegalArgumentException if the given projects list is {@code null}.
	 */
	public Copyrighter(IProject project) {
		if (project == null) {
			throw new IllegalArgumentException("The projects list cannot be null.");
		}// else

		this.project = project;
	}

	/**
	 * Set the copyright header to put into the projects' java files.
	 * 
	 * @param copyright The copyright text.
	 */
	public void setCopyright(String copyright) {
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

		// TODO Creating and launching the project replacer task
		
	}

}