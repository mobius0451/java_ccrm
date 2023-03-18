package com.ccrm.handler;

import com.ccrm.domain.model.LoginUser;
import com.ccrm.exception.CustomException;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * @CreateTime: 2022-11-21 22:57
 * @Description: 自定义元数据对象处理器
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入操作，自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        log.info(metaObject.toString());

        try {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = loginUser.getUser().getUserName();
            metaObject.setValue("createBy", userName);
        }
        catch (Exception e) {
            throw new CustomException("获取用户信息异常");
        }
        finally {
            metaObject.setValue("createTime", LocalDateTime.now());
        }
    }

    /**
     * 更新操作，自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");
        log.info(metaObject.toString());

        try {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = loginUser.getUser().getUserName();
            metaObject.setValue("updateBy",userName);
        }
        catch (Exception e) {
            throw new CustomException("获取用户信息异常");
        }
        finally {
            metaObject.setValue("updateTime",LocalDateTime.now());
        }
    }

}
