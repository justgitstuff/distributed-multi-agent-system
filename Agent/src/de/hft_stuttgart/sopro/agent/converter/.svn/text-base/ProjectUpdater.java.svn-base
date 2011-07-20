package de.hft_stuttgart.sopro.agent.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hft_stuttgart.sopro.agent.data.NegotiationData;
import de.hft_stuttgart.sopro.agent.gui.AmpManager;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceStub.ProjectChange;
import de.hft_stuttgart.sopro.common.project.IProject;

/**
 * @author Eduard Tudenhoefner - nastra@gmx.net
 * @author Sandro Degiorgi - sdegiorgi@gmail.com
 */
public class ProjectUpdater {

	/**
	 * The fake id of the agent when something is changed.
	 */
	private static final int AGENT_FAKE_ID = -5;

	/**
	 * Changes data on projectsToUpdate and returns it as a result.
	 * 
	 * @param projectsToUpdate
	 *            The list of projects which should be updated.
	 * @param negotiationDataMap
	 *            The hashmap with the negotiation data objects to update.
	 * @param changes
	 *            The changes which needs to be updated in to the
	 *            projectsToUpdate list.
	 * @return A list of updated projects or the previous passed
	 *         projectsToUpdate list when there were no changes.
	 */
	public List<IProject> changeProjects(List<IProject> projectsToUpdate, ProjectChange[] changes) {
		if (null != changes && changes.length > 0) {
			for (int i = 0; i < changes.length; i++) {
				ProjectChange projectChange = changes[i];
				if (null != projectChange) {
					int projectId = projectChange.getProjectId();

					int numberOfAgents = projectChange.getNumberOfAgentsOnProject();
					for (IProject project : projectsToUpdate) {
						if (project.getProjectId() == projectId) {
							// fill the hashmap with the needed values
							Map<Integer, NegotiationData> negotiationDataMap = new HashMap<Integer, NegotiationData>();
							NegotiationData negotiationData = new NegotiationData();
							negotiationData.setNegotiationRounds(projectChange.getNegotiationRounds());
							negotiationData.setProposalsPerRound(projectChange.getProposalsPerRound());
							negotiationData.setVotingAlgorithm(projectChange.getVotingAlgorithm());
							negotiationDataMap.put(new Integer(projectId), negotiationData);
							AmpManager.setNegotiationDataMap(negotiationDataMap);

							// update the current agents on the project
							List<Integer> currentAgentsOnProject = new ArrayList<Integer>(numberOfAgents);
							for (int j = 0; j < numberOfAgents; j++) {
								currentAgentsOnProject.add(new Integer(AGENT_FAKE_ID));
							}
							project.setCurrentAgentsOnProject(currentAgentsOnProject);
							break;
						}
					}
				}
			}
		}
		return projectsToUpdate;
	}

	/**
	 * Changes data on projectToUpdate and returns it as a result.
	 * 
	 * @param projectToUpdate
	 *            The {@link IProject} instance which should be changed.
	 * @param change
	 *            The {@link ProjectChange} instance containing the changes
	 *            which need to be updated.
	 * @return An {@link IProject} instances which was changed or the passed
	 *         instance when there were no changes.
	 */
	public IProject changeProject(IProject projectToUpdate, ProjectChange change) {
		if (null != change) {
			int numberOfAgents = change.getNumberOfAgentsOnProject();
			List<Integer> currentAgentsOnProject = new ArrayList<Integer>(numberOfAgents);
			for (int j = 0; j < numberOfAgents; j++) {
				currentAgentsOnProject.add(new Integer(AGENT_FAKE_ID));
			}
			projectToUpdate.setCurrentAgentsOnProject(currentAgentsOnProject);
		}
		return projectToUpdate;
	}

}
