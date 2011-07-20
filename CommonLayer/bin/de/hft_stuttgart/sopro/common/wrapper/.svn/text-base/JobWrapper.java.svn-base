package de.hft_stuttgart.sopro.common.wrapper;

import de.hft_stuttgart.sopro.common.project.IJob;

public class JobWrapper {
	/**
	 * The job number in the whole project.
	 */
	private int jobNumber = 0;

	/**
	 * duration of the job per period
	 */
	private int duration = 0;

	/**
	 * The list of successors of this {@link IJob}.
	 */
	private JobWrapper[] successors;

	/**
	 * The list of predecesors of this {@link IJob}.
	 */
	private JobWrapper[] predecessors;

	/**
	 * The payment of the job
	 */
	private double payment;

	/**
	 * Contains the units of each resource per period
	 */
	private int[] resources;

	/**
	 * start times of a job in the project for each resource
	 */
	private int[] startTimes;

	/**
	 * The id of the agent to which this job belongs to.
	 */
	private int agentId = 0;

	/**
	 * The id of the project to which this job belongs to.
	 */
	private int projectId = 0;

	/**
	 * Constructor taking the jobNumber as parameter.
	 * 
	 * @param jobNumber
	 *            The job number to set.
	 */
	public JobWrapper(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	/**
	 * Constructor taking the jobNumber and the projectId as parameter.
	 * 
	 * @param jobNumber
	 *            The job number to set.
	 * @param projectId
	 *            The project id to set.
	 */
	public JobWrapper(int jobNumber, int projectId) {
		this.jobNumber = jobNumber;
		this.projectId = projectId;
	}

	public int getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public JobWrapper[] getSuccessors() {
		return successors;
	}

	public void setSuccessors(JobWrapper[] successors) {
		this.successors = successors;
	}

	public JobWrapper[] getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(JobWrapper[] predecessors) {
		this.predecessors = predecessors;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public int[] getResources() {
		return resources;
	}

	public void setResources(int[] resources) {
		this.resources = resources;
	}

	public int[] getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(int[] startTimes) {
		this.startTimes = startTimes;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

}
