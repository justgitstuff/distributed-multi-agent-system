/*
 * $LastChangedRevision: 844 $ $LastChangedBy: edu $ $LastChangedDate:
 * 2009-11-08 19:34:06 +0100 (So, 08 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common
 * /project/IProject.java $ $Id: IProject.java 382 2009-11-18 14:57:03Z
 * annemarie $
 */
package de.hft_stuttgart.sopro.common.project;

import java.io.Serializable;
import java.util.List;

import de.hft_stuttgart.sopro.common.voting.algorithms.IVotingAlgorithm;

/**
 * The main interface for all projects.
 * 
 * @author Eduard Tudenhoefner
 */
public interface IProject extends Cloneable, Serializable {

	/**
	 * @return the projectID
	 */
	public int getProjectId();

	/**
	 * @param projectId
	 *            the projectID to set
	 */
	public void setProjectId(int projectId);

	/**
	 * @return the projectName
	 */
	public String getProjectName();

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName);

	/**
	 * @return the numberOfJobs
	 */
	public int getNumberOfJobs();

	/**
	 * @param numberOfJobs
	 *            the numberOfJobs to set
	 */
	public void setNumberOfJobs(int numberOfJobs);

	/**
	 * @return the numberOfResources
	 */
	public int getNumberOfResources();

	/**
	 * @param numberOfResources
	 *            the numberOfResources to set
	 */
	public void setNumberOfResources(int numberOfResources);

	/**
	 * @return the maxCapaceties
	 */
	public int[] getMaxCapacities();

	/**
	 * @param maxCapacities
	 *            the maxCapaceties to set
	 */
	public void setMaxCapacities(int[] maxCapacities);

	/**
	 * @return the job with the jobNr
	 */
	public IJob retrieveJobFromJobNumber(int jobNumber);

	/**
	 * @return the currentAgentsOnProject
	 */
	public List<Integer> getCurrentAgentsOnProject();

	/**
	 * Sets the current agents on the project.
	 * 
	 * @param currentAgentsOnProject
	 *            The list to set.
	 */
	public void setCurrentAgentsOnProject(List<Integer> currentAgentsOnProject);

	/**
	 * @return The currently used {@link IVotingAlgorithm} on that project.
	 */
	public IVotingAlgorithm getVotingAlgorithm();

	/**
	 * Sets the {@link IVotingAlgorithm} to be used for that project.
	 * 
	 * @param votingAlgorithm
	 *            The {@link IVotingAlgorithm} to set.
	 */
	public void setVotingAlgorithm(IVotingAlgorithm votingAlgorithm);

	/**
	 * Adds one agent to the current agents on project.
	 * 
	 * @param agentId
	 *            The id of an agent which joins to a project.
	 */
	public void addAgentToProject(int agentId);

	/**
	 * Removes an agent from the from the project.
	 * 
	 * @param agentId
	 *            The id of the Agent to remove.
	 * @return True if removing the Agent from the project was successful,
	 *         otherwise false.
	 */
	public boolean removeAgentFromProject(int agentId);

	/**
	 * @return Returns a list of {@link IJob} instances.
	 */
	public List<IJob> getJobs();

	/**
	 * Sets the list of {@link IJob} instances.
	 * 
	 * @param jobs
	 */
	public void setJobs(List<IJob> jobs);

	public IProject clone();

}
