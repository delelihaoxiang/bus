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
        <a href="${pageContext.request.contextPath}/commonapi/user_info_regist">注册</a>
      </li>
  </c:if>
  <c:if test="${login!=null }">
  	 <!-- 登入后的状态 -->
	   <li class="layui-nav-item">
        <a class="fly-nav-avatar" href="${pageContext.request.contextPath}/user/index">
          <cite class="layui-hide-xs">${login.name}</cite>
          
          <!--  <img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"> -->
        </a>
      </li>
       <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/commonapi/sys_logout">退出</a>
      </li>
  </c:if>
    </ul>
  </div>
</div>
<div class="fly-panel fly-column">
  <div class="layui-container">
    <ul class="layui-clear">
       <li class="layui-hide-xs layui-this"><a href="${pageContext.request.contextPath}/commonapi/index">所有线路</a></li> 

      <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><span class="fly-mid"></span></li> 
      
      <!-- 用户登入后显示 -->
       <!--
      <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="user/index.html">我发表的贴</a></li> 
      <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="user/index.html#collection">我收藏的贴</a></li>
      --> 
    </ul> 
  </div>
</div>
<div class="layui-container">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md12">
      <div class="fly-panel">
      <form class="layui-form">
      <div class="layui-row">
     
  		
    <div class="layui-col-xs6 layui-col-sm6 layui-col-md4" style="width:30%;padding:3px;">
    <label class="layui-form-label">起点站</label>
    <div class="layui-input-block">
      <select name="startId" id="startId" >
      <option value=""></option>
       <c:forEach var="item" items="${sl}">
       	 <option value="${item.id}">${item.stationName}</option>
       </c:forEach>
      </select>
    </div>
  </div>
  
      <div class="layui-col-xs6 layui-col-sm6 layui-col-md4" style="width:30%;padding:3px;">
    <label class="layui-form-label">终点站</label>
    <div class="layui-input-block">
      <select name="endId" id="endId" >
      <option value=""></option>
       <c:forEach var="item" items="${sl}">
       	 <option value="${item.id}">${item.stationName}</option>
       </c:forEach>
      </select>
    </div>
  </div>
   
     <div class="layui-col-xs6 layui-col-sm6 layui-col-md4" style="padding:3px;">
    		<button type="button" class="layui-btn layui-btn-normal" onclick="ajaxList(1)">查询</button>
  		</div>
      </div>
      </form>
      </div>
      <div class="fly-panel" style="margin-bottom: 0;height:400px;">
        <div class="fly-panel-title fly-filter">
          
          <span class="fly-filter-right layui-hide-xs">
          </span>
        </div>
        <ul class="fly-list" id="dataList">          
        </ul>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/static/bbs/res/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/common/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/common/page/myPagination.js"></script>
<script>
layui.use('form', function(){
	  var form = layui.form;
	  //监听提交
	  form.on('submit(formDemo)', function(data){
	    layer.msg(JSON.stringify(data.field));
	    return false;
	  });
	});
layui.cache.page = '';
layui.config({
  version: "3.0.0"
  ,base: '${pageContext.request.contextPath}/static/bbs/res/res/mods/' //这里实际使用时，建议改成绝对路径
}).extend({
  fly: 'index'
}).use('fly');
 $(function(){
	 ajaxList(1);
 })

function ajaxList(page) {
  	//1.默认搜索，2.排序按钮
  	var startId = $("#startId").val();
  	var endId = $("#endId").val();
  	
  	$.ajax({
  				type : 'post',
  				url : "${pageContext.request.contextPath}/commonapi/index/cal/submitCal",
  				data : {
  					
  					"startId":startId,
  					"endId":endId
  				},
  				success : function(result) {
  					var rows = result;//得到数据列
  					var html = '';
  					for (var i = 0; i < rows.length; i++) {
  						var list=rows[i].list;
  						var msg='';
  						for(var j=0;j<list.length;j++){
  							
  							msg+=list[j].stationName+'-->';
  						}
  						if(msg!=''){
  							msg = msg.substring(0,msg.length-3);
  						}

   		              		html+=' <li>';
							html+='<h2>';
							html+=' <a href="#">'+msg+'</a>';
							html+=' </h2>';
							html+=' <div class="fly-list-info">';
							
							html+='<span>换乘次数：'+rows[i].changeTimes+'</span>';
							html+='<span>费用：'+rows[i].totalPrice+'</span>';
							html+='<span>预计时间：'+rows[i].costTime+'分钟</span>';
							html+='</div>';
							html+=' <div class="fly-list-badge">';
							html+=' </div>';
							html+='</li>';

  					}
  					if(rows.length==0){
  						html+=' <div class="fly-none">没有相关数据</div>';
  					}
  						$("#dataList").html(html);	
  				}
  			});
  }
</script>
</body>
</html>
