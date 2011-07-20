package de.hft_stuttgart.sopro.mediator.converter;

import java.util.ArrayList;
import java.util.List;

import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmEnum;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmFactory;
import de.hft_stuttgart.sopro.common.voting.algorithms.ApprovalVotingAlgorithm;
import de.hft_stuttgart.sopro.common.voting.algorithms.BordaVotingAlgorithm;
import de.hft_stuttgart.sopro.common.voting.algorithms.CopelandVotingAlgorithm;
import de.hft_stuttgart.sopro.common.voting.algorithms.IVotingAlgorithm;
import de.hft_stuttgart.sopro.common.voting.algorithms.PluralityVotingAlgorithm;
import de.hft_stuttgart.sopro.common.voting.algorithms.ScoringVotingAlgorithm;
import de.hft_stuttgart.sopro.common.wrapper.ProjectWrapper;

/**
 * This class mainly converts from {@link IProject} instances to
 * {@link ProjectWrapper} instances and vice versa.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class ProjectConverter {

	/**
	 * Default constructor.
	 */
	public ProjectConverter() {
	}

	/**
	 * Converts a list of {@link IProject} instances to an array of
	 * {@link ProjectWrapper} instances.
	 * 
	 * @param sourceProjects
	 *            The {@link IProject} list to convert.
	 * @return An array containing {@link ProjectWrapper} instances or null.
	 */
	public ProjectWrapper[] fromProjectsToProjectWrappers(List<IProject> sourceProjects) {
		ProjectWrapper[] projects = null;
		if (null != sourceProjects) {
			projects = new ProjectWrapper[sourceProjects.size()];
			for (int i = 0; i < sourceProjects.size(); i++) {
				projects[i] = fromProjectToProjectWrapper(sourceProjects.get(i));
			}
		}
		return projects;
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
	 * Converts a single {@link IProject} instance to a {@link ProjectWrapper}.
	 * 
	 * @param sourceProject
	 *            The {@link IProject} instance to convert.
	 * @return An instance of {@link ProjectWrapper} or null.
	 */
	private ProjectWrapper fromProjectToProjectWrapper(IProject sourceProject) {
		ProjectWrapper project = null;
		if (null != sourceProject) {
			project = new ProjectWrapper(sourceProject.getProjectId(), sourceProject.getProjectName(), sourceProject.getNumberOfJobs(), sourceProject.getNumberOfResources());
			project.setMaxCapacities(sourceProject.getMaxCapacities());
			project.setCurrentAgentsOnProject(convertCurrentAgentsOnProject(sourceProject.getCurrentAgentsOnProject()));
			VotingAlgorithmEnum votingAlgorithmEnum = fromAlgorithmToEnum(sourceProject.getVotingAlgorithm());
			if (null != votingAlgorithmEnum) {
				project.setVotingAlgorithm(votingAlgorithmEnum.name());
			}
			project.setJobs(new JobConverter().fromJobListToJobWrapperArray(sourceProject.getJobs()));
		}
		return project;
	}

	/**
	 * Converts a list of {@link Integer} values to an int array.
	 * 
	 * @param currentAgentsOnProject
	 *            The list to convert.
	 * @return An array of int values or null.
	 */
	private int[] convertCurrentAgentsOnProject(List<Integer> currentAgentsOnProject) {
		int[] result = null;
		if (null != currentAgentsOnProject) {
			result = new int[currentAgentsOnProject.size()];
			for (int i = 0; i < currentAgentsOnProject.size(); i++) {
				result[i] = currentAgentsOnProject.get(i).intValue();
			}
		}
		return result;
	}

	/**
	 * Converts an int array to a list of {@link Integer} values.
	 * 
	 * @param currentAgentsOnProject
	 *            The array to convert.
	 * @return A list of {@link Integer} values.
	 */
	private List<Integer> convertCurrentAgentsOnProject(int[] currentAgentsOnProject) {
		List<Integer> result = null;
		if (null != currentAgentsOnProject) {
			result = new ArrayList<Integer>(currentAgentsOnProject.length);
			for (int i = 0; i < currentAgentsOnProject.length; i++) {
				result.add(new Integer(currentAgentsOnProject[i]));
			}
		}
		return result;
	}

	/**
	 * Returns the {@link VotingAlgorithmEnum} of a {@link IVotingAlgorithm}
	 * instance.
	 * 
	 * @param votingAlgorithm
	 *            The algorithm where to get the enum from.
	 * @return An {@link VotingAlgorithmEnum} of a {@link IVotingAlgorithm}
	 *         instance.
	 */
	private VotingAlgorithmEnum fromAlgorithmToEnum(IVotingAlgorithm votingAlgorithm) {
		if (votingAlgorithm instanceof BordaVotingAlgorithm) {
			return VotingAlgorithmEnum.BORDA;
		} else if (votingAlgorithm instanceof ApprovalVotingAlgorithm) {
			return VotingAlgorithmEnum.APPROVAL;
		} else if (votingAlgorithm instanceof CopelandVotingAlgorithm) {
			return VotingAlgorithmEnum.COPELAND;
		} else if (votingAlgorithm instanceof PluralityVotingAlgorithm) {
			return VotingAlgorithmEnum.PLURALITY;
		} else if (votingAlgorithm instanceof ScoringVotingAlgorithm) {
			return VotingAlgorithmEnum.SCORING;
		}
		return null;
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
			project.setVotingAlgorithm(VotingAlgorithmFactory.createInstance(VotingAlgorithmEnum.valueOf(sourceProject.getVotingAlgorithm())));
			project.setJobs(new JobConverter().fromJobWrapperArrayToJobList(sourceProject.getJobs()));
			project.setCurrentAgentsOnProject(convertCurrentAgentsOnProject(sourceProject.getCurrentAgentsOnProject()));
		}
		return project;
	}
}
