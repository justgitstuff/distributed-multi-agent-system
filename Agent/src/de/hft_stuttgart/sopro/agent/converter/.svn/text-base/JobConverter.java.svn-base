package de.hft_stuttgart.sopro.agent.converter;

import java.util.ArrayList;
import java.util.List;
//TODO: change imports to
//import de.hft_stuttgart.sopro.common.wrapper.JobWrapper;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceStub.JobWrapper;
import de.hft_stuttgart.sopro.common.exceptions.ResourceNotFoundException;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.Job;

/**
 * This class mainly converts from {@link IJob} instances to {@link JobWrapper}
 * instances and vice versa.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 */
public class JobConverter {

	/**
	 * Default constructor.
	 */
	public JobConverter() {
	}

	/**
	 * Converts a {@link IJob} list to a {@link JobWrapper} array.
	 * 
	 * @param sourceJobs
	 *            The jobs to convert.
	 * @return An array with {@link JobWrapper} objects or null.
	 */
	//TODO:  remove methods fromJobListToJobWrapperArray and fillJobDataInJobWrapper
	//		 because they are now in the JobWrapperConverter class
	public JobWrapper[] fromJobListToJobWrapperArray(List<IJob> sourceJobs) {
		JobWrapper[] jobs = null;
		if (null != sourceJobs) {
			int numberOfJobs = sourceJobs.size();
			jobs = new JobWrapper[numberOfJobs];
			// create job instances
			for (int i = 0; i < numberOfJobs; i++) {
				jobs[i] = new JobWrapper();
			}

			// Fill the job instances with information
			this.fillJobDataInJobWrapper(sourceJobs, jobs);

		}
		return jobs;

	}

	/**
	 * The method fills the JobWrapper instances of the jobWrapper array with
	 * the data of the given job list
	 * 
	 * @param sourceJobs
	 *            the job list which should be converted in a jobWrapper array
	 * @param jobWrapperArray
	 *            the array which contain the jobWrapper instances which should
	 *            be filled with the data fo the given job list
	 */
	private void fillJobDataInJobWrapper(List<IJob> sourceJobs, JobWrapper[] jobWrapperArray) {
		int numberOfJobs = sourceJobs.size();
		for (int i = 0; i < numberOfJobs; i++) {
			IJob jobSource = sourceJobs.get(i);
			JobWrapper jobWrapper = jobWrapperArray[i];

			// Set the data in the jobWrapper
			jobWrapper.setAgentId(jobSource.getAgentId());
			jobWrapper.setDuration(jobSource.getDuration());
			jobWrapper.setJobNumber(jobSource.getJobNumber());
			jobWrapper.setPayment(jobSource.getPayment());
			jobWrapper.setProjectId(jobSource.getProjectId());
			jobWrapper.setResources(jobSource.getResources());
			jobWrapper.setStartTimes(jobSource.getStartTimes());

			// // Fill an successor array with the wrapper
			// // instances and set it to the current wrapper instance
			// List<IJob> successorsSource = jobSource.getSuccessors();
			// int numOfSuccessors = successorsSource.size();
			// JobWrapper[] successorsWrapper = new JobWrapper[numOfSuccessors];
			// for (int j = 0; j < numOfSuccessors; j++) {
			// IJob successor = successorsSource.get(j);
			// int jobNr = successor.getJobNumber();
			//
			// // Get the jobWrapper Instance with the given jobNr --> index =
			// // jobNr - 1
			// JobWrapper successorWrapper = jobWrapperArray[jobNr - 1];
			//
			// // Add the successor wrapper in the successor wrapper array
			// successorsWrapper[j] = successorWrapper;
			// }
			// jobWrapper.setSuccessors(successorsWrapper);
			//
			// // Fill an predecessor array with the wrapper
			// // instances and set it to the current wrapper instance
			// List<IJob> predecessorSource = jobSource.getPredecessors();
			// int numOfPredecessors = predecessorSource.size();
			// JobWrapper[] predecessorsWrapper = new
			// JobWrapper[numOfPredecessors];
			// for (int j = 0; j < numOfPredecessors; j++) {
			// IJob predecessor = predecessorSource.get(j);
			// int jobNr = predecessor.getJobNumber();
			//
			// // Get the jobWrapper Instance with the given jobNr --> index =
			// // jobNr - 1
			// JobWrapper predecessorWrapper = jobWrapperArray[jobNr - 1];
			//
			// // Add the predecessor wrapper in the predecessor wrapper array
			// predecessorsWrapper[j] = predecessorWrapper;
			// }
			// jobWrapper.setPredecessors(predecessorsWrapper);
		}
	}

