package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.ReviewProjectAdmin;
import com.atguigu.crowd.entity.ReviewProjectAdminExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewProjectAdminMapper {
    int countByExample(ReviewProjectAdminExample example);

    int deleteByExample(ReviewProjectAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReviewProjectAdmin record);

    int insertSelective(ReviewProjectAdmin record);

    List<ReviewProjectAdmin> selectByExample(ReviewProjectAdminExample example);

    ReviewProjectAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReviewProjectAdmin record, @Param("example") ReviewProjectAdminExample example);

    int updateByExample(@Param("record") ReviewProjectAdmin record, @Param("example") ReviewProjectAdminExample example);

    int updateByPrimaryKeySelective(ReviewProjectAdmin record);

    int updateByPrimaryKey(ReviewProjectAdmin record);



}