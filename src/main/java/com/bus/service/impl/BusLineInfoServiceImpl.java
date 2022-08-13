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
public class BusLineInfoServiceImpl implements BusLineInfoService {
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
    @Autowired
    BusLineInfoMapper busLineInfoMapper;

    /**
      新增
    */
    @Override
    public String add(BusLineInfo model, LoginModel login) {
        model.setCreateTime(sdf1.format(new Date())); //当前时间格式
        busLineInfoMapper.insertSelective(model); //插入数据

        return "";
    }

    /**
      修改
    */
    @Override
    public String update(BusLineInfo model, LoginModel login) {
        BusLineInfo preModel = busLineInfoMapper.selectByPrimaryKey(model.getId());
        preModel.setLineName(model.getLineName());
        preModel.setPrice(model.getPrice());
        busLineInfoMapper.updateByPrimaryKey(preModel); //更新数据

        return "";
    }

    /**
    *根据参数查询公交线路列表数据
    */
    @Override
    public Map<String, Object> getDataList(BusLineInfo queryModel,
        Integer page, Integer pageSize, LoginModel login) {
        BusLineInfoExample se = new BusLineInfoExample();
        BusLineInfoExample.Criteria sc = se.createCriteria();
        se.setOrderByClause("id desc"); //默认按照id倒序排序

        if (queryModel.getId() != null) {
            sc.andIdEqualTo(queryModel.getId());
        }

        if ((queryModel.getLineName() != null) &&
                (queryModel.getLineName().equals("") == false)) {
            sc.andLineNameLike("%" + queryModel.getLineName() + "%"); //模糊查询
        }

        int count = (int) busLineInfoMapper.countByExample(se);
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

        List<BusLineInfo> list = busLineInfoMapper.selectByExample(se); //执行查询语句
        Map<String, Object> rs = new HashMap<String, Object>();
        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();

        for (BusLineInfo model : list) {
            list2.add(getBusLineInfoModel(model, login));
        }

        rs.put("list", list2); //数据列表
        rs.put("count", count); //数据总数
        rs.put("totalPage", totalPage); //总页数

        return rs;
    }

    /**
      封装公交线路为前台展示的数据形式
    */
    @Override
    public Map<String, Object> getBusLineInfoModel(BusLineInfo model,
        LoginModel login) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("busLineInfo", model);

        return map;
    }

    /**
    * 删除数据
    */
    @Override
    public void delete(Integer id) {
        busLineInfoMapper.deleteByPrimaryKey(id);
    }
}

