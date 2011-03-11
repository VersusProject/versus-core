package edu.illinois.ncsa.versus;

/**
 * Exception raised when an object is unable to process the class passed in.
 * 
 * @author Luigi Marini
 * 
 */
@SuppressWarnings("serial")
public class UnsupportedTypeException extends VersusException {

	/**
	 * Creates an {@link UnsupportedTypeException} with no message.
	 */
	public UnsupportedTypeException() {
		super();
	}

	/**
	 * Creates an {@link UnsupportedTypeException} wrapping an existing
	 * exception.
	 */
	public UnsupportedTypeException(String message, Throwable exception) {
		super(message, exception);
	}

	/**
	 * Creates an {@link UnsupportedTypeException} with a message.
	 * 
	 * @param message
	 *            reason to raise exception
	 */
	public UnsupportedTypeException(String message) {
		super(message);
	}
}
