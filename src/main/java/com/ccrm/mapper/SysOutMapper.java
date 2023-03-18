package com.ccrm.mapper;

import com.ccrm.domain.entity.SysOut;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @CreateTime: 2022-11-26 14:29
 * @Description:
 */
@Mapper
public interface SysOutMapper extends BaseMapper<SysOut> {

    @Select("select count(*) from sys_out where begin_time = #{data}")
    int countOutByDate(@PathVariable String date);
}
