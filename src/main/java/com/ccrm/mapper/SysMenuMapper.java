package com.ccrm.mapper;

import com.ccrm.domain.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @CreateTime: 2022-11-11 22:21
 * @Description:
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据角色编号查询菜单
     * @param roleId
     * @return
     */
    @Select("select * from sys_menu where menu_id in (" +
            "select menu_id from sys_role_menu where role_id = #{roleId})")
    List<SysMenu> selectMenusByRoleId(Long roleId);

    /**
     * 根据角色编号查询菜单
     * @param userId
     * @return
     */
    @Select("select * from sys_menu where menu_id in (" +
            "select menu_id from sys_role_menu where role_id in (" +
            "select role_id from sys_user_role where user_id = #{userId}))")
    List<SysMenu> selectMenusByUserId(Long userId);

    /**
     * 查询菜单使用数量
     * @param menuId
     */
    @Select("select count(1) from sys_role_menu where menu_id = #{menuId}")
    int checkMenuExistRole(Long menuId);

    /**
     * 插入角色拥有的菜单
     * @param roleId
     * @param menuId
     * @return
     */
    @Insert("insert into sys_role_menu values (#{roleId}, #{menuId})")
    int insertRoleMenu(Long roleId, Long menuId);

    /**
     * 删除角色拥有的菜单
     * @param roleId
     */
    @Delete("delete from sys_role_menu where role_Id = #{roleId}")
    int deleteRoleMenuByRoleId(@PathVariable Long roleId);
}
