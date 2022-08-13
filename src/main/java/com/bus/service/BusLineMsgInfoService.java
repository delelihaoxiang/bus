package com.bus.service;

import com.bus.controller.LoginModel;

import com.bus.pojo.*;

import java.io.InputStream;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface BusLineMsgInfoService {
    /**
      分页查询路线-车站关联数据列表
    */
    public Map<String, Object> getDataList(BusLineMsgInfo queryModel,
                                           Integer page, Integer pageSize, LoginModel login);

    /**
      封装路线-车站关联为前台展示的数据形式
    */
    public Map<String, Object> getBusLineMsgInfoModel(BusLineMsgInfo model,
                                                      LoginModel login);

    /**
    * 删除数据
    */
    public void delete(Integer id);

    /**
      新增
    */
    public String add(BusLineMsgInfo model, LoginModel login);

    /**
      修改
    */
    public String update(BusLineMsgInfo model, LoginModel login);
}

