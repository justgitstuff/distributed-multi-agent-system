/*
 * $LastChangedRevision: 473 $ $LastChangedBy: annemarie $ $LastChangedDate:
 * 2009-11-02 17:52:59 +0100 (Mo, 02 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common
 * /voting/VotingAlgorithmFactory.java $ $Id: VotingAlgorithmFactory.java 52
 * 2009-11-02 16:52:59Z sandro $
 */
package de.hft_stuttgart.sopro.mediator.data.generator;

import java.util.HashMap;
import java.util.Map;

import de.hft_stuttgart.sopro.common.project.IProject;

/**
 * The factory class for selecting the right {@link ProposalGeneratorAbstract}.
 * 
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 */
public final class ProposalGeneratorFactory {
	/**
	 * The map containing all {@link ProposalGeneratorAbstract} classes.
	 */
	@SuppressWarnings("unchecked")
	private static Map<Class, ProposalGeneratorEnum> proposalGeneratorMap = new HashMap<Class, ProposalGeneratorEnum>();

	/**
	 * The private constructor.
	 */
	private ProposalGeneratorFactory() {
	}

	/**
	 * @param proposalGeneratorEnum
	 *            The enumeration to select from.
	 * @return An new instance of {@link ProposalGeneratorAbstract} or null.
	 */
	public static ProposalGeneratorAbstract createInstance(ProposalGeneratorEnum proposalGeneratorEnum, IProject project, int negotiationRounds, int proposalsPerRound) {
		switch (proposalGeneratorEnum) {
		case PRIMITIVE:
			if (!proposalGeneratorMap.containsKey(ProposalGeneratorPrimitive.class)) {
				proposalGeneratorMap.put(ProposalGeneratorPrimitive.class, ProposalGeneratorEnum.PRIMITIVE);
			}
			return new ProposalGeneratorPrimitive(project, negotiationRounds, proposalsPerRound);
		case ADVANCED_ADDITION:
			if (!proposalGeneratorMap.containsKey(ProposalGeneratorAdvancedAddition.class)) {
				proposalGeneratorMap.put(ProposalGeneratorAdvancedAddition.class, ProposalGeneratorEnum.ADVANCED_ADDITION);
			}
			return new ProposalGeneratorAdvancedAddition(project, negotiationRounds, proposalsPerRound);
		default:
			return null;
		}
	}
}
