package com.ccrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @CreateTime: 2022-11-05 21:48
 * @Description: 启动程序
 */

@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
public class CCRMApplication {

    public static void main(String[] args) {
        SpringApplication.run(CCRMApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  项目启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
