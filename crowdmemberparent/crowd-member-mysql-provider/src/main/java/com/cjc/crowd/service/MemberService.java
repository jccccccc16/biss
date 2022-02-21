package com.cjc.crowd.service;

import com.cjc.crowd.entity.po.Member;

public interface MemberService {
    Member getMemberPOByLoginAcct(String loginacct);

    int save(Member member);
}
