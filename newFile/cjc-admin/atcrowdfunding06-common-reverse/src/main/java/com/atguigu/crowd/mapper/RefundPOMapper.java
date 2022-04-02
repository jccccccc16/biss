package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.RefundPO;
import com.atguigu.crowd.entity.RefundPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RefundPOMapper {
    int countByExample(RefundPOExample example);

    int deleteByExample(RefundPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RefundPO record);

    int insertSelective(RefundPO record);

    List<RefundPO> selectByExample(RefundPOExample example);

    RefundPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RefundPO record, @Param("example") RefundPOExample example);

    int updateByExample(@Param("record") RefundPO record, @Param("example") RefundPOExample example);

    int updateByPrimaryKeySelective(RefundPO record);

    int updateByPrimaryKey(RefundPO record);
}