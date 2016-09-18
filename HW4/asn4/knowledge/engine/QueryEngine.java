package cscie97.asn4.knowledge.engine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Set;

import cscie97.asn4.housemate.exception.QueryEngineException;


/**
 * Supports the execution of KnowledgeGraph queries. Queries are in the N-Triple format.
 * The "?" represents a wild card in the query. All matching results are printed out.
 */
public class QueryEngine{

	private final KnowledgeGraph knowledgeGraph = KnowledgeGraph.getInstance();
	
	/**
	 * Executes a single query against the KnowledgeGraph.
	 * @param query a N-Triple formatted string
	 * @throws QueryEngineException on invalid queries.
	 */
	public void executeQuery(String query) throws QueryEngineException{
		query = query.trim();
    	if(query.length() == 0){
    		return;
    	}
    	
    	/** The period is optional */
    	if(query.charAt(query.length() - 1) == '.'){
    		query = query.substring(0, query.length() - 1);
    	}
    	
    	String[] tokens = query.split("\\s+");
    	
    	if(tokens.length != 3){
    		throw new QueryEngineException(1, "Invalid query format. Acceptable format is: \"String String String.\"", query);
    	}
    	
    	TripleLiteral triple = new TripleLiteral(tokens[0],tokens[1],tokens[2]);
    	
    	Set<Triple> tripleSet = knowledgeGraph.executeQuery(triple);
    	
    	/** Output the original query, cleanly formatted. 
    	 * Since we want to keep the same case as the original, we use the tokens and not the identifier from the Triple. 
    	 * */
    	System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2] + "." );
    	
    	if(tripleSet.size() > 0){
    		for(Triple tripleMatch : tripleSet){
	            System.out.println(tripleMatch.getIdentifier() + ".");
	        }
    	}else{
    		System.out.println("<null>");
    	}
	}


	/**
	 * Reads a file line by line and executes queries against the KnowledgeGraph.
	 * @param filename the filename of the file to read the queries from
	 * @throws QueryEngineException on invalid queries and file reading errors.
	 */
	public void executeQueryFile(String filename) throws QueryEngineException{
		InputStream fis = null;
		BufferedReader br = null;
		String line;
		
		try {
		    fis = new FileInputStream(filename);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    br = new BufferedReader(isr);
		    int lineNumber = 0;
		    
		    while ((line = br.readLine()) != null) {
		    	lineNumber++;
		    	try{
		    		executeQuery(line);
		    	} catch(QueryEngineException ex){
		    		throw new QueryEngineException(lineNumber, ex.getDescription(), ex.getLineContents());
		    	}
		    }
		    
		}catch(IOException e){
			throw new QueryEngineException(0, e.getMessage() , filename);
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (br != null)
					br.close();
			} catch (IOException ex) {
				throw new QueryEngineException(0, ex.getMessage() , filename);
			}
		}
	}
	
}
