package de.hft_stuttgart.sopro.agent;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hft_stuttgart.sopro.agent.converter.ProjectConverter;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceStub.JobWrapper;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceStub.ProjectWrapper;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.project.Job;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmEnum;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmFactory;
import de.hft_stuttgart.sopro.common.voting.algorithms.IVotingAlgorithm;

/**
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class ProjectConverterTest {
	private int projectId = 1;
	private String projectName = "TestProject_X123";
	private int numberOfJobs = 3;
	private int numberOfResources = 4;
	private int firstAgentId = 10;
	private int secondAgentId = 11;
	private List<IVotingAlgorithm> votingAlgorithms = new ArrayList<IVotingAlgorithm>();
	private List<String> votingAlgorithmStrings = new ArrayList<String>();
	private int maxVotingAlgorithmIndex;
	private int votingAlgorithmStringIndex;

	@Before
	public void initializeVotingAlgorithms() {

		// initialize the Voting Algorithm Strings
		votingAlgorithmStringIndex = 0;
		votingAlgorithmStrings.add(VotingAlgorithmEnum.APPROVAL.name());
		votingAlgorithmStrings.add(VotingAlgorithmEnum.BORDA.name());
		votingAlgorithmStrings.add(VotingAlgorithmEnum.COPELAND.name());
		votingAlgorithmStrings.add(VotingAlgorithmEnum.PLURALITY.name());
		votingAlgorithmStrings.add(VotingAlgorithmEnum.SCORING.name());

		// the max index should be equal for both lists
		maxVotingAlgorithmIndex = votingAlgorithms.size() - 1;
	}

	@Test
	public void fromProjectWrappersToProjects() {
		// the sizes for the project and job lists
		int projectListSize = 5;

		// Job settings
		int agentId = 55;
		int startTimeOfJob = 0;
		int startTimeOfJobWrapper = 0;
		int[] resourcesOfJob;
		int[] resourcesOfJobWrapper;
		int[] startTimesOfJob;
		int[] startTimesOfJobWrapper;
		int jobNumber = 23;
		int duration = 24;
		double payment = 23.7;

		// Create resources for a Job
		resourcesOfJob = new int[numberOfResources];
		resourcesOfJob[0] = 2;
		resourcesOfJob[1] = 4;
		resourcesOfJob[2] = 6;
		resourcesOfJob[3] = 8;

		// Create resources for a JobWrapper
		resourcesOfJobWrapper = new int[numberOfResources];
		resourcesOfJobWrapper[0] = 2;
		resourcesOfJobWrapper[1] = 4;
		resourcesOfJobWrapper[2] = 6;
		resourcesOfJobWrapper[3] = 8;

		// create start times for a Job
		startTimesOfJob = new int[numberOfResources];
		for (int i = 0; i < numberOfResources; i++) {
			startTimesOfJob[i] = startTimeOfJob;
			++startTimeOfJob;
		}

		// create start times for a JobWrapper
		startTimesOfJobWrapper = new int[numberOfResources];
		for (int i = 0; i < numberOfResources; i++) {
			startTimesOfJobWrapper[i] = startTimeOfJobWrapper;
			++startTimeOfJobWrapper;
		}

		// Mock the Job class
		IJob mockedJob = mock(Job.class);
		// TODO ET: add the predecessors / successors to each job
		when(mockedJob.getAgentId()).thenReturn(agentId);
		when(mockedJob.getDuration()).thenReturn(duration);
		when(mockedJob.getJobNumber()).thenReturn(jobNumber);
		when(mockedJob.getPayment()).thenReturn(payment);
		when(mockedJob.getProjectId()).thenReturn(projectId);
		when(mockedJob.getStartTimes()).thenReturn(startTimesOfJob);
		when(mockedJob.getResources()).thenReturn(resourcesOfJob);

		// Mock the JobWrapper class
		JobWrapper mockedJobWrapper = mock(JobWrapper.class);
		// TODO ET: add the predecessors / successors to each JobWrapper
		when(mockedJobWrapper.getAgentId()).thenReturn(agentId);
		when(mockedJobWrapper.getDuration()).thenReturn(duration);
		when(mockedJobWrapper.getJobNumber()).thenReturn(jobNumber);
		when(mockedJobWrapper.getPayment()).thenReturn(payment);
		when(mockedJobWrapper.getProjectId()).thenReturn(projectId);
		when(mockedJobWrapper.getStartTimes()).thenReturn(startTimesOfJob);
		when(mockedJobWrapper.getResources()).thenReturn(resourcesOfJob);

		// create the projects
		ProjectWrapper[] projects = new ProjectWrapper[projectListSize];

		// the capacities
		int[] maxCapacities = new int[2];
		maxCapacities[0] = 2;
		maxCapacities[1] = 4;

		int[] currentAgentsOnProject = new int[2];
		currentAgentsOnProject[0] = firstAgentId;
		currentAgentsOnProject[1] = secondAgentId;

		// create some ProjectWrapper with the same settings
		for (int i = 0; i < projectListSize; i++) {
			ProjectWrapper project = new ProjectWrapper();
			project.setProjectId(projectId);
			project.setProjectName(projectName);
			project.setNumberOfJobs(numberOfJobs);
			project.setNumberOfResources(numberOfResources);
			project.setCurrentAgentsOnProject(currentAgentsOnProject);
			project.setMaxCapacities(maxCapacities);
			project.setVotingAlgorithm(getNextVotingAlgorithString());

			// adding the JobWrapper
			JobWrapper[] jobs = new JobWrapper[project.getNumberOfJobs()];
			for (int j = 0; j < numberOfJobs; j++) {
				jobs[j] = mockedJobWrapper;
			}

			project.setJobs(jobs);
			projects[i] = project;
		}

		// converting ProjectWrapper back to Projects
		List<IProject> projectList = new ProjectConverter().fromProjectWrappersToProjects(projects);
		for (int i = 0; i < projectList.size(); i++) {
			IProject targetProject = projectList.get(i);
			assertEquals(projectId, targetProject.getProjectId());
			assertEquals(projectName, targetProject.getProjectName());
			assertEquals(numberOfJobs, targetProject.getNumberOfJobs());
			assertEquals(numberOfResources, targetProject.getNumberOfResources());
			assertEquals(firstAgentId, targetProject.getCurrentAgentsOnProject().get(0).intValue());
			assertEquals(secondAgentId, targetProject.getCurrentAgentsOnProject().get(1).intValue());
			assertEquals(VotingAlgorithmEnum.valueOf(getNextVotingAlgorithString()), VotingAlgorithmFactory.getEnumOfVotingAlgorithm(targetProject.getVotingAlgorithm()));
			assertArrayEquals(maxCapacities, targetProject.getMaxCapacities());
			assertEquals(maxCapacities[0], targetProject.getMaxCapacities()[0]);
			assertEquals(maxCapacities[1], targetProject.getMaxCapacities()[1]);
			for (int j = 0; j < targetProject.getJobs().size(); j++) {
				IJob targetJob = targetProject.getJobs().get(j);
				assertEquals(mockedJobWrapper.getAgentId(), targetJob.getAgentId());
				assertEquals(mockedJobWrapper.getDuration(), targetJob.getDuration());
				assertEquals(mockedJobWrapper.getJobNumber(), targetJob.getJobNumber());
				assertEquals(mockedJobWrapper.getPayment(), targetJob.getPayment(), 0);
				assertEquals(mockedJobWrapper.getProjectId(), targetJob.getProjectId());
				assertArrayEquals(mockedJobWrapper.getResources(), targetJob.getResources());
				assertArrayEquals(mockedJobWrapper.getStartTimes(), targetJob.getStartTimes());
				// TODO ET: assert that predecessors / successors are the same
			}
		}

	}

	/**
	 * DON'T TOUCH THIS!!
	 * 
	 * @author Eduard Tudenhoefner - nastra@gmx.net
	 * @return The next available voting algorithm string in the list.
	 */
	private String getNextVotingAlgorithString() {
		// DON'T TOUCH THIS
		if (votingAlgorithmStringIndex > maxVotingAlgorithmIndex) {
			// reset the index and start from the beginning
			votingAlgorithmStringIndex = 0;
		}
		String votingAlgorithm = votingAlgorithmStrings.get(votingAlgorithmStringIndex);
		++votingAlgorithmStringIndex;
		return votingAlgorithm;
	}

}
