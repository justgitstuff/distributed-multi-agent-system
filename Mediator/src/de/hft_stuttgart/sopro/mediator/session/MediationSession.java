package de.hft_stuttgart.sopro.mediator.session;

import java.util.ArrayList;
import java.util.List;

import de.hft_stuttgart.sopro.common.exceptions.JobInPermutationNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.NotImplementedException;
import de.hft_stuttgart.sopro.common.exceptions.PerformAggregationException;
import de.hft_stuttgart.sopro.common.exceptions.ProposalNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.ResourceNotFoundException;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmEnum;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmFactory;
import de.hft_stuttgart.sopro.common.voting.algorithms.IVotingAlgorithm;
import de.hft_stuttgart.sopro.mediator.data.DataIO;
import de.hft_stuttgart.sopro.mediator.data.SolutionData;
import de.hft_stuttgart.sopro.mediator.data.generator.ProposalGeneratorAbstract;
import de.hft_stuttgart.sopro.mediator.data.generator.ProposalGeneratorEnum;
import de.hft_stuttgart.sopro.mediator.data.generator.ProposalGeneratorFactory;
import de.hft_stuttgart.sopro.mediator.exceptions.GenerateProposalException;
import de.hft_stuttgart.sopro.mediator.exceptions.ProjectTypeUnknownException;

/**
 * This class contains all information needed for a single mediation session.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 */
public class MediationSession {

	/**
	 * The epsilon for double comparison
	 */
	private double eps = 1e-5;

	/**
	 * The time when the last Agent did something on this session.
	 */
	private long lastModificationTime = 0;

	/**
	 * The current project which we are working on in this session.
	 */
	private IProject currentProject;

	/**
	 * The id of the Agent that joined this session first.
	 */
	private int creatorAgentId;

	/**
	 * The proposal generator for the session
	 */
	private ProposalGeneratorAbstract proposalGenerator = null;

	/**
	 * The proposals of one generation step, the generated proposals must be the
	 * same for both agents which call the method createProposals
	 */
	private ProposalComposition proposalsOfCurrentRound = null;

	/**
	 * The Voting Algorithm which is used for the negotiation
	 */
	private IVotingAlgorithm votingAlgorithm = null;

	/**
	 * Shows which agents are currently on that session.
	 */
	private List<Integer> currentAgentsOnSession = new ArrayList<Integer>(2);

	/**
	 * Number of negotiation rounds.
	 */
	private int negotiationRounds = 100;

	/**
	 * The number of rounds which are already negotiated
	 */
	private int currentRound = 0;

	/**
	 * Number of ants (proposals) per round rounds.
	 */
	private int proposalsPerRound = 30;

	/**
	 * The interest rate of the session
	 */
	private double interestRate = 0.01d;

	/**
	 * The container for the evaluated Proposals of the agents. The first
	 * proposalComposition is the evaluation of the agent which has opened the
	 * session, the second one is the evaluation of the agent which has joint
	 * the session
	 */
	ProposalComposition proposalEvaluation1 = null;
	ProposalComposition proposalEvaluation2 = null;

	/**
	 * The winner of the voting aggregation of the last round
	 */
	IProposal winnerProposalLastRound = null;

	/**
	 * The index of the winner of the voting aggregation of the last round in
	 * the current proposalComposition
	 */
	private int winnerProposalIndexLastRound = 0;

	/**
	 * The total cash value of the first Agent.
	 */
	private double totalCashValueFirstAgent = 0.0;

	/**
	 * The total cash value of the second Agent.
	 */
	private double totalCashValueSecondAgent = 0.0;

	/**
	 * The sum of the total cash values of both agents at the beginning of the
	 * negotiation
	 */
	private double sumFirstRound = 0.0;

