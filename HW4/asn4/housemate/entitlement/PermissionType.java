package cscie97.asn4.housemate.entitlement;

/**
 * Enumeration of all Permission types that the {@link cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI}
 * uses, as well as the Permission types that are used by the {@link cscie97.asn4.housemate.model.IHousemateModelServiceAPI} and
 * {@link cscie97.asn4.housemate.controller.IHouseMateControllerServiceAPI}.
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI
 * @see cscie97.asn4.housemate.model.IHouseMateModelServiceAPI
 * @see cscie97.asn4.housemate.controller.IHouseMateControllerServiceAPI
 */
public enum PermissionType {

    /** used by the {@link cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI} */
    DEFINE_PERMISSION ("define_permission"),

    /** used by the {@link cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI} */
    DEFINE_ROLE ("define_role"),

    /** used by the {@link cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI} */
    ADD_ENTITLEMENT_TO_ROLE ("add_entitlement_to_role"),

    /** used by the {@link cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI} */
    CREATE_USER ("create_user"),

    /** used by the {@link cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI} */
    ADD_USER_CREDENTIAL ("add_user_credential"),

    /** used by the {@link cscie97.asn4.housemate.entitlement.IEntitlementServiceAPI} */
    ADD_ENTITLEMENT_TO_USER ("add_entitlement_to_user"),

    /** used by the {@link cscie97.asn4.housemate.model.IHouseMateModelServiceAPI}    */
    CREATE_RESOURCE_ROLE ("create_resource_role"),

    /** used by the {@link cscie97.asn4.housemate.model.IHouseMateModelServiceAPI}    */
    ADD_RESOURCE_ROLE_TO_USER ("add_resource_role_to_user");


    private final String permissionName;

    PermissionType(String name) {
        this.permissionName = name;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

}
