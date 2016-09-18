package cscie97.asn2.housemate.model;



public class Sensor {

	private String id;

	private String name;
	
	private String state;

	public Sensor(String id, String name, String state) {
		this.id = id;
		this.name = name;
		this.state = state;
	}



	public String getId() {
		return id;
	}


	protected void setId(String id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}


	public String getState() {
		return state;
	}


	protected void setState(String state) {
		this.state = state;
	}


	public String toString() {
		return "Sensor: [id:"+this.id+", name:"+this.name+", state:"+this.state+"]";
	}


	public static boolean validateSensor(Sensor sensor) {
		return (
				(sensor.getId() != null && sensor.getId().length() > 0) &&
				(sensor.getName() != null && sensor.getName().length() > 0) &&
				(sensor.getState() != null & sensor.getState().length() > 0)
		);
	}
}

