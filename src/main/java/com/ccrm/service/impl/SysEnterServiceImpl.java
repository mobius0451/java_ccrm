package com.ccrm.service.impl;

import com.ccrm.domain.entity.SysEnter;
import com.ccrm.mapper.SysEnterMapper;
import com.ccrm.service.ISysEnterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @CreateTime: 2022-11-26 14:24
 * @Description:
 */
@Service
public class SysEnterServiceImpl extends ServiceImpl<SysEnterMapper, SysEnter> implements ISysEnterService {

    @Override
    public int countEnterByDate(String date) {
        return this.baseMapper.countEnterByDate(date);
    }
}
