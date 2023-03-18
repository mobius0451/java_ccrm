package com.ccrm.controller.heathy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysReport;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysInfectedService;
import com.ccrm.service.ISysReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:38
 * @Description:
 */
@RestController
@RequestMapping("/healthy/report")
public class SysReportController {

    @Autowired
    private ISysReportService reportService;

    @Autowired
    private ISysInfectedService infectedService;

    /**
     * 分页获取报备列表列表
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public ResponseResult list(int pageNum, int pageSize) {
        Page<SysReport> page = new Page<>(pageNum, pageSize);
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysReport::getUserId, loginUser.getUserId());
        queryWrapper.orderByDesc(SysReport::getCreateTime);
        reportService.page(page, queryWrapper);
        return ResponseResult.success(page);
    }


    /**
     * 查询今日是否报备
     * @return
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:query')")
    @GetMapping("/todayReport")
    public ResponseResult todayReport() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysReport::getUserId, loginUser.getUserId());
        queryWrapper.like(SysReport::getCreateTime, LocalDate.now());
        SysReport today = reportService.getOne(queryWrapper);
        if (null != today) {
            return ResponseResult.success(today);
        }
        return ResponseResult.success(0);
    }

    /**
     * 新增报备信息
     * @param report
     * @return
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:add')")
    @PostMapping
    public ResponseResult save(@RequestBody SysReport report) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysReport::getUserId, loginUser.getUserId());
        queryWrapper.like(SysReport::getCreateTime, LocalDate.now());
        SysReport today = reportService.getOne(queryWrapper);
        if (null == today) {
            report.setUserId(loginUser.getUserId());
            report.setReportTime(LocalDate.now());
            boolean r = reportService.save(report);
            String res = infectedService.editInfected(report);
            if (r) {
                return ResponseResult.success(res);
            } else {
                return ResponseResult.error();
            }
        } else {
            report.setReportId(today.getReportId());
            report.setUserId(today.getUserId());
            boolean r = reportService.updateById(report);
            String res = infectedService.editInfected(report);
            if (r) {
                return ResponseResult.success(res);
            } else {
                return ResponseResult.error();
            }
        }
    }


    /**
     * 删除报备信息
     * @param reportIds
     * @return
     */
//    @PreAuthorize("@ccrm.hasPermi('system:notice:remove')")
    @DeleteMapping("/{reportIds}")
    public ResponseResult remove(@PathVariable List<Long> reportIds) {
        boolean r = reportService.removeByIds(reportIds);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

}
