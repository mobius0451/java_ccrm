package com.ccrm.controller.system;

import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysNotice;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysNoticeService;
import com.ccrm.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @CreateTime: 2022-11-14 15:48
 * @Description:
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController {

    @Autowired
    private ISysNoticeService noticeService;

    @Value("${ccrm.fileMapping}")
    private String fileMapping;

    /**
     * 分页获取通知公告列表
     */
    @PreAuthorize("@ccrm.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public ResponseResult list(int pageNum, int pageSize, SysNotice notice) {
        Page<SysNotice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysNotice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(notice.getNoticeTitle()), SysNotice::getNoticeTitle, notice.getNoticeTitle());
        queryWrapper.like(StringUtils.isNotEmpty(notice.getCreateBy()), SysNotice::getCreateBy, notice.getCreateBy());
        queryWrapper.eq(StringUtils.isNotEmpty(notice.getNoticeType()), SysNotice::getNoticeType, notice.getNoticeType());
        queryWrapper.orderByDesc(SysNotice::getCreateTime);
        noticeService.page(page, queryWrapper);
        return ResponseResult.success(page);
    }


    /**
     * 根据通知公告编号获取详细信息
     * @param noticeId
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:notice:query')")
    @GetMapping("/{noticeId}")
    public ResponseResult getInfo(@PathVariable Long noticeId) {
        return ResponseResult.success(noticeService.getById(noticeId));
    }

    /**
     * 新增通知公告
     * @param notice
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:notice:add')")
    @PostMapping
    public ResponseResult save(@RequestBody SysNotice notice) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        notice.setPicture(fileMapping + loginUser.getUsername() + "/" + notice.getPicture());
        boolean r = noticeService.save(notice);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 修改通知公告
     * @param notice
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:notice:edit')")
    @PutMapping
    public ResponseResult update(@RequestBody SysNotice notice) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        notice.setPicture(fileMapping + loginUser.getUsername() + "/" + notice.getPicture());
        boolean r = noticeService.updateById(notice);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 删除通知公告
     * @param noticeIds
     * @return
     */
    @PreAuthorize("@ccrm.hasPermi('system:notice:remove')")
    @DeleteMapping("/{noticeIds}")
    public ResponseResult remove(@PathVariable List<Long> noticeIds) {
        boolean r = noticeService.removeByIds(noticeIds);
        if (r) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * 获取首页近期公告通知
     * @return
     */
    @GetMapping("/lastNotice")
    public ResponseResult lastNotice(SysNotice notice) {
        LambdaQueryWrapper<SysNotice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(notice.getNoticeType()), SysNotice::getNoticeType, notice.getNoticeType());
        queryWrapper.orderByDesc(SysNotice::getCreateTime).last("limit 9");
        List<SysNotice> list = noticeService.list(queryWrapper);
        return ResponseResult.success(list);
    }


}
