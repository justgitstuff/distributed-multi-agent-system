/**
 * 
 */
package de.hft_stuttgart.sopro.mediator.data.generator;

import java.util.ArrayList;
import java.util.List;

import de.hft_stuttgart.sopro.common.exceptions.JobInPermutationNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.ResourceNotFoundException;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.Proposal;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;
import de.hft_stuttgart.sopro.mediator.exceptions.GenerateProposalException;

/**
 * Class for the calculation of a Proposal, which contains: - the creation of
 * permutation of a jobs - the decoding of the permutation to start times of the
 * jobs
 * 
 * @author Annemarie Meissner meissner.annemarie@gmx.de
 */
public abstract class ProposalGeneratorAbstract {

	/**
	 * Project for which the proposals should be generated
	 */
	private IProject project = null;

	/**
	 * Total number of negotiation rounds.
	 */
	protected int negotiationRounds = 0;

	/**
	 * The number of proposals (ants) which should generated per iteration
	 */
	private int numOfProposals = 0;

	/**
	 * Pheromone matrix for the ant algorithm
	 */
	protected List<List<Double>> pheromoneMatrix = null;

	/**
	 * The enum for the starttime model which should be used
	 */
	private ProposalGeneratorModelEnum starttimeModel = ProposalGeneratorModelEnum.ONE_STARTTIME;

	/**
	 * Creates a ProposalGenerator instance which creates proposals for the
	 * given project
	 * 
	 * @param project
	 *            project for which the proposals should be generated
	 * @param numOfProposals
	 *            the number of the proposals (ants) which should generated per
	 *            iteration
	 */
	public ProposalGeneratorAbstract(IProject project, int negotiationRounds, int numOfProposals) {
		this.project = project;
		this.numOfProposals = numOfProposals;
		this.negotiationRounds = negotiationRounds;

		// Initialize the pheromone Matrix
		this.initializePheromoneMatrix();
	}

	/**
	 * The method generates a set of proposals
	 * 
	 * @return the proposal container which contains the proposal, the number of
	 *         the proposals is global for each Proposal generator
	 * @throws ResourceNotFoundException
	 * @throws GenerateProposalException
	 */
	public ProposalComposition generateProposals() throws GenerateProposalException, ResourceNotFoundException {
		ProposalComposition proposalContainer = new ProposalComposition();

		for (int i = 0; i < this.numOfProposals; i++) {
			// Generate one proposal for the current project
			IProposal proposal = this.generateProposal();

			// Add the generated proposal to the proposal container
			proposalContainer.addProposal(proposal);
		}
		return proposalContainer;
	}

	/**
	 * Generate one proposal
	 * 
	 * @throws GenerateProposalException
	 * @throws ResourceNotFoundException
	 */
	private IProposal generateProposal() throws GenerateProposalException, ResourceNotFoundException {
		List<IJob> permutation = this.generatePermutation();
		List<List<Integer>> starttimes = this.generateStartTimesJobOriented(permutation);

		IProposal proposal = new Proposal(project.getNumberOfJobs(), project.getNumberOfResources());
		proposal.setPermutation(permutation);
		proposal.setStarttimes(starttimes);

		return proposal;
	}

	/**
	 * The method initializes the pheromone Matrix with one
	 */
	private void initializePheromoneMatrix() {

		// The size of the matrix is numOfJobs x numOfJobs
		int numberOfJobs = this.project.getNumberOfJobs();

		// allocate memory for the columns of the matrix
		this.pheromoneMatrix = new ArrayList<List<Double>>(numberOfJobs);
		for (int i = 0; i < numberOfJobs; i++) {
			List<Double> column = new ArrayList<Double>(numberOfJobs);

			// Fill the the array with one
			for (int j = 0; j < numberOfJobs; j++) {
				column.add(new Double(1.0));
			}

			// Add the column to the pheromone Matrix
			this.pheromoneMatrix.add(column);
		}
	}

	/**
	 * @return the starttimeModel
	 */
	public ProposalGeneratorModelEnum getStarttimeModel() {
		return starttimeModel;
	}

	/**
	 * @param starttimeModel
	 *            the starttimeModel to set
	 */
	public void setStarttimeModel(ProposalGeneratorModelEnum starttimeModel) {
		this.starttimeModel = starttimeModel;
	}

