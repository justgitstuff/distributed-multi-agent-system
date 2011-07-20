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
 * unit with the value one.
 * 
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 */
public class ProposalGeneratorPrimitive extends ProposalGeneratorAbstract {

	/**
	 * {@inheritDoc}
	 */
	public ProposalGeneratorPrimitive(IProject project, int negotiationRounds, int numOfProposals) {
		super(project, negotiationRounds, numOfProposals);
	}
	
	public void setUpdateUnit(double unpdateUnit){
		System.out.println("Set update unit of primitive proposal generator not possible!");
	}
	
	public void setPercentageOfRounds(double percectageOfRounds){
		System.out.println("Set percentage of rounds of primitive proposal generator not possible!");
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
		int updateUnit = 1;
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
			currentValue += updateUnit;
			// set the new value in the column
			column.set(toJobNr - 1, currentValue);

			// Set the updated column to the matrix
			pheromoneMatrix.set(fromJobNr - 1, column);

			// Set the current toJobNr to the next fromJobNr
			fromJobNr = toJobNr;
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
			/*
			 * WITHOUT Pheromone matrix Random randomGenerator = new Random();
			 * nextJobIndex = (int) (randomGenerator.nextDouble() * numOfJobs);
			 * // it can happen, that the random number is exactly 1, // then we
			 * have to decrease the index if (nextJobIndex >= numOfJobs) {
			 * nextJobIndex = numOfJobs - 1; }
			 */

			/*
			 * WITH Pheromone matrix
			 */
			// Create a list with the summed probabilities of the pheromone
			// matrix
			double[] probabilityList = new double[numOfJobs];
			double currentPropapility = 0.0;
			// Fill up the list with the summed probabilities from the last job
			// to the next possible jobs
			for (int i = 0; i < numOfJobs; i++) {
				// Get the next possible job of the eligible
				IJob nextPossible = eligible.get(i);

				// get the probability of going from the last inserted job to
				// the
				// nextPossible and sum the probability to the
				// currentProbability

				// Get the column with the index lastJobNr - 1 with the
				// probabilities for the next possible jobs
				currentPropapility += (this.getPheromoneMatrixValueAt(lastJobNr, nextPossible.getJobNumber()) / (double) numOfJobs);

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
}
