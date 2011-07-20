package de.hft_stuttgart.sopro.common.wrapper;

/**
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class ProjectWrapper {
	/**
	 * Every project has its unique id
	 */
	private int projectId = 0;

	/**
	 * the project name is the filename of the problemdata
	 */
	private String projectName;

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
	private int[] maxCapacities;

	/**
	 * the jobs of the project, ordered by jobNumber
	 */
	private JobWrapper[] jobs;

	/**
	 * Id of the Agents which participates the project
	 */
	private int[] currentAgentsOnProject;

	/**
	 * The voting algorithm which is used for the project
	 */
	private String votingAlgorithm;

	/**
	 * creates a empty project
	 */
	public ProjectWrapper(int projectId, String projectName, int numberOfJobs, int numberOfResources) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.numberOfJobs = numberOfJobs;
		this.numberOfResources = numberOfResources;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getNumberOfJobs() {
		return numberOfJobs;
	}

	public void setNumberOfJobs(int numberOfJobs) {
		this.numberOfJobs = numberOfJobs;
	}

	public int getNumberOfResources() {
		return numberOfResources;
	}

	public void setNumberOfResources(int numberOfResources) {
		this.numberOfResources = numberOfResources;
	}

	public int[] getMaxCapacities() {
		return maxCapacities;
	}

	public void setMaxCapacities(int[] maxCapacities) {
		this.maxCapacities = maxCapacities;
	}

	public JobWrapper[] getJobs() {
		return jobs;
	}

	public void setJobs(JobWrapper[] jobs) {
		this.jobs = jobs;
	}

	public int[] getCurrentAgentsOnProject() {
		return currentAgentsOnProject;
	}

	public void setCurrentAgentsOnProject(int[] currentAgentsOnProject) {
		this.currentAgentsOnProject = currentAgentsOnProject;
	}

	public String getVotingAlgorithm() {
		return votingAlgorithm;
	}

	public void setVotingAlgorithm(String votingAlgorithm) {
		this.votingAlgorithm = votingAlgorithm;
	}

}
