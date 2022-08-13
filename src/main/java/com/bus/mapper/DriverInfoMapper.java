package com.bus.mapper;

import com.bus.pojo.DriverInfo;
import com.bus.pojo.DriverInfoExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DriverInfoMapper {
    long countByExample(DriverInfoExample example);

    int deleteByExample(DriverInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DriverInfo record);

    int insertSelective(DriverInfo record);

    List<DriverInfo> selectByExample(DriverInfoExample example);

    DriverInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record")
                                         DriverInfo record, @Param("example")
                                         DriverInfoExample example);

    int updateByExample(@Param("record")
                                DriverInfo record, @Param("example")
                                DriverInfoExample example);

    int updateByPrimaryKeySelective(DriverInfo record);

    int updateByPrimaryKey(DriverInfo record);
}

