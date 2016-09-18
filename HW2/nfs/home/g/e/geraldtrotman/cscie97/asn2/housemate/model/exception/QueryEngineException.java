package cscie97.asn2.housemate.model.exception;

/**
 * Exception for issues in the KnowledgeGraph or QueryEngine Classes from running on queries
 *
 * @author Gerald Trotman
 * @see	cscie97.asn1.knowledge.engine.KnowledgeGraph
 * @see csice97.asn1.knowledge.engine.QueryEngine
 * @see cscie97.asn1.knowledge.engine.Triple
 * @see cscie97.asn1.knowledge.engine.Importer
 *
 */

public class QueryEngineException extends Exception {


	private String query;

	private String lineWhereFailed;

	private int lineIndexWhereFailed;

	private String filename;

	private Throwable originalCause;

	public QueryEngineException (String msg, String query, int lineNum, String filename, Throwable cause) {
		super("QueryEngineException occured on query [" + query + "] of query file " + filename + " in line number [" + lineNum + "]", cause);

		this.query = query;
		this.lineIndexWhereFailed = lineNum;
		this.filename = filename;
		this.originalCause = cause;
	}

	public String getQuery() {
		return query;
	}


	public void setQuery(String query) {
		this.query = query;
	}

	public String getLineWhereFailed() {
		return lineWhereFailed;
	}

	public void setLineWhereFailed(String lineWhereFailed) {
		this.lineWhereFailed = lineWhereFailed;
	}

	public int getLineIndexWhereFailed() {
		return lineIndexWhereFailed;
	}
	
	public void setLineIndexWhereFaild(int lineIndexWhereFailed) {
		this.lineIndexWhereFailed = lineIndexWhereFailed;
	}


	public String getFilename() {
		return filename;
	}
		
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Throwable getOriginalCause() {
		return originalCause;
	}

	public void setOriginalCause(Throwable originalCause) {
		this.originalCause = originalCause;
	}
}


	
