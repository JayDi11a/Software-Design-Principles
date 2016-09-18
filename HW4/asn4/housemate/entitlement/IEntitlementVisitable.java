package cscie97.asn4.housemate.entitlement;

/**
 * Interface that marks implementing classes as able to accept an
 * {@link cscie97.asn4.housemate.entitlement.IEntitlementVisitor} for building up a printable product inventory.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.entitlement.Service
 * @see cscie97.asn4.housemate.entitlement.Entitlement
 * @see cscie97.asn4.housemate.entitlement.Role
 * @see cscie97.asn4.housemate.entitlement.Permission
 * @see cscie97.asn4.housemate.entitlement.User
 * @see cscie97.asn4.housemate.entitlement.IEntitlementVisitor
 */
public interface IEntitlementVisitable {

    /**
     * Accept a visiting class for the purpose of building up a printable product inventory.
     *
     * @param visitor  the visiting class
     * @return  a string representation of the object being visited for inclusion in a printable inventory
     */
    public String acceptVisitor(IEntitlementVisitor visitor);

}
