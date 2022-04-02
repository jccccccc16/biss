package com.atguigu.crowd.entity;

/**
 * 订单详情
 */
public class OrderDetailVO {

    private OrderPO orderPO;

    private OrderProjectPO orderProjectPO;

    private AddressPO addressPO;

    private String email;
    private String realName;
    private String loinAcct;


    public String getLoinAcct() {
        return loinAcct;
    }

    public void setLoinAcct(String loinAcct) {
        this.loinAcct = loinAcct;
    }

    public OrderPO getOrderPO() {
        return orderPO;
    }

    public void setOrderPO(OrderPO orderPO) {
        this.orderPO = orderPO;
    }

    public OrderProjectPO getOrderProjectPO() {
        return orderProjectPO;
    }

    public void setOrderProjectPO(OrderProjectPO orderProjectPO) {
        this.orderProjectPO = orderProjectPO;
    }

    public AddressPO getAddressPO() {
        return addressPO;
    }

    public void setAddressPO(AddressPO addressPO) {
        this.addressPO = addressPO;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
