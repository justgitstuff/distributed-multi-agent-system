/*
 * $LastChangedRevision: 317 $ $LastChangedBy: edu $ $LastChangedDate:
 * 2009-11-08 19:34:06 +0100 (So, 08 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common
 * /project/ProjectComposition.java $ $Id: ProjectComposition.java 243
 * 2009-11-08 18:34:06Z edu $
 */
package de.hft_stuttgart.sopro.common.proposal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hft_stuttgart.sopro.common.exceptions.ProposalNotFoundException;

/**
 * @author Annemarie Meissner meissner.annemarie@gmx.de
 */
public class ProposalComposition {

	/**
	 * The container of Proposals
	 */
	private List<IProposal> proposals = new ArrayList<IProposal>();

	/**
	 * The method adds a Proposal to the Proposal composition
	 * 
	 * @param proposal
	 *            Proposal which should added to the composition
	 */
	public void addProposal(IProposal proposal) {
		this.proposals.add(proposal);
	}

	/**
	 * The method returns the number of Proposals in the composition
	 * 
	 * @param
	 * @return returns an integer with the number of Proposals in the
	 *         composition
	 */
	public int getNumberOfProposals() {
		return this.proposals.size();
	}

	/**
	 * The method returns the Proposal which is at the index 'index' in the
	 * composition
	 * 
	 * @param index
	 *            index of the Proposal in the composition
	 * @return the Proposal at the index
	 */
	public IProposal getProposalByIndex(int index) throws ProposalNotFoundException {
		if (index < 0 || index >= this.proposals.size()) {
			// Exception is thrown when there is no Proposal found.
			throw new ProposalNotFoundException();
		}

		return this.proposals.get(index);
	}

	/**
	 * @return An unmodifiable list of {@link IProposal} instances.
	 */
	public List<IProposal> getProposals() {
		return Collections.unmodifiableList(proposals);
	}

}
