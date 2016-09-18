package cscie97.asn3.housemate.test;

import cscie97.asn3.housemate.model.*;
import cscie97.asn3.housemate.model.exception.*;
import cscie97.asn3.knowledge.engine.*;


public class ExtraTestDriver {


	public static void main(String[] args) throws Exception {
		
		System.out.println("\n\nRUNNING EXTRA TESTS:");
		try{
			badFileName();
			badCommand();
			missingStatus();
			reservedWord();
			invalidFirstCommand();
			invalidName();
			addToNonExistingIdentity();
			duplicateDefines();
		}catch(Exception e){
			System.out.println("TESTS FAILED: " + e.getMessage());
			return;
		}
		System.out.println("\nTESTS ALL PASSED!");
	}
	
	private static void throwError() throws Exception{
		throw new Exception(Thread.currentThread().getStackTrace()[2].getMethodName());
	}

	private static void badFileName() throws Exception {
		CommandImporter importer = new CommandImporter();
		try {
			importer.importFile("BADFILE");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			return;
		}
		throwError();
	}
	
	private static void badCommand() throws Exception {
		CommandImporter importer = new CommandImporter();
		try {
			importer.executeCommand("set notappliance house1:kitchen1:oven1 status temperature value 350");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			return;
		}
		throwError();
	}
	
	private static void missingStatus() throws Exception {
		CommandImporter importer = new CommandImporter();
		try {
			importer.executeCommand("set  appliance house1:kitchen1:oven1 nostatus temperature value 350");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			return;
		}
		throwError();
	}
	
	private static void reservedWord() throws Exception {
		CommandImporter importer = new CommandImporter();
		try {
			importer.executeCommand("define house ?");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			return;
		}
		throwError();
	}
	
	private static void invalidFirstCommand() throws Exception {
		CommandImporter importer = new CommandImporter();
		try {
			importer.executeCommand("invaliddefine house house1");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			return;
		}
		throwError();
	}
	
	private static void invalidName() throws Exception {
		CommandImporter importer = new CommandImporter();
		boolean passed = false;
		try {
			importer.executeCommand("define house house:1");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			passed = true;
		}
		
		if(!passed){ throwError(); } else{ passed = false; }
		
		try {
			importer.executeCommand("define occupant joe:smith type adult");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			passed = true;
		}
		
		if(!passed){ throwError(); } else{ passed = false; }
		
		try {
			importer.executeCommand("define room room:1 floor 1 type kitchen house house1");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			passed = true;
		}
		
		if(!passed){ throwError(); } else{ passed = false; }
		
		try {
			importer.executeCommand("define appliance oven:1 type oven room house1:kitchen1");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			passed = true;
		}
		
		if(!passed){ throwError(); } else{ passed = false; }
		
		try {
			importer.executeCommand("define sensor smoke:1 type oven room house1:kitchen1");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			passed = true;
		}
		
		if(!passed){ throwError(); } else{ passed = false; }
		
		try {
			importer.executeCommand("define house house2");
			importer.executeCommand("define room kitchen1 floor 1 type kitchen house house2");
			importer.executeCommand("define appliance oven1 type oven room house2:kitchen1");
			importer.executeCommand("set  appliance house2:kitchen1:oven1 status mode:power value ON"); // mode:power is invalid
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			passed = true;
		}
		
		if(!passed){ throwError(); }
		return;
	}
	
	
	private static void addToNonExistingIdentity() throws Exception {
		CommandImporter importer = new CommandImporter();
		boolean passed = false;
		try {
			importer.executeCommand("define room kitchen1 floor 1 type kitchen house houseThatDoesNotExist");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			passed = true;
		}
		
		if(!passed){ throwError(); }
		
		try {
			importer.executeCommand("define appliance oven1 type oven room house1:kitchen1:doesNotExist");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			passed = true;
		}
		
		if(!passed){ throwError(); }
		
		try {
			importer.executeCommand("define sensor smoke type smoke room house1:kitchen1:doesNotExist");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			passed = true;
		}
		
		if(!passed){ throwError(); }
		
		try {
			importer.executeCommand("set  appliance house1:kitchen1:oven1:doesNotExist status power value ON");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());
			passed = true;
		}
		
		if(!passed){ throwError(); }
		
		return;
	}
	
	
	private static void duplicateDefines() throws Exception {
		CommandImporter importer = new CommandImporter();
		try {
			importer.executeCommand("define house house4");
			importer.executeCommand("define house house4");
		} catch (HouseMateModelException e) {
			System.out.println(e.toString());

			try {
				importer.executeCommand("define room kitchen1 floor 1 type kitchen house house4");
				importer.executeCommand("define room kitchen1 floor 1 type kitchen house house4");
			} catch (HouseMateModelException e2) {
				System.out.println(e2.toString());
	
				
				try {
					importer.executeCommand("define occupant joe_smith type adult");
					importer.executeCommand("define occupant joe_smith type adult");
				} catch (HouseMateModelException e3) {
					System.out.println(e3.toString());
		
					
					try {
						importer.executeCommand("define sensor smoke_detector1 type smoke_detector room house4:kitchen1");
						importer.executeCommand("define sensor smoke_detector1 type smoke_detector room house4:kitchen1");
					} catch (HouseMateModelException e4) {
						System.out.println(e4.toString());
						
						try {
							importer.executeCommand("define appliance oven1 type oven room house4:kitchen1");
							importer.executeCommand("define appliance oven1 type oven room house4:kitchen1");
						} catch (HouseMateModelException e5) {
							System.out.println(e5.toString());
							return;
						}
					}
				}
			}
		}
		
		throwError();
	}
	
	
}
