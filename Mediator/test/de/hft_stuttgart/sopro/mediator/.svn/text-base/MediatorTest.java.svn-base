/**
 * 
 */
package de.hft_stuttgart.sopro.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.hft_stuttgart.sopro.common.exceptions.ProposalNotFoundException;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmEnum;
import de.hft_stuttgart.sopro.mediator.data.generator.AgentIdGenerator;

/**
 * @author Administrator
 */
public class MediatorTest {
	/**
	 * Test to instantiate
	 */
	@Test
	public void testInstatiate() {
		Mediator mediator = Mediator.getInstance();
		assertNotNull(mediator != null);

		// Mediator should always be the same instance --> singleton
		Mediator mediator2 = Mediator.getInstance();
		assertTrue(mediator == mediator2);
	}

	/**
	 * Test usage of the Mediator
	 */
	@Test
	public void testUsage() {
		Mediator mediator = Mediator.getInstance();

		// Get all available projects
		List<IProject> availableProjects = mediator.getAvailableProjects();

		// Get the project with the given ID
		// Make the test with the project with the id
		int projectIndex = 32; // Should be a project with 62 jobs
		IProject currentProject = availableProjects.get(projectIndex);
		int projectId = currentProject.getProjectId();
		int numberOfJobs = currentProject.getNumberOfJobs();

		// Set the negotiation paramenters for the test session
		int negotiationRounds = 5;
		int proposalsPerRound = 5;

		// Create two agent ids
		AgentIdGenerator agentIdGen = AgentIdGenerator.getInstance();
		int agentId1 = agentIdGen.getNextId();
		int agentId2 = agentIdGen.getNextId();

		// Join to the project
		mediator.joinProject(projectId, agentId1, negotiationRounds, proposalsPerRound, VotingAlgorithmEnum.BORDA);
		assertEquals(currentProject.getCurrentAgentsOnProject().size(), 1);
		mediator.joinProject(projectId, agentId2, negotiationRounds, proposalsPerRound, VotingAlgorithmEnum.BORDA);
		assertEquals(currentProject.getCurrentAgentsOnProject().size(), 2);

		// Get the payment data for both agents
		List<Double> paymentAgent1 = mediator.retrievePaymentDataForProject(projectId, agentId1);
		List<Double> paymentAgent2 = mediator.retrievePaymentDataForProject(projectId, agentId2);

		// Both payment lists have to have the same length
		assertEquals(paymentAgent1.size(), numberOfJobs);
		assertEquals(paymentAgent1.size(), paymentAgent2.size());

		// If the payment value in for one agent is unequal to zero the payment
		// value for the other agent has to be zero, the first and the last job
		// are dummy jobs and their payment value have to be zero for both
		double paymentValue1 = 0.0d;
		double paymentValue2 = 0.0d;
		double eps = 1e-5;
		for (int i = 0; i < numberOfJobs; i++) {
			paymentValue1 = paymentAgent1.get(i);
			paymentValue2 = paymentAgent2.get(i);

			// For the first and the last job both values have to be zero
			if (i == 0 || i == (numberOfJobs - 1)) {
				assertEquals(paymentValue1, 0.0d, eps);
				assertEquals(paymentValue2, 0.0d, eps);
			} else {
				if (Math.abs(paymentValue1) < eps) {
					assertTrue(Math.abs(paymentValue2) > eps);
				} else if (Math.abs(paymentValue2) < eps) {
					assertTrue(Math.abs(paymentValue1) > eps);
				} else { // Something is wrong --> error
					assertEquals(0, 1);
				}
			}
		}

		// Get proposals for both agents
		ProposalComposition proposalsAgent1 = mediator.retrieveProposals(projectId, agentId1);
		ProposalComposition proposalsAgent2 = mediator.retrieveProposals(projectId, agentId2);
		assertEquals(proposalsAgent1.getNumberOfProposals(), proposalsPerRound);
		assertEquals(proposalsAgent1.getNumberOfProposals(), proposalsAgent2.getNumberOfProposals());

		// The proposals for both agents should be the same
		for (int i = 0; i < proposalsPerRound; i++) {
			IProposal proposalAgent1 = null;
			IProposal proposalAgent2 = null;
			try {
				proposalAgent1 = proposalsAgent1.getProposalByIndex(i);
				proposalAgent2 = proposalsAgent2.getProposalByIndex(i);
			} catch (ProposalNotFoundException e) {
				e.printStackTrace();
			}

			assertEquals(proposalAgent1.getPermutationSize(), proposalAgent2.getPermutationSize());

			boolean equals = proposalAgent1.equals(proposalAgent2);
			assertTrue(equals);

			// Simulate the voting of the two agents and set the evaluation
			// points of the proposals
			proposalAgent1.setEvaluationPoints(i);
			proposalAgent2.setEvaluationPoints(i);
		}

		// Set the two evaluated ProposalCompositions for the session
		mediator.setProposalsWithScore(projectId, agentId1, proposalsAgent1);
		mediator.setProposalsWithScore(projectId, agentId2, proposalsAgent2);
		
		// The winner of the voting aggregation have to be the last proposal in
		// the two proposal compositions
		int winnerIndex = mediator.getWinnerProposalsIndexOfLastRound(projectId);
		IProposal winner = null;
		boolean winnerEquals = false;
		try {
			winner = proposalsAgent1.getProposalByIndex(winnerIndex);
			IProposal winnerAgent2 = proposalsAgent2.getProposalByIndex(winnerIndex);
			//The winnerIndex should retrieve the same Proposal of both agents
			assertTrue(winner.equals(winnerAgent2));
			
			winnerEquals = winner.equals(proposalsAgent1.getProposalByIndex(proposalsPerRound - 1));
		} catch (ProposalNotFoundException e) {
			e.printStackTrace();
		}
		assertTrue(winnerEquals);
		
		// Next round --> Generate new proposals
		proposalsAgent1 = mediator.retrieveProposals(projectId, agentId1);
		proposalsAgent2 = mediator.retrieveProposals(projectId, agentId2);

		// The proposals should be the same again
		assertEquals(proposalsAgent1.getNumberOfProposals(), proposalsPerRound);
		assertEquals(proposalsAgent1.getNumberOfProposals(), proposalsAgent2.getNumberOfProposals());

		// The proposals for both agents should be the same
		int[] evaluationPoints1 = new int[proposalsPerRound];
		int[] evaluationPoints2 = new int[proposalsPerRound];
		int assumedWinnerIndex = 2;
		for (int i = 0; i < proposalsPerRound; i++) {
			IProposal proposalAgent1 = null;
			IProposal proposalAgent2 = null;
			try {
				proposalAgent1 = proposalsAgent1.getProposalByIndex(i);
				proposalAgent2 = proposalsAgent2.getProposalByIndex(i);
			} catch (ProposalNotFoundException e) {
				e.printStackTrace();
			}

			assertEquals(proposalAgent1.getPermutationSize(), proposalAgent2.getPermutationSize());

			boolean equals = proposalAgent1.equals(proposalAgent2);
			assertTrue(equals);

			// Simulate the voting of the two agents and set the evaluation
			// points of the proposals
			evaluationPoints1[i] = 2*i;
			if(i==assumedWinnerIndex){
				evaluationPoints2[i] = proposalsPerRound  + 5 - i - 1;
			}
			else{
				evaluationPoints2[i] = proposalsPerRound  - i - 1;
			}
		}

		//Test the other approach for the evaluation setting
		mediator.setProposalsEvaluationPoints(projectId, agentId1, evaluationPoints1);
		mediator.setProposalsEvaluationPoints(projectId, agentId2, evaluationPoints2);

		// The winner of the voting aggregation have to be the first now
		// proposal in the two proposal compositions
		winnerIndex = mediator.getWinnerProposalsIndexOfLastRound(projectId);
		assertTrue(winnerIndex == assumedWinnerIndex);
		try {
			winner = proposalsAgent1.getProposalByIndex(winnerIndex);
			IProposal winnerAgent2 = proposalsAgent2.getProposalByIndex(winnerIndex);
			//The winnerIndex should retrieve the same Proposal of both agents
			assertTrue(winner.equals(winnerAgent2));
			
			winnerEquals = winner.equals(proposalsAgent1.getProposalByIndex(assumedWinnerIndex));
		} catch (ProposalNotFoundException e) {
			e.printStackTrace();
		}
		assertTrue(winnerEquals);

	}
}
