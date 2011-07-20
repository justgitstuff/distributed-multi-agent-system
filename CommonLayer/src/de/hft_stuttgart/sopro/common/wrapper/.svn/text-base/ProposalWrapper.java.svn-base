package de.hft_stuttgart.sopro.common.wrapper;

/**
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class ProposalWrapper {
	/**
	 * The permutation, one job order of the project
	 */
	private JobWrapper[] permutation = null;

	/**
	 * The number of jobs of the project
	 */
	private int numberOfJobs = 0;

	/**
	 * The number of resources of the project
	 */
	private int numberOfResources = 0;

	/**
	 * The decoded permutation, the starttimes of the jobs in the perutation for
	 * each resource
	 */
	private int[][] startTimes = null;

	/**
	 * The score with a evaluation of the proposal with respect to the total
	 * cash value
	 */
	private int evaluationPoints = 0;

	/**
	 * The constructor of a proposal has to know the number of jobs and
	 * resources in a project.
	 * 
	 * @param numberOfJobs
	 *            the number of jobs in a project for which a proposal should be
	 *            generated
	 * @param numberOfResources
	 *            the number of jobs in a project for which a proposal should be
	 *            generated
	 */
	public ProposalWrapper(int numberOfJobs, int numberOfResources) {
		this.numberOfJobs = numberOfJobs;
		this.numberOfResources = numberOfResources;
	}

	public JobWrapper[] getPermutation() {
		return permutation;
	}

	public void setPermutation(JobWrapper[] permutation) {
		this.permutation = permutation;
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

	public int[][] getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(int[][] startTimes) {
		this.startTimes = startTimes;
	}

	public int getEvaluationPoints() {
		return evaluationPoints;
	}

	public void setEvaluationPoints(int evaluationPoints) {
		this.evaluationPoints = evaluationPoints;
	}
}
