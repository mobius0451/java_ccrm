package com.ccrm.domain.entity;

import com.ccrm.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import java.util.List;

/**
 * @CreateTime: 2022-11-06 15:34
 * @Description:
 */
@Data
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /** 部门ID */
    private Long collegeId;

    /** 用户账号 */
    private String userName;

    /** 用户昵称 */
    private String nickName;

    /** 用户邮箱 */
    private String email;

    /** 手机号码 */
    private String phone;

    /** 用户性别 */
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 帐号状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    @TableLogic
    private Integer delFlag;

    /** 学院班级对象 */
    @TableField(exist = false)
    private SysCollege college;

    /** 角色对象 */
    @TableField(exist = false)
    private List<SysRole> roles;

    /** 角色组 */
    @TableField(exist = false)
    private List<Long> roleIds;

    /** 角色ID */
    @TableField(exist = false)
    private Long roleId;

    /** 无参构造 */
    public SysUser() {}

    /** 有参构造 */
    public SysUser(Long userId) {
        this.userId = userId;
    }

    /** 返回判定结果 */
    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    /** 判断是否为管理员（id为1L的为管理员） */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }


}

