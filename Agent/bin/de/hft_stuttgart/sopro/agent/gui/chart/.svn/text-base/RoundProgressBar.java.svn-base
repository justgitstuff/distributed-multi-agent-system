package de.hft_stuttgart.sopro.agent.gui.chart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import de.hft_stuttgart.sopro.agent.gui.AmpManager;

/**
 * Displays the progress bar which shows the round number information.
 * 
 * @author Frank Erzfeld - frank@erzfeld.de
 * @author Matthias Huber - MatthewHuber@web.de
 */
public class RoundProgressBar implements IChart {

	private Composite parent;
	private ProgressBar progressBar;
	private static final int NUMBER_OF_COLUMNS = 9;
	private int numberOfRounds;
	private Label counterLabel;

	public RoundProgressBar(Composite parent) {
		this.parent = parent;
		this.numberOfRounds = AmpManager.getNegotiationSession().getNegotiationRounds();
	}

	/**
	 * Creates the main Composite with the content.
	 */
	public void create() {
		progressBar = new ProgressBar(parent, SWT.SMOOTH);
		progressBar.setMinimum(0);
		progressBar.setMaximum(numberOfRounds);
		progressBar.setSelection(0);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = NUMBER_OF_COLUMNS - 1;
		progressBar.setLayoutData(gridData);

		counterLabel = new Label(parent, SWT.CENTER);
		counterLabel.setText(generateStringRepresentation());
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		counterLabel.setLayoutData(gridData);

	}

	@Override
	public void computeNextRound() {
		// in this chart everything is done in the method 'update'.
	}

	/**
	 * Updates the slider.
	 */
	public void update() {
		int round = AmpManager.getNegotiationSession().getRound();
		progressBar.setSelection(round);
		counterLabel.setText(generateStringRepresentation());
	}

	@Override
	public void dispose() {
	}
	
	private String generateStringRepresentation() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(progressBar.getSelection());
		buffer.append("/");
		buffer.append(progressBar.getMaximum());
		buffer.append(" Rounds");

		return buffer.toString();
	}

}
