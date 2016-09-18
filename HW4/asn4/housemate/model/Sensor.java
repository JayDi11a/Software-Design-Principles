package cscie97.asn4.housemate.model;

/**
 * Sensors represent items like cameras and smoke detectors.
 *
 */
public class Sensor extends HouseMateNode {

	/**
	 * Constructs a sensor.
	 * @param identifierString the identifier of the Sensor.
	 * @param identifier the friendly name of the sensor
	 * @param type the type of sensor.
	 */
	public Sensor(String identifierString, String identifier, String type) {
		super(identifierString, identifier);
		this.type = type;
	}

}
