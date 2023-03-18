package com.ccrm.mapper;

import com.ccrm.domain.entity.SysCollege;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @CreateTime: 2022-11-13 21:42
 * @Description:
 */
@Mapper
public interface SysCollegeMapper extends BaseMapper<SysCollege> {

    /**
     * 根据学院班级id修改状态
     * @param id
     * @param status
     */
    @Update("update sys_college set status = #{status} where college_id = #{id}")
    int updateStatusById(@PathVariable Long id, @PathVariable String status);
}
