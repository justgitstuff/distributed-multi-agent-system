/**
 * 
 */
package de.hft_stuttgart.sopro.mediator;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.JobName;

import org.junit.Test;

import de.hft_stuttgart.sopro.common.exceptions.JobInPermutationNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.ProposalNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.ResourceNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.StarttimeNotFoundException;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;
import de.hft_stuttgart.sopro.common.voting.VotingAlgorithmEnum;
import de.hft_stuttgart.sopro.mediator.data.generator.AgentIdGenerator;

/**
 * @author Administrator
 */
public class CapacityStarttimesTest {


	/**
	 * Test usage of the proposal generator
	 */
	@Test
	public void testUsage() {
		Mediator mediator = Mediator.getInstance();

		// Get all available projects
		List<IProject> availableProjects = mediator.getAvailableProjects();

		// Get the project with the given ID
		// Make the test with the project with the id
		int projectIndex = 5; //Should be the project X30_1
		IProject currentProject = availableProjects.get(projectIndex);
		int projectId = currentProject.getProjectId();

		// Set the negotiation paramenters for the test session
		int negotiationRounds = 5;
		int proposalsPerRound = 5;

		// Create two agent ids
		AgentIdGenerator agentIdGen = AgentIdGenerator.getInstance();
		int agentId1 = agentIdGen.getNextId();
		int agentId2 = agentIdGen.getNextId();

		// Join to the project
		mediator.joinProject(projectId, agentId1, negotiationRounds, proposalsPerRound, VotingAlgorithmEnum.BORDA);
		assertEquals(currentProject.getCurrentAgentsOnProject().size(), 1);
		mediator.joinProject(projectId, agentId2, negotiationRounds, proposalsPerRound, VotingAlgorithmEnum.BORDA);
		assertEquals(currentProject.getCurrentAgentsOnProject().size(), 2);

		// Get proposals for both agents
		ProposalComposition proposalsAgent1 = mediator.retrieveProposals(projectId, agentId1);
		ProposalComposition proposalsAgent2 = mediator.retrieveProposals(projectId, agentId2);
		assertEquals(proposalsAgent1.getNumberOfProposals(), proposalsPerRound);
		assertEquals(proposalsAgent1.getNumberOfProposals(), proposalsAgent2.getNumberOfProposals());
		try {
			// The proposals for both agents should be the same
			for (int i = 0; i < proposalsPerRound; i++) {
				//The proposals are equal --> use only the proposal of agent 1 
				IProposal proposal = proposalsAgent1.getProposalByIndex(i);
				
				//Make a list for each resource where we store the needed capacities
				int numOfRes = currentProject.getNumberOfResources();
				List<Integer> res1Capacities = new ArrayList<Integer>();
				List<Integer> res2Capacities = new ArrayList<Integer>();
				List<Integer> res3Capacities = new ArrayList<Integer>();
				List<Integer> res4Capacities = new ArrayList<Integer>();
				
				List<Integer> alreadyFinishedPermutationJobs = new ArrayList<Integer>();
				
				//Go throw the permutation and check the capacities
				int[] maxCapacities = currentProject.getMaxCapacities();
				for(int j=0; j<proposal.getPermutationSize(); j++){
					//Get the job
					IJob job = proposal.getPermutation(j);
					int duration = job.getDuration();
					//Go throw the resources and add the needed Capacity to the capacity array
					for(int n=0; n<numOfRes; n++){
						//Get the needed Capacity of resource 1
						int neededCapacity = job.retrieveResourceFromIndex(n);
						int starttime = job.retrieveStartTimeFromIndex(n);
						if(starttime!=-1){
							//Add the needed Capacity for the whole duration to the resouce capacity array
							for(int t=0; t<duration; t++){
								//Resource 1
								if(n==0){
									if(res1Capacities.size() <= starttime+t){
										while(res1Capacities.size() <= starttime+t){
											res1Capacities.add(new Integer(0));
										}
									}
									
									int currentCapacityValue = res1Capacities.get(starttime+t);
									res1Capacities.set(starttime+t, new Integer(currentCapacityValue + neededCapacity));
									currentCapacityValue = res1Capacities.get(starttime+t);
									assertTrue(currentCapacityValue<=maxCapacities[n]);
								}
								//Resource 2
								else if(n==1){
									if(res2Capacities.size() <= starttime+t){
										while(res2Capacities.size() <= starttime+t){
											res2Capacities.add(new Integer(0));
										}
									}
									
									int currentCapacityValue = res2Capacities.get(starttime+t);
									res2Capacities.set(starttime+t, new Integer(currentCapacityValue + neededCapacity));
									currentCapacityValue = res2Capacities.get(starttime+t);
									assertTrue(currentCapacityValue<=maxCapacities[n]);
								}
								//Resource 3
								else if(n==2){
									if(res3Capacities.size() <= starttime+t){
										while(res3Capacities.size() <= starttime+t){
											res3Capacities.add(new Integer(0));
										}
									}
									
									int currentCapacityValue = res3Capacities.get(starttime+t);
									res3Capacities.set(starttime+t, new Integer(currentCapacityValue + neededCapacity));
									currentCapacityValue = res3Capacities.get(starttime+t);
									assertTrue(currentCapacityValue<=maxCapacities[n]);
								}
								//Resource 4
								else{
									if(res4Capacities.size() <= starttime+t){
										while(res4Capacities.size() <= starttime+t){
											res4Capacities.add(new Integer(0));
										}
									}
									
									int currentCapacityValue = res4Capacities.get(starttime+t);
									res4Capacities.set(starttime+t, new Integer(currentCapacityValue + neededCapacity));
									currentCapacityValue = res4Capacities.get(starttime+t);
									assertTrue(currentCapacityValue<=maxCapacities[n]);
								}
							}
						}//end if starttime!=-1	
					}//end for numOfRes
					
					//Check if all predecessors of the job are allready finished
					boolean predecessorsInPermutation = this.checkPredecessorsInPermutation(job, alreadyFinishedPermutationJobs);
					assertTrue(predecessorsInPermutation);
					alreadyFinishedPermutationJobs.add(new Integer(job.getJobNumber()));
					
					//Check the endtimes of all predecessors --> so that they are smaller than the earliest starttime
					int earliestStarttime = Integer.MAX_VALUE;
					for(int l=0; l<numOfRes; l++){
						int currentStarttime = proposal.getStarttime(l, job.getJobNumber()-1);
						if( currentStarttime<earliestStarttime && currentStarttime!=-1){
							earliestStarttime = currentStarttime;
						}
					}
					//System.out.println("Earliest starttime: " + earliestStarttime);
					
					List<IJob> predecessors = proposal.retrievePermutationJobPredecessorsFromJob(job);
					int latestEndtime = 0;
					for(IJob predecessor : predecessors){
						for(int k=0; k<numOfRes; k++){
							int endtime = predecessor.retrieveEndTimeFromIndex(k);
							if(endtime > latestEndtime){
								latestEndtime = endtime;
							}					
						}
					}
					//System.out.println("latest endtime: " + latestEndtime);
					//System.out.println("Job nr: " + job.getJobNumber());
					//System.out.println();
					assertTrue(latestEndtime<=earliestStarttime);
					
				} //end:for over the permutation
			}//end: for numOfProposals
		} catch (ProposalNotFoundException e) {
				e.printStackTrace();
			} catch (JobInPermutationNotFoundException e) {
			e.printStackTrace();
		} catch (ResourceNotFoundException e) {
				e.printStackTrace();
		} catch (StarttimeNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkPredecessorsInPermutation(IJob job, List<Integer> alreadyFinishedPermutationJobs){
		for(IJob predecessor : job.getPredecessors()){
			int jobNr = predecessor.getJobNumber();
			if(!this.checkJobAlreadyFinished(jobNr, alreadyFinishedPermutationJobs)){
				return false;
			}
		}
		return true;
	}
	
	private boolean checkJobAlreadyFinished(int jobNr, List<Integer> alreadyFinishedPermutationJobs){
		for(Integer alreadyFinished:alreadyFinishedPermutationJobs){
			if(jobNr == alreadyFinished){
				return true;
			}
		}
		
		return false;
	}
}
