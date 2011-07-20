package de.hft_stuttgart.sopro.agent.converter;

import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition; // TODO:
// change
// imports
// to
// import de.hft_stuttgart.sopro.common.wrapper.ProposalCompositionWrapper;
// import de.hft_stuttgart.sopro.common.wrapper.ProposalWrapper;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceStub.ProposalCompositionWrapper;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceStub.ProposalWrapper;

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
		if (null != source && null != source.getProposals()) {
			target = new ProposalComposition();
			for (IProposal proposal : new ProposalConverter().fromProposalWrapperArrayToProposalList(source.getProposals())) {
				target.addProposal(proposal);
			}
		}
		return target;
	}

}
