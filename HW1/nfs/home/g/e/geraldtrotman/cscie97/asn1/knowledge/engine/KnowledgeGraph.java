package cscie97.asn1.knowledge.engine;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;
import cscie97.asn1.knowledge.engine.exception.ParseException;

/**
 * This is a database of Triples stored in memory. It also for the user to import
 * new Triples too. The specs request that the KnowledgeGraph be implemented as a Singleton which
 * emphasizes using only one instance. The Flyweight pattern is also specified such that there
 * is only one instance of the Node, the Predicate, or Triple. Gotta save memory when multithreading!
 *
 * @author Gerald Trotman
 * @see Triple
 * @see Importer
 * @see QueryEngine
 */


public class KnowledgeGraph {

	//Beginning Singleton implementation
	private static KnowledgeGraph instance = null;


	//Mandatory Class Constructor
	private KnowledgeGraph() {
		nodeMap = new HashMap<String, Node>();
		predicateMap = new HashMap<String, Predicate>();
		tripleMap = new HashMap<String, Triple>();
		queryMapSet = new HashMap<String, Set<Triple>>();
	}

	/**
	 * This is a specific format for impementing the Singleton instance
	 * that was specifically written in the most multithread friendly form
	 * http://en.wikipedia.org/wiki/Singleton_pattern
	 *
	 * @return singleton instance of KnowledgeGraph
	 */
	public static synchronized KnowledgeGraph getInstance() {
		if (instance == null) {
			instance = new KnowledgeGraph();
		}
		return instance;
	}

	/**
	 * collection of Nodes whose map key is the node identifier
	 * (Subject and/or Object.
	 */
	private Map<String, Node> nodeMap;

	/**
	 * collection of Predicates whose map key is the predicate identifier
	 *
	 */
	private Map<String, Predicate> predicateMap;
	
	/**
	 * collection of Triples whose map key is the Triple identifier
	 *
	 */
	private Map<String, Triple> tripleMap;

	/**
	 * a collection of fast query lookups whose map key is a query string
	 *
	 */
	private Map<String, Set<Triple>> queryMapSet;

	


	/**
	 * A method for adding a list of Triples to a KnowledgeGraph.
	 * This uses the Flyweight pattern for eliminating duplication
	 * such that there are three unique instances of a Subject,
	 * Predicate and Object.
	 *
	 * @param tripleList This is the list of Triples being added to the KnowledgeGraph
	 */
	public void importTriples(List<Triple> tripleList) {
		for (Triple triple : tripleList) {

			if (tripleMap.get(triple.getIdentifier().toLowerCase()) == null && triple.getIdentifier() != null) {
				if (nodeMap.get(triple.getSubject().getIdentifier().toLowerCase()) == null) {
					nodeMap.put(triple.getSubject().getIdentifier().toLowerCase(), triple.getSubject());
				}

				if (predicateMap.get(triple.getPredicate().getIdentifier().toLowerCase()) == null) {
					predicateMap.put(triple.getPredicate().getIdentifier().toLowerCase(), triple.getPredicate());
				}

				if (nodeMap.get(triple.getObject().getIdentifier().toLowerCase()) == null) {
					nodeMap.put(triple.getObject().getIdentifier().toLowerCase(), triple.getObject());
				}
				
				addTripleToQueryMapSet(triple);
			}
		}
	}

	/**
	 * In the event that a Triple isn't in the queryMap, this method adds to it.
	 * There are 8 specific scenarios of the query that this accounts for. 
	 *
	 * @param triple the Triple to add to the queryMapSet
	 *
	 */
	private void addTripleToQueryMapSet(Triple triple) {
		
		// This is for adding new triples that don't exist yet
		if (tripleMap.get(triple.getIdentifier().toLowerCase()) == null && triple.getIdentifier() != null) {
			String scrubbedIdentifier = scrubTripleIdentifier(triple.getIdentifier());
			tripleMap.put(scrubbedIdentifier, triple);

			//Saves the list of all the query string scenarios and update the queryMapSet
			// with the existing triple for each scenario so queries that follow match.
			List<String> queryStringMatches = new ArrayList<String>(Arrays.asList(
			
				//query form (1) subject predicate object
				triple.getSubject().getIdentifier().toLowerCase() + " " + triple.getPredicate().getIdentifier().toLowerCase() + " " + triple.getObject().getIdentifier().toLowerCase(),
				//query form (2) subject predicate ?
				triple.getSubject().getIdentifier().toLowerCase() + " " + triple.getPredicate().getIdentifier().toLowerCase() + " ?",

				//query form (3) subject ? object
				triple.getSubject().getIdentifier().toLowerCase() + " ?" + triple.getObject().getIdentifier().toLowerCase(),
				
				//query form (4) subject ? ?
				triple.getSubject().getIdentifier().toLowerCase() + " ? ?",
				
				//query form (5) ? predicate object
				"? " + triple.getPredicate().getIdentifier().toLowerCase() + " " + triple.getObject().getIdentifier().toLowerCase(),
			
				//query form (6) subject ? object
				"? " + triple.getPredicate().getIdentifier().toLowerCase() + " ?",
				
				//query form (7) ? ? object
				"? ? " + triple.getObject().getIdentifier().toLowerCase(),
				
				//query form: (8) ? ? ?
				"? ? ?"));
			
			for (String queryString : queryStringMatches) {
				Set<Triple> queryStringSetMatchingTriples = queryMapSet.get(queryString);
				if (queryStringSetMatchingTriples == null) {
					queryMapSet.put(queryString, new HashSet<Triple>(Arrays.asList(triple)) );
				
				} else {
					queryStringSetMatchingTriples.add(triple);
				}
			}
		}
	}


