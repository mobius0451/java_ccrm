package com.ccrm.service.impl;

import com.ccrm.domain.entity.SysVaccines;
import com.ccrm.domain.model.VaccinesNumInfo;
import com.ccrm.mapper.SysVaccinesMapper;
import com.ccrm.service.ISysVaccinesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:31
 * @Description:
 */
@Service
public class SysVaccinesServiceImpl extends ServiceImpl<SysVaccinesMapper, SysVaccines> implements ISysVaccinesService {

    @Override
    public List<Long> getVaccinesUserIds() {
        return this.baseMapper.selectVaccinesUserIds();
    }

    @Override
    public List<VaccinesNumInfo> getVaccinesNum() {
        return this.baseMapper.selectVaccinesNum();
    }
}
