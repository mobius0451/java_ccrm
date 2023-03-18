package com.ccrm.service.impl;

import com.ccrm.common.UserConstants;
import com.ccrm.domain.entity.SysRole;
import com.ccrm.exception.CustomException;
import com.ccrm.mapper.SysMenuMapper;
import com.ccrm.mapper.SysRoleMapper;
import com.ccrm.service.ISysRoleService;
import com.ccrm.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @CreateTime: 2022-11-07 14:40
 * @Description:
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public List<SysRole> selectRoleByUserId(Long userId) {
        List<SysRole> roles = this.baseMapper.selectRoleByUserId(userId);
        return roles;
    }

    @Override
    public List<SysRole> selectRoleFlagByUserId(Long userId) {
        List<SysRole> userRoles = this.selectRoleByUserId(userId);
        List<SysRole> roles = this.list();
        for (SysRole role : roles) {
            for (SysRole userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public String checkRoleNameUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleName, role.getRoleName());
        SysRole info = this.baseMapper.selectOne(queryWrapper);
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkRoleKeyUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleKey, role.getRoleKey());
        SysRole info = this.baseMapper.selectOne(queryWrapper);
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional
    public int saveRole(SysRole role) {
        int i = this.baseMapper.insert(role);
        for (Long menuId : role.getMenuIds()) {
            menuMapper.insertRoleMenu(role.getRoleId(), menuId);
        }
        return i;
    }

    @Override
    @Transactional
    public int updateRole(SysRole role) {
        int i = this.baseMapper.updateById(role);
        menuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        for (Long menuId : role.getMenuIds()) {
            menuMapper.insertRoleMenu(role.getRoleId(), menuId);
        }
        return i;
    }

    @Override
    public int deleteAuthUser(Long userId, Long roleId) {
        return this.baseMapper.deleteAuthUser(userId, roleId);
    }

    @Override
    public int insertAuthUsers(Long roleId, List<Long> userIds) {
        int row = 0;
        for (Long userId : userIds) {
            row = row + this.baseMapper.insertUserRole(userId, roleId);
        }
        return row;
    }

    @Override
    public int removeRoleByIds(List<Long> roleIds) {
        int i = this.baseMapper.deleteBatchIds(roleIds);
        for (Long roleId : roleIds) {
            i = i + menuMapper.deleteRoleMenuByRoleId(roleId);
        }
        return i;
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = this.baseMapper.selectRoleByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public void checkRoleAllowed(SysRole role) {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
            throw new CustomException("不允许操作超级管理员角色");
        }
    }

}
