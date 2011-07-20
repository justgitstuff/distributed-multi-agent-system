package de.hft_stuttgart.sopro.agent.gui;

import org.eclipse.swt.widgets.Composite;

import de.hft_stuttgart.sopro.agent.gui.chart.IChart;
import de.hft_stuttgart.sopro.agent.gui.chart.ProjectInformationStatistics;
import de.hft_stuttgart.sopro.agent.gui.chart.ResourceAllocationPlan;
import de.hft_stuttgart.sopro.agent.gui.chart.ResourceJobPlan;
import de.hft_stuttgart.sopro.agent.gui.chart.RoundProgressBar;
import de.hft_stuttgart.sopro.agent.gui.chart.TotalCashValueLineChart;
import de.hft_stuttgart.sopro.agent.gui.view.IView;

/**
 * Factory class for creating {@link IChart} instances.
 * 
 * @author Matthias Huber - MatthewHuber@web.de
 */
public class ChartFactory {

	/**
	 * Default constructor which should't be called.
	 */
	private ChartFactory() {
	}

	/**
	 * Creates a single {@link IChart} instance from the specifying
	 * {@link ViewEnum}.
	 * 
	 * @param viewEnum
	 *            The {@link ViewEnum} specifying the needed {@link IView}
	 *            instance.
	 * @param shell
	 *            The shell where to draw the {@link IView} to.
	 * @return An {@link IView} instance.
	 */
	public static IChart createChart(ChartEnum chartEnum, Composite parent) {
		switch (chartEnum) {
		case TOTALCASHVALUELINECHART:
			return new TotalCashValueLineChart(parent);
		case ROUNDPROGRESSBAR:
			return new RoundProgressBar(parent);
		case PROJECTINFORMATIONSTATISTICS:
			return new ProjectInformationStatistics(parent);
		default:
			return null;
		}
	}

	public static IChart createChart(ChartEnum chartEnum, Composite parent, int resourceNumber) {
		switch (chartEnum) {
		case RESOURCEALLOCATIONPLAN:
			return new ResourceAllocationPlan(parent, resourceNumber);
		case RESOURCEJOBPLAN:
			return new ResourceJobPlan(parent, resourceNumber);
		default:
			return null;
		}
	}
}
