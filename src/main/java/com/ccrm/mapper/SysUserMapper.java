package com.ccrm.mapper;

import com.ccrm.domain.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * @CreateTime: 2022-11-06 21:59
 * @Description: 用户持久层
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据角色编号查询该角色下的用户编号
     * @param roleId
     * @return
     */
    @Select("select user_id from sys_user_role where role_id = #{roleId}")
    List<Long> selectAllocatedList(Long roleId);


}
