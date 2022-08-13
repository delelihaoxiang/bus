package com.bus.controller.index;

import com.bus.controller.*;
import com.bus.mapper.*;
import com.bus.pojo.*;
import com.bus.service.*;
import com.bus.service.impl.BusLineCalServiceImpl;
import com.bus.utils.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/commonapi/index/cal")
public class IndexBusLineCalController {
    @Autowired
    BusLineCalServiceImpl busLineCalServiceImpl;
    @Autowired
    StationInfoMapper stationInfoMapper;
    @RequestMapping(value = "")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        LoginModel login = (LoginModel) request.getSession()
                                               .getAttribute(CommonVal.sessionName);
        modelMap.addAttribute("login", login);
        StationInfoExample se = new StationInfoExample();
        List<StationInfo> sl = stationInfoMapper.selectByExample(se);
        modelMap.addAttribute("sl", sl);
        return "index/cal_line";
    }

    /**
            进入搜索列表接口
    **/
    @RequestMapping(value = "submitCal")
    @ResponseBody
    public Object submitCal(ModelMap modelMap,Integer startId,Integer endId, HttpServletRequest request) {
        LoginModel login = (LoginModel) request.getSession()
                                               .getAttribute(CommonVal.sessionName);
        modelMap.addAttribute("login", login);
       
       return  busLineCalServiceImpl.getBusLine(startId, endId);
    }

   
}

