package cscie97.asn2.housemate.model;

import java.util.Set;

/**
 * Abstract class that represents a house.The attributes are common to all houses and each
 * entity we are modeling is unique such as Room or Occupant.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see HouseMateAPI
 * @see Occupant
 * @see Room
 *
 */



public abstract class House {

	/**
	 * A unique string identifier for each house item
	 */
	private String id;

	/**
	 * Name of the House item
	 */
	private String name;

	
	private String type;

		
	private Set<Appliance> appliances;


	private Set<Sensor> sensors;

	/**
	 * Which type of entity in the house.It is currently being used to 	
	 * differentiate Rooms from Occupants.I wanted to include this for appliances and
	 * sensors as well though.
	 */
	private InTheHouse inTheHouse;
	
	public String getID() {
		return id;
	}

	protected void setID(String id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}
	
	protected void setType(String type) {
		this.type = type;
	}


	public Set<Appliance> getAppliances() {
		return appliances;
	}


	protected void setAppliances(Set<Appliance> appliances) {
		this.appliances = appliances;
	}


	public Set<Sensor> getSensors() {
		return sensors;
	}


	protected void setSensors(Set<Sensor> sensors) {
		this.sensors = sensors;	
	}


	public InTheHouse getWhatsInTheHouse() {
		return inTheHouse;
	}

	protected void setWhatsInTheHouse(InTheHouse inTheHouse) {
		this.inTheHouse = inTheHouse;
	}



	/**
	 * Class constructor
	 * 
	 * @param id	the unique house identifier
	 * @param name  the house item name
	 * @param type	the type of entity in the house
	 * @param appliance The specific appliance name in the house
	 * @param sensor 	The specificly named senror in the house
	 */
	public House(String id, String name, String type, Set<Appliance> appliances, Set<Sensor> sensors, InTheHouse category) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.appliances = appliances;
		this.sensors = sensors;
		this.inTheHouse = category;
	}


	/**
	 * Public static method that makes sure all the required fields are set
	 * and that all items are valid once imported into the HouseMateAPI
	 *
	 * @param house the house to be validated for correct properties
	 * @return true if all properties are valid and false if not
	 */
	public static boolean validateHouse(House house) {
		return (
			(house.getID() != null && house.getID().length() > 0) &&
			(house.getName() != null && house.getName().length() > 0) &&
			(house.getType() != null && house.getType().length() > 0) &&
			(house.getAppliances() != null && house.getAppliances().size() > 0) &&
			(house.getSensors() != null && house.getSensors().size() > 0)
		);
	}

	/**
	 * Gives back a string representation of the house item for troubleshooting
	 *
	 * @return string representation of the house item.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("CONTENT:\n"));
		sb.append(String.format("\tID: [%s]\n", this.getID()));
		sb.append(String.format("\tNAME: [%s]\n", this.getName()));
		sb.append(String.format("\tTYPE: [%s]\n", this.getType()));
		sb.append(String.format("\tAPPLIANCE: [%s]\n", this.getAppliances().toString()));
		sb.append(String.format("\tSENSOR: [%s]\n", this.getSensors().toString()));
		return sb.toString();
	}


}
