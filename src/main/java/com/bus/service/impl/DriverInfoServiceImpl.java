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
public class DriverInfoServiceImpl implements DriverInfoService {
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
    @Autowired
    DriverInfoMapper driverInfoMapper;

    /**
      新增
    */
    @Override
    public String add(DriverInfo model, LoginModel login) {
        String check = checkData(model, 1); //检查数据是否符合要求

        if (check.equals("") == false) {
            return check;
        }

        model.setCreateTime(sdf1.format(new Date())); //当前时间格式
        driverInfoMapper.insertSelective(model); //插入数据

        return "";
    }

    /**
      修改
    */
    @Override
    public String update(DriverInfo model, LoginModel login) {
        DriverInfo preModel = driverInfoMapper.selectByPrimaryKey(model.getId());
        String check1 = checkData(model, 2); //检查数据是否合法

        if (check1.equals("") == false) {
            return check1;
        }

        preModel.setRealName(model.getRealName());
        preModel.setCelPhone(model.getCelPhone());
        preModel.setDriverIntro(model.getDriverIntro());
        driverInfoMapper.updateByPrimaryKey(preModel); //更新数据

        return "";
    }

    /**
    *根据参数查询司机列表数据
    */
    @Override
    public Map<String, Object> getDataList(DriverInfo queryModel, Integer page,
        Integer pageSize, LoginModel login) {
        DriverInfoExample se = new DriverInfoExample();
        DriverInfoExample.Criteria sc = se.createCriteria();
        se.setOrderByClause("id desc"); //默认按照id倒序排序

        if (queryModel.getId() != null) {
            sc.andIdEqualTo(queryModel.getId());
        }

        if ((queryModel.getRealName() != null) &&
                (queryModel.getRealName().equals("") == false)) {
            sc.andRealNameLike("%" + queryModel.getRealName() + "%"); //模糊查询
        }

        int count = (int) driverInfoMapper.countByExample(se);
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

        List<DriverInfo> list = driverInfoMapper.selectByExample(se); //执行查询语句
        Map<String, Object> rs = new HashMap<String, Object>();
        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();

        for (DriverInfo model : list) {
            list2.add(getDriverInfoModel(model, login));
        }

        rs.put("list", list2); //数据列表
        rs.put("count", count); //数据总数
        rs.put("totalPage", totalPage); //总页数

        return rs;
    }

    /**
      封装司机为前台展示的数据形式
    */
    @Override
    public Map<String, Object> getDriverInfoModel(DriverInfo model,
        LoginModel login) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("driverInfo", model);

        return map;
    }

    /**
    * 删除数据
    */
    @Override
    public void delete(Integer id) {
        driverInfoMapper.deleteByPrimaryKey(id);
    }

    public String checkData(DriverInfo model, Integer type) { //type=1 表示新增操作,type=2 表示修改操作,

        if ((type == 1) || (type == 2)) {
            if (model.getCelPhone() != null) {
                Pattern p = Pattern.compile(
                        "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$");
                Matcher m = p.matcher(model.getCelPhone());

                if (m.matches() == false) {
                    return "请输入正确的联系电话";
                }
            }
        }

        return "";
    }
}

