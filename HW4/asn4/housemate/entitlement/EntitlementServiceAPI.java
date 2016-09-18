package cscie97.asn4.housemate.entitlement;

import java.util.Set;
import java.util.HashSet;
import java.util.UUID;
import java.util.Arrays;
import java.util.Date;

/**
 * Concrete implementation class of the EntitlementServiceAPI.  Administrators may use the methods here to create and
 * administer new Roles, Permissions, Resources, and Users, and also define the properties on each.  Also contains
 * methods for logging in and logging out, which enable registered users to call restricted interface methods on the
 * {@link cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI},
 * {@link cscie97.asn4.housemate.model.IHouseMateModelServiceAPI}, and
 * {@link cscie97.asn4.housemate.controller.IHouseMateConrollerServiceAPI} classes.
 *
 * @author Gerald Trotman
 * @version 1.0
 * 
 */
public class EntitlementServiceAPI implements IEntitlementServiceAPI {

    private static final String ROOT_USERNAME = "gtrotman";

    private static final String ROOT_PASSWORD = "itsasecret";

    private User rootUser;

    /**
     * The unique top-level Entitlements contained in Entitlement Services; each Entitlement may only be
     * declared at the top-level once, but may be nested arbitrarily deeply in other Entitlements.
     */
    private Set<Entitlement> entitlements = new HashSet<Entitlement>();

    /**
     * The unique Resources contained in the Entitlement services.
     */
    private Set<Resource> resources = new HashSet<Resource>();

    /**
     * The unique registered Users contained in the Authentication catalog.
     */
    private Set<User> users = new HashSet<User>();

    /**
     * Singleton instance of the EntitlementServiceAPI
     */
    private static IEntitlementServiceAPI instance = null;

    /**
     * Class constructor.  Declares the top-level entitlements to initially be an empty HashSet.  Also creates the
     * initial "root user" that is used to load the Entitlement services.
     */
    private EntitlementServiceAPI() {
        this.entitlements = new HashSet<Entitlement>() { };

        // create an initial "Root User" that may be used to initially load the entitlement text  file
        createRootUser();
    }

    /**
     * Instantiates the EntitlementServiceAPI with a "root user" that can be used to initially load the
     * entitlement services via the {@link cscie97.asn4.housemate.entitlement.EntitlementImporter}. This user
     * will have all the Permissions of the EntitlementServiceAPI, and so will be able to call all the methods
     * defined in {@link cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI}.
     */
    private void createRootUser() {
        if (this.rootUser == null) {
            // create a hard-coded special "Root User" so that we can use that root user to import the Entitlement data
            this.rootUser = new User(UUID.randomUUID().toString(), ROOT_USERNAME, "Entitlement Root User");
            rootUser.addCredential( new Credentials(ROOT_USERNAME, ROOT_PASSWORD) );
            rootUser.setAccessToken( new AccessToken(rootUser.getID()) );

            // define primary Entitlement Resource
            String entitlementResourceID = "entitlement_service";
            String entitlementResourceName = "Entitlement Service";
            String entitlementResourceDescription = "Manage Authentication Configuration and Control Access to Restricted Service Interfaces";
            Resource entitlementResource = new Resource(entitlementResourceID, entitlementResourceName, entitlementResourceDescription);

            Permission p1  = new Permission(PermissionType.DEFINE_PERMISSION,"Define Permission Permission","Permission to create a new permission");
         
	    Permission p2  = new Permission(PermissionType.DEFINE_ROLE,"Define Role Permission","Permission to create a new role");
            Permission p3  = new Permission(PermissionType.ADD_ENTITLEMENT_TO_ROLE,"Add entitlement to role permission","Permission to add an entitlement to a role");
          
	    Permission p4  = new Permission(PermissionType.CREATE_USER,"Create User Permission","Permission to create a user");

	    Permission p5  = new Permission(PermissionType.ADD_USER_CREDENTIAL,"Add Credential to User Permission","Permission to add credentials to a user");
           
	    Permission p6 = new Permission(PermissionType.ADD_ENTITLEMENT_TO_USER,"Add Role to User Permission", "Permission to add a role to a user");

            Permission p7  = new Permission(PermissionType.CREATE_RESOURCE_ROLE,"Create Resource Role Permission","Permission to create a new resource role");

            Permission p8  = new Permission(PermissionType.ADD_RESOURCE_ROLE_TO_USER,"Add Resource Role to User Permission","Permission to add a resource role to a user");

            // define all Entitlement Service Permissions
            Set<Entitlement> allEntitlementPermissionsSet = new HashSet<Entitlement>(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8));
            Set<Permission> allEntitlementPermissionsList = new HashSet<Permission>(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8));

