package com.ccrm.controller.statistics;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.*;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.domain.model.VaccinesNumInfo;
import com.ccrm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @CreateTime: 2023-02-26 21:59
 * @Description:
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private ISysReportService reportService;

    @Autowired
    private ISysInfectedService infectedService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysVaccinesService vaccinesService;

    @Autowired
    private ISysSeriousInfoService seriousInfoService;

    @Autowired
    private ISysOutService outService;

    @Autowired
    private ISysEnterService enterService;

    @GetMapping("/temperatureLine")
    public ResponseResult temperatureLine() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysReport::getUserId, loginUser.getUserId());
        queryWrapper.orderByDesc(SysReport::getCreateTime);
        queryWrapper.last("limit 7");
        List<SysReport> list = reportService.list(queryWrapper);
        return ResponseResult.success(list);
    }

    @GetMapping("/locationPie")
    public ResponseResult locationPie() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        QueryWrapper<SysReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", loginUser.getUserId());
        queryWrapper.select("count(*) as num, location");
        queryWrapper.groupBy("location");
        List<Map<String, Object>> list = reportService.listMaps(queryWrapper);
        return ResponseResult.success(list);
    }

    @GetMapping("/infectedSpanBar")
    public ResponseResult infectedSpanBar() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysInfected> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysInfected::getUserId, loginUser.getUserId());
        queryWrapper.orderByAsc(SysInfected::getCreateTime);
        queryWrapper.last("limit 7");
        List<SysInfected> list = infectedService.list(queryWrapper);
        return ResponseResult.success(list);
    }

    @GetMapping("/numOutEnterBar")
    public ResponseResult numOutEnterBar() {
        List<Integer> list = new ArrayList<>();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysOut> outQueryWrapper = new LambdaQueryWrapper<>();
        outQueryWrapper.eq(SysOut::getUserId, loginUser.getUserId());
        outQueryWrapper.like(SysOut::getBeginTime, LocalDate.now());
        int numOut = outService.count(outQueryWrapper);
        list.add(numOut);
        LambdaQueryWrapper<SysEnter> enterQueryWrapper = new LambdaQueryWrapper<>();
        enterQueryWrapper.eq(SysEnter::getUserId, loginUser.getUserId());
        enterQueryWrapper.like(SysEnter::getBeginTime, LocalDate.now());
        int numEnter = enterService.count(enterQueryWrapper);
        list.add(numEnter);
        return ResponseResult.success(list);
    }

    @GetMapping("/reportInfoBar")
    public ResponseResult reportInfoBar() {
        int userCount = userService.count();
        LambdaQueryWrapper<SysReport> reportWrapper = new LambdaQueryWrapper<>();
        reportWrapper.like(SysReport::getCreateTime, LocalDate.now());
        int todayReportCount = reportService.count(reportWrapper);
        Map<String, Integer> map = new HashMap<>();
        map.put("total", userCount);
        map.put("todayReport", todayReportCount);
        return ResponseResult.success(map);
    }

    @GetMapping("/vaccinesNumPie")
    public ResponseResult vaccinesNumPie() {
        List<VaccinesNumInfo> vaccinesNumInfos = vaccinesService.getVaccinesNum();
        int userCount = userService.count();
        int s = 0;
        for (VaccinesNumInfo vaccinesNumInfo : vaccinesNumInfos) {
            s += vaccinesNumInfo.getNumPerson();
        }
        if (userCount - s != 0) {
            VaccinesNumInfo vaccinesNumInfo = new VaccinesNumInfo();
            vaccinesNumInfo.setNumVaccines(0);
            vaccinesNumInfo.setNumPerson(userCount - s);
            vaccinesNumInfos.add(vaccinesNumInfo);
        }
        return ResponseResult.success(vaccinesNumInfos);
    }

    @GetMapping("/infectedNumLine")
    public ResponseResult infectedNumLine() {
        QueryWrapper<SysInfected> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*) as num, infected_time");
        queryWrapper.groupBy("infected_time");
        List<Map<String, Object>> list = infectedService.listMaps(queryWrapper);
        return ResponseResult.success(list);
    }

    @GetMapping("/eachSeriousBar")
    public ResponseResult eachSeriousBar() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= 3; i++) {
            if (i == 0) {
                LambdaQueryWrapper<SysSeriousInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysSeriousInfo::getAge, 1);
                int count = seriousInfoService.count(queryWrapper);
                map.put("age", count);
            } else if (i == 1) {
                LambdaQueryWrapper<SysSeriousInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysSeriousInfo::getVaccines, 0);
                int count = seriousInfoService.count(queryWrapper);
                map.put("vaccines", count);
            } else if (i == 2) {
                LambdaQueryWrapper<SysSeriousInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysSeriousInfo::getWithDisease, 1);
                int count = seriousInfoService.count(queryWrapper);
                map.put("withDisease", count);
            } else if (i == 3) {
                LambdaQueryWrapper<SysSeriousInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysSeriousInfo::getDiscomfort, 1);
                int count = seriousInfoService.count(queryWrapper);
                map.put("discomfort", count);
            } else {
                break;
            }
        }
        return ResponseResult.success(map);
    }

    @GetMapping("/eachSeriousPie")
    public ResponseResult eachSeriousPie() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= 2; i++) {
            if (i == 0) {
                LambdaQueryWrapper<SysSeriousInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysSeriousInfo::getIsInfected, 1)
                        .and(qw -> qw.eq(SysSeriousInfo::getAge, 1)
                                .or().eq(SysSeriousInfo::getVaccines, 0)
                                .or().eq(SysSeriousInfo::getDiscomfort, 1)
                                .or().eq(SysSeriousInfo::getWithDisease, 1));
                int count = seriousInfoService.count(queryWrapper);
                map.put("infected", count);
            } else if (i == 1) {
                LambdaQueryWrapper<SysSeriousInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysSeriousInfo::getIsInfected, 0)
                        .and(qw -> qw.eq(SysSeriousInfo::getAge, 1)
                                .or().eq(SysSeriousInfo::getVaccines, 0)
                                .or().eq(SysSeriousInfo::getDiscomfort, 1)
                                .or().eq(SysSeriousInfo::getWithDisease, 1));
                int count = seriousInfoService.count(queryWrapper);
                map.put("noInfected", count);
            } else if (i == 2) {
                LambdaQueryWrapper<SysSeriousInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysSeriousInfo::getAge, 0);
                queryWrapper.eq(SysSeriousInfo::getVaccines, 1);
                queryWrapper.eq(SysSeriousInfo::getDiscomfort, 0);
                queryWrapper.eq(SysSeriousInfo::getWithDisease, 0);
                int count = seriousInfoService.count(queryWrapper);
                map.put("notSerious", count);
            } else {
                break;
            }
        }
        return ResponseResult.success(map);
    }

    @GetMapping("/dayOutEnterLine")
    public ResponseResult dayOutEnterLine() {
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = null;
        dates.add(String.valueOf(LocalDate.now()));
        for (int i=0;i<6;i++){
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, - i-1);
            dates.add(dateFormat.format(calendar.getTime()));
        }
        List<Integer> outValue = dates.stream().map(item -> outService.countOutByDate(item))
                .collect(Collectors.toList());
        List<Integer> enterValue = dates.stream().map(item -> enterService.countEnterByDate(item))
                .collect(Collectors.toList());
        List<List> result = new ArrayList<>();
        result.add(dates);
        result.add(outValue);
        result.add(enterValue);
        return ResponseResult.success(result);
    }

    @GetMapping("/typeOutEnterRadar")
    public ResponseResult typeOutEnterRadar() {
        QueryWrapper<SysOut> outWrapper = new QueryWrapper<>();
        outWrapper.select("count(*) as num, out_type");
        outWrapper.groupBy("out_type");
        List<Map<String, Object>> outResult = outService.listMaps(outWrapper);
        QueryWrapper<SysEnter> enterWrapper = new QueryWrapper<>();
        enterWrapper.select("count(*) as num, enter_type");
        enterWrapper.groupBy("enter_type");
        List<Map<String, Object>> enterResult = enterService.listMaps(enterWrapper);
        List<List> list = new ArrayList<>();
        list.add(outResult);
        list.add(enterResult);
        return ResponseResult.success(list);
    }



}
