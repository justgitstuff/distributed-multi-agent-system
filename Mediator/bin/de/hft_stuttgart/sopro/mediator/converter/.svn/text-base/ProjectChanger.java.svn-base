package de.hft_stuttgart.sopro.mediator.converter;

import java.util.Iterator;
import java.util.Map;

import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.project.ProjectChange;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmFactory;
import de.hft_stuttgart.sopro.common.voting.algorithms.IVotingAlgorithm;
import de.hft_stuttgart.sopro.mediator.Mediator;
import de.hft_stuttgart.sopro.mediator.session.MediationSession;

/**
 * @author Eduard Tudenhoefner - nastra@gmx.net
 * @author Sandro Degiorgi - sdegiorgi@gmail.com
 */
public class ProjectChanger {

	/**
	 * Returns an array of {@link ProjectChange} instances containing project
	 * changes.
	 * 
	 * @return An array if {@link ProjectChange} instances or null when there
	 *         were no changes.
	 */
	@SuppressWarnings("unchecked")
	public ProjectChange[] retrieveProjectChanges() {
		Map sessionMap = Mediator.getInstance().getMediationSessionMap();

		// need to synchronize the map, because we are using an Iterator
		synchronized (sessionMap) {
			if (null != sessionMap && !sessionMap.isEmpty()) {
				// get the information from the available sessions
				Iterator iterator = sessionMap.entrySet().iterator();
				ProjectChange[] projects = new ProjectChange[sessionMap.size()];
				int i = 0;
				while (iterator.hasNext()) {
					Map.Entry<String, MediationSession> entry = (Map.Entry<String, MediationSession>) iterator.next();
					MediationSession session = entry.getValue();
					ProjectChange projectChange = new ProjectChange(session.getCurrentProject().getProjectId(), session.getCurrentProject().getProjectName(), session.getCurrentAgentsOnSession()
							.size());
					projectChange.setNegotiationRounds(session.getNegotiationRounds());
					projectChange.setProposalsPerRound(session.getProposalsPerRound());
					IVotingAlgorithm votingAlgorithm = session.getVotingAlgorithm();
					if (null != votingAlgorithm) {
						projectChange.setVotingAlgorithm(VotingAlgorithmFactory.getEnumOfVotingAlgorithm(votingAlgorithm).name());
					}
					projects[i] = projectChange;
					++i;
				}
				return projects;
			}
		}
		return null;
	}

	/**
	 * Returns a {@link ProjectChange} instance containing a single project
	 * change. In this case a project change is when a second agent has joined
	 * the negotiation.
	 * 
	 * @return An instance of {@link ProjectChange} or null when there was no
	 *         change.
	 */
	@SuppressWarnings("unchecked")
	public ProjectChange retrieveProjectChange(int projectId) {
		ProjectChange projectChange = null;
		IProject project = null;
		Map sessionMap = Mediator.getInstance().getMediationSessionMap();

		if (null != sessionMap && !sessionMap.isEmpty()) {
			// get the information from the available sessions
			for (IProject currentProject : Mediator.getInstance().getAvailableProjects()) {
				// find the right project
				if (currentProject.getProjectId() == projectId) {
					project = currentProject;
					break;
				}
			}

			if (project != null) {
				// retrieve the change from the project only when this
				// project
				// is used in a session
				String hashMapKey = System.identityHashCode(project) + project.getProjectName();
				MediationSession session = (MediationSession) sessionMap.get(hashMapKey);
				if (null != session) {
					projectChange = new ProjectChange(session.getCurrentProject().getProjectId(), session.getCurrentProject().getProjectName(), session.getCurrentAgentsOnSession().size());
				}
			}
		}
		return projectChange;
	}

}
