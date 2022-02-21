package com.cjc.mvc.controller;

import com.cjc.crowd.entity.Role;
import com.cjc.service.RoleService;
import com.cjc.util.ResultEntity;
import com.cjc.util.constant.CrowdConstant;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;
    private Logger logger = LoggerFactory.getLogger(RoleController.class);
    @ResponseBody
    @RequestMapping(value = "/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getRolePage(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword",defaultValue = "")String keyword
            ){

        PageInfo<Role> rolePageInfo = roleService.getRole(keyword, pageNum, pageSize);
        logger.info("查询的role数据："+rolePageInfo+"");
        return ResultEntity.successWithData(rolePageInfo);
    }

    @ResponseBody
    @RequestMapping(value = "role/save.json")
    public ResultEntity<PageInfo<Role>> saveRole(Role role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping(value = "role/update.json")
    public ResultEntity updateRole(Role role){
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

}
