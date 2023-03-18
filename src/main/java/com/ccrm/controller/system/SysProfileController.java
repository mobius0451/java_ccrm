package com.ccrm.controller.system;

import com.ccrm.common.UserConstants;
import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.entity.SysCollege;
import com.ccrm.domain.entity.SysUser;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.service.ISysCollegeService;
import com.ccrm.service.ISysUserService;
import com.ccrm.utils.JwtUtils;
import com.ccrm.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @CreateTime: 2022-11-22 21:33
 * @Description: 个人信息
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysCollegeService collegeService;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${ccrm.filePath}")
    private String filePath;

    @Value("${ccrm.fileMapping}")
    private String fileMapping;

    /**
     * 个人信息
     */
    @GetMapping
    public ResponseResult profile() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = loginUser.getUser();
        SysCollege college = collegeService.getById(user.getCollegeId());
        user.setCollege(college);
        ResponseResult result = ResponseResult.success(user);
        result.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUserId()));
        return result;
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PutMapping
    public ResponseResult updateProfile(@RequestBody SysUser user) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = loginUser.getUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty(user.getPhone())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return ResponseResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return ResponseResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUserId(sysUser.getUserId());
        user.setPassword(null);
        user.setAvatar(null);
        user.setCollegeId(null);

        if (userService.updateById(user)) {
            // 更新缓存用户信息
            sysUser.setNickName(user.getNickName());
            sysUser.setPhone(user.getPhone());
            sysUser.setEmail(user.getEmail());
            sysUser.setSex(user.getSex());
            jwtUtils.setLoginUser(loginUser);
            return ResponseResult.success();
        }
        return ResponseResult.error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PutMapping("/updatePwd")
    public ResponseResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(oldPassword, password)) {
            return ResponseResult.error("修改密码失败，旧密码错误");
        }
        if (passwordEncoder.matches(newPassword, password)) {
            return ResponseResult.error("新密码不能与旧密码相同");
        }
        SysUser user = loginUser.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password", passwordEncoder.encode(newPassword));
        updateWrapper.eq("user_name", userName);
        if (userService.update(updateWrapper)) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(passwordEncoder.encode(newPassword));
            jwtUtils.setLoginUser(loginUser);
            return ResponseResult.success();
        }
        return ResponseResult.error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @PostMapping("/avatar")
    public ResponseResult avatar(@RequestParam("avatarfile") MultipartFile file) {
        if (!file.isEmpty()) {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //判断目录是否存在，不存在则新建
            String userPath = filePath + loginUser.getUsername() + "/";
            File dir = new File(userPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                String avatarName = UUID.randomUUID().toString() + ".png";
                file.transferTo(new File(userPath + avatarName));
                String avatarUrl = fileMapping + loginUser.getUsername() + "/" + avatarName;
                UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("avatar", avatarUrl);
                updateWrapper.eq("user_id", loginUser.getUserId());
                if (userService.update(updateWrapper)) {
                    ResponseResult result = ResponseResult.success();
                    result.put("imgUrl", avatarUrl);
                    // 更新缓存用户头像
                    loginUser.getUser().setAvatar(avatarName);
                    jwtUtils.setLoginUser(loginUser);
                    return result;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseResult.error("上传头像异常，请联系管理员");
    }

}
