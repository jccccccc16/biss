package cjc.mapper;

import com.cjc.crowd.entity.Admin;
import com.cjc.crowd.entity.AdminExample;
import com.cjc.util.CrowdUtil;
import com.cjc.util.constant.CrowdConstant;
import com.cjc.mapper.AdminMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-persist-mybatis.xml")
public class AdminMapperTest {
    @Autowired
    private AdminMapper adminMapper;
    @Test
    public void test01(){
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo("admin");
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }
    @Test
    public void selectAdmin(){
        List<Admin> adminList = adminMapper.selectAdmin("ad");
        for (Admin admin : adminList) {
            System.out.println(admin);
        }
    }

    @Test
    public void doSomething(){


    }

    @Test
    public void insert(){

        for(int i=0;i<50;i++){

            Admin admin = new Admin(null,"admin" + i, CrowdUtil.md5("admin"+i), "admin" + i, "admin@qq.com", CrowdUtil.getNow(CrowdConstant.DATE_PATTERN_01));
            System.out.println(admin);
            adminMapper.insertSelective(admin);
        }

    }
}

