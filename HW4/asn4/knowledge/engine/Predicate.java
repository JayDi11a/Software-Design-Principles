package cscie97.asn4.knowledge.engine;

/**
 * Predicate is used to represent the predicate.
 */
public class Predicate {
	
	private final String identifier;
	
	/**
	 * @param identifierString the string to store in the Predicate.
	 */
	public Predicate(String identifierString) {
		identifier = identifierString;
	}
	
	/**
	 * @return the identifier for the Predicate.
	 */
	public String getIdentifier(){
		return identifier;
	}
}
