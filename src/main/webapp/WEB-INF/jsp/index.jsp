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
</head>
<body>
<div id="top-menu" class="ui blue inverted  main top menu ng-scope">
        <span class="logo"><img  src="/img/logo.png"></span>
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
        <form id="frmSubmit" action="/seo/score" method="post">
        <div class="ui action left icon input">
            <div class="ui action left icon input">
            <i id="searchLoding" class="search icon"></i>
            <input type="text" id="inputValue" name="url" value=""  placeholder="请输入您要查询的网站" />
            <input type="text" id="useName" style="display: none">
            <div class="ui teal floating dropdown icon button" id="dropdown">
                <div class="text" id="menu_text">网站查询</div> <i class="dropdown icon"></i>
                <div class="menu" id="menu">
                    <div class="item menu_item" cname="btnSubmit" Search="1">网站查询</div>
                    <div class="item menu_item" cname="btnSubmit" Search="2">网页查询</div>
                    <input type="hidden" name="returnNum" id="returnNum" value="${search}">
                    <input type="hidden" id="returnUrl" value="${reutrnUrl}">
                </div>
            </div>
            </div>
            <div id="msg" >域名地址输入有误，请检查</div>
        </div>
        </form>
    </div>
    <h2 class="ui header">
        <i class="settings icon"></i>
        <div class="content">
            seo基本信息
            <div class="sub header">Search Engine Optimization，即搜索引擎优化.</div>
        </div>
    </h2>
    <table class="ui celled table segment">
        <tbody id="titleData">
            <tr>
                <td class="positive"><b>网站Title:</b></td>
                <td class="fifteen wide">---</td>
            </tr>
            <tr>
                <td class="positive"><b>世界排名：</b></td>
                <td>---</td>
            </tr>

            <tr>
                <td class="positive"><b>域名年龄：</b></td>
                <td>---</td>
            </tr>
            <tr>
                <td class="positive" rowspan="2" ><b>seo信息：</b></td>
                <td>---</td>
            </tr>
            <tr>
                <td>---</td>
            </tr>
        </tbody>
    </table>
    <table class="ui celled table segment">
        <thead>
        <tr>
            <th class="one wide">搜索引擎</th>
            <th>收录数量</th>
            <th>反向链接</th>
        </tr>
        </thead>
       <tbody id="searchInfo">
            <tr>
                <td class="positive"><img src="/images/ico_baidu.gif"><b>百度</b></td>
                <td>---</td>
                <td>---</td>
            </tr>
            <tr>
                <td class="positive"><img src="/images/ico_360so.gif"><b>360</b></td>
                <td>---</td>
                <td>---</td>
            </tr>
            <tr>
                <td class="positive"><img src="/images/ico_sogou.gif"><b>搜狗</b></td>
                <td>---</td>
                <td>---</td>
            </tr>
            <tr>
                <td class="positive"><img src="/images/ico_bing.gif"><b>必应</b></td>
                <td>---</td>
                <td>---</td>
            </tr>
        </tbody>
    </table>
    <table class="ui celled table segment">
        <thead>
        <tr>
            <th class="one wide">标签</th>
            <th>内容长度</th>
            <th>内容</th>
            <th>优化建议</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td class="positive"><b>网站标题</b></td>
            <td id="titleContenNUM">---</td>
            <td id="titleConten">---</td>
            <td>一般不超过80个字符</td>
        </tr>
        <tr>
            <td class="positive"><b>网站关键词</b></td>
            <td id="keyworldNUM">---</td>
            <td id="keyworld">---</td>
            <td>一般不超过100个字符</td>
        </tr>
        <tr>
            <td class="positive"><b>网站描述</b></td>
            <td id="messageNUM">---</td>
            <td id="message">---</td>
            <td>一般不超过200个字符</td>
        </tr>
        </tbody>
    </table>
</div>
</div>
<textarea id="modelData" style="display: none;">${model}</textarea>
<textarea id ="userData" style="display: none">${userData}</textarea>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/semantic.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/seo.js"></script>
<script type="text/javascript">

    $('.ui.dropdown').dropdown();



</script>
</body>
</html>