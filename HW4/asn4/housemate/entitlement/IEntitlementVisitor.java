package cscie97.asn4.housemate.entitlement;

/**
 * Interface for defining the visit methods that the implementing class must overwrite; follows the Visitor pattern.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.entitlement.Service
 * @see cscie97.asn4.housemate.entitlement.Entitlement
 * @see cscie97.asn4.housemate.entitlement.Role
 * @see cscie97.asn4.housemate.entitlement.Permission
 * @see cscie97.asn4.housemate.entitlement.User
 */
public interface IEntitlementVisitor {

    /**
     * Visits an {@link cscie97.asn4.housemate.entitlement.Entitlement} object and returns a string representing
     * its properties.  For {@link cscie97.asn4.housemate.entitlement.Role} objects, will use the
     * {@link cscie97.asn4.housemate.entitlement.RoleIterator} to iterate over all the child
     * {@link cscie97.asn4.housemate.entitlement.Role}s and
     * {@link cscie97.asn4.housemate.entitlement.Permission}s and include them in the returned string.
     *
     * @param entitlement  either a Role or Permission to get the properties for
     * @return  a string containing the  properties of the Entitlement
     */
    public String visitEntitlement(Entitlement entitlement);

    /**
     * Visits a {@link cscie97.asn4.housemate.entitlement.Permission} object and returns a string representing
     * its properties.
     *
     * @param permission  a Permission to get the properties for
     * @return  a string containing the properties of the Permission
     */
    public String visitPermission(Permission permission);

    /**
     * Visits an {@link cscie97.asn4.housemate.entitlement.Role} object and returns a string representing
     * its properties.  Will use the {@link cscie97.asn4.housemate.entitlement.RoleIterator} to iterate over all
     * the child {@link cscie97.asn4.housemate.entitlement.Role}s and
     * {@link cscie97.asn4.housemate.entitlement.Permission}s and include them in the returned string.
     *
     * @param role  a Role to get the properties for
     * @return  a string containing the properties of the Role
     */
    public String visitRole(Role role);

    /**
     * Visits a {@link cscie97.asn4.housemate.entitlement.Service} and prints out the properties of
     * the object.
     *
     * @param resource  the Resource to get the properties for
     * @return  a string containing the properties of the Resource
     */
    public String visitResource(Resource resource);

    /**
     * Visits a {@link cscie97.asn4.housemate.entitlement.User} and prints out the properties of
     * the object.
     *
     * @param user  the User to get the properties for
     * @return  a string containing the properties of the User
     */
    public String visitUser(User user);

    /**
     * Calls the appropriate visit* method based on the object type passed.
     *
     * @param item  an item that is visitable from the Entitlement Service, such as User, Resource, Role, or Permission
     * @return  a string representing the properties of the object
     */
    public String visit(Object item);

}
