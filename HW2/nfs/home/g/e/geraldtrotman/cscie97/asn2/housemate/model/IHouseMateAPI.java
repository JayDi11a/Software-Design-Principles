package cscie97.asn2.housemate.model;

import java.util.List;
import java.util.Set;













public interface IHouseMateAPI {





	public boolean validateAccessToken(String guid);



		
	public void importHouse(String guid, List<House> houseContent);




	public List<House> getAllHouses();



	public List<Occupant> getAllOccupants();



	public List<Room> getAllRooms();




	public Set<Appliance> getAppliances();



	public Set<Sensor> getSensors();



	public int getNumberHouseItems();


}
