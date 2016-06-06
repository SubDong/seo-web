<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>seo</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/semantic.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .ui.popup{
            z-index: 1000;
        }
    </style>
</head>
<body>
<div class="ui blue inverted large menu top">
    <span><img src="/img/logo.png"></span>
    <div class="right menu ng-scope menu_right">
        <div dropdown-setting="dropdown_settings" cg-dropdown="" ng-controller="UserDropdownController" class="ui dropdown item ng-scope">
            <a class="ui avatar image message-breath user_img" id="imagesTitle">
                <img src="/images/stevie.jpg">
            </a>
        </div>
        <div dropdown-setting="dropdown_settings" cg-dropdown="" ng-controller="UserDropdownController" class="ui dropdown item ng-scope">
            <div class="ui dropdown"><span id="time"></span><span id="userNameinfo"></span></div>
        </div>
        <div dropdown-setting="dropdown_settings" cg-dropdown="" ng-controller="UserDropdownController" class="ui dropdown item ng-scope">
            <div class="ui dropdown" id="account">
                <div class="text" id="account_box"></div>
                <i class="dropdown icon"></i>
                <div class="menu" id="account_list">
                    <div class="item account_item">---</div>
                </div>
            </div>
        </div>
        <div dropdown-setting="dropdown_settings" cg-dropdown="" ng-controller="UserDropdownController" class="ui dropdown item ng-scope">
            <div class="ui dropdown"><form class="fl" id="logout" method="GET" action="${pageContext.request.contextPath}/auth/logout">
                <span onclick="$('#logout').submit();">退出</span>
            </form></div>
        </div>
    </div>
</div>
<div class="ui">
    <div class="ui active dimmer" id="lodingser" style="display: none">
        <div class="ui text loader">正在加载中...</div>
    </div>
    <div class="container">
        <div class="home_input my_input">
            <form id="frmSubmit" action="/seo/domain" method="post">
                <div class="ui action left icon input">
                    <div class="ui action left icon input">
                        <i id="searchLoding" class="search icon"></i>
                        <input type="text" id="inputValue" name="url" value="${reutrnUrl}" placeholder="请输入您要查询的网站" readonly="readonly">
                        <input type="text" id="useName" style="display: none">
                        <div class="ui teal floating dropdown icon button" id="dropdown">
                            <div class="text">网站查询</div>
                            <i class="dropdown icon"></i>

                            <div class="menu" id="menu">
                                <div class="item" cname="btnSubmit" Search="1">网站查询</div>
                                <div class="item" cname="btnSubmit" Search="2">网页查询</div>
                                <input type="hidden" name="returnNum" id="returnNum" value="${search}">
                            </div>
                        </div>
                    </div>
                    <div id="msg">域名地址输入有误，请检查</div>
                </div>
            </form>
        </div>
        <h2 class="ui header">
            <i class="teal small star icon"></i>

            <div class="content">
                详细信息
            </div>
        </h2>
        <table class="ui celled table segment">
            <thead>
            <tr>
                <th class="three wide">标题</th>
                <th>得分</th>
                <th>存在的问题</th>
                <th>优化意见</th>
                <th>分值</th>
            </tr>
            </thead>
            <tbody id="dataDet">

            </tbody>
        </table>
        <h2 class="ui header">
            <i class=" teal small bar chart basic icon"></i>

            <div class="content">
                深度分析
                <a class="ui teal label" id="fenxi_click">
                   <div id="start">开始分析</div><div id="loading" style="display:none"><div class="ui active small inline loader"></div>　分析任务正在提交中....</div>
                </a>
                <%--<a class="ui teal label">
                    <div id="exit">取消</div>
                </a>--%>
            </div>
        </h2>

        <table class="ui celled table segment">
            <thead>
            <tr>
                <th class="three wide">日期</th>
                <th>分析url</th>
                <th>下载完整报告</th>
            </tr>
            </thead>
            <tbody id="liebiao">
            <tr>
                <td class="positive">--</td>
                <td>--</td>
                <td>--</td>
            </tr>
            </tbody>
        </table>
        <div id="pagination1" class="pagination"></div>
        <textarea id="jsonDataDet" style="display: none">${rows}</textarea>
    </div>
</div>

<div class="ui fullscreen modal">
    <i class="close icon"></i>
    <div class="header">
        报告详细信息
    </div>
    <div class="content">
        <table class="ui celled table segment">
            <thead>
            <tr>
                <th class="three wide">标题</th>
                <th>得分</th>
                <th>存在的问题</th>
                <th>问题连接</th>
                <th>总数量(条)</th>
                <th>分值</th>
            </tr>
            </thead>
            <tbody id="dataModal">
            </tbody>
        </table>
    </div>
    <div class="actions">
    </div>
</div>
<textarea id ="userData" style="display: none">${userData}</textarea>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<!--<script type="text/javascript" src="http://cdn.bootcss.com/echarts/2.1.10/echarts-all.js"></script>-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/semantic.min.js"></script>
<!--<script type="text/javascript" src="${pageContext.request.contextPath}/js/seo_echarts.js"></script>-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/seo.js"></script>
<script type="text/javascript">
    $("#fenxi_click").click(function(){
        $(document).ajaxStart(function () {
            $("#loading").show();
            $("#start").hide();
        })
        $(document).ajaxStop(function () {
            $("#loading").hide();
            $("#start").show();
        });
    })
</script>
</body>
</html>