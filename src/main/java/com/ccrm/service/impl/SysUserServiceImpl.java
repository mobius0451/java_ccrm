package com.ccrm.service.impl;

import com.ccrm.common.UserConstants;
import com.ccrm.domain.entity.SysRole;
import com.ccrm.domain.entity.SysUser;
import com.ccrm.exception.CustomException;
import com.ccrm.mapper.SysRoleMapper;
import com.ccrm.mapper.SysUserMapper;
import com.ccrm.service.ISysUserService;
import com.ccrm.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @CreateTime: 2022-11-11 22:09
 * @Description:
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public String checkUserNameUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, user.getUserName());
        SysUser info = this.baseMapper.selectOne(queryWrapper);
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getPhone, user.getPhone());
        SysUser info = this.baseMapper.selectOne(queryWrapper);
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getEmail, user.getEmail());
        SysUser info = this.baseMapper.selectOne(queryWrapper);
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new CustomException("不允许操作超级管理员用户");
        }
    }

    @Override
    @Transactional
    public int updateUser(SysUser user) {
        int i = this.baseMapper.updateById(user);
        this.saveUserRole(user.getUserId(), user.getRoleIds());
        return i;
    }

    @Override
    @Transactional
    public int saveUser(SysUser user) {
        int i = this.baseMapper.insert(user);
        if (StringUtils.isNotEmpty(user.getRoleIds())) {
            for (Long roleId : user.getRoleIds()) {
                roleMapper.insertUserRole(user.getUserId(), roleId);
            }
        }
        return i;
    }

    @Override
    @Transactional
    public int removeUser(List<Long> userIds) {
        for (Long userId : userIds) {
            roleMapper.deleteUserRoleByUserId(userId);
        }
        int i = this.baseMapper.deleteBatchIds(userIds);
        return i;
    }

    @Override
    public int saveUserRole(Long userId, List<Long> roleIds) {
        //删除用户原本拥有的角色
        roleMapper.deleteUserRoleByUserId(userId);
        //插入修改后用户拥有的角色
        if (StringUtils.isNotEmpty(roleIds)) {
            for (Long roleId : roleIds) {
                roleMapper.insertUserRole(userId, roleId);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public List<Long> selectAllocatedList(Long roleId) {
        return this.baseMapper.selectAllocatedList(roleId);
    }

    @Override
    public String selectUserRoleGroup(Long userId) {
        List<SysRole> list = roleMapper.selectRoleByUserId(userId);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }


}
