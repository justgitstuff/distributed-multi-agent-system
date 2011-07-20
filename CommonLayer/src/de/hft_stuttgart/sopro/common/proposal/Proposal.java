/**
 * 
 */
package de.hft_stuttgart.sopro.common.proposal;

import java.util.ArrayList;
import java.util.List;

import de.hft_stuttgart.sopro.common.exceptions.JobInPermutationNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.StarttimeNotFoundException;
import de.hft_stuttgart.sopro.common.project.IJob;

/**
 * The class contains all attributes of a Proposal. A Proposal contains: 1.) a
 * permutation (job order) of the jobs 2.) a decoded permutation, which means a
 * list with the start times of the jobs for each resource. For example, for one
 * resource one start time list (the inner list of integers is a starttime list)
 * the start time: of job nr 1 is on index 0 in the start time list, the start
 * time of job nr 2 is on index 1 in the start time list, etc. 3.) the points
 * for a evaluation of the proposal with respect to the total cash value
 * 
 * @author Annemarie Meissner meissner.annemarie@gmx.de
 */
public class Proposal implements IProposal {

	/**
	 * The permutation, one job order of the project
	 */
	private List<IJob> permutation = null;

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
	private List<List<Integer>> starttimes = null;

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
	public Proposal(int numberOfJobs, int numberOfResources) {

		this.numberOfJobs = numberOfJobs;
		this.numberOfResources = numberOfResources;

		// allocate memory of the permutation and the starttimes
		// a permutation contains numOfJobs jobs
		this.permutation = new ArrayList<IJob>(numberOfJobs);

		// a starttimes array contains for each resource one intager array with
		// starttimes allocate memory of the outer array
		this.starttimes = new ArrayList<List<Integer>>(numberOfResources);
		// allocate memory of the inner array
		for (int i = 0; i < numberOfResources; i++) {
			// Each starttime array contains for each job one starttime -->
			// malloc numOfJobs in the inner array
			List<Integer> starttimeArray = new ArrayList<Integer>(numberOfJobs);
			this.starttimes.add(starttimeArray);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public IProposal clone() {
		IProposal clone = new Proposal(this.numberOfJobs, this.numberOfResources);
		clone.setEvaluationPoints(this.evaluationPoints);
		clone.setPermutation(this.permutation);
		clone.setStarttimes(this.starttimes);

		return clone;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + evaluationPoints;
		result = prime * result + numberOfJobs;
		result = prime * result + numberOfResources;
		result = prime * result + ((permutation == null) ? 0 : permutation.hashCode());
		result = prime * result + ((starttimes == null) ? 0 : starttimes.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proposal other = (Proposal) obj;
		if (evaluationPoints != other.evaluationPoints)
			return false;
		if (numberOfJobs != other.numberOfJobs)
			return false;
		if (numberOfResources != other.numberOfResources)
			return false;
		if (permutation == null) {
			if (other.permutation != null)
				return false;
		} else if (!permutation.equals(other.permutation))
			return false;
		if (starttimes == null) {
			if (other.starttimes != null)
				return false;
		} else if (!starttimes.equals(other.starttimes))
			return false;
		return true;
	}

	/**
	 * @param index
	 *            the index of the Job in the permutation
	 * @return the permutation
	 * @throws JobInPermutationNotFoundException
	 */
	public IJob getPermutation(int index) throws JobInPermutationNotFoundException {
		if (index < 0 || index >= permutation.size()) {
			throw new JobInPermutationNotFoundException();
		}

		return this.permutation.get(index);
	}

	/**
	 * @return the permutation size
	 */
	public int getPermutationSize() {
		return this.permutation.size();
	}

	/**
	 * @param permutation
	 *            the permutation to set
	 */
	public void setPermutation(List<IJob> permutation) {
		// Copy all the jobs, so that changes of the permutation reference to
		// not touch the member permutation
		for (IJob job : permutation) {
			this.permutation.add(job.clone());
		}
	}

	/**
	 * The method returns the predecessors instances of the permutation of the
	 * given job
	 * 
	 * @param job
	 *            the job for which the predecessors should be returned
	 * @return the predecessor instances of the permutation of the given job
	 */
	public List<IJob> retrievePermutationJobPredecessorsFromJob(IJob job) {
		List<IJob> predecessors = new ArrayList<IJob>();
		for (IJob predecessor : job.getPredecessors()) {
			IJob predecessorInstance = this.getPermutationJobFromJobNr(predecessor.getJobNumber());
			if (predecessorInstance != null) {
				predecessors.add(predecessorInstance);
			}
		}
		return predecessors;
	}

	private IJob getPermutationJobFromJobNr(int jobNr) {
		for (IJob job : this.permutation) {
			if (job.getJobNumber() == jobNr) {
				return job;
			}
		}

		return null;
	}

	/**
	 * @param resourceIndex
	 *            the index of the resource for which you want the starttime
	 * @param jobIndex
	 *            the index of the job for which you want to get the starttime
	 * @return the starttime in the project plan
	 * @throws StarttimeNotFoundException
	 */
	public int getStarttime(int resourceIndex, int jobIndex) throws StarttimeNotFoundException {
		if (resourceIndex < 0 || resourceIndex >= numberOfResources || jobIndex < 0 || jobIndex >= numberOfJobs) {
			throw new StarttimeNotFoundException();
		}
		List<Integer> resourceStarttimes = this.starttimes.get(resourceIndex);
		return (resourceStarttimes.get(jobIndex)).intValue();
	}

	/**
	 * @return the number of resources for which a starttime array is available
	 */
	public int getNumberOfResourceStarttimes() {
		return this.starttimes.size();
	}

	/**
	 * @param starttimes
	 *            the starttimes to set
	 */
	public void setStarttimes(List<List<Integer>> starttimes) {
		// Copy all the starttimes, so that changes of the starttimes reference
		// to not touch the member starttimes
		for (int i = 0; i < numberOfResources; i++) {
			this.starttimes.set(i, starttimes.get(i));
		}
	}

	/**
	 * @return the evaluationPoints
	 */
	public int getEvaluationPoints() {
		return evaluationPoints;
	}

	/**
	 * @param evaluationPoints
	 *            the evaluationPoints to set
	 */
	public void setEvaluationPoints(int evaluationPoints) {
		this.evaluationPoints = evaluationPoints;
	}

	/**
	 * The method returns the makespans of all resouces for the proposal
	 * 
	 * @return the makespan of each resource for the proposal
	 */
	public int[] calculateMakespans() {
		// Get the last job of the permutation
		IJob job = this.permutation.get(numberOfJobs - 1);

		int[] makespans = new int[this.numberOfResources];
		try {
			for (int i = 0; i < this.numberOfResources; i++) {
				makespans[i] = this.getStarttime(i, job.getJobNumber() - 1) + job.getDuration();
			}

		} catch (StarttimeNotFoundException e) {
			e.printStackTrace();
		}

		return makespans;
	}

}
