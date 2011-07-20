package de.hft_stuttgart.sopro.common.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.project.Job;

public class JobTest {

	/**
	 * Test to instantiate
	 */
	@Test
	public void testInstatiate() {
		IJob job = new Job(1);
		assertNotNull(job);
	}

	/**
	 * Test usage of setting Job properties
	 */
	@Test
	public void testUsage() {
		IJob job = new Job(1);

		// Create one predecessor and one successor
		IJob predecessor = new Job(2);
		IJob successor = new Job(3);

		// Create resources
		int[] resources = new int[2];

		job.setAgentId(1);
		job.setProjectId(12);
		job.setDuration(8);
		job.setPayment(-30.5);
		job.addPredecessor(predecessor);
		job.addSuccessor(successor);
		job.setResources(resources);

		// Check the values
		assertEquals(1, job.getAgentId());
		assertEquals(12, job.getProjectId());
		assertEquals(8, job.getDuration());
		assertEquals(1, job.getPredecessors().size());
		assertEquals(1, job.getSuccessors().size());
		assertEquals(2, job.getResources().length);
		assertEquals(2, job.getStartTimes().length);
	}

}
