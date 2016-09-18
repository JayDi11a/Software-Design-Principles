package cscie97.asn1.knowledge.engine;

/**
 * This is a Node, part of a Triple. A node is either a subjet or an object of
 * the triple.
 *
 * @author Gerald Trotman
 * @see Triple
 *
 */


public class Node {


	/**
	 * unique using the Flyweight pattern
	 */
	private String identifier;

	/**
	 * Returns Node string identifier
 	 *
	 * @return the string identifier for this particular Node
	 *
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	
	/**
	 * Class constructior 
	 *
	 * @param identifier	the string that should set this Node apart from all others.
	 *
	 *
	 */
	public Node(String identifier) {
		this.identifier = identifier;
	}

}


