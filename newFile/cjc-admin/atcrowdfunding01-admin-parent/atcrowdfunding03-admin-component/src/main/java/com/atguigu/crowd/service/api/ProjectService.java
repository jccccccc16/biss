package com.atguigu.crowd.service.api;


import com.atguigu.crowd.entity.ProjectDetailVO;
import com.atguigu.crowd.entity.ProjectPO;
import com.atguigu.crowd.entity.ProjectReview;
import com.atguigu.crowd.entity.ProjectVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProjectService {
    /**
     * 获取待审核的项目
     * @return
     */
    PageInfo<ProjectPO> getToBeReviewProject(Integer pageNum, Integer pageSize);




    ProjectDetailVO getDetailProjectVO(Integer id);

    PageInfo<ProjectReview> getProjectReviews(Integer pageNum,Integer pageSize);

    int doReview(Integer id);

    int doDisReview(Integer projectId,String message);

    PageInfo<ProjectReview> getProjects(Integer pageNum,Integer pageSize);

    /**
     * 获取众筹中的项目
     * @return
     */
    List<ProjectPO> getProjectCollecting();

    /**
     * 更新
     * @param projectPO
     * @return
     */
    int updateSelective(ProjectPO projectPO);


}
