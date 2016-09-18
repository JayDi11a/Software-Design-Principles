package cscie97.asn3.housemate.model;

import java.util.Map;
import java.util.Set;
import cscie97.asn3.knowledge.engine.KnowledgeGraph;
import cscie97.asn3.knowledge.engine.Node;
import cscie97.asn3.knowledge.engine.QueryEngine;
import cscie97.asn3.knowledge.engine.TripleLiteral;
import cscie97.asn3.housemate.model.exception.InvalidCommandException;
import cscie97.asn3.housemate.model.exception.HouseMateModelException;
import cscie97.asn3.housemate.model.exception.AuthTokenException;

/**
 * HouseMateModelService is the public facing API for the model service.
 * It is a singleton.
 * All data is stored in the knowledge graph, so it can be queried, as well
 * as used to retrieve the proper relationships.
 *
 */
public class HouseMateModelService implements HouseMateModelServiceInterface {

	private static final HouseMateModelService INSTANCE = new HouseMateModelService(); 

	private final KnowledgeGraph knowledgeGraph = KnowledgeGraph.getInstance();
	
	/**
	 * The constructor is private so only the singleton may be used.
	 */
	private HouseMateModelService(){}	
	
	/**
	 * @return the singleton instance of HouseMateModelService.
	 */
	public static HouseMateModelService getInstance() {
        return INSTANCE;
    }
	
	/**
	 * Creates a house
	 * @param authToken Authorization token.
	 * @param identifier Unique Identifier for the house.
	 * @throws HouseMateModelException on invalid identifier.
	 */
	public void createHouse(String authToken, String identifier) throws HouseMateModelException {
		validateAuthToken(authToken);
		validateIdentifier(identifier);
		knowledgeGraph.validateIdentifierDoesNotExist(identifier);
		House tempHouse = new House(identifier); // tempHouse will be stored in the knowledge graph.
		Node system = new Node("system"); // System is used to contain all houses and occupants.
		knowledgeGraph.importTriple(system, "contains", tempHouse); // Since house may exist already, we want the existing one, with all of its set values.
	}

	/**
	 * Creates a room in a house.
	 * @param authToken Authorization token.
	 * @param identifier The identifier of the room. Must be unique for the house.
	 * @param floor The floor of the room.
	 * @param roomType The type of room.
	 * @param houseIdentifier The identifier of the house.
	 * @throws HouseMateModelException when there is an error creating the house.
	 */
	public void createRoom(String authToken, String identifier, int floor, String roomType, String houseIdentifier) throws HouseMateModelException {
		validateAuthToken(authToken);
		validateIdentifier(identifier);
		House house = (House) knowledgeGraph.getExistingNodeById(houseIdentifier.toLowerCase()); // This must exist or else an error is thrown.
		Room tempRoom = new Room(houseIdentifier + ":" + identifier, identifier, roomType, floor);
		knowledgeGraph.validateIdentifierDoesNotExist(tempRoom.getIdentifier());
		house.addRoom(tempRoom);
	}

	/**
	 * Creates an occupant.
	 * @param authToken Authorization token.
	 * @param identifier The unique id of the occupant.
	 * @param type The type of occupant. For example, adult, child, animal, etc.
	 * @throws HouseMateModelException when there is an error creating the room.
	 */
	public void createOccupant(String authToken, String identifier, String type) throws HouseMateModelException {
		validateAuthToken(authToken);
		validateIdentifier(identifier);
		Occupant tempOccupant = new Occupant(identifier, type);
		knowledgeGraph.validateIdentifierDoesNotExist(tempOccupant.getIdentifier());
		Node system = new Node("system");
		knowledgeGraph.importTriple(system, "has_occupant", tempOccupant); // Since house may exist already, we want the existing one, with all of its set values.

	}

	/**
	 * Adds an occupant to a house and sets the occupants relation to the house.
	 * @param authToken Authorization token.
	 * @param occupantIdentifier The identifier of the occupant.
	 * @param houseIdentifier The identifier of the house.
	 * @param relation The relation of the occupant to the house. For example, resident, guest, burglar.
	 * @throws HouseMateModelException when there is an error creating the occupant.
	 */
	public void addOccupant(String authToken, String occupantIdentifier, String houseIdentifier, String relation) throws HouseMateModelException {
		Occupant occupant = (Occupant) knowledgeGraph.getExistingNodeById(occupantIdentifier.toLowerCase());
		House house = (House) knowledgeGraph.getExistingNodeById(houseIdentifier.toLowerCase());
		house.addOccupant(occupant, relation);
	}

