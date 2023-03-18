package com.ccrm.controller.access;

import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysCollege;
import com.ccrm.domain.entity.SysEnter;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysCollegeService;
import com.ccrm.service.ISysEnterService;
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
@RequestMapping("/access/enter")
public class SysEnterController {

    @Autowired
    private ISysEnterService enterService;

    @Autowired
    private ISysCollegeService collegeService;

    @Value("${ccrm.fileMapping}")
    private String fileMapping;

    /**
     * 分页获取通知入校申请
     */
    @PreAuthorize("@ccrm.hasPermi('access:enter:list')")
    @GetMapping("/list")
    public ResponseResult list(int pageNum, int pageSize, SysEnter enter) {
        Page<SysEnter> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysEnter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(enter.getEnterType()), SysEnter::getEnterType, enter.getEnterType());
        queryWrapper.eq(StringUtils.isNotEmpty(enter.getStatus()), SysEnter::getStatus, enter.getStatus());
        queryWrapper.ge(StringUtils.isNotEmpty(enter.getParams()), SysEnter::getCreateTime, enter.getParams().get("beginTime"));
        queryWrapper.le(StringUtils.isNotEmpty(enter.getParams()), SysEnter::getCreateTime, enter.getParams().get("endTime"));
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
                queryWrapper.in(StringUtils.isNotEmpty(collegeIds), SysEnter::getUserCollegeId, collegeIds);
            }
            else {
                queryWrapper.eq(SysEnter::getUserId, loginUser.getUserId());
            }
        }
        queryWrapper.orderByDesc(SysEnter::getCreateTime);
        enterService.page(page, queryWrapper);
        return ResponseResult.success(page);
    }

    /**
     * 根据入校申请编号获取详细信息
     * @param enterId
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('access:enter:query')")
    @GetMapping("/{enterId}")
    public ResponseResult getInfo(@PathVariable Long enterId) {
        return ResponseResult.success(enterService.getById(enterId));
    }

    /**
     * 申请入校
     * @param enter
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('access:enter:add')")
    @PostMapping
    public ResponseResult save(@RequestBody SysEnter enter) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        enter.setUserId(loginUser.getUserId());
        enter.setUserCollegeId(loginUser.getCollegeId());
        enter.setHealthyCode(fileMapping + loginUser.getUsername() + "/" + enter.getHealthyCode());
        enter.setTripCode(fileMapping + loginUser.getUsername() + "/" + enter.getTripCode());
        enter.setNucleicAcid(fileMapping + loginUser.getUsername() + "/" + enter.getNucleicAcid());
        boolean r = enterService.save(enter);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 修改入校申请信息
     * @param enter
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('access:enter:edit')")
    @PutMapping
    public ResponseResult update(@RequestBody SysEnter enter) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        enter.setHealthyCode(fileMapping + loginUser.getUsername() + "/" + enter.getHealthyCode());
        enter.setTripCode(fileMapping + loginUser.getUsername() + "/" + enter.getTripCode());
        enter.setNucleicAcid(fileMapping + loginUser.getUsername() + "/" + enter.getNucleicAcid());
        boolean r = enterService.updateById(enter);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 删除申请记录
     * @param enterIds
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('access:enter:remove')")
    @DeleteMapping("/{enterIds}")
    public ResponseResult remove(@PathVariable List<Long> enterIds) {
        boolean r = enterService.removeByIds(enterIds);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

}
