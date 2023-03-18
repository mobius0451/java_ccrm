package com.ccrm.domain.model;

import lombok.Data;

/**
 * @CreateTime: 2023-02-27 17:55
 * @Description:
 */
@Data
public class VaccinesNumInfo {

    /**
     * 注射疫苗次数
     */
    private Integer numVaccines;

    /**
     * 各注释次数的人数
     */
    private Integer numPerson;
}