	/**
	 * The default constructor taking an {@link IProject} object. This
	 * constructor creates also a new {@link ProposalGeneratorAbstract} instance
	 * with the given {@link #getProposalsPerRound()}.
	 * 
	 * @param project
	 *            The current project on that session to set.
	 * @param agentId
	 *            The id of the agent that opens that session.
	 * @throws ProjectTypeUnknownException 
	 */
	public MediationSession(IProject project, int agentId, ProposalGeneratorEnum proposalGeneratorEnum, int negotiationRounds, int proposalsPerRound) throws ProjectTypeUnknownException {
		setCurrentProject(project);
		this.creatorAgentId = agentId;
		this.negotiationRounds = negotiationRounds;
		this.proposalsPerRound = proposalsPerRound;

		// Create a proposal generator for the session
		this.proposalGenerator = ProposalGeneratorFactory.createInstance(proposalGeneratorEnum, project, negotiationRounds, proposalsPerRound);

		// Depending on the number of jobs in the project --> set the parameter
		// of the proposal generator
		switch (project.getNumberOfJobs()) {
		case 32: {
			// The best parameterization for a project with 32 jobs is
			// UpdateUnit = 4 && percetageOfRounds = 80%
			this.proposalGenerator.setUpdateUnit(4.0);
			//this.proposalGenerator.setUpdateUnit(1.0); //Constant unit
			this.proposalGenerator.setPercentageOfRounds(0.8);
		}
			break;
		case 62: {
			// The best parameterization for a project with 62 jobs is
			// UpdateUnit = 100 && percetageOfRounds = 80%
			this.proposalGenerator.setUpdateUnit(200.0);
			//this.proposalGenerator.setUpdateUnit(7.0); //Constant unit
			this.proposalGenerator.setPercentageOfRounds(0.8);
		}
			break;
		case 122: {
			// The best parameterization for a project with 122 jobs is
			// UpdateUnit = 2000 && percetageOfRounds = 80%
			this.proposalGenerator.setUpdateUnit(15000.0);
			//this.proposalGenerator.setUpdateUnit(80.0); //Constant unit
			this.proposalGenerator.setPercentageOfRounds(0.8);
		}
			break;
		default: {
			throw new ProjectTypeUnknownException();
		}
		}
	}

	/**
	 * The method sets the voting algorithm which is used for the negotiation
	 * 
	 * @param votingAlgoEnum
	 *            the enum of the algorithm which should be used
	 */
	public void setVotingAlgorithm(VotingAlgorithmEnum votingAlgorithmEnum) {
		this.votingAlgorithm = VotingAlgorithmFactory.createInstance(votingAlgorithmEnum);
	}

	/**
	 * The method generates proposals for the session
	 * 
	 * @param agentId
	 *            The id of the Agent.
	 * @throws ResourceNotFoundException
	 * @throws GenerateProposalException
	 */
	public ProposalComposition retrieveProposals(int agentId) throws GenerateProposalException, ResourceNotFoundException {
		// update the last modification time
		this.setLastModificationTime(System.currentTimeMillis());

		// Generate the proposals once per round, after the round is over with
		// the update of the pheromone matrix set the proposals of the current
		// round again to null
		if (this.proposalsOfCurrentRound == null) {
			this.proposalsOfCurrentRound = proposalGenerator.generateProposals();
		}

		// Check which agent asks for the the proposals and is this round
		// already finished?
		if (agentId == this.creatorAgentId) {
			// If the evaluated proposals of agent1 is not null and the
			// evaluated proposals of agent2 is null --> agent1 has already
			// called the setProposalsWithScore method but agent2 hasn't so
			// agent1 can't get new proposals
			if (proposalEvaluation1 != null && proposalEvaluation2 == null) {
				return new ProposalComposition();
			} else {
				return this.proposalsOfCurrentRound;
			}
		} else {
			if (proposalEvaluation2 != null && proposalEvaluation1 == null) {
				return new ProposalComposition();
			} else {
				return this.proposalsOfCurrentRound;
			}
		}
	}

	/**
	 * The method updates the pheromone Matrix for the session
	 * 
	 * @param bestProposal
	 *            the winner of the voting aggregation
	 * @throws JobInPermutationNotFoundException
	 */
	public void updatePheromoneMatrix(IProposal bestProposal) throws JobInPermutationNotFoundException {
		proposalGenerator.updatePheromoneMatrix(bestProposal, this.currentRound);

		// Set the proposals of the current round again to null for the next
		// round
		this.proposalsOfCurrentRound = null;

		// Set the evaluated proposals of the current round to null for the next
		// round
		this.proposalEvaluation1 = null;
		this.proposalEvaluation2 = null;

		// Set the total cash values of the current round to zero for the next
		// round
		this.totalCashValueFirstAgent = 0.0;
		this.totalCashValueSecondAgent = 0.0;
	}

