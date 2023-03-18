package com.ccrm.controller.system;

import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysMenu;
import com.ccrm.domain.entity.SysUser;
import com.ccrm.domain.model.LoginBody;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysLoginService;
import com.ccrm.service.ISysMenuService;
import com.ccrm.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Set;

/**
 * @CreateTime: 2022-11-06 15:24
 * @Description: 登录验证
 */
@RestController
public class SysLoginController {

    @Autowired
    private ISysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登陆
     * @param loginBody
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginBody loginBody) {
        ResponseResult result = ResponseResult.success();
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        result.put("token", token);
        return result;
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/getInfo")
    public ResponseResult getInfo() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        ResponseResult result = ResponseResult.success();
        result.put("user", user);
        result.put("roles", roles);
        result.put("permissions", permissions);
        return result;
    }

    /**
     * 获取路由
     * @return
     */
    @GetMapping("/getRouters")
    public ResponseResult getRouters() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return ResponseResult.success(menuService.buildMenus(menus));
    }

}
