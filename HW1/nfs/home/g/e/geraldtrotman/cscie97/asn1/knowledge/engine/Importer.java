package cscie97.asn1.knowledge.engine;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import cscie97.asn1.knowledge.engine.exception.ImportException;
import cscie97.asn1.knowledge.engine.exception.ParseException;

/**
 * This loads new Triples from an input file that gets manipulated by the KnowledgeGraph.
 * The input file should be a plain text file. The matching lines are then imported into the KnowedgeGrap		
 * as Triples.
 *
 * @author Gerald Trotman
 * @see KnowledgeGraph
 * @see Triple
 *
 */

public class Importer {
	
	/**
	 * This is a method that import triples from the given filename into the KnowledgeGraph
	 * It throws an ImportException if accessing or processing the triple file fails.
	 *
	 * @param filename		the file that contains the triple to be loaded into the KnowledgeGraph
	 * @throws ImportException	thrown when failing to import file for some reason
	 * @throws ParseException	thrown when trying to extract triple and encounters issue with the content of the files format
	 */
	
	public static void importTripleFile(String filename) throws ImportException, ParseException {
		
		String line = null;
		int lineNumber = 1;

		try {
	
			KnowledgeGraph knowledgegraph = KnowledgeGraph.getInstance();

			BufferedReader bufferedreader = new BufferedReader(new FileReader(filename));
			
			//keeps track of the triples and gives them to the KnowledgeEngine to be loaded
			List<Triple> triplesToAdd = new ArrayList<Triple>();

			//reading line by line from file
			while ((line = bufferedreader.readLine()) != null) {
			

				System.out.println("processing line: >" + line + "<");

				//getting rid of trailers from string
				line = line.replaceAll("\\.+$","");

				System.out.println("triming line: >" + line + "<");

				//skip to next line if line is empty
				if (line.length() == 0) {
					continue;
				}
				
				System.out.println("line length : >" + line.length() + "<");

				//Set a minimum length limit of charaters otherwise, give me the next line
				//String scrubbedLine = knowledgegraph.scrubTripleIdentifier(line);
				String scrubbedLine = line;
	
	
       		if ( scrubbedLine == null || scrubbedLine.length() < 5) {
					continue;
				}
			
				//we don't want imported lines to have any ? characters
//				if (scrubbedLine.matches("(.*)\\?(.*)")) {
//                                        System.out.println("error scrub line: >" + line + "<");
//					throw new ParseException("Imported Triple contained a query character [?]", line, lineNumber, filename, null); 
//				}
				
                                System.out.println("scrubed line: >" + line + "<");

				String[] thirds = line.split("\\s");

				//Throw exception that the string isn't in the format that we want
				if (thirds.length < 3) {
					throw new ParseException("Triple should have 3 parts.This only has ["+thirds.length+"] parts: ["+line+"]", line, lineNumber, filename, null);			

				} else {
					
				        System.out.println("tokenizing line: >" + line + "<");

					Node subject = knowledgegraph.getNode(thirds[0]);

					Predicate predicate = knowledgegraph.getPredicate(thirds[1]);

					Node object = knowledgegraph.getNode(thirds[2]);

				        System.out.println("subject: " + subject);
				        System.out.println("predicate: " + predicate );
				        System.out.println("object: " + object);
					triplesToAdd.add(new Triple(subject, predicate, object));
				}	
				lineNumber++;
			}
	
			if (triplesToAdd.size() > 0) {
				knowledgegraph.importTriples(triplesToAdd);
			}
    		}	
		catch (FileNotFoundException filenotfound) {
			throw new ImportException("Couldn't find file ["+filename+"] to open", lineNumber, filename, filenotfound);
		}
	
		catch (IOException inputoutput) {
			throw new ImportException("There was an I/O Error trying to open ["+filename+"].", lineNumber, filename, inputoutput);
		}
	
		catch (Exception exception) {
			throw new ImportException("Someting went wrong trying to read file["+filename+"]", lineNumber, filename, exception);
		}
	}
}		  
