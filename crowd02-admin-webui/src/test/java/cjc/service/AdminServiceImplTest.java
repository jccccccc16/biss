package cjc.service;

import com.cjc.crowd.entity.Admin;
import com.cjc.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-persist-*.xml")
public class AdminServiceImplTest {
    @Autowired
    private AdminService adminService;
    @Test
    public void getAdminByLoginAcct(){
        Admin adminByLoginAcct = adminService.getAdminByLoginAcct("admin", "cjcisgood");
        System.out.println(adminByLoginAcct);
    }
}
