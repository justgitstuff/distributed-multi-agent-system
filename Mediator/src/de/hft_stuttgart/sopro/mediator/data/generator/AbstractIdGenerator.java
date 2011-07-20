/*
 * $LastChangedRevision: 261 $ $LastChangedBy: edu $ $LastChangedDate:
 * 2009-11-02 18:11:39 +0100 (Mo, 02 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common
 * /data/IdGenerator.java $ $Id: IdGenerator.java 55 2009-11-02 17:11:39Z sandro
 * $
 */
package de.hft_stuttgart.sopro.mediator.data.generator;

/**
 * This is the base class for all Id Generators.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 * @author Annemarie Meissner
 * @see AgentIdGenerator
 * @see ProjectIdGenerator
 */
public abstract class AbstractIdGenerator {

	/**
	 * The id.
	 */
	private int id;

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the next available id.
	 * 
	 * @return The next available id.
	 */
	public int getNextId() {
		return ++id;
	}
}
