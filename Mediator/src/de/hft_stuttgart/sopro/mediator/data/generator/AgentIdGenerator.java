package de.hft_stuttgart.sopro.mediator.data.generator;

/**
 * The singleton Id Generator for Agents.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class AgentIdGenerator extends AbstractIdGenerator {

	/**
	 * The singleton instance.
	 */
	private static AgentIdGenerator instance = null;

	/**
	 * Default constructor which should not be called from the outside.
	 */
	private AgentIdGenerator() {
	}

	/**
	 * Default constructor taking the id to set. This constructor should not be
	 * called from the outside.
	 * 
	 * @param id
	 *            The id to set.
	 */
	private AgentIdGenerator(int id) {
		super.setId(id);
	}

	/**
	 * Returns a singleton instance of {@link AgentIdGenerator}.
	 * 
	 * @return A singleton instance of {@link AgentIdGenerator}.
	 */
	public static AgentIdGenerator getInstance() {
		if (null == instance) {
			instance = new AgentIdGenerator(0);
		}
		return instance;
	}
}