	/**
	 * Update pheromone matrix with the given proposal
	 * 
	 * @param permutation
	 *            the proposal which is the best one and should get a better
	 *            pheromone track in the pheromone matrix
	 * @throws JobInPermutationNotFoundException
	 */
	public abstract void updatePheromoneMatrix(IProposal proposal, int currentRound) throws JobInPermutationNotFoundException;

	/**
	 * Set the update unit for the update of the pheromone matrix
	 * @param updateUnit
	 * 			update unit of the pheromone matrix
	 */
	public abstract void setUpdateUnit(double updateUnit);
	
	/**
	 * Set the percentage of rounds when the influence factor should be one
	 * @param percectageOfRounds
	 * 			percentage of rounds when the influence factor should be one
	 */
	public abstract void setPercentageOfRounds(double percectageOfRounds);
	
	/**
	 * print pheromone matrix
	 */
	public void printPheromoneMatrix() {
		for (List<Double> column : this.pheromoneMatrix) {
			for (Double value : column) {
				System.out.print(value.doubleValue() + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Returns the value of the pheromone matrix for going from job with jobNr
	 * fromJobNr to toJobNr
	 * 
	 * @param fromJobNr
	 *            jobNr of the job from which you want to start
	 * @param toJobNr
	 *            jobNr of the job to which you want to go
	 */
	public double getPheromoneMatrixValueAt(int fromJobNr, int toJobNr) {

		if (fromJobNr > 0 && fromJobNr <= this.pheromoneMatrix.size()) {

			// 1. get the column with the index fromJobNr - 1 of the pheromone
			// matrix
			List<Double> column = this.pheromoneMatrix.get(fromJobNr - 1);

			if (toJobNr > 0 && toJobNr <= column.size()) {
				// 2. get the column content at the index toJobNr - 1
				return column.get(toJobNr - 1).doubleValue();
			}
		}

		// indexes are not right
		return -1;
	}

	/**
	 * The method loads all problem data in a folder in the project composition
	 * 
	 * @param
	 * @return returns the permutation of the job list of the project
	 * @throws GenerateProposalException
	 */
	private List<IJob> generatePermutation() throws GenerateProposalException {

		// The permutation contains the job numbers in a certain order
		int numberOfJobs = this.project.getNumberOfJobs();
		List<IJob> permutation = new ArrayList<IJob>(numberOfJobs);
		List<IJob> eligible = new ArrayList<IJob>();

		// The dummy job with nr 1 is always the first job
		IJob lastInserted = this.project.retrieveJobFromJobNumber(1);
		permutation.add(lastInserted);

		// In each iteration add one job to the permutation
		// there are only numOfJobs - 1 iterations because
		// the first job is already inserted
		for (int i = 0; i < numberOfJobs - 2; i++) {

			// update the list of the eligible jobs with the last inserted job
			this.updateEligibleJobs(lastInserted, permutation, eligible);

			// chose one job in the list of the eligible jobs
			lastInserted = this.choseNextJob(eligible, lastInserted.getJobNumber());

			// Add a copy of the job to the permutation
			permutation.add(lastInserted);
		}

		// a copy of the last job is always the last dummy job
		lastInserted = this.project.retrieveJobFromJobNumber(numberOfJobs);
		permutation.add(lastInserted);

		return permutation;
	}

	/**
	 * The method updates the list of the eligible jobs with respect to the last
	 * inserted job
	 * 
	 * @param lastInserted
	 *            the job which is last inserted in the permutation
	 * @param permutation
	 *            the current permutation which contains as last element the
	 *            lastInserted Job
	 * @param eligible
	 *            list with all jobs which can be chosen as next job of the
	 *            permutation
	 * @return
	 */
	private void updateEligibleJobs(IJob lastInserted, List<IJob> permutation, List<IJob> eligible) {

		// Get all successors of the last inserted job
		List<IJob> successors = lastInserted.getSuccessors();

		// and add them to the eligible jobs if all their
		// predecessors are already in the permutation
		for (IJob job : successors) {

			// checks if all predecessors of a job are already in the
			// permutation
			if (this.checkPredecessorInPermutation(job, permutation)) {
				// add job the the eligible jobs
				eligible.add(job);
			}

		}
	}

	/**
	 * The method checks if all predecessors of a job are already in the
	 * permutation
	 * 
	 * @param job
	 *            the job which predecessors should be checked
	 * @param permutation
	 *            the current current permutation
	 * @return returns true if all predecessors of the job are part of the
	 *         permutation otherwise false
	 */
	private boolean checkPredecessorInPermutation(IJob job, List<IJob> permutation) {

		// Get the predecessors of the job
		List<IJob> predecessors = job.getPredecessors();
		for (IJob predecessor : predecessors) {
			boolean contains = false;

			// check if the predecessor is part of the permutation
			if (permutation.contains(predecessor)) {
				contains = true;
			}

			// The predecessor was not found in the permutation
			if (!contains) {
				return false;
			}
		}

		// all predecessors were found in the permutation
		return true;
	}

	/**
	 * The method chose randomly one job out of the eligible job list and remove
	 * the job in the eligible array
	 * 
	 * @param eligible
	 *            List of the jobs which can be chosen next for the permutation
	 * @param lastJobNr
	 *            the number of the job which is last inserted into the
	 *            permutation
	 * @return returns the randomly chosen job
	 */
	protected abstract IJob choseNextJob(List<IJob> eligible, int lastJobNr) throws GenerateProposalException;

	/**
	 * The method generates the project plan of a permutation and return a list
	 * of the start times of the jobs in the permutation
	 * 
	 * @param permutation
	 *            the permutation for which we want to generate the start times
	 * @return returns a list with the start times of the jobs for each
	 *         resource. In one start time list the start time of job nr 1 is on
	 *         index 0 in the start time list, the start time of job nr 2 is on
	 *         index 1 in the start time list, etc.
	 * @throws ResourceNotFoundException
	 */
	private List<List<Integer>> generateStartTimesResourceOriented(List<IJob> permutation) throws ResourceNotFoundException {

		// get number of resources and number of jobs of the project
		int numOfRes = this.project.getNumberOfResources();
		int numOfJobs = this.project.getNumberOfJobs();

		// Allocate memory for the start times of numOfRes resources
		List<List<Integer>> starttimes = new ArrayList<List<Integer>>(numOfRes);

		// Create one start time list for each resource
		for (int i = 0; i < numOfRes; i++) {

			// Create a list for the current resource with its max capacity
			// values
			List<Integer> resourceList = new ArrayList<Integer>();

			// Allocate memory for the start times of numOfJobs jobs for each
			// resource
			List<Integer> times = new ArrayList<Integer>(numOfJobs);
			// Initialize the list with zeros, because the starttimes which
			// are added for the permutation is not sorted
			for (int j = 0; j < numOfJobs; j++) {
				times.add(new Integer(0));
			}

			// go through all jobs of the permutation and look for the earliest
			// start time
			// with respect to the resource constraints of the current resource
			// which the job needs
			for (IJob job : permutation) {
				// For the last job --> set the latest endtime of all
				// predecessors
				if (job.getJobNumber() == numOfJobs) {
					int endtime = this.getMaxPredecessorEndtimeInAllResources(job, numOfRes);

					// add start time to the start time list at the index (jobNr
					// -
					// 1) of the current resource
					times.set(job.getJobNumber() - 1, new Integer(endtime));
				} else {
					// Find the earliest start time of the job
					int starttime = this.findStarttimeforOneResource(job, resourceList, i);

					// add start time to the start time list at the index (jobNr
					// -
					// 1) of the current resource
					times.set(job.getJobNumber() - 1, new Integer(starttime));
				}

			}

			// add the list with all start times of the current resource to the
			// start time list of all resources
			starttimes.add(times);
		}

		return starttimes;
	}

	/**
	 * The method generates the project plan of a permutation and return a list
	 * of the start times of the jobs in the permutation
	 * 
	 * @param permutation
	 *            the permutation for which we want to generate the start times
	 * @return returns a list with the start times of the jobs for each
	 *         resource. In one start time list the start time of job nr 1 is on
	 *         index 0 in the start time list, the start time of job nr 2 is on
	 *         index 1 in the start time list, etc.
	 * @throws ResourceNotFoundException
	 */
	private List<List<Integer>> generateStartTimesJobOriented(List<IJob> permutation) throws ResourceNotFoundException {

		// get number of resources and number of jobs of the project
		int numOfRes = this.project.getNumberOfResources();
		int numOfJobs = this.project.getNumberOfJobs();

		// Allocate memory for the start times of numOfRes resources
		// and the max capacity constraint of each resource
		List<List<Integer>> starttimes = new ArrayList<List<Integer>>(numOfRes);
		List<List<Integer>> resourceConstraints = new ArrayList<List<Integer>>(numOfRes);

		// Create one start time list for each resource
		for (int i = 0; i < numOfRes; i++) {
			// Allocate memory for the start times of numOfJobs jobs for each
			// resource and for the max capacity contraint of each resource
			List<Integer> times = new ArrayList<Integer>(numOfJobs);
			List<Integer> resourceList = new ArrayList<Integer>();

			// Initialize the list with zeros, because the starttimes which
			// are added for the permutation is not sorted
			for (int j = 0; j < numOfJobs; j++) {
				times.add(new Integer(0));
			}

			// add the list with the initialized start times and the max
			// capacity constraint of the current resource to the
			// start time list and the resource constraint list of all resources
			starttimes.add(times);
			resourceConstraints.add(resourceList);
		}

		// go through all jobs of the permutation and look for the earliest
		// start time with respect to the resource constraints of resources
		// which the job needs
		for (IJob job : permutation) {

			// For the last job --> set the latest endtime of all predecessors
			if (job.getJobNumber() == numOfJobs) {
				int endtime = this.getMaxPredecessorEndtimeInAllResources(job, numOfRes);
				// Go over the resources and set the found endtime as starttime
				for (int i = 0; i < numOfRes; i++) {
					// add start time to the start time list at the index (jobNr
					// - 1) of the current resource i
					(starttimes.get(i)).set(job.getJobNumber() - 1, new Integer(endtime));
				}
			} else {
				// Find the earliest start time of the job
				int[] starttime = this.findStarttimeforOneJob(job, resourceConstraints);

				// Go over the resources and set the found start time
				for (int i = 0; i < numOfRes; i++) {
					// add start time to the start time list at the index (jobNr
					// -1) of the current resource i
					(starttimes.get(i)).set(job.getJobNumber() - 1, new Integer(starttime[i]));
				}
			}
		}

		return starttimes;
	}

	/**
	 * The method finds the earliest start time of the job with respect to the
	 * resource constraints of the resources which the job needs
	 * 
	 * @param job
	 *            job for which we want to find the earliest start time
	 * @param resourceConstraint
	 *            List which contain the used capacity of a resource for a time
	 *            unit. For example, if the resource R1 has a max capacity of 4
	 *            and the index 0 contains the number -1, than is at time unit 0
	 *            one capacity of resource R1 used for a job. There are still 3
	 *            capacities of resource R1 for other jobs available.
	 * @param resourceNr
	 *            index of the resource for which the starttime should be
	 *            calculated
	 * @return returns the randomly chosen job
	 * @throws ResourceNotFoundException
	 */
	private int findStarttimeforOneResource(IJob job, List<Integer> resourceConstraint, int resourceNr) throws ResourceNotFoundException {

		// the maximum of the endtime of the predecessors is the earliest start
		// time
		// of the current job, but without the resource constraint --> check
		// resource constraint
		int starttime = getMaxPredecessorEndtime(job, resourceNr);

		// find the new starttime with the resource constraint --> check
		// resource constraint
		starttime = this.findStarttimeWithResourceConstraint(starttime, job, resourceConstraint, resourceNr);

		// A starttime was found where the resource has enough capacity during
		// the whole duration --> update the resource constraints
		this.updateResourceConstraints(starttime, job, resourceConstraint, resourceNr);

		// set the found starttime of the current job
		job.adjustStartTimeForIndex(starttime, resourceNr);

		return starttime;
	}

	/**
	 * The method finds the earliest start times of the job in all resources
	 * with respect to the resource constraints (max capacities) of the
	 * resources which the job needs
	 * 
	 * @param job
	 *            job for which we want to find the earliest start time
	 * @param resourceConstraints
	 *            List which contains for each resource a list which contain the
	 *            used capacity of a resource for a time unit. For example, if
	 *            the resource R1 has a max capacity of 4 and the index 0
	 *            contains the number -1, than is at time unit 0 one capacity of
	 *            resource R1 used for a job. There are still 3 capacities of
	 *            resource R1 for other jobs available.
	 * @return the starttimes for the job in each resource
	 * @throws ResourceNotFoundException
	 */
	private int[] findStarttimeforOneJob(IJob job, List<List<Integer>> resourceConstraints) throws ResourceNotFoundException {
		// Get the number of resources
		int numOfRes = this.project.getNumberOfResources();

		// the maximum of the endtime of the predecessors is the earliest start
		// time
		// of the current job, but without the resource constraint --> check
		// resource constraint
		int starttime = getMaxPredecessorEndtimeInAllResources(job, numOfRes);

		// find the new starttime with the resource constraint --> check
		// resource constraint
		int[] starttimesAllRes = this.findStarttimesAllResourcesWithResourceConstraint(starttime, job, resourceConstraints);

		// A starttime was found where the resource has enough capacity during
		// the whole duration --> update the resource constraints
		this.updateAllResourceConstraints(starttimesAllRes, job, resourceConstraints);

		// set the found starttimes of the current job
		for (int i = 0; i < numOfRes; i++) {
			if (this.starttimeModel == ProposalGeneratorModelEnum.ONE_STARTTIME) {
				// If a job does not need a resource --> set its starttime to -1
				if (job.retrieveResourceFromIndex(i) == 0) {
					starttimesAllRes[i] = -1;
				}
			}
			job.adjustStartTimeForIndex(starttimesAllRes[i], i);
		}

		return starttimesAllRes;
	}

	/**
	 * A starttime was found where the resource has enough capacity during the
	 * whole duration reduce now the available capacities of the duration for
	 * the neededUnitsPerPeriod, starting with the found starttime
	 * 
	 * @param starttimesAllRes
	 *            found starttimes for all resources with the resource
	 *            constraint
	 * @param job
	 *            job for which we want to find the new starttime with the RC
	 *            (resource constraint)
	 * @param resourceConstraints
	 *            List which contains for each resource a list which contain the
	 *            used capacity of a resource for a time unit. For example, if
	 *            the resource R1 has a max capacity of 4 and the index 0
	 *            contains the number -1, than is at time unit 0 one capacity of
	 *            resource R1 used for a job. There are still 3 capacities of
	 *            resource R1 for other jobs available.
	 * @throws ResourceNotFoundException
	 */
	private void updateAllResourceConstraints(int[] starttimesAllRes, IJob job, List<List<Integer>> resourceConstraints) throws ResourceNotFoundException {

		// Get the number of resources
		int numOfRes = this.project.getNumberOfResources();
		// Go throw all resources and update their resource constraints
		for (int i = 0; i < numOfRes; i++) {
			this.updateResourceConstraints(starttimesAllRes[i], job, resourceConstraints.get(i), i);
		}
	}

	/**
	 * The method finds the starttimes for each resource with enough capacity of
	 * the resource for the whole duration of the job
	 * 
	 * @param starttime
	 *            found starttime without the resource constraint
	 * @param job
	 *            job for which we want to find the new starttime with the RC
	 *            (resource constraint)
	 * @param resourceConstraints
	 *            List which contains for each resource a list which contain the
	 *            used capacity of a resource for a time unit. For example, if
	 *            the resource R1 has a max capacity of 4 and the index 0
	 *            contains the number -1, than is at time unit 0 one capacity of
	 *            resource R1 used for a job. There are still 3 capacities of
	 *            resource R1 for other jobs available.
	 * @return returns the new starttimes for all resources with RC
	 * @throws ResourceNotFoundException
	 */
	private int[] findStarttimesAllResourcesWithResourceConstraint(int starttime, IJob job, List<List<Integer>> resourceConstraints) throws ResourceNotFoundException {

		// Go over all resouces and update their resource constraints list
		int numOfRes = this.project.getNumberOfResources();

		// Inizialize starttime array
		int[] starttimes = new int[numOfRes];
		// Find the starttime for each resource
		int maxStarttime = -1;
		if (this.starttimeModel == ProposalGeneratorModelEnum.MULTI_STARTTIMES) {
			for (int i = 0; i < numOfRes; i++) {
				int resStarttime = this.findStarttimeWithResourceConstraint(starttime, job, resourceConstraints.get(i), i);
				starttimes[i] = resStarttime;
			}
		} else if (this.starttimeModel == ProposalGeneratorModelEnum.ONE_STARTTIME) {
			boolean findOneStarttimeForAllResources = false;
			while (!findOneStarttimeForAllResources) {
				for (int i = 0; i < numOfRes; i++) {
					int resStarttime = this.findStarttimeWithResourceConstraint(starttime, job, resourceConstraints.get(i), i);
					// Find the max starttime of all resources, becuase the job
					// has the
					// same starttime in all resources
					if (resStarttime > maxStarttime) {
						maxStarttime = resStarttime;
					}
					starttimes[i] = resStarttime;
				}

				// Set all starttimes to the max starttime of all resources
				setMaxStarttime: for (int i = 0; i < numOfRes; i++) {
					// The founded starttime is smaller than the max startime
					// --> set the initial starttime to the max starttime and
					// search again for all resources the starttime
					if (starttimes[i] < maxStarttime && starttimes[i] != -1) {
						starttime = maxStarttime;
						break setMaxStarttime;
					}

					// If no starttime in all resources had to be corrected -->
					// set findStarttimeForAllRes to true
					if (i == (numOfRes - 1)) {
						findOneStarttimeForAllResources = true;
					}
				}
			}
		} else {
			System.out.println("Starttime Model not Found!!!");
		}

		return starttimes;
	}

	/**
	 * The method finds the max end time of a job in all resources
	 * 
	 * @param job
	 *            job for which we want to find the max endtime
	 * @param numOfRes
	 *            the number of resources
	 * @return returns the max endtime of all predecessors of the job of all
	 *         resources
	 * @throws ResourceNotFoundException
	 */
	private int getMaxPredecessorEndtimeInAllResources(IJob job, int numOfRes) throws ResourceNotFoundException {
		List<IJob> predecessors = job.getPredecessors();
		// search the maximum of the endtimes of the predecessors
		int maxEndtime = 0;

		for (IJob predecessor : predecessors) {
			// Get the endtime of the predecessors in each resource and check if
			// it is greater than the current max
			for (int i = 0; i < numOfRes; i++) {
				if (predecessor.retrieveEndTimeFromIndex(i) > maxEndtime) {
					maxEndtime = predecessor.retrieveEndTimeFromIndex(i);
				}
			}
		}

		// If the starttime is -1 the real endtime is zero
		if (maxEndtime == -1) {
			maxEndtime = 0;
		}

		return maxEndtime;
	}

	/**
	 * A starttime was found where the resource has enough capacity during the
	 * whole duration reduce now the available capacities of the duration for
	 * the neededUnitsPerPeriod, starting with the found starttime
	 * 
	 * @param starttime
	 *            found starttime with the resource constraint
	 * @param job
	 *            job for which we want to find the new starttime with the RC
	 *            (resource constraint)
	 * @param resourceConstraint
	 *            List which contain the used capacity of a resource for a time
	 *            unit. For example, if the resource R1 has a max capacity of 4
	 *            and the index 0 contains the number -1, than is at time unit 0
	 *            one capacity of resource R1 used for a job. There are still 3
	 *            capacities of resource R1 for other jobs available.
	 * @param resourceNr
	 *            index of the resource for which the starttime should be
	 *            calculated
	 * @throws ResourceNotFoundException
	 */
	private void updateResourceConstraints(int starttime, IJob job, List<Integer> resourceConstraint, int resourceNr) throws ResourceNotFoundException {
		// get the capacity which is needed for each period for the job
		int needCapPerPeriod = job.retrieveResourceFromIndex(resourceNr);
		// If the current resource is not needed for the current job --> return
		if (needCapPerPeriod == 0) {
			return;
		}

		int duration = job.getDuration();

		// nothing to update
		if (duration == 0)
			return;

		int maxCapacityOfRes = project.getMaxCapacities()[resourceNr];
		// Prepare resource Constraint for the starttime search --> fill gaps
		// with the maxCapacity
		while (resourceConstraint.size() <= starttime) {
			resourceConstraint.add(new Integer(maxCapacityOfRes));
		}

		// A starttime was found where the resource has enough capacity during
		// the whole duration
		// reduce now the available capacities of the duration for the
		// neededUnitsPerPeriod,
		// starting with the found starttime
		for (int j = 0; j < duration; j++) {
			// Fill gaps if necessary
			if (resourceConstraint.size() <= (starttime + j)) {
				resourceConstraint.add(new Integer(maxCapacityOfRes));
			}
			// Get the available capacity of the resource at the starttime + j
			int currentValue = resourceConstraint.get(starttime + j).intValue();
			int newValue = currentValue - needCapPerPeriod;
			resourceConstraint.set(starttime + j, Integer.valueOf(newValue));
		}
	}

	/**
	 * The method finds the max endtime of a resource of the job
	 * 
	 * @param job
	 *            job for which we want to find the max endtime
	 * @param resourceNr
	 *            index of the resource for which the starttime should be
	 *            calculated
	 * @return returns the max endtime
	 * @throws ResourceNotFoundException
	 */
	private int getMaxPredecessorEndtime(IJob job, int resourceNr) throws ResourceNotFoundException {
		List<IJob> predecessors = job.getPredecessors();
		// search the maximum of the endtimes of the predecessors
		int maxEndtime = 0;

		for (IJob predecessor : predecessors) {
			// Get the endtime of the predecessor and check if it is greater
			// than the current max
			if (predecessor.retrieveEndTimeFromIndex(resourceNr) > maxEndtime) {
				maxEndtime = predecessor.retrieveEndTimeFromIndex(resourceNr);
			}
		}

		// If the starttime is -1 the real endtime is zero
		if (maxEndtime == -1) {
			maxEndtime = 0;
		}

		return maxEndtime;
	}

	/**
	 * The method finds the starttime with enough capacity of resource
	 * resourceNr for the whole duration of the job
	 * 
	 * @param starttime
	 *            found starttime without the resource constraint
	 * @param job
	 *            job for which we want to find the new starttime with the RC
	 *            (resource constraint)
	 * @param resourceConstraint
	 *            List which contain the used capacity of a resource for a time
	 *            unit. For example, if the resource R1 has a max capacity of 4
	 *            and the index 0 contains the number -1, than is at time unit 0
	 *            one capacity of resource R1 used for a job. There are still 3
	 *            capacities of resource R1 for other jobs available.
	 * @param resourceNr
	 *            index of the resource for which the starttime should be
	 *            calculated
	 * @return returns the new starttime with RC
	 * @throws ResourceNotFoundException
	 */
	private int findStarttimeWithResourceConstraint(int starttime, IJob job, List<Integer> resourceConstraint, int resourceNr) throws ResourceNotFoundException {

		// get the capacity which is needed for each period for the job
		int needCapPerPeriod = job.retrieveResourceFromIndex(resourceNr);
		int maxCapacityOfRes = project.getMaxCapacities()[resourceNr];
		int duration = job.getDuration();

		// Prepare resource Constraint for the starttime search --> fill gaps
		// with zero
		while (resourceConstraint.size() <= starttime) {
			resourceConstraint.add(new Integer(maxCapacityOfRes));
		}

		boolean findEnoughCapacity = false;
		while (!findEnoughCapacity) {

			// if the duration is zero, take the given starttime
			// as starttime and set findEnoughCapacity to true
			if (duration == 0) {
				findEnoughCapacity = true;
			}

			// Check if the available capacity is enough for the needed capacity
			// per period
			// and if there is enough capacity for the whole duration of the job
			duration: for (int j = 0; j < duration; j++) {

				// Get the available capacity of the resource at the starttime +
				// j
				// if the index (starttime + j is out ot range of the array)
				// --> no capacity is already used of any job --> set the value
				// to the max capacity
				Integer availableCap = null;
				if ((starttime + j) >= resourceConstraint.size()) {
					availableCap = new Integer(maxCapacityOfRes);
					resourceConstraint.add(starttime + j, availableCap);
				} else {
					availableCap = resourceConstraint.get(starttime + j);
				}

				if (availableCap.intValue() < needCapPerPeriod) {
					// the capacity with index starttime + j has not enough
					// capacity
					// therefore set the starttime to the next index
					// and check the whole duration again
					starttime += (j + 1);
					break duration;
				}

				// set findEnoughCapacity to true in the last iteration
				if (j == (duration - 1)) {
					findEnoughCapacity = true;
				}
			}
		}

		// If the current resource is not needed for the current job --> return
		// -1
		if (needCapPerPeriod == 0) {
			starttime = -1;
		}

		return starttime;
	}
}
