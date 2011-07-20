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

import org.junit.Test;

import de.hft_stuttgart.sopro.common.project.Project;

/**
 * @author sdegiorgi@gmail.com
 */
public class ProjectTest {

	/**
	 * Tests instantiation, getNumberOfJobs(), getNumberOfResources(),
	 * getProjectId() and getProjectName()
	 */
	@Test
	public void testProject() {
		String projectName = "TestProject";
		Project project = new Project(23, projectName, 42, 4);
		assertEquals(42, project.getNumberOfJobs());
		assertEquals(4, project.getNumberOfResources());
		assertEquals(23, project.getProjectId());
		assertEquals(projectName, project.getProjectName());
	}

}
