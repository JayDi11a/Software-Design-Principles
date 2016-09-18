package cscie97.asn4.housemate.test;


import cscie97.asn4.housemate.entitlement.*;
import cscie97.asn4.housemate.model.CommandImporter;
import cscie97.asn4.housemate.txt.*;
import cscie97.asn4.housemate.exception.*;

/**
 * Test harness for the CSCI-E 97 Assignment 4.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see Importer
 * @see IHouseMateModelServiceAPI
 */
public class TestDriver {

    /**
     * Executes the primary test logic.  Accepts two command line arguments that should be text files.  Command-line
     * arguments should be:
     * <ol> 
     * 		<li>filename of Entitlement    txt file</li>
     *		<li>filename of HouseMateModel txt file</li>
     *</ol>
     * @param args  first argument should be a text file of Entitlement data and the second argument should be a text
     *              file of the HouseMateModel setup.         
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                IEntitlementServiceAPI entitlementAPI = EntitlementServiceAPI.getInstance();

                //////// exercise the EntitlementService API ////////

                // login as the "root user" to process the Entitlement txt file
                AccessToken superToken = entitlementAPI.login("gtrotman", "itsasecret");

                EntitlementImporter.importEntitlementFile(superToken.getId(), args[0]);

                // logout as the "root user" to process the Entitlement txt file
                entitlementAPI.logout(superToken.getId());

                //////// exercise the HouseMateModelAPI ////////

                // login as one of the HouseMateModel Admins to process the  txt file.
                AccessToken houseMateModelAdminToken = entitlementAPI.login("sam","itsasecret");

		// This was where I needed to go back and retweak the HouseMateModelService
		// but I was unsuccessful in doing so. 
                /*CommandImporter.importFile(houseMateModelAdminToken.getId(), args[1]);*/


                // logout as the product admin user
                entitlementAPI.logout(houseMateModelAdminToken.getId());




                ////////////////////////////////////////////////////////////////////////////////////////////////////////

            }
            // if we catch an AccessDeniedException, the login information for the super user was incorrect OR the
            // super user was not actually loaded.  Verify that the username/password combo passed in this TestDriver
            // matches up to the correct super user defined at the EntitlementServiceAPI initialization.
            catch (AccessDeniedException ade) {
                System.out.println(ade.getMessage());
                System.exit(1);
            }
            // if we catch a ParseException, the entire program execution should
            // fail and the errors in the original files fixed before the program can be executed again
            catch (ParseException pe) {
                System.out.println(pe.getMessage());
                System.exit(1);
            }
            // if we catch an ImportException, the program should fail and exit
            catch (ImportException ie) {
                System.out.println(ie.getMessage());
                System.exit(1);
            }
        }
        else {
            System.out.println("Arguments to TestDriver should import Entitlement text file");
            System.exit(1);
        }
    }
}
