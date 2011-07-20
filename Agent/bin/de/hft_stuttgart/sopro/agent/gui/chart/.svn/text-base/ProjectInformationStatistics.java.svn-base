package de.hft_stuttgart.sopro.agent.gui.chart;

import java.text.DecimalFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import de.hft_stuttgart.sopro.agent.gui.AmpManager;
import de.hft_stuttgart.sopro.agent.gui.NegotiationSession;
import de.hft_stuttgart.sopro.common.project.IProject;

/**
 * Displays the current project information like number of jobs, cash value,
 * etc. and some statistics
 * 
 * @author Frank Erzfeld - frank@erzfeld.de
 * @author Eduard Tudenhoefner - nastra@gmx.net
 * @author Matthias Huber - MatthewHuber@web.de
 */
public class ProjectInformationStatistics implements IChart {

	/**
	 * The {@link RuntimeUpdater} Thread which is updating the shown negotiation
	 * runtime.
	 */
	private RuntimeUpdater runtimeUpdater;

	/**
	 * The sleep time for the {@link RuntimeUpdater} Thread.
	 */
	private long runtimeUpdaterSleepTime = 1000;

	/**
	 * Format of the money.
	 */
	private DecimalFormat decimalFormat = new DecimalFormat("##.00");

	/**
	 * The parent which holds the chart.
	 */
	private Composite parent;

	/**
	 * Start time of the negotiation. Used for visualization of the negotiation
	 * duration.
	 */
	private final long startTime = System.currentTimeMillis();

	private Label maximumProjectLengthData;
	private Label minimumProjectLengthData;
	private Label averageProjectLengthData;
	private Label currentProjectLengthData;
	private Label maximumCashValueData;
	private Label minimumCashValueData;
	private Label averageCashValueData;
	private Label currentCashValueData;
	private Label currentNegotiationRunTimeData;

	/**
	 * Creates the chart which shows project information.
	 * 
	 * @param parent
	 *            The parent of this chart.
	 */
	public ProjectInformationStatistics(Composite parent) {
		this.parent = parent;
	}

	/**
	 * {@inheritDoc}
	 */
	public void create() {
		// Set the Font for bold titles in the information view
		Font titleFont = new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD);

		// information statistics header
		Label informationsLabel = new Label(parent, SWT.NONE);
		IProject project = AmpManager.getNegotiationSession().getProject();
		informationsLabel.setText("Project " + project.getProjectName());
		informationsLabel.setFont(titleFont);
		informationsLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

		// Number of jobs left column
		Label numberOfJobsLabel = new Label(parent, SWT.NONE);
		numberOfJobsLabel.setText("Number of Jobs:");
		numberOfJobsLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		// Number of jobs right column
		Label numberOfJobsData = new Label(parent, SWT.NONE);
		numberOfJobsData.setText(Integer.toString(project.getNumberOfJobs() - 2));
		numberOfJobsData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// Maximum Project Length left column
		Label maximumProjectLengthLabel = new Label(parent, SWT.NONE);
		maximumProjectLengthLabel.setText("Maximum project length:");
		maximumProjectLengthLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		// maximum project length right column
		maximumProjectLengthData = new Label(parent, SWT.NONE);
		maximumProjectLengthData.setText("");
		maximumProjectLengthData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// minimum project length left column
		Label minimumProjectLengthLabel = new Label(parent, SWT.NONE);
		minimumProjectLengthLabel.setText("Minimum project length:");
		minimumProjectLengthLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		// minimum project length right column
		minimumProjectLengthData = new Label(parent, SWT.NONE);
		minimumProjectLengthData.setText("");
		minimumProjectLengthData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// average project length left column
		Label averageProjectLengthLabel = new Label(parent, SWT.NONE);
		averageProjectLengthLabel.setText("Average project length:");
		averageProjectLengthLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		// average project length right column
		averageProjectLengthData = new Label(parent, SWT.NONE);
		averageProjectLengthData.setText("");
		averageProjectLengthData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// current project length left column
		Label currentProjectLengthLabel = new Label(parent, SWT.NONE);
		currentProjectLengthLabel.setText("Current project length:");
		currentProjectLengthLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		// current project length right column
		currentProjectLengthData = new Label(parent, SWT.NONE);
		currentProjectLengthData.setText("");
		currentProjectLengthData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// empty row between project length and cash value, empty label used
		// because there is no other way to create empty row in grid layout
		Label emptyLabel = new Label(parent, SWT.NONE);
		emptyLabel.setVisible(false);
		emptyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

