package com.cjc.mvc.controller;


import com.cjc.crowd.entity.Admin;
import com.cjc.service.AdminService;
import com.cjc.util.constant.CrowdConstant;
import com.cjc.util.exception.LoginFailedException;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.print.DocFlavor;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/admin/login.html")
    public String login(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    ){
        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPswd);
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        logger.info("登录成功");
        // 重定向到主页面
        return "redirect:/admin/to/admin/main/page.html";
    }

    @RequestMapping("admin/do/logout.html")
    public String logout(HttpSession session){
        // session失效
        session.invalidate();
        logger.info("登录成功");
        // 跳转到登录页面
        return "redirect:/admin/to/login/page.html";
    }

    /**
     * 首页
     * @return
     */
    @RequestMapping("/index.html")
    public String index(){
        return "admin-login";
    }


    /**
     *
     * @return
     */
    @RequestMapping("/admin/page.html")
    public String getAdminPage(
            @RequestParam(value = "keyword",defaultValue = "")String keyWord,
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNuM,
            @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
            ModelMap modelMap
    ){
        PageInfo<Admin> pageInfo = adminService.findAll(keyWord, pageNuM, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        logger.info(pageInfo.getList().toString());
        return "admin-page";
    }

    @RequestMapping("/save/admin.html")
    public String addAdmin(Admin admin){
        adminService.saveAdmin(admin);
        return "redirect:/admin/page.html?pageNum="+Integer.MAX_VALUE;
    }

}
