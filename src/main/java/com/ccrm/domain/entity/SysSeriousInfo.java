package com.ccrm.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.ccrm.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @CreateTime: 2022-11-25 15:34
 * @Description: 重症信息收集表 sys_serious_info
 */
@Data
public class SysSeriousInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 信息ID */
    @TableId(type = IdType.AUTO)
    private Long infoId;

    /** 用户ID */
    private Long userId;

    /** 年龄是否>65(否：0， 是：1) */
    private Integer age;

    /** 是否完成全程疫苗接种（0：否， 1：是） */
    private Integer vaccines;

    /** 是否合并较为严重慢性疾病（0：否，1：是） */
    private Integer withDisease;

    /** 是否有呼吸频率增快、持续性高热、浑身肌肉疼痛等症状（0：否，1：是） */
    private Integer discomfort;

    /** 是否感染（0：否， 1：是） */
    private Integer isInfected;

    /** 删除标志（0代表存在 1代表删除） */
    @TableLogic
    private Integer delFlag;

}
