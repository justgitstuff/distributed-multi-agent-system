package de.hft_stuttgart.sopro.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hft_stuttgart.sopro.common.exceptions.ResourceNotFoundException;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.project.Job;
import de.hft_stuttgart.sopro.common.project.Project;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.Proposal;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;
import de.hft_stuttgart.sopro.common.wrapper.JobWrapper;
import de.hft_stuttgart.sopro.common.wrapper.ProposalCompositionWrapper;
import de.hft_stuttgart.sopro.common.wrapper.ProposalWrapper;
import de.hft_stuttgart.sopro.mediator.converter.ProposalCompositionConverter;
import de.hft_stuttgart.sopro.mediator.converter.ProposalConverter;

/**
 * @author Sandro Degiorgi - sdegiorgiq@gmail.com
 */
public class ProposalCompositionConverterTest {
	
	/**
	 * Test usage of the ProposalConverterClass
	 */
	@Test
	public void fromProposalCompositionToProposalCompositionWrapper() {
		
		// create test Project
		IProject project = new Project(0, "Test project", 5, 2);
		int numOfJobs = project.getNumberOfJobs();
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
		
		// Create a test permutation
		List<IJob> testPermutation = new ArrayList<IJob>();
		//Set the starttimes in the jobs
		try {
			job1.adjustStartTimeForIndex(0, 0);
			job2.adjustStartTimeForIndex(0, 0);
			job3.adjustStartTimeForIndex(2, 0);
			job4.adjustStartTimeForIndex(0, 0);
			job5.adjustStartTimeForIndex(5, 0);
			
			job1.adjustStartTimeForIndex(0, 1);
			job2.adjustStartTimeForIndex(0, 1);
			job3.adjustStartTimeForIndex(2, 1);
			job4.adjustStartTimeForIndex(5, 1);
			job5.adjustStartTimeForIndex(9, 1);
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		
		// add all jobs
		testPermutation.add(job1);
		testPermutation.add(job2);
		testPermutation.add(job3);
		testPermutation.add(job4);
		testPermutation.add(job5);
		
		//Create a test proposal
		IProposal proposal1 = new Proposal(numOfJobs, numOfRes);
		// Set the permutation
		proposal1.setPermutation(testPermutation);	
		
		//Generate Starttimes for proposal 1
		List<List<Integer>> starttimes1 = new ArrayList<List<Integer>>( numOfRes );
		for(int i=0; i<numOfRes; i++){
			List<Integer> resStarttime = new ArrayList<Integer>(numOfJobs);
			
			//Set starttimes for resource 1
			if(i==0){
				// For all permutations there are one starttime solutions for resource 1:
			    //[0 0 2 0 5]
				resStarttime.add(new Integer(0));
				resStarttime.add(new Integer(0));
				resStarttime.add(new Integer(2));
				resStarttime.add(new Integer(0));
				resStarttime.add(new Integer(5));
			}
			else{
				//For the permutations p(1 2 3 4 5) the starttime solution 
				//for resource 2 is [0 0 2 5 9]
				resStarttime.add(new Integer(0));
				resStarttime.add(new Integer(0));
				resStarttime.add(new Integer(2));
				resStarttime.add(new Integer(5));
				resStarttime.add(new Integer(9));
			}
			
			//Add resStarttimes to the starttimes
			starttimes1.add(resStarttime);
		}
		proposal1.setStarttimes(starttimes1);
		proposal1.setEvaluationPoints(10);
		
		// Create a test permutation
		List<IJob> testPermutation3 = new ArrayList<IJob>();
		//Set the starttimes in the jobs
		try {
			job1.adjustStartTimeForIndex(0, 0);
			job2.adjustStartTimeForIndex(0, 0);
			job3.adjustStartTimeForIndex(2, 0);
			job4.adjustStartTimeForIndex(0, 0);
			job5.adjustStartTimeForIndex(5, 0);
			
			job1.adjustStartTimeForIndex(0, 1);
			job2.adjustStartTimeForIndex(4, 1);
			job3.adjustStartTimeForIndex(6, 1);
			job4.adjustStartTimeForIndex(0, 1);
			job5.adjustStartTimeForIndex(9, 1);
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}		
		
		// add all jobs
		testPermutation3.add(job1);
		testPermutation3.add(job4);
		testPermutation3.add(job2);
		testPermutation3.add(job3);
		testPermutation3.add(job5);
		
		//Create a test proposal
		IProposal proposal3 = new Proposal(numOfJobs, numOfRes);
		// Set the permutation
		proposal3.setPermutation(testPermutation3);	
		
		//Generate Starttimes for proposal 3
		List<List<Integer>> starttimes3 = new ArrayList<List<Integer>>( numOfRes );
		for(int i=0; i<numOfRes; i++){
			List<Integer> resStarttime = new ArrayList<Integer>(numOfJobs);
			
			//Set starttimes for resource 1
			if(i==0){
				// For all permutations there are one starttime solutions for resource 1:
			    //[0 0 2 0 5]
				resStarttime.add(new Integer(0));
				resStarttime.add(new Integer(0));
				resStarttime.add(new Integer(2));
				resStarttime.add(new Integer(0));
				resStarttime.add(new Integer(5));
			}
			else{
				//For the permutations p(1 4 2 3 5) the starttime solution 
				//for resource 2 is [0 4 6 0 9]
				resStarttime.add(new Integer(0));
				resStarttime.add(new Integer(4));
				resStarttime.add(new Integer(6));
				resStarttime.add(new Integer(0));
				resStarttime.add(new Integer(9));
			}
			
			//Add resStarttimes to the starttimes
			starttimes3.add(resStarttime);
		}
		proposal3.setStarttimes(starttimes3);
		proposal3.setEvaluationPoints(20);
		
		
		//Create a new ProposalComposition
		ProposalComposition proposalComposition = new ProposalComposition();
		proposalComposition.addProposal(proposal1);
		proposalComposition.addProposal(proposal3);

        int numOfProposals = proposalComposition.getNumberOfProposals();
        ProposalCompositionConverter proposalCompositionConverter = new ProposalCompositionConverter();
        ProposalCompositionWrapper proposalCompositionWrapper = proposalCompositionConverter.fromProposalCompositionToProposalCompositionWrapper(proposalComposition);
        
        ProposalWrapper[] wrapperProposalArray = proposalCompositionWrapper.getProposals();
        List<IProposal>  proposalList = proposalComposition.getProposals();
        assertEquals(numOfProposals,  wrapperProposalArray.length);
        for(int i=0; i<numOfProposals; i++){
        	ProposalWrapper wrapperObj = wrapperProposalArray[i];
        	IProposal proposalObj = proposalList.get(i);
        	
        	boolean compare = new ProposalConverter().compareProposalWrapperWithProposal(wrapperObj, proposalObj);
        	assertTrue(compare);
        }
	}
	
	/**
	 * Test usage of the ProposalConverterClass
	 */
	@Test
	public void fromProposalWrapperToProposal() {
		
		// Create test jobWrappers
		int projectId = 1;
		int numOfRes = 2;
		int numOfJobs = 5;
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
		
		// Create a test permutation
		JobWrapper[] testPermutation = new JobWrapper[numOfJobs];
		// add all jobs
		testPermutation[0] = job1;
		testPermutation[1] = job2;
		testPermutation[2] = job3;
		testPermutation[3] = job4;
		testPermutation[4] = job5;
		
		
		//Create a test proposal
		ProposalWrapper proposal1 = new ProposalWrapper(numOfJobs, numOfRes);
		// Set the permutation
		proposal1.setPermutation(testPermutation);	
		
		//Generate Starttimes for proposal 1
		int[][] starttimes1 = new int[ numOfRes ][numOfJobs];
		for(int i=0; i<numOfRes; i++){
			int[] resStarttime = new int[numOfJobs];
			
			//Set starttimes for resource 1
			if(i==0){
				// For all permutations there are one starttime solutions for resource 1:
			    //[0 0 2 0 5]
				resStarttime[0] = 0;
				resStarttime[1] = 0;
				resStarttime[2] = 2;
				resStarttime[3] = 0;
				resStarttime[4] = 5;
			}
			else{
				//For the permutations p(1 2 3 4 5) the starttime solution 
				//for resource 2 is [0 0 2 5 9]
				resStarttime[0] = 0;
				resStarttime[1] = 0;
				resStarttime[2] = 2;
				resStarttime[3] = 5;
				resStarttime[4] = 9;
			}
			
			//Add resStarttimes to the starttimes
			starttimes1[i] = resStarttime;
		}
		proposal1.setStartTimes(starttimes1);
		proposal1.setEvaluationPoints(10);
		
		// Create a test permutation
		JobWrapper[] testPermutation3 = new JobWrapper[numOfJobs];
		
		// add all jobs
		testPermutation3[0] = job1;
		testPermutation3[1] = job4;
		testPermutation3[2] = job2;
		testPermutation3[3] = job3;
		testPermutation3[4] = job5;
		
		//Create a test proposal
		ProposalWrapper proposal3 = new ProposalWrapper(numOfJobs, numOfRes);
		// Set the permutation
		proposal3.setPermutation(testPermutation3);	
		
		//Generate Starttimes for proposal 3
		int[][] starttimes3 = new int[ numOfRes ][numOfJobs];;
		for(int i=0; i<numOfRes; i++){
			int[] resStarttime = new int[numOfJobs];
			
			//Set starttimes for resource 1
			if(i==0){
				// For all permutations there are one starttime solutions for resource 1:
			    //[0 0 2 0 5]
				resStarttime[0] = 0;
				resStarttime[1] = 0;
				resStarttime[2] = 2;
				resStarttime[3] = 0;
				resStarttime[4] = 5;
			}
			else{
				//For the permutations p(1 4 2 3 5) the starttime solution 
				//for resource 2 is [0 4 6 0 9]
				resStarttime[0] = 0;
				resStarttime[1] = 4;
				resStarttime[2] = 6;
				resStarttime[3] = 0;
				resStarttime[4] = 9;
			}
			
			//Add resStarttimes to the starttimes
			starttimes3[i] = resStarttime;
		}
		proposal3.setStartTimes(starttimes3);
		proposal3.setEvaluationPoints(20);
		
		ProposalWrapper[] testProposalArray = new ProposalWrapper[2];
		testProposalArray[0] = proposal1;
		testProposalArray[0] = proposal3;
		
		//Create a new proposalCompositionWrapper
		ProposalCompositionWrapper wrapperComposition = new ProposalCompositionWrapper();
		wrapperComposition.setProposals(testProposalArray);

        int numOfProposals = testProposalArray.length;
        ProposalCompositionConverter proposalCompositionConverter = new ProposalCompositionConverter();
        ProposalComposition proposalComposition = proposalCompositionConverter.fromProposalCompositionWrapperToProposalComposition(wrapperComposition);
        List<IProposal> proposalList = proposalComposition.getProposals();
        assertEquals(numOfProposals, proposalList.size());
        for(int i=0; i<numOfProposals; i++){
        	ProposalWrapper wrapperObj = testProposalArray[i];
        	IProposal proposalObj = proposalList.get(i);
        	
        	boolean compare = new ProposalConverter().compareProposalWrapperWithProposal(wrapperObj, proposalObj);
        	assertTrue(compare);
        }
	}

}
