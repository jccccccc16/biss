package com.cjc.crow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2020/11/13
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 **/
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60*60)
@EnableFeignClients
@SpringBootApplication
public class ProjectMainClass  {

    public static void main(String[] args) {
        SpringApplication.run(ProjectMainClass.class,args);
    }

}
