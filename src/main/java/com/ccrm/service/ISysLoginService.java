package com.ccrm.service;

/**
 * @CreateTime: 2022-11-20 21:26
 * @Description: 登录校验方法
 */
public interface ISysLoginService {

    /**
     * 登录验证
     * @param username
     * @param password
     * @param code
     * @param uuid
     * @return
     */
    String login(String username, String password, String code, String uuid);

}
