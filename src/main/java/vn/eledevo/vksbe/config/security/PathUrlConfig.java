package vn.eledevo.vksbe.config.security;

public class PathUrlConfig {
    private PathUrlConfig() {}

    // Đây là nơi khai báo các url là permitAll
    protected static final String[] WHITE_LIST_URL = {
        "/api/v1/auth/authenticate",
        "/api/v1/auth/createAccountTest",
        "/api/v1/private/categories",
        "/v2/api-docs",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui/**",
        "/webjars/**",
        "/swagger-ui.html",
        "/api/v1/private/minio/**",
    };

    //Các URL có cả 6 Role
    protected static final String[] ALL_ROLE_URL = {
        "/api/v1/auth/**",
        "/api/v1/private/accounts/get-user-info",
        "/api/v1/private/accounts/change-pin-code",
        "/api/v1/private/accounts/{id}/update-avatar-user-info",
        "/api/v1/private/case-status",
        "/api/v1/private/case-status/{id}/detail",
        "/api/v1/private/accounts/upload-image"
    };

    // Các URL có 5 Role
    //Viện trưởng, Viện phó, Trưởng phòng, Phó phòng, IT Admin
    protected static final String[] NO_ROLE_KIEM_SAT_VIEN_URL = {
        "/api/v1/private/accounts",
        "/api/v1/private/accounts/{id}/detail",
        "/api/v1/private/accounts/{id}/usb",
        "/api/v1/private/accounts/{accountId}/inactivate",
        "/api/v1/private/accounts/{accountId}/swap-account-status/{swapAccountId}",
        "/api/v1/private/accounts/{id}/devices",
        "/api/v1/private/departments",
        "/api/v1/private/organizations/search"
    };

    // Các URL có 4 Role
    //Viện trưởng, Viện phó, Trưởng phòng, Phó phòng
    protected static final String[] NO_ROLE_KIEM_SAT_VIEN_AND_NO_IT_ADMIN_URL = {
        "/api/v1/private/mindmapTemplate/**",
    };

    // Chỉ có Role IT_ADMIN
    protected static final String[] ROLE_IT_ADMIN_URL = {
        "/api/v1/private/accounts/reset-password/{id}",
        "/api/v1/private/computers/**",
        "/api/v1/private/usbs/**",
        "/api/v1/private/usbs/download/{username}",
        "/api/v1/private/accounts/{accountId}/remove-computer/{computerId}",
        "/api/v1/private/accounts/connect-computer/{id}/computers",
        "/api/v1/private/accounts/remove-usb/{accountId}/usb/{usbId}",
        "/api/v1/private/accounts/create",
        "/api/v1/private/accounts/{updatedAccId}/update-info",
        "/api/v1/private/accounts/connect-computer/{id}/computers",
        "/api/v1/private/computers/check-exist-computer",
    };

    // Các URL có 4 Role
    //Viện trưởng, Viện phó, Trưởng phòng, Phó phòng
    protected static final String[] VIEN_TRUONG_VIEN_PHO_IT_ADMIN_URL = {
        "/api/v1/private/departments/{id}/update-department",
        "/api/v1/private/case-status/create",
        "/api/v1/private/organizations/{id}/update",
        "/api/v1/private/organizations/{id}/delete",
        "/api/v1/private/organizations/create",
        "/api/v1/private/organizations/{id}/detail",
        "/api/v1/private/case-status/{id}/delete",
        "/api/v1/private/case-status/{id}/update",
    };

    // Các URL có 5 Role
    //Viện trưởng, Viện phó, Trưởng phòng, Phó phòng, Kiểm sát viên
    protected static final String[] NO_ROLE_IT_ADMIN_URL = {
        "/api/v1/private/cases/{id}/investigator",
        "/api/v1/private/accounts/get-account-case-filter",
        "/api/v1/private/citizens",
        "/api/v1/private/cases/{id}/infomation-detail",
        "/api/v1/private/cases/{id}/update",
        "/api/v1/private/cases/create",
        "/api/v1/private/cases/{id}/case-person/suspect-defendant",
        "/api/v1/private/citizens/{id}/update",
        "/api/v1/private/cases",
        "/api/v1/private/cases/{caseId}/documents/folder/create",
        "/api/v1/private/cases/{id}/user-in-charge",
        "/api/v1/private/cases/{id}/account-case/prosecutor",
        "/api/v1/private/citizens/create",
        "/api/v1/private/cases/accounts",
        "/api/v1/private/cases/{caseId}/case-flow",
        "/api/v1/private/cases/{id}/account-cases/update",
        "/api/v1/private/cases/{id}/case-person/suspect-defendant/update",
        "/api/v1/private/cases/{id}/case-person/suspect-defendant/type/update",
        "/api/v1/private/cases/{caseId}/case-flow/create",
        "/api/v1/private/cases",
        "/api/v1/private/cases/{id}/case-flow/{idCaseFlow}",
        "/api/v1/private/cases/{caseId}/mindMapTemplate",
        "/api/v1/private/cases/{id}/citizen",
    };
}
