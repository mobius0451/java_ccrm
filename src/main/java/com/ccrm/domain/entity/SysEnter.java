package com.ccrm.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.ccrm.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDate;

/**
 * @CreateTime: 2022-11-25 15:34
 * @Description: 入校申请表 sys_enter
 */
@Data
public class SysEnter extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 入校ID */
    @TableId(type = IdType.AUTO)
    private Long enterId;

    /** 用户ID */
    private Long userId;

    /** 用户所属班级ID */
    private Long userCollegeId;

    /** 入校类型(0:临时访校, 1:出校过期 2:假期返校) */
    private String enterType;

    /** 入校具体原因 */
    private String enterReason;

    /** 启程地 */
    private String departurePlace;

    /** 入校时间 */
    private LocalDate beginTime;

    /** 结束时间 */
    private LocalDate overTime;

    /** 联系方式 */
    private String contact;

    /** 紧急联系 */
    private String emergencyContact;

    /** 健康码 */
    private String healthyCode;

    /** 行程码 */
    private String tripCode;

    /** 核酸证明 */
    private String nucleicAcid;

    /** 审核状态（0审核中 1审核通过 2拒绝申请） */
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    @TableLogic
    private Integer delFlag;
}
