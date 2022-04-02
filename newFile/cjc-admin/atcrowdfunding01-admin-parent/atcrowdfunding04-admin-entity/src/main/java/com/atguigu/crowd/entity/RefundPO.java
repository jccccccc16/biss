package com.atguigu.crowd.entity;

public class RefundPO {
    private Integer id;

    private Integer financialPersonId;

    private String orderId;

    private String operTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFinancialPersonId() {
        return financialPersonId;
    }

    public void setFinancialPersonId(Integer financialPersonId) {
        this.financialPersonId = financialPersonId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime == null ? null : operTime.trim();
    }
}