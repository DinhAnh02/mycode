package vn.eledevo.vksbe.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import vn.eledevo.vksbe.entity.Accounts;

public class SecurityUtils {
    public static Long getUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Accounts user = (Accounts) securityContext.getAuthentication().getPrincipal();
        return user.getId();
    }

    public static String getUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Accounts user = (Accounts) securityContext.getAuthentication().getPrincipal();
        return user.getUsername();
    }
}
