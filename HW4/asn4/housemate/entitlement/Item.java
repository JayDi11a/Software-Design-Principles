package cscie97.asn4.housemate.entitlement;


/**
 * Abstract class representing an entitlement item (could be a {@link cscie97.asn4.housemate.entitlement.Role},
 * {@link cscie97.asn4.housemate.entitlement.Permission}, or {@link cscie97.asn4.housemate.entitlement.Resource})
 * that is included in the HouseMate Model Entitlement Service.  Attributes here are common to all items
 * in the Entitlement service, regardless of type.  All entitlement items (regardless of type) have the
 * following attributes:
 * <ul>
 *     <li>must have an <b>ID</b> that is a unique GUID</li>
 *     <li>must have a <b>name</b></li>
 *     <li>must have a <b>description</b></li>
 * </ul>
 * Certain specific types of Entitlement items may have additional required attributes.  Each
 * {@link cscie97.asn4.housemate.entitlement.Item} that lives in the Entitlement service is a unique item.
 * Entitlement items may be added to the entitlement catalog and made returnable by calling
 * {@link IEntitlementServiceAPI#getInventory()}.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.entitlement.Role
 * @see cscie97.asn4.housemate.entitlement.Service
 * @see cscie97.asn4.housemate.entitlement.Permission
 * @see cscie97.asn4.housemate.entitlement.Entitlement
 * @see cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI
 */
public abstract class Item implements IEntitlementVisitable {

    /**
     * A unique string identifier for each entitlement item; should be a GUID if implementing object is a User
     */
    private String id;

    /**
     * Name of the entitlement item
     */
    private String name;

    /**
     * A brief description of what this entitlement item is and it's features.
     */
    private String description;

    /**
     * Returns the unique entitlement ID of the item.
     *
     * @return  the unique entitlement ID
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the unique ID for this entitlement item.
     *
     * @param id  the unique ID to use for the entitlement item
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Returns the name of the entitlement item.
     *
     * @return  the entitlement item name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for this entitlement item.
     *
     * @param name  the name to use for the entitlement item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the entitlement item description.
     *
     * @return  entitlement item description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the entitlement item description
     *
     * @param description  the new entitlement item description to use
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Accepts a visitor object for the purposes of building up an inventory of items in the EntitlementService.
     *
     * @param visitor  the visiting object used to build up the inventory
     * @return  the string representation of the current object for inclusion in a printable inventory
     */
    public String acceptVisitor(IEntitlementVisitor visitor) {
        return visitor.visit(this);
    }

    /**
     * Public static method that checks that all required fields are set, and that all entitlement item values are
     * valid.
     *
     * @param content  the item to be validated for correct properties
     * @return  true if all properties are valid, false otherwise
     */
    public static boolean validateItem(Item content) {
        return (
                   (content.getID() != null && content.getID().length() > 0) &&
                   (content.getName() != null && content.getName().length() > 0) &&
                   (content.getDescription() != null && content.getDescription().length() > 0)
        );
    }

    /**
     * Returns a string representation of the entitlement item; useful for debugging.
     *
     * @return  string representation of the entitlement item
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ENTITLEMENT ITEM:\n") );
        sb.append(String.format("\tID: [%s]\n", this.getID()));
        sb.append(String.format("\tNAME: [%s]\n", this.getName()));
        sb.append(String.format("\tDESCRIPTION: [%s]\n", this.getDescription()));
        return sb.toString();
    }

    

}
