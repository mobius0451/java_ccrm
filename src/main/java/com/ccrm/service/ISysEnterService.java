package com.ccrm.service;

import com.ccrm.domain.entity.SysEnter;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @CreateTime: 2022-11-26 14:24
 * @Description:
 */
public interface ISysEnterService extends IService<SysEnter> {

    int countEnterByDate(String date);
}
