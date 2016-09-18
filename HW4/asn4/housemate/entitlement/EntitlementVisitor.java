package cscie97.asn4.housemate.entitlement;

/**
 * Used to aid in building up a printable inventory of the
 * {@link cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI} to list out all the
 * {@link cscie97.asn4.housemate.entitlement.Service}, {@link cscie97.asn4.housemate.entitlement.Entitlement}
 * (which are subclassed as {@link cscie97.asn4.housemate.entitlement.Role}s and
 * {@link cscie97.asn4.housemate.entitlement.Permission}s), and {@link cscie97.asn4.housemate.entitlement.User}.
 * This class is a primary actor in the Visitor pattern usage for building up a printable inventory of Entitlement
 * items.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.entitlement.Service
 * @see cscie97.asn4.housemate.entitlement.Entitlement
 * @see cscie97.asn4.housemate.entitlement.Role
 * @see cscie97.asn4.housemate.entitlement.Permission
 * @see cscie97.asn4.housemate.entitlement..User
 */
public class EntitlementVisitor implements IEntitlementVisitor {

    /**
     * Helper method to retrieve the printable properties of all classes that inherit from
     * {@link cscie97.asn4.housemate.entitlementItem}.
     *
     * @param item  an object that inherits from Item so has shared properties
     * @return  a string representation of the shared Item properties
     */
    private String getItemProperties(Item item) {
        String id = item.getID();
        String name = item.getName();
        return String.format("ID: [%s] Name: [%s]", id, name);
    }

    /**
     * Visits an {@link cscie97.asn4.housemate.entitlement.Entitlement} object and returns a string representing
     * its properties.  For {@link cscie97.asn4.housemate.entitlement.Role} objects, will use the
     * {@link cscie97.asn4.housemate.entitlement.RoleIterator} to iterate over all the child
     * {@link cscie97.asn4.housemate.entitlement.Role}s and
     * {@link cscie97.asn4.housemate.entitlement.Permission}s and include them in the returned string.
     *
     * @param entitlement  either a Role or Permission to get the properties for
     * @return  a string containing the properties of the Entitlement
     */
    @Override
    public String visitEntitlement(Entitlement entitlement) {
        String type = null;
        StringBuilder extra = new StringBuilder();
        if (entitlement instanceof Role) {
            type = "Role";
            RoleIterator iterator = ((Role)entitlement).getIterator();
            if (iterator.hasNext()) {
                extra.append(String.format("\n\t\tChildren:"));
            }
            while ( iterator.hasNext() ) {
                Entitlement entitlementChild = iterator.next();
                String childType = null;
                if (entitlementChild instanceof Role) {
                    childType = "Role";
                } else if (entitlementChild instanceof Permission) {
                    childType = "Permission";
                }
                extra.append(String.format("\n\t\t\t[%s] ID: [%s] Name: [%s]", childType, entitlementChild.getID(), entitlementChild.getName()));
            }
        } else if (entitlement instanceof Permission) {
            type = "Permission";
        }
        return String.format("\t[%s]: %s %s\n", type, getItemProperties(entitlement), extra.toString());
    }

    /**
     * Visits a {@link cscie97.asn4.housemate.entitlement.Permission} object and returns a string representing
     * its properties.
     *
     * @param permission  a Permission to get the properties for
     * @return  a string containing the properties of the Permission
     */
    @Override
    public String visitPermission(Permission permission) {
        String type = "Permission";
        StringBuilder extra = new StringBuilder();
        return String.format("\t[%s]: %s %s\n", type, getItemProperties(permission), extra.toString());
    }

    /**
     * Visits an {@link cscie97.asn4.housemate.entitlement.Role} object and returns a string representing
     * its properties.  Will use the {@link cscie97.asn4.housemate.entitlement.RoleIterator} to iterate over all
     * the child {@link cscie97.asn4.housemate.entitlement.Role}s and
     * {@link cscie97.asn4.housemate.entitlement.Permission}s and include them in the returned string.
     *
     * @param role  a Role to get the properties for
     * @return  a string containing the properties of the Role
     */
    @Override
    public String visitRole(Role role) {
        String type = "Role";
        StringBuilder extra = new StringBuilder();
        RoleIterator iterator = role.getIterator();
        if (iterator.hasNext()) {
            extra.append(String.format("\n\t\tChildren:"));
        }
        while ( iterator.hasNext() ) {
            Entitlement entitlementChild = iterator.next();
            String childType = null;
            if (entitlementChild instanceof Role) {
                childType = "Role";
            } else if (entitlementChild instanceof Permission) {
                childType = "Permission";
            }
            extra.append(String.format("\n\t\t\t[%s] ID: [%s] Name: [%s]", childType, entitlementChild.getID(), entitlementChild.getName()));
        }
        return String.format("\t[%s]: %s %s\n", type, getItemProperties(role), extra.toString());
    }

    /**
     * Visits a {@link cscie97.asn4.housemate.entitlement.Service} and prints out the properties of
     * the object.
     *
     * @param resource  the Resource to get the properties for
     * @return  a string containing the properties of the Resource
     */
    @Override
    public String visitResource(Resource resource) {
        return String.format("\t[Resource]: %s\n", getItemProperties(resource));
    }

    /**
     * Visits a {@link cscie97.asn4.housemate.entitlement.User} and prints out the properties of
     * the object.
     *
     * @param user  the User to get the properties for
     * @return  a string containing the properties of the User
     */
    @Override
    public String visitUser(User user) {
        return String.format("\t[User]: %s\n", getItemProperties(user));
    }

    /**
     * Calls the appropriate visit* method based on the object type passed.
     *
     * @param item  an item that is visitable from the Entitlement Service, such as User, Resource,  Role, or Permission
     * @return  a string representing the properties of the object
     */
    @Override
    public String visit(Object item) {
        if (item instanceof Resource) {
            return visitResource((Resource)item);
        }
        else if (item instanceof Role) {
            return visitRole((Role)item);
        }
        else if (item instanceof Permission) {
            return visitPermission((Permission)item);
        }
        else if (item instanceof User) {
            return visitUser((User)item);
        }
        return null;
    }
}
