package com.cjc.crow.handler;

import com.cjc.crow.api.MySqlRemoteService;
import com.cjc.crow.constant.CrowdConstant;
import com.cjc.crow.entity.Address;
import com.cjc.crow.entity.MemberLoginVO;
import com.cjc.crow.entity.OrderDetailVO;
import com.cjc.crow.entity.OrderProjectVO;
import com.cjc.crow.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2021/1/23
 * Time: 16:13
 * To change this template use File | Settings | File Templates.
 **/
@Controller
public class OrderHandler {

    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    private Logger logger = LoggerFactory.getLogger(OrderHandler.class);

    /**
     * 获取项目的具体回报
     *
     * @param projectId
     * @param returnId
     * @param session
     * @return
     */
    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirmInfo(@PathVariable("projectId") Integer projectId,
                                        @PathVariable("returnId") Integer returnId,
                                        HttpSession session){

         // 获取该项目的回报项目相关信息
        ResultEntity<OrderProjectVO> resultEntity = mySqlRemoteService.getOrderProjectVORemote(projectId,returnId);


        // 如果获取成功
        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            OrderProjectVO data = resultEntity.getData();
            // 初始化 汇报数量为1
            data.setReturnId(returnId);
            data.setReturnCount(1);
            data.setProjectId(projectId);
            session.setAttribute("orderProjectVO",data);
            session.setAttribute("projectId",projectId);
            logger.info("查找到的data :" + data);
        }else{
            logger.info(resultEntity+"");
            logger.info("查找OrderProjectVO失败");
        }
        return "confirm_return";

    }


    /**
     * 确定订单,结算页面
     * @param returnCount
     * @param session
     * @return
     */
    @RequestMapping("/confirm/order/{returnCount}")
    public String confirmOrder(
            @PathVariable("returnCount") Integer returnCount,
            HttpSession session                   ){

        // 修改session域中的returnCount
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        orderProjectVO.setReturnCount(returnCount);

        // 获取该用户信息
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        Integer id = memberLoginVO.getId();
        // 查找该用户对应的地址
        ResultEntity<List<Address>> resultEntityList = mySqlRemoteService.getAddressRemote(id);

        if (ResultEntity.SUCCESS.equals(resultEntityList.getResult())) {

            List<Address> data = resultEntityList.getData();
            logger.info("addressList:"+data);
            session.setAttribute("addressList", data);
        }

        return "confirm_order";
    }

    @ResponseBody
    @RequestMapping("/save/address.json")
    public ResultEntity<Address> saveAddress(@RequestBody Address address){
        logger.info("address:"+address.toString());
        ResultEntity<Address> resultEntity = mySqlRemoteService.saveAddressRemote(address);
        logger.info(resultEntity.toString());
        return resultEntity;
    }

    /**
     * 获取订单详情
     * @param orderNum
     * @param modelMap
     * @return
     */
    @GetMapping("/get/detail/{orderNum}")
    public String getOrderDetail(
            @PathVariable("orderNum") String orderNum,
            ModelMap modelMap
    ){
        ResultEntity<OrderDetailVO> resultEntity = mySqlRemoteService.getOrderDetailByOrderNum(orderNum);
        OrderDetailVO data = resultEntity.getData();
        if(data!=null){
            logger.info(data.toString());
            modelMap.addAttribute("orderDetail",data);
            return "order-detail";
        }
        throw new RuntimeException("订单为空");
    }
}
