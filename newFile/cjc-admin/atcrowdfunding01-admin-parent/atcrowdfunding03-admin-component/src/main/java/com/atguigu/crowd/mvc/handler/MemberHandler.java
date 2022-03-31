package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Member;
import com.atguigu.crowd.entity.MemberVO;
import com.atguigu.crowd.service.api.MemberService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/member")
@Controller
public class MemberHandler {
    @Autowired
    private MemberService memberService;


    @RequestMapping("/get/member/page.html")
    public String getMemberPage(
            ModelMap modelMap,
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            @RequestParam(value = "pageSize",defaultValue = "3")Integer pageSize,

            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        PageInfo<Member> pageInfo =  memberService.getMemberPageInfo(keyword, pageNum,pageSize);
        modelMap.addAttribute("pageInfo",pageInfo);
        return "member-page";
    }

}
