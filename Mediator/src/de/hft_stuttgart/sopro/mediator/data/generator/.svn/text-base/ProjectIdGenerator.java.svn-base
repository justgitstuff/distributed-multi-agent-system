package de.hft_stuttgart.sopro.mediator.data.generator;

/**
 * The singleton Id generator for Projects.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class ProjectIdGenerator extends AbstractIdGenerator {

	/**
	 * The singleton instance.
	 */
	private static ProjectIdGenerator instance = null;

	/**
	 * Default constructor which should not be called from the outside.
	 */
	private ProjectIdGenerator() {
	}

	/**
	 * Default constructor taking the id to set. This constructor should not be
	 * called from the outside.
	 * 
	 * @param id
	 *            The id to set.
	 */
	private ProjectIdGenerator(int id) {
		super.setId(id);
	}

	/**
	 * Returns a singleton instance of {@link ProjectIdGenerator}.
	 * 
	 * @return A singleton instance of {@link ProjectIdGenerator}.
	 */
	public static ProjectIdGenerator getInstance() {
		if (null == instance) {
			instance = new ProjectIdGenerator(0);
		}
		return instance;
	}
}