	/**
	 * Creates a sensor.
	 * @param authToken Authorization token.
	 * @param identifier The identifier for the sensor. Must be unique for the room.
	 * @param type The type of sensor. For example, smokedetector, camera etc.
	 * @param roomIdentifier the identifier of the room.
	 * @throws HouseMateModelException when there is an error adding the occupant.
	 */
	public void createSensor(String authToken, String identifier, String type, String roomIdentifier) throws HouseMateModelException {
		validateAuthToken(authToken);
		validateIdentifier(identifier);
		Room room = (Room) knowledgeGraph.getExistingNodeById(roomIdentifier.toLowerCase());
		Sensor tempSensor = new Sensor(roomIdentifier + ":" + identifier, identifier, type);
		knowledgeGraph.validateIdentifierDoesNotExist(tempSensor.getIdentifier());
		room.addSensor(tempSensor);
	}

	/**
	 * Creates an appliance.
	 * @param authToken Authorization token.
	 * @param identifier The identifier for the appliance. Must be unique for the room.
	 * @param type The type of appliance. For example, TV, Pandora, door etc.
	 * @param roomIdentifier The identifier of the room where the appliance is.
	 * @throws HouseMateModelException when there is an error creating the appliance.
	 */
	public void createAppliance(String authToken, String identifier, String type, String roomIdentifier) throws HouseMateModelException {
		validateAuthToken(authToken);
		validateIdentifier(identifier);
		Room room = (Room) knowledgeGraph.getExistingNodeById(roomIdentifier.toLowerCase());
		Appliance tempAppliance = new Appliance(roomIdentifier + ":" + identifier, identifier, type);
		knowledgeGraph.validateIdentifierDoesNotExist(tempAppliance.getIdentifier());
		room.addAppliance(tempAppliance);
	}

	/**
	 * Sets the value for a given status
	 * @param authToken Authorization token.
	 * @param identifier The identifier of the sensor or appliance.
	 * @param statusName The name of the status to set the value for.
	 * @param statusValue The value of the status.
	 * @throws HouseMateModelException when a value is invalid.
	 */
	public void setStatus(String authToken, String identifier, String statusName, String statusValue) throws HouseMateModelException {
		validateAuthToken(authToken);
		validateIdentifier(statusName);
		HouseMateNode node = (HouseMateNode) knowledgeGraph.getExistingNodeById(identifier.toLowerCase());
		Status tempStatus = new Status(identifier + ":" + statusName, statusName, statusValue);
		node.setStatus(tempStatus);
	}

	/**
	 * Shows the configuration for a given identifier. If identifier is null, then the entire configuration is displayed. 
	 * The more specific show method is called based off the type of the identifier.
	 * @param authToken Authorization token.
	 * @param identifier The identifier of the 
	 * @throws HouseMateModelException on error showing configuration
	 */
	public void showConfiguration(String authToken, String identifier) throws HouseMateModelException {
		if(identifier == null){
			Set<House> houses = getHouses();
			for(Node house1 : houses){
				House house = (House) house1;
				showHouseConfig(house);
	        }
		}else{
			HouseMateNode node = (HouseMateNode) knowledgeGraph.getExistingNodeById(identifier.toLowerCase());
			
			switch(node.getClass().getSimpleName()){
				case "House": showHouseConfig((House) node); break;
				case "Room": showRoomConfig((Room) node, ""); break;
				case "Appliance": showApplianceConfig((Appliance) node, ""); break;
				case "Sensor": showSensorConfig((Sensor) node, ""); break;
				case "Occupant": showOccupantConfig((Occupant) node, ""); break;
				case "Status": showStatusConfig((Status) node, ""); break;
			}
		}
	}
	
	/**
	 * Sets the validation for status options.
	 * @param authToken Authorization token.
	 * @param identifier The id of the status to be configured. The id is the appliance identifier:status name.
	 * @param params A map of key-value pairs to configure the options. Params.type = enum|range. 
	 * Enum options include String:params.values=pipe delimited values. Range options include int:params.min, int:params.max,
	 * @throws HouseMateModelException when the status option is invalid.
	 */
	public void setStatusOptions(String authToken, String identifier, Map<String, String> params) throws HouseMateModelException {
		validateAuthToken(authToken);
		Status status = (Status) knowledgeGraph.getExistingNodeById(identifier.toLowerCase());
		status.setOptions(params);
	}

	/**
	 * Executes a raw query against the knowledge graph.
	 * @param authToken Authorization token.
	 * @param query The query to execute against the knowledge graph.
	 * @throws HouseMateModelException on invalid identifier.
	 */
	public void query(String authToken, String query) throws HouseMateModelException {
		QueryEngine queryEngine = new QueryEngine();
		queryEngine.executeQuery(query);
		
	}
	
	
	
	/**
	 * Shows the configuration for a house.
	 * @param house The house to show the config for.
	 */
	private void showHouseConfig(House house){
		 System.out.println(house.getName());
         
         System.out.println("\t Occupants:");
         for(Occupant occupant : house.getOccupants()){
        	 showOccupantConfigForHouse(occupant, house, "\t\t ");
         }
         
         System.out.println("\t Rooms:");
         for(Room room : house.getRooms()){
        	 showRoomConfig(room, "\t\t ");
         } // end room loop
	}
	