	/**
	 * Converts a {@link JobWrapper} array to an {@link IJob} list.
	 * 
	 * @param sourceJobs
	 *            The jobs to convert.
	 * @return A list containing {@link IJob} instances or null.
	 */
	public List<IJob> fromJobWrapperArrayToJobList(JobWrapper[] sourceJobs) {
		List<IJob> jobs = null;
		if (null != sourceJobs) {
			int numberOfJobs = sourceJobs.length;
			jobs = new ArrayList<IJob>(numberOfJobs);

			// create job instances
			for (int i = 0; i < numberOfJobs; i++) {
				jobs.add(new Job(i + 1));
			}

			// Fill the job instances with information
			this.fillJobWrapperDataInJob(sourceJobs, jobs);

		}
		return jobs;
	}

	/**
	 * The method fills the Job instances of the job list with the data of the
	 * given job wrapper array
	 * 
	 * @param sourceJobs
	 *            the job list which should be converted in a jobWrapper array
	 * @param jobWrapperArray
	 *            the array which contain the jobWrapper instances which should
	 *            be filled with the data fo the given job list
	 */
	private void fillJobWrapperDataInJob(JobWrapper[] jobWrapperSource, List<IJob> jobList) {
		int numberOfJobs = jobWrapperSource.length;
		for (int i = 0; i < numberOfJobs; i++) {
			JobWrapper wrapperSource = jobWrapperSource[i];
			IJob jobDest = jobList.get(i);

			// Set the data in the jobWrapper
			jobDest.setAgentId(wrapperSource.getAgentId());
			jobDest.setDuration(wrapperSource.getDuration());
			jobDest.setJobNumber(wrapperSource.getJobNumber());
			jobDest.setPayment(wrapperSource.getPayment());
			jobDest.setProjectId(wrapperSource.getProjectId());
			jobDest.setResources(wrapperSource.getResources());

			// Set the start times of the in the job
			int[] starttimesArray = wrapperSource.getStartTimes();
			if (starttimesArray != null) {
				int numOfRes = starttimesArray.length;
				for (int j = 0; j < numOfRes; j++) {
					try {
						jobDest.adjustStartTimeForIndex(starttimesArray[j], j);
					} catch (ResourceNotFoundException e) {
						e.printStackTrace();
					}
				}
			}

			// // Fill an successor array with the wrapper
			// // instances and set it to the current wrapper instance
			// JobWrapper[] successorsWrapperSource =
			// wrapperSource.getSuccessors();
			// if(successorsWrapperSource!=null){
			// int numOfSuccessors = successorsWrapperSource.length;
			// List<IJob> successorsJobDest = new
			// ArrayList<IJob>(numOfSuccessors);
			// for (int j = 0; j < numOfSuccessors; j++) {
			// // Get the jobWrapper Instance with of the current index
			// JobWrapper successorWrapper = successorsWrapperSource[j];
			// int jobNr = successorWrapper.getJobNumber();
			//					
			// //Get the job of the given JobNr from the destJob list
			// IJob successor = jobList.get(jobNr - 1);
			//	
			// // Add the successor in the successor list
			// successorsJobDest.add(successor);
			// }
			// jobDest.setSuccessors(successorsJobDest);
			// }
			// else{
			// jobDest.setSuccessors(Collections.EMPTY_LIST);
			// }
			//
			// // Fill an predecessor array with the wrapper
			// // instances and set it to the current wrapper instance
			// JobWrapper[] predecessorsWrapperSource =
			// wrapperSource.getPredecessors();
			// if(predecessorsWrapperSource!=null){
			// int numOfPredecessors = predecessorsWrapperSource.length;
			// List<IJob> predeccessorsJobDest = new
			// ArrayList<IJob>(numOfPredecessors);
			// for (int j = 0; j < numOfPredecessors; j++) {
			// // Get the jobWrapper Instance with of the current index
			// JobWrapper predeccessorWrapper = predecessorsWrapperSource[j];
			// int jobNr = predeccessorWrapper.getJobNumber();
			//					
			// //Get the job of the given JobNr from the destJob list
			// IJob predeccessor = jobList.get(jobNr - 1);
			//	
			// // Add the successor in the successor list
			// predeccessorsJobDest.add(predeccessor);
			// }
			// jobDest.setPredecessors(predeccessorsJobDest);
			// }
			// else{
			// jobDest.setPredecessors(Collections.EMPTY_LIST);
			// }
		}
	}

