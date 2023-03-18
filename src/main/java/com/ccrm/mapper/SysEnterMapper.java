package com.ccrm.mapper;

import com.ccrm.domain.entity.SysEnter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @CreateTime: 2022-11-26 14:23
 * @Description:
 */
@Mapper
public interface SysEnterMapper extends BaseMapper<SysEnter> {

    @Select("select count(*) from sys_enter where begin_time = #{data}")
    int countEnterByDate(@PathVariable String date);
}
