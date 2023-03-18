package com.ccrm.service;

import com.ccrm.domain.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Set;

/**
 * @CreateTime: 2022-11-07 14:38
 * @Description: 用户管理
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRoleByUserId(Long userId);

    /**
     * 根据用户ID查询用户拥有并标记的角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRoleFlagByUserId(Long userId);

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    String checkRoleNameUnique(SysRole role);

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    String checkRoleKeyUnique(SysRole role);

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    int saveRole(SysRole role);

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    int updateRole(SysRole role);

    /**
     * 取消授权用户
     * @param userId
     * @param roleId
     * @return
     */
    int deleteAuthUser(Long userId, Long roleId);

    /**
     * 批量选择用户授权
     * @param roleId
     * @param userIds
     * @return
     */
    int insertAuthUsers(Long roleId, List<Long> userIds);

    /**
     * 批量删除角色根据角色编号
     * @param roleIds
     * @return
     */
    int removeRoleByIds(List<Long> roleIds);

    /**
     * 根据用户ID查询角色权限
     * @param userId
     * @return
     */
    Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * 检验角色是否允许操作
     * @param role
     */
    void checkRoleAllowed(SysRole role);
}
