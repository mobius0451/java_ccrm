package com.ccrm.common;

import com.ccrm.domain.entity.SysUser;

/**
 * @CreateTime: 2022-11-21 22:51
 * @Description:
 */
public class UserContextHolder {

    private static final ThreadLocal<SysUser> contextHolder = new ThreadLocal<>();

    public static SysUser getContext() {
        return contextHolder.get();
    }

    public static void setContext(SysUser user) {
        contextHolder.set(user);
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
