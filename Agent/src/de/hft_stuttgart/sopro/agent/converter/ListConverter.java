package de.hft_stuttgart.sopro.agent.converter;

import java.util.List;

/**
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class ListConverter {

	/**
	 * Default constructor.
	 */
	public ListConverter() {
	}

	/**
	 * Converts an {@link Integer} list to an int array.
	 * 
	 * @param source
	 *            The list to convert.
	 * @return An array containing int values.
	 */
	public int[] fromIntegerListToIntArray(List<Integer> source) {
		int[] target = null;
		if (null != source && !source.isEmpty()) {
			target = new int[source.size()];
			for (int i = 0; i < source.size(); i++) {
				target[i] = source.get(i).intValue();
			}
		}

		return target;
	}

}