	/**
	 * The method performs the aggregation on the two evaluated proposal
	 * Compositions and updates the pheromone matrix with the winner proposal
	 * 
	 * @param agentId
	 *            the id of the agent which sets its evaluated proposals
	 * @param evaluatedProposals
	 *            the container with the evaluated proposals
	 * @throws NotImplementedException
	 */
	public void performAggregation() throws NotImplementedException {
		// If both proposal evaluations are set than performAggregation
		if (this.proposalEvaluation1 != null && this.proposalEvaluation2 != null) {
			// Increase the counter of the currentRounds
			this.currentRound++;

			ProposalComposition winnerProposals = null;
			try {
				winnerProposals = votingAlgorithm.performAggregation(this.proposalEvaluation1, this.proposalEvaluation2);
				this.winnerProposalIndexLastRound = votingAlgorithm.getWinnerIndexLastRound();
			} catch (PerformAggregationException e) {
				e.printStackTrace();
			}

			// Update the pheromone matrix with the winner proposal
			switch (VotingAlgorithmFactory.getEnumOfVotingAlgorithm(votingAlgorithm)) {
			case BORDA:
				// Get the one winner proposal out of the winner container
				IProposal winner = null;
				try {
					// Update the pheromone matrix with the winner proposal
					winner = winnerProposals.getProposalByIndex(0);
					// Set the winner proposal of the current round
					this.winnerProposalLastRound = winner;
					this.updatePheromoneMatrix(winner);
				} catch (ProposalNotFoundException e) {
					e.printStackTrace();
				} catch (JobInPermutationNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case APPROVAL:
				throw new NotImplementedException("The Voting Algorithm " + VotingAlgorithmEnum.APPROVAL.name() + " is currently not implemented");
			case COPELAND:
				throw new NotImplementedException("The Voting Algorithm " + VotingAlgorithmEnum.COPELAND.name() + " is currently not implemented");
			case PLURALITY:
				throw new NotImplementedException("The Voting Algorithm " + VotingAlgorithmEnum.PLURALITY.name() + " is currently not implemented");
			case SCORING:
				throw new NotImplementedException("The Voting Algorithm " + VotingAlgorithmEnum.SCORING.name() + " is currently not implemented");
			default:
				try {
					throw new PerformAggregationException("The aggregation could not be performed");
				} catch (PerformAggregationException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	/**
	 * The method gets the winner proposal of the voting aggregation of the last
	 * round
	 * 
	 * @return the winner proposal of the voitng aggregation
	 */
	public IProposal getWinnerProposalOfLastRound() {
		return this.winnerProposalLastRound;
	}

	/**
	 * The method takes the evaluated ProposalComposition of the agent and set
	 * it
	 * 
	 * @param evaluatedProposals
	 *            the proposal container which contains now a score for each
	 *            proposal
	 * @param agentId
	 *            the id of the agent which has evaluated the proposals
	 */
	public void setProposalsWithScore(ProposalComposition evaluatedProposals, int agentId) {
		if (agentId == this.creatorAgentId) {
			this.proposalEvaluation1 = evaluatedProposals;
		} else {
			this.proposalEvaluation2 = evaluatedProposals;
		}
	}

	/**
	 * @param totalCashValue
	 * @param startCashValue
	 * @param agentId
	 */
	public void setTotalCashValueOfCurrentRound(double totalCashValue, double startCashValue, int agentId) {
		// If the total cash value is zero set it to eps, so that the session
		// can recognize that the total CashValue was set by an agent
		if (Math.abs(totalCashValue) < eps) {
			totalCashValue = eps;
		}
		if (agentId == this.creatorAgentId) {
			this.totalCashValueFirstAgent = totalCashValue;
			this.sumFirstRound += startCashValue;
		} else {
			this.totalCashValueSecondAgent = totalCashValue;
			this.sumFirstRound += startCashValue;
		}
	}

	/**
	 * The method dumps the data to a file and a database
	 */
	public void dumpEvaluationData() {
		// If both agents have set their total cash value --> dump the data to
		// the db and to a file
		if (Math.abs(this.totalCashValueFirstAgent) < eps && Math.abs(this.totalCashValueSecondAgent) < eps) {
			DataIO dataIO = new DataIO();

			// Pack the results of the negotiation in a solution data obj
			SolutionData solution = new SolutionData(currentProject.getProjectName(), currentProject.getNumberOfJobs(), sumFirstRound, this.totalCashValueFirstAgent + this.totalCashValueSecondAgent,
					VotingAlgorithmEnum.BORDA, ProposalGeneratorEnum.ADVANCED_ADDITION, negotiationRounds, proposalsPerRound, this.getWinnerProposalOfLastRound().calculateMakespans(), "not known");

			dataIO.writeNegotiationSolution(solution);
			dataIO.dumpData(solution);
		}
	}

	/**
	 * The method returns the index of the payment data of the agentId
	 * 
	 * @param agentId
	 *            the id of the agent which has evaluated the proposals
	 * @return the index of the payment data either 0 if the agent was the first
	 *         agent on the session or 1 if the agent was the second one
	 */
	public int getPaymentIndexOfAgent(int agentId) {
		int paymentIndedx = -1;
		if (agentId == this.creatorAgentId) {
			paymentIndedx = 0;
		} else {
			paymentIndedx = 1;
		}

		return paymentIndedx;
	}

	/**
	 * Add a agent to the session and to the current project
	 * 
	 * @param agentId
	 *            The id of the agent which should be added to the session
	 */
	public void addAgentToSession(int agentId) {
		// There can be at most two agents
		if (currentAgentsOnSession.size() < 2) {
			// update the last modification time
			this.setLastModificationTime(System.currentTimeMillis());

			currentAgentsOnSession.add(new Integer(agentId));

			// Set also the current agents in the project
			this.currentProject.addAgentToProject(new Integer(agentId));
		}
	}

	/**
	 * Removes an agent from the session and from the current project.
	 * 
	 * @param agentId
	 *            The id of the Agent to remove.
	 * @return True if removing the Agent from the session was successful,
	 *         otherwise false.
	 */
	public boolean removeAgentFromSession(int agentId) {
		boolean wasSuccessful = false;
		if (!currentAgentsOnSession.isEmpty()) {
			boolean firstSuccess = currentAgentsOnSession.remove(new Integer(agentId));
			boolean secondSuccess = this.currentProject.removeAgentFromProject(agentId);

			// returns only true when removing from the session and from the
			// project was successful
			wasSuccessful = firstSuccess && secondSuccess;
		}
		return wasSuccessful;
	}

	public int getWinnerProposalIndexOfLastRound() {
		return this.winnerProposalIndexLastRound;
	}

	public List<Integer> getCurrentAgentsOnSession() {
		return currentAgentsOnSession;
	}

	public int getNegotiationRounds() {
		return negotiationRounds;
	}

	public void setNegotiationRounds(int negotiationRounds) {
		this.negotiationRounds = negotiationRounds;
	}

	public int getProposalsPerRound() {
		return proposalsPerRound;
	}

	public void setProposalsPerRound(int proposalsPerRound) {
		this.proposalsPerRound = proposalsPerRound;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getTotalCashValueFirstAgent() {
		return totalCashValueFirstAgent;
	}

	public double getTotalCashValueSecondAgent() {
		return totalCashValueSecondAgent;
	}

	public void setCurrentProject(IProject currentProject) {
		this.currentProject = currentProject;
	}

	public IProject getCurrentProject() {
		return currentProject;
	}

	public long getLastModificationTime() {
		return lastModificationTime;
	}

	public void setLastModificationTime(long lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}

	/**
	 * Removes all Agents from that session. This method is mainly used by the
	 * {@link ResourceCleaner}.
	 */
	public void removeAllAgentsFromSession() {
		List<Integer> currentAgents = new ArrayList<Integer>(currentAgentsOnSession.size());
		currentAgents.addAll(currentAgentsOnSession);
		for (Integer agentId : currentAgents) {
			this.removeAgentFromSession(agentId.intValue());
		}
	}

	public IVotingAlgorithm getVotingAlgorithm() {
		return votingAlgorithm;
	}
}
