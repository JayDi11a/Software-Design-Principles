package cscie97.asn2.housemate.model;

import java.util.Set;


public class Occupant extends House {


	public Occupant(String id, String name, String type, Set<Appliance> appliances, Set<Sensor> sensors, InTheHouse category) {
		super(id, name, type, appliances, sensors, category);
		this.setWhatsInTheHouse(InTheHouse.OCCUPANT);
	}


	public static boolean validateHouse(Occupant house) {
		return (House.validateHouse(house));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( super.toString());
		return sb.toString();
	}

}
