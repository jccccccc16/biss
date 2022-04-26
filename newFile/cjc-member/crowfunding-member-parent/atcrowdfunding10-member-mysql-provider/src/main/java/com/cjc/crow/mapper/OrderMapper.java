package com.cjc.crow.mapper;

import com.cjc.crow.entity.*;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    int countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    OrderProjectVO selectProjectReturn(@Param("returnId")Integer returnId);

    int insertReturnAndOrderId(@Param("orderId") Integer orderId,@Param("returnId")Integer returnId);

    OrderDetailVO selectOrderDetailByOrderNum(@Param("orderNum")String orderNum);
}