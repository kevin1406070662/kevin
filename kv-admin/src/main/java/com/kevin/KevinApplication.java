package com.kevin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author kevin
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan(basePackages = {"com.kevin.system.dao", "com.kevin.generator.mapper"})
public class KevinApplication {
    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "ture");
        SpringApplication.run(com.kevin.KevinApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  kevinBoot启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }

}