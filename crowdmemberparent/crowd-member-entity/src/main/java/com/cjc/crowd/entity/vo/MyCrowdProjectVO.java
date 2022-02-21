package com.cjc.crowd.entity.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MyCrowdProjectVO {

    // 项目名称
    private String projectName;

    // 完成度
    private double schedule;

    // 剩余时间
    private Integer remainDay;
    // 状态，众筹中，众筹失败
    private Integer status;

    // 募集金额
    private long money;





}
