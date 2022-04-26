package com.cjc.crow.handler;

import com.cjc.crow.constant.CrowdConstant;
import com.cjc.crow.entity.*;
import com.cjc.crow.service.api.ProjectService;
import com.cjc.crow.util.CrowdUtil;
import com.cjc.crow.util.ResultEntity;
import com.cjc.crow.util.SmallUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2020/11/15
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 **/
@Controller
public class ProjectProviderHandler {


    @Autowired
    private ProjectService projectService;

    private Logger logger = LoggerFactory.getLogger(ProjectProviderHandler.class);


    @ResponseBody
    @RequestMapping("project/get/my/launch/project/by/{memberId}/remote")
    public ResultEntity<PageInfo<MyLaunchProjectVO>> getMyLaunchProjectVOPageInfo(
            @PathVariable("memberId") Integer memberId,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue ="3" ) Integer pageSize
    ){

        PageInfo<MyLaunchProjectVO> myLaunchProjectVOPageInfo = projectService.getMyLaunchProjectVOPageInfo(memberId, pageNum, pageSize);
        logger.info("查询到的用户所发起的众筹项目："+myLaunchProjectVOPageInfo);
        return ResultEntity.successWithData(myLaunchProjectVOPageInfo);

    }

    @ResponseBody
    @RequestMapping("/get/portal/type/VO/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeVORemote(){

        try {
            List<PortalTypeVO> portalTypeVOList = projectService.getPortalTypeVOList();
            ResultEntity<List<PortalTypeVO>> listResultEntity = ResultEntity.successWithData(portalTypeVOList);
            return listResultEntity;
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }

    }

    /***
     * 获取在众筹中的项目
     * @return
     */
    @ResponseBody
    @RequestMapping("/get/portal/VO/remote")
    public  ResultEntity<PageInfo<PortalProjectVO>> getPortalProjectVO(
            @RequestParam("pageNum")Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    ){

        try {
            // 获取数据
            PageInfo<PortalProjectVO> pageInfo = projectService.getPortalProjectList(pageNum,pageSize);
            ResultEntity<PageInfo<PortalProjectVO>> listResultEntity = ResultEntity.successWithData(pageInfo);
            return listResultEntity;
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }

    }

    /**
     *
     * 获取我支持的众筹项目
     *
     * @param memberId
     * @return
     */
    @ResponseBody
    @RequestMapping("/get/my/support/project/by/{memberId}/remote")
    public ResultEntity<PageInfo<MySupportProjectVO>> getMySupportProjectVOList(
            @PathVariable("memberId") Integer memberId,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue ="3" ) Integer pageSize
    ){
        try{
            pageNum = 1;
            pageSize = 3;
            PageInfo<MySupportProjectVO> mySupportProjectVOList = projectService.getMySupportProjectVOList(memberId,pageNum,pageSize);
            logger.info("查询到的该用户所支持的项目： "+mySupportProjectVOList.toString());
            return ResultEntity.successWithData(mySupportProjectVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    @ResponseBody
    @RequestMapping("/save/projectVO")
    public ResultEntity<String>  saveProjectVORemote(

            @RequestBody ProjectVO projectVO,
            @RequestParam("memberId") Integer memberId
    ){

        try {
            projectService.saveProject(projectVO,memberId);

            return ResultEntity.successWithoutData();

        } catch (Exception exception) {

            exception.printStackTrace();

            return ResultEntity.failed(exception.getMessage());

        }


    }



    @ResponseBody
    @RequestMapping("/get/project/detail/by/project/id/remote/{id}")
    public ResultEntity<ProjectDetailVO> getProjectDetailByProjectId(@PathVariable("id") Integer projectId) {

        try {

            ProjectDetailVO projectDetailVO = projectService.getDetailProjectVO(projectId);

            if (projectDetailVO == null) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_RESOURCE_VISIT_EXCEPTION);
            }

            return ResultEntity.successWithData(projectDetailVO);

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }

    }


    /**
     *
     * 更新项目支持人数
     *
     * @param returnId
     * @return
     */
    @ResponseBody
    @RequestMapping("update/project/supporter/by{returnId}")
    public ResultEntity<String> updateProjectSupporterByReturn(
            @PathVariable("returnId") Integer returnId

    ){
        int i = projectService.updateProjectSupporter(returnId);
        if(i==1){
            return ResultEntity.successWithoutData();
        }
        return ResultEntity.failed("更新项目支持人数失败");
    }

}
