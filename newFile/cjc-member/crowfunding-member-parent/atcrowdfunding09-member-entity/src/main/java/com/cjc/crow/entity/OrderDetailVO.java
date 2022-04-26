package com.cjc.crow.entity;

import java.io.Serializable;

/**
 * 订单详情
 */
public class OrderDetailVO implements Serializable {

    private Address address;
    // 发票 0为不需要发票，1为需要
    private Integer invoice;
    // 发票抬头
    private String invoiceTitle;

    private String orderRemark;

    private OrderProjectVO orderProjectVO;




    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public OrderProjectVO getOrderProjectVO() {
        return orderProjectVO;
    }

    public void setOrderProjectVO(OrderProjectVO orderProjectVO) {
        this.orderProjectVO = orderProjectVO;
    }


    @Override
    public String toString() {
        return "OrderDetailVO{" +
                "address=" + address +
                ", invoice=" + invoice +
                ", invoiceTitle='" + invoiceTitle + '\'' +
                ", orderRemark='" + orderRemark + '\'' +
                ", orderProjectVO=" + orderProjectVO +
                '}';
    }
}
