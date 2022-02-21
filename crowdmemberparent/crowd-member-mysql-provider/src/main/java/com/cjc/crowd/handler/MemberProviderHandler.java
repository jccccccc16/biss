package com.cjc.crowd.handler;

import com.cjc.crowd.entity.po.Member;
import com.cjc.crowd.entity.po.ProjectPO;
import com.cjc.crowd.entity.vo.ProjectVO;
import com.cjc.crowd.service.MemberService;
import com.cjc.crowd.service.ProjectService;
import com.cjc.crowd.util.ResultEntity;
import com.cjc.crowd.util.constant.CrowdConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MemberProviderHandler {
    @Resource
    private MemberService memberService;
    @Resource
    private ProjectService projectService;
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<Member> getMemberPOByLoginAcctRemote(@RequestParam("loginacct")String loginacct){
        try{
            Member member = memberService.getMemberPOByLoginAcct(loginacct);
            return ResultEntity.successWithData(member);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/save/memberpo")
    public ResultEntity<String> saveMemberpo(@RequestBody Member member){
        try{
            memberService.save(member);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
