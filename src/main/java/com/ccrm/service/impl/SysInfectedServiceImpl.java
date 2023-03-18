package com.ccrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccrm.domain.entity.SysInfected;
import com.ccrm.domain.entity.SysReport;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.mapper.SysInfectedMapper;
import com.ccrm.service.ISysInfectedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:28
 * @Description:
 */
@Service
public class SysInfectedServiceImpl extends ServiceImpl<SysInfectedMapper, SysInfected> implements ISysInfectedService {


    @Override
    public String editInfected(SysReport report) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<SysInfected> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysInfected::getUserId, loginUser.getUserId());
        queryWrapper.eq(SysInfected::getStatus, 0);
        SysInfected infected = this.getOne(queryWrapper);
        if (null != infected) {
            if (infected.getMaxTemperature() < report.getTemperature()) {
                infected.setMaxTemperature(report.getTemperature());
            }
            infected.setInfectedSpan(infected.getInfectedSpan() + 1);
            if (report.getTesting() == 0) {
                infected.setRecoveryTime(report.getReportTime());
                infected.setStatus(1);
            }
            this.updateById(infected);
            return "UPDATE";
        } else {
            if (report.getTesting() == 1) {
                SysInfected newInfected = new SysInfected();
                newInfected.setInfectedTime(report.getReportTime());
                newInfected.setUserId(loginUser.getUserId());
                newInfected.setStatus(0);
                newInfected.setMaxTemperature(report.getTemperature());
                this.save(newInfected);
                return "SAVE";
            } else {
                return "NOTHING";
            }

        }

    }

    @Override
    public List<SysInfected> listUserInfected() {
        return this.baseMapper.selectUserInfected();
    }

}
