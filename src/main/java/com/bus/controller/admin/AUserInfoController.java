package com.bus.controller.admin;

import com.bus.controller.LoginModel;

import com.bus.mapper.*;

import com.bus.pojo.*;

import com.bus.service.*;
import com.bus.service.impl.*;

import com.bus.utils.*;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin/user_info")
public class AUserInfoController {
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    AdminInfoMapper adminInfoMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    /**
    * 返回用户列表jsp页面
    */
    @RequestMapping(value = "")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        LoginModel login = (LoginModel) request.getSession()
                                               .getAttribute(CommonVal.sessionName); //获取当前登录账号信息
        AdminInfo adminInfo = adminInfoMapper.selectByPrimaryKey(login.getId());
        modelMap.addAttribute("user", adminInfo);

        return "admin/user_info/list";
    }

    /**
     * 根据查询条件分页查询用户数据,然后返回给前台渲染
    */
    @RequestMapping(value = "queryList")
    @ResponseBody
    public Object toList(UserInfo model, Integer page, String nameOrder,
        HttpServletRequest request) {
        LoginModel login = (LoginModel) request.getSession()
                                               .getAttribute(CommonVal.sessionName);

        return userInfoService.getDataList(nameOrder, model, page,
            CommonVal.pageSize, login); //分页查询数据
    }

    /**
     删除数据接口
    */
    @RequestMapping("del")
    @ResponseBody
    public Object del(Integer id, ModelMap modelMap, HttpServletRequest request) {
        LoginModel login = (LoginModel) request.getSession()
                                               .getAttribute(CommonVal.sessionName);
        Map<String, Object> rs = new HashMap<String, Object>();
        userInfoService.delete(id);
        rs.put("code", 1);
        rs.put("msg",
            "删除成功");

        return rs;
    }
}

