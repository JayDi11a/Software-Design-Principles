package cscie97.asn4.knowledge.engine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import cscie97.asn4.housemate.exception.ImportException;

/**
 * The Importer class is responsible for reading in triples from a file that is 
 * in the N-Triple format. The triples are trimmed and validated before being
 * passed to the KnowledgeGraph.
 */
public class Importer {

	private final KnowledgeGraph knowledgeGraph = KnowledgeGraph.getInstance();
	
	/**
	 * Attempts to read each line of the provided filename and convert to a triple.
	 * Empty lines are skipped. 
	 * @param filename the filename containing the line-separated triples to import.
	 * @throws ImportException on error processing a file or line
	 */
	public void importTripleFile(String filename) throws ImportException
	{
		InputStream fis = null;
		BufferedReader br = null;
		String line;
		List<TripleLiteral> tripleList = new ArrayList<TripleLiteral>();
		try {
			
		    fis = new FileInputStream(filename);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    br = new BufferedReader(isr);
		    
		    int lineNumber = 0; 
		    
		    while ((line = br.readLine()) != null) {
		    	lineNumber++;

		        String[] tokens = getTokensFromLine(line, lineNumber);
		    	
		        /** Tokens is null on empty lines. Skip and continue processing. */
		        if(tokens == null){
		        	continue;
		        }
		        
		    	tripleList.add( new TripleLiteral(tokens[0],tokens[1],tokens[2]) );
		    }
		    
		    knowledgeGraph.importTriples(tripleList);
		
		}catch(IOException e){
			throw new ImportException(0, e.getMessage() , filename);
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (br != null)
					br.close();
			} catch (IOException ex) {
				throw new ImportException(0, ex.getMessage() , filename);
			}
		}

	}
	
	/**
	 * Converts a line into tokens.
	 * @param line the line contents to be scrubbed, validated, and tokenized.
	 * @param lineNumber the current line number of the file being imported.
	 * @return an array of strings that have been trimmed and validated. if null, then nothing needs to be processed.
	 * @throws ImportException
	 */
	private String[] getTokensFromLine(String line, int lineNumber) throws ImportException{
		line = line.trim();
    	
		/** If the line is empty, skip it.  */
		if(line.length() == 0){
    		return null;
    	}
    	
    	/** Purposefully allowing lines without periods to pass validation.  */
    	if(line.charAt(line.length() - 1) == '.'){
    		line = line.substring(0, line.length() - 1);
    	}
    	
    	String[] tokens = line.split("\\s+");
    	
    	/** Invalid number of tokens. Do not continue processing.  */
    	if(tokens.length != 3){
    		throw new ImportException(lineNumber, "Invalid line format. Acceptable format is: \"String String String.\"", line);
    	}
    	
    	/** Invalid token. Do not continue processing.  */
    	if(tokens[0].equals("?") || tokens[1].equals("?") || tokens[2].equals("?") ){
    		throw new ImportException(lineNumber, "Invalid token. \"?\" is a reserved word and cannot be used.", line);
    	}
    	
    	return tokens;
    }
}
