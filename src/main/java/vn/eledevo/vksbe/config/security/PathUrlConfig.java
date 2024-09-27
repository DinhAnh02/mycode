package vn.eledevo.vksbe.config.security;

public class PathUrlConfig {
    // Đây là nơi khai báo các url public
    public static final String[] URL_PUBLIC = {
        "/api/v1/auth/authenticate",
        "/api/v1/private/categories/getAll",
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
        "/api/v1/auth/download"
    };
    // Đây là nơi khai báo các url phân quyền theo role
    public static final String[] URL_IT_ADMIN = {
        "/api/v1/private/computers**",
        "/api/v1/private/usbs/**",
        "/api/v1/private/accounts/{accountId}/remove-computer/{computerId}"
    };
    // Đây là nơi khai báo các url phân quyền theo 5 role trừ Kiểm sát viên
    public static final String[] URL_FIVE_ROLE_KIEM_SAT_VIEN = {
        "/api/v1/private/accounts/**",
    };
}
