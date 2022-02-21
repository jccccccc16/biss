package com.cjc.crowd.test;

import com.cjc.crowd.entity.po.Member;
import com.cjc.crowd.entity.po.MemberExample;
import com.cjc.crowd.mapper.MemberMapper;
import com.cjc.crowd.mapper.ProjectPOMapper;
import com.cjc.crowd.mapper.TypeProjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MysqlTest {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private ProjectPOMapper projectPOMapper;
    @Test
    public void testConnect(){
        List<Member> members = memberMapper.selectByExample(null);
        MemberExample memberExample = new MemberExample();
        MemberExample.Criteria criteria = memberExample.createCriteria();
        criteria.andLoginacctEqualTo("test");
        List<Member> members1 = memberMapper.selectByExample(memberExample);
        System.out.println(members1.get(0));
    }

    @Test
    public void testInsertProjectType(){
        projectPOMapper.saveTypeProject(1,1);
    }
}
