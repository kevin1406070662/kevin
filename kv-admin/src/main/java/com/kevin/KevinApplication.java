package com.kevin;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class KevinApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.kevin.KevinApplication.class, args);
        log.info("(♥◠‿◠)ﾉﾞ  kevinBoot启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }

}
