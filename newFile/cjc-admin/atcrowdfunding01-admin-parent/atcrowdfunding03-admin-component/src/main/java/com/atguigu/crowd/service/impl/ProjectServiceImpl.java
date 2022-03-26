package com.atguigu.crowd.service.impl;



import com.atguigu.crowd.entity.*;
import com.atguigu.crowd.mapper.ProjectPOMapper;
import com.atguigu.crowd.service.api.MemberService;
import com.atguigu.crowd.service.api.ProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

        return projectDetailVO;
    }

    @Override
    public PageInfo<ProjectReview> getProjectReviews(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ProjectReview> projectReviews = projectPOMapper.selectProjectReviews();
        return new PageInfo<ProjectReview>(projectReviews);
    }

    @Override
    public int doReview(Integer id) {
        ProjectPO projectPO = new ProjectPO();
        projectPO.setId(id);
        projectPO.setStatus(1);
        int i = projectPOMapper.updateByPrimaryKeySelective(projectPO);
        return i;
    }

    @Override
    public int doDisReview(Integer projectId,String message) {
        ProjectPO projectPO = new ProjectPO();
        projectPO.setId(projectId);
        projectPO.setStatus(2);
        projectPO.setMessage(message);
        int i = projectPOMapper.updateByPrimaryKeySelective(projectPO);
        return i;
    }

    @Override
    public PageInfo<ProjectReview> getProjects(Integer pageNum, Integer pageSize) {
        // 查找除了待审核，和审核不通过的项目
        PageHelper.startPage(pageNum,pageSize);
        List<ProjectReview> projectReviewList = projectPOMapper.selectProjectsWithoutStatusEqualTo0and2();
        return new PageInfo<ProjectReview>(projectReviewList);
    }
}
