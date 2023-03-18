package com.ccrm.domain.entity;

import com.ccrm.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDate;

/**
 * @CreateTime: 2022-11-25 15:35
 * @Description: 出校申请表 sys_out
 */
@Data
public class SysOut extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 出校ID */
    @TableId(type = IdType.AUTO)
    private Long outId;

    /** 用户ID */
    private Long userId;

    /** 用户所属班级id */
    private Long userCollegeId;

    /** 出校类型(0:临时出校, 1:固定出校) */
    private String outType;

    /** 出校原因 */
    private String outReason;

    /** 出校去向 */
    private String outWhere;

    /** 出校时间 */
    private LocalDate beginTime;

    /** 结束时间 */
    private LocalDate overTime;

    /** 联系方式 */
    private String contact;

    /** 紧急联系 */
    private String emergencyContact;

    /** 健康码 */
    private String healthyCode;

    /** 审核状态（0审核中 1审核通过 2拒绝申请） */
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    @TableLogic
    private Integer delFlag;
}
