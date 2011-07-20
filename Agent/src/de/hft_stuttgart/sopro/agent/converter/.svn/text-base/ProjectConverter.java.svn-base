package de.hft_stuttgart.sopro.agent.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmEnum;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmFactory;
import de.hft_stuttgart.sopro.agent.converter.JobConverter;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceStub.ProjectWrapper;

public class ProjectConverter {

	/**
	 * Default constructor.
	 */
	public ProjectConverter() {
	}

	/**
	 * Converts an array of {@link ProjectWrapper} instances to a list of
	 * {@link IProject} instances.
	 * 
	 * @param sourceProjects
	 *            The {@link ProjectWrapper} array to convert.
	 * @return A list of {@link IProject} instances or null.
	 */
	public List<IProject> fromProjectWrappersToProjects(ProjectWrapper[] sourceProjects) {
		List<IProject> projects = null;
		if (null != sourceProjects) {
			projects = new ArrayList<IProject>(sourceProjects.length);
			for (int i = 0; i < sourceProjects.length; i++) {
				projects.add(fromProjectWrapperToProject(sourceProjects[i]));
			}
		}
		return projects;
	}

	/**
	 * Converts an int array to a list of {@link Integer} values.
	 * 
	 * @param currentAgentsOnProject
	 *            The array to convert.
	 * @return A list of {@link Integer} values.
	 */
	@SuppressWarnings("unchecked")
	private List<Integer> convertCurrentAgentsOnProject(int[] currentAgentsOnProject) {
		List<Integer> result = Collections.EMPTY_LIST;
		if (null != currentAgentsOnProject) {
			result = new ArrayList<Integer>(currentAgentsOnProject.length);
			for (int i = 0; i < currentAgentsOnProject.length; i++) {
				result.add(new Integer(currentAgentsOnProject[i]));
			}
		}
		return result;
	}

	/**
	 * Converts a {@link ProjectWrapper} instance to an {@link IProject}
	 * instance.
	 * 
	 * @param sourceProject
	 *            The {@link ProjectWrapper} to convert.
	 * @return An {@link IProject} instance or null.
	 */
	private IProject fromProjectWrapperToProject(ProjectWrapper sourceProject) {
		IProject project = null;
		if (null != sourceProject) {
			project = new de.hft_stuttgart.sopro.common.project.Project(sourceProject.getProjectId(), sourceProject.getProjectName(), sourceProject.getNumberOfJobs(), sourceProject
					.getNumberOfResources());
			project.setMaxCapacities(sourceProject.getMaxCapacities());
			if (null != sourceProject.getVotingAlgorithm()) {
				project.setVotingAlgorithm(VotingAlgorithmFactory.createInstance(VotingAlgorithmEnum.valueOf(sourceProject.getVotingAlgorithm())));
			}
			project.setJobs(new JobConverter().fromJobWrapperArrayToJobList(sourceProject.getJobs()));
			project.setCurrentAgentsOnProject(convertCurrentAgentsOnProject(sourceProject.getCurrentAgentsOnProject()));
		}
		return project;
	}

}
