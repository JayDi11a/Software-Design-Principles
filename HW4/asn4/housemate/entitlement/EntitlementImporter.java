package cscie97.asn4.housemate.entitlement;

import cscie97.asn4.housemate.model.CommandImporter;
import cscie97.asn4.housemate.txt.Importer;
import cscie97.asn4.housemate.exception.*;
//import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Provides a single public method for handling the creation of new Entitlement Service items, which include:
 * <ul>
 *     <li>{@link cscie97.asn4.housemate.entitlement.User}</li>
 *     <li>{@link cscie97.asn4.housemate.entitlement.Role}</li>
 *     <li>{@link cscie97.asn4.housemate.entitlement.Permission}</li>
 *     <li>{@link cscie97.asn4.housemate.entitlement.Service}</li>
 * </ul>
 * Requires a text file be passed that contains the definitions of all items.  Also allows for defining the Permissions
 * that comprise a Role, adding child Roles to Roles, adding Permissions to Resources, and adding Credentials to Users
 * and granting Roles to Users.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI
 * @see cscie97.asn4.housemate.entitlement.User
 * @see cscie97.asn4.housemate.entitlement.Role
 * @see cscie97.asn4.housemate.entitlement.Permission
 * @see cscie97.asn4.housemate.entitlement.Service
 *
 */
public class EntitlementImporter extends Importer {

    /**
     * Public method for importing Entitlement items into the Entitlement Services, including Resources,
     * Roles, Permissions, and Users, and setting all appropriate attributes on those objects.
     *
     * @param tokenID                    access token for carrying out restricted interface actions such as this
     * @param filename                file with authentication items to load into the authentication catalog
     * @throws ImportException        thrown when encountering non-parse related exceptions in the import process
     * @throws ParseException         thrown when encountering any issues parsing the input file related to the format of the file contents
     * @throws AccessDeniedException  thrown when encountering any permission-related issues calling the restricted methods of the IEntitlementServiceAPI
     */
    public static void importEntitlementFile(String tokenID, String filename)
            throws ImportException, ParseException, AccessDeniedException {

        IEntitlementServiceAPI entitlementAPI = EntitlementServiceAPI.getInstance();

        // in order to run the import of the entitlement text file, the User who owns the passed GUID AccessToken
        // must have ALL of the permissions on the Entitlement Service API, which include:
        if (entitlementAPI.canAccess(tokenID, PermissionType.DEFINE_PERMISSION) &&
                entitlementAPI.canAccess(tokenID, PermissionType.DEFINE_ROLE) &&
                entitlementAPI.canAccess(tokenID, PermissionType.ADD_ENTITLEMENT_TO_ROLE) &&
                entitlementAPI.canAccess(tokenID, PermissionType.CREATE_USER) &&
                entitlementAPI.canAccess(tokenID, PermissionType.ADD_USER_CREDENTIAL) &&
                entitlementAPI.canAccess(tokenID, PermissionType.ADD_ENTITLEMENT_TO_USER)
        ) {
            int lineNumber = 0;  // keep track of what lineNumber we're reading in from the input file for exception handling
            String line;  // store the text on each line as it's processed

            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));

