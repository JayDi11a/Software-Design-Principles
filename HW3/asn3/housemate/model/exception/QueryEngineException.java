package cscie97.asn3.housemate.model.exception;

/**
 * The QueryEngineException class is used for exceptions that are thrown
 * during the querying process. Each QueryEngineException has a line number 
 * ( if relevant ), a description, and a line of the invalid input.
 */
public class QueryEngineException extends KnowledgeException {

	private static final long serialVersionUID = 1L;
	
	public QueryEngineException(int lineNumber, String description, String lineContents) {
		super(lineNumber, description, lineContents);
	}
	
	public String toString(){
		return "QueryEngineException: ( Line " + getLineNumber() + " ) " + getDescription() + " Input:" + getLineContents();
	}

}
