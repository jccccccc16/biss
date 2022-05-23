package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.OrderDetailVO;
import com.atguigu.crowd.entity.OrderPO;
import com.atguigu.crowd.service.api.OrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
public class OrderHandler {

    @Autowired
    private OrderService orderService;


    /**
     *
     * 获取订单列表
     *
     * @param pageSize
     * @param pageNum
     * @param keyword
     * @param modelMap
     * @return
     */
    @RequestMapping("/get/order/page.html")
    public String getOrderPage(
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            ModelMap modelMap
    ){
        PageInfo<OrderPO> orderPageInfo =
                orderService.getOrderPageInfo(keyword, pageNum, pageSize,null);
        modelMap.addAttribute("pageInfo",orderPageInfo);
        return "order-page";
    }

    /**
     *
     * 获取订单详情
     *
     * @param orderNum
     * @param modelMap
     * @return
     */
    @RequestMapping("/get/order/detail/page.html")
    public String getOrderDetail(
            @RequestParam("orderNum")String orderNum,
            ModelMap modelMap
    ){

        OrderDetailVO orderDetailVO = orderService.getOrderDetailVOByOrderNum(orderNum);
        modelMap.addAttribute("orderDetailVO",orderDetailVO);
        return "order-detail";
    }


    @RequestMapping("/get/order/refund.html")
    public String getOrderRefundPage( @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                      @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                      ModelMap modelMap){
        PageInfo<OrderPO> orderPageInfo =
                orderService.getOrderPageInfo("", pageNum, pageSize,4);
        modelMap.addAttribute("pageInfo",orderPageInfo);
        return "order-refund-page";
    }

}
