/*
 * $LastChangedRevision: 473 $ $LastChangedBy: annemarie $ $LastChangedDate:
 * 2009-11-02 17:52:59 +0100 (Mo, 02 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common
 * /voting/VotingAlgorithmFactory.java $ $Id: VotingAlgorithmFactory.java 52
 * 2009-11-02 16:52:59Z sandro $
 */
package de.hft_stuttgart.sopro.common.voting;

import java.util.HashMap;
import java.util.Map;

import de.hft_stuttgart.sopro.common.voting.algorithms.ApprovalVotingAlgorithm;
import de.hft_stuttgart.sopro.common.voting.algorithms.BordaVotingAlgorithm;
import de.hft_stuttgart.sopro.common.voting.algorithms.CopelandVotingAlgorithm;
import de.hft_stuttgart.sopro.common.voting.algorithms.IVotingAlgorithm;
import de.hft_stuttgart.sopro.common.voting.algorithms.PluralityVotingAlgorithm;
import de.hft_stuttgart.sopro.common.voting.algorithms.ScoringVotingAlgorithm;

/**
 * The factory class for selecting the right {@link IVotingAlgorithm}.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public final class VotingAlgorithmFactory {
	/**
	 * The map containing all {@link IVotingAlgorithm} classes.
	 */
	@SuppressWarnings("unchecked")
	private static Map<Class, VotingAlgorithmEnum> votingAlgorithmMap = new HashMap<Class, VotingAlgorithmEnum>();

	/**
	 * The private constructor.
	 */
	private VotingAlgorithmFactory() {
	}

	/**
	 * @param votingAlgorithmEnum
	 *            The enumeration to select from.
	 * @return An new instance of {@link IVotingAlgorithm} or null.
	 */
	public static IVotingAlgorithm createInstance(VotingAlgorithmEnum votingAlgorithmEnum) {
		switch (votingAlgorithmEnum) {
		case APPROVAL:
			if (!votingAlgorithmMap.containsKey(ApprovalVotingAlgorithm.class)) {
				votingAlgorithmMap.put(ApprovalVotingAlgorithm.class, VotingAlgorithmEnum.APPROVAL);
			}
			return new ApprovalVotingAlgorithm();
		case BORDA:
			if (!votingAlgorithmMap.containsKey(BordaVotingAlgorithm.class)) {
				votingAlgorithmMap.put(BordaVotingAlgorithm.class, VotingAlgorithmEnum.BORDA);
			}
			return new BordaVotingAlgorithm();
		case COPELAND:
			if (!votingAlgorithmMap.containsKey(CopelandVotingAlgorithm.class)) {
				votingAlgorithmMap.put(CopelandVotingAlgorithm.class, VotingAlgorithmEnum.COPELAND);
			}
			return new CopelandVotingAlgorithm();
		case PLURALITY:
			if (!votingAlgorithmMap.containsKey(PluralityVotingAlgorithm.class)) {
				votingAlgorithmMap.put(PluralityVotingAlgorithm.class, VotingAlgorithmEnum.PLURALITY);
			}
			return new PluralityVotingAlgorithm();
		case SCORING:
			if (!votingAlgorithmMap.containsKey(ScoringVotingAlgorithm.class)) {
				votingAlgorithmMap.put(ScoringVotingAlgorithm.class, VotingAlgorithmEnum.SCORING);
			}
			return new ScoringVotingAlgorithm();
		default:
			return null;
		}
	}

	/**
	 * The method returns the enum depending on the VotingAlgorithm object
	 * 
	 * @param votingAlgorithm
	 *            the voting algorithm object
	 * @return the enum of the given voting algorithm object.
	 */
	public static VotingAlgorithmEnum getEnumOfVotingAlgorithm(IVotingAlgorithm votingAlgorithm) {
		return votingAlgorithmMap.get(votingAlgorithm.getClass());
	}
}
