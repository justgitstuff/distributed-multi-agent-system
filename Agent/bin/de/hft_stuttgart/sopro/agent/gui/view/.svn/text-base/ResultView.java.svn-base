package de.hft_stuttgart.sopro.agent.gui.view;

import java.text.DecimalFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.hft_stuttgart.sopro.agent.IAgent;
import de.hft_stuttgart.sopro.agent.data.DataOutputWriter;
import de.hft_stuttgart.sopro.agent.gui.AmpManager;
import de.hft_stuttgart.sopro.agent.gui.ChartEnum;
import de.hft_stuttgart.sopro.agent.gui.ChartFactory;
import de.hft_stuttgart.sopro.agent.gui.NegotiationSession;
import de.hft_stuttgart.sopro.agent.gui.ViewEnum;
import de.hft_stuttgart.sopro.agent.gui.chart.IChart;
import de.hft_stuttgart.sopro.common.project.IProject;

/**
 * The result view that will be shown after successful completion of a
 * negotiation. This view will show statistical project information and charts.
 * 
 * @author Frank Erzfeld - frank@erzfeld.de
 * @author Matthias Huber - MatthewHuber@web.de
 */
public class ResultView implements IView {

	/**
	 * The shell of the user interface.
	 */
	private Shell shell;

	/**
	 * The number of columns of the used {@link GridLayout}.
	 */
	private static final int NUMBER_OF_COLUMNS = 6;

	/**
	 * Format of the money.
	 */
	private DecimalFormat decimalFormat = new DecimalFormat("##.00");

	/**
	 * The button to go back to the {@link ProjectView} to start a new
	 * negotiation.
	 */
	private Button newNegotiationButton;

	public ResultView(Shell shell) {
		this.shell = shell;
	}

	/**
	 * {@inheritDoc}
	 */
	public void show() {
		// resize the shell
		shell.setSize(800, 600);

		// create the composite holding all the other composites
		Composite composite = new Composite(shell, SWT.BORDER);
		composite.setSize(shell.getClientArea().width, shell.getClientArea().height);
		composite.setLayout(new GridLayout());

		NegotiationSession negotiationSession = AmpManager.getNegotiationSession();
		// create the composite showing the project information
		createProjectInformationComposite(composite, negotiationSession);

		// create the composite showing the charts
		createChartsArea(composite, negotiationSession);

		// create
		createBottomBar(composite);
		composite.layout();
	}

