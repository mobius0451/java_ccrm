package com.ccrm.mapper;

import com.ccrm.domain.entity.SysVaccines;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccrm.domain.model.VaccinesNumInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:30
 * @Description:
 */
@Mapper
public interface SysVaccinesMapper extends BaseMapper<SysVaccines> {

    /**
     * 获取疫苗记录条数不小于3的用户id
     * @return
     */
    @Select("SELECT user_id FROM sys_vaccines GROUP BY user_id HAVING COUNT(*) >= 3")
    List<Long> selectVaccinesUserIds();

    /**
     * 获取注射各针次的各人数
     * @return
     */
    @Select("SELECT COUNT(*) as num_person, num_vaccines  FROM " +
            "(SELECT user_id, count(*) AS num_vaccines FROM sys_vaccines GROUP BY user_id) temp " +
            "GROUP BY num_vaccines")
    List<VaccinesNumInfo> selectVaccinesNum();
}
