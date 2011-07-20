package de.hft_stuttgart.sopro.agent.gui.chart;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.IAxisSet;
import org.swtchart.ILineSeries;
import org.swtchart.ISeriesSet;
import org.swtchart.Range;
import org.swtchart.ILineSeries.PlotSymbolType;
import org.swtchart.ISeries.SeriesType;

import de.hft_stuttgart.sopro.agent.gui.AmpManager;
import de.hft_stuttgart.sopro.agent.gui.NegotiationSession;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.IProject;

/**
 * This class shows the allocation of the two agents. Every Agent's allocation
 * is visualized in his own color. This Chart uses SWTChart, more precisely the
 * ILineSeries with enabled area drawing and enabled stacking of LineSeries.
 * 
 * @author Matthias Huber - MatthewHuber@web.de
 */
public class ResourceAllocationPlan implements IChart {

	/**
	 * The composite which holds the chart.
	 */
	private Composite parent;

	/**
	 * The chart which shows the resource allocation.
	 */
	private Chart chart;

	/**
	 * The max resource allocation.
	 */
	private int resourceLimit;

	/**
	 * The jobs of the project.
	 */
	private List<IJob> jobs;

	/**
	 * The resource number.
	 */
	private int resourceNumber;

	/**
	 * The payments for the jobs.
	 */
	private List<Double> paymentData;

	/**
	 * The latest start time of the job. As the last job is the end dummy job
	 * with duration of 0, it's the end of the project (project length).
	 */
	private Integer latestStartTime = 10;

	/**
	 * The indices of the jobs performed by this agent. They pointing to the
	 * indices of 'List<IJob> jobs'.
	 */
	private List<Integer> indicesOfPerformedJobsThisAgent;

	/**
	 * The indices of the jobs performed by the other agent. They pointing to
	 * the indices of 'List<IJob> jobs'.
	 */
	private List<Integer> indicesOfPerformedJobsOtherAgent;

	/**
	 * The allocation values fo this Agent.
	 */
	private double[] allocationValuesThisAgent;

	/**
	 * The combined allocation values of the two agents.
	 */
	private double[] combinedValuesOfBothAgents;

	public ResourceAllocationPlan(Composite parent, int resourceNumber) {
		this.parent = parent;
		this.resourceNumber = resourceNumber;
		NegotiationSession negotiationSession = AmpManager.getNegotiationSession();
		IProject project = negotiationSession.getProject();
		int[] capacities = project.getMaxCapacities();
		this.resourceLimit = capacities[resourceNumber];
		this.jobs = project.getJobs();
		this.paymentData = negotiationSession.getPaymentData();
	}

	public void create() {
		// create a chart
		chart = new Chart(parent, SWT.NONE);

		// set the legend position to the bottom of the chart
		chart.getLegend().setPosition(SWT.BOTTOM);

		// set titles
		chart.getTitle().setText("Capacity Plan for Resource");
		IAxisSet axisSet = chart.getAxisSet();

		// set xAxis
		IAxis xAxis = axisSet.getXAxis(0);
		xAxis.getTitle().setText("Duration");
		xAxis.setRange(new Range(0.0, latestStartTime));

		// set yAxis;
		IAxis yAxis = axisSet.getYAxis(0);
		yAxis.getTitle().setText("Hours");
		yAxis.setRange(new Range(0.0, resourceLimit + 1.0));

		// draw resource limit line
		drawResourceLimit();
	}

	/**
	 * Draw a horizontal line which defines the max resource allocation
	 */
	private void drawResourceLimit() {
		ILineSeries maxResourceAllovation = (ILineSeries) chart.getSeriesSet().createSeries(SeriesType.LINE, "Max");
		maxResourceAllovation.setXSeries(new double[] { 0.0, latestStartTime + 1.0 });
		maxResourceAllovation.setYSeries(new double[] { resourceLimit, resourceLimit });
		maxResourceAllovation.setSymbolType(PlotSymbolType.NONE);
		maxResourceAllovation.setLineColor(new Color(parent.getDisplay(), 255, 0, 0));
	}

