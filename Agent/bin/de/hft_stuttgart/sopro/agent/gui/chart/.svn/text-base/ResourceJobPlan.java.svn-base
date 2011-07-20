package de.hft_stuttgart.sopro.agent.gui.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.IAxisSet;
import org.swtchart.IJobSeries;
import org.swtchart.ISeriesSet;
import org.swtchart.Range;
import org.swtchart.ISeries.SeriesType;

import de.hft_stuttgart.sopro.agent.gui.AmpManager;
import de.hft_stuttgart.sopro.agent.gui.NegotiationSession;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.IProject;

/**
 * This class uses the light weight chart component SWTChart (www.swtchart.org).
 * SWTChart was extended because with the available chart it wasn't possible to
 * create the desired chart. The chart shows all jobs of the project in time
 * flow. Jobs belonging to the same agent are visualized with the same color.
 * 
 * @author Matthias Huber - MatthewHuber@web.de
 */
// TODO MH: if time is available then rework the implementation to get a better
// performance
public class ResourceJobPlan implements IChart {

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
	 * The resource number.
	 */
	private int resourceNumber;

	/**
	 * This array defines the drawing area. It is used to compute the position
	 * of a job within the drawing area. True defines a place in the area which
	 * is already used by a job.
	 */
	private boolean[][] chartArea;

	/**
	 * This Hashmap stores the rectangle points which are needed for drawing for
	 * every job performed by the resource.
	 */
	private Map<IJob, List<Integer>> drawMap = new HashMap<IJob, List<Integer>>();

	/**
	 * The start time of the latest job. Due to the fact that the last job has
	 * always the duration value 0, it is also the project length. Therefore it
	 * is used to define the length of the x axis.
	 */
	private Integer latestStartTime = 10;

	/**
	 * Contains the jobs of a project.
	 */
	private List<IJob> jobs;

	private List<Double> paymentData;

	private int highestYValue;

	/**
	 * Creates a chart showing the jobs of a project in time flow.
	 * 
	 * @param parent
	 *            The composite which will hold this chart.
	 * @param resourceNumber
	 *            The number of the resource.
	 */
	public ResourceJobPlan(Composite parent, int resourceNumber) {
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
		yAxis.setRange(new Range(0.0, resourceLimit * 1.5));
		// yAxis.setRange(new Range(0.0, resourceLimit + 1.0));

	}

	@Override
	public void computeNextRound() {

		List<Integer> startTimes = AmpManager.getNegotiationSession().getStartTimesForResource(resourceNumber);
		// get latest start time => the start time of the end job
		latestStartTime = startTimes.get(startTimes.size() - 1);

		// the y axis (first array) is set to the double size. This is
		// necessary
		// because currently it's not possible to draw an exact chart that
		// only
		// uses the place from y = 0 to y = maxCapacities
		chartArea = new boolean[resourceLimit * 2][latestStartTime];

		// beginning from the start
		int startTime = 0;
		highestYValue = 0;
		// TODO MH: rework bad for loop
		while (startTime < latestStartTime) {

			Map<IJob, Boolean> sameStartTimes = new HashMap<IJob, Boolean>();
			int highestDuration = 0;
			for (int position = 0; position < startTimes.size(); ++position) {
				if (startTime == startTimes.get(position)) {
					IJob job = jobs.get(position);
					// check to which agent the job belongs to
					boolean jobOfThisAgent = paymentData.get(position) != 0;
					// add to the list
					sameStartTimes.put(job, jobOfThisAgent);
					// update highest duration time
					if (job.getDuration() > highestDuration) {
						highestDuration = job.getDuration();
					}
				}
			}

			int yValue = drawChecker(sameStartTimes, startTime, highestDuration);

			if (yValue > highestYValue) {
				highestYValue = yValue;
			}

			++startTime;
		}

	}

	public void update() {
		// TODO only draw when visible => a listener must be implemented
		// when TabItem is selected
		// only redraw if chart is visible
		// if (parent.isVisible()) {

		drawJobs();

		IAxisSet axisSet = chart.getAxisSet();
		axisSet.getXAxis(0).setRange(new Range(0.0, latestStartTime + 1.0));
		axisSet.getYAxis(0).setRange(new Range(0.0, highestYValue + 1.0));

		chart.redraw();
		// }
	}

	private int drawChecker(Map<IJob, Boolean> jobs, int startTime, int highestDuration) {
		int highestYValue = 0;
		// check if drawing possible, necessary values are duration
		// of job and resource allocation
		// TODO MH: refactore to improve performance (bad for loop)
		int duration = highestDuration;
		while (duration > 0) {
			Set<IJob> jobSet = jobs.keySet();
			for (IJob job : jobSet) {
				if (duration == job.getDuration()) {
					int durationOfJob = job.getDuration();
					int[] resources = job.getResources();
					int allocation = resources[resourceNumber];

					// store the starting y value and the resource allocation
					// which will be used for the drawing
					int startYValue = calculateYValue(durationOfJob, allocation, startTime);

					// check if new value is the highest one
					int endYValue = startYValue + allocation;
					if (endYValue > highestYValue) {
						highestYValue = endYValue;
					}

					List<Integer> valuesForDrawing = new ArrayList<Integer>(4);
					valuesForDrawing.add(startTime);
					valuesForDrawing.add(startYValue);
					valuesForDrawing.add(allocation);
					valuesForDrawing.add(durationOfJob);

					// TODO MH: rework
					// currently bad solution: the decision to which agent a job
					// belongs to is stored here
					if (jobs.get(job)) {
						valuesForDrawing.add(1);
					} else {
						valuesForDrawing.add(0);
					}

					drawMap.put(job, valuesForDrawing);
				}
			}
			--duration;
		}

		return highestYValue;
	}

