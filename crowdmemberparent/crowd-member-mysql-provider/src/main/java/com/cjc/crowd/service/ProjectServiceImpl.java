package com.cjc.crowd.service;

import com.cjc.crowd.entity.po.ProjectItemPicPO;
import com.cjc.crowd.entity.po.ProjectPO;
import com.cjc.crowd.entity.po.ProjectPOExample;
import com.cjc.crowd.entity.po.ReturnPO;
import com.cjc.crowd.entity.vo.ProjectVO;
import com.cjc.crowd.entity.vo.ReturnVO;
import com.cjc.crowd.mapper.ProjectItemPicPOMapper;
import com.cjc.crowd.mapper.ProjectPOMapper;
import com.cjc.crowd.mapper.ReturnPOMapper;
import com.cjc.crowd.util.CrowdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectPOMapper projectPOMapper;
    @Resource
    private ReturnPOMapper returnPOMapper;

    @Resource
    private ProjectItemPicPOMapper projectItemPicPOMapper;


    /**
     * 保存projectPO
     * @param projectVO
     * @param memberId
     * @return
     */
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            readOnly = false)
    public int saveProject(ProjectVO projectVO, Integer memberId) {
        ProjectPO projectPO = new ProjectPO();
        BeanUtils.copyProperties(projectVO,projectPO);
        projectPO.setMemberid(memberId);
        // 保存项目信息
        // 添加初始值
        // 项目状态 0-即将开始，1-众筹中，2-众筹成功，3-众筹失败，默认为1
        projectPO.setStatus(1);
        projectPO.setSupporter(0); // 支持者
        projectPO.setCompletion(0); //完成度
        projectPO.setFollower(0); // 支持者数量
        projectPO.setCreatedate(CrowdUtil.getNow("yyyy-MM-dd")); // 项目创建时间
        projectPO.setDeploydate(CrowdUtil.getNow("yyyy-MM-dd")); // 项目发起时间
        int insert = projectPOMapper.insert(projectPO);
        Integer projectPOId = projectPO.getId();
        // 保存类型
        List<Integer> typeIdList = projectVO.getTypeIdList();
        for (Integer typeId : typeIdList) {
            projectPOMapper.saveTypeProject(typeId,projectPOId);
        }
        // 保存回报
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            returnPO.setProjectid(projectPOId);
            BeanUtils.copyProperties(returnVO,returnPO);
            int returnInsert = returnPOMapper.insert(returnPO);
            if(returnInsert==0){
                return returnInsert;
            }
        }
        // 保存详情图
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        for (String path : detailPicturePathList) {
            ProjectItemPicPO projectItemPicPO = new ProjectItemPicPO();
            projectItemPicPO.setItemPicPath(path);
            projectItemPicPO.setProjectid(projectPOId);
            projectItemPicPOMapper.insert(projectItemPicPO);
        }



        return insert;
    }

    /**
     * 开启事务
     * @param projectId
     * @param typeId
     * @return
     */
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            readOnly = false)
    public int saveProjectType(Integer projectId, Integer typeId) {
        return projectPOMapper.saveTypeProject(projectId,typeId);

    }

    public List<ProjectPO> getProjectByMemberId(Integer memberId) {
        ProjectPOExample projectPOExample = new ProjectPOExample();
        ProjectPOExample.Criteria criteria = projectPOExample.createCriteria();
        criteria.andMemberidEqualTo(memberId);
        List<ProjectPO> projectPOList = projectPOMapper.selectByExample(projectPOExample);

        return projectPOList;
    }
}
