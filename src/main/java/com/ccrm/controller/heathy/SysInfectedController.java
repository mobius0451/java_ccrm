package com.ccrm.controller.heathy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysInfected;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysInfectedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:39
 * @Description:
 */
@RestController
@RequestMapping("/healthy/infected")
public class SysInfectedController {

    @Autowired
    private ISysInfectedService infectedService;

    /**
     * 分页获取报备列表列表
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public ResponseResult list() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysInfected> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysInfected::getUserId, loginUser.getUserId());
        queryWrapper.orderByDesc(SysInfected::getCreateTime);
        List<SysInfected> list = infectedService.list(queryWrapper);
        return ResponseResult.success(list);
    }

    /**
     * 首页检测信息
     * @return
     */
    @GetMapping("/checkInfected")
    public ResponseResult checkInfected() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysInfected> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysInfected::getUserId, loginUser.getUserId());
        queryWrapper.orderByDesc(SysInfected::getCreateTime).last("limit 1");
        SysInfected infected = infectedService.getOne(queryWrapper);
        return ResponseResult.success(infected);
    }

    /**
     * 新增报备信息
     * @param infected
     * @return
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:add')")
    @PostMapping
    public ResponseResult save(@RequestBody SysInfected infected) {
        boolean r = infectedService.save(infected);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 修改报备信息
     * @param infected
     * @return
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:edit')")
    @PutMapping
    public ResponseResult update(@RequestBody SysInfected infected) {
        boolean r = infectedService.updateById(infected);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 删除报备信息
     * @param infectedIds
     * @return
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:remove')")
    @DeleteMapping("/{infectedIds}")
    public ResponseResult remove(@PathVariable List<Long> infectedIds) {
        boolean r = infectedService.removeByIds(infectedIds);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

}
