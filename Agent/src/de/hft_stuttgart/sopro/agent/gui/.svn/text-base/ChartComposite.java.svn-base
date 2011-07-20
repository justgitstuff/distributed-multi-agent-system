package de.hft_stuttgart.sopro.agent.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hft_stuttgart.sopro.agent.gui.chart.IChart;

/**
 * Manages all {@link IChart} instances.
 * 
 * @author Matthias Huber - MatthewHuber@web.de
 */
public class ChartComposite {

	private List<IChart> charts = new ArrayList<IChart>();

	/**
	 * Adds an {@link IChart} to the container.
	 * 
	 * @param view
	 *            The chart to add.
	 */
	public void addChart(IChart chart) {
		if (!charts.contains(chart)) {
			charts.add(chart);
		}
	}

	/**
	 * Returns the list of {@link IChart} instances.
	 * 
	 * @return The list of {@link IChart} instances.
	 */
	public List<IChart> getCharts() {
		return Collections.unmodifiableList(charts);
	}

	/**
	 * Updates all {@link IChart} instances.
	 */
	public void update() {
		// all charts need to compute their new values for the next round
		for (IChart chart : charts) {
			// just delegate to all views
			chart.computeNextRound();
		}

		// repainting of all charts
		for (IChart chart : charts) {
			// just delegate to all views
			chart.update();
		}
	}
}