	/**
	 * Shows the configuration for an occupant in a house.
	 * @param occupant the occupant to show
	 * @param house the house containing the occupant.
	 * @param indent the indenting to be used.
	 */
	private void showOccupantConfigForHouse(Occupant occupant, House house, String indent){
		 System.out.println(indent + occupant.getName() + " (" + occupant.getType() + ", " + occupant.getRelation(house) + ")" );
		 for(Status status : occupant.getStatuses()){
    		 showStatusConfig(status, "\t" + indent); // TODO, set status to active or sleeping. Need to be able to replace?
         }
	}
	
	/**
	 * Shows the configuration for an occupant.
	 * @param occupant the occupant to display.
	 * @param indent the indenting to be used. 
	 */
	private void showOccupantConfig(Occupant occupant, String indent){
		 System.out.println(indent + occupant.getName() + " (" + occupant.getType() +")" );
		 for(Status status : occupant.getStatuses()){
    		 showStatusConfig(status, "\t" + indent); // TODO, set status to active or sleeping. Need to be able to replace?
         }
	}
	
	/**
	 * Shows the configuration for a room.
	 * @param room the room to display.
	 * @param indent the indenting to be used.
	 */
	private void showRoomConfig(Room room, String indent){
		System.out.println(indent + room.getName() + " (Floor:" + room.getFloor() + ", Room Type:" + room.getType() + ")" );
	     
	     System.out.println(indent + "Sensors:");
	     for(Sensor sensor : room.getSensors()){
	    	 showSensorConfig(sensor, "\t" + indent);
	     }
	     
	     System.out.println(indent + "Appliances:");
	     for(Appliance appliance : room.getAppliances()){
	    	 showApplianceConfig(appliance, "\t" + indent);
	     
	     }
	}
	
	/**
	 * Shows the configuration for a sensor.
	 * @param sensor the sensor to display.
	 * @param indent the indenting to be used.
	 */
	private void showSensorConfig(Sensor sensor, String indent){
		System.out.println(indent + sensor.getName() + " (Sensor Type:" + sensor.getType() + ")" ); // TODO add the status values.
	
		 for(Status status : sensor.getStatuses()){
    		 showStatusConfig(status, "\t" + indent);
         }
	}
	
	/**
	 * Shows the configuration for a appliance.
	 * @param appliance the appliance to display.
	 * @param indent the indenting to be used.
	 */
	private void showApplianceConfig(Appliance appliance, String indent){
		System.out.println(indent + appliance.getName() + " (Appliance Type:" + appliance.getType() + ")" ); // TODO add the status values.
	     
    	 for(Status status : appliance.getStatuses()){
    		 showStatusConfig(status, "\t" + indent);
         }
	}
	
	/**
	 * Shows the configuration for a status.
	 * @param status the status to display.
	 * @param indent the indenting to be used.
	 */
	private void showStatusConfig(Status status, String indent){
		 System.out.println(indent + status.getName() + ":" + status.getValue() ); // TODO add the status values.
	} 
	
	
	/**
	 * Gets a set of houses that are in the system.
	 * @return a set of houses.
	 */
	@SuppressWarnings("unchecked")
	private Set<House> getHouses(){
		return (Set<House>) (Set<?>)knowledgeGraph.getObjectsFromQuery(new TripleLiteral("system", "contains", "?"));
	}
	
	/**
	 * Gets a set of occupants that are in the system.
	 * @return a set of occupants
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private Set<Occupant> getOccupants(){
		return (Set<Occupant>) (Set<?>) knowledgeGraph.getObjectsFromQuery(new TripleLiteral("system", "has_occupant", "?"));
	}

	/**
	 * Validates that the identifier is valid and does not contain illegal characters.
	 * @param identifier The identifier to validate.
	 * @throws InvalidCommandException on invalid identifiers.
	 */
	private void validateIdentifier(String identifier) throws InvalidCommandException {
		if(identifier == null || identifier.equals("?")){
			throw new InvalidCommandException(0, "Identifier must be set and not equal '?'.", identifier); 
		}
		if(identifier.contains(":")){
			throw new InvalidCommandException(0, "Identifier must not contain ':'.", identifier); 
		}
	}


	/**
	 * Validates the authorization token.
	 * @param authToken The authorization token.
	 * @throws AuthTokenException on invalid authorization tokens.
	 */
	@SuppressWarnings("unused")
	private void validateAuthToken(String authToken) throws AuthTokenException {
		if(true){
			return;
		}
		throw new AuthTokenException(0, "Invalid authorization token", authToken);
	}
}
