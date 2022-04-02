package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.OrderDetailVO;
import com.atguigu.crowd.entity.OrderPO;
import com.atguigu.crowd.entity.OrderPOExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

import java.util.List;

public interface OrderPOMapper {
    int countByExample(OrderPOExample example);

    int deleteByExample(OrderPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderPO record);

    int insertSelective(OrderPO record);

    List<OrderPO> selectByExample(OrderPOExample example);

    OrderPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderPO record, @Param("example") OrderPOExample example);

    int updateByExample(@Param("record") OrderPO record, @Param("example") OrderPOExample example);

    int updateByPrimaryKeySelective(OrderPO record);

    int updateByPrimaryKey(OrderPO record);

    List<OrderPO> selectOrderList(@Param("keyword") String keyword);


    OrderDetailVO selectOrderDetail(String orderNum);
}