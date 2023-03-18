package com.ccrm.domain.model;

import lombok.Data;
import java.io.Serializable;

/**
 * @CreateTime: 2022-11-07 14:56
 * @Description: 分页查询条件
 */
@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 页码*/
    private int pageNumber;

    /** 页面大小*/
    private int pageSize;

    /** 模糊查询内容*/
    private String queryString;

}