                while ((line = reader.readLine()) != null) {
                    lineNumber++;

                    // FIRST check if we encountered an empty line, and just skip to the next one if so
                    if (line.length() == 0) { continue; }

                    // SECOND check if the line contains column headers, since some lines may contain comments
                    // (preceeded by hash character); if first character is a hash, skip to next line
                    if (line.substring(0,1).matches("#")) { continue; }

                   String[] cleanedColumns = Importer.parseTXTLine(line, ",");

                    // depending on both the size of cleanedColumns as well as the first item in the array,
                    // call the appropriate method to handle the command
                    if (cleanedColumns != null ) {
                        try {
                            // define permission
                            if (cleanedColumns.length == 5 && cleanedColumns[0].equalsIgnoreCase("define_permission")) {
                                EntitlementImporter.definePermission(tokenID, cleanedColumns);
                            }
                            // define role
                            if (cleanedColumns.length == 4 && cleanedColumns[0].equalsIgnoreCase("define_role")) {
                                EntitlementImporter.defineRole(tokenID, cleanedColumns);
                            }
                            // add entitlement to role
                            if (cleanedColumns.length == 3 && cleanedColumns[0].equalsIgnoreCase("add_entitlement_to_role")) {
                                EntitlementImporter.addEntitlementToRole(tokenID, cleanedColumns);
                            }
                            // create user
                            if (cleanedColumns.length == 3 && cleanedColumns[0].equalsIgnoreCase("create_user")) {
                                EntitlementImporter.createUser(tokenID, cleanedColumns);
                            }
                            // add credential
                            if (cleanedColumns.length == 4 && cleanedColumns[0].equalsIgnoreCase("add_credential")) {
                                EntitlementImporter.addCredentialToUser(tokenID, cleanedColumns);
                            }
                            // add entitlement to user
                            if (cleanedColumns.length == 3 && cleanedColumns[0].equalsIgnoreCase("add_entitlement_to_user")) {
                                EntitlementImporter.addEntitlementToUser(tokenID, cleanedColumns);
                            }
                        }
                        catch (ParseException pe) {
                            throw new ParseException(pe.getMessage(), line, lineNumber, filename, pe);
                        }
                    }
                    else {
                        throw new ParseException("Import Authentication line contains invalid data for the authentication data row.",
                                line,
                                lineNumber,
                                filename,
                                null);
                    }
                }

