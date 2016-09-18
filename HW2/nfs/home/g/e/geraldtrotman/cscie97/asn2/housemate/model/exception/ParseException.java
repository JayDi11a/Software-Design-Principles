package cscie97.asn2.housemate.model.exception;

/**
 * Exception for problems that the KnowledgeGraph incurs during 
 * importing triples or running queries
 *
 * @author Gerald Trotman
 * @see cscie97.asn1.knowledge.engine.KnowledgeGraph
 * @see cscie97.asn1.knowledge.engine.Triple
 * @see cscie97.asn1.knowledge.engine.Importer
 * @see cscie97.asn1.knowledge.engine.QueryEngine
 *
 */
public class ParseException extends Exception {

	private String lineWhereFailed = new String();


	private int lineIndexWhereFailed = 0;

	
	private String filename = new String();

	
	private Throwable originalCause = null;

	/**
	 * Wraps a more generic exception that may be thrown in Importer 
	 * or KnowledgeGraph, or QueryEngine Class.
	 *
	 * @param line	the string value of the line that caused the exception
 	 * @param cause	the wrapped exception that triggered this exception
 	 *
	 */
	public ParseException (String msg, String line, Throwable cause) {
		super("ParseException occured on line ["+ line + "]", cause);

		this.lineWhereFailed = line;
		this.originalCause = cause;
	}


	/**
	 * Wraps a more generic exception that may be thrwon in Importer or Knowledge
	 * or QueryEngine Class.
 	 *
	 * @param line		the string value of the line that caused the exception
	 * @param cause		the wrapped exception that triggered this exception
	 *
	 */
	public ParseException (String msg, String line, int lineNum, String filename, Throwable cause) {
		super("ParseException occured on line ["+ line + "] of file [" + filename + "] at line number [" + lineNum + "]", cause);

		this.lineWhereFailed = line;
		this.lineIndexWhereFailed = lineNum;
		this.filename = filename;
		this.originalCause = cause;
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

	public void setLineIndexWhereFailed(int lineIndexWhereFailed) {
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

