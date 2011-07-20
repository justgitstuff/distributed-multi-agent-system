package de.hft_stuttgart.sopro.agent.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.hft_stuttgart.sopro.agent.gui.view.IView;

/**
 * An instance of this class instantiates the graphical user interface of the
 * agent.
 * 
 * @author Matthias Huber - MatthewHuber@web.de
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class Amp {

	/**
	 * The display of user interface.
	 */
	private Display display;

	/**
	 * The shell of the user interface.
	 */
	private Shell shell;

	public Amp() {
		display = new Display();

		shell = new Shell(display, SWT.SHELL_TRIM & ~SWT.RESIZE);
		shell.setText("AMP - Ant-Based Multi-Agent Project Scheduling");
		shell.setLayout(new FillLayout());

		shell.addDisposeListener(new DisposeListener() {

			/**
			 * Disposes all registered views in the ViewComposite container.
			 */
			public void widgetDisposed(DisposeEvent event) {
				AmpManager.getViewComposite().dispose(event);
			}
		});
	}

	/**
	 * Creates the main shell with the given size.
	 * 
	 * @param width
	 *            The width of the shell.
	 * @param height
	 *            The height of the shell.
	 */
	public void create(int width, int height) {
		shell.setSize(width, height);
		shell.open();

		switchToView(ViewEnum.MEDIATORVIEW, shell);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}

	/**
	 * Currently this method is used to switch between the different views. The
	 * content of the old view will be disposed. So it is not possible to get
	 * the old view with its settings when the user decides to go back the
	 * previous view.
	 * 
	 * @param view
	 *            The view to switch to.
	 */
	public void switchToView(ViewEnum viewEnum, Shell shell) {
		disposeChildren();
		IView view = ViewFactory.createView(viewEnum, shell);
		if (null != view) {
			view.register();
			view.show();
		}
	}

	/**
	 * Disposes all children of the shell.
	 */
	private void disposeChildren() {
		for (Control control : shell.getChildren()) {
			control.dispose();
		}
	}
}