		// maximum cash value left column
		Label maximumCashValueLabel = new Label(parent, SWT.NONE);
		maximumCashValueLabel.setText("Maximum Cash Value:");
		maximumCashValueLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		// maximum cash value right column
		maximumCashValueData = new Label(parent, SWT.NONE);
		maximumCashValueData.setText("");
		maximumCashValueData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// minimum cash value left column
		Label minimumCashValueLabel = new Label(parent, SWT.NONE);
		minimumCashValueLabel.setText("Minimum Cash Value:");
		minimumCashValueLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		// minimum cash value right column
		minimumCashValueData = new Label(parent, SWT.NONE);
		minimumCashValueData.setText("");
		minimumCashValueData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// average cash value left column
		Label averageCashValueLabel = new Label(parent, SWT.NONE);
		averageCashValueLabel.setText("Average Cash Value:");
		averageCashValueLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		// average cash value right column
		averageCashValueData = new Label(parent, SWT.NONE);
		averageCashValueData.setText("");
		averageCashValueData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// current cash value left column
		Label currentCashValueLabel = new Label(parent, SWT.NONE);
		currentCashValueLabel.setText("Current Cash Value:");
		currentCashValueLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		// current cash value right column
		currentCashValueData = new Label(parent, SWT.NONE);
		currentCashValueData.setText("");
		currentCashValueData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// empty row between project length and cash value, empty label used
		// because there is no other way to create empty row in grid layout
		emptyLabel = new Label(parent, SWT.NONE);
		emptyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

		// current negotiation run time left column
		Label currentNegotiationRunTime = new Label(parent, SWT.NONE);
		currentNegotiationRunTime.setText("Current negotiation runtime:");
		currentNegotiationRunTime.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		// current negotiation run time right column
		currentNegotiationRunTimeData = new Label(parent, SWT.NONE);
		currentNegotiationRunTimeData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		runtimeUpdater = new RuntimeUpdater(parent.getDisplay());
		runtimeUpdater.start();
	};

	@Override
	public void computeNextRound() {
		// in this chart everything is done in the method 'update'.
	}
	/**
	 * {@inheritDoc}
	 */
	public void update() {
		NegotiationSession negotiationSession = AmpManager.getNegotiationSession();
		double maximumCashValue = negotiationSession.getCurrentMaximumTotalCashValueMediator();
		maximumCashValueData.setText(decimalFormat.format(maximumCashValue) + " EUR");

		double minimumCashValue = negotiationSession.getCurrentMinimumTotalCashValueMediator();
		minimumCashValueData.setText(decimalFormat.format(minimumCashValue) + " EUR");

		double averageCashValue = negotiationSession.getCurrentAverageTotalCashValue();
		averageCashValueData.setText(decimalFormat.format(averageCashValue) + " EUR");

		int currentProjectLength = negotiationSession.getCurrentProjectLength();
		currentProjectLengthData.setText(currentProjectLength + " days");

		int minimumProjectLength = negotiationSession.getCurrentMinimumProjectLength();
		minimumProjectLengthData.setText(minimumProjectLength + " days");

		int maximumProjectLength = negotiationSession.getCurrentMaximumProjectLength();
		maximumProjectLengthData.setText(maximumProjectLength + " days");

		int averageProjectLength = negotiationSession.getCurrentAverageProjectLength();
		averageProjectLengthData.setText(averageProjectLength + " days");

		double totalCashValue = negotiationSession.getLatestTotalCashValue();
		currentCashValueData.setText(decimalFormat.format(totalCashValue) + " EUR");
	}

	private class RuntimeUpdater extends Thread {

		/**
		 * Signalizes whether this Thread should be stopped.
		 */
		private boolean isStopped = false;

		private Display display;

		public void setStopped(boolean isStopped) {
			this.isStopped = isStopped;
		}

		public RuntimeUpdater(Display display) {
			this.display = display;
		}

		/**
		 * {@inheritDoc}
		 */
		public void run() {
			Thread thisThread = Thread.currentThread();

			while (runtimeUpdater == thisThread) {
				if (isStopped) {
					break;
				}

				synchronized (this) {
					try {
						wait(runtimeUpdaterSleepTime);
					} catch (InterruptedException e) {
						System.out.println("Error pausing the Thread");
					}

					if (null == display || display.isDisposed()) {
						break;
					}
					// TODO ET: check for a better solution
					display.asyncExec(new Runnable() {
						public void run() {
							// Set the current negotiation runtime
							if (currentNegotiationRunTimeData.isDisposed()) {
								return;
							}
							currentNegotiationRunTimeData.setText(decimalFormat.format(showRuntime()) + " s");
						}
					});
				}
			}
		}

		/**
		 * This method will return the current runtime of the negotiation
		 * 
		 * @return The current runtime of the project
		 */
		private long showRuntime() {
			// double runtimeInSecs = runtime % 60;
			// double runtimeInMins = (runtime - runtimeInSecs) / 60;
			return (System.currentTimeMillis() - startTime) / 1000;
		}
	}

	/**
	 * Should be called when this view is eligible.
	 */
	public void dispose() {
		stopRuntimeUpdater();
	}

	/**
	 * Stops the {@link RuntimeUpdater} Thread.
	 */
	private void stopRuntimeUpdater() {
		if (null != runtimeUpdater) {
			synchronized (runtimeUpdater) {
				runtimeUpdater.setStopped(true);
				runtimeUpdater = null;
			}
		}
	}

}
