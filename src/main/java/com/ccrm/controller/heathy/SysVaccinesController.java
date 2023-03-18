package com.ccrm.controller.heathy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysVaccines;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysVaccinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:39
 * @Description:
 */
@RestController
@RequestMapping("/healthy/vaccines")
public class SysVaccinesController {

    @Autowired
    private ISysVaccinesService vaccinesService;

    /**
     * 分页获取报备列表列表
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public ResponseResult list() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysVaccines> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysVaccines::getUserId, loginUser.getUserId());
        queryWrapper.orderByDesc(SysVaccines::getTime);
        List<SysVaccines> list = vaccinesService.list(queryWrapper);
        return ResponseResult.success(list);
    }

    /**
     * 首页检测信息
     * @return
     */
    @GetMapping("/checkVaccines")
    public ResponseResult checkVaccines() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysVaccines> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysVaccines::getUserId, loginUser.getUserId());
        List<SysVaccines> list = vaccinesService.list(queryWrapper);
        return ResponseResult.success(list);
    }

    /**
     * 新增报备信息
     * @param vaccines
     * @return
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:add')")
    @PostMapping
    public ResponseResult save(@RequestBody SysVaccines vaccines) {
        boolean r = vaccinesService.save(vaccines);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 修改报备信息
     * @param vaccines
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:notice:edit')")
    @PutMapping
    public ResponseResult update(@RequestBody SysVaccines vaccines) {
        boolean r = vaccinesService.updateById(vaccines);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 删除报备信息
     * @param vaccinesIds
     * @return
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:remove')")
    @DeleteMapping("/{vaccinesIds}")
    public ResponseResult remove(@PathVariable List<Long> vaccinesIds) {
        boolean r = vaccinesService.removeByIds(vaccinesIds);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

}
