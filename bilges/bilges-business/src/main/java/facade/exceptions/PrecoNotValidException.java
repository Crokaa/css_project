package facade.exceptions;

public class PrecoNotValidException extends ApplicationException {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public PrecoNotValidException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public PrecoNotValidException (String message, Exception e) {
		super(message, e);
	}
}
