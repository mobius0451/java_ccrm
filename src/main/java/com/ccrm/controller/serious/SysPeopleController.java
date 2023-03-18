package com.ccrm.controller.serious;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysSeriousInfo;
import com.ccrm.service.ISysSeriousInfoService;
import com.ccrm.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @CreateTime: 2023-02-14 22:05
 * @Description:
 */
@RestController
@RequestMapping("/serious/people")
public class SysPeopleController {

    @Autowired
    private ISysSeriousInfoService seriousInfoService;

    @GetMapping("/list")
    public ResponseResult list(int pageNum, int pageSize, String tabActiveName) {
        Page<SysSeriousInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysSeriousInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysSeriousInfo::getUserId);
        if (tabActiveName.equals("infected")) {
            queryWrapper.eq(SysSeriousInfo::getIsInfected, 1)
                    .and(qw -> qw.eq(SysSeriousInfo::getAge, 1)
                            .or().eq(SysSeriousInfo::getVaccines, 0)
                            .or().eq(SysSeriousInfo::getDiscomfort, 1)
                            .or().eq(SysSeriousInfo::getWithDisease, 1));
        } else if (tabActiveName.equals("noInfected")) {
            queryWrapper.eq(SysSeriousInfo::getIsInfected, 0)
                    .and(qw -> qw.eq(SysSeriousInfo::getAge, 1)
                            .or().eq(SysSeriousInfo::getVaccines, 0)
                            .or().eq(SysSeriousInfo::getDiscomfort, 1)
                            .or().eq(SysSeriousInfo::getWithDisease, 1));
        } else if (tabActiveName.equals("notSerious")) {
            queryWrapper.eq(SysSeriousInfo::getAge, 0);
            queryWrapper.eq(SysSeriousInfo::getVaccines, 1);
            queryWrapper.eq(SysSeriousInfo::getDiscomfort, 0);
            queryWrapper.eq(SysSeriousInfo::getWithDisease, 0);
        }
        seriousInfoService.page(page, queryWrapper);
        return ResponseResult.success(page);
    }
}
