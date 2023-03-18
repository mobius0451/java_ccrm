package com.ccrm.service;

import com.ccrm.domain.entity.SysInfected;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccrm.domain.entity.SysReport;

import java.util.List;

/**
 * @CreateTime: 2022-11-26 14:28
 * @Description:
 */
public interface ISysInfectedService extends IService<SysInfected> {

    /**
     * 编辑感染信息
     * @return 返回对感染信息的操作（更新UPDATE, 新增SAVE）
     */
    String editInfected(SysReport report);

    /**
     * 分组查询每个用户最新的感染信息
     * @return
     */
    List<SysInfected> listUserInfected();

}
