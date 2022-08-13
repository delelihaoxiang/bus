package com.bus.service.impl;

import com.bus.controller.LoginModel;

import com.bus.mapper.*;

import com.bus.pojo.*;

import com.bus.service.*;

import com.bus.utils.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class StationInfoServiceImpl implements StationInfoService {
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
    @Autowired
    StationInfoMapper stationInfoMapper;

    /**
      新增
    */
    @Override
    public String add(StationInfo model, LoginModel login) {
        model.setCreateTime(sdf1.format(new Date())); //当前时间格式
        stationInfoMapper.insertSelective(model); //插入数据

        return "";
    }

    /**
      修改
    */
    @Override
    public String update(StationInfo model, LoginModel login) {
        StationInfo preModel = stationInfoMapper.selectByPrimaryKey(model.getId());
        preModel.setStationName(model.getStationName());
        stationInfoMapper.updateByPrimaryKey(preModel); //更新数据

        return "";
    }

    /**
    *根据参数查询车站列表数据
    */
    @Override
    public Map<String, Object> getDataList(StationInfo queryModel,
        Integer page, Integer pageSize, LoginModel login) {
        StationInfoExample se = new StationInfoExample();
        StationInfoExample.Criteria sc = se.createCriteria();
        se.setOrderByClause("id desc"); //默认按照id倒序排序

        if (queryModel.getId() != null) {
            sc.andIdEqualTo(queryModel.getId());
        }

        if ((queryModel.getStationName() != null) &&
                (queryModel.getStationName().equals("") == false)) {
            sc.andStationNameLike("%" + queryModel.getStationName() + "%"); //模糊查询
        }

        int count = (int) stationInfoMapper.countByExample(se);
        int totalPage = 0;

        if ((page != null) && (pageSize != null)) { //分页

            if ((count > 0) && ((count % pageSize) == 0)) {
                totalPage = count / pageSize;
            } else {
                totalPage = (count / pageSize) + 1;
            }

            se.setPageRows(pageSize);
            se.setStartRow((page - 1) * pageSize);
        }

        List<StationInfo> list = stationInfoMapper.selectByExample(se); //执行查询语句
        Map<String, Object> rs = new HashMap<String, Object>();
        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();

        for (StationInfo model : list) {
            list2.add(getStationInfoModel(model, login));
        }

        rs.put("list", list2); //数据列表
        rs.put("count", count); //数据总数
        rs.put("totalPage", totalPage); //总页数

        return rs;
    }

    /**
      封装车站为前台展示的数据形式
    */
    @Override
    public Map<String, Object> getStationInfoModel(StationInfo model,
        LoginModel login) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stationInfo", model);

        return map;
    }

    /**
    * 删除数据
    */
    @Override
    public void delete(Integer id) {
        stationInfoMapper.deleteByPrimaryKey(id);
    }
}

