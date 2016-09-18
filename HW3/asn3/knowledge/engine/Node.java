package cscie97.asn3.knowledge.engine;

/**
 * Node is used to represent subjects and objects.
 */
public class Node {
	
	private final String identifier;
	
	/**
	 * @param identifierString the string to store.
	 */
	public Node(String identifierString) {
		identifier = identifierString;
	}

	/**
	 * @return the identifier for the node.
	 */
	public String getIdentifier(){
		return identifier;
	}
}
