package com.ccrm.service.impl;

import com.ccrm.common.AuthenticationContextHolder;
import com.ccrm.common.CacheConstants;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.exception.CustomException;
import com.ccrm.service.ISysLoginService;
import com.ccrm.utils.JwtUtils;
import com.ccrm.utils.RedisCache;
import com.ccrm.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @CreateTime: 2022-11-20 21:27
 * @Description:
 */
@Service
public class SysLoginServiceImpl implements ISysLoginService {

    @Autowired
    private JwtUtils jwtUtils;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        validateCaptcha(code, uuid);
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new CustomException("密码错误");
            }
            else {
                throw new CustomException(e.getMessage());
            }
        }
        finally {
            AuthenticationContextHolder.clearContext();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return jwtUtils.createToken(loginUser);
    }

    /**
     * 校验验证码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CustomException("验证码过期");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CustomException("验证码错误");
        }
    }

}
