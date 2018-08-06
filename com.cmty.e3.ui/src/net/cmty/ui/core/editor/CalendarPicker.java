package net.cmty.ui.core.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CalendarPicker extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public CalendarPicker(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public CalendarPicker(Shell parent) {
		this(parent, SWT.NONE);
	}

	public CalendarPicker() {
		super(Display.getDefault().getActiveShell());
	}

	/**
	 * Open the dialog
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM);
		shell.setSize(448, 201);
		shell.setText("Time Picker");
		shell.setLayout(new GridLayout(3, false));

		final DateTime calendar = new DateTime(shell, SWT.CALENDAR | SWT.BORDER);
		final DateTime date = new DateTime(shell, SWT.DATE | SWT.SHORT);
		final DateTime time = new DateTime(shell, SWT.TIME | SWT.SHORT);

		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText(" OK ");
		ok.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println(" Calendar date selected (MM/DD/YYYY) = "
						+ (calendar.getMonth() + 1) + " / " + calendar.getDay()
						+ " / " + calendar.getYear());
				System.out.println(" Date selected (MM/YYYY) = "
						+ (date.getMonth() + 1) + " / " + date.getYear());
				System.out.println(" Time selected (HH:MM) = "
						+ time.getHours() + " : " + time.getMinutes());

				System.out.println("(YYYY/MM/DD/HH:MM)" + calendar.getYear()
						+ "/" + (calendar.getMonth() + 1) + "/"
						+ calendar.getDay() + "/" + time.getHours() + ":"
						+ time.getMinutes());
				shell.close();

			}

		});
		shell.setDefaultButton(ok);
		//
	}

}
