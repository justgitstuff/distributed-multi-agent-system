package org.swtchart.internal.series;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.swtchart.Chart;
import org.swtchart.IJobSeries;
import org.swtchart.LineStyle;
import org.swtchart.Range;
import org.swtchart.IAxis.Direction;
import org.swtchart.internal.Util;
import org.swtchart.internal.axis.Axis;
import org.swtchart.internal.compress.CompressLineSeries;
import org.swtchart.internal.compress.CompressScatterSeries;

/**
 * {@see IJobSeries}
 * 
 * @author Matthias Huber (MatthewHuber@web.de)
 */
public class JobSeries extends Series implements IJobSeries {

	/** the line style */
	private LineStyle lineStyle;

	/** the line color */
	private Color lineColor;

	/** the line width */
	private int lineWidth;

	/** the state indicating if area chart is enabled */
	private boolean areaEnabled;

	/** the anti-aliasing value for drawing line */
	private int antialias;

	/** the alpha value to draw area */
	private static final int ALPHA = 75;

	/** the default line style */
	private static final LineStyle DEFAULT_LINE_STYLE = LineStyle.SOLID;

	/** the default line width */
	private static final int DEFAULT_LINE_WIDTH = 1;

	/** the default line color */
	private static final int DEFAULT_LINE_COLOR = SWT.COLOR_BLUE;

	/** the default anti-aliasing value */
	private static final int DEFAULT_ANTIALIAS = SWT.DEFAULT;

	/** the margin in pixels attached at the minimum/maximum plot */
	private static final int MARGIN_AT_MIN_MAX_PLOT = 6;

	/** the list contains the texts for the job labels */
	private List<String> labelTexts;

	/**
	 * Constructor.
	 * 
	 * @param chart
	 *            the chart
	 * @param id
	 *            the series id
	 */
	protected JobSeries(Chart chart, String id) {
		super(chart, id);

		lineStyle = DEFAULT_LINE_STYLE;
		lineColor = Display.getDefault().getSystemColor(DEFAULT_LINE_COLOR);

		areaEnabled = true;

		antialias = DEFAULT_ANTIALIAS;
		lineWidth = DEFAULT_LINE_WIDTH;

		compressor = new CompressLineSeries();
	}

	/**
	 * @see IJobSeries#getLineStyle()
	 */
	public LineStyle getLineStyle() {
		return lineStyle;
	}

	/**
	 * @see IJobSeries#getLineColor()
	 */
	public Color getLineColor() {
		if (lineColor.isDisposed()) {
			lineColor = Display.getDefault().getSystemColor(DEFAULT_LINE_COLOR);
		}
		return lineColor;
	}

	/**
	 * @see IJobSeries#setLineColor(Color)
	 */
	public void setLineColor(Color color) {
		if (color != null && color.isDisposed()) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}

