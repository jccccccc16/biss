package com.cjc.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@MapperScan("com.cjc.crowd.mapper")
@SpringBootApplication
public class MysqlProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MysqlProviderApplication.class);
    }
}
