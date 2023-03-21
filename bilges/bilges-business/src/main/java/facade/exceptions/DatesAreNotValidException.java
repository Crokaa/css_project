package facade.exceptions;

public class DatesAreNotValidException extends ApplicationException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public DatesAreNotValidException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public DatesAreNotValidException (String message, Exception e) {
		super(message, e);
	}

}
