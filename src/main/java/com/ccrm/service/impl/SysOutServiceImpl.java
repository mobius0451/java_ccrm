package com.ccrm.service.impl;

import com.ccrm.domain.entity.SysOut;
import com.ccrm.mapper.SysOutMapper;
import com.ccrm.service.ISysOutService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @CreateTime: 2022-11-26 14:30
 * @Description:
 */
@Service
public class SysOutServiceImpl extends ServiceImpl<SysOutMapper, SysOut> implements ISysOutService {

    @Override
    public int countOutByDate(String date) {
        return this.baseMapper.countOutByDate(date);
    }
}
