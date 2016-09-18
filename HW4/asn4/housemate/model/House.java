package cscie97.asn4.housemate.model;

import java.util.Set;
import cscie97.asn4.knowledge.engine.KnowledgeGraph;
import cscie97.asn4.knowledge.engine.TripleLiteral;
import cscie97.asn4.knowledge.engine.Triple;

import cscie97.asn4.housemate.exception.HouseMateModelException;

/**
 * House class can contain rooms and occupants. 
 * The rooms and occupants are stored in the knowledge graph, 
 * the single source of truth.
 *
 */
public class House extends HouseMateNode {

	/**
	 * Constructor.
	 * @param identifierString The identifier for the house.
	 */
	public House(String identifierString) {
		super(identifierString, identifierString);
	}

	/**
	 * Gets the rooms for the house.
	 * @return a set of rooms added to the house.
	 */
	@SuppressWarnings("unchecked")
	public Set<Room> getRooms(){
		return (Set<Room>) (Set<?>) KnowledgeGraph.getInstance().getObjectsFromQuery(new TripleLiteral(getIdentifier(), "contains_room", "?"));
	}

	/**
	 * Adds a room to the house.
	 * @param tempRoom The room to be added to the house.
	 */
	public void addRoom(Room tempRoom) {
		KnowledgeGraph.getInstance().importTriple(this, "contains_room", tempRoom);
	}

	/**
	 * Gets the occupants for the house.
	 * @return a set of occupants.
	 */
	@SuppressWarnings("unchecked")
	public Set<Occupant> getOccupants(){
		return (Set<Occupant>) (Set<?>)KnowledgeGraph.getInstance().getObjectsFromQuery(new TripleLiteral(getIdentifier(), "contains_occupant", "?"));
	}
	
	/**
	 * Adds an occupant to the house.
	 * @param occupant The occupant to add
	 * @param relation The relation to set for the occupant and house.
	 * @throws HouseMateModelException on invalid relation type.
	 */
	public void addOccupant(Occupant occupant, String relation) throws HouseMateModelException {
		Triple triple = KnowledgeGraph.getInstance().importTriple(this, "contains_occupant", occupant);
		Occupant storedOccupant = (Occupant) triple.getObject();
		storedOccupant.setRelation(this, relation);
	}
	
}
