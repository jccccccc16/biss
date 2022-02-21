package com.cjc.crowd.handler;

import com.cjc.crowd.entity.po.Member;
import com.cjc.crowd.util.constant.CrowdConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class PortalHandler {

    @RequestMapping("/test/session")
    @ResponseBody
    public Member testSession(HttpSession session){
        Member attribute = (Member) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        return attribute;

    }

}
