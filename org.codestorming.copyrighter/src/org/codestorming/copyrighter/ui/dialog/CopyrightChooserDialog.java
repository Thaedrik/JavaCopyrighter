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
package org.codestorming.copyrighter.ui.dialog;

import java.util.Set;

import org.codestorming.copyrighter.license.License;
import org.codestorming.copyrighter.preferences.CopyrighterPreferences;
import org.codestorming.copyrighter.ui.L;
import org.codestorming.copyrighter.ui.LicenseLabelProvider;
import org.codestorming.eclipse.util.swt.SWTUtil;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * Dialog for configuring the copyright and license header to put on java files.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class CopyrightChooserDialog extends Dialog {

	/**
	 * Flag of the {@code OK} button (value = {@value #OK}).
	 */
	public static final int OK = 0;

	/**
	 * Flag of the {@code CANCEL} button (value = {@value #CANCEL}).
	 */
	public static final int CANCEL = 1;

	protected CopyrighterPreferences preferences = new CopyrighterPreferences();

	private String copyrightHeader;

	private Set<String> contributors;

	private Shell shell;

	private int buttonPressed;

	private GridDataFactory dataFactory;

	private Text txt_Copryright;

	private ComboViewer viewer_Presets;

	private Text txt_Contributor;

	private Button btn_AddContributor;

	private ListViewer viewer_Contributors;

	private Button btn_Cancel;

	private Button btn_Ok;

	private Button btn_RemoveContributor;

	/**
	 * Creates a new {@code CopyrightChooserDialog}.
	 * 
	 * @param parent
	 */
	public CopyrightChooserDialog(Shell parent) {
		super(parent, SWT.RESIZE | SWT.APPLICATION_MODAL);
	}

	/**
	 * Opens this {@code CopyrightChooserDialog}.
	 * 
	 * @return the pressed button flag; either {@link #OK} or {@link #CANCEL}.
	 */
	public int open() {
		buttonPressed = CANCEL;
		Shell parent = getParent();
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
		shell.setText(getText());
		createDialog(shell);
		shell.setSize(shell.computeSize(750, 250));
		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return buttonPressed;
	}

	/**
	 * Creates this dialog content.
	 * 
	 * @param shell
	 */
	private void createDialog(Shell shell) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		shell.setLayout(layout);

		// Field with the Copyright
		createCopyrightField();
		// Combo with the list of preset licences (Eclipse v1, Apache, GNU ...)
		createPresetLicencesCombo();
		// Modifiable list of contributors
		createContributorField();
		// Ok and Cancel buttons
		createButtons();
	}

	/**
	 * Creation of the field for the copyright.
	 */
	private void createCopyrightField() {
		Label lbl_Copyright = new Label(shell, SWT.NONE);
		lbl_Copyright.setText(L.lbl_copyright);
		GridDataFactory.swtDefaults().applyTo(lbl_Copyright);

		txt_Copryright = new Text(shell, SWT.BORDER);
		getFillHorizontalData().applyTo(txt_Copryright);
	}

	/**
	 * Creation of the licenses preset combo.
	 */
	private void createPresetLicencesCombo() {
		Label lbl_Presets = new Label(shell, SWT.NONE);
		lbl_Presets.setText(L.lbl_license);
		GridDataFactory.swtDefaults().applyTo(lbl_Presets);

		Combo cc_Presets = new Combo(shell, SWT.DROP_DOWN);
		getFillHorizontalData().applyTo(cc_Presets);
		viewer_Presets = new ComboViewer(cc_Presets);
		viewer_Presets.setContentProvider(ArrayContentProvider.getInstance());
		viewer_Presets.setLabelProvider(LicenseLabelProvider.getInstance());
		viewer_Presets.setInput(preferences.getLicences());
	}

	/**
	 * Creation of the contributor group field.
	 */
	private void createContributorField() {
		// Composite containing the contributors text field and list viewer.
		Composite contributorsContainer = new Composite(shell, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		contributorsContainer.setLayout(layout);
		contributorsContainer.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).span(2, 1).create());

		// Text field for putting a contributor in the list
		txt_Contributor = new Text(contributorsContainer, SWT.BORDER);
		getFillHorizontalData().applyTo(txt_Contributor);
		txt_Contributor.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				btn_AddContributor.setEnabled(txt_Contributor.getText().length() > 0);
			}
		});

		// Button for adding the specified contributor into the list
		btn_AddContributor = new Button(contributorsContainer, SWT.PUSH);
		btn_AddContributor.setText(L.btn_addContributor);
		SWTUtil.computeButton(btn_AddContributor, GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.create());
		btn_AddContributor.setEnabled(false);
		btn_AddContributor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (txt_Contributor.getText().length() > 0) {
					contributors.add(txt_Contributor.getText());
					viewer_Contributors.refresh(false);
				}
			}
		});

		// List viewer with the contributors
		viewer_Contributors = new ListViewer(contributorsContainer, SWT.MULTI | SWT.BORDER);
		viewer_Contributors.getList().setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		viewer_Contributors.setContentProvider(ArrayContentProvider.getInstance());
		contributors = preferences.getContributors();
		viewer_Contributors.setInput(contributors);
		viewer_Contributors.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer_Contributors.getSelection();
				Object selected = selection.getFirstElement();
				btn_RemoveContributor.setEnabled(selected != null);
			}
		});

		// Button to remove the selected contributor(s) from the list
		btn_RemoveContributor = new Button(contributorsContainer, SWT.PUSH);
		btn_RemoveContributor.setText(L.btn_removeContributor);
		SWTUtil.computeButton(btn_RemoveContributor, GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.create());
		btn_RemoveContributor.setEnabled(false);
	}

	/**
	 * Creation of the OK and CANCEL buttons.
	 */
	private void createButtons() {
		// Buttons container with right aligned content
		Composite buttonsContainer = new Composite(shell, SWT.NONE);
		buttonsContainer.setLayoutData(GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.BEGINNING).span(2, 1)
				.create());
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		buttonsContainer.setLayout(layout);

		// CANCEL button
		btn_Cancel = new Button(buttonsContainer, SWT.PUSH);
		btn_Cancel.setText(L.btn_cancel);
		SWTUtil.computeButton(btn_Cancel, GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.BEGINNING).create());
		btn_Cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				buttonPressed = CANCEL;
				shell.dispose();
			}
		});

		// OK button
		btn_Ok = new Button(buttonsContainer, SWT.PUSH);
		btn_Ok.setText(L.btn_ok);
		SWTUtil.computeButton(btn_Ok, GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.BEGINNING).create());
		btn_Ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				buttonPressed = OK;
				createCopyright();
				save();
				shell.dispose();
			}
		});
	}

	/**
	 * Returns the selected license header or the empty string if no license is selected.
	 * 
	 * @return the selected license header.
	 */
	private String getLicenseText() {
		License license = getLicense();
		if (license != null) {
			return ((License) license).getHeader();
		}// else
		return L.emptyString;
	}

	/**
	 * Returns the selected license, or {@code null}.
	 * 
	 * @return the selected license, or {@code null}.
	 */
	private License getLicense() {
		IStructuredSelection selection = (IStructuredSelection) viewer_Presets.getSelection();
		Object selected = selection.getFirstElement();
		if (selected != null && selected instanceof License) {
			return (License) selected;
		}// else
		return null;
	}

	/**
	 * Returns the configured copyright to put on java files.
	 * 
	 * @return the configured copyright to put on java files.
	 */
	public String getCopyright() {
		return copyrightHeader;
	}

	/**
	 * Generates the header to put on java files with the configuration made with this
	 * dialog.
	 */
	protected void createCopyright() {
		StringBuilder header = new StringBuilder();
		header.append("/***************************************************************************").append('\n');
		header.append(" * ").append(txt_Copryright.getText().replaceAll("\n", "\n * ")).append("\n");
		header.append(" * ").append('\n');
		if (getLicenseText().length() > 0) {
			header.append(getLicenseText().replaceAll("\n", "\n * "));
			header.append(" * ").append('\n');
		}
		header.append(" * Contributors:\n");
		for (String contributor : contributors) {
			header.append(" *     ").append(contributor).append('\n');
		}
		header.append(" ****************************************************************************/").append('\n');
		copyrightHeader = header.toString();
	}

	/**
	 * Saves the user choices
	 */
	protected void save() {
		preferences.setContributors(contributors);
		License license = getLicense();
		if (license != null) {
			preferences.setLastLicensePreset(license.getName());
		}
	}

	/**
	 * Returns the <em>fill horizontal</em> {@link GridDataFactory}.
	 * 
	 * @return the <em>fill horizontal</em> {@link GridDataFactory}.
	 */
	protected GridDataFactory getFillHorizontalData() {
		if (dataFactory == null) {
			dataFactory = GridDataFactory.swtDefaults();
			dataFactory.align(SWT.FILL, SWT.BEGINNING).grab(true, false);
		}
		return dataFactory;
	}
}