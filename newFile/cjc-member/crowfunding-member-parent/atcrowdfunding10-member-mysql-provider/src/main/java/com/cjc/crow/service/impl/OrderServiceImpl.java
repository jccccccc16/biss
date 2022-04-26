package com.cjc.crow.service.impl;

import com.cjc.crow.constant.CrowdConstant;
import com.cjc.crow.entity.*;
import com.cjc.crow.mapper.AddressMapper;
import com.cjc.crow.mapper.OrderMapper;
import com.cjc.crow.mapper.OrderProjectMapper;
import com.cjc.crow.service.api.OrderService;
import com.cjc.crow.service.api.ProjectService;
import com.cjc.crow.util.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2021/1/23
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 **/
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderProjectMapper orderProjectMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private AddressMapper addressMapper;

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private ProjectService projectService;

    public OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId) {

        return orderMapper.selectProjectReturn(returnId);

    }

    public List<Address> getAddressByMemberId(Integer memberId) {
        List<Address> addressList = addressMapper.selectAddressByMemberId(memberId);
        return addressList;
    }

    public Address saveAddress(Address address) {
        addressMapper.insert(address);
        return address;
    }

    /**
     * 保存订单
     * @param orderVO
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveOrder(OrderVO orderVO) {

        Order orderPO = new Order();

        BeanUtils.copyProperties(orderVO, orderPO);

        OrderProject orderProjectPO = new OrderProject();

        BeanUtils.copyProperties(orderVO.getOrderProjectVO(), orderProjectPO);
        orderPO.setCreateTime(CrowdUtil.getNow(CrowdConstant.DATE_PATTERN));
        // 设置order状态为1，已支付
        orderPO.setStatus(1);
        // 保存orderPO自动生成的主键是orderProjectPO需要用到的外键
        orderMapper.insert(orderPO);

        // 从orderPO中获取orderId
        Integer id = orderPO.getId();

        // 更新项目支持人数


        // 将orderId设置到orderProjectPO
        orderProjectPO.setOrderId(id);
        // 保存回报关联订单
        orderMapper.insertReturnAndOrderId(orderPO.getId(),orderProjectPO.getReturnId());
        // 保存项目信息，回报订单
        orderProjectMapper.insert(orderProjectPO);
    }

    /**
     * 获取订单详情
     * @param orderNum
     * @return
     */
    public OrderDetailVO getOrderDetailVOByDetailNum(String orderNum) {
        OrderDetailVO orderDetailVO = orderMapper.selectOrderDetailByOrderNum(orderNum);
        logger.info(orderDetailVO.toString());
        return orderDetailVO;
    }


}
