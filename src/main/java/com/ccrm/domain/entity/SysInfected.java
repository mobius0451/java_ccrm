package com.ccrm.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.ccrm.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @CreateTime: 2022-11-25 15:35
 * @Description: 核酸信息表 sys_infected
 */
@Data
public class SysInfected extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 记录ID */
    @TableId(type = IdType.AUTO)
    private Long infectedId;

    /** 用户id */
    private Long userId;

    /** 感染持续时间/天 */
    private Integer infectedSpan;

    /** 感染期间最高体温 */
    private float maxTemperature;

    /** 感染时间 */
    private LocalDate infectedTime;

    /** 康复时间 */
    private LocalDate recoveryTime;

    /** 是否康复（0：未康复，1：已康复） */
    private Integer status;

    /** 删除标志（0代表存在 1代表删除） */
    @TableLogic
    private Integer delFlag;
}
