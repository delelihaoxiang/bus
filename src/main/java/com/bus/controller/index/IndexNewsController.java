package com.bus.controller.index;

import com.bus.controller.*;
import com.bus.mapper.*;
import com.bus.pojo.*;
import com.bus.service.*;
import com.bus.utils.*;
import com.bus.controller.LoginModel;
import com.bus.pojo.AdminInfo;
import com.bus.pojo.NewsInfo;
import com.bus.utils.CommonVal;

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
@RequestMapping("/commonapi/index/news_info")
public class IndexNewsController {
    @Autowired
    NewsInfoMapper newsInfoMapper;
    @Autowired
    AdminInfoMapper adminInfoMapper;

    /**
            进入搜索列表接口
    **/
    @RequestMapping(value = "")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        LoginModel login = (LoginModel) request.getSession()
                                               .getAttribute(CommonVal.sessionName);
        modelMap.addAttribute("login", login);
        return "index/news_info_list";
    }

    @RequestMapping(value = "search")
    @ResponseBody
    public Object search(String searchWord, Integer page,
        HttpServletRequest request) {
        LoginModel login = (LoginModel) request.getSession()
                                               .getAttribute(CommonVal.sessionName);
        int pageSize = 8;

        if (page == null) {
            page = 1;
        }

        NewsInfoExample te = new NewsInfoExample();
        NewsInfoExample.Criteria tc = te.createCriteria();

        if ((searchWord != null) && (searchWord.trim().equals("") == false)) {
            tc.andTitleLike("%" + searchWord + "%");
        }

        int count = (int) newsInfoMapper.countByExample(te);
        int totalPage = 0;

        if ((count > 0) && ((count % pageSize) == 0)) {
            totalPage = count / pageSize;
        } else {
            totalPage = (count / pageSize) + 1;
        }

        te.setPageRows(pageSize);
        te.setStartRow((page - 1) * pageSize);

        List<NewsInfo> tl = newsInfoMapper.selectByExample(te);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (NewsInfo t : tl) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("t", t);

            list.add(map);
        }

        Map<String, Object> rs = new HashMap<String, Object>();
        rs.put("data", list);
        rs.put("count", count);
        rs.put("totalPage", totalPage);
        rs.put("pageSize", pageSize);

        return rs;
    }
    
    
    @RequestMapping(value = "detail")
    public String detail(ModelMap modelMap, Integer id,
        HttpServletRequest request) {
        LoginModel login = (LoginModel) request.getSession()
                                               .getAttribute(CommonVal.sessionName);
        modelMap.addAttribute("login", login);
        NewsInfo t = newsInfoMapper.selectByPrimaryKey(id);
        if (t == null) {
            t = new NewsInfo();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("t", t);

        modelMap.addAttribute("detail", map);


        return "index/news_info_detail";
    }
}

