/*
 * $LastChangedRevision: 734 $ $LastChangedBy: annemarie $ $LastChangedDate:
 * 2009-11-13 14:44:21 +0100 (Fr, 13 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common
 * /voting/algorithms/CopelandVotingAlgorithm.java $ $Id:
 * CopelandVotingAlgorithm.java 334 2009-11-13 13:44:21Z annemarie $
 */
package de.hft_stuttgart.sopro.common.voting.algorithms;

import java.util.List;

import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;

public class CopelandVotingAlgorithm implements IVotingAlgorithm {


	/**
	 * The index of the proposal which is the winner of the aggregation
	 */
	int winnerIndexLastRound;
	
	
	/**
	 * {@inheritDoc}
	 */
	public List<Integer> performVoting(double bestTotalCashValue, List<Double> totalCashValueAllProposals) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public ProposalComposition performAggregation(ProposalComposition proposalEvaluationAgent1, ProposalComposition proposalEvaluationAgent2) {
		return null;
	}
	

	public int getWinnerIndexLastRound(){
		return winnerIndexLastRound;
	}
}
