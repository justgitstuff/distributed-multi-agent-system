package de.hft_stuttgart.sopro.agent.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties.ProxyProperties;

import de.hft_stuttgart.sopro.agent.Agent;
import de.hft_stuttgart.sopro.agent.IAgent;
import de.hft_stuttgart.sopro.agent.converter.ObjectCloner;
import de.hft_stuttgart.sopro.agent.data.NegotiationData;
import de.hft_stuttgart.sopro.agent.exception.StubNotDefinedException;
import de.hft_stuttgart.sopro.agent.gui.view.IView;
import de.hft_stuttgart.sopro.agent.gui.view.MediatorView;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceStub;
import de.hft_stuttgart.sopro.agent.remote.MediatorAgentServiceWrapper;
import de.hft_stuttgart.sopro.common.project.IProject;

public class AmpManager {

	private static final long TIMEOUT = 60000;

	/**
	 * The {@link Amp} instance.
	 */
	private static Amp amp;

	/**
	 * The {@link IAgent} instance.
	 */
	private static IAgent agent = new Agent();

	/**
	 * The {@link MediatorAgentServiceStub} instance.
	 */
	private static MediatorAgentServiceStub stub;

	/**
	 * The {@link NegotiationSession} instance.
	 */
	private static NegotiationSession negotiationSession;

	/**
	 * The canonical address to the Mediator.
	 */
	private static String addressToWebService;

	/**
	 * The wrapper class which hides the WebService stuff from the Agent.
	 */
	private static MediatorAgentServiceWrapper wrapper;

	/**
	 * Contains the available Projects.
	 */
	private static List<IProject> availableProjects;

	/**
	 * The {@link ViewComposite} holding all {@link IView} instances.
	 */
	private static ViewComposite viewComposite;

	/**
	 * The map containing the {@link NegotiationData} objects.
	 */
	private static Map<Integer, NegotiationData> negotiationDataMap;

	/**
	 * @return The {@link Amp} instance.
	 */
	public static Amp getAmp() {
		if (null == amp) {
			amp = new Amp();
		}
		return amp;
	}

	/**
	 * @return The {@link IAgent} instance.
	 */
	public static IAgent getAgent() {
		return agent;
	}

