package com.ccrm.mapper;

import com.ccrm.domain.entity.SysInfected;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:27
 * @Description:
 */
@Mapper
public interface SysInfectedMapper extends BaseMapper<SysInfected> {

    /**
     * 分组查询每个用户最新的感染信息
     * @return
     */
    @Select("SELECT * FROM sys_infected t1 " +
            "INNER JOIN (SELECT MAX(infected_id) AS id FROM sys_infected GROUP BY user_id) t2 " +
            "ON t1.infected_id = t2.id")
    List<SysInfected> selectUserInfected();
}
