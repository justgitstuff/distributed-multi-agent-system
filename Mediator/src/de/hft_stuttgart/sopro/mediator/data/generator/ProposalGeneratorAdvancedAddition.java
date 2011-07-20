/**
 * 
 */
package de.hft_stuttgart.sopro.mediator.data.generator;

import java.util.List;
import java.util.Random;

import de.hft_stuttgart.sopro.common.exceptions.JobInPermutationNotFoundException;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.mediator.exceptions.GenerateProposalException;

/**
 * This proposal generator updates the pheromone matrix with an arbitrary update
 * unit. The update unit must not have necessary the value one, as in the
 * primitive ProposalGenerator.
 * 
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 */
public class ProposalGeneratorAdvancedAddition extends ProposalGeneratorAbstract {

	/**
	 * {@inheritDoc}
	 */
	public ProposalGeneratorAdvancedAddition(IProject project, int negotiationRounds, int numOfProposals) {
		super(project, negotiationRounds, numOfProposals);
	}

	/**
	 * The update unit for the pheromone matrix for one update step
	 */
	private double updateUnit = 0.0;
	
	/**
	 * This factor gives the percentage of rounds when the influence factor should be one
	 */
	private double percentageOfRounds = 0.0;

	
	/**
	 * @return the percentageOfRounds
	 */
	public double getPercentageOfRounds() {
		return percentageOfRounds;
	}

	/**
	 * @param percentageOfRounds the percentageOfRounds to set
	 */
	public void setPercentageOfRounds(double percentageOfRounds) {
		this.percentageOfRounds = percentageOfRounds;
	}

	/**
	 * @return the updateUnit
	 */
	public double getUpdateUnit() {
		return updateUnit;
	}

	/**
	 * @param updateUnit
	 *            the updateUnit to set
	 */
	public void setUpdateUnit(double updateUnit) {
		this.updateUnit = updateUnit;
	}

	/**
	 * Update pheromone matrix with the given proposal
	 * 
	 * @param permutation
	 *            the proposal which is the best one and should get a better
	 *            pheromone track in the pheromone matrix
	 * @throws JobInPermutationNotFoundException
	 */
	public void updatePheromoneMatrix(IProposal proposal, int currentRound) throws JobInPermutationNotFoundException {

		if (proposal.getPermutationSize() <= 1) {
			return;
		}

		// Get the number of the first job of the permutation
		int fromJobNr = proposal.getPermutation(0).getJobNumber();
		int toJobNr = 0;
	
		/**
		 * Parameter for the influence of the pheromone Matrix for the next job
		 * selection. This factor is for the beginning of the negotiation near zero
		 * and at the end near one 0.0 --> older solutions has no influence on the
		 * current solution 1.0 --> concentrate more on the older solutions
		 */
		double influenceFactor = 1.0;

		// ** Example for a linear influence factor **
		// influenceFactor = this.getLinearInfluenceFactor(currentRound);

		// ** Example for an increasing linear influence factor. The factor
		// increases linear until 90% of the negotiation and than 4 times more
		// **
		// influenceFactor =
		// this.getLinearInceasingInfluenceFactor(currentRound, 0.9, 4);

		// ** Example for an exponential increasing influence factor. The factor
		// is one if 80% of the negotiation is finished. ** --> SCHLECHT
		// influenceFactor = this.getExponentialIncreasingInfluenceFactor(currentRound, 0.9);
		
		// ** Example for an linear influence factor which is one in the end of the negotiation ** 
		influenceFactor = this.getLinearInfluenceFactorEndEqual(currentRound, this.percentageOfRounds);
		if(currentRound % 1000 == 0){
			System.out.println("Update Unit = " + this.updateUnit);
			System.out.println("Percentage of Rounds = " + this.percentageOfRounds);
			System.out.println("Influence Factor in round " + currentRound + " = " + influenceFactor);
		}
		
		for (int i = 1; i < proposal.getPermutationSize(); i++) {

			// Get the jobNr of the next job in the permutation
			toJobNr = proposal.getPermutation(i).getJobNumber();

			// Add one to pheromone matrix at the index (fromJobNr - 1, toJobNr
			// - 1)
			// 1. get the column with the index fromJobNr - 1 of the pheromone
			// matrix
			List<Double> column = pheromoneMatrix.get(fromJobNr - 1);

			// 2. increase the column content at the index toJobNr - 1
			double currentValue = column.get(toJobNr - 1).doubleValue();
			// increase the value with the updateUnit
			currentValue += (this.updateUnit * influenceFactor);
			// set the new value in the column
			column.set(toJobNr - 1, currentValue);

			// Set the updated column to the matrix
			pheromoneMatrix.set(fromJobNr - 1, column);

			// Set the current toJobNr to the next fromJobNr
			fromJobNr = toJobNr;
		}
	}