	/**
	 * Compare all JobWrapper attributes with the Job attributes
	 * 
	 * @param wrapperObj
	 *            the jobWrapper object
	 * @param jobObj
	 *            the job object
	 * @return true if all attributes of the job objects have the same value as
	 *         the attributes of the job wrapper object
	 */
	public boolean compareJobWrapperWithJob(JobWrapper wrapperObj, IJob jobObj) {

		if (wrapperObj == null && jobObj == null) {
			return true;
		} else if (wrapperObj != null && jobObj == null) {
			return false;
		} else if (wrapperObj == null && jobObj != null) {
			return false;
		} else {

			// Check agentId
			if (wrapperObj.getAgentId() != jobObj.getAgentId()) {
				return false;
			}
			// Check duration
			if (wrapperObj.getDuration() != jobObj.getDuration()) {
				return false;
			}
			// Check job number
			if (wrapperObj.getJobNumber() != jobObj.getJobNumber()) {
				return false;
			}
			// Check payment
			if (wrapperObj.getPayment() != jobObj.getPayment()) {
				return false;
			}
			// Check project id
			if (wrapperObj.getProjectId() != jobObj.getProjectId()) {
				return false;
			}

			// Check resources
			int[] resourcesWrapper = wrapperObj.getResources();
			int[] resourcesJob = jobObj.getResources();
			if (resourcesWrapper != null && resourcesJob != null) {
				int numOfRes = resourcesJob.length;
				if (numOfRes != resourcesWrapper.length) {
					return false;
				}

				for (int i = 0; i < numOfRes; i++) {
					if (resourcesJob[i] != resourcesWrapper[i]) {
						return false;
					}
				}
			} else {
				if (resourcesWrapper != resourcesJob) {
					return false;
				}
			}

			// Check start times
			int[] starttimesWrapper = wrapperObj.getStartTimes();
			int[] starttimesJob = jobObj.getStartTimes();
			if (starttimesWrapper != null && starttimesJob != null) {
				int numOfRes = starttimesJob.length;
				if (numOfRes != starttimesWrapper.length) {
					return false;
				}

				for (int i = 0; i < numOfRes; i++) {
					if (starttimesJob[i] != starttimesWrapper[i]) {
						return false;
					}
				}
			} else {
				if (starttimesJob != null) {
					// The start times of the job has to be zero
					for (int i = 0; i < starttimesJob.length; i++) {
						if (starttimesJob[i] != 0) {
							return false;
						}
					}
				}
			}

			// // Check successors
			// JobWrapper[] successorsWrapper = wrapperObj.getSuccessors();
			// List<IJob> successorsJob = jobObj.getSuccessors();
			// if (successorsWrapper != null && successorsJob != null) {
			// int numOfJobs = successorsJob.size();
			// if (numOfJobs != successorsWrapper.length) {
			// return false;
			// }
			//
			// for (int i = 0; i < numOfJobs; i++) {
			// JobWrapper successorWrapper = successorsWrapper[i];
			// IJob successorJob = successorsJob.get(i);
			// if
			// (!this.compareJobWrapperWithJobSuccessorsPredecessors(successorWrapper,
			// successorJob)) {
			// return false;
			// }
			// }
			// }
			//
			// // Check predecessors
			// JobWrapper[] predecessorsWrapper = wrapperObj.getPredecessors();
			// List<IJob> predecessorsJob = jobObj.getPredecessors();
			// if (predecessorsWrapper != null && predecessorsJob != null) {
			// int numOfJobs = predecessorsJob.size();
			// if (numOfJobs != predecessorsWrapper.length) {
			// return false;
			// }
			//
			// for (int i = 0; i < numOfJobs; i++) {
			// JobWrapper predecessorWrapper = predecessorsWrapper[i];
			// IJob predecessorJob = predecessorsJob.get(i);
			// if
			// (!this.compareJobWrapperWithJobSuccessorsPredecessors(predecessorWrapper,
			// predecessorJob)) {
			// return false;
			// }
			// }
			// }

			return true;
		}
	}

