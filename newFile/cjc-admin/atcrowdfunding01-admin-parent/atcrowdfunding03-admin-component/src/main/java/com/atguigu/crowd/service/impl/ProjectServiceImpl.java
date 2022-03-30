package com.atguigu.crowd.service.impl;



import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.*;
import com.atguigu.crowd.mapper.ProjectPOMapper;
import com.atguigu.crowd.mapper.ReviewProjectAdminMapper;
import com.atguigu.crowd.mvc.config.SecurityAdmin;
import com.atguigu.crowd.service.api.MemberService;
import com.atguigu.crowd.service.api.ProjectService;
import com.atguigu.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectPOMapper projectPOMapper;
    @Autowired
    private ReviewProjectAdminMapper reviewProjectAdminMapper;



    private Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

//    @Autowired
//    private MemberMapper memberMapper;

    @Override
    public PageInfo<ProjectPO> getToBeReviewProject(Integer pageNum, Integer pageSize) {
        ProjectPOExample projectPOExample = new ProjectPOExample();
        ProjectPOExample.Criteria criteria = projectPOExample.createCriteria();
        criteria.andStatusEqualTo(0);
        // 使用pageHelper进行分页
        PageHelper.startPage(pageNum,pageSize);
        List<ProjectPO> projectPOS = projectPOMapper.selectByExample(projectPOExample);

        return new PageInfo<ProjectPO>(projectPOS);
    }



    public ProjectDetailVO getDetailProjectVO(Integer id) {

        ProjectDetailVO projectDetailVO = projectPOMapper.selectDetailProjectVO(id);
        // 设置剩余天数
        projectDetailVO.setLastDay(CrowdUtil.getDateSub(projectDetailVO.getDay(),projectDetailVO.getDeployDate()));
        return projectDetailVO;
    }

    @Override
    public PageInfo<ProjectReview> getProjectReviews(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ProjectReview> projectReviews = projectPOMapper.selectProjectReviews();
        return new PageInfo<ProjectReview>(projectReviews);
    }

    @Override
    public int doReview(Integer projectId) {

        return review(projectId,"",false);
//        ProjectPO projectPO = new ProjectPO();
//        projectPO.setId(projectId);
//        // 判断是否为二次审核
//        ReviewProjectAdminExample reviewProjectAdminExample = new ReviewProjectAdminExample();
//        reviewProjectAdminExample.createCriteria().andProjectIdEqualTo(projectId);
//        List<ReviewProjectAdmin> reviewProjectAdmins = reviewProjectAdminMapper.selectByExample(reviewProjectAdminExample);
//        // 插入审核表
//        ReviewProjectAdmin reviewProjectAdmin = new ReviewProjectAdmin();
//        // 审核项目id
//        reviewProjectAdmin.setProjectId(projectId);
//        // 审核员的id
//        reviewProjectAdmin.setAdminId(getCurrentAdmin().getId());
//        // 更新日期
//        reviewProjectAdmin.setCreateDate(CrowdUtil.getNow(CrowdConstant.DATE_PATTERN));
//        // 修改状态
//        reviewProjectAdmin.setProjectStatus(1);
//        reviewProjectAdmin.setRemark("");
//        if(reviewProjectAdmins!=null){
//
//            reviewProjectAdminMapper.updateByExampleSelective(reviewProjectAdmin,reviewProjectAdminExample);
//            // 项目表只修改状态
//            projectPO.setStatus(1);
//            int i = projectPOMapper.updateByPrimaryKeySelective(projectPO);
//            return i;
//        }else{
//            // 如果是第一次审核,新增审核表
//            reviewProjectAdminMapper.insert(reviewProjectAdmin);
//            // 审核通过设置为1，备注设置为无
//            projectPO.setStatus(1);
//            int i = projectPOMapper.updateByPrimaryKeySelective(projectPO);
//            return i;
//        }

    }


    private int review(Integer projectId,String remark,boolean isDisReview){
        // 是否是第二次审核
        boolean isDiErCiShenHe = false;

        ProjectPO projectPO = new ProjectPO();
        projectPO.setId(projectId);

        ReviewProjectAdminExample reviewProjectAdminExample = new ReviewProjectAdminExample();
        reviewProjectAdminExample.createCriteria().andProjectIdEqualTo(projectId);
        List<ReviewProjectAdmin> reviewProjectAdmins = reviewProjectAdminMapper.selectByExample(reviewProjectAdminExample);
        // 是否是第一次审核
        isDiErCiShenHe = (reviewProjectAdmins != null);
        // 插入审核表
        ReviewProjectAdmin reviewProjectAdmin = new ReviewProjectAdmin();
        // 审核项目id
        reviewProjectAdmin.setProjectId(projectId);
        // 审核员的id
        reviewProjectAdmin.setAdminId(getCurrentAdmin().getId());
        // 更新日期
        reviewProjectAdmin.setCreateDate(CrowdUtil.getNow(CrowdConstant.DATE_PATTERN));

        reviewProjectAdmin.setRemark(remark);

        // 审核不通过
        if(isDisReview){

            // 修改状态
            reviewProjectAdmin.setProjectStatus(2);
            // 项目表只修改状态
            projectPO.setStatus(2);

            // 判断是否为二次审核
            if(isDiErCiShenHe){
                reviewProjectAdminMapper.updateByExampleSelective(reviewProjectAdmin,reviewProjectAdminExample);

            }else{
                // 如果是第一次审核,新增审核表
                reviewProjectAdminMapper.insert(reviewProjectAdmin);

            }

            int i = projectPOMapper.updateByPrimaryKeySelective(projectPO);
            return i;
        }else{
            // 通过审核
            // 修改状态
            reviewProjectAdmin.setProjectStatus(1);
            projectPO.setStatus(1);

            // 第二次审核
            if(isDiErCiShenHe){

                reviewProjectAdminMapper.updateByExampleSelective(reviewProjectAdmin,reviewProjectAdminExample);
                // 项目表只修改状态
                projectPO.setStatus(1);
                int i = projectPOMapper.updateByPrimaryKeySelective(projectPO);
                return i;
            }else{
                // 如果是第一次审核,新增审核表
                reviewProjectAdminMapper.insert(reviewProjectAdmin);
                // 审核通过设置为1，备注设置为无
                projectPO.setStatus(1);
                int i = projectPOMapper.updateByPrimaryKeySelective(projectPO);
                return i;
            }
        }

    }





    @Override
    public int doDisReview(Integer projectId,String remark) {
        return review(projectId,remark,true);
//        // 如果是二次审核
//        ProjectPO projectPO = new ProjectPO();
//        projectPO.setId(projectId);
//        // 判断是否为二次审核
//        ReviewProjectAdminExample reviewProjectAdminExample = new ReviewProjectAdminExample();
//        reviewProjectAdminExample.createCriteria().andProjectIdEqualTo(projectId);
//        List<ReviewProjectAdmin> reviewProjectAdmins = reviewProjectAdminMapper.selectByExample(reviewProjectAdminExample);
//        // 插入审核表
//        ReviewProjectAdmin reviewProjectAdmin = new ReviewProjectAdmin();
//        // 审核项目id
//        reviewProjectAdmin.setProjectId(projectId);
//        // 审核员的id
//        reviewProjectAdmin.setAdminId(getCurrentAdmin().getId());
//        // 更新日期
//        reviewProjectAdmin.setCreateDate(CrowdUtil.getNow(CrowdConstant.DATE_PATTERN));
//        // 修改状态
//        reviewProjectAdmin.setProjectStatus(2);
//        reviewProjectAdmin.setRemark(remark);
//
//
//        projectPO.setId(projectId);
//        projectPO.setStatus(2);
//        projectPO.setMessage(remark);
//        int i = projectPOMapper.updateByPrimaryKeySelective(projectPO);
//        return i;
    }

    @Override
    public PageInfo<ProjectReview> getProjects(Integer pageNum, Integer pageSize) {
        // 查找除了待审核，和审核不通过的项目
        PageHelper.startPage(pageNum,pageSize);
        List<ProjectReview> projectReviewList = projectPOMapper.selectProjectsWithoutStatusEqualTo0and2();
        return new PageInfo<ProjectReview>(projectReviewList);
    }

    /**
     * 获取当前审核员
     * @return
     */
    public static Admin getCurrentAdmin() {
        SecurityAdmin securityAdmin = (SecurityAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Admin originalAdmin = securityAdmin.getOriginalAdmin();
        return originalAdmin;
    }
}
