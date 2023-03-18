package com.ccrm.controller.system;

import com.ccrm.common.UserConstants;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysMenu;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysMenuService;
import com.ccrm.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @CreateTime: 2022-11-14 15:15
 * @Description: 菜单信息
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取所有菜单
     * @param menu
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:menu:list')")
    @GetMapping("/list")
    public ResponseResult list(SysMenu menu) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(menu.getMenuName()), SysMenu::getMenuName, menu.getMenuName());
        queryWrapper.eq(StringUtils.isNotEmpty(menu.getStatus()), SysMenu::getStatus, menu.getStatus());
        queryWrapper.orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menus = menuService.list(queryWrapper);
        return ResponseResult.success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     * @param menuId
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public ResponseResult getInfo(@PathVariable Long menuId) {
        return ResponseResult.success(menuService.getById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     * @return
     */
    @GetMapping("/treeSelect")
    public ResponseResult treeSelect() {
        //根据等前用户获取菜单列表
        List<SysMenu> menus = menuService.selectMenusByUserId(1L);
        return ResponseResult.success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     * @param roleId
     * @return
     */
    @GetMapping(value = "/roleMenuTreeSelect/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable Long roleId) {
        //根据等前用户获取菜单列表
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SysMenu> menus = menuService.selectMenusByUserId(loginUser.getUserId());
        ResponseResult result = ResponseResult.success();
        result.put("checkedKeys", menuService.selectCheckMenusByRoleId(roleId));
        result.put("menus", menuService.buildMenuTreeSelect(menus));
        return result;
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:menu:add')")
    @PostMapping
    public ResponseResult save(@RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return ResponseResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return ResponseResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        boolean r = menuService.save(menu);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:menu:edit')")
    @PutMapping
    public ResponseResult update(@RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return ResponseResult.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return ResponseResult.error("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        else if (menu.getMenuId().equals(menu.getParentId())) {
            return ResponseResult.error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        boolean r = menuService.updateById(menu);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:menu:remove')")
    @DeleteMapping("/{menuId}")
    public ResponseResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return ResponseResult.warn("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return ResponseResult.warn("菜单已分配,不允许删除");
        }
        boolean r = menuService.removeById(menuId);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }
}
