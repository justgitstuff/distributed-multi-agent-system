/*
 * $LastChangedRevision: 844 $ $LastChangedBy: edu $ $LastChangedDate:
 * 2009-11-08 19:34:06 +0100 (So, 08 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common
 * /project/Project.java $ $Id: Project.java 382 2009-11-18 14:57:03Z annemarie
 * $
 */
package de.hft_stuttgart.sopro.common.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hft_stuttgart.sopro.common.voting.algorithms.IVotingAlgorithm;

/**
 * The main project class which contains one or more {@link IJob} objects.
 * 
 * @author Annemarie Meissner
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class Project implements IProject {

	/**
	 * The generated serial version UID.
	 */
	private static final long serialVersionUID = -6654123976992667752L;

	/**
	 * Every project has its unique id
	 */
	private int projectId = 0;

	/**
	 * the project name is the filename of the problemdata
	 */
	private String projectName = null;

	/**
	 * Total number of jobs in a project
	 */
	private int numberOfJobs = 0;

	/**
	 * Total number of resources in a project
	 */
	private int numberOfResources = 0;

	/**
	 * max capacity for each resource
	 */
	private int[] maxCapacities = null;

	/**
	 * the jobs of the project, ordered by jobNumber
	 */
	private List<IJob> jobs = new ArrayList<IJob>();

	/**
	 * Id of the Agents which participates the project
	 */
	private List<Integer> currentAgentsOnProject = new ArrayList<Integer>(2);

	/**
	 * The voting algorithm which is used for the project
	 */
	private IVotingAlgorithm votingAlgorithm;

	/**
	 * Creates a new Project, initializes the {@link IJob} instances and adds
	 * them to the list of Jobs which can be retrieved by {@link #getJobs()}.
	 * 
	 * @param projectId
	 *            The project id to set.
	 * @param projectName
	 *            The name of the project to set.
	 * @param numberOfJobs
	 *            The number of jobs to set.
	 * @param numberOfResources
	 *            The number of resources to set.
	 */
	public Project(int projectId, String projectName, int numberOfJobs, int numberOfResources) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.numberOfJobs = numberOfJobs;
		this.numberOfResources = numberOfResources;

		// create job instances
		for (int i = 0; i < numberOfJobs; i++) {
			this.jobs.add(new Job(i + 1, projectId));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	/**
	 * {@inheritDoc}
	 */
	public IVotingAlgorithm getVotingAlgorithm() {
		return votingAlgorithm;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setVotingAlgorithm(IVotingAlgorithm votingAlgorithm) {
		this.votingAlgorithm = votingAlgorithm;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getNumberOfJobs() {
		return numberOfJobs;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNumberOfJobs(int numberOfJobs) {
		this.numberOfJobs = numberOfJobs;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getNumberOfResources() {
		return numberOfResources;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNumberOfResources(int numberOfResources) {
		this.numberOfResources = numberOfResources;
	}

	/**
	 * {@inheritDoc}
	 */
	public int[] getMaxCapacities() {
		if (null != maxCapacities) {
			return maxCapacities.clone();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMaxCapacities(int[] maxCapacities) {
		if (null != maxCapacities) {
			this.maxCapacities = maxCapacities.clone();
		} else {
			this.maxCapacities = null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Integer> getCurrentAgentsOnProject() {
		return Collections.unmodifiableList(currentAgentsOnProject);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCurrentAgentsOnProject(List<Integer> currentAgentsOnProject) {
		this.currentAgentsOnProject = currentAgentsOnProject;
	}

	/**
	 * Overwrite toString method of Object and print out all properties of a job
	 * 
	 * @param no
	 *            parameter
	 * @return returns the properties of a job as a String
	 */
	public String toString() {
		// TODO AM: need to check for null references before printing
		StringBuffer returnValue = new StringBuffer();
		returnValue.append("Projectname: " + projectName + "\n");
		returnValue.append("Number of jobs: " + String.valueOf(this.numberOfJobs) + "\n");
		returnValue.append("Number of resources: " + String.valueOf(this.numberOfResources) + "\n");

		if (maxCapacities != null) {
			returnValue.append("Max capacities of the resources: ");
			for (int maxCapacety : maxCapacities) {
				returnValue.append(String.valueOf(maxCapacety) + " ");
			}
			returnValue.append("\n");
		}

		for (IJob job : jobs) {
			returnValue.append(String.valueOf(job));
		}
		returnValue.append("\n");

		returnValue.append("Current agents on project:  \n");
		returnValue.append("agentID 1: " + String.valueOf(this.getCurrentAgentsOnProject().get(0)) + "\n");
		returnValue.append("agentID 2: " + String.valueOf(this.getCurrentAgentsOnProject().get(1)) + "\n");

		return returnValue.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public void addAgentToProject(int agentId) {
		currentAgentsOnProject.add(new Integer(agentId));
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IJob> getJobs() {
		return Collections.unmodifiableList(jobs);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setJobs(List<IJob> jobs) {
		this.jobs = jobs;
	}

	/**
	 * {@inheritDoc}
	 */
	public IJob retrieveJobFromJobNumber(int jobNumber) {
		if (null != jobs) {
			return jobs.get(jobNumber - 1);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean removeAgentFromProject(int agentId) {
		boolean wasSuccessful = false;
		if (null != currentAgentsOnProject && !currentAgentsOnProject.isEmpty()) {
			wasSuccessful = currentAgentsOnProject.remove(new Integer(agentId));
		}
		return wasSuccessful;
	}

	/**
	 * {@inheritDoc}
	 */
	public IProject clone() {
		IProject project = null;
		try {
			project = (IProject) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return project;
	}

}
