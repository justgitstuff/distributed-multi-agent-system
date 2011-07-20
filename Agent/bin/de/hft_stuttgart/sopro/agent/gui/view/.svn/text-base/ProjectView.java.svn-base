package de.hft_stuttgart.sopro.agent.gui.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import de.hft_stuttgart.sopro.agent.IAgent;
import de.hft_stuttgart.sopro.agent.data.NegotiationData;
import de.hft_stuttgart.sopro.agent.gui.AmpManager;
import de.hft_stuttgart.sopro.agent.gui.ViewEnum;
import de.hft_stuttgart.sopro.common.negotiationValues.NegotiationRoundValues;
import de.hft_stuttgart.sopro.common.negotiationValues.ProposalsPerRoundValues;
import de.hft_stuttgart.sopro.common.negotiationValues.RateOfInterestValues;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmEnum;

/**
 * This view provides the user the choice to join a project.
 * 
 * @author Matthias Huber - MatthewHuber@web.de
 * @author Frank Erzfeld - frank@erzfeld.de
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class ProjectView implements IView {

	/**
	 * The update Thread of the {@link ProjectView}.
	 */
	private ProjectViewRefresher projectViewRefresher;

	/**
	 * The refresh time of the {@link ProjectView}.
	 */
	private long projectViewRefreshTime = 5000;

	/**
	 * The shell of the user interface.
	 */
	private Shell shell;

	/**
	 * The composite which holds all widgets of the view.
	 */
	private Composite composite;

	/**
	 * This list contains all available projects depending on the filter
	 * (all/none/one).
	 */
	private List projectList;

	/**
	 * The number of columns of the used {@link GridLayout}.
	 */
	private static final int NUMBER_OF_COLUMNS = 6;

	/**
	 * The list contains all projects received from the mediator.
	 */
	private java.util.List<IProject> allProjects = new ArrayList<IProject>();

	/**
	 * The list contains all project names
	 */
	private String[] allProjectNames;

	/**
	 * The list contains the project names of projects with none negotiator.
	 */
	private String[] noneNegotiatorProjectNames;

	/**
	 * The list contains the project names of projects with one negotiator.
	 */
	private String[] oneNegotiatorProjectNames;

	private Button allButton;
	private Button noneButton;
	private Button oneButton;
	private Button joinButton;

	/**
	 * Label that displays the project name
	 */
	private Label projectNameText;

	/**
	 * Label that displays the number of jobs
	 */
	private Label numberOfJobsText;

	/**
	 * Label that displays the number of resources.
	 */
	private Label numberOfResourcesText;

	private Button backButton;

	private Combo votingAlgorithmCombo;

	private Combo negotiationRoundsCombo;

	private Combo proposalsPerRoundCombo;

	private Label rateOfInterestText;

	/**
	 * Constructor for the project view
	 * 
	 * @param shell
	 */
	public ProjectView(Shell shell) {
		this.shell = shell;
	}

	/**
	 * {@inheritDoc}
	 */
	public void show() {
		shell.setSize(600, 400);

		// Create the composite holding all the other composites
		composite = new Composite(shell, SWT.NONE);
		composite.setSize(shell.getClientArea().width, shell.getClientArea().height);
		composite.setLayout(new GridLayout());

		// create the composite holding the buttons
		createFilterButtonsComposite(composite);

		// create the composite holding the project list
		createProjectsComposite(composite);

		// create the composite holding the status bar and next/cancel buttons
		createButtonHolderComposite(composite);

		// check button states
		updateButtons();

		// enable/disable filter buttons
		allButton.setSelection(true);
		noneButton.setSelection(false);
		oneButton.setSelection(false);

		// draw view before catching the projects
		composite.layout();
		composite.update();

		// receive all projects; show progress bar during processing
		allProjects = AmpManager.retrieveAvailableProjects();

		// fill array which containing all project names
		allProjectNames = receiveAllProjectNames();

		// add all projects; show progress bar during processing
		projectList.setItems(allProjectNames);

		// start update thread
		startRefresherThread();
	}

	/**
	 * Creates the topmost composite holding the filter buttons
	 */
	private void createFilterButtonsComposite(Composite parent) {
		Composite filterButtonsComposite = new Composite(parent, SWT.NONE);
		filterButtonsComposite.setLayout(new GridLayout(NUMBER_OF_COLUMNS, true));
		filterButtonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, NUMBER_OF_COLUMNS, 1));

		allButton = new Button(filterButtonsComposite, SWT.TOGGLE);
		allButton.setText("All Projects");
		allButton.setToolTipText("Displays all available Projects");
		allButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		allButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Button widget = (Button) e.widget;
				if (widget.getSelection()) {
					oneButton.setSelection(false);
					noneButton.setSelection(false);
					projectList.setItems(allProjectNames);
					updateButtons();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		noneButton = new Button(filterButtonsComposite, SWT.TOGGLE);
		noneButton.setText("No Agent");
		noneButton.setToolTipText("Displays all Projects with no Agents connected");
		noneButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		noneButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Button widget = (Button) e.widget;
				if (widget.getSelection()) {
					oneButton.setSelection(false);
					allButton.setSelection(false);
					projectList.setItems(noneNegotiatorProjectNames);
					updateButtons();
				} else {
					// set selection on widget, otherwise it's not pressed
					// afterwards
					widget.setSelection(true);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		oneButton = new Button(filterButtonsComposite, SWT.TOGGLE);
		oneButton.setText("One Agent");
		oneButton.setToolTipText("Displays all Projects with one assigned Agent");
		oneButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		oneButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Button widget = (Button) e.widget;
				if (widget.getSelection()) {
					allButton.setSelection(false);
					noneButton.setSelection(false);
					projectList.setItems(oneNegotiatorProjectNames);
					updateButtons();
				} else {
					// set selection on widget, otherwise it's not pressed
					// afterwards
					widget.setSelection(true);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

	}

	/**
	 * Creates the composite in the middle holding the project list composite
	 * and the composites for static and dynamic project information
	 */
	private void createProjectsComposite(Composite parent) {
		// composite which holds composites of the middle area
		Composite projectsComposite = new Composite(parent, SWT.NONE);
		projectsComposite.setLayout(new GridLayout(NUMBER_OF_COLUMNS, true));
		projectsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, NUMBER_OF_COLUMNS, 1));

		// create all the other composites
		createProjectListComposite(projectsComposite);
		createDynamicInformationComposite(projectsComposite);
		createStaticInformationComposite(projectsComposite);
	}

	/**
	 * Creates the Composite holding the Projects.
	 * 
	 * @param middleArea
	 *            The parent composite.
	 */
	private void createProjectListComposite(Composite middleArea) {
		Composite projectListComposite = new Composite(middleArea, SWT.NONE);
		projectListComposite.setLayout(new GridLayout(1, true));
		projectListComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, NUMBER_OF_COLUMNS - 3, 2));

		Label projectsListLabel = new Label(projectListComposite, SWT.NONE);
		projectsListLabel.setText("List of available Projects");
		projectsListLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// list containing the project names
		projectList = new List(projectListComposite, SWT.BORDER | SWT.V_SCROLL);
		projectList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		projectList.addSelectionListener(new ProjectListSelectionListener());
		projectList.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseDoubleClick(MouseEvent e) {
				if (!(projectList.getSelectionIndex() == -1)) {
					joinProject();					
				}
			}
		});
	}

	/**
	 * Creates the composite holding the dynamic project information
	 * 
	 * @param middleArea
	 *            The parent composite
	 */
	private void createDynamicInformationComposite(Composite middleArea) {
		Composite dynamicInformationComposite = new Composite(middleArea, SWT.BORDER);
		dynamicInformationComposite.setLayout(new GridLayout(3, true));
		dynamicInformationComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, NUMBER_OF_COLUMNS - 3, 1));

		// Voting Algorithm
		Label votingAlgorithmLabel = new Label(dynamicInformationComposite, SWT.NONE);
		votingAlgorithmLabel.setText("Voting Algorithm:");
		votingAlgorithmLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		votingAlgorithmCombo = new Combo(dynamicInformationComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		votingAlgorithmCombo.add("BORDA");
		votingAlgorithmCombo.select(0);
		votingAlgorithmCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		// Negotiation Rounds
		Label negotiationRoundsLabel = new Label(dynamicInformationComposite, SWT.NONE);
		negotiationRoundsLabel.setText("Negotiation Rounds:");
		negotiationRoundsLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		negotiationRoundsCombo = new Combo(dynamicInformationComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		negotiationRoundsCombo.setItems(negotiationRoundsToStringArray());
		negotiationRoundsCombo.select(0);
		negotiationRoundsCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// Proposal Per Round
		Label proposalsPerRoundLabel = new Label(dynamicInformationComposite, SWT.NONE);
		proposalsPerRoundLabel.setText("Proposals per Round:");
		proposalsPerRoundLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		proposalsPerRoundCombo = new Combo(dynamicInformationComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		proposalsPerRoundCombo.setItems(proposalsPerRoundToStringArray());
		proposalsPerRoundCombo.select(0);
		proposalsPerRoundCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// rate of interest
		Label rateOfInterestLabel = new Label(dynamicInformationComposite, SWT.NONE);
		rateOfInterestLabel.setText("Rate of Interest:");
		rateOfInterestLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		rateOfInterestText = new Label(dynamicInformationComposite, SWT.SINGLE | SWT.TRAIL);
		// rateOfInterestText.setText(String.valueOf(RateOfInterestValues.RATE_OF_INTEREST_1.getValue()));
		rateOfInterestText.setText("1 %");
		rateOfInterestText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

	}

	/**
	 * Creates the composite holding the static information about the selected
	 * project
	 * 
	 * @param middleArea
	 *            The parent composite
	 */
	private void createStaticInformationComposite(Composite middleArea) {
		Composite staticInformationComposite = new Composite(middleArea, SWT.BORDER);
		staticInformationComposite.setLayout(new GridLayout(3, true));
		staticInformationComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, NUMBER_OF_COLUMNS - 3, 1));

		Label projectNameLabel = new Label(staticInformationComposite, SWT.NONE);
		projectNameLabel.setText("Project Name:");
		projectNameLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		projectNameText = new Label(staticInformationComposite, SWT.NONE | SWT.SINGLE | SWT.TRAIL);
		projectNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label numberOfJobsLabel = new Label(staticInformationComposite, SWT.NONE);
		numberOfJobsLabel.setText("Number of Jobs:");
		numberOfJobsLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		numberOfJobsText = new Label(staticInformationComposite, SWT.NONE | SWT.SINGLE | SWT.TRAIL);
		numberOfJobsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label numberOfResourcesLabel = new Label(staticInformationComposite, SWT.NONE);
		numberOfResourcesLabel.setText("Number of Resources:");
		numberOfResourcesLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		numberOfResourcesText = new Label(staticInformationComposite, SWT.NONE | SWT.SINGLE | SWT.TRAIL);
		numberOfResourcesText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	/**
	 * Creates the composite holding the join/cancel button
	 */
	private void createButtonHolderComposite(Composite parent) {
		// composite which holds composites of the middle area
		Composite buttonHolderComposite = new Composite(parent, SWT.NONE);
		buttonHolderComposite.setLayout(new GridLayout(NUMBER_OF_COLUMNS, true));
		buttonHolderComposite.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, NUMBER_OF_COLUMNS, 1));

		// Empty Label invisible - it's not possible to create empty grid cells
		// in a GridLayout
		Label emptyLabel = new Label(buttonHolderComposite, SWT.NONE);
		emptyLabel.setVisible(false);
		emptyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, NUMBER_OF_COLUMNS - 2, 1));

		backButton = new Button(buttonHolderComposite, SWT.PUSH);
		backButton.setText("Back");
		backButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		backButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				dispose(event);
				AmpManager.getAmp().switchToView(ViewEnum.MEDIATORVIEW, shell);
			}

			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});

		// Join Button
		joinButton = new Button(buttonHolderComposite, SWT.PUSH);
		joinButton.setText("Join");
		joinButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		joinButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				joinProject();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	/**
	 * Opens the ProgessMonitorDialog which will be showed during the joining to
	 * a project and waiting until another negotiator joins the project.
	 * 
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 */
	private void showProgressMonitorDialog(final int projectId, final int agentId, final int negotiationRounds, final int proposalsPerRound, final String votingAlgorithm,
			final IProject selectedProject, final String rateOfInterest) throws InvocationTargetException, InterruptedException {

		ProgressMonitorDialog progressMonitor = new ProgressMonitorDialog(composite.getShell());
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			boolean negotiationCanStart = false;

			@SuppressWarnings("static-access")
			public void run(final IProgressMonitor progressMonitor) throws InvocationTargetException, InterruptedException {
				progressMonitor.beginTask("Joining Project...", IProgressMonitor.UNKNOWN);
				boolean joinedSuccessful = AmpManager.getRemoteInstance().joinProject(projectId, agentId, negotiationRounds, proposalsPerRound, votingAlgorithm);

				if (joinedSuccessful) {
					stopRefresherThread();
					setValuesOnAgent();
					progressMonitor.setTaskName("Waiting for another Negotiator...");

					while (!negotiationCanStart) {
						// check if user canceled operation
						// TODO ET: check whether this could be implemented in a
						// nicer way
						if (progressMonitor.isCanceled()) {
							throw new InterruptedException();
						}

						// send the progress monitor to sleep
						Thread.currentThread().sleep(projectViewRefreshTime);
						negotiationCanStart = checkNegotiatorAmount();
						progressMonitor.worked(1);
					}
				} else {
					// TODO MH/ET: need to handle the case when joining a
					// project fails.
				}
				progressMonitor.done();
			}

			/**
			 * Sets the needed information on the Agent.
			 */
			private void setValuesOnAgent() {
				IAgent agent = AmpManager.getAgent();
				agent.setVotingAlgorithm(VotingAlgorithmEnum.valueOf(votingAlgorithm));
				// agent.setInterestRate(Double.valueOf(rateOfInterest));
				agent.setInterestRate(RateOfInterestValues.RATE_OF_INTEREST_1.getValue());
				agent.setCurrentProject(selectedProject);
			}

			/**
			 * Checks whether another Negotiatior has joined the project.
			 * 
			 * @return Whether another Negotiatior has joined this project.
			 */
			private boolean checkNegotiatorAmount() {
				IAgent agent = AmpManager.getAgent();
				IProject project = AmpManager.getRemoteInstance().retrieveProjectChange(agent.getCurrentProject());
				agent.setCurrentProject(project);
				return project.getCurrentAgentsOnProject().size() == 2;
			}
		};
		progressMonitor.run(true, true, runnable);
	}

	/**
	 * This method performs the tasks necessary to join a project.
	 */
	private void joinProject() {
		int selectionIndex = projectList.getSelectionIndex();
		// check if it's assured that a project is selected
		if (-1 != selectionIndex) {
			StringBuilder stringBuilder = new StringBuilder(projectList.getItem(projectList.getSelectionIndex()));
			stringBuilder.delete(stringBuilder.length() - 6, stringBuilder.length());

			// search for the selected project
			IProject selectedProject = getProjectByName(allProjects, stringBuilder.toString());

			if (null != selectedProject) {
				IAgent agent = AmpManager.getAgent();
				int projectId = selectedProject.getProjectId();
				int agentId = agent.getAgentId();
				int negotiationRounds = Integer.valueOf(negotiationRoundsCombo.getItem(negotiationRoundsCombo.getSelectionIndex()));
				int proposalsPerRound = Integer.valueOf(proposalsPerRoundCombo.getItem(proposalsPerRoundCombo.getSelectionIndex()));
				String votingAlgorithm = votingAlgorithmCombo.getItem(votingAlgorithmCombo.getSelectionIndex());
				String rateOfInterest = rateOfInterestText.getText();
				try {
					// show the progress monitor dialog during the process of
					// joining the project and waiting until the second
					// negotiator
					// has joined the project
					showProgressMonitorDialog(projectId, agentId, negotiationRounds, proposalsPerRound, votingAlgorithm, selectedProject, rateOfInterest);

					// create NegotiationSession before switching to the
					// negotiation
					// view
					AmpManager.createNegotiationSession(negotiationRounds, selectedProject);
					AmpManager.getAmp().switchToView(ViewEnum.NEGOTIATIONVIEW, shell);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// ProgressMonitorDialog was canceled by the user:
					// leave the joined project and restart the update
					// thread is necessary
					AmpManager.getRemoteInstance().leaveProject(projectId, agentId);
					agent.setCurrentProject(null);

					startRefresherThread();
				}
			}
		}
	}

	/**
	 * This method returns all project names.
	 * 
	 * @return Returns all project names.
	 */
	private String[] receiveAllProjectNames() {
		// TODO MH/ET: refactor this method because it currently creates two
		// other arrays
		if (null == allProjects || allProjects.isEmpty()) {
			return new String[] { "No Jobs available" };
		}

		String[] projectNames = new String[allProjects.size()];

		java.util.List<IProject> projectsWithNoneNegotiator = new ArrayList<IProject>();
		java.util.List<IProject> projectsWithOneNegotiator = new ArrayList<IProject>();

		// fill all projects name into the array which contains all;
		// additionally add the projects into the different categories (one/none
		// negotiator).
		int counter = 0;
		for (IProject project : allProjects) {
			int negotiator = 0;
			if (null != project.getCurrentAgentsOnProject()) {
				negotiator = project.getCurrentAgentsOnProject().size();
			}
			// add project name
			projectNames[counter] = project.getProjectName() + " (" + negotiator + "/2)";

			// add project name to a category for the other filters
			if (null != project.getCurrentAgentsOnProject() && negotiator == 0) {
				projectsWithNoneNegotiator.add(project);
			} else if (null != project.getCurrentAgentsOnProject() && negotiator == 1) {
				projectsWithOneNegotiator.add(project);
			}
			++counter;
		}

		noneNegotiatorProjectNames = createNoneNegotiatorProjectsArray(projectsWithNoneNegotiator);
		oneNegotiatorProjectNames = createOneNegotiatorProjectsArray(projectsWithOneNegotiator);

		return projectNames;
	}

	/**
	 * This method returns the project names of projects with no negotiator.
	 * 
	 * @return Returns the project names of projects with none negotiator.
	 */
	private String[] createNoneNegotiatorProjectsArray(java.util.List<IProject> sourceProjects) {
		String[] projectNames = new String[sourceProjects.size()];
		int counter = 0;
		for (IProject project : sourceProjects) {
			projectNames[counter] = project.getProjectName() + " (0/2)";
			++counter;
		}

		return projectNames;
	}

	/**
	 * This method returns the project names of projects with one negotiator.
	 * 
	 * @return Returns the project names of projects with one negotiator.
	 */
	private String[] createOneNegotiatorProjectsArray(java.util.List<IProject> sourceProjects) {
		String[] projectNames = new String[sourceProjects.size()];
		int i = 0;
		for (IProject project : sourceProjects) {
			projectNames[i] = project.getProjectName() + " (1/2)";
			++i;
		}

		return projectNames;
	}

	/**
	 * Checks if a project is selected
	 * 
	 * @return Returns true if a project is selected, otherwise false
	 */
	private boolean isNegotiationSelected() {
		return projectList.getSelectionIndex() != -1;
	}

	/**
	 * This method updates the state (enabled/disabled) of the buttons.
	 */
	private void updateButtons() {
		joinButton.setEnabled(isNegotiationSelected());
	}

	/**
	 * A method to convert the enumerations of the negotiation round values to a
	 * String array
	 * 
	 * @return String array of the negotiation rounds
	 */
	private String[] negotiationRoundsToStringArray() {
		NegotiationRoundValues[] values = NegotiationRoundValues.values();
		String[] results = new String[values.length];
		int counter = 0;

		for (NegotiationRoundValues each : values) {
			results[counter] = Integer.toString(each.getValue());
			counter++;
		}
		return results;
	}

	/**
	 * A method to convert the enumerations of the proposals per round values to
	 * a String
	 * 
	 * @return String array of the proposals per round
	 */
	private String[] proposalsPerRoundToStringArray() {
		ProposalsPerRoundValues[] values = ProposalsPerRoundValues.values();
		String[] results = new String[values.length];
		int counter = 0;

		for (ProposalsPerRoundValues each : values) {
			results[counter] = Integer.toString(each.getValue());
			counter++;
		}
		return results;
	}

	/**
	 * This method searches for a project within the project list. For searching
	 * the name of the project is used.
	 * 
	 * @param sourceList
	 *            The list containing the projects.
	 * @param projectName
	 *            The project name to search for.
	 * @return
	 */
	private IProject getProjectByName(java.util.List<IProject> sourceList, String projectName) {
		for (IProject project : sourceList) {
			if (projectName.equals(project.getProjectName())) {
				return project;
			}
		}
		return null;
	}

	/**
	 * The listener class for the Project List.
	 * 
	 * @author Matthias Huber - MatthewHuber@web.de
	 * @author Eduard Tudenhoefner - nastra@gmx.net
	 */
	private class ProjectListSelectionListener implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			// check if an entry is selected.
			if (!(projectList.getSelectionIndex() == -1)) {
				// retrieve the project name
				String listEntryText = projectList.getItem(projectList.getSelectionIndex());
				String projectName = retrieveProjectName(listEntryText);
				// update the view
				updateDynamicInformationComposite(listEntryText);
				updateStaticInformationComposite(projectName);

				// Create the tooltip for the selected item
				createToolTip(projectName);

				updateButtons();
			}
		}

		private String retrieveProjectName(String listEntryText) {
			StringBuilder stringBuilder = new StringBuilder(listEntryText);
			int lengthOfProjectName = stringBuilder.length();
			stringBuilder.delete(lengthOfProjectName - 6, lengthOfProjectName);

			return stringBuilder.toString();
		}

		private void updateDynamicInformationComposite(String listEntryText) {
			// validate the number of joined negotiators; only if no one has
			// joined the project, the user has the possibility to do
			// settings, otherwise
			int indexOfSlash = listEntryText.lastIndexOf("/");
			int negotiatorNumber = Integer.parseInt(listEntryText.substring(indexOfSlash - 1, indexOfSlash));
			String projectName = retrieveProjectName(listEntryText);
			switch (negotiatorNumber) {
			case 0:
				enableWidgets(true);
				break;
			case 1:
				setNegotiationValues(projectName);
				enableWidgets(false);
				break;
			case 2:
				setNegotiationValues(projectName);
				enableWidgets(false);
				break;
			default:
				break;
			}
		}

		/**
		 * Enables some widgets.
		 * 
		 * @param enabled
		 *            Indicates whether the widgets should be enabled or not.
		 */
		private void enableWidgets(boolean enabled) {
			votingAlgorithmCombo.setEnabled(enabled);
			negotiationRoundsCombo.setEnabled(enabled);
			proposalsPerRoundCombo.setEnabled(enabled);
			rateOfInterestText.setEnabled(enabled);
		}

		private void setNegotiationValues(String projectName) {
			Map<Integer, NegotiationData> negotiationDataMap = AmpManager.getNegotiationDataMap();
			if (null != negotiationDataMap && !negotiationDataMap.isEmpty()) {
				IProject project = getProjectByName(allProjects, projectName);
				NegotiationData negotiationData = negotiationDataMap.get(new Integer(project.getProjectId()));
				if (null != negotiationData) {
					votingAlgorithmCombo.select(votingAlgorithmCombo.indexOf(negotiationData.getVotingAlgorithm()));
					negotiationRoundsCombo.select(negotiationRoundsCombo.indexOf(Integer.toString(negotiationData.getNegotiationRounds())));
					proposalsPerRoundCombo.select(proposalsPerRoundCombo.indexOf(Integer.toString(negotiationData.getProposalsPerRound())));
				}
			}
		}

		private void updateStaticInformationComposite(String projectName) {
			projectNameText.setText(projectName);
			// get the project and set the needed information
			IProject project = getProjectByName(allProjects, projectName);
			numberOfJobsText.setText(Integer.toString(project.getNumberOfJobs() - 2));
			numberOfResourcesText.setText(Integer.toString(project.getNumberOfResources()));
		}

		/**
		 * Creates the tool tip for the given Project.
		 * 
		 * @param projectName
		 *            The project for which the tool tip should be created.
		 */
		private void createToolTip(String projectName) {
			// get the project
			IProject project = getProjectByName(allProjects, projectName);
			StringBuffer stringBuffer = new StringBuffer("Resource information for Project: ");
			stringBuffer.append(projectName);
			stringBuffer.append("\n\n");
			int[] maxCapacity = project.getMaxCapacities();

			for (int i = 0; i < project.getNumberOfResources(); i++) {
				stringBuffer.append("Capacity for Resource " + (i + 1) + ": " + maxCapacity[i] + " hours\n");
			}
			projectList.setToolTipText(stringBuffer.toString());
		}
	}

	/**
	 * This class is a {@link Thread} which waits the amount of time specified
	 * by projectViewRefreshTime and retrieves project updates from the
	 * WebService.
	 * 
	 * @author Eduard Tudenhoefner - nastra@gmx.net
	 */
	private class ProjectViewRefresher extends Thread {

		/**
		 * Signalizes whether this Thread should be interrupted.
		 */
		private boolean isInterrupted = false;

		public void setInterrupted(boolean isInterrupted) {
			this.isInterrupted = isInterrupted;
		}

		/**
		 * {@inheritDoc}
		 */
		public void run() {
			Thread thisThread = Thread.currentThread();

			while (projectViewRefresher == thisThread) {
				if (isInterrupted) {
					break;
				}

				synchronized (this) {
					try {
						wait(projectViewRefreshTime);
					} catch (InterruptedException e) {
						System.out.println("Error pausing the Thread");
					}
				}

				if (null == AmpManager.getAgent().getCurrentProject()) {
					allProjects = AmpManager.getRemoteInstance().retrieveProjectChanges();
					updateView();
				}
			}
		}

		/**
		 * Updates the project list
		 */
		private void updateView() {
			if (composite.isDisposed()) {
				return;
			}

			// set the list input of the active filter button
			composite.getDisplay().asyncExec(new Runnable() {
				public void run() {
					// create the new arrays containing the project names
					allProjectNames = receiveAllProjectNames();

					// store the index of the item at the top of the receiver
					int topIndex = projectList.getTopIndex();

					// store the old selection and position of the horizontal
					// bar's slider
					String oldProjectName = null;
					if (!projectList.isDisposed() && (-1 != projectList.getSelectionIndex())) {
						oldProjectName = projectList.getItem(projectList.getSelectionIndex());
					}

					// update List depending on the activated filter
					if (!allButton.isDisposed() && allButton.getSelection()) {
						projectList.setItems(allProjectNames);
					} else if (!noneButton.isDisposed() && noneButton.getSelection()) {
						projectList.setItems(noneNegotiatorProjectNames);
					} else if (!oneButton.isDisposed() && oneButton.getSelection()) {
						projectList.setItems(oneNegotiatorProjectNames);
					}

					// select the old selection if possible and the slider
					// position
					if (!projectList.isDisposed()) {
						if (null != oldProjectName) {
							projectList.setSelection(projectList.indexOf(oldProjectName));
						}
						projectList.setTopIndex(topIndex);
					}

				}
			});
		}
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
		stopRefresherThread();

		// unregister and resetting of stub should only be done when 'Back'
		// button was pressed. If Shell is closed then the resetting of the Stub
		// is handled by the MediatorView
		if (event.getSource() instanceof Button) {
			Button button = (Button) event.getSource();
			if (button.equals(backButton)) {

				// unregister the Agent when he was registered.
				int agentId = AmpManager.getAgent().getAgentId();
				if (agentId > 0) {
					AmpManager.getRemoteInstance().unregisterAgent(agentId);
				}

				// reset stub instance and the available projects
				AmpManager.resetStubInstanceOnDisconnect();
				AmpManager.resetAvailableProjects();
			}
		}
	}

	/**
	 * Creates a new instance of the {@link ProjectViewRefresher} and starts it.
	 */
	private void startRefresherThread() {
		projectViewRefresher = new ProjectViewRefresher();
		projectViewRefresher.start();
	}

	/**
	 * Stops the {@link ProjectViewRefresher} thread.
	 */
	private void stopRefresherThread() {
		// stops the refresher thread
		if (null != projectViewRefresher) {
			synchronized (projectViewRefresher) {
				projectViewRefresher.setInterrupted(true);
				projectViewRefresher = null;
			}
		}
	}

}
