package cscie97.asn1.knowledge.engine;

/**
 * This is the Predicate, part of the Triple. This is the middle part of the Triple identifier 
 * 
 * @author Gerald Trotman
 * @see Triple
 *
 */

public class Predicate {
	
	/**
	 * uses the Flyweight pattern for uniquely identifying the Predicate
	 *
	 */
	private String identifier;



	/** 
	 * Returns the Predicate identifier as a string
	 *
	 * @return returns the predicate identifer as a string
	 */
	public String getIdentifier() {
		return identifier;
	}


	/**
	 * Class constructor.
	 *
	 * @param identifier the String that should uniquely identify this Node from all others
	 * 
	 */
	public Predicate(String identifier) {
		this.identifier = identifier;
	}

}
