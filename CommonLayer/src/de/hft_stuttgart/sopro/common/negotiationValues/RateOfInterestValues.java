package de.hft_stuttgart.sopro.common.negotiationValues;

/**
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 * 
 */

public enum RateOfInterestValues {
	RATE_OF_INTEREST_1(0.01), RATE_OF_INTEREST_2(0.01), RATE_OF_INTEREST_5(0.01);
	
	private double value;
	
	private RateOfInterestValues(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
}
