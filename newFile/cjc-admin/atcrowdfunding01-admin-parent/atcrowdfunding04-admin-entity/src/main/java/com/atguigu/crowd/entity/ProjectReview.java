package com.atguigu.crowd.entity;

import java.io.Serializable;

public class ProjectReview implements Serializable {
    private ProjectPO projectPO;
    // 0 为待审核，1为审核成功，2为审核不通过

    // 携带的消息
    private String message;
    // 项目发起人
    private String loginAcct;

    // 审核员
    private String reviewAdmin;
    // 审核日期
    private String reviewDate;

    private String remark;

    private String projectStatus;

    public ProjectReview() {
    }

    public ProjectReview(ProjectPO projectPO, String message, String loginAcct) {
        this.projectPO = projectPO;
        this.message = message;
        this.loginAcct = loginAcct;
    }

    /**
     * 用于创建没有错误信息的project
     * @param projectPO
     * @param loginAcct
     */
    public ProjectReview(ProjectPO projectPO, String loginAcct) {
        this.projectPO = projectPO;
        this.loginAcct = loginAcct;
        this.message = "";
    }


    public ProjectReview(ProjectPO projectPO, String message, String loginAcct, String reviewAdmin) {
        this.projectPO = projectPO;
        this.message = message;
        this.loginAcct = loginAcct;
        this.reviewAdmin = reviewAdmin;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewAdmin() {
        return reviewAdmin;
    }

    public void setReviewAdmin(String reviewAdmin) {
        this.reviewAdmin = reviewAdmin;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
