package cscie97.asn4.knowledge.engine;

/**
 * The Triple contains the subject, predicate, and object.
 */
public class Triple  {
	
	private final String identifier;
	private final Node subject;
	private final Node object;
	private final Predicate predicate;

	/**
	 * Creates a triple with a subject, predicate and object.
	 * @param subject the subject to store in the Triple.
	 * @param predicate the predicate to store in the Triple.
	 * @param object the object to store in the Triple.
	 */
	public Triple(Node subject, Predicate predicate, Node object) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
		identifier = subject.getIdentifier() + " " + predicate.getIdentifier() + " " + object.getIdentifier();
	}

	public String getIdentifier(){
		return identifier;
	}
	
	public Node getSubject(){
		return subject;
	}
		
	public Predicate getPredicate(){
		return predicate;
	}
	
	public Node getObject(){
		return object;
	}
}
