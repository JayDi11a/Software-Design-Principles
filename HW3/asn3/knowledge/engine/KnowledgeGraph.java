
package cscie97.asn3.knowledge.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cscie97.asn3.housemate.model.exception.KnowledgeException;

/**
 * The KnowledgeGraph is responsible for managing the set of active triples.
 * The triples are stored in memory and not persisted. The KnowledgeGraph is a
 * singleton.
 */
public  class  KnowledgeGraph {
	private static final KnowledgeGraph INSTANCE = new KnowledgeGraph(); 
	private Map<String, Node> nodeMap = new HashMap<String, Node>();
	private Map<String, Triple> tripleMap = new HashMap<String, Triple>();
	private Map<String, Set<Triple>> queryMapSet = new HashMap<String, Set<Triple>>();
	private Map<String, Predicate> predicateMap = new HashMap<String, Predicate>();
	
	
	/**
	 * The constructor is private so only the singleton may be used.
	 */
	private KnowledgeGraph(){
		
	}	
	
	/**
	 * @return the singleton instance of KnowledgeGraph.
	 */
	public static KnowledgeGraph getInstance() {
        return INSTANCE;
    }
		
	/**
	 * Imports a list of triples. 
	 * @param tripleList the list of tripleLiterals to be imported.
	 */
	public void importTriples(List<TripleLiteral> tripleList){
		for(TripleLiteral tripleLiteral : tripleList){
			getTriple(
				getNode(tripleLiteral.getSubject()), 
				getPredicate(tripleLiteral.getPredicate()), 
				getNode(tripleLiteral.getObject())
			);
        }
	}
	
	
	/**
	 * Imports a triple using Nodes for the subject and object. This allows for preserving the different sub-classes of Nodes.
	 * @param subject A Node that is the subject.
	 * @param predicate A string representing the predicate.
	 * @param object A Node to be used for the object.
	 * @return the imported triple, or the existing one if one exists.
	 */
	public Triple importTriple(Node subject, String predicate, Node object){
			return getTriple(
				addNodeIfNeeded(subject), 
				getPredicate(predicate), 
				addNodeIfNeeded(object)
			);
	}
	
	/**
	 * Executes a query against the KnowledgeGraph.
	 * @param query the Triple to search the KnowledgeGraph.
	 * @return a set of triples that match the query.
	 */
	public Set<Triple> executeQuery(TripleLiteral query){
		Set<Triple> tripleSet = queryMapSet.get(query.getIdentifier().toLowerCase());
		if(tripleSet == null){
			tripleSet = new HashSet<Triple>();
		}
		return tripleSet;
	}
	
	
	/**
	 * Gets a node for a given identifier. If one does not exist, a new Node is created.
	 * @param identifier the identifier to search for.
	 * @return the matching Node for the identifier.
	 */
	public Node getNode(String identifier){
		Node node =  nodeMap.get(identifier.toLowerCase());
		if(node == null){
			System.out.println("BUSTED");
			node = new Node(identifier);
			nodeMap.put(node.getIdentifier().toLowerCase(), node);
		}
		return node;
	}
	
	/**
	 * Gets a node by an identifier, and throws an error if the Node does not exist.
	 * @param identifier The id to look up in the nodeMap.
	 * @return the found Node.
	 * @throws KnowledgeException if the Node is not found.
	 */
	public Node getExistingNodeById(String identifier) throws KnowledgeException{
		Node resultNode = nodeMap.get(identifier.toLowerCase());
		if(resultNode == null){
			throw new KnowledgeException(0, "Unknown identity.", identifier);
		}
		return resultNode;
	}
	
	/**
	 * Verifies that a given identifier does not exist in the KnowledgeGraph.
	 * @param identifier the identifier to check.
	 * @throws KnowledgeException if the identifier does already exist.
	 */
	public void validateIdentifierDoesNotExist(String identifier) throws KnowledgeException{
		Node resultNode = nodeMap.get(identifier.toLowerCase());
		if(resultNode != null){
			throw new KnowledgeException(0, "Identity already exists.", identifier);
		}
	}
	
	/**
	 * Adds a node to the nodeMap if it does not already exist.
	 * @param newNode the node to be added.
	 * @return the Node.
	 */
	public Node addNodeIfNeeded(Node newNode){
		Node node =  nodeMap.get(newNode.getIdentifier().toLowerCase());
		if(node == null){
			nodeMap.put(newNode.getIdentifier().toLowerCase(), newNode);
			return newNode;
		}
		return node;
	}
	
