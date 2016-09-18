package cscie97.asn3.housemate.model.exception;

/**
 * AuthTokenException is thrown when authorization fails.
 *
 */
public class AuthTokenException extends HouseMateModelException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param lineNumber The line number where the exception occurs.
	 * @param description A short description of the error.
	 * @param lineContents The invalid input.
	 */
	public AuthTokenException(int lineNumber, String description, String lineContents) {
		super(lineNumber, description, lineContents);
	}
	
}