	private int calculateYValue(int durationOfJob, int allocation, int startTime) {
		int finalYPosition = 0;

		int checkedCounter = 0;
		int yPosition = 0;

		while (checkedCounter < allocation) {
			// it cannot be guaranteed that the yPosition is always in the index
			// range of the array
			try {
				if (chartArea[yPosition][startTime] == false) {
					++checkedCounter;
				} else {
					// reset checked counter
					checkedCounter = 0;
					finalYPosition = yPosition + 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			++yPosition;
		}

		// update the chartArea two dimensional array
		for (int i = 0; i < durationOfJob; ++i) {
			for (int j = 0; j < allocation; ++j) {
				chartArea[finalYPosition + j][startTime + i] = true;
			}
		}

		return finalYPosition;
	}

	private void drawJobs() {
		ISeriesSet seriesSet = chart.getSeriesSet();

		// draw the job
		Set<IJob> jobs = drawMap.keySet();

		List<List<double[]>> jobSeriesValuesThisAgent = new ArrayList<List<double[]>>();
		List<String> jobSeriesLabelTextThisAgent = new ArrayList<String>();
		List<List<double[]>> jobSeriesValuesOtherAgent = new ArrayList<List<double[]>>();
		List<String> jobSeriesLabelTextOtherAgent = new ArrayList<String>();

		for (IJob job : jobs) {
			List<Integer> values = drawMap.get(job);
			List<double[]> jobSeriesValues = generateLineSeries(values.get(0), values.get(1), values.get(2), values.get(3));
			if (values.get(4) == 1) {
				jobSeriesValuesThisAgent.add(jobSeriesValues);
				jobSeriesLabelTextThisAgent.add(Integer.toString(job.getJobNumber()));
			} else {
				jobSeriesValuesOtherAgent.add(jobSeriesValues);
				jobSeriesLabelTextOtherAgent.add(Integer.toString(job.getJobNumber()));
			}
		}

		// jobSeries of this agent
		double[] xSeriesThisAgent = new double[jobSeriesValuesThisAgent.size() * 5];
		double[] ySeriesThisAgent = new double[jobSeriesValuesThisAgent.size() * 5];
		int index = 0;
		for (List<double[]> jobSeriesValues : jobSeriesValuesThisAgent) {
			double[] xSeriesValues = jobSeriesValues.get(0);
			double[] ySeriesValues = jobSeriesValues.get(1);
			for (int i = 0; i < xSeriesValues.length; ++i) {
				xSeriesThisAgent[index] = xSeriesValues[i];
				ySeriesThisAgent[index] = ySeriesValues[i];
				++index;
			}
		}

		IJobSeries jobSeriesThisAgent = (IJobSeries) seriesSet.getSeries("Our jobs");
		if (null == jobSeriesThisAgent) {
			jobSeriesThisAgent = (IJobSeries) seriesSet.createSeries(SeriesType.JOB, "Our jobs");
			jobSeriesThisAgent.getLabel().setVisible(true);
			jobSeriesThisAgent.enableArea(true);
		}
		jobSeriesThisAgent.setXSeries(xSeriesThisAgent);
		jobSeriesThisAgent.setYSeries(ySeriesThisAgent);
		jobSeriesThisAgent.setLabelTexts(jobSeriesLabelTextThisAgent);

		// jobSeries of the other agent
		double[] xSeriesOtherAgent = new double[jobSeriesValuesOtherAgent.size() * 5];
		double[] ySeriesOtherAgent = new double[jobSeriesValuesOtherAgent.size() * 5];
		index = 0;
		for (List<double[]> jobSeriesValues : jobSeriesValuesOtherAgent) {
			double[] xSeriesValues = jobSeriesValues.get(0);
			double[] ySeriesValues = jobSeriesValues.get(1);
			for (int i = 0; i < xSeriesValues.length; ++i) {
				xSeriesOtherAgent[index] = xSeriesValues[i];
				ySeriesOtherAgent[index] = ySeriesValues[i];
				++index;
			}
		}

		IJobSeries jobSeriesOtherAgent = (IJobSeries) seriesSet.getSeries("Other agent's jobs");
		if (null == jobSeriesOtherAgent) {
			jobSeriesOtherAgent = (IJobSeries) seriesSet.createSeries(SeriesType.JOB, "Other agent's jobs");
			jobSeriesOtherAgent.getLabel().setVisible(true);
			jobSeriesOtherAgent.setLineColor(new Color(parent.getDisplay(), 0, 255, 0));
			jobSeriesOtherAgent.enableArea(true);
		}
		jobSeriesOtherAgent.setXSeries(xSeriesOtherAgent);
		jobSeriesOtherAgent.setYSeries(ySeriesOtherAgent);
		jobSeriesOtherAgent.setLabelTexts(jobSeriesLabelTextOtherAgent);
	}

	private List<double[]> generateLineSeries(double startTime, double startYValue, double allocation, double durationOfJob) {
		List<double[]> series = new ArrayList<double[]>(2);

		// xSeries
		double[] xSeries = new double[5];
		xSeries[0] = startTime;
		xSeries[1] = startTime + durationOfJob;
		xSeries[2] = startTime + durationOfJob;
		xSeries[3] = startTime;
		xSeries[4] = startTime;

		series.add(xSeries);

		// ySeries
		double[] ySeries = new double[5];
		ySeries[0] = startYValue + allocation;
		ySeries[1] = startYValue + allocation;
		ySeries[2] = startYValue;
		ySeries[3] = startYValue;
		ySeries[4] = startYValue + allocation;

		series.add(ySeries);

		return series;
	}

	public int getResourceNumber() {
		return resourceNumber;
	}

	@Override
	public void dispose() {
	}

}
