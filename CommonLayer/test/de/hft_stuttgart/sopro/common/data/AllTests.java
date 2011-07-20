/*
 * $LastChangedRevision: 500 $ $LastChangedBy: edu $ $LastChangedDate:
 * 2009-11-04 16:00:32 +0100 (Mi, 04 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/test/de/hft_stuttgart/sopro/common
 * /data/AllTests.java $ $Id: AllTests.java 500 2009-11-24 07:56:28Z edu $
 */
package de.hft_stuttgart.sopro.common.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author sdegiorgi@gmail.com
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { JobTest.class, ProjectCompositionTest.class, ProjectTest.class, ProposalTest.class })
public class AllTests {

}
