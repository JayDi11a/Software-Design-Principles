package cscie97.asn1.knowledge.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;
import java.io.IOException;
import java.io.FileNotFoundException;
import cscie97.asn1.knowledge.engine.exception.ParseException;
import cscie97.asn1.knowledge.engine.exception.ImportException;
import cscie97.asn1.knowledge.engine.exception.QueryEngineException;

/**
 * This is used to run queries on the KnowledgeGraph
 *
 * @author Gerald Trotman
 * @see Triple
 * @see KnowledgeGraph
 * 
 *
 */

public class QueryEngine {
	
	/**
	 * Method for executing a single query on the KnowledgeGraph. Throws
	 * a QueryEngineException on error
	 *
	 * @param query		the query to run Triples
	 * @throws ParseException for problems with the given query
	 *
	 */
	public static void executeQuery(String query) throws ParseException {
		KnowledgeGraph knowledgegraph = KnowledgeGraph.getInstance();

		Triple queryTriple = knowledgegraph.getQueryTripleFromStringIdentifier(query);

		Set<Triple> queryResults = knowledgegraph.executeQuery(queryTriple);

		System.out.println("QUERY: " + queryTriple.getIdentifier() );

		if (queryResults != null && queryResults.size() > 0) {
			for (Triple triple : queryResults) {
				System.out.println(triple.getIdentifier());
			}
		}
		System.out.println();

	}

	
	/**
	 * Method for running a set of queries read from a file. Checks if the 
	 * filename is valid. Throws QueryEngineException on error.
	 *
	 * @param filename	the query file to read 
	 * @throws QueryEngineException	thrown when a problem occurs upon reading the query file
	 * @throws ParseException	throws when a problem occurs upon reading query line
	 * @throws ImportException	throws when a problem occurs upon opening file
	 */
	public static void executeQueryFile(String filename) throws QueryEngineException, ParseException, ImportException {
		try {
			BufferedReader bufferedreader = new BufferedReader(new FileReader(filename));
			String line = null;
			while ((line = bufferedreader.readLine()) != null) {
				QueryEngine.executeQuery(line);
			}	
		}
		
		catch (FileNotFoundException filenotfound) {
			throw new ImportException("Couldn't find file ["+filename+"] to open", 0, filename, filenotfound);
		}

		catch (IOException inputoutput) {
			throw new ImportException("There was an I/O Error trying to open query filefor reading", 0, filename, inputoutput);
		}

		catch (Exception exception) {
			throw new ImportException("Something went wrong trying to read file ["+filename+"]", 0, filename, exception);
		}
	}		

}
