package de.hft_stuttgart.sopro.agent.converter;

import java.util.ArrayList;
import java.util.Collections;
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
	 * Converts a double array to a unmodifiable list of {@link Double} values.
	 * 
	 * @param source
	 *            The array to convert.
	 * @return An unmodifiable list of {@link Double} values.
	 */
	@SuppressWarnings("unchecked")
	public List<Double> fromArrayToList(double[] source) {
		List<Double> target = Collections.EMPTY_LIST;
		if (null != source) {
			target = new ArrayList<Double>(source.length);
			for (int i = 0; i < source.length; i++) {
				target.add(new Double(source[i]));
			}
		}
		return Collections.unmodifiableList(target);
	}
}
