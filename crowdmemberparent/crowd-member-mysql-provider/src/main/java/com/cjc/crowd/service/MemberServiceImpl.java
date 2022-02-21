package com.cjc.crowd.service;

import com.cjc.crowd.entity.po.Member;
import com.cjc.crowd.entity.po.MemberExample;
import com.cjc.crowd.mapper.MemberMapper;
import com.cjc.crowd.util.CrowdUtil;
import com.cjc.crowd.util.constant.CrowdConstant;
import com.cjc.crowd.util.exception.LoginFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Transactional(readOnly = true)
    public Member getMemberPOByLoginAcct(String loginacct) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andLoginacctEqualTo(loginacct);
        List<Member> members = memberMapper.selectByExample(memberExample);
        if(members==null){
            return null;
        }
        return members.get(0);
    }
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            readOnly = false)
    public int save(Member member) {
       return memberMapper.insert(member);

    }
}
