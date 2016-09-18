package cscie97.asn4.housemate.model;

/**
 * Appliances represent devices that have sensors and can be controlled.
 * Some examples include TVs, thermostats, Pandora, doors, windows, etc.
 * The control of appliances is setup when the Appliance is imported
 * into the system. For example, a TV's channels can be configured to be 
 * between 1-100.
 */

public class Appliance extends HouseMateNode {
	
	/**
	 * Constructs an appliance.
	 * @param identifierString The unique identifier for the appliance. 
	 * @param friendlyName The user-friendly name of the device.
	 * @param type The type of device, such as TV, Door, etc.
	 */
	public Appliance(String identifierString, String friendlyName, String type) {
		super(identifierString, friendlyName);
		this.type = type;
	}
}
