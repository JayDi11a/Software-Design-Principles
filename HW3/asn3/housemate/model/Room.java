package cscie97.asn3.housemate.model;

import java.util.Set;

import cscie97.asn3.knowledge.engine.KnowledgeGraph;
import cscie97.asn3.knowledge.engine.TripleLiteral;


/**
 * A room has a floor and contains sensors and appliances.
 *
 */
public class Room extends HouseMateNode {

	private int floor;
	
	/**
	 * @param identifierString the identifier of the room.
	 * @param name the friendly name of the room.
	 * @param type the type of room.
	 * @param floor the floor of the room
	 */
	public Room(String identifierString, String name, String type, int floor) {
		super(identifierString, name);
		this.floor = floor;
		this.type = type;
	}

	/**
	 * @return the floor
	 */
	public int getFloor() {
		return floor;
	}


	/**
	 * Adds a sensor to a room.
	 * @param sensor The sensor to add to the room.
	 */
	public void addSensor(Sensor sensor) {
		KnowledgeGraph.getInstance().importTriple(this, "contains_sensor", sensor);
	}
	
	/**
	 * Adds an appliance to a room.
	 * @param appliance The appliance to add to the room.
	 */
	public void addAppliance(Appliance appliance) {
		KnowledgeGraph.getInstance().importTriple(this, "contains_appliance", appliance);
	}
	
	/**
	 * Gets the sensors in a room.
	 * @return a set of Sensors.
	 */
	@SuppressWarnings("unchecked")
	public  Set<Sensor> getSensors() {
		return (Set<Sensor>) (Set<?>)KnowledgeGraph.getInstance().getObjectsFromQuery(new TripleLiteral(getIdentifier(), "contains_sensor", "?"));
	}

	/**
	 * Gets the appliances in a room.
	 * @return a set of Appliances.
	 */
	@SuppressWarnings("unchecked")
	public Set<Appliance> getAppliances() {
		return (Set<Appliance>) (Set<?>) KnowledgeGraph.getInstance().getObjectsFromQuery(new TripleLiteral(getIdentifier(), "contains_appliance", "?"));
	}
	
}
