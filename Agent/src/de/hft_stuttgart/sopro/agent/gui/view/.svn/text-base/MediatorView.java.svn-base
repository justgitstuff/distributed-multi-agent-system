package de.hft_stuttgart.sopro.agent.gui.view;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import de.hft_stuttgart.sopro.agent.IAgent;
import de.hft_stuttgart.sopro.agent.gui.AmpManager;
import de.hft_stuttgart.sopro.agent.gui.ViewEnum;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceWrapper;

/**
 * This view provides the user the choice to which mediator the agent will be
 * connecting to.
 * 
 * @author Matthias Huber - MatthewHuber@web.de
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class MediatorView implements IView {

	/**
	 * The shell of the user interface.
	 */
	private Shell shell;

	/**
	 * The canonical address of the live Mediator.
	 */
	private static final String LIVE_MEDIATOR = "http://tomcat.examer.de/axis2/services/MediatorAgentService (Live Mediator)";

	/**
	 * The canonical address of the local Mediator.
	 */
	private static final String LOCAL_MEDIATOR = "http://localhost:8080/axis2/services/MediatorAgentService (Local Mediator)";

	/**
	 * This list contains the mediators.
	 */
	private List mediatorList;

	/**
	 * The composite which holds all widgets of the view.
	 */
	private Composite composite;

	/**
	 * The number of columns of the used {@link GridLayout}.
	 */
	private static final int NUMBER_OF_COLUMNS = 5;

	private Button removeMediatorButton;
	private Button connectButton;
	private Button editMediatorButton;

	private Label statusLine;

	private boolean isConnected = false;

	private Button exitButton;

	public MediatorView(Shell shell) {
		this.shell = shell;
	}

	/**
	 * {@inheritDoc}
	 */
	public void show() {
		shell.setSize(600, 400);
		composite = new Composite(shell, SWT.NONE);
		composite.setSize(shell.getClientArea().width, shell.getClientArea().height);
		GridLayout gridLayout = new GridLayout();
		composite.setLayout(gridLayout);

		// top area
		createHeaderComposite(composite);

		// middle area
		createMediatorListComposite(composite);

		// bottom area
		createNavigationButtonsComposite(composite);

		// check button states
		updateButtons();

		composite.layout();

	}

	/**
	 * This method creates the top part of the view. Included widgets are the
	 * label, the list box containing the mediators and the three buttons for
	 * adding, editing and removing of mediators.
	 */
	private void createHeaderComposite(Composite parent) {
		Composite headerComposite = new Composite(parent, SWT.NONE);
		headerComposite.setLayout(new GridLayout(NUMBER_OF_COLUMNS, true));
		headerComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		Label headerLabel = new Label(headerComposite, SWT.NONE);
		headerLabel.setText("Select a Mediator: ");
		headerLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 1, 1));
	}

	/**
	 * This method creates the Composite holding the Mediator and the needed
	 * buttons.
	 */
	private void createMediatorListComposite(Composite parent) {
		Composite mediatorListComposite = new Composite(parent, SWT.NONE);
		mediatorListComposite.setLayout(new GridLayout(NUMBER_OF_COLUMNS, true));
		mediatorListComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite middleAreaLeft = new Composite(mediatorListComposite, SWT.NONE);
		middleAreaLeft.setLayout(new GridLayout(1, true));
		middleAreaLeft.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, NUMBER_OF_COLUMNS - 1, 1));

		mediatorList = new List(middleAreaLeft, SWT.BORDER | SWT.V_SCROLL);
		mediatorList.add(LIVE_MEDIATOR);
		mediatorList.add(LOCAL_MEDIATOR);
		mediatorList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, NUMBER_OF_COLUMNS - 1, 1));

		mediatorList.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateButtons();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		mediatorList.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent arg0) {
			}

			public void mouseDown(MouseEvent arg0) {
			}

			public void mouseDoubleClick(MouseEvent arg0) {
				if (mediatorList.getSelectionIndex() == -1) {
					return;
				}

				String serverAddress = mediatorList.getItem(mediatorList.getSelectionIndex());
				connectToServer(serverAddress);
			}
		});

		Composite controlButtonsComposite = new Composite(mediatorListComposite, SWT.NONE);
		controlButtonsComposite.setLayout(new GridLayout(1, true));
		controlButtonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, NUMBER_OF_COLUMNS - 4, 1));

		Button addMediatorButton = new Button(controlButtonsComposite, SWT.PUSH);
		addMediatorButton.setText("Add");
		addMediatorButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		addMediatorButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button widget = (Button) e.widget;
				AddMediatorView view = new AddMediatorView(widget.getShell(), mediatorList);
				view.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		editMediatorButton = new Button(controlButtonsComposite, SWT.PUSH);
		editMediatorButton.setText("Edit");
		editMediatorButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		editMediatorButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button widget = (Button) e.widget;
				AddMediatorView view = new AddMediatorView(widget.getShell(), mediatorList);
				String selectedItem = mediatorList.getItem(mediatorList.getSelectionIndex());
				String name = selectedItem.substring(selectedItem.indexOf("(") + 1, selectedItem.indexOf(")"));
				String address = selectedItem.substring(0, selectedItem.indexOf("(") - 1);
				view.open();
				view.setMediator(name, address);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		removeMediatorButton = new Button(controlButtonsComposite, SWT.PUSH);
		removeMediatorButton.setText("Remove");
		removeMediatorButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		removeMediatorButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (-1 != mediatorList.getSelectionIndex()) {
					mediatorList.remove(mediatorList.getSelectionIndex());
					updateButtons();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	/**
	 * This method creates the bottom part of the view. Included widgets are the
	 * label which acts like a status line and the two Buttons.
	 */
	private void createNavigationButtonsComposite(Composite parent) {
		Composite navigationButtonsComposite = new Composite(parent, SWT.NONE);
		navigationButtonsComposite.setLayout(new GridLayout(NUMBER_OF_COLUMNS, true));
		navigationButtonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		// Status line if connection fails
		statusLine = new Label(navigationButtonsComposite, SWT.NONE);
		statusLine.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 3, 1));

		exitButton = new Button(navigationButtonsComposite, SWT.PUSH);
		exitButton.setText("Exit");
		exitButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		exitButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (isConnected) {
					if (disconnectFromServer()) {
						isConnected = false;
						changeWidgetTexts();
					} else {
						// TODO MH/ET: handle this case
					}
				} else {
					e.display.getActiveShell().dispose();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			/**
			 * This method tries to disconnect from the mediator.
			 * 
			 * @return Returns true if disconnecting was successful, otherwise
			 *         false.
			 */
			private boolean disconnectFromServer() {
				boolean wasSuccessful = AmpManager.getRemoteInstance().unregisterAgent(AmpManager.getAgent().getAgentId());
				if (wasSuccessful) {
					AmpManager.resetStubInstanceOnDisconnect();
				}
				return wasSuccessful;
			}

		});

		connectButton = new Button(navigationButtonsComposite, SWT.PUSH);
		connectButton.setText("Connect");
		connectButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		connectButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				String serverAddress = mediatorList.getItem(mediatorList.getSelectionIndex());
				connectToServer(serverAddress);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	/**
	 * Connects to the Mediator when the previous registration was successful.
	 * 
	 * @param serverAddress
	 *            The canonical address to the Mediator.
	 */
	private void connectToServer(String serverAddress) {
		try {
			showPogressMonitorDialog(serverAddress);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// ProgressMonitorDialog was canceled by the user:
			// resetting of stub instance and the retrieved projects is
			// necessary
			AmpManager.resetStubInstanceOnDisconnect();
			AmpManager.resetAvailableProjects();
		}
		if (AmpManager.getAgent().getAgentId() != -1) {
			AmpManager.getAmp().switchToView(ViewEnum.PROJECTVIEW, shell);
		} else {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Registering failed!");
			messageBox.setMessage("Registration of Agent failed. Maybe the Mediator is offline or the mediator address is incorrect.");
			messageBox.open();
		}
	}

	private void showPogressMonitorDialog(final String serverAddress) throws InvocationTargetException, InterruptedException {
		ProgressMonitorDialog progressMonitor = new ProgressMonitorDialog(composite.getShell());
		IRunnableWithProgress runnable = new IRunnableWithProgress() {

			public void run(final IProgressMonitor progressMonitor) throws InvocationTargetException, InterruptedException {
				progressMonitor.beginTask("Connecting to Server", IProgressMonitor.UNKNOWN);
				progressMonitor.subTask("Registering Agent");
				int receivedAgentId = connectToMediatorFromProgressMonitorDialog(serverAddress);
				checkedCancelEvent(progressMonitor);

				if (-1 != receivedAgentId) {
					progressMonitor.subTask("Receiving Projects");
					retrieveAvailableProjects();
				} else {
					// need to reset the stub when there was an error on
					// establishing the connection
					AmpManager.resetStubInstanceOnDisconnect();
				}

				checkedCancelEvent(progressMonitor);
				progressMonitor.done();
			}

			/**
			 * Connects to the Mediator and tries to register the Agent.
			 * 
			 * @param serverAddress
			 *            The canonical address to the Mediator.
			 * @return The registered Id for the Agent.
			 */
			private int connectToMediatorFromProgressMonitorDialog(String serverAddress) {
				IAgent agent = AmpManager.getAgent();
				String webServiceAddress = serverAddress.substring(0, serverAddress.indexOf("(") - 1);
				AmpManager.setAddressToWebService(webServiceAddress);
				MediatorAgentServiceWrapper wrapper = AmpManager.getRemoteInstance();
				agent.setAgentId(wrapper.registerAgent());
				return agent.getAgentId();
			}

			/**
			 * Checks whether the Cancel button was pressed.
			 * 
			 * @param progressMonitor
			 *            The progress monitor to cancel.
			 * @throws InterruptedException
			 *             When the cancel button was pressed.
			 */
			private void checkedCancelEvent(IProgressMonitor progressMonitor) throws InterruptedException {
				if (progressMonitor.isCanceled()) {
					throw new InterruptedException();
				}
			}
		};
		progressMonitor.run(true, true, runnable);
	}

	/**
	 * Checks if a mediator is selected
	 * 
	 * @return True if a mediator is selected
	 */
	private boolean isMediatorSelected() {
		return mediatorList.getSelectionIndex() != -1;
	}

	/**
	 * This method updates the state (enabled/disabled) of the buttons.
	 */
	private void updateButtons() {
		boolean enableButtons = isMediatorSelected();
		removeMediatorButton.setEnabled(enableButtons);
		connectButton.setEnabled(enableButtons);
		editMediatorButton.setEnabled(enableButtons);
	}

	/**
	 * Changes the text of the label (status line) and the two buttons (cancel /
	 * connect).
	 */
	private void changeWidgetTexts() {
		if (isConnected) {
			connectButton.setText("Next");
			exitButton.setText("Disconnect");
			statusLine.setText("Connection established.");
		} else {
			exitButton.setText("Cancel");
			connectButton.setText("Connect");
			statusLine.setText("You are now disconnected from server.");
		}
	}

	/**
	 * Retrieves the available projects from the {@link AmpManager}.
	 */
	private void retrieveAvailableProjects() {
		AmpManager.retrieveAvailableProjects();
	}

	/**
	 * {@inheritDoc}
	 */
	public void register() {
		AmpManager.getViewComposite().addView(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose(TypedEvent event) {
		if (event.getSource() instanceof Button || event.getSource() instanceof Shell) {
			// unregister the Agent when he was registered.
			int agentId = AmpManager.getAgent().getAgentId();
			if (agentId > 0) {
				AmpManager.getRemoteInstance().unregisterAgent(agentId);
			}

			// reset stub instance
			AmpManager.resetStubInstanceOnDisconnect();
		}
	}
}
