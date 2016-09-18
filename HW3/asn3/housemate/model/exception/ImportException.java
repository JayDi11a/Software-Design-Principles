package cscie97.asn3.housemate.model.exception;

/**
 * The ImportException class is used for exceptions that are thrown
 * during the import process. Each ImportException has a line number 
 * ( if relevant ), a description, and a line of the invalid input.
 */
public class ImportException extends KnowledgeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param lineNumber The line the error occurred.
	 * @param description A description of the error.
	 * @param lineContents The invalid input.
	 */
	public ImportException(int lineNumber, String description, String lineContents) {
		super(lineNumber, description, lineContents);
	}
	
	public String toString(){
		return "ImportException: ( Line " + getLineNumber() + " ) " + getDescription() + " Input:" + getLineContents();
	}
}
