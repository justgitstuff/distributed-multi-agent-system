package de.hft_stuttgart.sopro.agent.gui.chart;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.IAxisSet;
import org.swtchart.ILineSeries;
import org.swtchart.ISeriesSet;
import org.swtchart.LineStyle;
import org.swtchart.Range;
import org.swtchart.ILineSeries.PlotSymbolType;
import org.swtchart.ISeries.SeriesType;

import de.hft_stuttgart.sopro.agent.gui.AmpManager;
import de.hft_stuttgart.sopro.agent.gui.NegotiationSession;

/**
 * This class generate a line chart which shows the total cash values over time
 * in the form of a line chart. The horizontal line displays the highest total
 * cash value.
 * 
 * @author Matthias Huber - MatthewHuber@web.de
 */
public class TotalCashValueLineChart implements IChart {

	/**
	 * The composite which holds all widgets of the view.
	 */
	private Composite parent;

	/**
	 * The line chart.
	 */
	private Chart chart;

	/**
	 * This is the variance which will be multiplied to the range of the YAxis
	 * to the lowest y value. This value must be lower than 1. E.g value 0.8
	 * means that there will be a variance of 20%. Value example lowest y value
	 * = 1000 and minVariance = 0.8 => 1000 * 0.8 = 800;
	 */
	private static final double MIN_VARIANCE = 0.99;

	/**
	 * This is the variance which will be multiplied to the range of the YAxis
	 * to the highest y value. This value must be greater than 1. E.g value 1.2
	 * means that there will be a variance of 20%. Value example highest y value
	 * = 1000 and minVariance = 1.2 => 1000 * 1.2 = 1200;
	 */
	private static final double MAX_VARIANCE = 1.01;

	/**
	 * This defines the amount of rounds which will be displayed in the chart.
	 */
	private static final double AMOUNT_VISUALIZED_ROUNDS = 50.0;

	private double negotiationRounds = 50.0;

	public TotalCashValueLineChart(Composite parent) {
		this.parent = parent;
		negotiationRounds = AmpManager.getNegotiationSession().getNegotiationRounds();
	}

