package cscie97.asn3.housemate.model.exception;

/**
 * InvalidCommandExceptions are thrown when a command has invalid fields.
 *
 */
public class InvalidCommandException extends HouseMateModelException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param lineNumber The line the error occurred.
	 * @param description A description of the error.
	 * @param lineContents The invalid input.
	 */
	public InvalidCommandException(int lineNumber, String description, String lineContents) {
		super(lineNumber, description, lineContents);
	}
	
}
