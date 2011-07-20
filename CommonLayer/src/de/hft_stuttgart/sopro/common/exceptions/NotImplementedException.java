/*
 * $LastChangedRevision: 55 $ $LastChangedBy: sandro $ $LastChangedDate:
 * 2009-11-02 18:11:39 +0100 (Mo, 02 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common
 * /exceptions/ProjectNotFoundException.java $ $Id:
 * ProjectNotFoundException.java 55 2009-11-02 17:11:39Z sandro $
 */
package de.hft_stuttgart.sopro.common.exceptions;

/**
 * @author Annemarie Meissner
 */
public class NotImplementedException extends Exception {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 3432667915361789649L;

	public NotImplementedException() {
		super();
	}

	public NotImplementedException(String message) {
		super(message);
	}

}
