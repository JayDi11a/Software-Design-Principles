package cscie97.asn4.knowledge.engine;

/**
 * The TripleLiteral class contains the strings used to create
 * a real Triple.
 */
public class TripleLiteral {
	private final String subject;
	private final String predicate;
	private final String object;
	private final String identifier;

	/**
	 * Creates a TripleLiteral from 3 strings.
	 * @param subject the subject of the Triple
	 * @param predicate the predicate of the Triple
	 * @param object the object of the Triple
	 */
	public TripleLiteral(String subject, String predicate, String object) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
		identifier = subject + " " + predicate + " " + object;
	}

	public String getSubject() {
		return subject;
	}

	public String getPredicate() {
		return predicate;
	}

	public String getObject() {
		return object;
	}

	public String getIdentifier() {
		return identifier;
	}

}
