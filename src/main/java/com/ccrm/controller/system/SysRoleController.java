package com.ccrm.controller.system;

import com.ccrm.common.UserConstants;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysRole;
import com.ccrm.domain.entity.SysUser;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysRoleService;
import com.ccrm.service.ISysUserService;
import com.ccrm.service.SysPermissionService;
import com.ccrm.utils.JwtUtils;
import com.ccrm.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @CreateTime: 2022-11-14 15:05
 * @Description: 角色信息
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SysPermissionService permissionService;



    /**
     * 分页获取角色列表
     * @param pageNum
     * @param pageSize
     * @param role
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:list')")
    @GetMapping("list")
    public ResponseResult list(int pageNum, int pageSize, SysRole role) {
        Page<SysRole> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(role.getRoleName()), SysRole::getRoleName, role.getRoleName());
        queryWrapper.like(StringUtils.isNotEmpty(role.getRoleKey()), SysRole::getRoleKey, role.getRoleKey());
        queryWrapper.eq(StringUtils.isNotEmpty(role.getStatus()), SysRole::getStatus, role.getStatus());
        queryWrapper.ge(StringUtils.isNotEmpty(role.getParams()), SysRole::getCreateTime, role.getParams().get("beginTime"));
        queryWrapper.le(StringUtils.isNotEmpty(role.getParams()), SysRole::getCreateTime, role.getParams().get("endTime"));
        queryWrapper.orderByAsc(SysRole::getRoleSort);
        roleService.page(page, queryWrapper);
        return ResponseResult.success(page);
    }

    /**
     * 根据角色编号获取详细信息
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:query')")
    @GetMapping(value = "/{roleId}")
    public ResponseResult getInfo(@PathVariable Long roleId) {
        return ResponseResult.success(roleService.getById(roleId));
    }

    /**
     * 新增角色
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:add')")
    @PostMapping
    public ResponseResult save(@Validated @RequestBody SysRole role) {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return ResponseResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return ResponseResult.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        int row = roleService.saveRole(role);
        if (row > 0) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }

    }

    /**
     * 修改保存角色
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:edit')")
    @PutMapping
    public ResponseResult update(@RequestBody SysRole role) {
        roleService.checkRoleAllowed(role);
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return ResponseResult.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return ResponseResult.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        if (roleService.updateRole(role) > 0) {
//            // 更新缓存用户权限
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (StringUtils.isNotNull(loginUser.getUser()) && !loginUser.getUser().isAdmin()) {
                loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
                LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysUser::getUserName, loginUser.getUser().getUserName());
                loginUser.setUser(userService.getOne(queryWrapper));
                jwtUtils.setLoginUser(loginUser);
            }
            return ResponseResult.success();
        }
        return ResponseResult.error("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }


    /**
     * 状态修改
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:edit')")
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody SysRole role) {
        roleService.checkRoleAllowed(role);
        boolean i = roleService.updateById(role);
        if (i) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 删除角色
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:remove')")
    @DeleteMapping("/{roleIds}")
    public ResponseResult remove(@PathVariable List<Long> roleIds) {
        int r = roleService.removeRoleByIds(roleIds);
        if (r > 0) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 获取角色选择框列表
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:query')")
    @GetMapping("/optionselect")
    public ResponseResult optionSelect() {
        return ResponseResult.success(roleService.list());
    }

    /**
     * 查询已分配用户角色列表
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:list')")
    @GetMapping("/authUser/allocatedList")
    public ResponseResult allocatedList(int pageNum, int pageSize, SysUser user) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        List<Long> userIds = userService.selectAllocatedList(user.getRoleId());
        if (StringUtils.isNotEmpty(userIds)) {
            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysUser::getUserId, userIds);
            queryWrapper.like(StringUtils.isNotEmpty(user.getUserName()), SysUser::getUserName, user.getUserName());
            queryWrapper.like(StringUtils.isNotEmpty(user.getPhone()), SysUser::getPhone, user.getPhone());
            userService.page(page, queryWrapper);
        }
        return ResponseResult.success(page);
    }

    /**
     * 查询未分配用户角色列表
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:list')")
    @GetMapping("/authUser/unallocatedList")
    public ResponseResult unallocatedList(int pageNum, int pageSize,SysUser user) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        List<Long> userIds = userService.selectAllocatedList(user.getRoleId());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.notIn(StringUtils.isNotEmpty(userIds), SysUser::getUserId, userIds);
        queryWrapper.like(StringUtils.isNotEmpty(user.getUserName()), SysUser::getUserName, user.getUserName());
        queryWrapper.like(StringUtils.isNotEmpty(user.getPhone()), SysUser::getPhone, user.getPhone());
        userService.page(page, queryWrapper);
        return ResponseResult.success(page);
    }

    /**
     * 取消授权用户
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:edit')")
    @PutMapping("/authUser/cancel")
    public ResponseResult cancelAuthUser(@RequestBody SysUser user) {
        int row = roleService.deleteAuthUser(user.getUserId(), user.getRoleId());
        if (row > 0) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 批量取消授权用户
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:edit')")
    @PutMapping("/authUser/cancelAll")
    public ResponseResult cancelAuthUserAll(Long roleId,@RequestParam List<Long> userIds) {
        int row = 0;
        for (Long userId : userIds) {
            row = row + roleService.deleteAuthUser(userId, roleId);
        }
        if (row > 0) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 批量选择用户授权
     */
    @PreAuthorize("@ccrm.hasPermi('system:role:edit')")
    @PutMapping("/authUser/selectAll")
    public ResponseResult selectAuthUserAll(Long roleId,@RequestParam List<Long> userIds) {
        if (roleService.insertAuthUsers(roleId, userIds) > 0) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

}
