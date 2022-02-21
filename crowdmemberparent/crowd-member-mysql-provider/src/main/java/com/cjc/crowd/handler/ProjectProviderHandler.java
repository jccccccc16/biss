package com.cjc.crowd.handler;

import com.cjc.crowd.entity.po.ProjectPO;
import com.cjc.crowd.entity.vo.ProjectVO;
import com.cjc.crowd.service.ProjectService;
import com.cjc.crowd.util.ResultEntity;
import com.cjc.crowd.util.constant.CrowdConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProjectProviderHandler {
    @Resource
    private ProjectService projectService;

    /**
     *
     * @param projectVO
     * @param memberId
     * @return 返回id
     */
    @RequestMapping("/save/project/remote")
    ResultEntity<String> saveProjectVORemote(
            @RequestBody ProjectVO projectVO,
            @RequestParam("memberId") Integer memberId
    ){
        int insert = projectService.saveProject(projectVO, memberId);
        if(insert==0){
            return ResultEntity.failed(CrowdConstant.MESSAGE_DATA_INSERT_FAIL);
        }
         // 返回主键
        return ResultEntity.successWithoutData();
    }


    @RequestMapping("/save/project/type")
    ResultEntity<String> savProjectType(
            @RequestParam("projectId")Integer projectId,
            @RequestParam("typeId") Integer typeId
    ){
        int insert = projectService.saveProjectType(projectId, typeId);
        if(insert==0){
            return ResultEntity.failed(CrowdConstant.MESSAGE_DATA_INSERT_FAIL);
        }
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/get/my/project")
    ResultEntity<List<ProjectPO>> getProjectByMemberId(
            @RequestParam("memberId")Integer memberId
    ){
        List<ProjectPO> projectPOList = projectService.getProjectByMemberId(memberId);
        return ResultEntity.successWithData(projectPOList);
    }
}
