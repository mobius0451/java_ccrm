package com.ccrm.service;

import com.ccrm.domain.TreeSelect;
import com.ccrm.domain.entity.SysMenu;
import com.ccrm.domain.vo.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Set;

/**
 * @CreateTime: 2022-11-11 22:20
 * @Description:
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 查询出所有菜单
     * 并把子菜单整合进父级菜单的children属性中
     * @return
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVo> buildMenus(List<SysMenu> menus);

    /**
     * 根据当前登陆者编号获取菜单列表
     * @param userId
     * @return
     */
    List<SysMenu> selectMenusByUserId(Long userId);

    /**
     * 构建前端所需要树结构
     * @param menus 菜单列表
     * @return 树结构列表
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * 构建前端所需要下拉树结构
     * @param menus
     * @return
     */
    List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * 根据角色编号查询选中菜单
     * @param roleId
     * @return
     */
    List<Long> selectCheckMenusByRoleId(Long roleId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    String checkMenuNameUnique(SysMenu menu);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    boolean hasChildByMenuId(Long menuId);

    /**
     * 查询菜单是否存在角色
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkMenuExistRole(Long menuId);

    /**
     * 根据角色编号查询菜单权限
     * @param roleId
     * @return
     */
    Set<String> selectMenuPermsByRoleId(Long roleId);

    /**
     * 根据用户编号查询菜单权限
     * @param userId
     * @return
     */
    Set<String> selectMenuPermsByUserId(Long userId);
}
