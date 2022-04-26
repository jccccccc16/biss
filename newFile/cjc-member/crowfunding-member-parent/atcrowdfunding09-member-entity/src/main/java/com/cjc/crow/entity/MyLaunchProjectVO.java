package com.cjc.crow.entity;

import com.cjc.crow.util.CrowdUtil;

import java.io.Serializable;

public class MyLaunchProjectVO implements Serializable {
    private String projectId;
    private String projectName;
    private Integer day;
    private String deployDate;
    private Integer percentage;
    private Integer money;
    private Integer status;
    private Integer lastDays;
    private String statusString;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(String deployDate) {
        this.deployDate = deployDate;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLastDays() {

        return lastDays;
    }

    public void setLastDays(Integer lastDays) {
        this.lastDays = lastDays;
    }

    public String getStatusString() {
        return CrowdUtil.getProjectStatusString(this.getStatus());
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    @Override
    public String toString() {
        return "MyLaunchProjectVO{" +
                "projectName='" + projectName + '\'' +
                ", day=" + day +
                ", deployDate='" + deployDate + '\'' +
                ", percentage=" + percentage +
                ", money=" + money +
                ", status=" + status +
                ", lastDays=" + lastDays +
                ", statusString='" + statusString + '\'' +
                '}';
    }
}
