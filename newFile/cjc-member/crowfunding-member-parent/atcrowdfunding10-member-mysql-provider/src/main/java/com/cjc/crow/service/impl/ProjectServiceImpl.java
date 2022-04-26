package com.cjc.crow.service.impl;

import com.cjc.crow.entity.*;
import com.cjc.crow.mapper.*;
import com.cjc.crow.service.api.ProjectService;


import com.cjc.crow.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2020/11/15
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 * 保存project
 **/
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectPOMapper projectPOMapper;

    @Resource
    private ReturnPOMapper returnPOMapper;

    @Resource
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Resource
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Resource
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

    private Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void saveProject(ProjectVO projectVO, Integer memberId) {

        ProjectPO projectPO = new ProjectPO();

        BeanUtils.copyProperties(projectVO,projectPO);
        projectPO.setStatus(0); // 设置默认审核状态为0，待审核

        // 设置memberId
        projectPO.setMemberid(memberId);


        // createTime设置时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        projectPO.setCreatedate(date);

        // 发布日期，这里选择与创建日期一样
        projectPO.setDeploydate(date);

        // 以筹集的金额，设置为0
        long supportedMoney = 0;
        projectPO.setSupportmoney(supportedMoney);

        // 关注者
        projectPO.setFollower(0);

        // 支持者
        projectPO.setSupporter(0);

        // 进度
        projectPO.setCompletion(0);


        // 设置状态
        projectPO.setStatus(0);

        // 设置审核状态，默认为0，待审核状态
        projectPO.setReviewStatus(0);

        projectPOMapper.insertSelective(projectPO);

        // 获取自增id

        Integer projectId = projectPO.getId();

        logger.info("获取的自增projectId为： "+projectId);


        // 插入类型

//        List<Integer> typeIdList = projectVO.getTypeIdList();
//
//        projectPOMapper.insertTypeRelationship(typeIdList,projectId);

        // 插入标签

        List<Integer> tagIdList = projectVO.getTagIdList();

//        projectPOMapper.insertTagRelationship(tagIdList,projectId);

        // 插入详情图片
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();

        projectItemPicPOMapper.insertDetailPathList(detailPicturePathList,projectId);

        // 发起人
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();

        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();

        BeanUtils.copyProperties(memberLauchInfoVO,memberLaunchInfoPO);

        memberLaunchInfoPO.setMemberid(memberId);

        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);

        // 回报
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();

        List<ReturnPO> returnPOList = new ArrayList<ReturnPO>();

        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO,returnPO);
            returnPOList.add(returnPO);
        }

        // 批量插入returnPO
        returnPOMapper.insertReturnBatch(returnPOList,projectId);

        // 项目确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();

        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();

        BeanUtils.copyProperties(memberConfirmInfoVO,memberConfirmInfoPO);

        memberConfirmInfoPO.setMemberid(memberId);

        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);

    }

    public List<PortalTypeVO> getPortalTypeVOList() {
        List<PortalTypeVO> portalTypeVOS = projectPOMapper.selectPortalTypeVOList();
        return portalTypeVOS;

    }

    public ProjectDetailVO getDetailProjectVO(Integer id) {

        ProjectDetailVO projectDetailVO = projectPOMapper.selectDetailProjectVO(id);

        return projectDetailVO;
    }

    /**
     *
     * 获取我支持的项目
     *
     * @param memberId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<MySupportProjectVO> getMySupportProjectVOList(Integer memberId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(1,3);
        List<MySupportProjectVO> mySupportProjectVOList = projectPOMapper.selectMySupportProjectVO(memberId);
        logger.info("查询到该用户所支持的项目："+mySupportProjectVOList);
        for (MySupportProjectVO mySupportProjectVO : mySupportProjectVOList) {
           mySupportProjectVO.setLastDays(CrowdUtil.getDateSub(mySupportProjectVO.getDay(),mySupportProjectVO.getDeployDate()));
        }

        return new PageInfo<MySupportProjectVO>(mySupportProjectVOList);
    }

    public PageInfo<MyLaunchProjectVO> getMyLaunchProjectVOPageInfo(Integer memberId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<MyLaunchProjectVO> myLaunchProjectVOS = projectPOMapper.selectMyLaunchProjectList(memberId);
        for (MyLaunchProjectVO myLaunchProjectVO : myLaunchProjectVOS) {
            myLaunchProjectVO.setLastDays( CrowdUtil.getDateSub(myLaunchProjectVO.getDay(), myLaunchProjectVO.getDeployDate()));
        }
        logger.info("查询到该用户所发起的项目："+myLaunchProjectVOS);
        return new PageInfo<MyLaunchProjectVO>(myLaunchProjectVOS);
    }

    public int updateProjectSupporter(Integer projectId) {
        return projectPOMapper.updateSupporter(projectId);
    }

    public PageInfo<PortalProjectVO> getPortalProjectList(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<PortalProjectVO> portalProjectVOList = projectPOMapper.selectPortalProjectList();
        logger.info(portalProjectVOList.toString());
        return new PageInfo<PortalProjectVO>(portalProjectVOList);
    }


}
