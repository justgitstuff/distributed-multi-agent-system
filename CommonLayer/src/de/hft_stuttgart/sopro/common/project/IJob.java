/*
 * $LastChangedRevision: 844 $ $LastChangedBy: edu $ $LastChangedDate:
 * 2009-11-08 19:34:06 +0100 (So, 08 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common/project/IJob.java
 * $ $Id: IJob.java 844 2010-01-10 16:16:17Z edu $
 */
package de.hft_stuttgart.sopro.common.project;

import java.io.Serializable;
import java.util.List;

import de.hft_stuttgart.sopro.common.exceptions.ResourceNotFoundException;

/**
 * The main interface for a {@link Job}.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public interface IJob extends Serializable {

	/**
	 * {@inheritDoc}
	 */
	public IJob clone();

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj);

	/**
	 * @return The duration.
	 */
	public int getDuration();

	/**
	 * @param duration
	 *            The duration to set.
	 */
	public void setDuration(int duration);

	/**
	 * @return The payment.
	 */
	public double getPayment();

	/**
	 * @param payment
	 *            The payment to set.
	 */
	public void setPayment(double payment);

	/**
	 * @return The resources.
	 */
	public int[] getResources();

	/**
	 * @param resourceIndex
	 *            the resource index for which you want the units per period of
	 *            the job
	 * @return units per period.
	 */
	public int retrieveResourceFromIndex(int resourceIndex) throws ResourceNotFoundException;

	/**
	 * @param resourceIndex
	 *            the resource index for which you want the starttime of the job
	 * @return starttime.
	 */
	public int retrieveStartTimeFromIndex(int resourceIndex) throws ResourceNotFoundException;

	/**
	 * @param startTime
	 *            the starttime to set on index resIndex resIndex the resource
	 *            index for which you want the starttime of the job
	 */
	public void adjustStartTimeForIndex(int startTime, int resourceIndex) throws ResourceNotFoundException;

	/**
	 * @param resourceIndex
	 *            the resource index for which you want the endtime of the job
	 * @return endtime.
	 */
	public int retrieveEndTimeFromIndex(int resourceIndex) throws ResourceNotFoundException;

	/**
	 * Sets the resources and initializes the startTimes of the {@link IJob}
	 * instance. The startTimes can be retrieved by calling
	 * {@link #getStartTimes()}.
	 * 
	 * @param resources
	 *            The resources to set.
	 */
	public void setResources(int[] resources);

	/**
	 * @return the agentId of that {@link IJob}.
	 */
	public int getAgentId();

	/**
	 * @param agentId
	 *            The agentId to set.
	 */
	public void setAgentId(int agentId);

	/**
	 * @return The projectId of the project.
	 */
	public int getProjectId();

	/**
	 * @param projectId
	 *            The projectId to set.
	 */
	public void setProjectId(int projectId);

	/**
	 * @param successors
	 *            The successors to set.
	 */
	public void setSuccessors(List<IJob> successors);

	/**
	 * @return returns the predecessors of the job
	 */
	public List<IJob> getPredecessors();

	/**
	 * @return returns the successors of the job
	 */
	public List<IJob> getSuccessors();

	/**
	 * @param predecessors
	 *            The predecessors of a job to set.
	 */
	public void setPredecessors(List<IJob> predecessors);

	/**
	 * @return returns the member of the job with the job number.
	 */
	public int getJobNumber();

	/**
	 * @param jobNumber
	 *            The job number to set.
	 * @return returns the member of the job with the job number.
	 */
	public void setJobNumber(int jobNumber);

	/**
	 * Adds a job as predecessor to the current job.
	 * 
	 * @param predecessor
	 *            The predecessor to add.
	 */
	public void addPredecessor(IJob predecessor);

	/**
	 * Adds a job as successor to the current job.
	 * 
	 * @param successor
	 *            The successor to add.
	 */
	public void addSuccessor(IJob successor);

	/**
	 * @return the startTimes of a job. One start time for each resource
	 */
	public int[] getStartTimes();

}
