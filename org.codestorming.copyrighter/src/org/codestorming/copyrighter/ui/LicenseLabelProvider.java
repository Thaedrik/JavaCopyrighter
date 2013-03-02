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
package org.codestorming.copyrighter.ui;

import org.codestorming.copyrighter.license.License;
import org.eclipse.jface.viewers.LabelProvider;

/**
 * {@link LabelProvider} for {@link License licenses}.
 * <p>
 * One {@code LicenseLabelProvider} can be used by multiple viewers, a 'static' instance
 * is provided by the {@link #getInstance()} method.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class LicenseLabelProvider extends LabelProvider {

	private static LicenseLabelProvider instance;

	@Override
	public String getText(Object element) {
		if (element != null && element instanceof License) {
			String label = ((License) element).getName();
			return label == null ? L.emptyString : label;
		}// else
		return super.getText(element);
	}

	/**
	 * Returns the {@code LicenseLabelProvider} instance.
	 * 
	 * @return the {@code LicenseLabelProvider} instance.
	 */
	public static LicenseLabelProvider getInstance() {
		if (instance == null) {
			instance = new LicenseLabelProvider();
		}
		return instance;
	}

}
