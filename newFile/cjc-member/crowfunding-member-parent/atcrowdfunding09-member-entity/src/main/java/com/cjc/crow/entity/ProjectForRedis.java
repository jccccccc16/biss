package com.cjc.crow.entity;

import java.io.Serializable;

public class ProjectForRedis implements Serializable {
    private ProjectVO projectVO;
    // 0 为待审核，1为审核成功，2为审核不通过
    private Integer status;
    // 携带的消息
    private String message;
    // 项目所属人
    private String loginAcct;

    // 会员id
    private Integer memberId;

    public ProjectVO getProjectVO() {
        return projectVO;
    }

    public void setProjectVO(ProjectVO projectVO) {
        this.projectVO = projectVO;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "ProjectForRedis{" +
                "projectVO=" + projectVO +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", loginAcct='" + loginAcct + '\'' +
                ", memberId=" + memberId +
                '}';
    }
}