		if (color == null) {
			this.lineColor = Display.getDefault().getSystemColor(DEFAULT_LINE_COLOR);
		} else {
			this.lineColor = color;
		}
	}

	/**
	 * @see IJobSeries#getLineWidth()
	 */
	public int getLineWidth() {
		return lineWidth;
	}

	/**
	 * @see IJobSeries#setLineWidth(int)
	 */
	public void setLineWidth(int width) {
		if (width <= 0) {
			this.lineWidth = DEFAULT_LINE_WIDTH;
		} else {
			this.lineWidth = width;
		}
	}

	/**
	 * @see Series#setCompressor()
	 */
	@Override
	protected void setCompressor() {
		if (isXMonotoneIncreasing) {
			compressor = new CompressLineSeries();
		} else {
			compressor = new CompressScatterSeries();
			((CompressScatterSeries) compressor).setLineVisible(getLineStyle() != LineStyle.NONE);
		}
	}

	/**
	 * @see IJobSeries#enableArea(boolean)
	 */
	public void enableArea(boolean enabled) {
		areaEnabled = enabled;
	}

	/**
	 * @see IJobSeries#isAreaEnabled()
	 */
	public boolean isAreaEnabled() {
		return areaEnabled;
	}

	/**
	 * @see IJobSeries#getAntialias()
	 */
	public int getAntialias() {
		return antialias;
	}

	/**
	 * @see IJobSeries#setAntialias(int)
	 */
	public void setAntialias(int antialias) {
		if (antialias != SWT.DEFAULT && antialias != SWT.ON && antialias != SWT.OFF) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		this.antialias = antialias;
	}

	public void setLabelTexts(List<String> labelTexts) {
		this.labelTexts = new ArrayList<String>(labelTexts);
	}

	/**
	 * Gets the line points to draw line and area.
	 * 
	 * @param xseries
	 *            the horizontal series
	 * @param yseries
	 *            the vertical series
	 * @param index
	 *            the index of series
	 * @param xAxis
	 *            the X axis
	 * @param yAxis
	 *            the Y axis
	 * @return the line points
	 */
	private int[] getLineAndAreaPoints(double[] xseries, double[] yseries, int index, Axis xAxis, Axis yAxis) {

		int x1 = xAxis.getPixelCoordinate(xseries[index]);
		int x2 = xAxis.getPixelCoordinate(xseries[index + 1]);
		int x3 = x2;
		int x4 = x1;
		int y1 = yAxis.getPixelCoordinate(yseries[index]);
		int y2 = yAxis.getPixelCoordinate(yseries[index + 1]);
		int y3 = yAxis.getPixelCoordinate(yseries[index + 2]);
		int y4 = y3;

		return new int[] { x1, y1, x2, y2, x3, y3, x4, y4 };
	}

	private int[] getLinePoints(double[] xseries, double[] yseries, int index, Axis xAxis, Axis yAxis) {
		int x1 = xAxis.getPixelCoordinate(xseries[index]);
		int x2 = xAxis.getPixelCoordinate(xseries[index + 1]);
		int y1 = yAxis.getPixelCoordinate(yseries[index]);
		int y2 = yAxis.getPixelCoordinate(yseries[index + 1]);

		return new int[] { x1, y1, x2, y2 };
	}

	/**
	 * @see Series#draw(GC, int, int, Axis, Axis)
	 */
	@Override
	protected void draw(GC gc, int width, int height, Axis xAxis, Axis yAxis) {
		int oldAntialias = gc.getAntialias();
		int oldLineWidth = gc.getLineWidth();
		gc.setAntialias(antialias);
		gc.setLineWidth(lineWidth);

		if (lineStyle != LineStyle.NONE) {
			drawJobs(gc, xAxis, yAxis);
		}

		// if (getLabel().isVisible()) {
		// drawLabel(gc, xAxis, yAxis);
		// }

		gc.setAntialias(oldAntialias);
		gc.setLineWidth(oldLineWidth);
	}

	/**
	 * Draws the jobs.
	 * 
	 * @param gc
	 *            the graphics context
	 * @param xAxis
	 *            the x axis
	 * @param yAxis
	 *            the y axis
	 */
	private void drawJobs(GC gc, Axis xAxis, Axis yAxis) {

		// get x and y series
		double[] xSeries = compressor.getCompressedXSeries();
		double[] ySeries = compressor.getCompressedYSeries();
		if (xSeries.length == 0 || ySeries.length == 0) {
			return;
		}
		int[] indexes = compressor.getCompressedIndexes();
		if (xAxis.isValidCategoryAxis()) {
			for (int i = 0; i < xSeries.length; i++) {
				xSeries[i] = indexes[i];
			}
		}

		gc.setLineStyle(Util.getIndexDefinedInSWT(lineStyle));

		int counter = 0;
		// draw lines and area (without last line)
		for (int i = 0; i < xSeries.length - 1; ++i) {
			gc.setForeground(getLineColor());
			if (0 != (i + 1) % 5) {
				// draw line and area for the first point of the job, otherwise
				// only the line
				if (0 == i % 5) {
					// draw line
					int[] p = getLineAndAreaPoints(xSeries, ySeries, i, xAxis, yAxis);
					gc.drawLine(p[0], p[1], p[2], p[3]);
					if (areaEnabled) {
						drawArea(gc, p);
					}
					// draw label
					if (getLabel().isVisible()) {
						int h = (xAxis.getPixelCoordinate(xSeries[i]) + xAxis.getPixelCoordinate(xSeries[i + 1])) / 2;
						int v = (yAxis.getPixelCoordinate(ySeries[i]) + yAxis.getPixelCoordinate(ySeries[i + 3])) / 2;
						seriesLabel.setLabelText(labelTexts.get(i / 5));
						seriesLabel.draw(gc, h, v, ySeries[i], indexes[i], SWT.CENTER);
					}
				} else {
					int[] p = getLinePoints(xSeries, ySeries, i, xAxis, yAxis);
					gc.drawLine(p[0], p[1], p[2], p[3]);
				}
			}
			++counter;
		}
	}

	/**
	 * Draws the area.
	 * 
	 * @param gc
	 *            the graphic context
	 * @param p
	 *            the line points
	 */
	private void drawArea(GC gc, int[] p) {
		int alpha = gc.getAlpha();
		gc.setAlpha(ALPHA);
		gc.setBackground(getLineColor());

		int[] pointArray = new int[] { p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7], p[0], p[1] };

		gc.fillPolygon(pointArray);
		gc.setAlpha(alpha);
	}

	/**
	 * @see Series#getAdjustedRange(Axis, int)
	 */
	@Override
	public Range getAdjustedRange(Axis axis, int length) {

		Range range;
		if (axis.getDirection() == Direction.X) {
			range = getXRange();
		} else {
			range = getYRange();
		}

		int lowerPlotMargin = MARGIN_AT_MIN_MAX_PLOT;
		int upperPlotMargin = MARGIN_AT_MIN_MAX_PLOT;

		return getRangeWithMargin(lowerPlotMargin, upperPlotMargin, length, axis, range);
	}

}
