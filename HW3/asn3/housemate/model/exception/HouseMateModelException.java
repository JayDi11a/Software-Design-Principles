package cscie97.asn3.housemate.model.exception;

/**
 * Base exception class for the package cscie97.asn2.housemate.model
 *
 */
public class HouseMateModelException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private final int lineNumber;
	private final String description;
	private final String lineContents;
	
	/**
	 * @param lineNumber the line number where the error occurs.
	 * @param description the details about the error.
	 * @param lineContents the invalid input.
	 */
	public HouseMateModelException(int lineNumber, String description, String lineContents) {
		this.lineNumber = lineNumber;
		this.description = description;
		this.lineContents = lineContents;
	}

	public int getLineNumber(){
		return lineNumber;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getLineContents(){
		return lineContents;
	}
	
	public String toString(){
		return this.getClass().getSimpleName() + ": ( Line " + getLineNumber() + " ) " + getDescription() + " Input: " + getLineContents();
	}
}
