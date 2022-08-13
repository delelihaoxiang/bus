package com.bus.service;

import com.bus.controller.LoginModel;

import com.bus.pojo.*;

import java.io.InputStream;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface BusLineInfoService {
    /**
      分页查询公交线路数据列表
    */
    public Map<String, Object> getDataList(BusLineInfo queryModel,
                                           Integer page, Integer pageSize, LoginModel login);

    /**
      封装公交线路为前台展示的数据形式
    */
    public Map<String, Object> getBusLineInfoModel(BusLineInfo model,
                                                   LoginModel login);

    /**
    * 删除数据
    */
    public void delete(Integer id);

    /**
      新增
    */
    public String add(BusLineInfo model, LoginModel login);

    /**
      修改
    */
    public String update(BusLineInfo model, LoginModel login);
}

