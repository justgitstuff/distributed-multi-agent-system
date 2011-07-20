/*
 * $LastChangedRevision: 55 $
 * $LastChangedBy: sandro $
 * $LastChangedDate: 2009-11-02 18:11:39 +0100 (Mo, 02 Nov 2009) $
 * $HeadURL: http://sopro.examer.de/svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common/exceptions/DataIOException.java $
 * $Id: DataIOException.java 55 2009-11-02 17:11:39Z sandro $
 */
package de.hft_stuttgart.sopro.common.exceptions;

/**
 * 
 * @author Annemarie Meissner
 * 
 */
public class DataIOException extends Exception {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 5675047442032548086L;

	public DataIOException() {
		super();
	}

	public DataIOException(String message) {
		super(message);
	}

}
