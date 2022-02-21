package com.cjc.crowd.test;

import com.cjc.crowd.api.MySqlRemoteService;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.spring5.context.SpringContextUtils;



@RunWith(SpringRunner.class)
@SpringBootTest
public class FeginTest {

    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    @Test
    public void testMysql(){
        System.out.println(mySqlRemoteService);
    }
}
