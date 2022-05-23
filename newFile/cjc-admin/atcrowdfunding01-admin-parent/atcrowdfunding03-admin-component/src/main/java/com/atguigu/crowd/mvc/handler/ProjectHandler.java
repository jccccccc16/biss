package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.MemberPO;
import com.atguigu.crowd.entity.ProjectDetailVO;
import com.atguigu.crowd.entity.ProjectPO;
import com.atguigu.crowd.entity.ProjectReview;
import com.atguigu.crowd.monitor.annotation.BusinessType;
import com.atguigu.crowd.service.api.MemberService;
import com.atguigu.crowd.service.api.ProjectService;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
     *
     * @return
     */
    @BusinessType("获取待审核的项目")
    @PreAuthorize("hasAuthority('project:get:review')")
    @RequestMapping("/get/project/to/be/review/page.html")
    public String getProjectTemp(
            ModelMap modelMap,
            @RequestParam(value = "pageSize",defaultValue = "3")Integer pageSize,

            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        PageInfo<ProjectReview> toBeReviewProjectPageInfo = projectService.getProjectReviews(pageNum,pageSize);
        modelMap.addAttribute("pageInfo",toBeReviewProjectPageInfo);
        logger.info("查询到project："+toBeReviewProjectPageInfo.toString());
        return "projects-review";
    }

    /**
     * 获取审核项目详情
     * @param projectId
     * @param modelMap
     * @return
     */
    @BusinessType("获取审核项目详情")
    @RequestMapping("/to/get/project/detail.html")
    public String getReviewProjectDetail(
            @RequestParam("projectId")Integer projectId,
            @RequestParam("type") String type,
            ModelMap modelMap
    ){
        ProjectDetailVO detailProjectVO = projectService.getDetailProjectVO(projectId);
        modelMap.addAttribute("detailProject",detailProjectVO);
        // 设置访问类型，用于前端展示不同的页面
        modelMap.addAttribute("type",type);
        return "project-detail";
    }



    /**
     * 审核通过
     */
    @BusinessType("审核通过项目")
    @PreAuthorize("hasAuthority('project:do:review')")
    @RequestMapping("/do/review/project.html")
    public String doReview(
            @RequestParam("projectId") Integer projectId,
            HttpServletRequest request
    ){
        int i = projectService.doReview(projectId);
        if(i==0){
            throw new RuntimeException("操作失败");
        }
        request.setAttribute("message","通过审核成功");
        return "redirect:/project/get/project/to/be/review/page.html";
    }

    /**
     * 审核不通过
     * @param projectId
     * @param remark
     * @param modelMap
     * @return
     */
    @BusinessType("审核不通过项目")
    @PreAuthorize("hasAuthority('project:do:review')")
    @RequestMapping("/do/disReview/project.html")
    public String doDisReview(
            @RequestParam("projectId") Integer projectId,
            @RequestParam("message") String remark,
            ModelMap modelMap

    ){
        int i = projectService.doDisReview(projectId, remark);
        if(i==0){
            throw new RuntimeException("操作失败");
        }
        return "redirect:/project/get/project/to/be/review/page.html";
    }


    /**
     * 获取除了0和2状态的项目，用于展示项目管理
     * @param modelMap
     * @param pageSize
     * @param pageNum /project/to/get/projects/page.html
     * @return
     */
    @BusinessType("查看项目管理")
    @PreAuthorize("hasAuthority('project:get')")
    @RequestMapping("/to/get/projects/page.html")
    public String toProjectPage(
            ModelMap modelMap,
            @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,

            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum
    ){

        PageInfo<ProjectReview> projects = projectService.getProjects(pageNum, pageSize);
        modelMap.addAttribute("pageInfo",projects);
        return "projects-page";
    }



    






}
