package cscie97.asn4.housemate.entitlement;

/**
 * Permissions represent actions that registered Users may carry out on the
 * {@link cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI},
 * {@link cscie97.asn4.housemate.model.IHouseMateModelServiceAPI}, and
 * {@link cscie97.asn4.housemate.controller.IHouseMateControllerServiceAPI}.  Permissions <b>must</b> be added to
 * {@link cscie97.asn4.housemate.entitlement.Service}s, and may be added to
 * {@link cscie97.asn4.housemate.entitlement.Role}s.  A {@link cscie97.asn4.housemate.entitlement.User} may be
 * granted a Permission either directly or through a {@link cscie97.asn4.housemate.entitlement.Role}.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.entitlement.Item
 * @see cscie97.asn4.housemate.entitlement.Entitlement
 * @see cscie97.asn4.housemate.entitlement.Role
 * @see cscie97.asn4.housemate.entitlement.User
 * @see cscie97.asn4.housemate.entitlement.PermissionType
 */
public class Permission extends Entitlement implements IEntitlementVisitable {

    /**
     * No-argument class constructor.
     */
    public Permission() { }

    /**
     * Class constructor.
     *
     * @param id           the id of the Permission
     * @param name         the name of the Permission
     * @param description  a brief description of the Permission
     */
    public Permission(String id, String name, String description) {
        super(id, name, description);
    }

    /**
     * Class constructor.
     *
     * @param permissionType  the PermissionType for the Permission
     * @param name            the name of the Permission
     * @param description     a brief description of the Permission
     */
    public Permission(PermissionType permissionType, String name, String description) {
        super(permissionType.getPermissionName(), name, description);
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
