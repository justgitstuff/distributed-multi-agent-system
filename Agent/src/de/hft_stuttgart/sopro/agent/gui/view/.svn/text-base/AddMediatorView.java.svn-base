package de.hft_stuttgart.sopro.agent.gui.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Instances of this class open a new shell which can be used to define a new
 * mediator definition.
 * 
 * @author Matthias Huber - MatthewHuber@web.de
 */
public class AddMediatorView {

	/**
	 * The composite which holds all widgets.
	 */
	private Composite composite;

	/**
	 * The list to which the new definition will be added at the end.
	 */
	private List mediatorList;

	/**
	 * This widget contains the name of the mediator.
	 */
	private Text nameText;

	/**
	 * This widget contains the address to the mediator.
	 */
	private Text addressText;

	/**
	 * The number of columns used in the grid layout
	 */
	private static final int NUMBER_OF_COLUMNS = 4;

	private boolean editMode = false;

	/**
	 * Default constructor taking the parent composite and the list of
	 * Mediators.
	 * 
	 * @param composite
	 *            The parent composite.
	 * @param mediators
	 *            The list of Mediators.
	 */
	public AddMediatorView(Composite composite, List mediators) {
		this.composite = composite;
		this.mediatorList = mediators;
	}

	/**
	 * Creates the initial view.
	 */
	public void open() {
		Shell shell = new Shell(composite.getShell());
		shell.setSize(400, 120);
		shell.setLayout(new FillLayout());

		Composite composite = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = NUMBER_OF_COLUMNS;
		gridLayout.makeColumnsEqualWidth = true;
		composite.setLayout(gridLayout);

		Label name = new Label(composite, SWT.NONE);
		name.setText("Name:");
		name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		nameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = NUMBER_OF_COLUMNS - 1;
		nameText.setLayoutData(gridData);

		Label address = new Label(composite, SWT.NONE);
		address.setText("Address:");
		address.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		addressText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = NUMBER_OF_COLUMNS - 1;
		addressText.setText("http://");
		addressText.setLayoutData(gridData);

		// two empty Label
		new Label(composite, SWT.NONE).setVisible(false);
		new Label(composite, SWT.NONE).setVisible(false);

		Button cancel = new Button(composite, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		cancel.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				e.display.getActiveShell().dispose();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Button apply = new Button(composite, SWT.PUSH);
		apply.setText("Apply");
		apply.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		apply.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!editMode) {
					mediatorList.add(addressText.getText() + " (" + nameText.getText() + ")");
					// select the added mediator
					mediatorList.select(mediatorList.getItemCount() - 1);
				} else {
					mediatorList.setItem(mediatorList.getSelectionIndex(), addressText.getText() + " (" + nameText.getText() + ")");
				}
				e.display.getActiveShell().dispose();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		shell.open();
	}

	/**
	 * Sets the Mediator.
	 * 
	 * @param name
	 *            The name of the Mediator.
	 * @param address
	 *            The canonical adress of the Mediator.
	 */
	public void setMediator(String name, String address) {
		nameText.setText(name);
		addressText.setText(address);
		editMode = true;
	}
}
