package com.cjc.crow.entity;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2021/1/27
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 **/
public class OrderVO implements Serializable {

    private OrderProjectVO orderProjectVO;

    private Integer id;

    private String orderNum;

    private String payOrderNum;

    private Double orderAmount;

    private Integer invoice;

    private String invoiceTitle;

    private String orderRemark;

    private Integer addressId;

    // 订单创建时间
    private String createTime;
    // 发货时间
    private String sendTime;

    // 确认订单时间
    private String confirmTime;
    // 退款时间
    private String returnTime;

    private Integer status;

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public String getPayOrderNum() {
        return payOrderNum;
    }

    public void setPayOrderNum(String payOrderNum) {
        this.payOrderNum = payOrderNum == null ? null : payOrderNum.trim();
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
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
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark == null ? null : orderRemark.trim();
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public OrderProjectVO getOrderProjectVO() {
        return orderProjectVO;
    }

    public void setOrderProjectVO(OrderProjectVO orderProjectVO) {
        this.orderProjectVO = orderProjectVO;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "orderProjectVO=" + orderProjectVO +
                ", id=" + id +
                ", orderNum='" + orderNum + '\'' +
                ", payOrderNum='" + payOrderNum + '\'' +
                ", orderAmount=" + orderAmount +
                ", invoice=" + invoice +
                ", invoiceTitle='" + invoiceTitle + '\'' +
                ", orderRemark='" + orderRemark + '\'' +
                ", addressId=" + addressId +
                '}';
    }
}