            // define primary Entitlement ROOT USER Role, with all appropriate Permissions
            String entitlementResourceRootRoleID = "authentication_admin_role";
            String entitlementResourceRootRoleName = "Authentication Admin";
            String entitlementResourceRootRoleDescription = "All permissions required by Authentication Administrator";
            Role entitlementRole = new Role(entitlementResourceRootRoleID, entitlementResourceRootRoleName, entitlementResourceRootRoleDescription);
            entitlementRole.addChildren( allEntitlementPermissionsSet );

            entitlementResource.addPermissions( allEntitlementPermissionsList );

            // because the services are defined as a Set, we can add this without fear of it being a duplicate
            this.resources.add(entitlementResource);

            this.entitlements.addAll( allEntitlementPermissionsSet );
            this.entitlements.add( entitlementRole );

            rootUser.addEntitlement(entitlementRole);

            this.users.add(rootUser);
        }
    }

    /**
     * Returns a reference to the single static instance of the EntitlementServiceAPI.  Will also create a
     * "root user" account that can be used for loading all the Entitlement service items.
     *
     * @return  singleton instance of EntitlementServiceAPI
     */
    public static synchronized IEntitlementServiceAPI getInstance() {
        if (instance == null) {
            instance = new EntitlementServiceAPI();
        }
        return instance;
    }

    /**
     * Adds a new Resource definition. Resources can contain child permissions, but primarily serve as a
     * marker class to group permissions into sets.
     *
     * @param tokenID  id of the AccessToken to use for entitlement to execute this method
     * @param resource  the Resource object to add to the Entitlement service
     */
    @Override
    public void addResource(String tokenID, Resource resource) {
        if (this.canAccess(tokenID, "define_resource")) {
            this.resources.add(resource);
        }
    }

    /**
     * Adds a new registered User.
     *
     * @param tokenID  id of the AccessToken to use for entitlement to execute this method
     * @param user     the User object to add to the Entitlement service
     */
    @Override
    public void addUser(String tokenID, User user) {
        if (this.canAccess(tokenID, PermissionType.CREATE_USER)) {
            this.users.add(user);
        }
    }

    /**
     * Adds a new Role.  Roles may contain other Roles or Permissions to define logical groupings that
     * correspond to types of Users that may use the EntitlementServiceAPI.
     *
     * @param tokenID  id of the AccessToken to use for entitlement to execute this method
     * @param role     the Role object to add to the Entitlement service
     */
    @Override
    public void addRole(String tokenID, Role role) {
        if (canAccess(tokenID, PermissionType.DEFINE_ROLE)) {
            this.entitlements.add(role);
        }
    }

    /**
     * Adds a new Permission to entitlement service as a child of the Resource.
     *
     * @param tokenID     id of the AccessToken to use for entitlement to execute this method
     * @param resourceID   the pre-existing Resource ID to add the Permission to
     * @param permission  the Permission to add as a child of the resource
     */
    @Override
    public void addPermissionToResource(String tokenID, String resourceID, Permission permission) {
        if (canAccess(tokenID, PermissionType.DEFINE_PERMISSION)) {
            Resource resource = this.getResourceById(resourceID);
            if (resource != null) {
                resource.addPermission(permission);
                this.entitlements.add(permission);
            }
        }
    }

    /**
     * Adds a new Permission to entitlement service as a child of the Role.
     *
     * @param tokenID       id of the AccessToken to use for entitlement to execute this method
     * @param roleID        the pre-existing Role ID to add the Permission to
     * @param permissionID  the pre-existing Permission ID to add as a child of the Role
     */
    @Override
    public void addPermissionToRole(String tokenID, String roleID, String permissionID) {
        if (canAccess(tokenID, PermissionType.ADD_ENTITLEMENT_TO_ROLE)) {
            Entitlement foundRole = this.getEntitlementById(roleID);
            Entitlement foundPermission = this.getEntitlementById(permissionID);
            if (foundRole != null &&
                foundRole instanceof Role &&
                foundPermission != null &&
                foundPermission instanceof Permission
            ) {
                ((Role) foundRole).addChild(foundPermission);
            }
        }
    }

    /**
     * Adds a new Cred to the User.
     *
     * @param tokenID   id of the AccessToken to use for entitlement to execute this method
     * @param userID    the pre-existing User ID to add the Credential to
     * @param username  the username to use to create the Credential
     * @param password  the password to use to create the Credential
     */
    @Override
    public void addCredentialToUser(String tokenID, String userID, String username, String password) {
        if (canAccess(tokenID, PermissionType.ADD_USER_CREDENTIAL)) {
            User foundUser = this.getUserByUserID(userID);
            if (foundUser != null) {
                foundUser.addCredential( new Credentials(username, password) );
            }
        }
    }

    /**
     * Adds a new Entitlement (can be a Role or Permission) to the User.
     *
     * @param tokenID        id of the AccessToken to use for entitlement to execute this method
     * @param userID         the pre-existing User ID to add the Entitlement to
     * @param entitlementID  the pre-existing Entitlement ID (can be a Role or Permission) to add to the User
     */
    @Override
    public void addEntitlementToUser(String tokenID, String userID, String entitlementID) {
        if (canAccess(tokenID, PermissionType.ADD_ENTITLEMENT_TO_USER)) {
            User foundUser = this.getUserByUserID(userID);
            if (foundUser != null) {
                Entitlement entitlement = this.getEntitlementById(entitlementID);
                foundUser.addEntitlement(entitlement);
            }
        }
    }

    /**
     * Logs a user into the EntitlementService.  Modifies the AccessToken of the User to expire in 1 hour.
     *
     * @param username   the username to validate
     * @param password   the user's password to validate
     * @throws AccessDeniedException  thrown if the credentials passed are invalid
     * @return the AccessToken of the user
     */
    @Override
    public AccessToken login(String username, String password) throws AccessDeniedException {
        User foundUser = getUserByUsername(username);
        if (foundUser != null) {
            if ( foundUser.validatePassword(password) ) {
                // whether the user has an existing access token or not, just generate a new on that will
                // expire in an hour, assign that to the user, and return it
                AccessToken token = new AccessToken( foundUser.getID() );
                foundUser.setAccessToken(token);
                return foundUser.getAccessToken();
            }
        }
        throw new AccessDeniedException(username, "", 0, "", null);
    }

    /**
     * Logs the User out that owns the AccessToken with the supplied ID.  Modifies the AccessToken to set the
     * expiration time to be now.
     *
     * @param tokenID  the id of the AccessToken for the owning user
     */
    @Override
    public void logout(String tokenID) {
        // need to find the user that owns the token, then destroy the token on that user
        User foundUser = getUserByAccessTokenID(tokenID);
        if (foundUser != null) {
            AccessToken foundToken = foundUser.getAccessToken();
            if (foundToken != null && foundToken.getId().equals(tokenID)) {
                foundToken.setLastUpdated(new Date());
                foundToken.setExpirationTime(new Date());
            }
        }
    }

    /**
     * Checks if the User that owns the AccessToken corresponding to the passed tokenID has permission to use
     * the Permission.  Looks up the AccessToken's owning User, and inspects their Entitlements to confirm whether
     * or not the user has the corresponding Permission.
     *
     * @param tokenID       the id of the AccessToken to check
     * @param permissionID  the id of the Permission to check the user for
     * @return true if the user has the Permission, false otherwise
     */
    @Override
    public boolean canAccess(String tokenID, String permissionID) {
        User foundUser = getUserByAccessTokenID(tokenID);
        if (foundUser != null) {
            // if the user HAS an access token currently but it's expired, throw an exception and have them login again
            AccessToken foundToken = foundUser.getAccessToken();
            if (foundToken != null && !foundToken.getExpirationTime().after(new Date())) {
                return false;
            } else {
                return foundUser.hasPermission(permissionID);
            }
        }
        return false;
    }

    /**
     * Checks if the User that owns the AccessToken corresponding to the passed tokenID has permission to use
     * the Permission.  Looks up the AccessToken's owning User, and inspects their Entitlements to confirm whether
     * or not the user has the corresponding Permission.
     *
     * @param tokenID         the id of the AccessToken to check
     * @param permissionType  the PermissionType to check the user for
     * @return true if the user has the Permission, false otherwise
     */
    @Override
    public boolean canAccess(String tokenID, PermissionType permissionType) {
        return canAccess(tokenID, permissionType.getPermissionName());
    }

    /**
     * Returns a string representation of the entire Entitlements services, including Resources,
     * Users, Roles, and Permissions.  Uses the Visitor pattern to visit each Resource, User, Role, and Permission to
     * inquire about the object's salient properties.
     *
     * @return  string representation of the entire Entitlement services
     */
    @Override
    public String getInventory() {
        EntitlementVisitor ev = new EntitlementVisitor();

        // get counts of how many services and users, and how many distinct Permissions/Roles
        int numResources = this.resources.size();
        int numUsers = this.users.size();
        int numEntitlements = this.entitlements.size();

        StringBuilder inventory = new StringBuilder();
        inventory.append("Entitlement Service API Inventory\n------------------------------------\n\n");
        inventory.append(String.format("There are [%d] registered Users.  They are:\n",numUsers));
        for (User u : users) {
            inventory.append( u.acceptVisitor(ev) );
        }

        inventory.append("\n");
        inventory.append(String.format("There are [%d] defined Resources.  They are:\n",numResources));
        for (Resource r : resources) {
            inventory.append( r.acceptVisitor(ev) );
        }

        inventory.append("\n");
        inventory.append(String.format("There are [%d] defined Entitlements (Roles or Permissions).  They are:\n",numEntitlements));
        for (Entitlement e : entitlements) {
            inventory.append( e.acceptVisitor(ev) );
        }

        return inventory.toString();
    }


    /* begin region: Private helper methods */

    /**
     * Helper method to retrieve a User by their ID.
     *
     * @param userID  the id of the user to find
     * @return  the found user
     */
    private User getUserByUserID(String userID) {
        for (User user : users) {
            if (user.getID().equals(userID) ) {
                return user;
            }
        }
        return null;
    }

    /**
     * Helper method to retrieve a User by any of their associated usernames.
     *
     * @param username  the username of the user to find
     * @return  the found user
     */
    private User getUserByUsername(String username) {
        for (User user : users) {
            for (Credentials credentials : user.getCredentials()) {
                if ( credentials.getUsername().equals(username)) {
                    return user;
                }
            }
        }
        return null;
    }

    /**
     * Helper method to retrieve a User by the id of their AccessToken
     *
     * @param tokenID  the id of the AccessToken that the belongs to the user
     * @return  the found user
     */
    private User getUserByAccessTokenID(String tokenID) {
        for (User user : users) {
            AccessToken token = user.getAccessToken();
            if (token != null && token.getId().equals(tokenID)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Helper method to retrieve an Entitlement by its ID.
     *
     * @param entitlementId  the id of the Entitlement to find
     * @return  the found Entitlement
     */
    private Entitlement getEntitlementById(String entitlementId) {
        for (Entitlement e : this.entitlements) {
            if (e.getID().equals(entitlementId))
                return e;
        }
        return null;
    }

    /**
     * Helper method to retrieve a Resource by its ID.
     *
     * @param resourceId  the id of the Resource to find
     * @return  the found Service
     */
    private Resource getResourceById(String resourceId) {
        for (Resource r : this.resources) {
            if (r.getID().equals(resourceId))
                return r;
        }
        return null;
    }

    /* end region: Private helper methods */

}
