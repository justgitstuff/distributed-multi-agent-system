/*
 * $LastChangedRevision: 856 $ $LastChangedBy: annemarie $ $LastChangedDate:
 * 2009-11-05 16:58:34 +0100 (Do, 05 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/Agent/src/de/hft_stuttgart/sopro/agent/Agent.java $ $Id:
 * Agent.java 143 2009-11-05 15:58:34Z sandro $
 */
package de.hft_stuttgart.sopro.agent;

import java.util.ArrayList;
import java.util.List;

import de.hft_stuttgart.sopro.common.exceptions.JobInPermutationNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.ProposalNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.ResourceNotFoundException;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmEnum;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmFactory;
import de.hft_stuttgart.sopro.common.voting.algorithms.IVotingAlgorithm;

/**
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class Agent implements IAgent {

	/**
	 * The current project in which the Agent is participating in.
	 */
	private IProject currentProject;

	/**
	 * The winner {@link IProposal} which is set by
	 * {@link #performVoting(ProposalComposition)}.
	 */
	private IProposal winnerProposal;

	/**
	 * The id of this agent.
	 */
	private int agentId = -1;

	/**
	 * The interest rate which is needed for the calculation of the cashvalue
	 */
	private double interestRate = 0.01;

	/**
	 * The payments of the jobs of the agent
	 */
	private List<Double> payments;

	/**
	 * The Voting Algorithm which is used for the negotiation
	 */
	private IVotingAlgorithm votingAlgorithm = null;

	/**
	 * {@inheritDoc}
	 */
	public void setVotingAlgorithm(VotingAlgorithmEnum votingAlgorithmEnum) {
		this.votingAlgorithm = VotingAlgorithmFactory.createInstance(votingAlgorithmEnum);
	}

	/**
	 * The method calculates the total cash value of a certain project plan
	 * 
	 * @param proposal
	 *            a project plan for which the total cash value is calculated
	 * @return the total cash value of a certain project plan ( proposal )
	 */
	public double calculateTotalCashValue(IProposal proposal) {
		double totalCashValue = 0.0;
		try {
			if (null != proposal) {
				// Calculate the total cashValue for each resource and sum them
				// up
				int numOfRes = proposal.getNumberOfResourceStarttimes();
				int numOfJobs = proposal.getPermutationSize();
				
				// Go through all jobs of the proposal and calculate the
				// cash value for the job for the current resource with
				// index i
				for (int j = 0; j < numOfJobs; j++) {
					IJob job = proposal.getPermutation(j);
					int numOfUsedRes = 0;
					double totalCashValueOfRes = 0;
					for (int i = 0; i < numOfRes; i++) {
						double jobValue = this.calculateCashValue(job, i);
						totalCashValueOfRes += jobValue;
						if(jobValue!=0){
							numOfUsedRes++;
						}
					}
					
					if(numOfUsedRes == 0){
						continue;
					}
					
					totalCashValue += (totalCashValueOfRes/numOfUsedRes);
				}
			}
		} catch (JobInPermutationNotFoundException e) {
			e.printStackTrace();
		}

		return totalCashValue;
	}

	/**
	 * The method calculates the cash value of a job with the given rate of
	 * interest z, the payment of the job p and its starttime t in a resources
	 * project plan as: cashValue = p / (1 + z)^t
	 * 
	 * @param job
	 *            the job for which you want to calculate the cash value
	 * @param resourceIndex
	 *            the index of the resource which starttime should be used for
	 *            the calculation
	 * @return the cash value for the given job, its payment and its starttime
	 */
	private double calculateCashValue(IJob job, int resourceIndex) {
		// Get the endtime of the job in the given resource
		int starttime = 0;
		int endtime = 0;
		try {
			starttime = job.retrieveStartTimeFromIndex(resourceIndex);
			if(starttime==-1){
				return 0;
			}
			endtime = job.retrieveEndTimeFromIndex(resourceIndex);
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		
		// get the payment of the given job
		double payment = this.payments.get(job.getJobNumber() - 1).doubleValue();
		// Calculate the cash value
		double returnValue = (payment / Math.pow((1 + interestRate), endtime));
		return returnValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Integer> performVoting(ProposalComposition proposalComposition) {
		int numOfProposals = proposalComposition.getNumberOfProposals();

		// Calculate for each proposal the total cash value
		List<Double> totalCashValue = new ArrayList<Double>(numOfProposals);
		double currentTotalCashValue = 0.0;
		double maxTotalCashValue = 0;
		for (int i = 0; i < numOfProposals; i++) {
			// Get the proposal of the proposal composition
			IProposal proposal = null;
			try {
				proposal = proposalComposition.getProposalByIndex(i);
			} catch (ProposalNotFoundException e) {
				e.printStackTrace();
			}

			// Calculate the total cash value of the current proposal
			currentTotalCashValue = this.calculateTotalCashValue(proposal);
			if (currentTotalCashValue > maxTotalCashValue) {
				maxTotalCashValue = currentTotalCashValue;
			}

			// Add the current cash value to the cash value array
			totalCashValue.add(new Double(currentTotalCashValue));
		}

		// Go again through the proposals and calculate now their points
		// according to the Voting algorithm which is used
		List<Integer> evaluationPoints = this.votingAlgorithm.performVoting(maxTotalCashValue, totalCashValue);
		int winnerIndex = 0;
		int maxEvaluationPoints = 0;
		for (int i = 0; i < numOfProposals; i++) {
			// Get the proposal of the proposal composition
			IProposal proposal = null;
			try {
				proposal = proposalComposition.getProposalByIndex(i);
			} catch (ProposalNotFoundException e) {
				e.printStackTrace();
			}

			// Set the evaluation Points in the Proposal
			proposal.setEvaluationPoints(evaluationPoints.get(i));
			if (evaluationPoints.get(i) > maxEvaluationPoints) {
				maxEvaluationPoints = evaluationPoints.get(i);
				winnerIndex = i;
			}
		}

		// Get the winner of the voting
		try {
			IProposal winner = proposalComposition.getProposalByIndex(winnerIndex);
			this.setWinnerProposal(winner);
		} catch (ProposalNotFoundException e) {
			e.printStackTrace();
		}

		return evaluationPoints;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPaymentData(List<Double> payments) {
		// the input list is unmodifiable --> the member is also unmodifiable
		this.payments = payments;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getAgentId() {
		return agentId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the interestRate
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * @param interestRate
	 *            the interestRate to set
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * {@inheritDoc}
	 */
	public IProject getCurrentProject() {
		return currentProject;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCurrentProject(IProject currentProject) {
		this.currentProject = currentProject;
	}

	/**
	 * {@inheritDoc}
	 */
	public IProposal getWinnerProposal() {
		return winnerProposal;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setWinnerProposal(IProposal winnerProposal) {
		this.winnerProposal = winnerProposal;
	}

}
