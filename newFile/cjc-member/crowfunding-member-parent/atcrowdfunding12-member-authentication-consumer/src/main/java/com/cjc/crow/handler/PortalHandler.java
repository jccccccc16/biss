package com.cjc.crow.handler;

import com.cjc.crow.api.MySqlRemoteService;
import com.cjc.crow.constant.CrowdConstant;
import com.cjc.crow.entity.PortalProjectVO;
import com.cjc.crow.entity.PortalTypeVO;
import com.cjc.crow.entity.ProjectVO;
import com.cjc.crow.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2020/11/4
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 **/
@Controller
public class PortalHandler {

    private Logger logger = LoggerFactory.getLogger(PortalHandler.class);

    @Resource
    private MySqlRemoteService mySqlRemoteService;


    /**
     * 映射到主页面
     *
     * @return
     */
    @RequestMapping("/")
    public String showPortalPage(ModelMap map,
        @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
        @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize
    ) {

        // 不通过分类展示
//        ResultEntity<List<PortalTypeVO>> portalTypeVOListResultEntity
//                = mySqlRemoteService.getPortalTypeVORemote();
//
//        List<PortalTypeVO> portalTypeVOList
//                = portalTypeVOListResultEntity.getData();
//        map.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_TYPE_VO_LIST, portalTypeVOList);
        List<PortalProjectVO> portalProjectVOList = new ArrayList<PortalProjectVO>();
        ResultEntity<PageInfo<PortalProjectVO>> pageInfoResultEntity
                = mySqlRemoteService.getPortalProjectVO(pageNum,pageSize);
        PageInfo<PortalProjectVO> pageInfo = pageInfoResultEntity.getData();
        map.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        logger.debug(pageInfo.toString());
        return "portal";
    }





}
