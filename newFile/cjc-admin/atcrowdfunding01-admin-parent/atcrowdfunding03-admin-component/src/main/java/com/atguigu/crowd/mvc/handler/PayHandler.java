package com.atguigu.crowd.mvc.handler;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.atguigu.crowd.entity.OrderPO;
import com.atguigu.crowd.service.api.OrderService;
import com.atguigu.crowd.service.impl.AliPayService;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayHandler {
    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping("/alipay/refund.json")
    public ResultEntity refund(@RequestParam("orderId")Integer orderId){
        OrderPO orderPO = orderService.getOrderPOById(orderId);
        if(orderPO==null){
            return ResultEntity.failed("没有该订单");
        }
        String payOrderNum = orderPO.getPayOrderNum();
        Double orderAmount = orderPO.getOrderAmount();
        String orderNum = orderPO.getOrderNum();
        AlipayTradeRefundResponse response = aliPayService.refund(orderNum,payOrderNum, orderAmount);
        if(response.isSuccess()){
            // 修改为4，已退款
            orderPO.setStatus(5);
            orderService.updateOrder(orderPO);
            return ResultEntity.successWithoutData();
        }
        // 判断该项目是否已经全部退款啊
        return ResultEntity.failed("退款失败请重试");
    }
}
