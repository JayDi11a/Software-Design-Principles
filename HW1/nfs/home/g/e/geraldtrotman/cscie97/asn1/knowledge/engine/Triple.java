package cscie97.asn1.knowledge.engine;

/**
 * This is a Triple, a subject (Node), predicate, and an object (Node) and the relationship
 * stored in the KnowledgeGraph.
 *
 * @author Gerald Trotman
 * @see Triple
 *
 */

public class Triple {
	
	/**
	 * private unique identifier for the Triple of the form:
	 * subject.identifier + " " + predicate.identifier + " " + object.identifier
 	 */	
	private String identifier;

	/**
	 * returns Triple identifier  
	 *
	 * @return the string identifier for the Triple
	 *
	 *
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * association to the Subject instance which could include the wild card.
	 *
	 */
	private Node subject;


	/**
	 * Gets the Subject Node of the Triple (the first of the Triple)
	 *
	 * @return the Node subject of the Triple
	 */
	public Node getSubject() {
		return subject;
	}


	/**
	 * association to the Object instance which could include the wild card.
	 *
	 */
	private Node object;


	/**
	 * Gets the Object Node of the Triple (the third of the Triple)
	 *
	 * @return the Node object of the Triple
	 */
	public Node getObject() {
		return object;
	}

	/**
	 * association to the Predicate instance which could include the wild card.
	 *
	 */
	private Predicate predicate;

	/**
	 * Gets the predicate of the Triple ( the second of the Triple)
	 *
	 * @return The Predicate of the Triple
	 * 
	 */
	public Predicate getPredicate() {
		return predicate;
	}
	
	/**
	 * Class Constructor.
 	 *
	 * @param subject string identifying the Subject node including a wild card
	 * @param predicate string identifying the Predicate node including a wild card
	 * @param object   string identifying the Object node including a wild card
	 *
	 */
	public Triple(Node subject, Predicate predicate, Node object) {
		this.subject = (subject == null) ? new Node("?") : subject;
		this.predicate = (predicate == null) ? new Predicate("?") : predicate;
		this.object = (object == null) ? new Node("?") : object;
		this.identifier = this.subject.getIdentifier() + " " + this.predicate.getIdentifier() + " " + this.object.getIdentifier() + ".";
	}

}
