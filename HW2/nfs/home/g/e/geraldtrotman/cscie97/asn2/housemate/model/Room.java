package cscie97.asn2.housemate.model;


import java.util.Set;



public class Room extends House {



	public Room(String id, String name, String type, Set<Appliance> appliances, Set<Sensor> sensors, InTheHouse category) {
		super(id, name, type, appliances, sensors, category);
		this.setWhatsInTheHouse(InTheHouse.ROOM);
	}



	public static boolean validateHouse(Room house) {
		return (House.validateHouse(house));
	}



	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( super.toString());
		return sb.toString();
	}


}

