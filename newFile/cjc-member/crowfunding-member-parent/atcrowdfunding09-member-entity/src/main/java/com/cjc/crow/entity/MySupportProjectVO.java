package com.cjc.crow.entity;


import com.cjc.crow.util.CrowdUtil;

import java.io.Serializable;

/**
 * 我支持的众筹
 */
public class MySupportProjectVO implements Serializable {
    // 项目名称
    private String projectName;
    // 订单号
    private String orderNum;
    // 完成度
    private Double percentage;

    // 支持日期
    private String supportDate;
    //支持金额
    private Double supportAmount;
    // 回报数量
    private Integer returnCount;
    // 邮费
    private Double freight;
    // 订单状态
    private Integer orderStatus;
    // 项目状态
    private Integer projectStatus;
    // 剩余天数
    private Integer lastDays;

    // 众筹要求天数
    private Integer day;
    // 项目发起时间
    private String deployDate;

    public String getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(String deployDate) {
        this.deployDate = deployDate;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getLastDays() {

        return lastDays;
    }

    public void setLastDays(Integer lastDays) {
        this.lastDays = lastDays;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getSupportDate() {
        return supportDate;
    }

    public void setSupportDate(String supportDate) {
        this.supportDate = supportDate;
    }

    public Double getSupportAmount() {
        return supportAmount;
    }

    public void setSupportAmount(Double supportAmount) {
        this.supportAmount = supportAmount;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    @Override
    public String toString() {
        return "MySupportProjectVO{" +
                "projectName='" + projectName + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", percentage=" + percentage +
                ", supportDate='" + supportDate + '\'' +
                ", supportAmount=" + supportAmount +
                ", returnCount=" + returnCount +
                ", freight=" + freight +
                ", orderStatus=" + orderStatus +
                ", projectStatus=" + projectStatus +
                ", lastDays=" + lastDays +
                ", day=" + day +
                ", deployDate='" + deployDate + '\'' +
                '}';
    }
}