	/**
	 * Gets a Predicate for a given identifier. If one does not exist, a new Predicate is created.
	 * @param identifier the identifier to search for.
	 * @return the matching Predicate for the identifier.
	 */
	public Predicate getPredicate(String identifier){
		Predicate predicate =  predicateMap.get(identifier.toLowerCase());
		if(predicate == null){
			predicate = new Predicate(identifier);
			predicateMap.put(predicate.getIdentifier().toLowerCase(), predicate);
		}
		return predicate;
	}
	
	/**
	 * Gets a Triple for the given subject, predicate, and object. If one does not exist, a new Triple is created,
	 * and all of its permutations are added to the queryMap.
	 * @param subject the subject of the Triple
	 * @param predicate the predicate of the Triple
	 * @param object the object of the Triple
	 * @return the triple stored in the tripleMap.
	 */
	public Triple getTriple(Node subject, Predicate predicate, Node object){
		Triple triple = new Triple(subject, predicate, object);
		Triple searchedTriple =  tripleMap.get(triple.getIdentifier().toLowerCase());
		if(searchedTriple == null){
			/** The triple is not in the map yet. Add it to the queryMap and the tripleMap. */
			pushToMapSet(triple.getIdentifier(), triple);
			pushToMapSet(subject.getIdentifier() + " " + predicate.getIdentifier() + " ?", triple);
			pushToMapSet(subject.getIdentifier() + " ? " + object.getIdentifier(), triple);
			pushToMapSet(subject.getIdentifier() + " ? ?", triple);
			pushToMapSet("? " + predicate.getIdentifier() + " " + object.getIdentifier(), triple);
			pushToMapSet("? " + predicate.getIdentifier() + " ?", triple);
			pushToMapSet("? ? " + object.getIdentifier(), triple);
			pushToMapSet("? ? ?", triple);

			tripleMap.put(triple.getIdentifier().toLowerCase(), triple);
		}
		return triple;
	}
	
	
	
	/**
	 * Gets all the objects from the results of a query.
	 * @param tripleLiteral the query
	 * @return a set of the objects matching the query.
	 */
	public Set<Node> getObjectsFromQuery(TripleLiteral tripleLiteral){
		Set<Node> objects = new HashSet<Node>();
		Set<Triple> triples = executeQuery(tripleLiteral);
		for(Triple triple : triples){
			objects.add(triple.getObject());
        }
		return objects;
	}
	
	
	/**
	 * Adds a Triple to corresponding set in the queryMap. If a set is not found, then a new set in inserted.
	 * @param identifier the identifier of the set to search for.
	 * @param triple the triple to insert into the set at the matching identifier.
	 */
	private void pushToMapSet(String identifier, Triple triple){
		Set<Triple> tripleSet =  queryMapSet.get(identifier.toLowerCase());
		if(tripleSet == null){
			tripleSet = new HashSet<Triple>();
			queryMapSet.put(identifier.toLowerCase(), tripleSet);
		}
		tripleSet.add(triple);
	}
	
	/**
	 * Removes a triple for a given identifier 
	 * @param identifier the identifier the triple is associated with.
	 * @param triple the triple to remove.
	 */
	private void removeFromMapSet(String identifier, Triple triple){
		Set<Triple> tripleSet =  queryMapSet.get(identifier.toLowerCase());
		if(tripleSet == null){
			return;
		}
		tripleSet.remove(triple);
	}

	/**
	 * Deletes a triple from the knowledgeGraph. Useful for updating existing facts.
	 * @param subject the Node of the subject for the triple to be deleted. 
	 * @param predicateString the string of the predicate for the triple to be deleted.
	 * @param object the Node of the object for the triple to be deleted. 
	 */
	public void deleteTriple(Node subject, String predicateString, Node object) {
		Predicate predicate = new Predicate(predicateString);
		Triple triple = new Triple(subject, predicate, object);
		Triple searchedTriple =  tripleMap.get(triple.getIdentifier().toLowerCase());
		if(searchedTriple == null){
			return;
		}
		tripleMap.remove(searchedTriple.getIdentifier().toLowerCase());
		removeFromMapSet(searchedTriple.getIdentifier(), searchedTriple);
		removeFromMapSet(subject.getIdentifier() + " " + predicate.getIdentifier() + " ?", searchedTriple);
		removeFromMapSet(subject.getIdentifier() + " ? " + object.getIdentifier(), searchedTriple);
		removeFromMapSet(subject.getIdentifier() + " ? ?", searchedTriple);
		removeFromMapSet("? " + predicate.getIdentifier() + " " + object.getIdentifier(), searchedTriple);
		removeFromMapSet("? " + predicate.getIdentifier() + " ?", searchedTriple);
		removeFromMapSet("? ? " + object.getIdentifier(), searchedTriple);
		removeFromMapSet("? ? ?", searchedTriple);
		
	}
	
}
