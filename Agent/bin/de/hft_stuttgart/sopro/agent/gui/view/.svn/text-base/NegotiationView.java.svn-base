package de.hft_stuttgart.sopro.agent.gui.view;

import java.util.List;

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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.hft_stuttgart.sopro.agent.Agent;
import de.hft_stuttgart.sopro.agent.IAgent;
import de.hft_stuttgart.sopro.agent.gui.Amp;
import de.hft_stuttgart.sopro.agent.gui.AmpManager;
import de.hft_stuttgart.sopro.agent.gui.ChartComposite;
import de.hft_stuttgart.sopro.agent.gui.ChartEnum;
import de.hft_stuttgart.sopro.agent.gui.ChartFactory;
import de.hft_stuttgart.sopro.agent.gui.NegotiationSession;
import de.hft_stuttgart.sopro.agent.gui.ViewEnum;
import de.hft_stuttgart.sopro.agent.gui.chart.IChart;
import de.hft_stuttgart.sopro.agent.gui.chart.RoundProgressBar;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceWrapper;
import de.hft_stuttgart.sopro.common.exceptions.ProposalNotFoundException;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;

/**
 * This view will be used to show the user information about the ongoing
 * negotiation. Therefore charts will be used. A line chart shows the total cash
 * value over the time. Other charts showing a resource plan for every resource.
 * Beside that the user gets some information.
 * 
 * @author Matthias Huber - MatthewHuber@web.de
 * @author Frank Erzfeld - frank@erzfeld.de
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class NegotiationView implements IView {

	/**
	 * The {@link ProposalRetriever} Thread.
	 */
	private ProposalRetriever proposalRetriever;

	/**
	 * The retrieve time of new Proposals.
	 */
	private long proposalRetrieveTime = 1000;

	/**
	 * The shell of the user interface.
	 */
	private Shell shell;

	/**
	 * The composite which holds all widgets of the view.
	 */
	private Composite composite;

	/**
	 * The number of columns of the used {@link GridLayout}.
	 */
	private static final int NUMBER_OF_COLUMNS = 10;

	/**
	 * The line chart showing the total cash values over the negotiation rounds
	 */
	private IChart totalCashValueLineChart;

	/**
	 * Shows information about the current running negotiation.
	 */
	private IChart projectInformationStatistics;

	/**
	 * The progress bar which shows the negotiation round state.
	 */
	private IChart bottomProgressBar;

	/**
	 * The button for leaving the negotiation.
	 */
	private Button leaveNegotiationButton;

	/**
	 * Default constructor taking the {@link Shell} as an argument.
	 * 
	 * @param shell
	 *            The shell.
	 */
	public NegotiationView(Shell shell) {
		this.shell = shell;
	}

	/**
	 * {@inheritDoc}
	 */
	public void show() {
		// resize the shell
		shell.setSize(1200, 800);

		// Create the composite holding all the other composites
		composite = new Composite(shell, SWT.BORDER);
		composite.setSize(shell.getClientArea().width, shell.getClientArea().height);
		composite.setLayout(new GridLayout(NUMBER_OF_COLUMNS, true));

		ChartComposite chartComposite = AmpManager.getNegotiationSession().getChartComposite();
		// top left area
		createCashValueLineChartComposite(composite, chartComposite);

		// top right area
		createProjectInformationStatisticsComposite(composite, chartComposite);

		// create the resources view
		createResourcesComposite(composite, chartComposite);

		// create bottom slider
		createBottomSliderComposite(composite, chartComposite);

		// force layout
		composite.layout();

		// start the thread which communicates with the mediator
		proposalRetriever = new ProposalRetriever();
		proposalRetriever.start();
	}

	/**
	 * Creates the composite including line chart for the total cash value.
	 * 
	 * @param parent
	 *            The parent which holds the widgets.
	 * @param chartComposite
	 *            The chartComposite which holds all charts. The charts must
	 *            register themselves at the chartComposite.
	 */
	private void createCashValueLineChartComposite(Composite parent, ChartComposite chartComposite) {
		Composite cashValueLineChartComposite = new Composite(parent, SWT.BORDER);
		cashValueLineChartComposite.setLayout(new FillLayout());
		cashValueLineChartComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, NUMBER_OF_COLUMNS - 2, 1));

		totalCashValueLineChart = ChartFactory.createChart(ChartEnum.TOTALCASHVALUELINECHART, cashValueLineChartComposite);
		chartComposite.addChart(totalCashValueLineChart);
		totalCashValueLineChart.create();
	}

	/**
	 * Creates the top right composite (ProjectInformationStatistics).
	 * 
	 * @param parent
	 *            The parent which holds the widgets.
	 * @param chartComposite
	 *            The chartComposite which holds all charts. The charts must
	 *            register themselves at the chartComposite.
	 */
	private void createProjectInformationStatisticsComposite(Composite parent, ChartComposite chartComposite) {
		Composite projectInformationComposite = new Composite(parent, SWT.BORDER);
		projectInformationComposite.setLayout(new GridLayout(3, true));
		projectInformationComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, NUMBER_OF_COLUMNS - 8, 1));

		projectInformationStatistics = ChartFactory.createChart(ChartEnum.PROJECTINFORMATIONSTATISTICS, projectInformationComposite);
		chartComposite.addChart(projectInformationStatistics);
		projectInformationStatistics.create();
	}

	/**
	 * Creates the allocation and job plans.
	 * 
	 * @param parent
	 *            The parent which holds the widgets.
	 * @param chartComposite
	 *            The chartComposite which holds all charts. The charts must
	 *            register themselves at the chartComposite.
	 */
	private void createResourcesComposite(Composite parent, ChartComposite chartComposite) {
		Composite resourcesComposite = new Composite(parent, SWT.BORDER);
		resourcesComposite.setSize(parent.getClientArea().width, (int) (parent.getClientArea().height * 0.45));
		resourcesComposite.setLayout(new FillLayout());
		resourcesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));

		// TabFolder holding the resources
		TabFolder tabFolder = new TabFolder(resourcesComposite, SWT.TOP);

		TabItem capacityPlansTabItem = new TabItem(tabFolder, SWT.NONE);
		capacityPlansTabItem.setText("Allocation Plans");

		Composite capacityPlansTabItemComposite = new Composite(tabFolder, SWT.NONE);
		capacityPlansTabItemComposite.setLayout(new FillLayout());
		capacityPlansTabItemComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		capacityPlansTabItem.setControl(capacityPlansTabItemComposite);

		CTabFolder resourceFolder = new CTabFolder(capacityPlansTabItemComposite, SWT.BOTTOM | SWT.FLAT);
		int numberOfResources = AmpManager.getNegotiationSession().getProject().getNumberOfResources();
		for (int i = 0; i < numberOfResources; ++i) {
			Composite allocationComposite = createCTabItem(resourceFolder, "Resource" + (i + 1));
			IChart allocationPlan = ChartFactory.createChart(ChartEnum.RESOURCEALLOCATIONPLAN, allocationComposite, i);
			chartComposite.addChart(allocationPlan);
			allocationPlan.create();
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
			chartComposite.addChart(resourcePlan);
			resourcePlan.create();
		}

		resourceFolder2.setSelection(0);
	}

	/**
	 * Creates the {@link RoundProgressBar}.
	 * 
	 * @param parent
	 *            The parent which holds the widgets.
	 * @param chartComposite
	 *            The chartComposite which holds all charts. The charts must
	 *            register themselves at the chartComposite.
	 */
	private void createBottomSliderComposite(Composite parent, ChartComposite chartComposite) {
		Composite bottomProgressBarComposite = new Composite(parent, SWT.BORDER);
		bottomProgressBarComposite.setLayout(new GridLayout(10, true));
		bottomProgressBarComposite.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 10, 1));

		// slider
		bottomProgressBar = new RoundProgressBar(bottomProgressBarComposite);
		chartComposite.addChart(bottomProgressBar);
		bottomProgressBar.create();

		leaveNegotiationButton = new Button(bottomProgressBarComposite, SWT.NONE);
		leaveNegotiationButton.setText("Leave Negotiation");
		leaveNegotiationButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));

		leaveNegotiationButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				dispose(event);
				AmpManager.getAmp().switchToView(ViewEnum.PROJECTVIEW, shell);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	// TODO:refactor, this method returns a composite so it makes more than
	// creating a tab item
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

	/**
	 * This class is a {@link Thread} which waits the amount of time specified
	 * by projectViewRefreshTime and retrieves project update for a specific
	 * project from the WebService.
	 * 
	 * @author Eduard Tudenhoefner - nastra@gmx.net
	 */
	private class ProposalRetriever extends Thread {

		/**
		 * Signalizes whether this Thread should be stopped.
		 */
		private boolean isStopped = false;

		/**
		 * Stores the old winner total cash value of the agent. So we can handle
		 * the round gap between Agent and Mediator.
		 */
		private double oldWinnerTotalCashValueAgent;

		private IProposal oldWinnerProposalAgent;

		private ProposalComposition oldProposalComposition;

		private int round = 0;

		private int rounds = AmpManager.getNegotiationSession().getNegotiationRounds();

		public void setStopped(boolean isStopped) {
			this.isStopped = isStopped;
		}

		/**
		 * {@inheritDoc}
		 */
		public void run() {
			Thread thisThread = Thread.currentThread();

			while (proposalRetriever == thisThread) {
				if (isStopped) {
					break;
				}

				synchronized (this) {
					try {
						wait(proposalRetrieveTime);
					} catch (InterruptedException e) {
						System.out.println("Error pausing the Thread");
					}
				}
				performNegotiation();
			}

		}

		/**
		 * Performs all the steps needed for a negotiation.
		 */
		private void performNegotiation() {
			if (round < rounds) {
				// retrieve proposals
				IAgent agent = AmpManager.getAgent();
				int projectId = AmpManager.getNegotiationSession().getProject().getProjectId();
				MediatorAgentServiceWrapper remoteInstance = AmpManager.getRemoteInstance();
				ProposalComposition proposalComposition = remoteInstance.retrieveProposals(projectId, agent.getAgentId());
				if (null != proposalComposition && proposalComposition.getNumberOfProposals() > 0) {
					if (round > 0) {
						// set a new negotiation round
						getMediatorWinnerAndStoreValues();
						// update the view
						updateView();
					}
					// perform Voting
					List<Integer> evaluationPoints = agent.performVoting(proposalComposition);

					// set the evaluation points
					boolean successful = remoteInstance.setProposalsEvaluationPoints(projectId, agent.getAgentId(), evaluationPoints);

					if (!successful) {
						// the other Agent left the negotiation, so leave it too
						// and go back to ProjectView or step further to
						// ResultView
						closeNegotiation();

						if (!composite.isDisposed()) {
							composite.getDisplay().asyncExec(new Runnable() {
								public void run() {
									MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.YES | SWT.NO);
									messageBox.setText("Negotiation aborted");
									messageBox.setMessage("The other negotiatior has left the negotiation. Would you like to see the results of this negotiation until this time?");
									int buttonID = messageBox.open();
									Amp amp = AmpManager.getAmp();
									switch (buttonID) {
									case (SWT.YES):
										amp.switchToView(ViewEnum.RESULTVIEW, shell);
										break;
									case (SWT.NO):
										dispose(null);
										amp.switchToView(ViewEnum.PROJECTVIEW, shell);
										break;
									}
								}
							});
						}
					}

					// store the old information of the agent
					oldWinnerProposalAgent = agent.getWinnerProposal();
					oldWinnerTotalCashValueAgent = ((Agent) agent).calculateTotalCashValue(oldWinnerProposalAgent);
					oldProposalComposition = proposalComposition;

					++round;
				}
			} else if (round == rounds) {
				getMediatorWinnerAndStoreValues();
				updateView();
				++round;
			} else {
				// close negotiation and go to result view
				closeNegotiation();
				if (!composite.isDisposed()) {
					composite.getDisplay().asyncExec(new Runnable() {
						public void run() {
							AmpManager.getAmp().switchToView(ViewEnum.RESULTVIEW, shell);
						}
					});
				}
			}
		}

		private void getMediatorWinnerAndStoreValues() {
			MediatorAgentServiceWrapper remoteInstance = AmpManager.getRemoteInstance();
			NegotiationSession negotiationSession = AmpManager.getNegotiationSession();
			int indexOfWinnerProposalMediator = remoteInstance.getWinnerProposalsIndexOfLastRound(negotiationSession.getProject().getProjectId());
			double winnerTotalCashValueMediator;
			IProposal winnerProposalMediator;
			if (-1 != indexOfWinnerProposalMediator) {
				try {
					winnerProposalMediator = oldProposalComposition.getProposalByIndex(indexOfWinnerProposalMediator);
					// calculate total cash value
					winnerTotalCashValueMediator = ((Agent) AmpManager.getAgent()).calculateTotalCashValue(winnerProposalMediator);
				} catch (ProposalNotFoundException e) {
					System.out.println("Proposal was not found");
					winnerProposalMediator = oldWinnerProposalAgent;
					winnerTotalCashValueMediator = oldWinnerTotalCashValueAgent;
				}
			} else {
				// TODO ET/MH: check this case, when leaving a negotiation this
				// sysout is printed out
				System.out.println("index is -1");
				winnerProposalMediator = oldWinnerProposalAgent;
				winnerTotalCashValueMediator = oldWinnerTotalCashValueAgent;
			}

			// add the new gained negotiation information to the negotiation
			// session
			negotiationSession.setNegotiationRoundValues(oldWinnerTotalCashValueAgent, winnerTotalCashValueMediator, winnerProposalMediator);
		}
	}

	private void updateView() {
		if (composite.isDisposed()) {
			return;
		}

		composite.getDisplay().asyncExec(new Runnable() {
			public void run() {
				// update all charts
				AmpManager.getNegotiationSession().getChartComposite().update();
			}
		});
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
		stopProposalRetriever();
		projectInformationStatistics.dispose();

		IAgent agent = AmpManager.getAgent();
		AmpManager.getRemoteInstance().leaveProject(AmpManager.getNegotiationSession().getProject().getProjectId(), agent.getAgentId());
		agent.setCurrentProject(null);
	}

	private void closeNegotiation() {
		projectInformationStatistics.dispose();
		stopProposalRetriever();
		AmpManager.getNegotiationSession().setIsFinished(true);
	}

	/**
	 * Stops the Thread which retrieves Proposals from the WebService.
	 */
	private void stopProposalRetriever() {
		if (null != proposalRetriever) {
			synchronized (proposalRetriever) {
				proposalRetriever.setStopped(true);
				proposalRetriever = null;
			}
		}
	}
}
