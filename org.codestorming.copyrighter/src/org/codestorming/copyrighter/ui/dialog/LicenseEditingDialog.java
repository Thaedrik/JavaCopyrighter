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

import org.codestorming.copyrighter.license.License;
import org.codestorming.copyrighter.ui.L;
import org.codestorming.eclipse.util.swt.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog for editing a {@link License}.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class LicenseEditingDialog extends Dialog {

	/**
	 * Flag of the {@code OK} button (value = {@value #OK}).
	 */
	public static final int OK = 0;

	/**
	 * Flag of the {@code CANCEL} button (value = {@value #CANCEL}).
	 */
	public static final int CANCEL = 1;

	private Shell shell;

	private int buttonPressed;

	private Text txt_LicenseName;

	private Text txt_LicenseHeader;

	private License license;

	/**
	 * Creates a new {@code LicenseEditingDialog}.
	 * 
	 * @param parent The parent {@link Shell shell}.
	 */
	public LicenseEditingDialog(Shell parent) {
		super(parent, SWT.RESIZE | SWT.APPLICATION_MODAL);
	}

	/**
	 * Creates a new {@code LicenseEditingDialog}.
	 * 
	 * @param parent The parent {@link Shell shell}.
	 */
	public LicenseEditingDialog(Shell parent, License license) {
		super(parent, SWT.RESIZE | SWT.APPLICATION_MODAL);
		this.license = license;
	}

	/**
	 * Opens this {@code LicenseEditingDialog}.
	 * 
	 * @return the pressed button flag; either {@link #OK} or {@link #CANCEL}.
	 */
	public int open() {
		buttonPressed = CANCEL;
		Shell parent = getParent();
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
		shell.setText(getText());
		createDialog(shell);
		shell.setSize(shell.computeSize(750, 500));
		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return buttonPressed;
	}

	/**
	 * Create this dialog content.
	 * 
	 * @param shell The parent shell.
	 */
	private void createDialog(Shell shell) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		shell.setLayout(layout);

		Label lbl_LicenseName = new Label(shell, SWT.NONE);
		lbl_LicenseName.setText("Name");
		txt_LicenseName = new Text(shell, SWT.BORDER);
		txt_LicenseName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		if (license != null) {
			txt_LicenseName.setText(license.getName());
			txt_LicenseName.setEnabled(false);
		}

		Label lbl_LicenseHeader = new Label(shell, SWT.NONE);
		lbl_LicenseHeader.setText("Header");
		lbl_LicenseHeader.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		txt_LicenseHeader = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		txt_LicenseHeader.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		if (license != null) {
			txt_LicenseHeader.setText(license.getHeader());
		}

		// TODO Add a textarea for the license content.

		Composite buttonContainer = new Composite(shell, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		data.horizontalSpan = 2;
		buttonContainer.setLayoutData(data);
		buttonContainer.setLayout(new GridLayout(2, false));

		Button btn_Cancel = new Button(buttonContainer, SWT.PUSH);
		btn_Cancel.setText(L.btn_cancel);
		SWTUtil.computeButton(btn_Cancel, new GridData(SWT.RIGHT, SWT.BEGINNING, true, false));
		btn_Cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				buttonPressed = CANCEL;
				license = null;
				LicenseEditingDialog.this.shell.close();
			}
		});

		Button btn_Ok = new Button(buttonContainer, SWT.PUSH);
		btn_Ok.setText(L.btn_ok);
		SWTUtil.computeButton(btn_Ok, new GridData(SWT.RIGHT, SWT.BEGINNING, false, false));
		btn_Ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				buttonPressed = OK;
				createLicense();
				LicenseEditingDialog.this.shell.close();
			}
		});
	}

	/**
	 * Creates the license
	 */
	private void createLicense() {
		final License license;
		if (this.license == null) {
			license = new License();
		} else {
			license = this.license;
		}
		license.setName(txt_LicenseName.getText());
		license.setHeader(txt_LicenseHeader.getText());
		this.license = license;
	}

	/**
	 * Returns the defined license.
	 * 
	 * @return the defined license.
	 */
	public License getLicense() {
		return license;
	}

}
