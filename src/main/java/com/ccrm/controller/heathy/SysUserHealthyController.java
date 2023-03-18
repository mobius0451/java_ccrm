package com.ccrm.controller.heathy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.*;
import com.ccrm.domain.model.UserHealthy;
import com.ccrm.service.*;
import com.ccrm.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @CreateTime: 2023-02-22 14:49
 * @Description:
 */
@RestController
@RequestMapping("/healthy/userHealthy")
public class SysUserHealthyController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysCollegeService collegeService;

    @Autowired
    private ISysReportService reportService;

    @Autowired
    private ISysInfectedService infectedService;

    @Autowired
    private ISysVaccinesService vaccinesService;

    /**
     * 获取用户健康信息
     * @param pageNum
     * @param pageSize
     * @param user
     * @param tabActiveName
     * @return
     */
//    @PreAuthorize("@ccrm.hasPermi('system:user:list')")
    @GetMapping("/list")
    public ResponseResult list(int pageNum, int pageSize, SysUser user, String tabActiveName) {
        //构建分页条件
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (user.getCollegeId() != null) {
            LambdaQueryWrapper<SysCollege> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(SysCollege::getCollegeId);
            wrapper.like(SysCollege::getAncestors, user.getCollegeId());
            List<SysCollege> list = collegeService.list(wrapper);
            List<Long> collegeIds = new ArrayList<>();
            if (StringUtils.isNotEmpty(list)) {
                for (SysCollege college : list) {
                    collegeIds.add(college.getCollegeId());
                }
                collegeIds.add(user.getCollegeId());
                queryWrapper.in(StringUtils.isNotEmpty(collegeIds), SysUser::getCollegeId, collegeIds);
            }
            else {
                queryWrapper.eq(SysUser::getCollegeId, user.getCollegeId());
            }
        }
        if (tabActiveName.equals("noReport")) {
            LambdaQueryWrapper<SysReport> reportWrapper = new LambdaQueryWrapper<>();
            reportWrapper.select(SysReport::getUserId);
            reportWrapper.like(SysReport::getCreateTime, LocalDate.now());
            List<SysReport> reports = reportService.list(reportWrapper);
            ArrayList<Long> userIds = new ArrayList<>();
            if (StringUtils.isNotEmpty(reports)) {
                for (SysReport report : reports) {
                    userIds.add(report.getUserId());
                }
                queryWrapper.notIn(StringUtils.isNotEmpty(userIds), SysUser::getUserId, userIds);
            }

        }
        if (tabActiveName.equals("infected")) {
            List<SysInfected> infecteds = infectedService.listUserInfected();
            List<Long> userIds = new ArrayList<>();
            if (StringUtils.isNotEmpty(infecteds)) {
                for (SysInfected infected : infecteds) {
                    if (infected.getStatus() == 0) {
                        userIds.add(infected.getUserId());
                    }
                }
                if (StringUtils.isNotEmpty(userIds)) {
                    queryWrapper.in(SysUser::getUserId, userIds);
                } else {
                    return ResponseResult.success();
                }
            }
        }
        if (tabActiveName.equals("noVaccines")) {
            List<Long> userIds = vaccinesService.getVaccinesUserIds();
            queryWrapper.notIn(StringUtils.isNotEmpty(userIds), SysUser::getUserId, userIds);
        }
        userService.page(page, queryWrapper);
        List<SysUser> records = page.getRecords();
        List<UserHealthy> list = records.stream().map((item) -> {
            //根据班级id查询班级信息并存入用户信息
            SysCollege college = collegeService.getById(item.getCollegeId());
            item.setCollege(college);
            UserHealthy userHealthy = new UserHealthy();
            userHealthy.setUser(item);

            LambdaQueryWrapper<SysInfected> infectedWrapper = new LambdaQueryWrapper<>();
            infectedWrapper.eq(SysInfected::getUserId, item.getUserId());
            infectedWrapper.orderByDesc(SysInfected::getCreateTime);
            infectedWrapper.last("limit 1");
            userHealthy.setInfected(infectedService.getOne(infectedWrapper));

            LambdaQueryWrapper<SysVaccines> vaccinesWrapper = new LambdaQueryWrapper<>();
            vaccinesWrapper.eq(SysVaccines::getUserId, item.getUserId());
            vaccinesWrapper.orderByDesc(SysVaccines::getTime);
            userHealthy.setVaccines(vaccinesService.list(vaccinesWrapper));

            return userHealthy;
        }).collect(Collectors.toList());
        Page<UserHealthy> healthyPage = new Page<>();
        BeanUtils.copyProperties(page, healthyPage, "records");

        healthyPage.setRecords(list);
        return ResponseResult.success(healthyPage);
    }
}
