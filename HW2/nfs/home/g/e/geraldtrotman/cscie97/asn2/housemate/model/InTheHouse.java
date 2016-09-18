package cscie97.asn2.housemate.model;











public enum InTheHouse {




	ROOM ("Room", "Rooms of the House"),




	OCCUPANT ("Occupant", "Occupant of the House");




	private String description;



	private String display;



	public String getDescription() {
		return this.description;
	}


	public String display() {
		return this.display;
	}



	InTheHouse(String display, String description) {
		this.display = display;
		this.description = description;
	}
}
