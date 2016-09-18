/**
 * 
 */
package cscie97.asn3.housemate.model;

import java.util.Map;
import cscie97.asn3.housemate.model.exception.HouseMateModelException;

/**
 * Interface for the HouseMateModelService
 *
 */
public interface HouseMateModelServiceInterface {

	/**
	 * Creates a house
	 * @param authToken Authorization token.
	 * @param identifier Unique Identifier for the house.
	 * @throws HouseMateModelException on invalid identifier.
	 */
	public void createHouse(String authToken, String identifier) throws HouseMateModelException;

	/**
	 * Creates a room in a house.
	 * @param authToken Authorization token.
	 * @param identifier The identifier of the room. Must be unique for the house.
	 * @param floor The floor of the room.
	 * @param roomType The type of room.
	 * @param houseIdentifier The identifier of the house.
	 * @throws HouseMateModelException when there is an error creating the house.
	 */
	public void createRoom(String authToken, String identifier, int floor, String roomType, String houseIdentifier)  throws HouseMateModelException;

	/**
	 * Creates an occupant.
	 * @param authToken Authorization token.
	 * @param identifier The unique id of the occupant.
	 * @param type The type of occupant. For example, adult, child, animal, etc.
	 * @throws HouseMateModelException when there is an error creating the room.
	 */
	public void createOccupant(String authToken, String identifier, String type)  throws HouseMateModelException;

	/**
	 * Adds an occupant to a house and sets the occupants relation to the house.
	 * @param authToken Authorization token.
	 * @param occupantIdentifier The identifier of the occupant.
	 * @param houseIdentifier The identifier of the house.
	 * @param relation The relation of the occupant to the house. For example, resident, guest, burglar.
	 * @throws HouseMateModelException when there is an error creating the occupant.
	 */
	public void addOccupant(String authToken, String occupantIdentifier, String houseIdentifier, String relation)  throws HouseMateModelException;

	/**
	 * Creates a sensor.
	 * @param authToken Authorization token.
	 * @param identifier The identifier for the sensor. Must be unique for the room.
	 * @param type The type of sensor. For example, smokedetector, camera etc.
	 * @param roomIdentifier identifier of the room.
	 * @throws HouseMateModelException when there is an error adding the occupant.
	 */
	public void createSensor(String authToken, String identifier, String type, String roomIdentifier)  throws HouseMateModelException;

	/**
	 * Creates an appliance.
	 * @param authToken Authorization token.
	 * @param identifier The identifier for the appliance. Must be unique for the room.
	 * @param type The type of appliance. For example, TV, Pandora, door etc.
	 * @param roomIdentifier The identifier of the room where the appliance is.
	 * @throws HouseMateModelException when there is an error creating the appliance.
	 */
	public void createAppliance(String authToken, String identifier, String type, String roomIdentifier)  throws HouseMateModelException;

	/**
	 * Sets the value for a given status
	 * @param authToken Authorization token.
	 * @param identifier The identifier of the sensor or appliance.
	 * @param statusName The name of the status to set the value for.
	 * @param statusValue The value of the status.
	 * @throws HouseMateModelException when there is an error setting the status.
	 */
	public void setStatus(String authToken, String identifier, String statusName, String statusValue)  throws HouseMateModelException;

	/**
	 * Shows the configuration for a given identifier. If identifier is null, then the entire configuration is displayed.
	 * @param authToken Authorization token.
	 * @param identifier The identifier of the 
	 * @throws HouseMateModelException on error showing configuration
	 */
	public void showConfiguration(String authToken, String identifier)  throws HouseMateModelException;
	
	/**
	 * Sets the validation for status options.
	 * @param authToken Authorization token.
	 * @param identifier The id of the status to be configured. The id is the appliance identifier:status name.
	 * @param params A map of key-value pairs to configure the options. Params.type = enum|range. 
	 * Enum options include String:params.values=pipe delimited values. Range options include int:params.min, int:params.max,
	 * @throws HouseMateModelException when the option is malformed.
	 */
	public void setStatusOptions(String authToken, String identifier, Map<String, String> params) throws HouseMateModelException;
	
	/**
	 * Executes a raw query against the knowledge graph.
	 * @param authToken Authorization token.
	 * @param query The query to execute against the knowledge graph.
	 * @throws HouseMateModelException on invalid identifier.
	 */
	public void query(String authToken, String query) throws HouseMateModelException;
}
