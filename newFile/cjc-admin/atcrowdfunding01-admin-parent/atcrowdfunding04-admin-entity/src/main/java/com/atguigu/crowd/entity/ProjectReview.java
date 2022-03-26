package com.atguigu.crowd.entity;

import java.io.Serializable;

public class ProjectReview implements Serializable {
    private ProjectPO projectPO;
    // 0 为待审核，1为审核成功，2为审核不通过

    // 携带的消息
    private String message;
    // 项目发起人
    private String loginAcct;

    public ProjectReview() {
    }

    public ProjectReview(ProjectPO projectPO, String message, String loginAcct) {
        this.projectPO = projectPO;
        this.message = message;
        this.loginAcct = loginAcct;
    }

    public ProjectReview(ProjectPO projectPO, String loginAcct) {
        this.projectPO = projectPO;
        this.loginAcct = loginAcct;
        this.message = "";
    }

    public ProjectPO getProjectPO() {
        return projectPO;
    }

    public void setProjectPO(ProjectPO projectPO) {
        this.projectPO = projectPO;
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


    @Override
    public String toString() {
        return "ProjectReview{" +
                "projectPO=" + projectPO +
                ", message='" + message + '\'' +
                ", loginAcct='" + loginAcct + '\'' +
                '}';
    }
}
