package cscie97.asn2.housemate.model;

/**
 * Represents an Appliance in the House Mate Model.Appliances are House items
 * but have the ability to determine the state and the controlling functionality
 * of the Appliance as well.
 * 
 * @author Gerald Trotman
 * @version 1
 *
 * @see HouseMateAPI
 * @see House
 * @see Sensor
 *
 */

public class Appliance {


	/**
	 * The unique identifier
	 */
	private String id;


	/**
	 * The name of the appliance
	 *
	 */
	private String name;


	/**
	 * The given state of an appliance once commanded
	 */
	private String state;


	/**
	 * The controlling functionality of the appliance
	 *
	 */
	private String control;



	/**
	 * The class constructor.
	 *
	 * @param id
	 * @param name
	 * @param state
	 * @param control
	 *
	 */
	public Appliance(String id, String name, String state, String control) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.control = control;
	}


	/**
	 * Returns the unique identifier for the specific appliance
	 *
	 * @return unique identifier for this device
	 */
	public String getId() {
		return id;
	}


	/**
	 * Sets the unique identifier for this specific appliance
	 * 
	 * @param id identifier for the appliance
	 */
	protected void setId(String id) {
		this.id = id;	
	}



	/**
	 *  Returns the name of the appliance
	 * 
	 * @return name of appliance
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name of the the appliance
	 *
	 * @param name the name of the device
	 */
	protected void setName(String name) {
		this.name = name;
	}


	public String getState() {
		return state;
	}

	
	protected void setState() {
		this.state = state;
	}


	public String getControl() {
		return control;
	}

	protected void setControl() {
		this.control = control;
	}

	public String toString() {
		return "Appliance: [id:"+this.id+", name:"+this.name+", state:"+this.state+", control:"+this.control+"]";
	}


	/**
	 * Checks that all required fields are set and that all appliances are valid
	 *
	 * @param appliance true if all properties are valid, false otherwise
	 *
	 */
	public static boolean validateAppliance(Appliance appliance) {
		return (
				(appliance.getId() != null && appliance.getId().length() > 0) &&
				(appliance.getName() != null && appliance.getName().length() > 0) &&
				(appliance.getState() != null && appliance.getState().length() > 0) &&
				(appliance.getControl() != null && appliance.getControl().length() >= 0)
		);
	}

}


