package com.ccrm.controller.system;

import com.ccrm.common.UserConstants;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysCollege;
import com.ccrm.service.ISysCollegeService;
import com.ccrm.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @CreateTime: 2022-11-14 15:39
 * @Description: 学院班级信息
 */
@RestController
@RequestMapping("/system/college")
public class SysCollegeController {

    @Autowired
    private ISysCollegeService collegeService;

    /**
     * 获取所属学院班级
     * @param college
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:college:list')")
    @GetMapping("/list")
    public ResponseResult list(SysCollege college) {
        LambdaQueryWrapper<SysCollege> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(college.getCollegeName()), SysCollege::getCollegeName, college.getCollegeName());
        queryWrapper.eq(StringUtils.isNotEmpty(college.getStatus()), SysCollege::getStatus, college.getStatus());
        queryWrapper.eq(college.getCollegeId() != null, SysCollege::getCollegeId, college.getCollegeId());
        queryWrapper.eq(college.getParentId() != null, SysCollege::getParentId, college.getParentId());
        queryWrapper.orderByAsc(SysCollege::getOrderNum);
        List<SysCollege> colleges = collegeService.list(queryWrapper);
        return ResponseResult.success(colleges);
    }

    /**
     * 查询学院班级列表（排除节点）
     * @param collegeId
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:college:list')")
    @GetMapping("/list/exclude/{collegeId}")
    public ResponseResult excludeChild(@PathVariable(value = "collegeId", required = false) Long collegeId) {
        List<SysCollege> colleges = collegeService.list();
        colleges.removeIf(c -> c.getCollegeId().intValue() == collegeId || ArrayUtils.contains(StringUtils.split(c.getAncestors(), ","), collegeId + ""));
        return ResponseResult.success(colleges);
    }

    /**
     * 根据部门编号获取详细信息
     * @param collegeId
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:college:query')")
    @GetMapping(value = "/{collegeId}")
    public ResponseResult getInfo(@PathVariable Long collegeId) {
        return ResponseResult.success(collegeService.getById(collegeId));
    }

    /**
     * 新增部门
     * @param college
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:college:add')")
    @PostMapping
    public ResponseResult save(@RequestBody SysCollege college) {
        if (UserConstants.NOT_UNIQUE.equals(collegeService.checkCollegeNameUnique(college))) {
            return ResponseResult.error("新增部门'" + college.getCollegeName() + "'失败，部门名称已存在");
        }
        int r = collegeService.saveCollege(college);
        if (r > 0) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 修改部门
     */
    @PreAuthorize("@ccrm.hasPermi('system:college:edit')")
    @PutMapping
    public ResponseResult update(@RequestBody SysCollege college) {
        if (UserConstants.NOT_UNIQUE.equals(collegeService.checkCollegeNameUnique(college))) {
            return ResponseResult.error("修改部门'" + college.getCollegeName() + "'失败，部门名称已存在");
        }
        else if (college.getParentId().equals(college.getCollegeId())) {
            return ResponseResult.error("修改部门'" + college.getCollegeName() + "'失败，上级部门不能是自己");
        }
        else if (StringUtils.equals(UserConstants.COLLEGE_DISABLE, college.getStatus()) && collegeService.selectNormalChildrenCollegeById(college.getCollegeId()) > 0)
        {
            return ResponseResult.error("该部门包含未停用的子部门！");
        }
        int r = collegeService.updateCollege(college);
        if (r > 0) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 删除部门
     */
    @PreAuthorize("@ccrm.hasPermi('system:college:remove')")
    @DeleteMapping("/{collegeId}")
    public ResponseResult remove(@PathVariable Long collegeId) {
        if (collegeService.hasChildByCollegeId(collegeId)) {
            return ResponseResult.warn("存在下级部门,不允许删除");
        }
        if (collegeService.checkCollegeExistUser(collegeId)) {
            return ResponseResult.warn("部门存在用户,不允许删除");
        }
        boolean r = collegeService.removeById(collegeId);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

}
