package de.hft_stuttgart.sopro.agent.gui;

import java.util.ArrayList;
import java.util.List;

import de.hft_stuttgart.sopro.agent.IAgent;
import de.hft_stuttgart.sopro.agent.gui.chart.IChart;
import de.hft_stuttgart.sopro.common.exceptions.StarttimeNotFoundException;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmEnum;

/**
 * This class holds all information which are necessary for the negotiation.
 * Beside the negotiation parameters like amount of negotiation rounds it is
 * used as storage for values like the total cash value of the Mediator and
 * Agent over the time. All Charts (type IChart) retrieve their data for the
 * visualization from this class.
 * 
 * @author Matthias Huber - MatthewHuber@web.de
 */
public class NegotiationSession {

	/**
	 * The payment Data.
	 */
	private List<Double> payments;

	/**
	 * The project which is negotiated.
	 */
	private IProject project;

	/**
	 * The amount of negotiation rounds.
	 */
	private int negotiationRounds;

	/**
	 * The current round of the negotiation.
	 */
	private int round = 0;

	/**
	 * This list holds for every round the Total Cash Value of the Agent.
	 */
	private List<Double> totalCashValuesAgent;

	/**
	 * This list holds for every round the Total Cash Value of the Mediator.
	 */
	private List<Double> totalCashValuesMediator;

	/**
	 * This list holds for every round the minimum Total Cash Value of the
	 * Agent.
	 */
	private List<Double> minimumTotalCashValuesAgent;

	/**
	 * This list holds for every round the minimum Total Cash Value of the
	 * Mediator.
	 */
	private List<Double> minimumTotalCashValuesMediator;

	/**
	 * This list holds for every round the maximum Total Cash Value of the
	 * Agent.
	 */
	private List<Double> maximumTotalCashValuesAgent;

	/**
	 * This list holds for every round the maximum Total Cash Value of the
	 * Mediator.
	 */
	private List<Double> maximumTotalCashValuesMediator;

	/**
	 * This list holds for every round the average Total Cash Value of the
	 * Mediator.
	 */
	private List<Double> averageTotalCashValuesMediator;

	/**
	 * This list holds for every round the minimum Project Length.
	 */
	private List<Integer> minimumProjectLengths;

	/**
	 * This list holds for every round the maximum Project Length.
	 */
	private List<Integer> maximumProjectLengths;

	/**
	 * This list holds for every round the average Project Length.
	 */
	private List<Integer> averageProjectLengths;

	/**
	 * This list holds for every round the Project Length.
	 */
	private List<Integer> projectLengths;

	/**
	 * The sum of all Total Cash Values of the Mediator.
	 */
	private double sumTotalCashValuesMediator = 0.0;

	/**
	 * The sum of all Project Lengths.
	 */
	private int sumProjectLengths = 0;

	/**
	 * The latest proposal which the Mediator has computed as round winner.
	 */
	private IProposal latestWinnerProposalMediator;

	/**
	 * Defines if the negotiation is finished or not.
	 */
	private Boolean isFinished = false;

	/**
	 * The {@link ChartComposite} holding all {@link IChart} instances.
	 */
	private ChartComposite chartComposite;

	/**
	 * Creates the NegotiationSession.
	 * 
	 * @param negotiationRounds
	 *            The amount of negotiation rounds.
	 * @param project
	 *            The project which is negotiated.
	 */
	public NegotiationSession(int negotiationRounds, IProject project) {
		this.negotiationRounds = negotiationRounds;
		this.project = project;
		totalCashValuesAgent = new ArrayList<Double>(negotiationRounds);
		totalCashValuesMediator = new ArrayList<Double>(negotiationRounds);
		minimumTotalCashValuesAgent = new ArrayList<Double>(negotiationRounds);
		minimumTotalCashValuesMediator = new ArrayList<Double>(negotiationRounds);
		maximumTotalCashValuesAgent = new ArrayList<Double>(negotiationRounds);
		maximumTotalCashValuesMediator = new ArrayList<Double>(negotiationRounds);
		averageTotalCashValuesMediator = new ArrayList<Double>(negotiationRounds);
		minimumProjectLengths = new ArrayList<Integer>(negotiationRounds);
		maximumProjectLengths = new ArrayList<Integer>(negotiationRounds);
		averageProjectLengths = new ArrayList<Integer>(negotiationRounds);
		projectLengths = new ArrayList<Integer>(negotiationRounds);

		setNecessaryNegotiationInformationAtAgent();
	}

	public void setPaymentData(List<Double> paymentData) {
		this.payments = paymentData;
	}

