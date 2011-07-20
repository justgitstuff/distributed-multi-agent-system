package org.swtchart;

import java.util.List;

import org.eclipse.swt.graphics.Color;

/**
 * Creates a Job Series. The JobSeries is a modified version of the LineSeries.
 * The Series needs five double values. The first four values are the edge points
 * of the rectangle. The fifth value is the same as the first one. Without this
 * last value it's not possible to draw every edge of the rectangle.
 * 
 * @author Matthias Huber (MatthewHuber@web.de)
 */
public interface IJobSeries extends ISeries {

	/**
	 * Sets the label texts of the jobs.
	 * 
	 * @param labelTexts The labels for the jobs.
	 */
	void setLabelTexts(List<String> labelTexts);
	
	/**
	 * Gets line style.
	 * 
	 * @return line style.
	 */
	LineStyle getLineStyle();

	/**
	 * Gets the line color.
	 * 
	 * @return the line color
	 */
	Color getLineColor();

	/**
	 * Sets line color. If null is given, default color will be set.
	 * 
	 * @param color
	 *            the line color
	 */
	void setLineColor(Color color);

	/**
	 * Gets the line width.
	 * 
	 * @return the line width
	 */
	int getLineWidth();

	/**
	 * Sets the width of line connecting data points and also line drawing
	 * symbol if applicable (i.e. <tt>PlotSymbolType.CROSS</tt> or
	 * <tt>PlotSymbolType.PLUS</tt>). The default width is 1.
	 * 
	 * @param width
	 *            the line width
	 */
	void setLineWidth(int width);

	/**
	 * Enables the area chart.
	 * 
	 * @param enabled
	 *            true if enabling area chart
	 */
	void enableArea(boolean enabled);

	/**
	 * Gets the state indicating if area chart is enabled.
	 * 
	 * @return true if area chart is enabled
	 */
	boolean isAreaEnabled();

	/**
	 * Gets the anti-aliasing value for drawing line. The default value is
	 * <tt>SWT.DEFAULT<tt>.
	 * 
	 * @return the anti-aliasing value which can be <tt>SWT.DEFAULT</tt>,
	 *         <tt>SWT.ON</tt> or <tt>SWT.OFF</tt>.
	 */
	int getAntialias();

	/**
	 * Sets the anti-aliasing value for drawing line.
	 * <p>
	 * If number of data points is too large, the series is drawn as a
	 * collection of dots rather than lines. In this case, the anti-alias
	 * doesn't really make effect, and just causes performance degradation.
	 * Therefore, client code may automatically enable/disable the anti-alias
	 * for each series depending on the number of data points, or alternatively
	 * may let end-user configure it.
	 * 
	 * @param antialias
	 *            the anti-aliasing value which can be <tt>SWT.DEFAULT</tt>,
	 *            <tt>SWT.ON</tt> or <tt>SWT.OFF</tt>.
	 */
	void setAntialias(int antialias);
}
