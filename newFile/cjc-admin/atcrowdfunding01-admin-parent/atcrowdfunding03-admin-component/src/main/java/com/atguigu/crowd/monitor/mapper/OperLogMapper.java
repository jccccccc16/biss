package com.atguigu.crowd.monitor.mapper;

import com.atguigu.crowd.entity.OperLog;
import com.atguigu.crowd.entity.OperLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 日志操作mapper
 */
public interface OperLogMapper {
    int countByExample(OperLogExample example);

    int deleteByExample(OperLogExample example);

    int deleteByPrimaryKey(String pkId);

    int insert(OperLog record);

    int insertSelective(OperLog record);

    List<OperLog> selectByExample(OperLogExample example);

    OperLog selectByPrimaryKey(String pkId);

    int updateByExampleSelective(@Param("record") OperLog record, @Param("example") OperLogExample example);

    int updateByExample(@Param("record") OperLog record, @Param("example") OperLogExample example);

    int updateByPrimaryKeySelective(OperLog record);

    int updateByPrimaryKey(OperLog record);

    List<OperLog> selectOperLogList( @Param("keyword") String keyword,@Param("datePre")String datePre,@Param("datePost")String datePost);
}