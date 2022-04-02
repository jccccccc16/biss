package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.OrderDetailVO;
import com.atguigu.crowd.entity.OrderPO;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 订单管理
 */
public interface OrderService {

    public PageInfo<OrderPO> getOrderPageInfo(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据订单号查询详情
     * @param OrderNum
     * @return
     */
    public OrderDetailVO getOrderDetailVOByOrderNum(@Param("orderNum") String OrderNum);
}
