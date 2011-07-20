package de.hft_stuttgart.sopro.agent.gui.view;

import org.eclipse.swt.events.TypedEvent;

import de.hft_stuttgart.sopro.agent.gui.ViewComposite;

public interface IView {

	/**
	 * This method creates the view with all it's children.
	 */
	public void show();

	/**
	 * This method register the IView in the {@link ViewComposite} container.
	 */
	public void register();

	/**
	 * This method handles necessary steps when a disposal of the shell occurs
	 * (e.g. unregistering of the agent)
	 * 
	 * @param event
	 *            The event which was fired.
	 */
	public void dispose(TypedEvent event);

}
