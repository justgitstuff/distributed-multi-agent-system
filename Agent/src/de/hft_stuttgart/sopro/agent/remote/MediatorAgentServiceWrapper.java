package de.hft_stuttgart.sopro.agent.remote;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

import de.hft_stuttgart.sopro.agent.converter.DoubleConverter;
import de.hft_stuttgart.sopro.agent.converter.ListConverter;
import de.hft_stuttgart.sopro.agent.converter.ProjectConverter;
import de.hft_stuttgart.sopro.agent.converter.ProjectUpdater;
import de.hft_stuttgart.sopro.agent.converter.ProposalCompositionConverter;
import de.hft_stuttgart.sopro.agent.exception.StubNotDefinedException;
import de.hft_stuttgart.sopro.agent.gui.AmpManager;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceStub.ProposalCompositionWrapper;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;
import de.hft_stuttgart.sopro.common.voting.algorithms.IVotingAlgorithm;

/**
 * This class retrieves the Web Service requests from the Client and sends them
 * to the Mediator.
 * 
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class MediatorAgentServiceWrapper {

	/**
	 * The stub for the remote communication.
	 */
	private MediatorAgentServiceStub stub;

	/**
	 * @param stub
	 *            The {@link MediatorAgentServiceStub} instance to use for the
	 *            remote calls.
	 * @throws StubNotDefinedException
	 *             When the {@link MediatorAgentServiceStub} instance was not
	 *             properly defined.
	 */
	public MediatorAgentServiceWrapper(MediatorAgentServiceStub stub) throws StubNotDefinedException {
		if (null == stub || null != stub._getServiceClient().getServiceContext().getTargetEPR()) {
			throw new StubNotDefinedException("The Stub reference is not properly defined");
		}
		this.stub = stub;
	}

	/**
	 * Returns all available projects.
	 * 
	 * @return A list with available {@link IProject} objects.
	 */
	@SuppressWarnings("unchecked")
	public List<IProject> getAvailableProjects() {
		List<IProject> projects = Collections.EMPTY_LIST;
		try {
			projects = new ProjectConverter().fromProjectWrappersToProjects(stub.getAvailableProjects());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return projects;
	}

	/**
	 * Registers an agent and returns a unique Id for this agent.
	 * 
	 * @return A unique Id for the Agent or -1 if the registration was not
	 *         successful.
	 */
	public int registerAgent() {
		try {
			return stub.registerAgent();
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Unregisters an Agent.
	 * 
	 * @param agentId
	 *            The id of the Agent to unregister.
	 * @return True when unregistration was successful, otherwise false.
	 */
	public boolean unregisterAgent(int agentId) {
		boolean wasSuccessful = false;
		try {
			wasSuccessful = stub.unregisterAgent(agentId);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return wasSuccessful;
	}

	/**
	 *Allows an Agent to join an {@link IProject} by specifying the projectId
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
	public boolean joinProject(int projectId, int agentId, int negotiationRounds, int proposalsPerRound, String votingAlgorithm) {
		boolean wasSuccessful = false;
		try {
			wasSuccessful = stub.joinProject(projectId, agentId, negotiationRounds, proposalsPerRound, votingAlgorithm);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return wasSuccessful;
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
		boolean wasSuccessful = false;
		try {
			wasSuccessful = stub.leaveProject(projectId, agentId);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return wasSuccessful;
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
	public List<Double> retrievePaymentDataForProject(int projectId, int agentId) {
		double[] payments = null;
		try {
			payments = (double[]) stub.retrievePaymentDataForProject(projectId, agentId);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return new DoubleConverter().fromArrayToList(payments);
	}

	/**
	 * Returns the proposals for the given project id.
	 * 
	 * @param projectId
	 *            The project id from where to retrieve the proposals from.
	 * @param agentId
	 *            The id of the Agent.
	 * @return All proposals for the given project id.
	 */
	public ProposalComposition retrieveProposals(int projectId, int agentId) {
		ProposalCompositionWrapper proposalCompositionWrapper = null;
		try {
			proposalCompositionWrapper = stub.retrieveProposals(projectId, agentId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ProposalCompositionConverter().fromProposalCompositionWrapperToProposalComposition(proposalCompositionWrapper);
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
	public boolean setProposalsWithScore(int projectId, int agentId, ProposalComposition evaluatedProposals) {
		try {
			return stub.setProposalsWithScore(projectId, agentId, new ProposalCompositionConverter().fromProposalCompositionToProposalCompositionWrapper(evaluatedProposals));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * The method takes the evaluated ProposalComposition of the agent and set
	 * it in the session. The method returns the index of the winner proposal of
	 * the voting aggregation of the last round in the current
	 * ProposalComposition for the session with the given projectId. TODO ET:
	 * change javadoc
	 * 
	 * @param projectId
	 *            The id of the project
	 * @param agentId
	 *            The id of the agent which set its evaluation points
	 * @param evaluationPoints
	 *            the evaluation Points of the current proposals given by the
	 *            agents voting
	 * @return True when performing the aggregation was successful.
	 */
	public boolean setProposalsEvaluationPoints(int projectId, int agentId, List<Integer> evaluationPoints) {
		try {
			return stub.setProposalsEvaluationPoints(projectId, agentId, new ListConverter().fromIntegerListToIntArray(evaluationPoints));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
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
		try {
			return stub.getWinnerProposalsIndexOfLastRound(projectId);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Changes data on projectsToUpdate and returns it as a result.
	 * 
	 * @param negotiationDataMap
	 *            The map containing the negotiation data objects to update.
	 * @return A list of updated projects.
	 */
	public List<IProject> retrieveProjectChanges() {
		List<IProject> projectsToUpdate = AmpManager.retrieveAvailableProjects();
		try {
			projectsToUpdate = new ProjectUpdater().changeProjects(projectsToUpdate, stub.retrieveProjectChanges());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return projectsToUpdate;
	}

	/**
	 * Changes data on projectToUpdate and returns it as a result.
	 * 
	 * @param projectToUpdate
	 *            The {@link IProject} instance which should be changed.
	 * @return An {@link IProject} instances which was changed or the passed
	 *         instance when there were no changes.
	 */
	public IProject retrieveProjectChange(IProject projectToUpdate) {
		try {
			if (null != projectToUpdate) {
				projectToUpdate = new ProjectUpdater().changeProject(projectToUpdate, stub.retrieveProjectChange(projectToUpdate.getProjectId()));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return projectToUpdate;
	}
}
