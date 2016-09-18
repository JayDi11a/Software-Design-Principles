package cscie97.asn3.housemate.model;

import java.util.Set;

import cscie97.asn3.knowledge.engine.KnowledgeGraph;
import cscie97.asn3.knowledge.engine.Node;
import cscie97.asn3.knowledge.engine.Triple;
import cscie97.asn3.knowledge.engine.TripleLiteral;
import cscie97.asn3.housemate.model.exception.InvalidCommandException;

/**
 * HouseMateNode extends node so that it can be used in the knowledge graph.
 * Each HouseMateNode has an identifier, and a user friendly name. The
 * user friendly name is the shortened name without all the globally uniquer prefixes.
 *
 */
public abstract class HouseMateNode extends Node {

	protected String name;
	protected String type;

	/**
	 * Constructor.
	 * @param identifierString the unique identifier.
	 * @param name the user friendly name.
	 */
	public HouseMateNode(String identifierString, String name) {
		super(identifierString);
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the user friendly name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the statuses associated with this node.
	 * @return a set of Statuses.
	 */
	@SuppressWarnings("unchecked")
	public Set<Status> getStatuses(){
		return (Set<Status>) (Set<?>) KnowledgeGraph.getInstance().getObjectsFromQuery(new TripleLiteral(getIdentifier(), "has_status", "?"));
	}
	
	/**
	 * Sets a status on this node.
	 * @param tempStatus the Status to set on this node.
	 * @throws InvalidCommandException when there is an error setting the status.
	 */
	public void setStatus(Status tempStatus) throws InvalidCommandException {
		Triple triple = KnowledgeGraph.getInstance().importTriple(this, "has_status", tempStatus);
		Status status = (Status) triple.getObject();
		status.setValue(tempStatus.getValue());
	}

}
