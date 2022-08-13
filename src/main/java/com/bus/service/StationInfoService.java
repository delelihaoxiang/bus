package com.bus.service;

import com.bus.controller.LoginModel;

import com.bus.pojo.*;

import java.io.InputStream;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface StationInfoService {
    /**
      分页查询车站数据列表
    */
    public Map<String, Object> getDataList(StationInfo queryModel,
                                           Integer page, Integer pageSize, LoginModel login);

    /**
      封装车站为前台展示的数据形式
    */
    public Map<String, Object> getStationInfoModel(StationInfo model,
                                                   LoginModel login);

    /**
    * 删除数据
    */
    public void delete(Integer id);

    /**
      新增
    */
    public String add(StationInfo model, LoginModel login);

    /**
      修改
    */
    public String update(StationInfo model, LoginModel login);
}

