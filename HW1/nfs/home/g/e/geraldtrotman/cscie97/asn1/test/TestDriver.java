package cscie97.asn1.test;

import cscie97.asn1.knowledge.engine.Importer;
import cscie97.asn1.knowledge.engine.QueryEngine;
import cscie97.asn1.knowledge.engine.Triple;
import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn1.knowledge.engine.exception.ParseException;
import cscie97.asn1.knowledge.engine.exception.ImportException;
import cscie97.asn1.knowledge.engine.exception.QueryEngineException;

/**
 * Reads in the importFile to load new Triples into the KnowledgeGraph
 * and then runs the given query.
 *
 * @author Gerald Trotman
 * @see Triple
 * @see KnowledgeGraph
 * @see Importer
 * @see QueryEngine
 *
 */



public class TestDriver {
	
	/**
	 * Runs the actual test logic. Calls the importTripleFile(String) method
	 * passes in the name of the provided triple file as the first argument
	 * and after that, it calls the executeQueryFile(String) method, taking
	 * the given query filename as the second argument then spits out the result
	 *
	 * @param args first argument is the input file containing a triple and then a second query file
	 *
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			try {
				Importer.importTripleFile(args[0]);
				QueryEngine.executeQueryFile(args[1]);
			}


			catch (ParseException parseexception) {
				System.out.println(parseexception.getMessage());
				System.exit(1);
			}

			catch (ImportException importexception) {
				System.out.println(importexception.getMessage());
				System.exit(1);
			}
			
			catch (QueryEngineException queryexception) {
				System.out.println(queryexception.getMessage());
				System.exit(1);
			}
		
		} else {
			System.out.println("Arguments (1) and (2) should be import Triple file and Query file");
			System.exit(1);
		}
	}
}
