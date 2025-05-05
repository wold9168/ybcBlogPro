package com.hgd.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static <T> T getAuthUser() {
        try {
            return (T) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
