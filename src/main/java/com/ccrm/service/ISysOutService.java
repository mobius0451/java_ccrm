package com.ccrm.service;

import com.ccrm.domain.entity.SysOut;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @CreateTime: 2022-11-26 14:29
 * @Description:
 */
public interface ISysOutService extends IService<SysOut> {

    int countOutByDate(String date);
}
