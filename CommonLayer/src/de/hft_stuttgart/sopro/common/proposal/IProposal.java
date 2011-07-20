/*
 * $LastChangedRevision: 261 $ $LastChangedBy: edu $ $LastChangedDate:
 * 2009-11-08 19:34:06 +0100 (So, 08 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common/project/IJob.java
 * $ $Id: IJob.java 261 2009-11-09 16:59:29Z edu $
 */
package de.hft_stuttgart.sopro.common.proposal;

import java.util.List;

import de.hft_stuttgart.sopro.common.exceptions.JobInPermutationNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.StarttimeNotFoundException;
import de.hft_stuttgart.sopro.common.project.IJob;

/**
 * The main interface for a {@link Proposal}.
 * 
 * @author Annemarie Meissner
 */
public interface IProposal {

	/**
	 * {@inheritDoc}
	 */
	public IProposal clone();

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj);

	/**
	 * @param index
	 *            the index of the Job in the permutation
	 * @return the permutation
	 * @throws JobInPermutationNotFoundException
	 */
	public IJob getPermutation(int index) throws JobInPermutationNotFoundException;

	/**
	 * @return the permutation size
	 */
	public int getPermutationSize();

	/**
	 * @param permutation
	 *            the permutation to set
	 */
	public void setPermutation(List<IJob> permutation);

	/**
	 * The method returns the predecessors instances of the permutation of the
	 * given job
	 * 
	 * @param job
	 *            the job for which the predecessors should be returned
	 * @return the predecessor instances of the permutation of the given job
	 */
	public List<IJob> retrievePermutationJobPredecessorsFromJob(IJob job);

	/**
	 * @param resourceIndex
	 *            the index of the resource for which you want the starttime
	 * @param jobIndex
	 *            the index of the job for which you want to get the starttime
	 * @return the starttime in the project plan
	 * @throws StarttimeNotFoundException
	 */
	public int getStarttime(int resourceIndex, int jobIndex) throws StarttimeNotFoundException;

	/**
	 * @return the number of resources for which a starttime array is available
	 */
	public int getNumberOfResourceStarttimes();

	/**
	 * @param starttimes
	 *            the starttimes to set
	 */
	public void setStarttimes(List<List<Integer>> starttimes);

	/**
	 * @return the evaluationPoints
	 */
	public int getEvaluationPoints();

	/**
	 * @param evaluationPoints
	 *            the evaluationPoints to set
	 */
	public void setEvaluationPoints(int evaluationPoints);

	/**
	 * The method returns the makespans of all resouces for the proposal
	 * 
	 * @return the makespan of each resource for the proposal
	 */
	public int[] calculateMakespans();
}