	/**
	 * @return The {@link MediatorAgentServiceWrapper} instance to call methods
	 *         remotely.
	 */
	public static MediatorAgentServiceWrapper getRemoteInstance() {
		if (null == stub) {
			try {
				stub = new MediatorAgentServiceStub(addressToWebService);
				Options options = stub._getServiceClient().getOptions();

				// using HTTP version 1.0
				options.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);

				// increasing the timeout of the Client
				options.setTimeOutInMilliSeconds(TIMEOUT);

				// enable GZIP compression
				options.setProperty(org.apache.axis2.transport.http.HTTPConstants.MC_ACCEPT_GZIP, Boolean.TRUE);
				// options.setProperty(org.apache.axis2.transport.http.HTTPConstants.MC_GZIP_REQUEST,Boolean.TRUE);
				options.setProperty(org.apache.axis2.transport.http.HTTPConstants.MC_GZIP_RESPONSE, Boolean.TRUE);
				options.setProperty(org.apache.axis2.transport.http.HTTPConstants.COMPRESSION_GZIP, Boolean.TRUE);
				options.setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);
				options.setProperty(org.apache.axis2.transport.http.HTTPConstants.HEADER_CONTENT_ENCODING, "gzip");

				// TODO SD: this is not failsafe i know, poc only
				if (System.getProperty("jnlp.http.proxySet", null) != null) {
					System.out.println("Active proxy settings detected:");
					System.out.println("pSet : " + System.getProperty("jnlp.http.proxySet"));
					System.out.println("pHost: " + System.getProperty("jnlp.http.proxyHost"));
					System.out.println("pPort: " + System.getProperty("jnlp.http.proxyPort"));
					ProxyProperties pp = new ProxyProperties();
					pp.setProxyName(System.getProperty("jnlp.http.proxyHost"));
					pp.setProxyPort(Integer.parseInt(System.getProperty("jnlp.http.proxyPort")));
					stub._getServiceClient().getOptions().setProperty(HTTPConstants.PROXY, pp);
				}

				wrapper = new MediatorAgentServiceWrapper(stub);
			} catch (AxisFault e) {
				e.printStackTrace();
			} catch (StubNotDefinedException e) {
				e.printStackTrace();
			}
		}
		return wrapper;
	}

	/**
	 * This method should only be called on a disconnect from the Mediator.
	 */
	public static void resetStubInstanceOnDisconnect() {
		stub = null;
	}

	/**
	 * Sets the address to the Mediator. This method must be called before
	 * retrieving the remote instance by calling {@link #getRemoteInstance()}.
	 * 
	 * @param addressToWebService
	 *            the address to the Mediator to set.
	 */
	public static void setAddressToWebService(String addressToWebService) {
		AmpManager.addressToWebService = addressToWebService;
	}

	/**
	 * Retrieves all projects from the Mediator. This method must be called
	 * first from the {@link MediatorView} during the connecting phase. After
	 * the first call this method only returns the already available list.
	 * 
	 * @return Returns an unmodifiable list of available projects.
	 */
	public static List<IProject> retrieveAvailableProjects() {
		if (null == availableProjects) {
			availableProjects = wrapper.getAvailableProjects();
			// Collections.unmodifiableCollection(availableProjects);
		}
		return cloneElements(availableProjects);
	}

	/**
	 * Resets the available Projects.
	 */
	public static void resetAvailableProjects() {
		availableProjects = null;
	}

	/**
	 * @return The {@link ViewComposite} containing all registered {@link IView}
	 *         instances.
	 */
	public static ViewComposite getViewComposite() {
		if (null == viewComposite) {
			viewComposite = new ViewComposite();
		}
		return viewComposite;
	}

	/**
	 * Create a negotiation session
	 * 
	 * @param negotiationRounds
	 *            The negotiation rounds
	 */
	public static void createNegotiationSession(int negotiationRounds, IProject project) {
		negotiationSession = new NegotiationSession(negotiationRounds, project);
	}

	/**
	 * This method returns the NegotiationSession instance or null if no
	 * MediationSession is available. Ensure that you have created a session
	 * before first calling of this method. Furthermore check if return value is
	 * null because maybe the session was resetted at the wrong time.
	 * 
	 * @return The MediationSession instance or null if no instance was created
	 *         before.
	 */
	public static NegotiationSession getNegotiationSession() {
		return negotiationSession;
	}

	/**
	 * @return The map containing the negotiation data.
	 */
	public static Map<Integer, NegotiationData> getNegotiationDataMap() {
		return negotiationDataMap;
	}

	/**
	 * Sets the map containing the negotiation data information.
	 * 
	 * @param negotiationDataMap
	 *            The map to set.
	 */
	public static void setNegotiationDataMap(Map<Integer, NegotiationData> negotiationDataMap) {
		AmpManager.negotiationDataMap = negotiationDataMap;
	}

	/**
	 * Makes a deep copy of the available projects and the list of current
	 * agents in each project.
	 * 
	 * @param projects
	 *            The project from which to make a deep copy.
	 * @return A deep copy of the available projects and the list of current
	 *         agents in each project.
	 */
	@SuppressWarnings("unchecked")
	private static List<IProject> cloneElements(List<IProject> projects) {
		List<IProject> result = new ArrayList<IProject>();
		try {
			for (IProject project : projects) {
				project.setCurrentAgentsOnProject((List<Integer>) ObjectCloner.deepCopy(project.getCurrentAgentsOnProject()));
				result.add((IProject) ObjectCloner.deepCopy(project));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
