package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.MemberPO;
import com.atguigu.crowd.entity.ProjectDetailVO;
import com.atguigu.crowd.entity.ProjectPO;
import com.atguigu.crowd.entity.ProjectReview;
import com.atguigu.crowd.service.api.MemberService;
import com.atguigu.crowd.service.api.ProjectService;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectHandler {
    @Resource
    private ProjectService projectService;
    private Logger logger = LoggerFactory.getLogger(ProjectHandler.class);
    @Autowired
    private MemberService memberService;
    /**
     * 获取待审核的项目
     * @return
     */

    @RequestMapping("/get/project/to/be/review/page.html")
    public String getProjectTemp(ModelMap modelMap, @RequestParam(value = "pageSize",defaultValue = "3")Integer pageSize,
                                 @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        PageInfo<ProjectReview> toBeReviewProjectPageInfo = projectService.getProjectReviews(pageNum,pageSize);
        modelMap.addAttribute("pageInfo",toBeReviewProjectPageInfo);
        logger.info("查询到project："+toBeReviewProjectPageInfo.toString());
        return "projects-review";
    }

    @RequestMapping("to/review/project.html")
    public String getReviewProjectDetail(
            @RequestParam("projectId")Integer projectId,
            ModelMap modelMap
    ){
        ProjectDetailVO detailProjectVO = projectService.getDetailProjectVO(projectId);
        modelMap.addAttribute("detailProject",detailProjectVO);
        return "project-detail";
    }






}
