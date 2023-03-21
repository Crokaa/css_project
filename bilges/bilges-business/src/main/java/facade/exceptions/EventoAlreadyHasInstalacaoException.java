package facade.exceptions;

public class EventoAlreadyHasInstalacaoException extends ApplicationException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public EventoAlreadyHasInstalacaoException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public EventoAlreadyHasInstalacaoException (String message, Exception e) {
		super(message, e);
	}
}
