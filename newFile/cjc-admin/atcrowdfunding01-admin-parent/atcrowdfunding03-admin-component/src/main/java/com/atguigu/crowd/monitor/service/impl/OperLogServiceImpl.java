package com.atguigu.crowd.monitor.service.impl;

import com.atguigu.crowd.entity.OperLog;
import com.atguigu.crowd.entity.OperLogExample;
import com.atguigu.crowd.monitor.mapper.OperLogMapper;
import com.atguigu.crowd.monitor.service.api.OperLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class OperLogServiceImpl implements OperLogService {

    @Autowired
    private OperLogMapper operLogMapper;
    private Logger logger = LoggerFactory.getLogger(OperLogServiceImpl.class);
    @Override
    public int saveOperLog(OperLog operLog) {
        int insert = operLogMapper.insert(operLog);
        return insert;
    }

    /**
     *
     *      *
     *      * 按照使时间倒叙查询
     *      * keyword查询 业务类型，method方法，请求方法，operName
     *      * @param pageNum
     *      * @param pageSize
     *      * @param keyword 查询
     *      * @return
     *
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @param datePre  查询时间间隔
     * @param datePost
     * @return
     */
    @Override
    public PageInfo<OperLog> getOperLogList(Integer pageNum, Integer pageSize, String keyword,String datePre,String datePost) {

        // 根据日期倒序获取日志

        PageHelper.startPage(pageNum, pageSize);
        List<OperLog> operLogList = operLogMapper.selectOperLogList(keyword,datePre,datePost);
        logger.info("查询到的日志"+operLogList.toString());
        return new PageInfo<OperLog>(operLogList);
    }
}
