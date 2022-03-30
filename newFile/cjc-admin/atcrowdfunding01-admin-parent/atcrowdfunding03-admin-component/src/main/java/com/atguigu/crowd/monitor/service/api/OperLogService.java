package com.atguigu.crowd.monitor.service.api;

import com.atguigu.crowd.entity.OperLog;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface OperLogService {
    /**
     * 记录一条日志操作
     *
     * @param operLog
     * @return 返回插入的条数
     */
    public int saveOperLog(OperLog operLog);

    /**
     * 获取日志记录
     *
     * @return
     */
    public PageInfo<OperLog> getOperLogList(Integer pageNum, Integer pageSize, String keyword, String datePre, String datePost);

}
