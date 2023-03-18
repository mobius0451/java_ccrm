package com.ccrm.service.impl;

import com.ccrm.domain.entity.SysReport;
import com.ccrm.mapper.SysReportMapper;
import com.ccrm.service.ISysReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:21
 * @Description:
 */
@Service
public class SysReportServiceImpl extends ServiceImpl<SysReportMapper, SysReport> implements ISysReportService {
}
