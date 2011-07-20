package de.hft_stuttgart.sopro.agent.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.events.TypedEvent;

import de.hft_stuttgart.sopro.agent.gui.view.IView;

/**
 * Manages all {@link IView} instances.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class ViewComposite {

	private List<IView> views = new ArrayList<IView>();

	/**
	 * Adds an {@link IView} to the container.
	 * 
	 * @param view
	 *            The view to add.
	 */
	public void addView(IView view) {
		if (!views.contains(view)) {
			views.add(view);
		}
	}

	/**
	 * Returns the list of {@link IView} instances.
	 * 
	 * @return The list of {@link IView} instances.
	 */
	public List<IView> getViews() {
		return Collections.unmodifiableList(views);
	}

	/**
	 * Disposes all {@link IView} instances and passes the event object to them.
	 * 
	 * @param event
	 *            The event which was fired.
	 */
	public void dispose(TypedEvent event) {
		// Views need to be disposed in a reverse order
		List<IView> reversedViews = new ArrayList<IView>();
		reversedViews.addAll(getViews());
		Collections.reverse(reversedViews);

		for (IView view : reversedViews) {
			// just delegate to all views
			view.dispose(event);
		}
	}
}