	public List<Double> getPaymentData() {
		return payments;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	public IProject getProject() {
		return project;
	}

	public int getNegotiationRounds() {
		return negotiationRounds;
	}

	/**
	 * This method stores the gathered information of a negotiation round in the
	 * session.
	 * 
	 * @param totalCashValueAgent
	 *            The Total Cash Value of the Agent.
	 * @param totalCashValueMediator
	 *            The Total Cash Value of the Mediator.
	 * @param latestWinnerProposalMediator
	 *            The latest proposal which the Mediator has computed as round
	 *            winner.
	 */
	public void setNegotiationRoundValues(double totalCashValueAgent, double totalCashValueMediator, IProposal latestWinnerProposalMediator) {
		incrementCounter();
		addTotalCashValueAgent(totalCashValueAgent);
		addTotalCashValueMediator(totalCashValueMediator);
		this.latestWinnerProposalMediator = latestWinnerProposalMediator;
		generateProjectLengthsInformation();
	}

	private void incrementCounter() {
		++round;
	}

	public int getRound() {
		return round;
	}

	public List<Double> getTotalCashValuesAgent() {
		return totalCashValuesAgent;
	}

	public List<Double> getTotalCashValuesMediator() {
		return totalCashValuesMediator;
	}

	public double getCurrentMinimumTotalCashValueAgent() {
		return minimumTotalCashValuesAgent.get(round - 1);
	}

	public double getCurrentMinimumTotalCashValueMediator() {
		return minimumTotalCashValuesMediator.get(round - 1);
	}

	public double getCurrentMaximumTotalCashValueAgent() {
		return maximumTotalCashValuesAgent.get(round - 1);
	}

	public double getCurrentMaximumTotalCashValueMediator() {
		return maximumTotalCashValuesMediator.get(round - 1);
	}

	public double getCurrentAverageTotalCashValue() {
		return averageTotalCashValuesMediator.get(round - 1);
	}

	public int getCurrentProjectLength() {
		return projectLengths.get(round - 1);
	}

	public int getCurrentMinimumProjectLength() {
		return minimumProjectLengths.get(round - 1);
	}

	public int getCurrentMaximumProjectLength() {
		return maximumProjectLengths.get(round - 1);
	}

	public int getCurrentAverageProjectLength() {
		return averageProjectLengths.get(round - 1);
	}

	public double getLatestTotalCashValue() {
		return totalCashValuesMediator.get(totalCashValuesMediator.size() - 1);
	}

	/**
	 * Returns the Start Times of the Winner Proposal computed by the mediator.
	 * 
	 * @return Start Times of the Winner Proposal
	 */
	public List<List<Integer>> getAllStartTimesOfLatestWinnerProposal() {
		int numberOfResource = project.getNumberOfResources();
		List<List<Integer>> allStartTimes = new ArrayList<List<Integer>>(numberOfResource);
		int numberOfJobs = project.getNumberOfJobs();
		for (int resourceIndex = 0; resourceIndex < numberOfResource; ++resourceIndex) {
			List<Integer> startTimes = new ArrayList<Integer>(numberOfJobs);
			for (int jobIndex = 0; jobIndex < numberOfJobs; ++jobIndex) {
				try {
					startTimes.add(jobIndex, latestWinnerProposalMediator.getStarttime(resourceIndex, jobIndex));
				} catch (StarttimeNotFoundException e) {
					e.printStackTrace();
				}
			}
			allStartTimes.add(startTimes);
		}

		return allStartTimes;
	}

	/**
	 * Returns the Resource Start Times of the Winner Proposal computed by the
	 * mediator.
	 * 
	 * @param resourceNumber
	 *            The number of the resource.
	 * @return Start Times of the Winner Proposal
	 */
	// TODO: refactor => upper method does nearly the same (maybe they can be
	// combined)
	public List<Integer> getStartTimesForResource(int resourceNumber) {
		int numberOfResource = project.getNumberOfResources();
		List<List<Integer>> allStartTimes = new ArrayList<List<Integer>>(numberOfResource);
		int numberOfJobs = project.getNumberOfJobs();
		for (int resourceIndex = 0; resourceIndex < numberOfResource; ++resourceIndex) {
			List<Integer> startTimes = new ArrayList<Integer>(numberOfJobs);
			for (int jobIndex = 0; jobIndex < numberOfJobs; ++jobIndex) {
				try {
					startTimes.add(jobIndex, latestWinnerProposalMediator.getStarttime(resourceIndex, jobIndex));
				} catch (StarttimeNotFoundException e) {
					e.printStackTrace();
				}
			}
			allStartTimes.add(startTimes);
		}

		return allStartTimes.get(resourceNumber);
	}

	public ChartComposite getChartComposite() {
		if (null == chartComposite) {
			chartComposite = new ChartComposite();
		}
		return chartComposite;
	}

	public IProposal getLatestWinnerProposalMediator() {
		return latestWinnerProposalMediator;
	}

	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}

