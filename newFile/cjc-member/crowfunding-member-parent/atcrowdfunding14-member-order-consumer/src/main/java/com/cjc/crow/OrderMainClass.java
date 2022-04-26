package com.cjc.crow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2021/1/25
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 **/



@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60*60)
@EnableFeignClients
@SpringBootApplication
public class OrderMainClass {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainClass.class,args);
    }
}
