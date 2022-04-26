package com.cjc.crow.service.api;

import com.cjc.crow.entity.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProjectService {

    void saveProject(ProjectVO projectVO,Integer memberId);

    List<PortalTypeVO> getPortalTypeVOList();

    ProjectDetailVO getDetailProjectVO(Integer id);

    PageInfo<MySupportProjectVO> getMySupportProjectVOList(Integer memberId,Integer pageNum,Integer pageSize);

    PageInfo<MyLaunchProjectVO> getMyLaunchProjectVOPageInfo(Integer memberId,Integer pageNum,Integer pageSize);

    int updateProjectSupporter(Integer projectId);

    PageInfo<PortalProjectVO> getPortalProjectList(Integer pageNum,Integer pageSize);
}
