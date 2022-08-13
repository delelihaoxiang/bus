package com.bus.mapper;

import com.bus.pojo.StationInfo;
import com.bus.pojo.StationInfoExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StationInfoMapper {
    long countByExample(StationInfoExample example);

    int deleteByExample(StationInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StationInfo record);

    int insertSelective(StationInfo record);

    List<StationInfo> selectByExample(StationInfoExample example);

    StationInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record")
                                         StationInfo record, @Param("example")
                                         StationInfoExample example);

    int updateByExample(@Param("record")
                                StationInfo record, @Param("example")
                                StationInfoExample example);

    int updateByPrimaryKeySelective(StationInfo record);

    int updateByPrimaryKey(StationInfo record);
}

