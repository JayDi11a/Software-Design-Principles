package cscie97.asn4.housemate.entitlement;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

/**
 * Registered Users may call restricted interface methods on each of the
 * {@link cscie97.asn4.housemate.entitlment.IEntitlementServiceAPI},
 * {@link cscie97.asn4.housemate.model.IHouseMateModelServiceAPI}, and
 * {@link cscie97.asn4.housemate.controller.IHouseMateControllerServiceAPI} services, as long as the User has the appropriate
 * Entitlements.  Users may have multiple sets of {@link cscie97.asn4.housemate.entitlement.Credentials}, which
 * mean they are able to log in with different sets of usernames and passwords.  However, each user may only have a
 * single AccessToken, the ID of which is passed around to the restricted interface methods to ensure the user is
 * authorized to carry out the method being called.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.entitlement.Credentials
 * @see cscie97.asn4.housemate.entitlement.AccessToken
 */
public class User extends Item implements IEntitlementVisitable {

    /**
     * The set of Credentials (usernames, passwords) that the user may use for logging in
     */
    private Set<Credentials> credentials = new HashSet<Credentials>();

    /**
     * The set of unique Entitlements (either Roles, Permissions, or both) that the user has access to
     */
    private Set<Entitlement> entitlements = new HashSet<Entitlement>();

    /**
     * The AccessToken is what is checked for validity when the user calls restricted interface methods on any
     * of the published APIs
     */
    private AccessToken token;

    /**
     * Class constructor.
     *
     * @param id           the unique user ID
     * @param name         the entitlement item name
     * @param description  entitlement item description
     */
    public User(String id, String name, String description) {
        if (id == null || id.length() == 0) {
            this.setID(UUID.randomUUID().toString());
        } else {
            this.setID(id);
        }
        this.setName(name);
        this.setDescription(description);
    }

    /**
     * Class constructor.  Since no ID or description are specified, will generate a new GUID for the ID, and
     * also use the name as part of the description.
     *
     * @param name  the user name
     */
    public User(String name) {
        this(UUID.randomUUID().toString(), name, String.format("User account for %s", name) );
    }

    /**
     * Class constructor.  Since no description is specified, will generate a default description based on the name
     *
     * @param id    the user ID
     * @param name  the user name
     */
    public User(String id, String name) {
        this(id, name, String.format("User account for %s", name) );
    }

    /**
     * Gets the set of Credentials for the User
     *
     * @return  the user's credentials
     */
    public Set<Credentials> getCredentials() {
        return credentials;
    }

    /**
     * Sets the Credentials for the user account
     *
     * @param credentials  the credentials for the user
     */
    public void setCredentials(Set<Credentials> credentials) {
        this.credentials = credentials;
    }

    /**
     * Gets the set of Entitlements for the User
     *
     * @return  the user's entitlements
     */
    public Set<Entitlement> getEntitlements() {
        return entitlements;
    }

    /**
     * Sets the Entitlements for the user account
     *
     * @param entitlements  the entitlements for the user
     */
    public void setEntitlements(Set<Entitlement> entitlements) {
        this.entitlements = entitlements;
    }

    /**
     * Gets the AccessToken for the User
     *
     * @return  the user's token
     */
    public AccessToken getAccessToken() {
        return token;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.token = accessToken;
    }

    /**
     * Adds a Credential to the user account
     *
     * @param credential  the credential to add to the user
     */
    public void addCredential(Credentials credential) {
        this.credentials.add(credential);
    }

    /**
     * Creates a new {@link cscie97.asn4.housemate.entitlement.Credentials} object and adds it to the User.
     *
     * @param username  the username to use to construct the new Credentials object to add to the user
     * @param password  the password to use to construct the new Credentials object to add to the user
     */
    public void addCredential(String username, String password) {
        Credentials credential = new Credentials(username, password);
        addCredential(credential);
    }

    /**
     * Adds the Entitlement (may be a Role or Permission) to the User.
     *
     * @param entitlement  the entitlement to add to the user
     */
    public void addEntitlement(Entitlement entitlement) {
        this.entitlements.add(entitlement);
    }

    /**
     * Checks to see if the User has an {@link cscie97.asn4.housemate.entitlement.Entitlement} that has the
     * passed permissionID.
     *
     * @param permissionID  the id of the {@link cscie97.asn4.housemate.entitlement.Permission} to check if the
     *                      user has it
     * @return  true if the user has the Permission, false otherwise
     */
    public boolean hasPermission(String permissionID) {
        for (Entitlement e : getEntitlements()) {
            if (e.getID().equals(permissionID)) {
                return true;
            }
            else if (e instanceof Role) {
                RoleIterator iterator = ((Role) e).getIterator();
                while (iterator.hasNext()) {
                    Entitlement e2 = iterator.next();
                    if (e2.getID().equals(permissionID)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Validates that the password exists on the User's set of Credentials.
     *
     * @param password  the password to validate
     * @return  true if the password is correct for the user, false otherwise
     */
    public boolean validatePassword(String password) {
        for (Credentials c : credentials) {
            try {
                if (PasswordHash.validatePassword(password, c.getPasswordHash()) )
                    return true;
            }
            catch (NoSuchAlgorithmException nsae) { }
            catch (InvalidKeySpecException ikse) { }
        }
        return false;
    }

}
