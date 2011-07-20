package de.hft_stuttgart.sopro.mediator.registration;

import java.util.ArrayList;
import java.util.List;

import de.hft_stuttgart.sopro.mediator.data.generator.AgentIdGenerator;

/**
 * This class enables the registration of Agents.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class Registration {

	/**
	 * A list of Ids from previous registered Agents.
	 */
	private static List<Integer> registeredAgents = new ArrayList<Integer>();

	/**
	 * Registers an Agent.
	 * 
	 * @return A unique id.
	 */
	public static int registerAgent() {
		int registeredAgentId = AgentIdGenerator.getInstance().getNextId();
		registeredAgents.add(new Integer(registeredAgentId));
		return registeredAgentId;
	}

	/**
	 * Unregisters an Agent from the Mediator.
	 * 
	 * @param agentId
	 *            The id of the Agent which should be unregistered.
	 * @return True when unregistration was successful, otherwise false.
	 */
	public static boolean unregisterAgent(int agentId) {
		for (Integer element : registeredAgents) {
			if (element.intValue() == agentId) {
				return registeredAgents.remove(element);
			}
		}
		return false;
	}

	/**
	 * @return A list of registered Agents.
	 */
	public static List<Integer> getRegisteredAgents() {
		return registeredAgents;
	}
}
