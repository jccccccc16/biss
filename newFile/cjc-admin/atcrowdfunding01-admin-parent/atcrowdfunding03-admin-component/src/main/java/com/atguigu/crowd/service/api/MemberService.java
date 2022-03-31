package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Member;
import com.atguigu.crowd.entity.MemberPO;
import com.github.pagehelper.PageInfo;


public interface MemberService {

	MemberPO getMemberPOByLoginAcct(String loginacct);

	void saveMember(MemberPO memberPO);

	MemberPO getMemberById(Integer memberId);


    PageInfo<Member> getMemberPageInfo(String keyword, Integer pageNum, Integer pageSize);
}
