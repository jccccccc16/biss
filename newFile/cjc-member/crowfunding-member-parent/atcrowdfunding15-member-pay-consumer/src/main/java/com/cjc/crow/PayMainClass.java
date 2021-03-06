package com.cjc.crow;

import com.cjc.crow.config.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2021/1/27
 * Time: 20:37
 * To change this template use File | Settings | File Templates.
 **/
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60*60)
@EnableFeignClients
@EnableConfigurationProperties({AlipayProperties.class})
@SpringBootApplication
public class PayMainClass {
    public static void main(String[] args) {
        SpringApplication.run(PayMainClass.class,args);
    }
}
