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
}
