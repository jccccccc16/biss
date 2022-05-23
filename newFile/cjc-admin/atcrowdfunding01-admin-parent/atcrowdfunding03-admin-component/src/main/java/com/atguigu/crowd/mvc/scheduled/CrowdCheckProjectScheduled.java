package com.atguigu.crowd.mvc.scheduled;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.ProjectPO;
import com.atguigu.crowd.service.api.ProjectService;
import com.atguigu.crowd.util.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务，每天零时判断众筹项目是否过期
 */

@Component
public class CrowdCheckProjectScheduled {

    private Logger logger = LoggerFactory.getLogger(CrowdCheckProjectScheduled.class);

    @Autowired
    private ProjectService projectService;

    /**
     * 每天凌晨执行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void doCheckOverDue() {
        logger.info("----定时任务开始执行，判断项目是否过期-----");
        //执行具体业务逻辑----------
        List<ProjectPO> projectCollectingList = projectService.getProjectCollecting();
        int overDue = judgeAndSet(projectCollectingList);
        logger.info("----定时任务执行结束,共有 "+projectCollectingList.size()+" 条数据,其中 "+overDue+"个项目过期-----");
    }

    /**
     * 判断和修改已过期项目
     */
    public int judgeAndSet(List<ProjectPO> projectPoList){
        // 保存过期的数量
        int overDue = 0;
        for (ProjectPO projectPO : projectPoList) {
            // 判断是否过期
            boolean isOverDue = judgeIsOverDue(projectPO);
            // 已过期
            if(isOverDue){
                overDue++;
                logger.info(
                        "项目id："
                                +projectPO.getId()+
                                "项目名称："
                                +projectPO.getProjectName()+
                                "超过众筹期限，众筹失败!");
                setStatus(projectPO);
                projectService.updateSelective(projectPO);
                continue;
            }
        }
        return overDue;
    }

    /**
     * 返回更新条数
     * @param projectPO
     * @return
     */
    private int setStatus(ProjectPO projectPO){
        // 设置状态为状态众4，众筹失败，待退款
        // 设置message
        projectPO.setStatus(4);
        projectPO.setMessage(CrowdConstant.MESSAGE_PROJECT_OVERDUE);
        int i = projectService.updateSelective(projectPO);
        return i;
    }

    /**
     * 判断是否过期，返回true即为过期
     * @param projectPO
     * @return
     */
    private boolean judgeIsOverDue(ProjectPO projectPO){
        Integer dateSub = CrowdUtil.getDateSub(projectPO.getDay(), projectPO.getDeploydate());
        // 如果剩余天数小于等于0
        if(dateSub<=0){
            // 已过期
            return true;
        }
        // 还没过期
        return false;
    }

}
