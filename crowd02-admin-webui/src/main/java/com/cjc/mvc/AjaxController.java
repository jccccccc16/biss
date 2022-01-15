package com.cjc.mvc;

import com.cjc.crowd.entity.Admin;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {

    @ResponseBody
    @RequestMapping("/get/admin/by/ajax.json")
    public Admin getAdmin(){
        Admin admin = new Admin();
        admin.setId(1);
        admin.setEmail("617028197");
        return admin;
    }


}
