package com.ccrm.service;

import com.ccrm.domain.entity.SysVaccines;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccrm.domain.model.VaccinesNumInfo;

import java.util.List;
import java.util.Map;

/**
 * @CreateTime: 2022-11-26 14:31
 * @Description:
 */
public interface ISysVaccinesService extends IService<SysVaccines> {

    /**
     * 获取疫苗记录条数不小于3的用户id
     * @return
     */
    List<Long> getVaccinesUserIds();

    /**
     * 获取注射各针次的各人数
     * @return
     */
    List<VaccinesNumInfo> getVaccinesNum();
}
