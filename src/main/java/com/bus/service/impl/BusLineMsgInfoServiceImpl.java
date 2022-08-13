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
public class BusLineMsgInfoServiceImpl implements BusLineMsgInfoService {
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
    @Autowired
    BusLineMsgInfoMapper busLineMsgInfoMapper;
    @Autowired
    BusLineInfoMapper busLineInfoMapper;
    @Autowired
    StationInfoMapper stationInfoMapper;

    /**
      新增
    */
    @Override
    public String add(BusLineMsgInfo model, LoginModel login) {
        model.setCreateTime(sdf1.format(new Date())); //当前时间格式
        
        boolean flag = checkHasSameStation(model.getLineId(), model.getId(), model.getStationId());
        if(flag==false){
        	return "该路线已含有该车站";
        }
        busLineMsgInfoMapper.insertSelective(model); //插入数据

        return "";
    }

    /**
      修改
    */
    @Override
    public String update(BusLineMsgInfo model, LoginModel login) {
        BusLineMsgInfo preModel = busLineMsgInfoMapper.selectByPrimaryKey(model.getId());
        preModel.setNo(model.getNo());
        preModel.setStationId(model.getStationId());
        boolean flag = checkHasSameStation(preModel.getLineId(), preModel.getId(), preModel.getStationId());
        if(flag==false){
        	return "该路线已含有该车站";
        }
        
        busLineMsgInfoMapper.updateByPrimaryKey(preModel); //更新数据

        return "";
    }
    private boolean checkHasSameStation(Integer lineId,Integer id,Integer stationId){
    	 BusLineMsgInfoExample be = new BusLineMsgInfoExample();
    	 BusLineMsgInfoExample.Criteria bc= be.createCriteria();
    	 bc.andLineIdEqualTo(lineId);
    	 bc.andStationIdEqualTo(stationId);
    	 if(id!=null){
    		 bc.andIdNotEqualTo(id);
    	 }
    	 int count = (int)busLineMsgInfoMapper.countByExample(be);
    	 if(count>0){
    		 return false;
    	 }else{
    		 return true;
    	 }
    }

    /**
    *根据参数查询路线-车站关联列表数据
    */
    @Override
    public Map<String, Object> getDataList(BusLineMsgInfo queryModel,
        Integer page, Integer pageSize, LoginModel login) {
        BusLineMsgInfoExample se = new BusLineMsgInfoExample();
        BusLineMsgInfoExample.Criteria sc = se.createCriteria();
        se.setOrderByClause("id desc"); //默认按照id倒序排序

        if (queryModel.getId() != null) {
            sc.andIdEqualTo(queryModel.getId());
        }

        if (queryModel.getLineId() != null) {
            sc.andLineIdEqualTo(queryModel.getLineId());
        }

        int count = (int) busLineMsgInfoMapper.countByExample(se);
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

        List<BusLineMsgInfo> list = busLineMsgInfoMapper.selectByExample(se); //执行查询语句
        Map<String, Object> rs = new HashMap<String, Object>();
        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();

        for (BusLineMsgInfo model : list) {
            list2.add(getBusLineMsgInfoModel(model, login));
        }

        rs.put("list", list2); //数据列表
        rs.put("count", count); //数据总数
        rs.put("totalPage", totalPage); //总页数

        return rs;
    }

    /**
      封装路线-车站关联为前台展示的数据形式
    */
    @Override
    public Map<String, Object> getBusLineMsgInfoModel(BusLineMsgInfo model,
        LoginModel login) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("busLineMsgInfo", model);

        if (model.getLineId() != null) {
            BusLineInfo busLineInfo = busLineInfoMapper.selectByPrimaryKey(model.getLineId()); //所属路线为外接字段,需要关联公交线路来解释该字段

            if (busLineInfo != null) {
                map.put("lineIdStr", busLineInfo.getLineName());
            }
        }

        if (model.getStationId() != null) {
            StationInfo stationInfo = stationInfoMapper.selectByPrimaryKey(model.getStationId()); //当前车站为外接字段,需要关联车站来解释该字段

            if (stationInfo != null) {
                map.put("stationIdStr", stationInfo.getStationName());
            }
        }

        return map;
    }

    /**
    * 删除数据
    */
    @Override
    public void delete(Integer id) {
        busLineMsgInfoMapper.deleteByPrimaryKey(id);
    }
}

