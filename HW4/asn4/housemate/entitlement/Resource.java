package cscie97.asn4.housemate.entitlement;

import java.util.HashSet;
import java.util.Set;

/**
 * Resources represent the major functional areas of the House Mate Service, which now includes the
 * EntitlementServiceAPI, HouseMateControllerServiceAPI, and HouseMateModelServiceAPI. 
 * Primarily marker classes for logical groupings of child {@link cscie97.asn4.housemate.entitlement.Permission}s, 
 * Resources don't currently exhibit much behavior.
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.entitlement.Permission
 * @see cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI
 */
public class Resource extends Item implements IEntitlementVisitable {

    /**
     * Child {@link cscie97.asn4.housemate.entitlement.Permission} objects of the Resource
     */
    private Set<Permission> permissions = new HashSet<Permission>();

    /**
     * Class constructor.
     *
     * @param id                  the unique entitlement Resource ID
     * @param name                the entitlement resource name
     * @param description         entitlement resource description
     */
    public Resource(String id, String name, String description) {
        this.setID(id);
        this.setName(name);
        this.setDescription(description);
    }

    /**
     * No-argument class constructor.
     */
    public Resource() { }

    /**
     * Gets the child permissions of the Resource
     *
     * @return the set of Permissions of the Resource
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Sets the child permissions of the Resource
     *
     * @param permissions  the set of Permissions of the Resource
     */
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Adds a single Permission to the Resource
     *
     * @param permission  a new Permission to add to the Resource
     */
    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    /**
     * Adds a set of Permissions to the Resource
     *
     * @param permissions  a new set of additional Permissions to add to the Resource
     */
    public void addPermissions(Set<Permission> permissions) {
        this.permissions.addAll(permissions);
    }

    /**
     * Accepts a visitor object for the purposes of building up an inventory of items in the EntitlementService.
     *
     * @param visitor  the visiting object used to build up the inventory
     * @return  the string representation of the current object for inclusion in a printable inventory
     */
    public String acceptVisitor(IEntitlementVisitor visitor) {
        return visitor.visitResource(this);
    }


}
