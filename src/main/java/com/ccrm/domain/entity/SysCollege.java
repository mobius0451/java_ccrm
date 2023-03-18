package com.ccrm.domain.entity;

import com.ccrm.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @CreateTime: 2022-11-06 15:36
 * @Description: 学院班级表 sys_college
 */
@Data
public class SysCollege extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 学院班级ID */
    @TableId(type = IdType.AUTO)
    private Long collegeId;

    /** 父级学院班级ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 学院班级名称 */
    private String collegeName;

    /** 显示顺序 */
    private Integer orderNum;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 学院班级状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @TableLogic
    private Integer delFlag;

    /** 父级学院班级名称 */
    @TableField(exist = false)
    private String parentName;

    /** 子学院班级 */
    @TableField(exist = false)
    private List<SysCollege> children = new ArrayList<SysCollege>();

}
