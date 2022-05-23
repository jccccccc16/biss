package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.OrderDetailVO;
import com.atguigu.crowd.entity.OrderPO;
import com.atguigu.crowd.entity.OrderPOExample;
import com.atguigu.crowd.mapper.OrderPOMapper;
import com.atguigu.crowd.service.api.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderPOMapper orderPOMapper;

    /**
     * 获取订单列表
     *keyword是订单编号
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<OrderPO> getOrderPageInfo(String keyword, Integer pageNum, Integer pageSize,Integer status) {

        PageHelper.startPage(pageNum,pageSize,"create_time desc");
        List<OrderPO> orderPOList =  orderPOMapper.selectOrderList(keyword,status);
        return new PageInfo<OrderPO>(orderPOList);
    }

    /**
     * 根据订单编号获取详情
     * @param orderNum
     * @return
     */
    @Override
    public OrderDetailVO getOrderDetailVOByOrderNum(String orderNum) {
        OrderDetailVO orderDetailVO = orderPOMapper.selectOrderDetail(orderNum);
        return orderDetailVO;
    }

    @Override
    public OrderPO getOrderPOById(Integer id) {
        OrderPO orderPO = orderPOMapper.selectByPrimaryKey(id);
        return orderPO;
    }

    @Override
    public int updateOrder(OrderPO orderPO) {
        OrderPOExample orderPOExample = new OrderPOExample();
        orderPOExample.createCriteria().andIdEqualTo(orderPO.getId());
       return orderPOMapper.updateByExample(orderPO,orderPOExample);

    }
}
