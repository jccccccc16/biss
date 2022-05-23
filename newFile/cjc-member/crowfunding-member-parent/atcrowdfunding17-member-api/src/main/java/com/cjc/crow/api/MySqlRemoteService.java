package com.cjc.crow.api;

import com.cjc.crow.entity.*;
import com.cjc.crow.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2020/11/1
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 **/
@FeignClient("crow-mysql")
public interface MySqlRemoteService {




    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<Member> getMemberByLoginAcct(@RequestParam("loginacct") String loginAcct);

    @RequestMapping("/save/member/remote")
    public ResultEntity<Member> saveMember(@RequestBody Member member);

    @ResponseBody
    @RequestMapping("project/get/my/launch/project/by/{memberId}/remote")
    public ResultEntity<PageInfo<MyLaunchProjectVO>> getMyLaunchProjectVOPageInfo(
            @PathVariable("memberId") Integer memberId,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue ="3" ) Integer pageSize
    );

    @ResponseBody
    @RequestMapping("/save/projectVO")
    public ResultEntity<String> saveProjectVORemote(

            @RequestBody ProjectVO projectVO,
            @RequestParam("memberId") Integer memberId
    );

    @ResponseBody
    @RequestMapping("/get/portal/VO/remote")
    public  ResultEntity<PageInfo<PortalProjectVO>> getPortalProjectVO(
            @RequestParam("pageNum")Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    );

    @ResponseBody
    @RequestMapping("/get/portal/VO/remote")
    public ResultEntity<List<PortalProjectVO>> PortalProjectVO();

    @ResponseBody
    @RequestMapping("/get/portal/type/VO/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeVORemote();


    @ResponseBody
    @RequestMapping("/get/project/detail/by/project/id/remote/{id}")
    public ResultEntity<ProjectDetailVO>
        getProjectDetailByProjectId(@PathVariable("id") Integer projectId);


    @ResponseBody
    @RequestMapping("/get/order/project/vo/remote")
    public ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("projectId") Integer projectId, @RequestParam("returnId") Integer returnId);

    @ResponseBody
    @RequestMapping("/get/address/remote")
    ResultEntity<List<Address>> getAddressRemote(@RequestParam("id")Integer id);

    @ResponseBody
    @RequestMapping("/save/address/remote")
    ResultEntity<Address> saveAddressRemote(@RequestBody Address address);

    @RequestMapping("/save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO);


    /**
     * 获取我支持的列表
     * @param memberId
     * @return
     */
    @ResponseBody
    @RequestMapping("/get/my/support/project/by/{memberId}/remote")
    public ResultEntity<PageInfo<MySupportProjectVO>> getMySupportProjectVOList(
            @PathVariable("memberId") Integer memberId,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue ="3" ) Integer pageSize
    );


    /**
     * 更新项目支持人数
     * 由于数据库表的设计问题，使用returnId来查询并来修改项目支持人数
     *
     * @param returnId
     * @return
     */
    @ResponseBody
    @RequestMapping("update/project/supporter/by{returnId}")
    public ResultEntity<String> updateProjectSupporterByReturn(
            @PathVariable("returnId") Integer returnId
    );

    /**
     * 获取订单详情
     *
     * @param orderNum
     * @return
     */
    @ResponseBody
    @RequestMapping("/get/order/detail/by/{orderNum}")
    ResultEntity<OrderDetailVO> getOrderDetailByOrderNum(
            @PathVariable("orderNum") String  orderNum
    );

    @ResponseBody
    @RequestMapping("/get/project/po/by/{id}")
    ResultEntity<ProjectPO> getProjectById(@PathVariable("id")Integer id);

    @ResponseBody
    @RequestMapping("/remove/project/po/by/{id}")
    ResultEntity<Integer> removeProject(Integer id);
}