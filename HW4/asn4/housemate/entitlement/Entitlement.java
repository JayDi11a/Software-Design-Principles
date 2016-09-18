package cscie97.asn4.housemate.entitlement;

/**
 * Abstract parent class for {@link cscie97.asn4.housemate.entitlement.Role} and
 * {@link cscie97.asn4.housemate.entitlement.Permission}. Parent class of
 * {@link cscie97.asn4.housemate.entitlement.Permission} and {@link cscie97.asn4.housemate.entitlement.Role}.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.entitlement.Item
 * @see cscie97.asn4.housemate.entitlement.Permission
 * @see cscie97.asn4.housemate.entitlement.Role
 */
public abstract class Entitlement extends Item implements IEntitlementVisitable {

    /**
     * Abstract class constructor.
     *
     * @param id                  the unique entitlement item ID
     * @param name                the entitlement item name
     * @param description         entitlement item description
     */
    public Entitlement(String id, String name, String description) {
        this.setID(id);
        this.setName(name);
        this.setDescription(description);
    }

    /**
     * No-argument class constructor.
     */
    public Entitlement() { }

    /**
     * Accepts a visitor object for the purposes of building up an inventory of items in the EntitlementService.
     *
     * @param visitor  the visiting object used to build up the inventory
     * @return  the string representation of the current object for inclusion in a printable inventory
     */
    public String acceptVisitor(IEntitlementVisitor visitor) {
        return visitor.visitEntitlement(this);
    }

    
}