                // lastly, print out an inventory of all the items in the Entitlement Service API
                System.out.println(String.format("\n******************************\n"));
                System.out.println(entitlementAPI.getInventory());
                System.out.println(String.format("\n******************************\n"));
            }
            catch (FileNotFoundException fnfe) {
                throw new ImportException("Could not find file ["+filename+"] to open for reading", lineNumber, filename, fnfe);
            }
            catch (IOException ioe) {
                throw new ImportException("Encountered an IOException when trying to open ["+filename+"] for reading", lineNumber, filename, ioe);
            }
            catch (Exception e) {
                throw new ImportException("Caught a generic Exception when attempting to read file ["+filename+"]", lineNumber, filename, e);
            }

        }
        // NOT ALLOWED!
        else throw new AccessDeniedException(tokenID, "", 0, "", null);
    }

    /**
     * Creates permissions and adds them to the {@link cscie97.asn4.housemate.entitlement.EntitlementServiceAPI}.
     * The format of each element in entitlementData should be:
     * <ol>
     *     <li><b>define_permission</b></li>
     *     <li>resource ID</li>
     *     <li>permission id</li>
     *     <li>permission name</li>
     *     <li>resource description</li>
     * </ol>
     *
     * @param tokenID                   access token for carrying out restricted interface actions such as this
     * @param entitlementData     string array of lines (each line is part of a .txt file) to be parsed and loaded as a new Entitlement Permission
     * @throws cscie97.asn4.housemate.exception.ParseException    if an error occurred parsing the entitlementData
     */
    private static void definePermission(String tokenID, String[] entitlementData) throws ParseException {
        // ensure that we have at least 5 elements passed and that the first element is "define_permission"
       /* if (entitlementData == null ||
                entitlementData.length != 5 ||
                !entitlementData[0].trim().equalsIgnoreCase(PermissionType.DEFINE_PERMISSION.getPermissionName())
        ) {
            throw new ParseException("Import Entitlement line contains invalid data when calling definePermission(): "+ StringUtils.join(entitlementData, ","),
                    null,
                    0,
                    null,
                    null);
        }*/

        Permission permission = new Permission();
        permission.setID(entitlementData[2].trim());
        permission.setName(entitlementData[3].trim());
        permission.setDescription(entitlementData[4].trim());

        String resourceID = entitlementData[1].trim();

        IEntitlementServiceAPI entitlementAPI = EntitlementServiceAPI.getInstance();
        entitlementAPI.addPermissionToResource(tokenID, resourceID, permission);

        System.out.println(String.format("Adding Permission on ResourceID [%s] to EntitlementService services: [%s]\n", resourceID, permission));
    }

    /**
     * Creates roles and adds them to the {@link cscie97.asn4.housemate.entitlement.EntitlementServiceAPI}.
     * The format of each element in entitlementData should be:
     * <ol>
     *     <li><b>define_role</b></li>
     *     <li>role id</li>
     *     <li>role name</li>
     *     <li>role description</li>
     * </ol>
     *
     * @param tokenID                   access token for carrying out restricted interface actions such as this
     * @param entitlementData     string array of lines (each line is part of a .txt file) to be parsed and loaded as a new Entitlement Permission
     * @throws cscie97.asn4.housemate.exception.ParseException    if an error occurred parsing the entitlementData
     */
    private static void defineRole(String tokenID, String[] entitlementData) throws ParseException {
        // ensure that we have exactly 4 elements passed and that the first element is "define_role"
       /* if (entitlementData == null ||
                entitlementData.length != 4 ||
                !entitlementData[0].trim().equalsIgnoreCase(PermissionType.DEFINE_ROLE.getPermissionName())
        ) {
            throw new ParseException("Import Entitlement line contains invalid data when calling defineRole(): "+ StringUtils.join(entitlementData, ","),
                    null,
                    0,
                    null,
                    null);
        }*/

        Role role = new Role();
        role.setID(entitlementData[1].trim());
        role.setName(entitlementData[2].trim());
        role.setDescription(entitlementData[3].trim());

        IEntitlementServiceAPI entitlementAPI = EntitlementServiceAPI.getInstance();
        entitlementAPI.addRole(tokenID, role);

        System.out.println(String.format("Adding Role to EntitlementService services: [%s]\n", role));
    }

    /**
     * Creates registered users and adds them to the {@link cscie97.asn4.housemate.entitlement.EntitlementServiceAPI}.
     * The format of each element in entitlementData should be:
     * <ol>
     *     <li><b>create_user</b></li>
     *     <li>user id</li>
     *     <li>user name</li>
     * </ol>
     *
     * @param tokenID                   access token for carrying out restricted interface actions such as this
     * @param entitlementData     string array of lines (each line is part of a .txt file) to be parsed and loaded as a new Entitlement Service registered User
     * @throws cscie97.asn4.housemate.exception.ParseException    if an error occurred parsing the entitlementData
     */
    private static void createUser(String tokenID, String[] entitlementData) throws ParseException {
        // ensure that we have exactly 3 elements passed and that the first element is "create_user"
        /*if (entitlementData == null ||
                entitlementData.length != 3 ||
                !entitlementData[0].trim().equalsIgnoreCase(PermissionType.CREATE_USER.getPermissionName())
        ) {
            throw new ParseException("Import Entitlement line contains invalid data when calling createUser(): "+ StringUtils.join(entitlementData, ","),
                    null,
                    0,
                    null,
                    null);
        }*/

        User user = new User(entitlementData[1].trim(),entitlementData[2].trim());

        IEntitlementServiceAPI entitlementAPI = EntitlementServiceAPI.getInstance();
        entitlementAPI.addUser(tokenID, user);

        System.out.println(String.format("Adding User to EntitlementService services: [%s]\n", user));
    }

    /**
     * Adds Entitlements (which may be actually either Roles or Permissions) to Roles.  The format of each element
     * in entitlementData should be:
     * <ol>
     *     <li><b>add_entitlement_to_role</b></li>
     *     <li>role id</li>
     *     <li>entitlement id</li>
     * </ol>
     *
     * @param tokenID                   access token for carrying out restricted interface actions such as this
     * @param entitlementData     string array of lines (each line is part of a .txt file) to be parsed and loaded as a new Entitlement Service registered User
     * @throws cscie97.asn4.housemate.exception.ParseException    if an error occurred parsing the entitlementData
     */
    private static void addEntitlementToRole(String tokenID, String[] entitlementData) throws ParseException {
        // ensure that we have exactly 3 elements passed and that the first element is "add_entitlement_to_role"
        /*if (entitlementData == null ||
                entitlementData.length != 3 ||
                !entitlementData[0].trim().equalsIgnoreCase(PermissionType.ADD_ENTITLEMENT_TO_ROLE.getPermissionName())
        ) {
            throw new ParseException("Import Entitlement line contains invalid data when calling addEntitlementToRole(): "+ StringUtils.join(entitlementData, ","),
                    null,
                    0,
                    null,
                    null);
        }*/

        String roleID = entitlementData[1].trim();
        String entitlementID = entitlementData[2].trim();

        IEntitlementServiceAPI entitlementAPI = EntitlementServiceAPI.getInstance();
        entitlementAPI.addPermissionToRole(tokenID, roleID, entitlementID);

        System.out.println(String.format("Adding Entitlement ID [%s] to Role ID [%s]\n", roleID, entitlementID));
    }

    /**
     * Adds Credentials to users, which are comprised of a username and password (hashed).  Users can have multiple
     * sets of Credentials, which will allow them to login to the EntitlementServiceAPI with different usernames
     * and password.
     *
     * The format of each element in entitlementData should be:
     * <ol>
     *     <li><b>add_user_credential</b></li>
     *     <li>user id</li>
     *     <li>username</li>
     *     <li>password</li>
     * </ol>
     *
     * @param tokenID                   access token for carrying out restricted interface actions such as this
     * @param entitlementData     string array of lines (each line is part of a .txt file) to be parsed and loaded as a new Entitlement Service registered User
     * @throws cscie97.asn4.housemate.exception.ParseException    if an error occurred parsing the entitlementData
     */
    private static void addCredentialToUser(String tokenID, String[] entitlementData) throws ParseException {
        // ensure that we have exactly 4 elements passed and that the first element is "add_user_credential"
        /*if (entitlementData == null ||
            entitlementData.length != 4 ||
            !entitlementData[0].trim().equalsIgnoreCase(PermissionType.ADD_USER_CREDENTIAL.getPermissionName())
        ) {
            throw new ParseException("Import Entitlement line contains invalid data when calling addCredential(): "+ StringUtils.join(entitlementData, ","),
                    null,
                    0,
                    null,
                    null);
        }*/

        String userID = entitlementData[1].trim();
        String username = entitlementData[2].trim();
        String password = entitlementData[3].trim();

        IEntitlementServiceAPI entitlementAPI = EntitlementServiceAPI.getInstance();
        entitlementAPI.addCredentialToUser(tokenID, userID, username, password);

        System.out.println(String.format("Adding Credentials username [%s] password [%s] to User ID [%s]\n", username, password, userID));
    }

    /**
     * Adds Entitlements to users, which can be either a Permission or Role.
     *
     * The format of each element in entitlementData should be:
     * <ol>
     *     <li><b>add_entitlement_to_user</b></li>
     *     <li>user id</li>
     *     <li>entitlement id</li>
     * </ol>
     *
     * @param tokenID                   access token for carrying out restricted interface actions such as this
     * @param entitlementData     string array of lines (each line is part of a .txt file) to be parsed and loaded as a new Entitlement Service registered User
     * @throws cscie97.asn4.housemate.exception.ParseException    if an error occurred parsing the entitlementData
     */
    private static void addEntitlementToUser(String tokenID, String[] entitlementData) throws ParseException {
        // ensure that we have exactly 4 elements passed and that the first element is "add_entitlement_to_user"
        /*if (entitlementData == null ||
            entitlementData.length != 3 ||
            !entitlementData[0].trim().equalsIgnoreCase(PermissionType.ADD_ENTITLEMENT_TO_USER.getPermissionName())
        ) {
            throw new ParseException("Import Entitlement line contains invalid data when calling addEntitlementToUser(): "+ StringUtils.join(entitlementData, ","),
                    null,
                    0,
                    null,
                    null);
        }*/

        String userID = entitlementData[1].trim();
        String entitlementID  = entitlementData[2].trim();

        IEntitlementServiceAPI entitlementAPI = EntitlementServiceAPI.getInstance();
        entitlementAPI.addEntitlementToUser(tokenID, userID, entitlementID);

        System.out.println(String.format("Adding Entitlement ID [%s] to User ID [%s]\n", entitlementID, userID));
    }

}
