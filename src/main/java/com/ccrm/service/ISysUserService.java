package com.ccrm.service;

import com.ccrm.domain.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @CreateTime: 2022-11-11 22:03
 * @Description: 用户业务层
 */
public interface ISysUserService extends IService<SysUser> {


    /**
     * 校验用户名称是否唯一
     * @param user
     * @return
     */
    String checkUserNameUnique(SysUser user);

    /**
     * 校验手机号是否唯一
     * @param user
     * @return
     */
    String checkPhoneUnique(SysUser user);

    /**
     * 校验邮箱是否唯一
     * @param user
     * @return
     */
    String checkEmailUnique(SysUser user);

    /**
     * 校验用户是否允许操作
     * @param user
     */
    void checkUserAllowed(SysUser user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUser(SysUser user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int saveUser(SysUser user);

    /**
     * 修改用户信息
     * @param userIds
     * @return
     */
    int removeUser(List<Long> userIds);

    /**
     * 保存用户拥有角色
     * @param userId
     * @param roleIds
     * @return
     */
    int saveUserRole(Long userId, List<Long> roleIds);

    /**
     * 根据角色编号查询其包含的用户编号
     * @param roleId
     * @return
     */
    List<Long> selectAllocatedList(Long roleId);

    /**
     * 根据用户id查询拥有角色名
     * @param userId
     * @return
     */
    String selectUserRoleGroup(Long userId);
}
