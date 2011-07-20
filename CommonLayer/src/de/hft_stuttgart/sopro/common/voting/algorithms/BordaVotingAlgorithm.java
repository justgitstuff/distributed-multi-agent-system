/*
 * $LastChangedRevision: 818 $ $LastChangedBy: annemarie $ $LastChangedDate:
 * 2009-11-13 14:44:21 +0100 (Fr, 13 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common
 * /voting/algorithms/BordaVotingAlgorithm.java $ $Id: BordaVotingAlgorithm.java
 * 334 2009-11-13 13:44:21Z annemarie $
 */
package de.hft_stuttgart.sopro.common.voting.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hft_stuttgart.sopro.common.exceptions.PerformAggregationException;
import de.hft_stuttgart.sopro.common.exceptions.ProposalNotFoundException;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;

public class BordaVotingAlgorithm implements IVotingAlgorithm {

	/**
	 * The index of the proposal which is the winner of the aggregation
	 */
	int winnerIndexLastRound;
	
	
	/**
	 * {@inheritDoc}
	 */
	public List<Integer> performVoting(double bestTotalCashValue, List<Double> totalCashValueAllProposals) {

		// Get the number of proposals which should be evaluated
		int numOfProposals = totalCashValueAllProposals.size();

		// Create a new evaluation array for the proposals
		List<Integer> evaluation = new ArrayList<Integer>(numOfProposals);

		// Sort the cash values in a new array
		List<Double> sortedCashValues = new ArrayList<Double>(numOfProposals);
		sortedCashValues.addAll(totalCashValueAllProposals);
		Collections.sort(sortedCashValues);
	
		// Go throw all cash values and calculate their evaluation points
		for (int i = 0; i < numOfProposals; i++) {
			Double currentValue = totalCashValueAllProposals.get(i);

			// The index+1 of the current Value is the same as the evaluation
			// points
			int evaluationPoints = sortedCashValues.indexOf(currentValue);

			// Add the evaluation points to the evaluation point list
			while (evaluation.contains(evaluationPoints)) {
				evaluationPoints++;
			}

			evaluation.add(new Integer(evaluationPoints));
		}

		return evaluation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws PerformAggregationException
	 */
	public ProposalComposition performAggregation(ProposalComposition proposalEvaluationAgent1, ProposalComposition proposalEvaluationAgent2) throws PerformAggregationException {

		// The proposal Compositions have to have the same size
		if (proposalEvaluationAgent1.getNumberOfProposals() != proposalEvaluationAgent2.getNumberOfProposals()) {
			throw new PerformAggregationException();
		}

		int sumEvaluationPoints = 0;
		int maxEvaluationPoints = 0;
		int winnerIndex = -1;

		// Go throw all the evaluated proposals and look for the winner
		for (int i = 0; i < proposalEvaluationAgent1.getNumberOfProposals(); i++) {
			IProposal proposalAgent1 = null;
			IProposal proposalAgent2 = null;
			try {
				proposalAgent1 = proposalEvaluationAgent1.getProposalByIndex(i);
				proposalAgent2 = proposalEvaluationAgent2.getProposalByIndex(i);
			} catch (ProposalNotFoundException e) {
				e.printStackTrace();
			}

			// Sum up the two evaluation points of the tow proposals
			sumEvaluationPoints = proposalAgent1.getEvaluationPoints() + proposalAgent2.getEvaluationPoints();
			//System.out.println("EvaluationPoints Agent1 = " + proposalAgent1.getEvaluationPoints());
			//System.out.println("EvaluationPoints Agent2 = " + proposalAgent2.getEvaluationPoints());
			//System.out.println("Sum = " + sumEvaluationPoints);
			
			// If the current proposals have a higher evaluation sum --> set the
			// new sum to the max evaluation points and store the current index
			// in the winnerIndex variable
			if (sumEvaluationPoints > maxEvaluationPoints) {
				maxEvaluationPoints = sumEvaluationPoints;
				winnerIndex = i;
			}
		}

		// Add the winner proposal to the return ProposalComposition
		ProposalComposition returnValue = new ProposalComposition();
		try {
			returnValue.addProposal(proposalEvaluationAgent1.getProposalByIndex(winnerIndex));
		} catch (ProposalNotFoundException e) {
			e.printStackTrace();
		}
		
		//Set the winner index of the aggregation
		this.winnerIndexLastRound = winnerIndex;

		// If there is no proposal in the output container there is a error -->
		// throw exception
		if (returnValue.getNumberOfProposals() == 0) {
			throw new PerformAggregationException();
		}

		return returnValue;
	}

	public int getWinnerIndexLastRound(){
		return winnerIndexLastRound;
	}
}