package de.hft_stuttgart.sopro.agent.gui;

import org.eclipse.swt.widgets.Shell;

import de.hft_stuttgart.sopro.agent.gui.view.IView;
import de.hft_stuttgart.sopro.agent.gui.view.MediatorView;
import de.hft_stuttgart.sopro.agent.gui.view.NegotiationView;
import de.hft_stuttgart.sopro.agent.gui.view.ProjectView;
import de.hft_stuttgart.sopro.agent.gui.view.ResultView;

/**
 * Factory class for creating {@link IView} instances.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public final class ViewFactory {

	/**
	 * Private constructor which should not be called.
	 */
	private ViewFactory() {
	}

	/**
	 * Creates a single {@link IView} instance from the specifying
	 * {@link ViewEnum}.
	 * 
	 * @param viewEnum
	 *            The {@link ViewEnum} specifying the needed {@link IView}
	 *            instance.
	 * @param shell
	 *            The shell where to draw the {@link IView} to.
	 * @return An {@link IView} instance.
	 */
	public static IView createView(ViewEnum viewEnum, Shell shell) {
		switch (viewEnum) {
		case MEDIATORVIEW:
			return new MediatorView(shell);
		case PROJECTVIEW:
			return new ProjectView(shell);
		case NEGOTIATIONVIEW:
			return new NegotiationView(shell);
		case RESULTVIEW:
			return new ResultView(shell);
		default:
			return null;
		}
	}
}
