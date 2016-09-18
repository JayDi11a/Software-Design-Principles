package cscie97.asn2.housemate.model;



import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;




public class HouseMateAPI implements IHouseMateAPI {

	private Set<Appliance> appliances;

	private Set<Sensor> sensors;


	private Set<House> houseContents;



	private static IHouseMateAPI instance = null;



	private HouseMateAPI() {
		this.houseContents = new HashSet<House>(){ };
		this.appliances = new HashSet<Appliance>() { };
		this.sensors = new HashSet<Sensor>() { };
	}



	public static synchronized IHouseMateAPI getInstance() {
		if (instance == null) {
			instance = new HouseMateAPI();
		}
		return instance;
	}



	public boolean validateAccessToken(String guid) {
		if (guid != null && guid.length() > 0) {
			return true;
		}
		return false;
	}





	public void importHouse(String guid, List<House> houseContents) {
		if (validateAccessToken(guid)) {
			for (House houseContent : houseContents) {
				if (houseContent instanceof Occupant && Occupant.validateHouse(houseContent)) {
					this.houseContents.add(houseContent);
				} else if (houseContent instanceof Room && Room.validateHouse(houseContent)) {
					this.houseContents.add(houseContent);
				}
			}
		}
	}


	public List<Room> getAllRooms() {
		List<Room> allRooms = new ArrayList<Room>();
		for (House houseContent : this.houseContents) {
			if (houseContent instanceof Room) {
				allRooms.add((Room)houseContent);
			}
		}
		return allRooms;
	}

	

	public List<Occupant> getAllOccupants() {
		List<Occupant> allOccupants = new ArrayList<Occupant>();
		for (House houseContent : this.houseContents) {
			if (houseContent instanceof Occupant) {
				allOccupants.add((Occupant)houseContent);
			}
		}
		return allOccupants;
	}



	public List<House> getAllHouses() {
		List<House> allHouses = new ArrayList<House>();
		allHouses.addAll(this.houseContents);
		return allHouses;
	}

	public Set<Appliance> getAppliances() {
		return this.appliances;
	}


	public Set<Sensor> getSensors() {
		return this.sensors;
	}


	public int getNumberHouseItems() {
		return this.houseContents.size();
	}
}
