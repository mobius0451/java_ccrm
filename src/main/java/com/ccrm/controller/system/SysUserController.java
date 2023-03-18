package com.ccrm.controller.system;

import com.ccrm.common.UserConstants;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysCollege;
import com.ccrm.domain.entity.SysRole;
import com.ccrm.domain.entity.SysUser;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysCollegeService;
import com.ccrm.service.ISysRoleService;
import com.ccrm.service.ISysUserService;
import com.ccrm.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @CreateTime: 2022-11-13 21:39
 * @Description: 用户信息
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysCollegeService collegeService;

    /**
     * 分页获取用户列表
     * @param pageNum
     * @param pageSize
     * @param user
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:user:list')")
    @GetMapping("/list")
    public ResponseResult list(int pageNum, int pageSize, SysUser user) {
        //构建分页条件
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(user.getUserName()), SysUser::getUserName, user.getUserName());
        queryWrapper.like(StringUtils.isNotEmpty(user.getPhone()), SysUser::getPhone, user.getPhone());
        queryWrapper.eq(StringUtils.isNotEmpty(user.getStatus()), SysUser::getStatus, user.getStatus());
        queryWrapper.ge(StringUtils.isNotEmpty(user.getParams()), SysUser::getCreateTime, user.getParams().get("beginTime"));
        queryWrapper.le(StringUtils.isNotEmpty(user.getParams()), SysUser::getCreateTime, user.getParams().get("endTime"));
        if (user.getCollegeId() != null) {
            LambdaQueryWrapper<SysCollege> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(SysCollege::getCollegeId);
            wrapper.like(SysCollege::getAncestors, user.getCollegeId());
            List<SysCollege> list = collegeService.list(wrapper);
            List<Long> collegeIds = new ArrayList<>();
            if (StringUtils.isNotEmpty(list)) {
                for (SysCollege college : list) {
                    collegeIds.add(college.getCollegeId());
                }
                collegeIds.add(user.getCollegeId());
                queryWrapper.in(StringUtils.isNotEmpty(collegeIds), SysUser::getCollegeId, collegeIds);
            }
            else {
                queryWrapper.eq(SysUser::getCollegeId, user.getCollegeId());
            }
        }
        userService.page(page, queryWrapper);
        List<SysUser> records = page.getRecords();
        records.stream().map((item) -> {
            //根据班级id查询班级信息并存入用户信息
            SysCollege college = collegeService.getById(item.getCollegeId());
            item.setCollege(college);
            return item;
        }).collect(Collectors.toList());
        page.setRecords(records);
        return ResponseResult.success(page);
    }

    /**
     * 获取班级树列表
     */
    @GetMapping("/collegeTree")
    public ResponseResult deptTree() {
        return ResponseResult.success(collegeService.selectCollegeTreeList());
    }

    /**
     * 根据用户编号获取详细信息
     * @param userId
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:user:query')")
    @GetMapping(value = { "/", "/{userId}" })
    public ResponseResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        ResponseResult result = ResponseResult.success();
        List<SysRole> roleList = roleService.list();
        result.put("roleList", SysUser.isAdmin(userId) ? roleList : roleList.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        if (StringUtils.isNotNull(userId)) {
            SysUser user = userService.getById(userId);
            SysCollege college = collegeService.getById(user.getCollegeId());
            user.setCollege(college);
            List<SysRole> roles = roleService.selectRoleByUserId(userId);
            user.setRoles(roles);
            List<Long> roleIds = user.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList());
            user.setRoleIds(roleIds);
            result.put(ResponseResult.DATA_TAG, user);
        }
        return result;
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:user:add')")
    @PostMapping
    public ResponseResult save(@RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user))) {
            return ResponseResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhone())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return ResponseResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return ResponseResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        //密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int row = userService.saveUser(user);
        if (row > 0) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:user:edit')")
    @PutMapping
    public ResponseResult update(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user))) {
            return ResponseResult.error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhone())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return ResponseResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return ResponseResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        int row = userService.updateUser(user);
        if (row > 0) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:user:remove')")
    @DeleteMapping("/{userIds}")
    public ResponseResult remove(@PathVariable List<Long> userIds) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userIds.contains(loginUser.getUserId())) {
            return ResponseResult.error("当前用户不能删除");
        }
        int row = userService.removeUser(userIds);
        if (row > 0) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 重置密码
     * @param user
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:user:resetPwd')")
    @PutMapping("/resetPwd")
    public ResponseResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        //密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean r = userService.updateById(user);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 修改状态
     * @param user
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:user:edit')")
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        boolean r = userService.updateById(user);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 根据用户编号获取授权角色
     * @param userId
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:user:query')")
    @GetMapping("/authRole/{userId}")
    public ResponseResult authRole(@PathVariable("userId") Long userId) {
        ResponseResult result = ResponseResult.success();
        SysUser user = userService.getById(userId);
        user.setCollege(collegeService.getById(user.getCollegeId()));
        List<SysRole> roles = roleService.selectRoleFlagByUserId(userId);
        result.put("user", user);
        result.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return result;
    }

    /**
     * 用户授权角色
     * @param userId
     * @param roleIds
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:user:edit')")
    @PutMapping("/authRole")
    public ResponseResult insertAuthRole(Long userId, List<Long> roleIds) {
        userService.saveUserRole(userId, roleIds);
        return ResponseResult.success();
    }

}