	/**
	 * Creates the composite holding the information of the winning proposal
	 * 
	 * @param parent
	 *            The parent composite
	 */
	private void createProjectInformationComposite(Composite parent, NegotiationSession negotiationSession) {
		Composite projectInformationComposite = new Composite(parent, SWT.BORDER);
		projectInformationComposite.setLayout(new GridLayout(NUMBER_OF_COLUMNS / 3, true));
		projectInformationComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Composite titleComposite = new Composite(projectInformationComposite, SWT.NONE);
		titleComposite.setLayout(new GridLayout());
		titleComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, NUMBER_OF_COLUMNS / 3, 1));

		Label projectNameLabel = new Label(titleComposite, SWT.NONE);
		projectNameLabel.setText("Information about winning proposal for Project " + negotiationSession.getProject().getProjectName());

		Composite cashValueInformationComposite = new Composite(projectInformationComposite, SWT.BORDER);
		cashValueInformationComposite.setLayout(new GridLayout(NUMBER_OF_COLUMNS / 2, true));
		cashValueInformationComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Composite projectLengthInformationComposite = new Composite(projectInformationComposite, SWT.BORDER);
		projectLengthInformationComposite.setLayout(new GridLayout(NUMBER_OF_COLUMNS / 2, true));
		projectLengthInformationComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Label totalCashValueLabel = new Label(cashValueInformationComposite, SWT.NONE);
		totalCashValueLabel.setText("Total Cash Value:");
		totalCashValueLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		Label totalCashValueDataLabel = new Label(cashValueInformationComposite, SWT.NONE);
		double totalCashValue = negotiationSession.getLatestTotalCashValue();
		totalCashValueDataLabel.setText(decimalFormat.format(totalCashValue));
		totalCashValueDataLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		Label minimumCashValueLabel = new Label(cashValueInformationComposite, SWT.NONE);
		minimumCashValueLabel.setText("Minimum Cash Value:");
		minimumCashValueLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		Label minimumCashValueDataLabel = new Label(cashValueInformationComposite, SWT.NONE);
		double minimumCashValue = negotiationSession.getCurrentMinimumTotalCashValueMediator();
		minimumCashValueDataLabel.setText(decimalFormat.format(minimumCashValue));
		minimumCashValueDataLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		Label maximumCashValueLabel = new Label(cashValueInformationComposite, SWT.NONE);
		maximumCashValueLabel.setText("Maximum Cash Value:");
		maximumCashValueLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		Label maximumCashValueDataLabel = new Label(cashValueInformationComposite, SWT.NONE);
		double maximumCashValue = negotiationSession.getCurrentMaximumTotalCashValueMediator();
		maximumCashValueDataLabel.setText(decimalFormat.format(maximumCashValue));
		maximumCashValueDataLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		Label averageCashValueLabel = new Label(cashValueInformationComposite, SWT.NONE);
		averageCashValueLabel.setText("Average Cash Value:");
		averageCashValueLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		Label averageCashValueDataLabel = new Label(cashValueInformationComposite, SWT.NONE);
		double averageCashValue = negotiationSession.getCurrentAverageTotalCashValue();
		averageCashValueDataLabel.setText(decimalFormat.format(averageCashValue));
		averageCashValueDataLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		Label projectLengthLabel = new Label(projectLengthInformationComposite, SWT.NONE);
		projectLengthLabel.setText("Winner Project Duration:");
		projectLengthLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		Label projectLengthDataLabel = new Label(projectLengthInformationComposite, SWT.NONE);
		int projectLength = negotiationSession.getCurrentProjectLength();
		projectLengthDataLabel.setText(Integer.toString(projectLength));
		projectLengthDataLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		Label minimumProjectLengthLabel = new Label(projectLengthInformationComposite, SWT.NONE);
		minimumProjectLengthLabel.setText("Minimum Project Duration:");
		minimumProjectLengthLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		Label minimumProjectLengthDataLabel = new Label(projectLengthInformationComposite, SWT.NONE);
		int minimumProjectLength = negotiationSession.getCurrentMinimumProjectLength();
		minimumProjectLengthDataLabel.setText(Integer.toString(minimumProjectLength));
		minimumProjectLengthDataLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		Label maximumProjectLengthLabel = new Label(projectLengthInformationComposite, SWT.NONE);
		maximumProjectLengthLabel.setText("Maximum Project Duration:");
		maximumProjectLengthLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		Label maximumProjectLengthDataLabel = new Label(projectLengthInformationComposite, SWT.NONE);
		int maximumProjectLength = negotiationSession.getCurrentMaximumProjectLength();
		maximumProjectLengthDataLabel.setText(Integer.toString(maximumProjectLength));
		maximumProjectLengthDataLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		Label averageProjectLengthLabel = new Label(projectLengthInformationComposite, SWT.NONE);
		averageProjectLengthLabel.setText("Average Project Duration:");
		averageProjectLengthLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		Label averageProjectLengthDataLabel = new Label(projectLengthInformationComposite, SWT.NONE);
		int averageProjectLength = negotiationSession.getCurrentAverageProjectLength();
		averageProjectLengthDataLabel.setText(Integer.toString(averageProjectLength));
		averageProjectLengthDataLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));
	}

	/**
	 * Creates the composite holding the tab folder
	 * 
	 * @param parent
	 *            The parent composite
	 */
	private void createChartsArea(Composite parent, NegotiationSession negotiationSession) {
		Composite projectGraphicsComposite = new Composite(parent, SWT.NONE);
		projectGraphicsComposite.setLayout(new FillLayout());
		projectGraphicsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		// Create the TabFolder holding the tab composites
		TabFolder tabFolder = new TabFolder(projectGraphicsComposite, SWT.NONE);

		TabItem totalCashValueTabItem = new TabItem(tabFolder, SWT.NONE);
		totalCashValueTabItem.setText("Total Cash Value");

		Composite totalCashValueTabItemComposite = new Composite(tabFolder, SWT.NONE);
		totalCashValueTabItemComposite.setLayout(new FillLayout());
		totalCashValueTabItemComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		totalCashValueTabItem.setControl(totalCashValueTabItemComposite);

		// create a new total cash value line chart
		IChart totalCashValueLineChart = ChartFactory.createChart(ChartEnum.TOTALCASHVALUELINECHART, totalCashValueTabItemComposite);
		totalCashValueLineChart.create();
		totalCashValueLineChart.computeNextRound();
		totalCashValueLineChart.update();

		TabItem capacityPlansTabItem = new TabItem(tabFolder, SWT.NONE);
		capacityPlansTabItem.setText("Allocation Plans");

		Composite capacityPlansTabItemComposite = new Composite(tabFolder, SWT.NONE);
		capacityPlansTabItemComposite.setLayout(new FillLayout());
		capacityPlansTabItemComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		capacityPlansTabItem.setControl(capacityPlansTabItemComposite);

		CTabFolder resourceFolder = new CTabFolder(capacityPlansTabItemComposite, SWT.BOTTOM | SWT.FLAT);
		int numberOfResources = negotiationSession.getProject().getNumberOfResources();
		for (int i = 0; i < numberOfResources; ++i) {
			Composite allocationComposite = createCTabItem(resourceFolder, "Resource" + (i + 1));
			IChart allocationPlan = ChartFactory.createChart(ChartEnum.RESOURCEALLOCATIONPLAN, allocationComposite, i);
			allocationPlan.create();
			allocationPlan.computeNextRound();
			allocationPlan.update();
		}

		resourceFolder.setSelection(0);

		// Create resource plans tab item
		TabItem resourcePlansTabItem = new TabItem(tabFolder, SWT.NONE);
		resourcePlansTabItem.setText("Job Plans");

		Composite resourcePlansTabItemComposite = new Composite(tabFolder, SWT.NONE);
		resourcePlansTabItemComposite.setLayout(new FillLayout());
		resourcePlansTabItemComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		resourcePlansTabItem.setControl(resourcePlansTabItemComposite);

		CTabFolder resourceFolder2 = new CTabFolder(resourcePlansTabItemComposite, SWT.BOTTOM | SWT.FLAT);
		for (int i = 0; i < numberOfResources; ++i) {
			Composite jobsComposite = createCTabItem(resourceFolder2, "Resource" + (i + 1));
			IChart resourcePlan = ChartFactory.createChart(ChartEnum.RESOURCEJOBPLAN, jobsComposite, i);
			resourcePlan.create();
			resourcePlan.computeNextRound();
			resourcePlan.update();
		}

		resourceFolder2.setSelection(0);

	}

	/**
	 * Creates the composite for the bottom bar holding the buttons
	 * 
	 * @param parent
	 *            The parent composite
	 */
	private void createBottomBar(Composite parent) {
		Composite bottomBarComposite = new Composite(parent, SWT.NONE);
		bottomBarComposite.setLayout(new GridLayout(NUMBER_OF_COLUMNS - 2, true));
		bottomBarComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Label emptyLabel = new Label(bottomBarComposite, SWT.NONE);
		emptyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		emptyLabel.setVisible(false);

		Button saveResultsToFileButton = new Button(bottomBarComposite, SWT.PUSH);
		saveResultsToFileButton.setText("Save Results To File");
		saveResultsToFileButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		saveResultsToFileButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				FileDialog saveFile = new FileDialog(shell, SWT.SAVE);
				String[] extensions = {"*.csv", "*.*"};
				saveFile.setFilterExtensions(extensions);
				saveFile.setText("Save Results to File");
				saveFile.setFilterPath(".\\");
				saveFile.setOverwrite(true);
				String filePath = saveFile.open();
				// Cancel
				if(filePath == null){
					return;
				}
				
			
				/*				
				//Check the extention of the filename --> should be .csv
				int extentionIndex = filePath.lastIndexOf(".");
				String extension = ".csv";
				if (extentionIndex != -1) {
					int pathLength = filePath.length();
					String realExtension = "";
					if (pathLength < 0) {
						realExtension = filePath.substring(extentionIndex, pathLength - 1);
					}

					if (realExtension.compareTo(extension) != 0) {
						// Add the extension to the filepath
						filePath += extension;
					}
				} else {
					// Add the extension to the filepath
					filePath += extension;
				}
				*/
					
				DataOutputWriter dataOutput = new DataOutputWriter();
				NegotiationSession negotiationSession = AmpManager.getNegotiationSession();
				dataOutput.writeSolutionProposal(filePath, negotiationSession.getLatestWinnerProposalMediator(), negotiationSession.getProject().getProjectName());
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		newNegotiationButton = new Button(bottomBarComposite, SWT.PUSH);
		newNegotiationButton.setText("New Negotiation");
		newNegotiationButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		newNegotiationButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				dispose(e);
				AmpManager.getAmp().switchToView(ViewEnum.PROJECTVIEW, shell);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Button exitButton = new Button(bottomBarComposite, SWT.PUSH);
		exitButton.setText("Exit Amp");
		exitButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		exitButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose(TypedEvent event) {
		// just leave the current project and reset the project
		IAgent agent = AmpManager.getAgent();
		IProject project = AmpManager.getNegotiationSession().getProject();
		AmpManager.getRemoteInstance().leaveProject(project.getProjectId(), agent.getAgentId());
		agent.setCurrentProject(null);
	}

	/**
	 * {@inheritDoc}
	 */
	public void register() {
		AmpManager.getViewComposite().addView(this);
	}

	private Composite createCTabItem(CTabFolder parent, String itemText) {
		CTabItem tabItem = new CTabItem(parent, SWT.NONE);
		tabItem.setText(itemText);

		// Create the Composite
		Composite resourcePlanComposite = new Composite(parent, SWT.NONE);
		resourcePlanComposite.setLayout(new FillLayout());
		resourcePlanComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));
		tabItem.setControl(resourcePlanComposite);
		return resourcePlanComposite;
	}
}
