package com.atguigu.crowd.monitor.controller;

import com.atguigu.crowd.entity.OperLog;
import com.atguigu.crowd.monitor.service.api.OperLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 日志操作Controller
 */
@Controller
@RequestMapping("/log")
public class OperLogController {
    @Autowired
    private OperLogService operLogService;

    /**
     *
     * 查询日志操作
     *
     * @param pageNum
     * @param pageSize
     * @param keyword 查询的关键词
     * @param datePre 时间间隔
     * @param datePost
     * @param modelMap
     * @return
     */
    @RequestMapping("/get/oper/log/page.html")
    public String showLog(// pageNum默认值使用1
                          @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,

                          // pageSize默认值使用5
                          @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
                          @RequestParam(value = "keyword",defaultValue = "")String keyword,
                          @RequestParam(value = "datePre",defaultValue = "")String datePre,
                          @RequestParam(value = "datePost",defaultValue = "")String datePost,
                          ModelMap modelMap){



        PageInfo<OperLog> operLogList =
                operLogService.getOperLogList(pageNum,pageSize,keyword,datePre,datePost);
        modelMap.addAttribute("pageInfo",operLogList);
        return "log-page";
    }



}
