package de.hft_stuttgart.sopro.common.negotiationValues;

/**
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 */
public enum ProposalsPerRoundValues {
	PROPOSALS_PER_ROUND_10(10), PROPOSALS_PER_ROUND_25(25), PROPOSALS_PER_ROUND_50(50), PROPOSALS_PER_ROUND_100(100), PROPOSALS_PER_ROUND_500(500);

	private int value;

	private ProposalsPerRoundValues(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
