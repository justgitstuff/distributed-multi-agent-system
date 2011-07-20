package de.hft_stuttgart.sopro.mediator.converter;

import de.hft_stuttgart.sopro.common.exceptions.ProposalNotFoundException;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;
import de.hft_stuttgart.sopro.common.wrapper.ProposalCompositionWrapper;
import de.hft_stuttgart.sopro.common.wrapper.ProposalWrapper;

/**
 * @author Eduard Tudenhoefner - nastra@gmx.net
 * @author Sandro Degiorgi - sdegiorgi@gmail.com
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 */
public class ProposalCompositionConverter {

	/**
	 * Converts from a {@link ProposalComposition} to
	 * {@link ProposalCompositionWrapper}.
	 * 
	 * @param source
	 *            The {@link ProposalComposition} which should be converted.
	 * @return An instance of {@link ProposalCompositionWrapper}.
	 */
	public ProposalCompositionWrapper fromProposalCompositionToProposalCompositionWrapper(ProposalComposition source) {
		ProposalCompositionWrapper target = null;
		if (null != source) {
			target = new ProposalCompositionWrapper();
			ProposalWrapper[] proposals = new ProposalConverter().fromProposalListToProposalWrapperArray(source.getProposals());
			target.setProposals(proposals);
		}
		return target;
	}

	/**
	 * Converts from a {@link ProposalCompositionWrapper} to
	 * {@link ProposalComposition}.
	 * 
	 * @param source
	 *            The {@link ProposalCompositionWrapper} which should be
	 *            converted.
	 * @return An instance of {@link ProposalComposition}.
	 */
	public ProposalComposition fromProposalCompositionWrapperToProposalComposition(ProposalCompositionWrapper source) {
		ProposalComposition target = null;
		if (null != source) {
			target = new ProposalComposition();
			for (IProposal proposal : new ProposalConverter().fromProposalWrapperArrayToProposalList(source.getProposals())) {
				target.addProposal(proposal);
			}
		}
		return target;
	}

	/**
	 * Map the evaluation points of the agent to the current ProposalComposition
	 * and return the evaluated ProposalComposition
	 * 
	 * @param currentProposalComposition
	 *            the unevaluated ProposalComposition of the current round
	 * @param evaluationPoints
	 *            the evaluation Points given by the agents voting
	 * @return the evaluated ProposalComposition
	 */
	public ProposalComposition mapEvaluationToProposals(ProposalComposition currentProposalComposition, int[] evaluationPoints) {
		ProposalComposition evaluatedProposals = null;
		if (null != currentProposalComposition) {
			//Create a new ProposalComposition 
			evaluatedProposals = new ProposalComposition();
			for (int i = 0; i < currentProposalComposition.getNumberOfProposals(); i++) {
				try {
					// retrieve proposal of the  input composition and add a copy to the new Proposal Composition
					evaluatedProposals.addProposal(currentProposalComposition.getProposalByIndex(i).clone());
					evaluatedProposals.getProposalByIndex(i).setEvaluationPoints(evaluationPoints[i]);
					//System.out.println("Evaluation Point of Proposal " + i + " = " + evaluatedProposals.getProposalByIndex(i).getEvaluationPoints());
				} catch (ProposalNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		return evaluatedProposals;
	}

}
