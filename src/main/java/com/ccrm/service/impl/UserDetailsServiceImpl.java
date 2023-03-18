package com.ccrm.service.impl;

import com.ccrm.domain.entity.SysUser;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.exception.CustomException;
import com.ccrm.service.ISysUserService;
import com.ccrm.service.SysPermissionService;
import com.ccrm.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Set;

/**
 * @CreateTime: 2022-11-20 21:52
 * @Description: 用户验证处理
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, username);
        SysUser user = userService.getOne(queryWrapper);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new CustomException("登录用户：" + username + " 不存在");
        }
        else if (user.getDelFlag() == 1) {
            log.info("登录用户：{} 已被删除.", username);
            throw new CustomException("对不起，您的账号：" + username + " 已被删除");
        }
        else if ("1".equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new CustomException("对不起，您的账号：" + username + " 已停用");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        Set<String> permission = permissionService.getMenuPermission(user);
        System.out.println("查询用户权限：" + permission);
        return new LoginUser(user.getUserId(), user.getCollegeId(), user, permission);
    }

}
