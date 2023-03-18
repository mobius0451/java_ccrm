package com.ccrm.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ccrm.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDate;

/**
 * @CreateTime: 2022-11-25 15:34
 * @Description: 健康打卡表 sys_report
 */
@Data
public class SysReport extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 打卡ID */
    @TableId(type = IdType.AUTO)
    private Long reportId;

    /** 用户ID */
    private Long userId;

    /** 身体状况（0：正常， 1：发热咳嗽， 2：其他不正常状况） */
    private Integer bodyCondition;

    /** 体温 */
    private float temperature;

    /** 抗原或核酸检测情况是否异常（0：否， 1：是） */
    private Integer testing;

    /** 是否接触新冠阳性患者（0：否， 1：是） */
    private Integer touch;

    /** 报备位置 */
    private String location;

    /** 报备日期 */
    private LocalDate reportTime;

    /** 删除标志（0代表存在 1代表删除） */
    @TableLogic
    private Integer delFlag;

    /** 临时字段 */
    @TableField(exist = false)
    private int num;

}
