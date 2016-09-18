package cscie97.asn3.housemate.test;

import cscie97.asn3.housemate.model.*;
import cscie97.asn3.housemate.model.exception.*;
import cscie97.asn3.knowledge.engine.*;


public class TestDriver {


	public static void main(String[] args) throws Exception {
		
		if(args.length != 1){
			System.out.println("Error: Please provide an input filename.");
			return;
		}
		
		CommandImporter importer = new CommandImporter();
		try {
			importer.importFile(args[0]);
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			return;
		}
	}

}
