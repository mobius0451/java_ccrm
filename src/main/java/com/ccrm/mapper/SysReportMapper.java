package com.ccrm.mapper;

import com.ccrm.domain.entity.SysReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:20
 * @Description:
 */
@Mapper
public interface SysReportMapper extends BaseMapper<SysReport> {
}