	/**
	 * Take a given triple identifier or query and either convert
	 * strings to lower case, get rid of trailing periods, and/or
	 * replace occurences of more than two white spaces
	 *
	 * @param stringToParse
	 * @return string that has to correct formatting.
	 *
	 */
	
	//there may be an error here
	public String scrubTripleIdentifier(String stringToParse) {
		stringToParse = stringToParse.toLowerCase();
		stringToParse = stringToParse.replaceAll("\\.+$", "");
		stringToParse = stringToParse.replaceAll("\\s{s,}", " ");
		return stringToParse;
	}

	/**
	 * takes a queryMapSet and figures out the Triple that matches a given query.
	 * It modifies any trailing periods and occurrences of white space. Returns empty 
	 * set if all Triple criteria are met. Question marks 
	 * are used as wild cards.
	 *
	 * @param query the query string to search for matching Triples. 
	 * @return	the Set of all Triples that match the input
	 */
	public Set<Triple> executeQuery(Triple query) {
		if (query != null && query.getIdentifier() != null && query.getIdentifier().length() > 0) {
			String actualQuery = scrubTripleIdentifier(query.getIdentifier());
			if (actualQuery != null && actualQuery.length() > 0) {
				Set<Triple> locateResults = this.queryMapSet.get(actualQuery);
				return locateResults;
			}
			return null;
		}
		return null;
	
	}


	
	/**
	 * Return a Node instance for a given identifier. If the node doesn't exist
	 * create it and add it to the nodeMap. Nodes can be the subject or the object
	 *
	 *
	 * @param identifier the string identifier of the Node
	 * @return	     the newly created Node object depending on the identifier
	 */
	public Node getNode(String identifier) {
		if (nodeMap.keySet().contains(identifier.toLowerCase())) {
			return nodeMap.get(identifier.toLowerCase());
		}
		
		//This is new and needs to be added to the map
		Node node = new Node(identifier);
		nodeMap.put(identifier.toLowerCase(), node);
		return node;
	}

	
	/**
	 * Return a Predicate instance for a given identifier. If the predicate doesn't
	 * exist, create it and add it to the predicateMap.
	 *
	 * @param identifier the string identifier of the Predicate
	 * @return	     the newly created Predicate object depending on the identifier
	 */
	public Predicate getPredicate(String identifier) {
		if (predicateMap.keySet().contains(identifier.toLowerCase())) {
			return predicateMap.get(identifier.toLowerCase());
		}
		
		//This is new and needs to be added to the map
		Predicate predicate = new Predicate(identifier);
		predicateMap.put(identifier.toLowerCase(), predicate);
		return predicate;
	}

	/**
	 * Return a Triple instance for a given subject, predicate, AND object. If the triple
	 * doesnt exist, create it and add it to the tripleMap. The queryMapSet needs to be
	 * updated too!
	 *
	 * @param subject	the subject Node for the Triple
	 * @param predicate	the predicate for the Triple
	 * @param object	the object for the Triple
	 * @return 		either newly created or already existed
	 */
	public Triple getTriple(Node subject, Predicate predicate, Node object) {
		String tripleIdentifier = subject.getIdentifier().toLowerCase() + " " + predicate.getIdentifier().toLowerCase() + " " + object.getIdentifier().toLowerCase() + ".";


		if (tripleMap.keySet().contains(tripleIdentifier)) {
			return tripleMap.get(tripleIdentifier);
		}

		Triple triple = new Triple(subject, predicate, object);
		tripleMap.put(tripleIdentifier, triple);
		return triple;

	}

	/**
	 * This constructs a Triple object that can be used as 
	 * the input parameter itself to the method executeQuery()
	 * which comes into play when handling the wild card character
	 *
	 * @param identifier	string used to consrut a new Triple
	 * @return		a new triple object based on the String
	 * @throws ParseException thrown if there aren't the required three parts
	 */
	public Triple getQueryTripleFromStringIdentifier(String identifier) throws ParseException {
		if (identifier != null && identifier.length() > 0) {
			identifier = scrubTripleIdentifier(identifier);
			String[] thirds = identifier.split("\\s");

		if (thirds.length < 3) {
			throw new ParseException("Triple identifier should have 3 parts but it was only given ["+thirds.length+"] parts: ["+identifier+"]", identifier, null);
		} else {
			Node subject = null;
			Predicate predicate = null;
			Node object = null;

			if (!thirds[0].equalsIgnoreCase("?")) {
				subject = getNode(thirds[0]);
			}
			if (!thirds[1].equalsIgnoreCase("?")) {
				predicate = getPredicate(thirds[1]);
			}
			if (!thirds[2].equalsIgnoreCase("?")) {
				object = getNode(thirds[2]);
			}

			return new Triple(subject, predicate, object);
		}
		}
	return null;
	}
}
