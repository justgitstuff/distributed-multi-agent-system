package de.hft_stuttgart.sopro.mediator;

import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;

import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.project.Project;
import de.hft_stuttgart.sopro.common.wrapper.JobWrapper;
import de.hft_stuttgart.sopro.mediator.converter.JobConverter;

/**
 * @author Eduard Tudenhoefner - nastra@gmx.net
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 */
public class JobConverterTest {

	/**
	 * Test usage of the JobConverterClass
	 */
	@Test
	public void testUsageJobToJobWrapper() {

		// create test Project
		IProject project = new Project(0, "Test project", 5, 2);
		int numOfRes = project.getNumberOfResources();
		int[] maxCapacities = new int[numOfRes];
		maxCapacities[0] = 4;
		maxCapacities[1] = 3;
		project.setMaxCapacities(maxCapacities);
		/*
		 * Test with the following network plan 2 / \ 1 ----3--5 \ --4-/
		 */
		// Get the jobs of the project and fill them with data
		IJob job1 = project.retrieveJobFromJobNumber(1);
		IJob job2 = project.retrieveJobFromJobNumber(2);
		IJob job3 = project.retrieveJobFromJobNumber(3);
		IJob job4 = project.retrieveJobFromJobNumber(4);
		IJob job5 = project.retrieveJobFromJobNumber(5);

		// set resources and duration of the jobs
		int[] resources1 = new int[numOfRes];
		resources1[0] = 0;
		resources1[1] = 0;
		job1.setResources(resources1);
		job1.setDuration(0);

		int[] resources2 = new int[numOfRes];
		resources2[0] = 1;
		resources2[1] = 1;
		job2.setResources(resources2);
		job2.setDuration(2);

		int[] resources3 = new int[numOfRes];
		resources3[0] = 1;
		resources3[1] = 1;
		job3.setResources(resources3);
		job3.setDuration(3);

		int[] resources4 = new int[numOfRes];
		resources4[0] = 3;
		resources4[1] = 3;
		job4.setResources(resources4);
		job4.setDuration(4);

		int[] resources5 = new int[numOfRes];
		resources5[0] = 0;
		resources5[1] = 0;
		job5.setResources(resources5);
		job5.setDuration(0);

		// Add predecessors and successors of the jobs
		job1.addSuccessor(job2);
		job1.addSuccessor(job3);
		job1.addSuccessor(job4);

		job2.addPredecessor(job1);
		job2.addSuccessor(job3);

		job3.addPredecessor(job2);
		job3.addPredecessor(job1);
		job3.addSuccessor(job5);

		job4.addPredecessor(job1);
		job4.addSuccessor(job5);

		job5.addPredecessor(job3);
		job5.addPredecessor(job4);

		// Put the jobs in a list
		List<IJob> jobList = project.getJobs();

		// Convert the jobList in a array of JobWrapper
		JobConverter jobConverter = new JobConverter();
		JobWrapper[] jobWrapperArray = jobConverter.fromJobListToJobWrapperArray(jobList);

		// Go through the jobs and compare the attribute values
		for (int i = 0; i < jobList.size(); i++) {
			JobWrapper wrapperObj = jobWrapperArray[i];
			IJob jobObj = jobList.get(i);

			boolean compare = jobConverter.compareJobWrapperWithJob(wrapperObj, jobObj);
			assertTrue(compare);
		}
	}

	/**
	 * Test usage of the JobConverterClass
	 */
	@Test
	public void testUsageJobWrapperToJob() {

		// Create test jobWrappers
		int projectId = 1;
		int numOfRes = 2;
		JobWrapper job1 = new JobWrapper(1, projectId);
		JobWrapper job2 = new JobWrapper(2, projectId);
		JobWrapper job3 = new JobWrapper(3, projectId);
		JobWrapper job4 = new JobWrapper(4, projectId);
		JobWrapper job5 = new JobWrapper(5, projectId);

		// set resources and duration of the jobs
		int[] resources1 = new int[numOfRes];
		resources1[0] = 0;
		resources1[1] = 0;
		job1.setResources(resources1);
		job1.setResources(null);
		job1.setDuration(0);

		int[] resources2 = new int[numOfRes];
		resources2[0] = 1;
		resources2[1] = 1;
		job2.setResources(resources2);
		job2.setResources(null);
		job2.setDuration(2);

		int[] resources3 = new int[numOfRes];
		resources3[0] = 1;
		resources3[1] = 1;
		job3.setResources(resources3);
		job3.setResources(null);
		job3.setDuration(3);

		int[] resources4 = new int[numOfRes];
		resources4[0] = 3;
		resources4[1] = 3;
		job4.setResources(resources4);
		job4.setResources(null);
		job4.setDuration(4);

		int[] resources5 = new int[numOfRes];
		resources5[0] = 0;
		resources5[1] = 0;
		job5.setResources(resources5);
		job5.setResources(null);
		job5.setDuration(0);

		// Add predecessors and successors of the jobs
		JobWrapper[] successors1 = new JobWrapper[3];
		JobWrapper[] predecessors1 = null;
		successors1[0] = job2;
		successors1[1] = job3;
		successors1[2] = job4;
		job1.setSuccessors(successors1);
		job1.setPredecessors(predecessors1);

		JobWrapper[] successors2 = new JobWrapper[1];
		JobWrapper[] predecessors2 = new JobWrapper[1];
		predecessors2[0] = job1;
		successors2[0] = job3;
		job2.setSuccessors(successors2);
		job2.setPredecessors(predecessors2);

		JobWrapper[] successors3 = new JobWrapper[1];
		JobWrapper[] predecessors3 = new JobWrapper[2];
		predecessors3[0] = job2;
		predecessors3[1] = job1;
		successors3[0] = job5;
		job3.setSuccessors(successors3);
		job3.setPredecessors(predecessors3);

		JobWrapper[] successors4 = new JobWrapper[1];
		JobWrapper[] predecessors4 = new JobWrapper[1];
		predecessors4[0] = job1;
		successors4[0] = job5;
		job4.setSuccessors(successors4);
		job4.setPredecessors(predecessors4);

		JobWrapper[] successors5 = null;
		JobWrapper[] predecessors5 = new JobWrapper[2];
		predecessors5[0] = job3;
		predecessors5[1] = job4;
		job5.setSuccessors(successors5);
		job5.setPredecessors(predecessors5);

		//Create a JobWrapper array
		JobWrapper[] jobWrapperArray = new JobWrapper[5];
		jobWrapperArray[0] = job1;
		jobWrapperArray[1] = job2;
		jobWrapperArray[2] = job3;
		jobWrapperArray[3] = job4;
		jobWrapperArray[4] = job5;

		// Convert the jobList in a array of JobWrapper
		JobConverter jobConverter =  new JobConverter();
		List<IJob> jobList =jobConverter.fromJobWrapperArrayToJobList(jobWrapperArray);

		// Go through the jobs and compare the attribute values
		for (int i = 0; i < jobList.size(); i++) {
			JobWrapper wrapperObj = jobWrapperArray[i];
			IJob jobObj = jobList.get(i);

			boolean compare = jobConverter.compareJobWrapperWithJob(wrapperObj, jobObj);
			assertTrue(compare);
		}
	}

}
