package cscie97.asn1.knowledge.engine.exception;


/**
 * Exception for problems with Importer.java. This
 * class will catch lower level exceptions too like FileNotFound, etc
 *
 * @author Gerald Trotman
 * @see cscie97.asn1.knowledge.engine.Importer
 *
 *
 */

public class ImportException extends Exception {

	private String lineWhereFailed;


	private int lineIndexWhereFailed;


	private String filename;


	private Throwable originalCause;

	
	/**
	 * "Wraps" a generic exception that could have been thrown in 
	 * the Importer class. 
	 *
	 * @param line 		The string value of the line that caused the exception
	 * @param lineNum	The line number in the file that cause the failure
	 * @param filename	The filename that was the cause of the exception
	 * @param cause		The wrapped exception that triggered this exception
	 */
	public ImportException (String line, int lineNum, String filename, Throwable cause) {
		super("ImportException occured at line " + lineNum + " for file " + filename + " in line number [" + lineNum + "]", cause);

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

