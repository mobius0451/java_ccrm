package com.ccrm.common;

import org.springframework.security.core.Authentication;

/**
 * @CreateTime: 2022-11-20 21:48
 * @Description: 身份验证信息身份验证信息
 */
public class AuthenticationContextHolder {

    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    public static Authentication getContext() {
        return contextHolder.get();
    }

    public static void setContext(Authentication context) {
        contextHolder.set(context);
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
