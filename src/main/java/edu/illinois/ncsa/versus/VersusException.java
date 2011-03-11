/**
 * 
 */
package edu.illinois.ncsa.versus;

/**
 * Root exception for the framework. Other exceptions should extends this class.
 * 
 * @author Luigi Marini
 * 
 */
@SuppressWarnings("serial")
public class VersusException extends Exception {
	
	/**
	 * Creates an {@link VersusException} with no message.
	 */
	public VersusException() {
		super();
	}

	/**
	 * Creates an {@link UnsupportedTypeException} wrapping an existing
	 * exception.
	 */
	public VersusException(String message, Throwable exception) {
		super(message, exception);
	}

	/**
	 * Creates an {@link VersusException} with a message.
	 * 
	 * @param message
	 *            reason to raise exception
	 */
	public VersusException(String message) {
		super(message);
	}
}
