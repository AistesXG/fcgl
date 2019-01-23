package com.fcgl.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * SecurityUtils
 *
 * @author furg@senthink.com
 * @date 2018/03/08
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * 获取UserDetails
     *
     * @return
     */
    public static CurrentUser getCurrentUser() {
        try {
            Authentication authentication = getAuthentication();
            return (CurrentUser) authentication.getPrincipal();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Null authentication!");
        }
        return authentication;
    }
}
