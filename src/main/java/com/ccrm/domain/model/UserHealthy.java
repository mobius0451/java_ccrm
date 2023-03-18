package com.ccrm.domain.model;

import com.ccrm.domain.entity.SysInfected;
import com.ccrm.domain.entity.SysUser;
import com.ccrm.domain.entity.SysVaccines;
import lombok.Data;
import java.util.List;

/**
 * @CreateTime: 2023-02-14 22:09
 * @Description: 重症人群对象
 */
@Data
public class UserHealthy {

    /** 用户信息 */
    private SysUser user;

    /** 感染信息 */
    private SysInfected infected;

    /** 疫苗接种信息 */
    private List<SysVaccines> vaccines;


}
