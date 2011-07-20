package de.hft_stuttgart.sopro.mediator.session;

import java.util.Iterator;
import java.util.Map;

import de.hft_stuttgart.sopro.mediator.Mediator;

/**
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class ResourceCleaner {

	/**
	 * The release time.
	 */
	private static final long CLEANUP_TIME = 900000;

	/**
	 * The {@link CleanerThread} instance.
	 */
	private CleanerThread cleanerThread;

	/**
	 * Starts the {@link CleanerThread} instance.
	 */
	public void start() {
		if (null == cleanerThread) {
			cleanerThread = new CleanerThread();
			cleanerThread.start();
			System.out.println("ResourceCleaner was started. Cleanup time is " + CLEANUP_TIME + " ms.");
		}
	}

	/**
	 * Stops the {@link CleanerThread} instance.
	 */
	public void stop() {
		if (null != cleanerThread) {
			synchronized (cleanerThread) {
				cleanerThread.setInterrupted(true);
				cleanerThread = null;
				System.out.println("ResourceCleaner was stopped");
			}
		}
	}

	/**
	 * The main Thread for cleaning up some resources.
	 * 
	 * @author Eduard Tudenhoefner - nastra@gmx.net
	 */
	private class CleanerThread extends Thread {
		/**
		 * Signalizes whether the Thread should be interrupted
		 */
		private boolean isInterrupted = false;

		public boolean isInterrupted() {
			return isInterrupted;
		}

		public void setInterrupted(boolean isInterrupted) {
			this.isInterrupted = isInterrupted;
		}

		/**
		 * {@inheritDoc}
		 */
		public void run() {
			Thread thisThread = Thread.currentThread();

			while (cleanerThread == thisThread) {
				if (isInterrupted) {
					break;
				}

				synchronized (this) {
					try {
						wait(CLEANUP_TIME);
					} catch (InterruptedException e) {
						System.out.println("Error pausing the CleanerThread");
					}
				}
				releaseSessionResources();
			}
		}

		/**
		 * Releases the resources.
		 */
		@SuppressWarnings("unchecked")
		private void releaseSessionResources() {
			Map<String, MediationSession> sessionMap = Mediator.getInstance().getMediationSessionMap();

			// need to synchronize the map, because we are using an Iterator
			synchronized (sessionMap) {
				if (null != sessionMap && !sessionMap.isEmpty()) {
					// get the information from the available sessions
					Iterator iterator = sessionMap.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry<String, MediationSession> entry = (Map.Entry<String, MediationSession>) iterator.next();
						MediationSession session = entry.getValue();
						long sessionDisposeTime = session.getLastModificationTime() + CLEANUP_TIME;
						long currentTime = System.currentTimeMillis();

						if (currentTime >= sessionDisposeTime) {
							session.removeAllAgentsFromSession();
							iterator.remove();
							System.out.println("Released resources for the Project " + session.getCurrentProject().getProjectName());
						}
					}
				}
			}
		}
	}
}