	private double getExponentialIncreasingInfluenceFactor(int currentRound, double percentageForOne) {
		double percentageOfCompletion = ((double) currentRound) / ((double) negotiationRounds);
		if (percentageOfCompletion < percentageForOne) {
			return percentageOfCompletion;
		} else {
			int roundOneValue = (int) ((double) negotiationRounds * percentageForOne);
			int xValue = currentRound - roundOneValue;
			double exponentialFactor = Math.exp((double) xValue);
			return exponentialFactor;
		}
	}

	private double getLinearInfluenceFactor(int currentRound) {
		return ((double) currentRound) / ((double) negotiationRounds);
	}

	private double getLinearInceasingInfluenceFactor(int currentRound, double percentageForIncreasing, double increasingFactor) {
		double percentageOfCompletion = ((double) currentRound) / ((double) negotiationRounds);
		if (percentageOfCompletion < percentageForIncreasing) {
			return percentageOfCompletion;
		} else {
			return percentageForIncreasing * increasingFactor;
		}
	}
	
	private double getLinearInfluenceFactorEndEqual(int currentRound, double percentageForEqual) {
		double percentageOfCompletion = ((double) currentRound) / ((double) negotiationRounds);
		if (percentageOfCompletion > percentageForEqual) {
			return 1.0;
		} else {
			int roundOfEqualOne = (int)(percentageForEqual * negotiationRounds);
			return ((double)currentRound / (double)roundOfEqualOne);
		}
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
	protected IJob choseNextJob(List<IJob> eligible, int lastJobNr) throws GenerateProposalException {

		// Create a uniformly distributed random number between zero and
		// numOfJobs
		int numOfJobs = eligible.size();

		// If no jobs in the eligible list --> throw Exception
		if (numOfJobs == 0) {
			throw new GenerateProposalException("Number of jobs should not be zero");
		}

		int nextJobIndex = 0;
		if (numOfJobs > 1) {

			// Create a list with the summed probabilities of the pheromone
			// matrix
			double[] probabilityList = new double[numOfJobs];
			double currentPropapility = 0.0;
			// Fill up the list with the summed probabilities from the last job
			// to the next possible jobs
			double sumPossiblePheromoneMatrixValues = this.lastJobToPossibleJobsMatrixSum(eligible, lastJobNr);
			for (int i = 0; i < numOfJobs; i++) {
				// Get the next possible job of the eligible
				IJob nextPossible = eligible.get(i);

				// get the probability of going from the last inserted job to
				// the nextPossible and sum the probability to the
				// currentProbability

				// Get the column with the index lastJobNr - 1 with the
				// probabilities for the next possible jobs
				currentPropapility += (this.getPheromoneMatrixValueAt(lastJobNr, nextPossible.getJobNumber()) / sumPossiblePheromoneMatrixValues);

				// set the new currentProbability to the probabilityList
				probabilityList[i] = currentPropapility;
			}

			// Get a random number between 0 and 1
			Random randomGenerator = new Random();
			double randomNumber = randomGenerator.nextDouble();

			// chose the next job with the random number and the probability
			// list of the eligible jobs
			for (int j = 0; j < numOfJobs; j++) {
				if (randomNumber <= probabilityList[j]) {
					nextJobIndex = j;
					break;
				}
			}
		}

		// select the job out of the job list
		IJob nextJob = eligible.get(nextJobIndex);

		// Delete the job in the job list as eligible
		eligible.remove(nextJobIndex);

		// return the selected job
		return nextJob;
	}

	/**
	 * The method sums up all values of the pheromone Matrix of the possible
	 * next jobs
	 * 
	 * @param eligible
	 *            List of the jobs which can be chosen next for the permutation
	 * @param lastJobNr
	 *            the number of the job which is last inserted into the
	 *            permutation
	 * @return the sum of all values of the pheromone Matrix of the possible
	 *         next jobs
	 */
	private double lastJobToPossibleJobsMatrixSum(List<IJob> eligible, int lastJobNr) {
		double sum = 0.0;

		for (int i = 0; i < eligible.size(); i++) {
			// Get the next possible job of the eligible
			IJob nextPossible = eligible.get(i);

			// Get the column with the index lastJobNr - 1 with the
			// probabilities for the next possible jobs and sum up the values
			sum += this.getPheromoneMatrixValueAt(lastJobNr, nextPossible.getJobNumber());
		}

		return sum;
	}
}
