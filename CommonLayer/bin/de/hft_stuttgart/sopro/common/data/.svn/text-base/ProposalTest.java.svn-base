/*
 * $LastChangedRevision: 243 $ $LastChangedBy: edu $ $LastChangedDate:
 * 2009-11-03 20:37:51 +0100 (Di, 03 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/test/de/hft_stuttgart/sopro/common
 * /data/ProjectTest.java $ $Id: ProjectTest.java 92 2009-11-03 19:37:51Z sandro
 * $
 */
package de.hft_stuttgart.sopro.common.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.hft_stuttgart.sopro.common.exceptions.JobInPermutationNotFoundException;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.project.Project;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.Proposal;

/**
 * @author Annemarie Meissner
 */
public class ProposalTest {

	/**
	 * @throws JobInPermutationNotFoundException
	 */
	@Test
	public void testProposal() throws JobInPermutationNotFoundException {

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

		// Create a test permutation
		List<IJob> testPermutation = new ArrayList<IJob>();
		// add all jobs
		testPermutation.add(job1);
		testPermutation.add(job2);
		testPermutation.add(job3);
		testPermutation.add(job4);
		testPermutation.add(job5);

		IProposal proposal = new Proposal(numOfJobs, numOfRes);

		// Set the permutation
		proposal.setPermutation(testPermutation);
		assertEquals((proposal.getPermutation(0)).getJobNumber(), 1);
		assertEquals((proposal.getPermutation(1)).getJobNumber(), 2);
		assertEquals((proposal.getPermutation(2)).getJobNumber(), 3);
		assertEquals((proposal.getPermutation(3)).getJobNumber(), 4);
		assertEquals((proposal.getPermutation(4)).getJobNumber(), 5);

		assertEquals(proposal.getPermutationSize(), numOfJobs);

		// If the permutation change outside of the Proposal the proposals
		// permutation should not be touched
		testPermutation.clear();
		assertEquals(proposal.getPermutationSize(), numOfJobs);

		// Generate Starttimes
		List<List<Integer>> starttimes = new ArrayList<List<Integer>>(numOfRes);
		for (int i = 0; i < numOfRes; i++) {
			List<Integer> resStarttime = new ArrayList<Integer>(numOfJobs);
			for (int j = 0; j < numOfJobs; j++) {
				// For testing initialize all starttimes with zero
				resStarttime.add(new Integer(0));
			}

			// Add resStarttimes to the starttimes
			starttimes.add(resStarttime);
		}

		proposal.setStarttimes(starttimes);
		assertEquals(proposal.getNumberOfResourceStarttimes(), numOfRes);

		// If the Starttimes change outside of the Proposal the proposals
		// Starttimes should not be touched
		starttimes.clear();
		assertEquals(proposal.getNumberOfResourceStarttimes(), numOfRes);
	}

}
