package de.hft_stuttgart.sopro.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.hft_stuttgart.sopro.common.exceptions.JobInPermutationNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.ProposalNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.ResourceNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.StarttimeNotFoundException;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.project.Project;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.ProposalComposition;
import de.hft_stuttgart.sopro.mediator.data.generator.ProposalGeneratorAbstract;
import de.hft_stuttgart.sopro.mediator.data.generator.ProposalGeneratorAdvancedAddition;
import de.hft_stuttgart.sopro.mediator.data.generator.ProposalGeneratorModelEnum;
import de.hft_stuttgart.sopro.mediator.data.generator.ProposalGeneratorPrimitive;
import de.hft_stuttgart.sopro.mediator.exceptions.GenerateProposalException;

public class ProposalGeneratorAdvancedAdditionTest {

	/**
	 * Test to instantiate (pretty needless)
	 */
	@Test
	public void testInstatiate() {
		// create test Project
		Project project = new Project(0, "Test project", 5, 1);
		ProposalGeneratorAbstract projectGen = new ProposalGeneratorAdvancedAddition(project, 5, 1);
		assertNotNull(projectGen);
	}

	/**
	 * Test usage of the ProposalGenerator
	 * 
	 * @throws ResourceNotFoundException
	 * @throws JobInPermutationNotFoundException 
	 * @throws StarttimeNotFoundException 
	 * @throws ProposalNotFoundException 
	 * @throws GenerateProposalException
	 */
	/**
	 * @throws ResourceNotFoundException
	 * @throws JobInPermutationNotFoundException
	 * @throws StarttimeNotFoundException
	 * @throws ProposalNotFoundException
	 */
	public void testUsage() throws ResourceNotFoundException, JobInPermutationNotFoundException, StarttimeNotFoundException, ProposalNotFoundException {

		// create test Project
		IProject project = new Project(0, "Test project", 5, 2);
		int numOfRes = project.getNumberOfResources();
		int[] maxCapacities = new int[numOfRes];
		maxCapacities[0] = 4;
		maxCapacities[1] = 3;
		project.setMaxCapacities(maxCapacities);
		/*
    	 * Test with the following network plan
    	 * 		2
    	 * 	  /	  \	
    	 * 	1 ----3--5
    	 * 	  \	--4-/
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

		// Generate a permutation of the jobs
		ProposalGeneratorAbstract proGen = new ProposalGeneratorAdvancedAddition(project, 5, 1);
		ProposalComposition proposalContainer = new ProposalComposition();
		
		try {
			proposalContainer = proGen.generateProposals();
		} catch (GenerateProposalException e) {
			e.printStackTrace();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(proposalContainer.getNumberOfProposals(), 1);
		
		//Get the generated Proposal
		IProposal proposal = proposalContainer.getProposalByIndex(0);

		assertEquals(5, proposal.getPermutationSize());

		// The first job in the array must be job number 1
		assertTrue(proposal.getPermutation(0).getJobNumber() == 1);

		// There are three possible solutions 1 2 3 4 5 || 1 2 4 3 5 || 1 4 2 3 5
		if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 3) {
			assertTrue(proposal.getPermutation(3).getJobNumber() == 4);
		} else if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 4) {
			assertTrue(proposal.getPermutation(3).getJobNumber() == 3);
		} else if (proposal.getPermutation(1).getJobNumber() == 4 && proposal.getPermutation(2).getJobNumber() == 2) {
			assertTrue(proposal.getPermutation(3).getJobNumber() == 3);
		} else
			// otherwise there is an error
			assertEquals(0, 1);

		// The last job in the array must be job number 5
		assertTrue(proposal.getPermutation(4).getJobNumber() == 5);

		/*
		 * update the pheromone matrix with the given permutation
		 */
		try {
			proGen.updatePheromoneMatrix(proposal, 1);
		} catch (JobInPermutationNotFoundException e) {
			e.printStackTrace();
		}
		
		//For each possible solution 1 2 3 4 5 || 1 2 4 3 5 || 1 4 2 3 5 check the pheromone matrix
		double eps = 1e-8;
		double updatedValue = 1.0 + ((ProposalGeneratorAdvancedAddition)proGen).getUpdateUnit();
		if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 3 && proposal.getPermutation(3).getJobNumber() == 4 ) {
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 2), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 3), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 4), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 5), updatedValue, eps);
			
			//Test some other values, that they are one
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 3), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 4), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 1), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 3), 1, eps);

		} else if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 4 && proposal.getPermutation(3).getJobNumber() == 3) {
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 2), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 4), updatedValue, eps);;
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 3), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 5), updatedValue, eps);
			
			//Test some other values, that they are one
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 3), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 3), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 1), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 5), 1, eps);
		} else if (proposal.getPermutation(1).getJobNumber() == 4 && proposal.getPermutation(2).getJobNumber() == 2 && proposal.getPermutation(3).getJobNumber() == 3) {
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 4), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 2), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 3), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 5), updatedValue, eps);
			
			//Test some other values, that they are one
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 3), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 5), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 4), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 5), 1, eps);
		} else
			// otherwise there is an error
			assertEquals(0, 1);
		
		/*
		 * generation of the permutation OK test now the decoding, which means
		 * the generation of the starttimes of the permutation
		 */

		// The numbers of the integer lists must be equal to the number of
		// resources
		assertEquals(proposal.getNumberOfResourceStarttimes(), project.getNumberOfResources());

    	/*
    	 * There are three possible permutations 1 2 3 4 5 || 1 2 4 3 5 || 1 4 2 3 5
    	 * The jobs has the following job data:
    	 * Job1:  duration = 0; unitsPerPeriod R1 = 0; unitsPerPeriod R2 = 0; 
    	 * Job2:  duration = 2; unitsPerPeriod R1 = 1; unitsPerPeriod R2 = 1; 
    	 * Job3:  duration = 3; unitsPerPeriod R1 = 1; unitsPerPeriod R2 = 1; 
    	 * Job4:  duration = 4; unitsPerPeriod R1 = 3; unitsPerPeriod R2 = 3; 
    	 * Job5:  duration = 0; unitsPerPeriod R1 = 0; unitsPerPeriod R2 = 0; 
    	 * 
    	 * For all permutations there are one starttime solutions for resource 1:
    	 * 			[0 0 2 0 5]
    	 *R1	^
    	 * 		|---------------
    	 * 	4	|				|
    	 * 		|				|
    	 * 	3	|		4		|
    	 *		|				|
    	 * 	2	|				|
    	 * 		|-------------------		
    	 * 	1	|   2   |	  3		|
    	 * 		----------------------->
    	 * 		0	1	2	3	4	5
    	 */ 
		assertTrue(proposal.getStarttime(0, 0) == -1); // Start-Dummy-Job
		assertTrue(proposal.getStarttime(0, 1) == 0);
		assertTrue(proposal.getStarttime(0, 2) == 2);
		assertTrue(proposal.getStarttime(0, 3) == 0);
		assertTrue(proposal.getStarttime(0, 4) == 5); // End-Dummy-Job

    	/*
    	 * There are three possible permutations 1 2 3 4 5 || 1 2 4 3 5 || 1 4 2 3 5
    	 * The jobs has the following job data:
    	 * Job1:  duration = 0; unitsPerPeriod R1 = 0; unitsPerPeriod R2 = 0; 
    	 * Job2:  duration = 2; unitsPerPeriod R1 = 1; unitsPerPeriod R2 = 1; 
    	 * Job3:  duration = 3; unitsPerPeriod R1 = 1; unitsPerPeriod R2 = 1; 
    	 * Job4:  duration = 4; unitsPerPeriod R1 = 3; unitsPerPeriod R2 = 3; 
    	 * Job5:  duration = 0; unitsPerPeriod R1 = 0; unitsPerPeriod R2 = 0; 
    	 * 
    	 * For all permutations there are three starttime solutions for resource 2
    	 * 
    	 * 			p(1 4 2 3 5) --> [0 4 6 0 9]					p(1 2 3 4 5) --> [0 0 2 5 9]
    	 *
    	 *R2	^											R2	^
    	 * 		|---------------								|					 ---------------
    	 * 	3	|				|							3	|					|				|
    	 *		|				|								|					|				|
    	 * 	2	|		4		|							2	|					|		4		|
    	 * 		|				|-------------------			|-------------------|				|
    	 * 	1	|  				|	2	|	  3		|		1	|  2   |	  3		|				|
    	 * 		--------------------------------------->		--------------------------------------->
    	 * 		0	1	2	3	4	5	6	7	8	9			0	1	2	3	4	5	6	7	8	9
    	 * 
    	 * 		
    	 * 			p(1 2 4 3 5) --> [0 0 6 2 9]	
    	 *
    	 *R2	^										
    	 * 		|		 ---------------			
    	 * 	3	|		|				|			
    	 *		|		|				|					
    	 * 	2	|		|		4		|							
    	 * 		|-------|				|-----------		
    	 * 	1	|  	2	|	  			|	  3		|		
    	 * 		--------------------------------------->	
    	 * 		0	1	2	3	4	5	6	7	8	9		
    	 */ 

		// Get the starttime list of the resource2
		// p(1 2 3 4 5) --> [0 0 2 5 9]
		if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 3 && proposal.getPermutation(3).getJobNumber() == 4) {
			assertTrue(proposal.getStarttime(1, 0) == -1);
			assertTrue(proposal.getStarttime(1, 1) == 0);
			assertTrue(proposal.getStarttime(1, 2) == 2);
			assertTrue(proposal.getStarttime(1, 3) == 5);
			assertTrue(proposal.getStarttime(1, 4) == 9);
		}
		// p(1 2 4 3 5) --> [0 0 6 2 9]
		else if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 4 && proposal.getPermutation(3).getJobNumber() == 3) {
			assertTrue(proposal.getStarttime(1, 0) == -1);
			assertTrue(proposal.getStarttime(1, 1) == 0);
			assertTrue(proposal.getStarttime(1, 2) == 6);
			assertTrue(proposal.getStarttime(1, 3) == 2);
			assertTrue(proposal.getStarttime(1, 4) == 9);
		}
		// p(1 4 2 3 5) --> [0 4 6 0 9]
		else if (proposal.getPermutation(1).getJobNumber() == 4 && proposal.getPermutation(2).getJobNumber() == 2 && proposal.getPermutation(3).getJobNumber() == 3) {
			assertTrue(proposal.getStarttime(1, 0) == -1);
			assertTrue(proposal.getStarttime(1, 1) == 4);
			assertTrue(proposal.getStarttime(1, 2) == 6);
			assertTrue(proposal.getStarttime(1, 3) == 0);
			assertTrue(proposal.getStarttime(1, 4) == 9);
		} else
			// otherwise there is an error
			assertEquals(0, 1);
	}
	
	/**
	 * Test usage of the ProposalGenerator
	 * 
	 * @throws ResourceNotFoundException
	 * @throws JobInPermutationNotFoundException 
	 * @throws StarttimeNotFoundException 
	 * @throws ProposalNotFoundException 
	 * @throws GenerateProposalException
	 */
	@Test
	public void testUsageStarttimesJobOriented() throws ResourceNotFoundException, JobInPermutationNotFoundException, StarttimeNotFoundException, ProposalNotFoundException {

		// create test Project
		IProject project = new Project(0, "Test project", 6, 2);
		int numOfRes = project.getNumberOfResources();
		int[] maxCapacities = new int[numOfRes];
		maxCapacities[0] = 4;
		maxCapacities[1] = 3;
		project.setMaxCapacities(maxCapacities);
		/*
    	 * Test with the following network plan
    	 * 		2
    	 * 	  /	  \	
    	 * 	1 ----3--5
    	 * 	  \	--4-/
    	 */
		// Get the jobs of the project and fill them with data
		IJob job1 = project.retrieveJobFromJobNumber(1);
		IJob job2 = project.retrieveJobFromJobNumber(2);
		IJob job3 = project.retrieveJobFromJobNumber(3);
		IJob job4 = project.retrieveJobFromJobNumber(4);
		IJob job5 = project.retrieveJobFromJobNumber(5);
		IJob job6 = project.retrieveJobFromJobNumber(6);

		// set resources and duration of the jobs
		int[] resources1 = new int[numOfRes];
		resources1[0] = 0;
		resources1[1] = 0;
		job1.setResources(resources1);
		job1.setDuration(0);

		int[] resources2 = new int[numOfRes];
		resources2[0] = 2;
		resources2[1] = 0;
		job2.setResources(resources2);
		job2.setDuration(2);

		int[] resources3 = new int[numOfRes];
		resources3[0] = 3;
		resources3[1] = 2;
		job3.setResources(resources3);
		job3.setDuration(3);

		int[] resources4 = new int[numOfRes];
		resources4[0] = 4;
		resources4[1] = 3;
		job4.setResources(resources4);
		job4.setDuration(1);

		int[] resources5 = new int[numOfRes];
		resources5[0] = 3;
		resources5[1] = 2;
		job5.setResources(resources5);
		job5.setDuration(4);
		
		int[] resources6 = new int[numOfRes];
		resources6[0] = 0;
		resources6[1] = 0;
		job6.setResources(resources6);
		job6.setDuration(0);

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
		job5.addSuccessor(job6);
		
		job6.addPredecessor(job5);

		// Generate a permutation of the jobs
		ProposalGeneratorAbstract proGen = new ProposalGeneratorAdvancedAddition(project, 5, 1);
		proGen.setStarttimeModel(ProposalGeneratorModelEnum.MULTI_STARTTIMES);
		ProposalComposition proposalContainer = new ProposalComposition();
		
		try {
			proposalContainer = proGen.generateProposals();
		} catch (GenerateProposalException e) {
			e.printStackTrace();
		}
		catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(proposalContainer.getNumberOfProposals(), 1);
		
		//Get the generated Proposal
		IProposal proposal = proposalContainer.getProposalByIndex(0);


		// For that who wants to see the permutations
		/*System.out.print("Permutation: "); 
		for(IJob jobPerm : permutation){
			System.out.print( jobPerm.getJobNumber() + " " ); 
		}
		System.out.println();*/

		assertEquals(6, proposal.getPermutationSize());

		// The first job in the array must be job number 1
		assertTrue(proposal.getPermutation(0).getJobNumber() == 1);

		// There are three possible solutions 1 2 3 4 5 6 || 1 2 4 3 5 6 || 1 4 2 3 5 6
		if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 3) {
			assertTrue(proposal.getPermutation(3).getJobNumber() == 4);
		} else if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 4) {
			assertTrue(proposal.getPermutation(3).getJobNumber() == 3);
		} else if (proposal.getPermutation(1).getJobNumber() == 4 && proposal.getPermutation(2).getJobNumber() == 2) {
			assertTrue(proposal.getPermutation(3).getJobNumber() == 3);
		} else
			// otherwise there is an error
			assertEquals(0, 1);

		// The last two jobs in the array must be job number 5 and 6
		assertTrue(proposal.getPermutation(4).getJobNumber() == 5);
		assertTrue(proposal.getPermutation(5).getJobNumber() == 6);

		/*
		 * update the pheromone matrix with the given permutation
		 */
		try {
			proGen.updatePheromoneMatrix(proposal, 1);
		} catch (JobInPermutationNotFoundException e) {
			e.printStackTrace();
		}
		
		//For each possible solution 1 2 3 4 5 || 1 2 4 3 5 || 1 4 2 3 5 check the pheromone matrix
		double eps = 1e-8;
		double updatedValue = 1.0 + ((ProposalGeneratorAdvancedAddition)proGen).getUpdateUnit();
		if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 3 && proposal.getPermutation(3).getJobNumber() == 4 ) {
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 2), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 3), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 4), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 5), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(5, 6), updatedValue, eps);
			
			//Test some other values, that they are one
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 3), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 4), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 1), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 3), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(5, 2), 1, eps);

		} else if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 4 && proposal.getPermutation(3).getJobNumber() == 3) {
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 2), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 4), updatedValue, eps);;
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 3), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 5), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(5, 6), updatedValue, eps);
			
			//Test some other values, that they are one
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 3), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 3), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 1), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 5), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(5, 4), 1, eps);
		} else if (proposal.getPermutation(1).getJobNumber() == 4 && proposal.getPermutation(2).getJobNumber() == 2 && proposal.getPermutation(3).getJobNumber() == 3) {
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 4), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 2), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 3), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 5), updatedValue, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(5, 6), updatedValue, eps);
			
			//Test some other values, that they are one
			assertEquals(proGen.getPheromoneMatrixValueAt(1, 3), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(2, 5), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(3, 4), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(4, 5), 1, eps);
			assertEquals(proGen.getPheromoneMatrixValueAt(5, 2), 1, eps);
		} else
			// otherwise there is an error
			assertEquals(0, 1);
		
		/*
		 * generation of the permutation OK test now the decoding, which means
		 * the generation of the starttimes of the permutation
		 */

		// The numbers of the integer lists must be equal to the number of
		// resources
		assertEquals(proposal.getNumberOfResourceStarttimes(), project.getNumberOfResources());

		
		//Check the starttimes depending on the starttimeModel of the proposalGenerator
		if(proGen.getStarttimeModel() == ProposalGeneratorModelEnum.ONE_STARTTIME){
	    	/*
	    	 * There are three possible permutations 1 2 3 4 5 6 || 1 2 4 3 5 6 || 1 4 2 3 5 6
	    	 * The jobs has the following job data:
	    	 * Job1:  duration = 0; unitsPerPeriod R1 = 0; unitsPerPeriod R2 = 0; 
	    	 * Job2:  duration = 2; unitsPerPeriod R1 = 2; unitsPerPeriod R2 = 0; 
	    	 * Job3:  duration = 3; unitsPerPeriod R1 = 3; unitsPerPeriod R2 = 2; 
	    	 * Job4:  duration = 1; unitsPerPeriod R1 = 4; unitsPerPeriod R2 = 3; 
	    	 * Job5:  duration = 4; unitsPerPeriod R1 = 3; unitsPerPeriod R2 = 2; 
	    	 * Job6:  duration = 0; unitsPerPeriod R1 = 0; unitsPerPeriod R2 = 0; 
	    	 * 
	    	 * For all permutations there are the following starttime solutions for resource 1 and 2:
	    	 */
			// There are three possible solutions 1 2 3 4 5 6 || 1 2 4 3 5 6 || 1 4 2 3 5 6
			if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 3 && proposal.getPermutation(3).getJobNumber() == 4) {
				/* 			1 2 3 4 5 6 --> [-1 0 2 5 6 10]
		      	 *R1	^
		      	 * 		|--------------------------------------------- Max Capacity
		      	 * 	4	|					|	|
		      	 * 		|		 -----------|	| ---------------	
		      	 * 	3	|		|			|	|				|
		      	 *		|-------|			| 4	|				|
		      	 * 	2	|		|	  3		|	|		5		|
		      	 * 		|	2	|			| 	|				|
		      	 * 	1	|       |			|	|				|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 * 
		      	 * 			1 2 3 4 5 6 --> [-1 -1 2 0 6 10]
		      	 * R2	^
		      	 * 		|
		      	 * 	4	|				
		      	 * 		|--------------------------------------------- Max Capacity	
		      	 * 	3	|	|			
		      	 *		|	|	 -----------	 ---------------
		      	 * 	2	| 4	|	|  			|	|				|
		      	 * 		|	|	|	  3		|	| 		5		|
		      	 * 	1	|   |	|			|	|				|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 */ 
				assertTrue(proposal.getStarttime(0, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(0, 1) == 0);
				assertTrue(proposal.getStarttime(0, 2) == 2);
				assertTrue(proposal.getStarttime(0, 3) == 5);
				assertTrue(proposal.getStarttime(0, 4) == 6); 
				assertTrue(proposal.getStarttime(0, 5) == 10); // End-Dummy-Job
				
				assertTrue(proposal.getStarttime(1, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(1, 1) == -1);
				assertTrue(proposal.getStarttime(1, 2) == 2);
				assertTrue(proposal.getStarttime(1, 3) == 0);
				assertTrue(proposal.getStarttime(1, 4) == 6); 
				assertTrue(proposal.getStarttime(1, 5) == 10); // End-Dummy-Job
			} else if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 4 && proposal.getPermutation(3).getJobNumber() == 3) {
				/* 			1 2 4 3 5 6 --> [-1 0 3 2 6 10]
		      	 *R1	^
		      	 * 		|--------------------------------------------- Max Capacity
		      	 * 	4	|		|	|		
		      	 * 		|		| 	|----------- ---------------
		      	 * 	3	|		|	|			|				|
		      	 *		|-------| 4	|			|				|
		      	 * 	2	|		|	| 	  3		|		5		|
		      	 * 		|	2	|	|			|				|
		      	 * 	1	|       |	|			|				|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 * 
		      	 * 			1 2 4 3 5 6 --> [-1 -1 2 0 6 10]
		      	 * R2	^
		      	 * 		|
		      	 * 	4	|				
		      	 * 		|--------------------------------------------- Max Capacity	
		      	 * 	3	|	|			
		      	 *		|	|	 -----------
		      	 * 	2	| 4	|	|  			|
		      	 * 		|	|	|	  3		|	 ---------------
		      	 * 	1	|   |	|			|	|		5		|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 */ 
				assertTrue(proposal.getStarttime(0, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(0, 1) == 0);
				assertTrue(proposal.getStarttime(0, 2) == 3);
				assertTrue(proposal.getStarttime(0, 3) == 2);
				assertTrue(proposal.getStarttime(0, 4) == 6); 
				assertTrue(proposal.getStarttime(0, 5) == 10); // End-Dummy-Job
				
				assertTrue(proposal.getStarttime(1, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(1, 1) == -1);
				assertTrue(proposal.getStarttime(1, 2) == 2);
				assertTrue(proposal.getStarttime(1, 3) == 0);
				assertTrue(proposal.getStarttime(1, 4) == 6); 
				assertTrue(proposal.getStarttime(1, 5) == 10); // End-Dummy-Job
			} else if (proposal.getPermutation(1).getJobNumber() == 4 && proposal.getPermutation(2).getJobNumber() == 2 && proposal.getPermutation(3).getJobNumber() == 3) {
				/* 			1 4 2 3 5 6 --> [-1 1 3 0 6 10]
		      	 *R1	^
		      	 * 		|--------------------------------------------- Max Capacity
		      	 * 	4	|	|			
		      	 * 		|	|		 ----------- ---------------
		      	 * 	3	|	|		|			|				|
		      	 *		| 4	|-------|			|				|
		      	 * 	2	|	|		|	  3		|		5		|
		      	 * 		|	|	2	|			|				|
		      	 * 	1	| 	|		|			|				|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 * 
		      	 * 			1 4 2 3 5 6 --> [-1 -1 3 0 6 10]
		      	 * R2	^
		      	 * 		|
		      	 * 	4	|				
		      	 * 		|--------------------------------------------- Max Capacity	
		      	 * 	3	|	|			
		      	 *		|	|		 -----------
		      	 * 	2	| 4	|		|  			|
		      	 * 		|	|		|	  3		|---------------
		      	 * 	1	|   |		|			|		5		|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 */ 
				assertTrue(proposal.getStarttime(0, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(0, 1) == 1);
				assertTrue(proposal.getStarttime(0, 2) == 3);
				assertTrue(proposal.getStarttime(0, 3) == 0);
				assertTrue(proposal.getStarttime(0, 4) == 6); 
				assertTrue(proposal.getStarttime(0, 5) == 10); // End-Dummy-Job
				
				assertTrue(proposal.getStarttime(1, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(1, 1) == -1);
				assertTrue(proposal.getStarttime(1, 2) == 3);
				assertTrue(proposal.getStarttime(1, 3) == 0);
				assertTrue(proposal.getStarttime(1, 4) == 6); 
				assertTrue(proposal.getStarttime(1, 5) == 10); // End-Dummy-Job
			} else
				// otherwise there is an error
				assertEquals(0, 1);
		}
		else if(proGen.getStarttimeModel() == ProposalGeneratorModelEnum.ONE_STARTTIME){
		  	/*
	    	 * There are three possible permutations 1 2 3 4 5 6 || 1 2 4 3 5 6 || 1 4 2 3 5 6
	    	 * The jobs has the following job data:
	    	 * Job1:  duration = 0; unitsPerPeriod R1 = 0; unitsPerPeriod R2 = 0; 
	    	 * Job2:  duration = 2; unitsPerPeriod R1 = 2; unitsPerPeriod R2 = 0; 
	    	 * Job3:  duration = 3; unitsPerPeriod R1 = 3; unitsPerPeriod R2 = 2; 
	    	 * Job4:  duration = 1; unitsPerPeriod R1 = 4; unitsPerPeriod R2 = 3; 
	    	 * Job5:  duration = 4; unitsPerPeriod R1 = 3; unitsPerPeriod R2 = 2; 
	    	 * Job6:  duration = 0; unitsPerPeriod R1 = 0; unitsPerPeriod R2 = 0; 
	    	 * 
	    	 * For all permutations there are the following starttime solutions for resource 1 and 2:
	    	 */
			// There are three possible solutions 1 2 3 4 5 6 || 1 2 4 3 5 6 || 1 4 2 3 5 6
			if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 3 && proposal.getPermutation(3).getJobNumber() == 4) {
				/* 			1 2 3 4 5 6 --> [-1 0 2 5 6 10]
		      	 *R1	^
		      	 * 		|--------------------------------------------- Max Capacity
		      	 * 	4	|					|	|
		      	 * 		|		 -----------|	| ---------------	
		      	 * 	3	|		|			|	|				|
		      	 *		|-------|			| 4	|				|
		      	 * 	2	|		|	  3		|	|		5		|
		      	 * 		|	2	|			| 	|				|
		      	 * 	1	|       |			|	|				|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 * 
		      	 * 			1 2 3 4 5 6 --> [-1 -1 2 0 6 10]
		      	 * R2	^
		      	 * 		|
		      	 * 	4	|				
		      	 * 		|--------------------------------------------- Max Capacity	
		      	 * 	3	|					|	|
		      	 *		|		 -----------|	|---------------
		      	 * 	2	| 		|  			| 4	|				|
		      	 * 		|		|	  3		|	| 		5		|
		      	 * 	1	|   	|			|	|				|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 */ 
				assertTrue(proposal.getStarttime(0, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(0, 1) == 0);
				assertTrue(proposal.getStarttime(0, 2) == 2);
				assertTrue(proposal.getStarttime(0, 3) == 5);
				assertTrue(proposal.getStarttime(0, 4) == 6); 
				assertTrue(proposal.getStarttime(0, 5) == 10); // End-Dummy-Job
				
				assertTrue(proposal.getStarttime(1, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(1, 1) == -1);
				assertTrue(proposal.getStarttime(1, 2) == 2);
				assertTrue(proposal.getStarttime(1, 3) == 5);
				assertTrue(proposal.getStarttime(1, 4) == 6); 
				assertTrue(proposal.getStarttime(1, 5) == 10); // End-Dummy-Job
			} else if (proposal.getPermutation(1).getJobNumber() == 2 && proposal.getPermutation(2).getJobNumber() == 4 && proposal.getPermutation(3).getJobNumber() == 3) {
				/* 			1 2 4 3 5 6 --> [-1 0 3 2 6 10]
		      	 *R1	^
		      	 * 		|--------------------------------------------- Max Capacity
		      	 * 	4	|		|	|		
		      	 * 		|		| 	|----------- ---------------
		      	 * 	3	|		|	|			|				|
		      	 *		|-------| 4	|			|				|
		      	 * 	2	|		|	| 	  3		|		5		|
		      	 * 		|	2	|	|			|				|
		      	 * 	1	|       |	|			|				|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 * 
		      	 * 			1 2 4 3 5 6 --> [-1 -1 2 0 6 10]
		      	 * R2	^
		      	 * 		|
		      	 * 	4	|				
		      	 * 		|--------------------------------------------- Max Capacity	
		      	 * 	3	|		|	|		
		      	 *		|		|	|-----------
		      	 * 	2	| 		| 4	|  			|
		      	 * 		|		|	|	  3		|---------------
		      	 * 	1	|   	|	|			|		5		|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 */ 
				assertTrue(proposal.getStarttime(0, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(0, 1) == 0);
				assertTrue(proposal.getStarttime(0, 2) == 3);
				assertTrue(proposal.getStarttime(0, 3) == 2);
				assertTrue(proposal.getStarttime(0, 4) == 6); 
				assertTrue(proposal.getStarttime(0, 5) == 10); // End-Dummy-Job
				
				assertTrue(proposal.getStarttime(1, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(1, 1) == -1);
				assertTrue(proposal.getStarttime(1, 2) == 3);
				assertTrue(proposal.getStarttime(1, 3) == 2);
				assertTrue(proposal.getStarttime(1, 4) == 6); 
				assertTrue(proposal.getStarttime(1, 5) == 10); // End-Dummy-Job
			} else if (proposal.getPermutation(1).getJobNumber() == 4 && proposal.getPermutation(2).getJobNumber() == 2 && proposal.getPermutation(3).getJobNumber() == 3) {
				/* 			1 4 2 3 5 6 --> [-1 1 3 0 6 10]
		      	 *R1	^
		      	 * 		|--------------------------------------------- Max Capacity
		      	 * 	4	|	|			
		      	 * 		|	|		 ----------- ---------------
		      	 * 	3	|	|		|			|				|
		      	 *		| 4	|-------|			|				|
		      	 * 	2	|	|		|	  3		|		5		|
		      	 * 		|	|	2	|			|				|
		      	 * 	1	| 	|		|			|				|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 * 
		      	 * 			1 4 2 3 5 6 --> [-1 -1 3 0 6 10]
		      	 * R2	^
		      	 * 		|
		      	 * 	4	|				
		      	 * 		|--------------------------------------------- Max Capacity	
		      	 * 	3	|	|			
		      	 *		|	|		 -----------
		      	 * 	2	| 4	|		|  			|
		      	 * 		|	|		|	  3		|---------------
		      	 * 	1	|   |		|			|		5		|
		      	 * 		------------------------------------------------>
		      	 * 		0	1	2	3	4	5	6	7	8	9	10	11
		      	 */ 
				assertTrue(proposal.getStarttime(0, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(0, 1) == 1);
				assertTrue(proposal.getStarttime(0, 2) == 3);
				assertTrue(proposal.getStarttime(0, 3) == 0);
				assertTrue(proposal.getStarttime(0, 4) == 6); 
				assertTrue(proposal.getStarttime(0, 5) == 10); // End-Dummy-Job
				
				assertTrue(proposal.getStarttime(1, 0) == -1); // Start-Dummy-Job
				assertTrue(proposal.getStarttime(1, 1) == -1);
				assertTrue(proposal.getStarttime(1, 2) == 3);
				assertTrue(proposal.getStarttime(1, 3) == 0);
				assertTrue(proposal.getStarttime(1, 4) == 6); 
				assertTrue(proposal.getStarttime(1, 5) == 10); // End-Dummy-Job
			} else
				// otherwise there is an error
				assertEquals(0, 1);
		}
		
	}
}
