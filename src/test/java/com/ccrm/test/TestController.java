package com.ccrm.test;

import com.ccrm.domain.entity.SysUser;
import com.ccrm.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @CreateTime: 2022-11-06 22:01
 * @Description: 测试类
 */
@SpringBootTest
public class TestController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void findAll() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        for (SysUser sysUser : sysUsers) {
            System.out.println(sysUser);
        }
    }

    @Test
    public void testAdd() {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("测试用户");
        sysUser.setNickName("测试用户");
        int insert = sysUserMapper.insert(sysUser);
        System.out.println(insert);
    }

    @Test
    public void testDelete() {
        int insert = sysUserMapper.deleteById(101);
        System.out.println(insert);
    }
}
