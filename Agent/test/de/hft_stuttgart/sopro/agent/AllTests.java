/*
 * $LastChangedRevision: 556 $ $LastChangedBy: annemarie $ $LastChangedDate:
 * 2009-11-02 19:51:13 +0100 (Mo, 02 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/Agent/test/de/hft_stuttgart/sopro/agent/AllTests.java $ $Id:
 * AllTests.java 71 2009-11-02 18:51:13Z sandro $
 */
package de.hft_stuttgart.sopro.agent;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author sdegiorgi@gmail.com
 */

@RunWith(Suite.class)
@Suite.SuiteClasses( { AgentTest.class, JobConverterTest.class, ProjectConverterTest.class, ProposalCompositionConverterTest.class, ProposalConverterTest.class })
public class AllTests {

}
