package cscie97.asn4.housemate.entitlement;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Roles represent aggregations of {@link cscie97.asn4.housemate.entitlement.Permission}s and other
 * {@link cscie97.asn4.housemate.entitlement.Role}s.  A Role defines "who a user is"; for example, a role called
 * "admin_role" would contain all the {@link cscie97.asn4.housemate.entitlement.Permission}s that
 * collectively define all the restricted actions that only an EntitlementServiceAPI administrator could perform.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.entitlement.Entitlement
 * @see cscie97.asn4.housemate.entitlement.Role
 * @see cscie97.asn4.housemate.entitlement.Permission
 * @see cscie97.asn4.housemate.entitlement.User
 */
public class Role extends Entitlement implements IEntitlementVisitable {

    /**
     * Defines the iterator for the current role
     */
    private RoleIterator iterator = null;

    /**
     * Returns the iterator for the current Role.  The iterator also follows the Singleton pattern; once the
     * iterator has been declared and initialized, the already-declared one will be returned.  If the iterator has
     * not been defined when getIterator() is called, a new
     * {@link cscie97.asn4.housemate.entitlement.RoleIterator} will be created and initialized.
     *
     * @return  the iterator for the Role
     */
    public RoleIterator getIterator() {
        this.iterator = new RoleIterator(this);
        return this.iterator;
    }

    /**
     * Defines the child elements of the Role, which may be other Roles or Permissions.
     */
    private List<Entitlement> children = new ArrayList<Entitlement>();

    /**
     * Class constructor.
     *
     * @param id           the id of the role
     * @param name         the name of the role
     * @param description  a brief description of the role
     */
    public Role(String id, String name, String description) {
        super(id, name, description);
    }

    /**
     * No-argument class constructor.
     */
    public Role() { }

    /**
     * Gets the children Entitlements of the Role
     *
     * @return  the children Entitlements of the Role
     */
    public List<Entitlement> getChildren() {
        return children;
    }

    /**
     * Sets the current children Entitlements of the Role
     *
     * @param children  the child Entitlements of the Role
     */
    public void setChildren(List<Entitlement> children) {
        this.children = children;
    }

    /**
     * Adds a child Entitlement to the Role
     *
     * @param entitlement  the children Entitlement to add to the Role
     */
    public void addChild(Entitlement entitlement) {
        this.children.add(entitlement);
    }

    /**
     * Adds multiple children Entitlements to the Role
     *
     * @param entitlements  the set of children Entitlements to add to the Role
     */
    public void addChildren(Set<Entitlement> entitlements) {
        this.children.addAll(entitlements);
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

    

}
