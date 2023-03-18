package com.ccrm.domain.entity;

import com.ccrm.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @CreateTime: 2022-11-09 17:06
 * @Description: 公告实体
 */
@Data
public class SysNotice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 公告ID */
    @TableId(type = IdType.AUTO)
    private Long noticeId;

    /** 公告标题 */
    private String noticeTitle;

    /** 公告类型（1通知 2公告） */
    private String noticeType;

    /** 公告内容 */
    private String noticeContent;

    /** 公告配图 */
    private String picture;

    /** 公告状态（0正常 1关闭） */
    private String status;

}
