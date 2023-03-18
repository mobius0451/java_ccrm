package com.ccrm.mapper;

import com.ccrm.domain.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * @CreateTime: 2022-11-07 14:38
 * @Description: 角色管理
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户ID查询角色列表
     * @param userId
     * @return
     */
    @Select("select * from sys_role where role_id in (select role_id from sys_user_role where user_id = #{userId})")
    List<SysRole> selectRoleByUserId(Long userId);

    /**
     * 删除用户拥有的角色
     * @param userId
     */
    @Delete("delete from sys_user_role where user_Id = #{userId}")
    int deleteUserRoleByUserId(Long userId);

    /**
     * 删除用户与角色的关系
     * @param userId
     */
    @Delete("delete from sys_user_role where user_Id = #{userId} and role_id = #{roleId}")
    int deleteAuthUser(Long userId, Long roleId);

    /**
     * 插入用户拥有的角色
     * @param userId
     * @param roleId
     */
    @Insert("insert into sys_user_role(user_id, role_id) values (#{userId}, #{roleId})")
    int insertUserRole(Long userId, Long roleId);
}
