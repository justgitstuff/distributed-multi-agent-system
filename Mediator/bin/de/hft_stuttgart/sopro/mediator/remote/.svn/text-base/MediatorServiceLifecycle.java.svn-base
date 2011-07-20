package de.hft_stuttgart.sopro.mediator.remote;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.ServiceLifeCycle;

import de.hft_stuttgart.sopro.mediator.Mediator;
import de.hft_stuttgart.sopro.mediator.session.ResourceCleaner;

/**
 * The methods of this class are called when the Service is deployed on the
 * Server.
 * 
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class MediatorServiceLifecycle implements ServiceLifeCycle {

	/**
	 * The {@link ResourceCleaner} instance.
	 */
	private ResourceCleaner resourceCleaner;

	/**
	 * Initializes the {@link Mediator} instance and the available projects on
	 * startup of the Service.
	 */
	public void startUp(ConfigurationContext configurationContext, AxisService axisService) {
		Mediator.getInstance();
		resourceCleaner = new ResourceCleaner();
		resourceCleaner.start();
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutDown(ConfigurationContext configurationContext, AxisService axisService) {
		resourceCleaner.stop();
		Mediator.getInstance().getMediationSessionMap().clear();
	}
}
