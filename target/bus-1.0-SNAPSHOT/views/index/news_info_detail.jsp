<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>公交查询</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="keywords" content="公交查询">
  <meta name="description" content="公交查询">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bbs/res/layui/css/layui.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bbs/res/css/global.css">
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/common/page/myPagination.css?t=4" />
</head>
<body>
<div class="fly-header layui-bg-black">
  <div class="layui-container">
    <a style="float:left;left:-30px;margin-top:10px;" href="${pageContext.request.contextPath}/commonapi/index">
     <h1 style="color:white">公交查询</h1>
    </a>
    <ul class="layui-nav fly-nav layui-hide-xs">
         <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/commonapi/index">所有线路</a>
      </li>
        <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/commonapi/index/cal">换乘查询</a>
      </li>
           <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/commonapi/index/news_info">新闻</a>
      </li>
    </ul>
    <ul class="layui-nav fly-nav-user">
<c:if test="${login==null }">
	 <!-- 未登入的状态 -->
      <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/commonapi/sys_login">登录</a>
      </li>
      <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/commonapi/student_info_regist">注册</a>
      </li>
  </c:if>
  <c:if test="${login!=null }">
  	  <!-- 登入后的状态 -->
	   <li class="layui-nav-item">
	   <c:if test="${login.loginType==3}">
	      <a class="fly-nav-avatar" href="${pageContext.request.contextPath}/xs/index">
          <cite class="layui-hide-xs">${login.name}</cite>
           <img src="${uheadImg}">
        </a>
	   </c:if>
	   
	   <c:if test="${login.loginType==2}">
	      <a class="fly-nav-avatar" href="${pageContext.request.contextPath}/admin/index">
          <cite class="layui-hide-xs">${login.name}</cite>
           <img src="${uheadImg}">
        </a>
	   </c:if>
     
      </li>
       <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/commonapi/sys_logout">退出</a>
      </li>
  </c:if>
    </ul>
  </div>
</div>
<div class="layui-container">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md12 content detail">
      <div class="fly-panel detail-box">
        <h1>${detail.t.title}</h1>
        <div class="fly-detail-info">
         <span class="layui-badge layui-bg-green fly-detail-column"></span>
        </div>
         <div class="detail-about">
 <div class="fly-detail-user">
 	<span>${detail.t.createTime}</span>
</div>
 </div>

        <div class="detail-body photos">
         	<p style="margin-left:10%;margin-top:20px;">${detail.t.content}</p>

        </div>
      </div>

    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/static/bbs/res/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/common/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/common/page/myPagination.js"></script>
<script>

</script>
</body>
</html>