	public Boolean getIsFinished() {
		return isFinished;
	}

	private void setNecessaryNegotiationInformationAtAgent() {
		IAgent agent = AmpManager.getAgent();
		setPaymentData(AmpManager.getRemoteInstance().retrievePaymentDataForProject(project.getProjectId(), agent.getAgentId()));
		agent.setPaymentData(getPaymentData());
		agent.setVotingAlgorithm(VotingAlgorithmEnum.BORDA);
	}

	private void addTotalCashValueAgent(double totalCashValue) {
		totalCashValuesAgent.add(totalCashValue);
		if (round > 1) {
			evaluateTotalCashValueAgent(totalCashValue);
		} else {
			initializeTotalCashValuesAgent(totalCashValue);
		}
	}

	private void addTotalCashValueMediator(double totalCashValue) {
		totalCashValuesMediator.add(totalCashValue);
		generateNewAverageTotalCashValue(totalCashValue);
		if (round > 1) {
			evaluateTotalCashValueMediator(totalCashValue);
		} else {
			initializeTotalCashValuesMediator(totalCashValue);
		}
	}

	private void generateNewAverageTotalCashValue(double totalCashValue) {
		sumTotalCashValuesMediator += totalCashValue;
		averageTotalCashValuesMediator.add(sumTotalCashValuesMediator / round);
	}

	private void evaluateTotalCashValueAgent(double totalCashValue) {
		double minimumTotalCashValue = minimumTotalCashValuesAgent.get(round - 2);
		minimumTotalCashValuesAgent.add(totalCashValue < minimumTotalCashValue ? totalCashValue : minimumTotalCashValue);
		double maximumTotalCashValue = maximumTotalCashValuesAgent.get(round - 2);
		maximumTotalCashValuesAgent.add(totalCashValue > maximumTotalCashValue ? totalCashValue : maximumTotalCashValue);
	}

	private void evaluateTotalCashValueMediator(double totalCashValue) {
		double minimumTotalCashValue = minimumTotalCashValuesMediator.get(round - 2);
		minimumTotalCashValuesMediator.add(totalCashValue < minimumTotalCashValue ? totalCashValue : minimumTotalCashValue);
		double maximumTotalCashValue = maximumTotalCashValuesMediator.get(round - 2);
		maximumTotalCashValuesMediator.add(totalCashValue > maximumTotalCashValue ? totalCashValue : maximumTotalCashValue);
	}

	private void initializeTotalCashValuesAgent(double totalCashValue) {
		minimumTotalCashValuesAgent.add(totalCashValue);
		maximumTotalCashValuesAgent.add(totalCashValue);
	}

	private void initializeTotalCashValuesMediator(double totalCashValue) {
		minimumTotalCashValuesMediator.add(totalCashValue);
		maximumTotalCashValuesMediator.add(totalCashValue);
	}

	private void generateProjectLengthsInformation() {
		Integer projectLength = calculateProjectLength();
		projectLengths.add(projectLength);
		sumProjectLengths += projectLength;
		averageProjectLengths.add(sumProjectLengths / round);
		if (round > 1) {
			int minimalProjectLength = minimumProjectLengths.get(round - 2);
			minimumProjectLengths.add(projectLength < minimalProjectLength ? projectLength : minimalProjectLength);
			int maximalProjectLength = maximumProjectLengths.get(round - 2);
			maximumProjectLengths.add(projectLength > maximalProjectLength ? projectLength : maximalProjectLength);
		} else {
			minimumProjectLengths.add(projectLength);
			maximumProjectLengths.add(projectLength);
		}
	}

	private Integer calculateProjectLength() {
		Integer projectLength = 0;
		List<List<Integer>> fromNegoSession = AmpManager.getNegotiationSession().getAllStartTimesOfLatestWinnerProposal();
		for (int i = 0; i < fromNegoSession.size(); i++) {
			List<Integer> start = fromNegoSession.get(i);
			Integer temp = start.get(start.size() - 1);
			if (temp > projectLength)
				projectLength = temp;
		}
		return projectLength;
	}

}
