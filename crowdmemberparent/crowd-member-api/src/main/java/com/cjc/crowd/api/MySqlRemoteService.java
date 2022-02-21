package com.cjc.crowd.api;

import com.cjc.crowd.entity.po.Member;
import com.cjc.crowd.entity.po.ProjectPO;
import com.cjc.crowd.entity.vo.ProjectVO;
import com.cjc.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("cjc-crowd-mysql")
public interface MySqlRemoteService {
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<Member> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

    @RequestMapping("/save/memberpo")
    public ResultEntity<String> saveMemberpo(@RequestBody Member member);

    /**
     * @param projectVO
     * @param memberId
     * @return
     */
    @RequestMapping("/save/project/remote")
    ResultEntity<String> saveProjectVORemote(
            @RequestBody ProjectVO projectVO,
            @RequestParam("memberId") Integer memberId
    );


    @RequestMapping("/save/project/type")
    public ResultEntity<String> savProjectType(
            @RequestParam("projectId") Integer projectId,
            @RequestParam("typeId") Integer typeId
    );

    @RequestMapping("/get/my/project")
    ResultEntity<List<ProjectPO>> getProjectByMemberId(
            @RequestParam("memberId")Integer memberId
    );
}
