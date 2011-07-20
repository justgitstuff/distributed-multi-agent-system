package de.hft_stuttgart.sopro.common.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.hft_stuttgart.sopro.common.exceptions.AgentIndexOutOfBoundsException;
import de.hft_stuttgart.sopro.common.exceptions.PaymentDatatNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.ProjectNotFoundException;
import de.hft_stuttgart.sopro.common.project.IProject;
import de.hft_stuttgart.sopro.common.project.Project;
import de.hft_stuttgart.sopro.common.project.ProjectComposition;

public class ProjectCompositionTest {

	/**
	 * Test to instantiate
	 */
	@Test
	public void testInstatiate() {
		ProjectComposition allProjects = new ProjectComposition();
		assertNotNull(allProjects);
	}

	/**
	 * Test usage of setting ProjectComposition properties
	 * 
	 * @throws ProjectNotFoundException
	 * @throws AgentIndexOutOfBoundsException 
	 * @throws PaymentDatatNotFoundException 
	 */
	@Test
	public void testUsage() throws ProjectNotFoundException, AgentIndexOutOfBoundsException, PaymentDatatNotFoundException {
		ProjectComposition allProjects = new ProjectComposition();

		// Create new projects
		IProject project1 = new Project(0, "Project_1", 2, 2);
		IProject project2 = new Project(1, "Project_2", 5, 4);

		// add projects to composition
		allProjects.addProject(project1);
		allProjects.addProject(project2);

		// Get project by id
		assertEquals(0, (allProjects.getProjectById(0)).getProjectId());

		// Get project by index
		assertEquals(1, (allProjects.getProjectByIndex(1)).getProjectId());

		// Check size
		assertEquals(2, allProjects.getNumberOfProjects());

		// Create payments for the projects with the same number of payments as
		// the jobs in the project
		List<Double> paymentProject1Agent1 = new ArrayList<Double>(2);
		paymentProject1Agent1.add(new Double(0));
		paymentProject1Agent1.add(new Double(0));
		List<Double> paymentProject1Agent2 = new ArrayList<Double>(2);
		paymentProject1Agent2.add(new Double(0));
		paymentProject1Agent2.add(new Double(0));
		paymentProject1Agent2.add(new Double(0));
		
		List<Double> paymentProject2Agent1 = new ArrayList<Double>(5);
		paymentProject2Agent1.add(new Double(0));
		paymentProject2Agent1.add(new Double(10));
		paymentProject2Agent1.add(new Double(10));
		paymentProject2Agent1.add(new Double(10));
		List<Double> paymentProject2Agent2 = new ArrayList<Double>(5);
		paymentProject2Agent2.add(new Double(0));
		paymentProject2Agent2.add(new Double(10));
		paymentProject2Agent2.add(new Double(10));
		paymentProject2Agent2.add(new Double(10));
		paymentProject2Agent2.add(new Double(0));
		
		//Set the payments in the ProjectComposition
		allProjects.setPaymentDataOfProject(0, paymentProject1Agent1, 0);
		allProjects.setPaymentDataOfProject(0, paymentProject1Agent2, 1);
		
		allProjects.setPaymentDataOfProject(1, paymentProject2Agent1, 0);
		allProjects.setPaymentDataOfProject(1, paymentProject2Agent2, 1);

		assertEquals(allProjects.getPaymentDataOfProject(0, 0).size(), 2);
		assertEquals(allProjects.getPaymentDataOfProject(0, 1).size(), 3);
		
		assertEquals(allProjects.getPaymentDataOfProject(1, 0).size(), 4);
		assertEquals(allProjects.getPaymentDataOfProject(1, 1).size(), 5);
	}
}