	@Override
	public void computeNextRound() {

		List<Integer> startTimes = AmpManager.getNegotiationSession().getStartTimesForResource(resourceNumber);
		// get latest start time => the start time of the end job
		latestStartTime = startTimes.get(startTimes.size() - 1);

		// store the indices of the performed jobs. With the help of the
		// indices
		// it's possible to retrieve the corresponding job to the start time
		// of
		// the job.
		if (null == indicesOfPerformedJobsThisAgent || null == indicesOfPerformedJobsOtherAgent) {
			indicesOfPerformedJobsThisAgent = new ArrayList<Integer>();
			indicesOfPerformedJobsOtherAgent = new ArrayList<Integer>();

			for (int i = 0; i < startTimes.size(); ++i) {
				if (startTimes.get(i) > -1) {
					Double payment = paymentData.get(i);
					// if true then it's this agents job
					if (payment != 0) {
						indicesOfPerformedJobsThisAgent.add(i);
					} else {
						indicesOfPerformedJobsOtherAgent.add(i);
					}
				}
			}
		}

		// this array contains the allocation values of the resource
		allocationValuesThisAgent = aggregateAllocationValues(indicesOfPerformedJobsThisAgent, startTimes);

		// IMPORTANT: this are only the aggregation values for the other
		// job.
		// For presentation its necessary to combine the values of the two
		// agents
		double[] allocationValuesOtherAgent = aggregateAllocationValues(indicesOfPerformedJobsOtherAgent, startTimes);

		combinedValuesOfBothAgents = new double[allocationValuesThisAgent.length];
		for (int i = 0; i < combinedValuesOfBothAgents.length; ++i) {
			combinedValuesOfBothAgents[i] = allocationValuesThisAgent[i] + allocationValuesOtherAgent[i];
		}
	}

	public void update() {
		// TODO MH: only draw when visible => a listener must be implemented
		// when TabItem is selected
		// only redraw if chart is visible
		// if (parent.isVisible()) {

		// draw the two areas
		ISeriesSet seriesSet = chart.getSeriesSet();
		drawAllocationArea(combinedValuesOfBothAgents, seriesSet, false, "Other Agent's allocation");
		drawAllocationArea(allocationValuesThisAgent, seriesSet, true, "Our allocation");

		// draw resource limit line
		drawResourceLimit();

		// only the X-axis is variable, Y-Axis is fix
		chart.getAxisSet().getXAxis(0).setRange(new Range(0.0, latestStartTime + 1.0));

		chart.redraw();
		// }
	}

	private double[] aggregateAllocationValues(List<Integer> performedJobs, List<Integer> startTimes) {
		double[] allocationValues = new double[latestStartTime + 1];

		for (Integer index : performedJobs) {
			IJob job = jobs.get(index);
			int startTime = startTimes.get(index);
			int duration = job.getDuration();
			int[] resources = job.getResources();
			int allocation = resources[resourceNumber];

			// insert values: just aggregate the values at each point of time
			for (int i = 0; i < duration; ++i) {
				allocationValues[startTime + i] = allocationValues[startTime + i] + allocation;
			}
		}

		return allocationValues;
	}

	private void drawAllocationArea(double[] allocationValues, ISeriesSet seriesSet, boolean thisAgent, String legendText) {
		// draw the allocations
		ILineSeries allocation = (ILineSeries) seriesSet.getSeries(legendText);
		if (null == allocation) {
			allocation = (ILineSeries) seriesSet.createSeries(SeriesType.LINE, legendText);
			allocation.setSymbolType(PlotSymbolType.NONE);
			allocation.enableArea(true);
			allocation.enableStack(true);
			if (!thisAgent) {
				allocation.setLineColor(new Color(parent.getDisplay(), 0, 255, 0));
			}
		}
		// set Series
		List<double[]> newSeries = generateSeries(allocationValues);
		allocation.setXSeries(newSeries.get(0));
		allocation.setYSeries(newSeries.get(1));
	}

	private List<double[]> generateSeries(double[] allocationValues) {
		List<double[]> series = new ArrayList<double[]>(2);

		// lists are used to store the chart points because the
		// number of points is not fix
		List<Double> xValues = new ArrayList<Double>();
		List<Double> yValues = new ArrayList<Double>();

		double oldY = 0;
		double x = 0;
		for (int i = 0; i < allocationValues.length; ++i) {
			double y = allocationValues[i];
			// true if oldY and y build a edge => _| or |_ ; therefore we add an
			// additional point
			if (oldY != y) {
				xValues.add(x);
				yValues.add(oldY);
			}
			xValues.add(x);
			yValues.add(y);

			++x;
			oldY = y;
		}

		// create the xSeries
		double[] xSeries = new double[xValues.size()];
		for (int i = 0; i < xSeries.length; ++i) {
			xSeries[i] = xValues.get(i);
		}

		// create the ySeries
		double[] ySeries = new double[yValues.size()];
		for (int i = 0; i < ySeries.length; ++i) {
			ySeries[i] = yValues.get(i);
		}

		series.add(xSeries);
		series.add(ySeries);

		return series;
	}

	@Override
	public void dispose() {
	}

}
