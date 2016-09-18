package cscie97.asn4.housemate.model;

import java.util.Set;
import cscie97.asn4.knowledge.engine.KnowledgeGraph;
import cscie97.asn4.knowledge.engine.Node;
import cscie97.asn4.knowledge.engine.TripleLiteral;
import cscie97.asn4.housemate.exception.HouseMateModelException;
import cscie97.asn4.housemate.exception.InvalidCommandException;

/**
 * Occupants can belong to multiple houses. They can have different relationships with each house.
 * The type of occupant can be an adult, child, pet, or unknown.
 */
public class Occupant extends HouseMateNode {

	/**
	 * Constructs a new occupant.
	 * @param identifierString the identifier of the occupant.
	 * @param type The type can be adult, child, pet, or unknown.
	 * @throws HouseMateModelException on invalid type.
	 */
	public Occupant(String identifierString, String type) throws HouseMateModelException {
		super(identifierString, identifierString);
		type  = type.toLowerCase();
		if(!type.equals("adult") && !type.equals("child") && !type.equals("pet") && !type.equals("unknown")){
//			throw new InvalidCommandException(0, "occupant type must be adult, child, pet, or unknown", null);
		}
		this.type = type;
	}

	/**
	 * For a given house, return the relationship of the occupant.
	 * @param house the House in consideration for the relationship.
	 * @return the relationship
	 */
	public String getRelation(House house) {
		Set<Node> relationships = KnowledgeGraph.getInstance().getObjectsFromQuery(new TripleLiteral(getRelationshipNodeIdentifier(house), "has_relationship", "?"));
		for(Node node : relationships){
            return node.getIdentifier().toLowerCase(); // return the first one.
        }
		return "unknown"; // None found, return unknown.
	}

	/**
	 * Sets the relationship for a given house.
	 * @param house the House in consideration for the relationship.
	 * @param relation the type of relationship.
	 * @throws InvalidCommandException when the relation is invalid.
	 */
	public void setRelation(House house, String relation) throws InvalidCommandException {
		relation  = relation.toLowerCase();
		if(!relation.equals("resident") && !relation.equals("guest") && !relation.equals("burglar")){
//			throw new InvalidCommandException(0, "relation must be resident, guest, or burglar", null);
		}
		
		Node occupantHouseIdentifier = new Node(getRelationshipNodeIdentifier(house));
		Node relationKeyNode = new Node(relation); // Store the relationship in a node.
		KnowledgeGraph.getInstance().importTriple(occupantHouseIdentifier, "has_relationship", relationKeyNode);
	}

	
	/**
	 * Gets a unique identifier for the house and occupant.
	 * @param house to create the unique id from.
	 * @return a string for the identifier.
	 */
	private String getRelationshipNodeIdentifier(House house){
		return house.getIdentifier() + ":"+ this.getIdentifier();
	}
}
