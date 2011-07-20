package de.hft_stuttgart.sopro.mediator.converter;

import java.util.List;

/**
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class DoubleConverter {

	/**
	 * Default constructor taking no arguments.
	 */
	public DoubleConverter() {
	}

	/**
	 * Converts from a list of {@link Double} values to an array of double.
	 * 
	 * @param source
	 *            The list which should be converted.
	 * @return A double array containing all values or null if source list was
	 *         empty.
	 */
	public double[] fromListToArray(List<Double> source) {
		double[] target = null;
		if (null != source && !source.isEmpty()) {
			target = new double[source.size()];
			for (int i = 0; i < source.size(); i++) {
				target[i] = source.get(i).doubleValue();
			}
		}
		return target;
	}
}
