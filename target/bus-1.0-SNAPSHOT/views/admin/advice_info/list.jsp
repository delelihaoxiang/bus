<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="uri" value="${pageContext.request.contextPath}" />
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/hui/lib/html5shiv.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/hui/lib/respond.min.js"></script>
            <![endif]-->
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/hui/static/h-ui/css/H-ui.min.css" />
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/hui/static/h-ui.admin/css/H-ui.admin.css" />
                    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/hui/lib/Hui-iconfont/1.0.8/iconfont.css" />
                        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/hui/static/h-ui.admin/skin/green/skin.css" id="skin" />
                            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/hui/static/h-ui.admin/css/style.css" />
                                <!--[if IE 6]>
                                <script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
                                <script>DD_belatedPNG.fix('*');</script>
                                <![endif]-->
                                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/common/page/myPagination.css?t=4" />
                                    
                                    <style type="text/css">
                                    .btnStyle{
                                        margin-left:8px;
                                        margin-top:8px
                                    }
                                    .loadingModel {
                                        position: absolute;
                                        top: 0;
                                        left: 0;
                                        background-color: rgba(9, 9, 9, 0.63);
                                        width: 100%;
                                        height: 100%;
                                        z-index: 1000;
                                    }
                                    
                                    
                                    .loading-content {
                                        width: 50%;
                                        text-align: center;
                                        background: #ffffff;
                                        border-radius: 6px;
                                        line-height: 30px;
                                        z-index: 10001;
                                    }
                                    </style>
                                    <!--[if IE 6]>
                                    
                                    <script>DD_belatedPNG.fix('*');</script>
                                    <![endif]-->
                                    <title>????????????</title>
                                    </head>
                                    <body>
                                    <div id="loadingDiv"></div>
                                    <div id="bodyModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                    <div class="modal-content radius">
                                    <div class="modal-header">
                                    <h3 class="modal-title" id="bodyModalTile"></h3>
                                    <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">??</a>
                                    </div>
                                    <div class="modal-body" id="bodyModalContent">
                                    </div>
                                    <div class="modal-footer">
                                    <button class="btn" data-dismiss="modal" aria-hidden="true">??????</button>
                                    </div>
                                    </div>
                                    </div>
                                    </div>
                                    <nav class="breadcrumb"><i class="Hui-iconfont">???</i>????????????<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="??????" ><i class="Hui-iconfont">???</i></a></nav>
                                    <div class="page-container">
                                    
                                    
                                    <div style="display:inline;margin-top:30px"><span style="width:200px;margin-left:50px;">???????????????</span>
                                    <select  id="isDeal" style="height:30px;width:250px;display:inline" >
                                    <option value="">??????</option>
                                    <c:forEach items="${isDealList}" var="item">
                                        <option value="${item.id}">${item.name}</option>
                                            </c:forEach>
                                            </select>
                                            
                                            </div>
                                            
                                            <button type="button" onclick="ajaxList(1);" style="margin-left:30px" class="btn btn-success" ><i class="Hui-iconfont">???</i> ??????</button>
                                            
                                            
                                            <div class="cl pd-5 bg-1 bk-gray mt-20">
                                            <span class="l" id="globalBtns">
                                            
                                            
                                            </span>
                                            <span id="batchUpdateDiv">
                                            
                                            </span>
                                            </div>
                                            
                                            <table class="table table-border table-bordered table-hover table-bg table-sort" style="">
                                            <thead>
                                            <tr style="text-align:center">
                                            <th>????????????</th>
                                            <th>????????????</th>
                                            <th>????????????</th>
                                            <th>???????????????</th>
                                            <th>????????????</th>
                                            <th>????????????</th>
                                            <th>????????????</th>
                                            <th style="width:200px;">??????</th>
                                            
                                            </tr>
                                            </thead>
                                            <tbody id="advice_info_body">
                                            </tbody>
                                            </table>
                                            
                                            <div id="advice_info_bar" class="pagination" style="margin-top:20px;">
                                            </div>
                                            <div>
                                            </div>
                                            </div>
                                            <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/jquery-1.11.1.min.js"></script>
                                                <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/page/myPagination.js"></script>
                                                    
                                                    
                                                    <script type="text/javascript" src="${pageContext.request.contextPath}/static/hui/lib/layer/2.4/layer.js"></script>
                                                        <script type="text/javascript" src="${pageContext.request.contextPath}/static/hui/static/h-ui/js/H-ui.min.js"></script>
                                                            <script type="text/javascript" src="${pageContext.request.contextPath}/static/hui/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer ??????????????????????????????-->
                                                                <script type="text/javascript" src="${pageContext.request.contextPath}/static/hui/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
                                                                    
                                                                    
                                                                    
                                                                    
                                                                    
                                                                    <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/utils/listutils.js?v=6568"></script>
                                                                        
                                                                        
                                                                        <script type="text/javascript">
                                                                        
                                                                        $(function(){
                                                                            
                                                                            ajaxList(1);
                                                                            
                                                                            
                                                                        });
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        function ajaxList(page) {
                                                                            var isDeal = $("#isDeal").val();
                                                                            
                                                                            showLoading('${pageContext.request.contextPath}/static/common/loading.gif');
                                                                                $.ajax({
                                                                                    type : 'post',
                                                                                    url : "${pageContext.request.contextPath}/admin/advice_info/queryList",
                                                                                        data : {
                                                                                            "page":page,
                                                                                            "isDeal":isDeal
                                                                                            
                                                                                        },
                                                                                        success : function(result) {
                                                                                            hideLoading();
                                                                                            var rows = result.list;//???????????????
                                                                                            var total = result.count;//??????????????????
                                                                                            lastPage=result.totalPage;
                                                                                            totalCount=total;
                                                                                            var html = '';
                                                                                            for (var i = 0; i < rows.length; i++) {
                                                                                                html += '<tr>' ;
                                                                                                var realNameVal=setNullToEmpty(rows[i].adviceInfo.realName);
                                                                                                html+='<td>'+ realNameVal+'</td>';
                                                                                                var celPhoneVal=setNullToEmpty(rows[i].adviceInfo.celPhone);
                                                                                                html+='<td>'+ celPhoneVal+'</td>';
                                                                                                var contentVal=setNullToEmpty(rows[i].adviceInfo.content);
                                                                                                html+='<td>'+ contentVal+'</td>';
                                                                                                var isDealVal=setNullToEmpty(rows[i].isDealStr);
                                                                                                if(rows[i].adviceInfo.isDeal=='1'){
                                                                                                isDealVal ='<span class="badge badge-warning radius">'+isDealVal+'</span>'}
                                                                                                if(rows[i].adviceInfo.isDeal=='3'){
                                                                                                isDealVal ='<span class="badge badge-secondary radius">'+isDealVal+'</span>'}
                                                                                                html+='<td>'+isDealVal+'</td>';
                                                                                                var replyContentVal=setNullToEmpty(rows[i].adviceInfo.replyContent);
                                                                                                html+='<td>'+ replyContentVal+'</td>';
                                                                                                var userIdVal=setNullToEmpty(rows[i].userIdStr);
                                                                                                html+='<td>'+ userIdVal+'</td>';
                                                                                                var createTimeVal=setNullToEmpty(rows[i].adviceInfo.createTime);
                                                                                                html+='<td>'+ createTimeVal+'</td>';
                                                                                                html+='<td>';
                                                                                                html+='<a href="${pageContext.request.contextPath}/admin/advice_info/reply?id='+rows[i].adviceInfo.id+'" class="btn btn-primary  radius btnStyle">??????</a>';
                                                                                                    html+='</td>';
                                                                                                    html+='</tr>';
                                                                                                }
                                                                                                if (rows.length == 0) {
                                                                                                    html += '<tr><td></td><td></td><td></td><td></td><td>?????????</td><td></td><td></td><td></td></tr>';
                                                                                                }
                                                                                                $("#advice_info_body").html(html);
                                                                                                
                                                                                                
                                                                                                new myPagination({
                                                                                                    id: 'advice_info_bar',
                                                                                                    curPage:page, //????????????
                                                                                                    pageTotal: result.totalPage, //?????????
                                                                                                    pageAmount: 10,  //???????????????
                                                                                                    dataTotal: total, //?????????????????????
                                                                                                    pageSize: 5, //??????,????????????
                                                                                                    showPageTotalFlag:true, //????????????????????????
                                                                                                    showSkipInputFlag:false, //??????????????????
                                                                                                    getPage: function (page) {
                                                                                                        ajaxList(page);
                                                                                                    }
                                                                                                })
                                                                                                
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                    
                                                                                    
                                                                                    
                                                                                    function gotoPage(e){
                                                                                        var pageName = $(e).attr("page-name");
                                                                                        var url = $(e).attr("data-url");
                                                                                        creatIframe(url,pageName);
                                                                                    }
                                                                                    
                                                                                    
                                                                                    
                                                                                    </script>
                                                                                    </body>
                                                                                    </html>
