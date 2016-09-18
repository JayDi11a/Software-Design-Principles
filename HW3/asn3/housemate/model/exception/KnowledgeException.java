package cscie97.asn3.housemate.model.exception;


/**
 * Base exception class for the knowledgeGraph exceptions.
 *
 */
public class KnowledgeException extends HouseMateModelException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * @param lineNumber the line number where the error occurs.
	 * @param description the details about the error.
	 * @param lineContents the invalid input.
	 */
	public KnowledgeException(int lineNumber, String description, String lineContents) {
		super(lineNumber, description, lineContents);
	}


	
}
