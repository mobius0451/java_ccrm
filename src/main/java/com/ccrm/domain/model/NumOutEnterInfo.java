package com.ccrm.domain.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * @CreateTime: 2023-03-01 22:00
 * @Description: 临时模型，映射个人出入校数量
 */
@Data
public class NumOutEnterInfo {

    /**  出入校数量 */
    private Integer num;

    /**  出入校日期 */
    private LocalDate date;

}
