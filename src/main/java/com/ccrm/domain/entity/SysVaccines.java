package com.ccrm.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.ccrm.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @CreateTime: 2022-11-25 15:35
 * @Description: 隔离信息表 sys_quarantine
 */
@Data
public class SysVaccines extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 疫苗ID */
    @TableId(type = IdType.AUTO)
    private Long vaccinesId;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 接种针次 */
    private String frequency;

    /** 接种单位 */
    private String company;

    /** 疫苗厂家品牌 */
    private String vaccinesBrand;

    /** 接种地点 */
    private String location;

    /** 接种时间 */
    private LocalDateTime time;

    /** 删除标志（0代表存在 1代表删除） */
    @TableLogic
    private Integer delFlag;

}