	public void create() {
		chart = new Chart(parent, SWT.NONE);

		// set titles
		chart.getTitle().setText("Total Cash Value");
		chart.getAxisSet().getXAxis(0).getTitle().setText("Rounds");
		chart.getAxisSet().getYAxis(0).getTitle().setText("EUR");

		// set the legend position to the bottom of the chart
		chart.getLegend().setPosition(SWT.BOTTOM);

		// add a mouse listener for zooming in and out
		chart.getPlotArea().addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if (e.button == 1) {
					double pointX = chart.getAxisSet().getXAxis(0).getDataCoordinate(e.x);
					double pointY = chart.getAxisSet().getYAxis(0).getDataCoordinate(e.y);
					chart.getAxisSet().getXAxis(0).zoomIn(pointX);
					chart.getAxisSet().getYAxis(0).zoomIn(pointY);
				} else if (e.button == 3) {
					double pointX = chart.getAxisSet().getXAxis(0).getDataCoordinate(e.x);
					double pointY = chart.getAxisSet().getYAxis(0).getDataCoordinate(e.y);
					chart.getAxisSet().getXAxis(0).zoomOut(pointX);
					chart.getAxisSet().getYAxis(0).zoomOut(pointY);
				} else {
					chart.getAxisSet().adjustRange();
				}
				chart.redraw();
			}

		});

		IAxisSet axisSet = chart.getAxisSet();
		Range xAxisRange;
		if (negotiationRounds < AMOUNT_VISUALIZED_ROUNDS) {	
			xAxisRange = new Range(0.0, negotiationRounds + 1.0);
		} else {
			xAxisRange = new Range(0.0, AMOUNT_VISUALIZED_ROUNDS + 1.0);			
		}
		IAxis xAxis = axisSet.getXAxis(0);
		xAxis.setRange(xAxisRange);

		Range yAxisRange = new Range(0.0, 1000.0);
		IAxis yAxis = axisSet.getYAxis(0);
		yAxis.setRange(yAxisRange);
	}

	@Override
	public void computeNextRound() {
		// get all necessary information which are needed for drawing the chart
		NegotiationSession negotiationSession = AmpManager.getNegotiationSession();
		List<Double> totalCashValuesAgent = new ArrayList<Double>(negotiationSession.getTotalCashValuesAgent());
		List<Double> totalCashValuesMediator = new ArrayList<Double>(negotiationSession.getTotalCashValuesMediator());
		double minimumTotalCashValueAgent = negotiationSession.getCurrentMinimumTotalCashValueAgent();
		double minimumTotalCashValueMediator = negotiationSession.getCurrentMinimumTotalCashValueMediator();
		double maximumTotalCashValueAgent = negotiationSession.getCurrentMaximumTotalCashValueAgent();
		double maximumTotalCashValueMediator = negotiationSession.getCurrentMaximumTotalCashValueMediator();
		int round = negotiationSession.getRound();

		int amountOfValues = totalCashValuesAgent.size();
		// create the array for the lineSeries
		double[] xSeriesValues = new double[amountOfValues];
		double[] ySeriesValuesAgent = new double[amountOfValues];
		double[] ySeriesValuesMediator = new double[amountOfValues];
		for (int i = 0; i < amountOfValues; i++) {
			ySeriesValuesAgent[i] = totalCashValuesAgent.get(i);
			ySeriesValuesMediator[i] = totalCashValuesMediator.get(i);
			// xValues are starting with 1 until the amountOfValues
			xSeriesValues[i] = i + 1;
		}

		// get the series set
		ISeriesSet seriesSet = chart.getSeriesSet();
		// create the line series if their not available and set the values
		// containing the total cash values of
		// the agent
		ILineSeries totalCashValuesLineSeriesAgent = (ILineSeries) seriesSet.getSeries("Agent");
		if (null == totalCashValuesLineSeriesAgent) {
			totalCashValuesLineSeriesAgent = (ILineSeries) seriesSet.createSeries(SeriesType.LINE, "Agent");
			totalCashValuesLineSeriesAgent.setSymbolSize(1);
			totalCashValuesLineSeriesAgent.setAntialias(SWT.ON);
		}
		totalCashValuesLineSeriesAgent.setXSeries(xSeriesValues);
		totalCashValuesLineSeriesAgent.setYSeries(ySeriesValuesAgent);

		ILineSeries totalCashValuesLineSeriesMediator = (ILineSeries) seriesSet.getSeries("Mediator");
		if (null == totalCashValuesLineSeriesMediator) {
			totalCashValuesLineSeriesMediator = (ILineSeries) seriesSet.createSeries(SeriesType.LINE, "Mediator");
			totalCashValuesLineSeriesMediator.setSymbolSize(1);
			totalCashValuesLineSeriesMediator.setAntialias(SWT.ON);
			totalCashValuesLineSeriesMediator.setLineColor(new Color(parent.getDisplay(), 0, 255, 0));
		}
		totalCashValuesLineSeriesMediator.setXSeries(xSeriesValues);
		totalCashValuesLineSeriesMediator.setYSeries(ySeriesValuesMediator);

		// create and set the line series containing the horizontal line
		// which visualize the highest total cash value
		ILineSeries highestTotalCashValueLine = (ILineSeries) seriesSet.getSeries("Highest Total Cash Value");
		if (null == highestTotalCashValueLine) {
			highestTotalCashValueLine = (ILineSeries) seriesSet.createSeries(SeriesType.LINE, "Highest Total Cash Value");			
			highestTotalCashValueLine.setSymbolType(PlotSymbolType.NONE);
			highestTotalCashValueLine.setLineColor(new Color(parent.getDisplay(), 255, 0, 0));
			highestTotalCashValueLine.setLineStyle(LineStyle.DASH);
			highestTotalCashValueLine.setAntialias(SWT.ON);
		}
		highestTotalCashValueLine.setXSeries(new double[] { 0.0, round + AMOUNT_VISUALIZED_ROUNDS + 1.0 });
		highestTotalCashValueLine.setYSeries(new double[] { maximumTotalCashValueMediator, maximumTotalCashValueMediator });

		/* Update the chart */
		IAxisSet axisSet = chart.getAxisSet();
		// set the ranges of the axis
		Range xAxisRange = null;

		if (negotiationRounds <= AMOUNT_VISUALIZED_ROUNDS) {
			xAxisRange = new Range(0.0, negotiationRounds + 1.0);
		} else {
			if (round < AMOUNT_VISUALIZED_ROUNDS) {
				xAxisRange = new Range(0.0, AMOUNT_VISUALIZED_ROUNDS + 1.0);
			} else {
				if (!negotiationSession.getIsFinished()) {
					xAxisRange = new Range(round - AMOUNT_VISUALIZED_ROUNDS + 1.0, round + 1.0);
				} else {
					xAxisRange = new Range(0.0, round + 1.0);
				}
			}
		}

		IAxis xAxis = axisSet.getXAxis(0);
		xAxis.setRange(xAxisRange);

		// check which is the highest Total Cash Value (Agent vs. Mediator)
		double lowestYValue = minimumTotalCashValueMediator > minimumTotalCashValueAgent ? minimumTotalCashValueAgent : minimumTotalCashValueMediator;
		double highestYValue = maximumTotalCashValueMediator > maximumTotalCashValueAgent ? maximumTotalCashValueMediator : maximumTotalCashValueAgent;

		IAxis yAxis = axisSet.getYAxis(0);
		yAxis.setRange(new Range(lowestYValue * MIN_VARIANCE, highestYValue * MAX_VARIANCE));

	}

	public void update() {
		chart.redraw();
	}

	@Override
	public void dispose() {
	}

}
