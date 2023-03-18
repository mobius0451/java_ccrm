package com.ccrm.controller.serious;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysInfected;
import com.ccrm.domain.entity.SysSeriousInfo;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysInfectedService;
import com.ccrm.service.ISysSeriousInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:40
 * @Description:
 */
@RestController
@RequestMapping("/serious/info")
public class SysSeriousInfoController {

    @Autowired
    private ISysSeriousInfoService seriousInfoService;

    @Autowired
    private ISysInfectedService infectedService;

    /**
     * 分页获取重症列表列表
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public ResponseResult list(int pageNum, int pageSize) {
        Page<SysSeriousInfo> page = new Page<>(pageNum, pageSize);
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysSeriousInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysSeriousInfo::getUserId, loginUser.getUserId());
        seriousInfoService.page(page, queryWrapper);
        return ResponseResult.success(page);
    }

    @GetMapping
    public ResponseResult getInfo() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysSeriousInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysSeriousInfo::getUserId, loginUser.getUserId());
        SysSeriousInfo info = seriousInfoService.getOne(queryWrapper);
        return ResponseResult.success(info);
    }

    /**
     * 新增
     * @param seriousInfo
     * @return
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:add')")
    @PostMapping
    public ResponseResult save(@RequestBody SysSeriousInfo seriousInfo) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //        查询用户感染信息
        LambdaQueryWrapper<SysInfected> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysInfected::getUserId, loginUser.getUserId());
        queryWrapper.orderByDesc(SysInfected::getCreateTime);
        queryWrapper.last("limit 1");
        SysInfected infectedInfo = infectedService.getOne(queryWrapper);

        if (null != infectedInfo) {
            seriousInfo.setIsInfected(infectedInfo.getStatus());
        }
        seriousInfo.setUserId(loginUser.getUserId());

        boolean r = seriousInfoService.save(seriousInfo);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 修改
     * @param seriousInfo
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:notice:edit')")
    @PutMapping
    public ResponseResult update(@RequestBody SysSeriousInfo seriousInfo) {
        boolean r = seriousInfoService.updateById(seriousInfo);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 删除
     * @param infoIds
     * @return
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:remove')")
    @DeleteMapping("/{infoIds}")
    public ResponseResult remove(@PathVariable List<Long> infoIds) {
        boolean r = seriousInfoService.removeByIds(infoIds);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

}