	/**
	 * Compare all JobWrapper attributes with the Job attributes
	 * 
	 * @param wrapperObj
	 *            the jobWrapper object
	 * @param jobObj
	 *            the job object
	 * @return true if all attributes of the job objects have the same value as
	 *         the attributes of the job wrapper object
	 */
	private boolean compareJobWrapperWithJobSuccessorsPredecessors(JobWrapper wrapperSuccessorPredecessor, IJob jobSuccessorPredecessor) {

		if (wrapperSuccessorPredecessor == null && jobSuccessorPredecessor == null) {
			return true;
		} else if (wrapperSuccessorPredecessor != null && jobSuccessorPredecessor == null) {
			return false;
		} else if (wrapperSuccessorPredecessor == null && jobSuccessorPredecessor != null) {
			return false;
		} else {

			// Check agentId
			if (wrapperSuccessorPredecessor.getAgentId() != jobSuccessorPredecessor.getAgentId()) {
				return false;
			}
			// Check duration
			if (wrapperSuccessorPredecessor.getDuration() != jobSuccessorPredecessor.getDuration()) {
				return false;
			}
			// Check job number
			if (wrapperSuccessorPredecessor.getJobNumber() != jobSuccessorPredecessor.getJobNumber()) {
				return false;
			}
			// Check payment
			if (wrapperSuccessorPredecessor.getPayment() != jobSuccessorPredecessor.getPayment()) {
				return false;
			}
			// Check project id
			if (wrapperSuccessorPredecessor.getProjectId() != jobSuccessorPredecessor.getProjectId()) {
				return false;
			}

			// Check resources
			int[] resourcesWrapper = wrapperSuccessorPredecessor.getResources();
			int[] resourcesJob = jobSuccessorPredecessor.getResources();
			if (resourcesWrapper != null && resourcesJob != null) {
				int numOfRes = resourcesJob.length;
				if (numOfRes != resourcesWrapper.length) {
					return false;
				}

				for (int i = 0; i < numOfRes; i++) {
					if (resourcesJob[i] != resourcesWrapper[i]) {
						return false;
					}
				}
			} else {
				if (resourcesWrapper != resourcesJob) {
					return false;
				}
			}

			// Check start times
			int[] starttimesWrapper = wrapperSuccessorPredecessor.getStartTimes();
			int[] starttimesJob = jobSuccessorPredecessor.getStartTimes();
			if (starttimesWrapper != null && starttimesJob != null) {
				int numOfRes = starttimesJob.length;
				if (numOfRes != starttimesWrapper.length) {
					return false;
				}

				for (int i = 0; i < numOfRes; i++) {
					if (starttimesJob[i] != starttimesWrapper[i]) {
						return false;
					}
				}
			} else {
				if (starttimesJob != null) {
					// The start times of the job has to be zero
					for (int i = 0; i < starttimesJob.length; i++) {
						if (starttimesJob[i] != 0) {
							return false;
						}
					}
				}
			}

			// Do not check the predecessors and successors, otherwise you get
			// an infinite loop
			return true;
		}
	}

}
