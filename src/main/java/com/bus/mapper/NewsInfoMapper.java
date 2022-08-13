package com.bus.mapper;

import com.bus.pojo.NewsInfo;
import com.bus.pojo.NewsInfoExample;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface NewsInfoMapper {
    long countByExample(NewsInfoExample example);

    int deleteByExample(NewsInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NewsInfo record);

    int insertSelective(NewsInfo record);

    List<NewsInfo> selectByExample(NewsInfoExample example);

    NewsInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record")
                                         NewsInfo record, @Param("example")
                                         NewsInfoExample example);

    int updateByExample(@Param("record")
                                NewsInfo record, @Param("example")
                                NewsInfoExample example);

    int updateByPrimaryKeySelective(NewsInfo record);

    int updateByPrimaryKey(NewsInfo record);
}

