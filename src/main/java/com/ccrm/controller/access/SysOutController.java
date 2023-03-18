package com.ccrm.controller.access;

import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysCollege;
import com.ccrm.domain.entity.SysOut;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysCollegeService;
import com.ccrm.service.ISysOutService;
import com.ccrm.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:37
 * @Description:
 */
@RestController
@RequestMapping("/access/out")
public class SysOutController {

    @Autowired
    private ISysOutService outService;

    @Autowired
    private ISysCollegeService collegeService;

    @Value("${ccrm.fileMapping}")
    private String fileMapping;

    /**
     * 分页获取通知出校申请
     */
    @PreAuthorize("@ccrm.hasPermi('access:out:list')")
    @GetMapping("/list")
    public ResponseResult list(int pageNum, int pageSize, SysOut out) {
        Page<SysOut> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysOut> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(out.getOutType()), SysOut::getOutType, out.getOutType());
        queryWrapper.eq(StringUtils.isNotEmpty(out.getStatus()), SysOut::getStatus, out.getStatus());
        queryWrapper.ge(StringUtils.isNotEmpty(out.getParams()), SysOut::getCreateTime, out.getParams().get("beginTime"));
        queryWrapper.le(StringUtils.isNotEmpty(out.getParams()), SysOut::getCreateTime, out.getParams().get("endTime"));
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUser.getCollegeId() != null) {
            LambdaQueryWrapper<SysCollege> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(SysCollege::getCollegeId);
            wrapper.like(SysCollege::getAncestors, loginUser.getCollegeId());
            List<SysCollege> list = collegeService.list(wrapper);
            List<Long> collegeIds = new ArrayList<>();
            collegeIds.add(loginUser.getCollegeId());
            if (StringUtils.isNotEmpty(list)) {
                for (SysCollege college : list) {
                    collegeIds.add(college.getCollegeId());
                }
                queryWrapper.in(StringUtils.isNotEmpty(collegeIds), SysOut::getUserCollegeId, collegeIds);
            }
            else {
                queryWrapper.eq(SysOut::getUserId, loginUser.getUserId());
            }
        }
        queryWrapper.orderByDesc(SysOut::getCreateTime);
        outService.page(page, queryWrapper);
        return ResponseResult.success(page);
    }

    /**
     * 根据出校申请编号获取详细信息
     * @param outId
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('access:out:query')")
    @GetMapping("/{outId}")
    public ResponseResult getInfo(@PathVariable Long outId) {
        return ResponseResult.success(outService.getById(outId));
    }

    /**
     * 申请出校
     * @param out
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('access:out:add')")
    @PostMapping
    public ResponseResult save(@RequestBody SysOut out) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        out.setUserId(loginUser.getUserId());
        out.setUserCollegeId(loginUser.getCollegeId());
        out.setHealthyCode(fileMapping + loginUser.getUsername() + "/" + out.getHealthyCode());
        boolean r = outService.save(out);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 修改出校申请信息
     * @param out
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('access:out:edit')")
    @PutMapping
    public ResponseResult update(@RequestBody SysOut out) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        out.setHealthyCode(fileMapping + loginUser.getUsername() + "/" + out.getHealthyCode());
        boolean r = outService.updateById(out);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 删除申请记录
     * @param outIds
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('access:out:remove')")
    @DeleteMapping("/{outIds}")
    public ResponseResult remove(@PathVariable List<Long> outIds) {
        boolean r = outService.removeByIds(outIds);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

}
