package com.cjc.crowd.service;

import com.cjc.crowd.entity.po.ProjectPO;
import com.cjc.crowd.entity.vo.ProjectVO;

import java.util.List;

public interface ProjectService {
    public int saveProject(ProjectVO projectVO, Integer memberId);
    public int saveProjectType(Integer projectId,Integer typeId);

    List<ProjectPO> getProjectByMemberId(Integer memberId);
}
