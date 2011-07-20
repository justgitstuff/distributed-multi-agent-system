/**
 * 
 */
package de.hft_stuttgart.sopro.mediator.data;

import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmEnum;
import de.hft_stuttgart.sopro.mediator.data.generator.ProposalGeneratorEnum;

/**
 * The class is a data class. A Object of the class contains all attributes of a
 * found solution of the negotiation which is needed for the evaluation of a
 * solution.
 * 
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 */
public class SolutionData {

	/**
	 * name of the project (problem data)
	 */
	private String projectName = null;

	/**
	 * number of jobs in the project
	 */
	private int numOfJobs = 0;

	/**
	 * best solution (cash value of a found project plan) for one agent. Out of
	 * the solution data of Fink
	 */
	private double bestSolutionOneAgent1 = 0.0;

	/**
	 * best solution (cash value of a found project plan) for one agent. Out of
	 * the solution data of another one
	 */
	private double bestSolutionOneAgent2 = 0.0;

	/**
	 * Finks best solution (cash value of a found project plan) for two agents.
	 * Out of the solution data of Fink
	 */
	private double bestSolutionMultiAgent = 0.0;

	/**
	 * the first solution (cash value of a the first proposal) of the AMP system
	 * for two agents.
	 */
	private double solutionAMP_start = 0.0;

	/**
	 * the calculated solution (cash value of a found project plan) of the AMP
	 * system for two agents.
	 */
	private double solutionAMP_end = 0.0;

	/**
	 * the used voting algorithm for the negotiation
	 */
	private String votingAlgorithm;

	/**
	 * the number of iterations for the negotiation of the project
	 */
	private int numberOfIterations = 0;

	/**
	 * the number of the ants (proposals) per iteration
	 */
	private int numberOfProposals = 0;

	/**
	 * the used proposal generation algorithm for the negotiation
	 */
	private String proposalGeneratorAlgorithm;

	/**
	 * The solution proposal of amp --> the winner of the last negotiation round
	 */
	private int[] makespans = null;
	
	/**
	 * A description for the update rule of the pheromone matrix
	 */
	private String updateRule = null;

	/**
	 * Constructor, which sets the data for the evaluation
	 * 
	 * @param projectName
	 *            the name of the project for which the solution was calculated
	 */
	public SolutionData(String projectName, int numberOfJobs, double solutionAMP_start, double solutionAMP_end, VotingAlgorithmEnum votingAlgorithm, ProposalGeneratorEnum proposalGenerator,
			int numberOfRounds, int proposalsPerRound, int[] makespans, String updateRule) {

		// Get the evaluation data
		BestSolutionEvaluationDataEnum enumBestSolution = BestSolutionEvaluationDataEnum.valueOf(projectName);
		this.bestSolutionOneAgent1 = enumBestSolution.getValueAgent1();
		this.bestSolutionOneAgent2 = enumBestSolution.getValueAgent2();
		this.bestSolutionMultiAgent = enumBestSolution.getValueSum();

		this.projectName = projectName;
		this.numOfJobs = numberOfJobs;
		this.solutionAMP_start = solutionAMP_start;
		this.solutionAMP_end = solutionAMP_end;
		this.votingAlgorithm = votingAlgorithm.name();
		this.numberOfIterations = numberOfRounds;
		this.numberOfProposals = proposalsPerRound;
		this.proposalGeneratorAlgorithm = proposalGenerator.name();
		this.makespans = makespans;
		this.updateRule = updateRule;
	}
	
	

	/**
	 * @return the updateRule
	 */
	public String getUpdateRule() {
		return updateRule;
	}



	/**
	 * @param updateRule the updateRule to set
	 */
	public void setUpdateRule(String updateRule) {
		this.updateRule = updateRule;
	}



	/**
	 * @return the proposalGeneratorAlgorithm
	 */
	public String getProposalGeneratorAlgorithm() {
		return proposalGeneratorAlgorithm;
	}

	/**
	 * @return the solutionAMP_start
	 */
	public double getSolutionAMP_start() {
		return solutionAMP_start;
	}

	/**
	 * @return the makespans
	 */
	public int[] getMakespans() {
		return makespans;
	}

	/**
	 * @param proposalGeneratorAlgorithm
	 *            the proposalGeneratorAlgorithm to set
	 */
	public void setProposalGeneratorAlgorithm(String proposalGeneratorAlgorithm) {
		this.proposalGeneratorAlgorithm = proposalGeneratorAlgorithm;
	}

	/**
	 * @return the numOfJobs
	 */
	public int getNumOfJobs() {
		return numOfJobs;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @return the bestSolutionOneAgent
	 */
	public double getBestSolutionOneAgent1() {
		return bestSolutionOneAgent1;
	}

	/**
	 * @return the bestSolutionOneAgent
	 */
	public double getBestSolutionOneAgent2() {
		return bestSolutionOneAgent2;
	}

	/**
	 * @return the bestSolutionMultiAgent
	 */
	public double getBestSolutionMultiAgent() {
		return bestSolutionMultiAgent;
	}

	/**
	 * @return the solutionAMP
	 */
	public double getSolutionAMP_end() {
		return solutionAMP_end;
	}

	/**
	 * @return the votingAlgorithm
	 */
	public String getVotingAlgorithm() {
		return votingAlgorithm;
	}

	/**
	 * @return the numberOfIterations
	 */
	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	/**
	 * @return the numberOfProposals
	 */
	public int getNumberOfProposals() {
		return numberOfProposals;
	}
}
