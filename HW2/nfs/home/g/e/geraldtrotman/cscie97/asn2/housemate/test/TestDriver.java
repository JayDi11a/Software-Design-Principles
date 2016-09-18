package cscie97.asn2.test;


import cscie97.asn2.housemate.model.exception.ImportException;
import cscie97.asn2.housemate.model.exception.QueryEngineException;
import cscie97.asn2.housemate.model.exception.ParseException;
import cscie97.asn2.housemate.model.Importer;
import cscie97.asn2.housemate.model.IHouseMateAPI;
import cscie97.asn2.housemate.model.House;


/**
 * Testing for the CSCI E-97 Assignment 2. It reads input file which takes those commands and stores them
 * into the House Mate controller
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see Importer
 * @see IHouseMateAPI
 * @see House
 */



public class TestDriver {

/**
 * Execute the primary test logic accepting a .text file command line argument
 * calls various methods on the Importer class to load the .text file of commands
 * to then import and be held by the HouseMateAPI.
 *
 *
 */

	public static void main(String[] args) {
		if (args.length == 1) {
			try {

				String myGUID = "This better work!";


				Importer.importHouseSetup(myGUID, args[0]);


			}

			catch (ParseException pe) {
				System.out.println(pe.getMessage());
				System.exit(1);
			}


			catch (ImportException ie) {
				System.out.println(ie.getMessage());
				System.exit(1);
			}
		
			catch (QueryEngineException qee) {
				System.out.println(qee.getMessage());
				System.exit(1);
			}
		} else {
			System.out.println("Arguments to TestDriver should be import HouseSetup.txt");
			System.exit();
		}
	}
}
						
