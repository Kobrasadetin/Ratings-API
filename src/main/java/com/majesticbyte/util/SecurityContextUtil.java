package com.majesticbyte.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {

    public static boolean hasRole (String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    public static String username ()
    {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
