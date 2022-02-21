package cjc.service;

import com.cjc.crowd.entity.Role;
import com.cjc.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.annotation.Resources;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-web-mvc.xml"})
public class RoleServiceImpleTest {

    @Resource
    private RoleService roleService;
    @Test
    public void getRole(){
        PageInfo<Role> role = roleService.getRole("", 1, 5);
        System.out.println(role);
    }

}
