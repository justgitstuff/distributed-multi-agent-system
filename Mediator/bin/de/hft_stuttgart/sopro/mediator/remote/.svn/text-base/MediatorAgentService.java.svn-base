package de.hft_stuttgart.sopro.mediator.remote;

import java.util.List;

import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.project.ProjectChange;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmEnum;
import de.hft_stuttgart.sopro.common.voting.algorithms.IVotingAlgorithm;
import de.hft_stuttgart.sopro.common.wrapper.ProjectWrapper;
import de.hft_stuttgart.sopro.common.wrapper.ProposalCompositionWrapper;
import de.hft_stuttgart.sopro.mediator.Mediator;
import de.hft_stuttgart.sopro.mediator.converter.DoubleConverter;
import de.hft_stuttgart.sopro.mediator.converter.ProjectChanger;
import de.hft_stuttgart.sopro.mediator.converter.ProjectConverter;
import de.hft_stuttgart.sopro.mediator.converter.ProposalCompositionConverter;
import de.hft_stuttgart.sopro.mediator.registration.Registration;

/**
 * This is the main service class. All theses methods are called by the Client
 * through WebServices.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class MediatorAgentService {

	/**
	 * Registers an agent and returns a unique Id for this agent.
	 * 
	 * @return An unique identifier for an Agent.
	 */
	public synchronized int registerAgent() {
		int agentId = Registration.registerAgent();
		System.out.println("Agent " + agentId + " was registered");
		return agentId;
	}

	/**
	 * Unregisters an Agent.
	 * 
	 * @param agentId
	 *            The id of the Agent to unregister.
	 * @return True when unregistration was successful, otherwise false.
	 */
	public synchronized boolean unregisterAgent(int agentId) {
		System.out.println("Agent " + agentId + " was unregistered");
		return Registration.unregisterAgent(agentId);
	}

	/**
	 * Returns all available projects.
	 * 
	 * @return A list with available {@link IProject} objects.
	 */
	public ProjectWrapper[] getAvailableProjects() {
		List<IProject> projects = Mediator.getInstance().getAvailableProjects();
		ProjectWrapper[] result = new ProjectConverter().fromProjectsToProjectWrappers(projects);
		System.out.println("Returning " + result.length + " Projects");
		return result;
	}

	/**
	 * Allows an Agent to join an {@link IProject} by specifying the projectId
	 * and its agentId.
	 * 
	 * @param projectId
	 *            The id of the project to join.
	 * @param agentId
	 *            The id of the agent that wants to join the project.
	 * @param negotiationRounds
	 *            The number of negotiation rounds.
	 * @param proposalsPerRound
	 *            The number of created proposals per round.
	 * @param votingAlgorithm
	 *            The used {@link IVotingAlgorithm}.
	 * @return True if joining the project was successful, otherwise false.
	 */
	public synchronized boolean joinProject(int projectId, int agentId, int negotiationRounds, int proposalsPerRound, String votingAlgorithm) {
		System.out.println("Agent " + agentId + " is joining project " + projectId);
		return Mediator.getInstance().joinProject(projectId, agentId, negotiationRounds, proposalsPerRound, VotingAlgorithmEnum.valueOf(votingAlgorithm));
	}

	/**
	 * Allows an Agent to leave an {@link IProject}.
	 * 
	 * @param projectId
	 *            The project to leave.
	 * @param agentId
	 *            The id of the Agent.
	 * @return True when leaving the project was successful, otherwise false.
	 */
	public synchronized boolean leaveProject(int projectId, int agentId) {
		System.out.println("Agent " + agentId + " is leaving the Project " + projectId);
		return Mediator.getInstance().leaveProject(projectId, agentId);
	}

	/**
	 * Returns the payment data of the Agent with the agentId and for the
	 * {@link IProject} specified by the projectId.
	 * 
	 * @param projectId
	 *            The id of the project to retrieve the payment information
	 *            from.
	 * @param agentId
	 *            The agent id.
	 * @return The payment data of the Agent with the agentId and for the
	 *         {@link IProject} specified by the projectId.
	 */
	public synchronized double[] retrievePaymentDataForProject(int projectId, int agentId) {
		System.out.println("Agent " + agentId + " is retrieving payment data for the Project " + projectId);
		List<Double> payments = Mediator.getInstance().retrievePaymentDataForProject(projectId, agentId);
		return new DoubleConverter().fromListToArray(payments);
	}

	/**
	 * Returns the proposals for the given project id.
	 * 
	 * @param projectId
	 *            The project id from where to retrieve the proposals from.
	 * @return All proposals for the given project id.FF
	 */
	public synchronized ProposalCompositionWrapper retrieveProposals(int projectId, int agentId) {
		ProposalComposition proposalComposition = Mediator.getInstance().retrieveProposals(projectId, agentId);
		ProposalCompositionWrapper wrapper = new ProposalCompositionConverter().fromProposalCompositionToProposalCompositionWrapper(proposalComposition);
		return wrapper;
	}

	/**
	 * By calling this method the Agent can set his proposals.
	 * 
	 * @param projectId
	 *            The id of the project.
	 * @param agentId
	 *            The id of the agent.
	 * @param evaluatedProposals
	 *            The evaluated proposals.
	 */
	public synchronized boolean setProposalsWithScore(int projectId, int agentId, ProposalCompositionWrapper evaluatedProposals) {
		ProposalComposition proposalComposition = new ProposalCompositionConverter().fromProposalCompositionWrapperToProposalComposition(evaluatedProposals);
		return Mediator.getInstance().setProposalsWithScore(projectId, agentId, proposalComposition);
	}

	/**
	 * The method takes the evaluated ProposalComposition of the agent and set
	 * it in the session.
	 * 
	 * @param projectId
	 *            The id of the project
	 * @param agentId
	 *            The id of the agent which set its evaluation points
	 * @param evaluationPoints
	 *            the evaluation Points of the current proposals given by the
	 *            agents voting
	 * @return success or fail
	 */
	public boolean setProposalsEvaluationPoints(int projectId, int agentId, int[] evaluationPoints) {
		return Mediator.getInstance().setProposalsEvaluationPoints(projectId, agentId, evaluationPoints);
	}

	/**
	 * The method returns the index of the winner proposal of the voting
	 * aggregation of the last round in the current ProposalComposition for the
	 * session with the given projectId.
	 * 
	 * @param projectId
	 *            the id of the project for the session.
	 * @return The index of the winner proposal of the voting aggregation or -1
	 *         when the project was not found.
	 */
	public int getWinnerProposalsIndexOfLastRound(int projectId) {
		return Mediator.getInstance().getWinnerProposalsIndexOfLastRound(projectId);
	}

	/**
	 * Returns an array of {@link ProjectChange} instances containing project
	 * changes.
	 * 
	 * @return An array if {@link ProjectChange} instances or null when there
	 *         were no changes.
	 */
	public ProjectChange[] retrieveProjectChanges() {
		return new ProjectChanger().retrieveProjectChanges();
	}

	/**
	 * Returns a {@link ProjectChange} instance containing project changes.
	 * 
	 * @return A {@link ProjectChange} instance or null when there was no
	 *         change.
	 */
	public ProjectChange retrieveProjectChange(int projectId) {
		return new ProjectChanger().retrieveProjectChange(projectId);
	}

}
