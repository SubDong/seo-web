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
<div class="ui blue inverted large menu top">
    <span><img src="/img/logo.png"></span>
    <a class=" item" href="/seo/score?returnNum=1&url="+${reutrnUrl}>
        <i class="home icon"></i> 主页
    </a>
    <a class="active item" href="/seo/seo?returnNum=2&url="+${reutrnUrl}>
        <i class="search icon"></i> seo评价信息
    </a>
</div>
<div class="container">
    <div class="ui piled segment">
        <h2 class="ui header">
            <i class="blue star icon"></i>
            <div class="content">
                网页质量
            </div>
        </h2>
        <div class="ui divider"></div>
        <div id="KeywordPaiming" class="Echart" style="width:100%;height:400px"></div>
    </div>
    <div class="ui piled segment">
        <h2 class="ui header">
            <i class="blue bar chart basic icon"></i>
            <div class="content">
                网站质量
            </div>
        </h2>
        <div class="ui divider"></div>
        <div id="paiming" style="width:100%;height:400px"></div>
    </div>
    <div class="ui piled segment">
        <h2 class="ui header">
            <i class="blue desktop icon"></i>
            <div class="content">
                外部链接
            </div>
        </h2>
        <div class="ui divider"></div>
        <div id="lishishoulu" class="Echart" style="width:100%;height:300px"></div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/echarts/2.1.10/echarts-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/semantic.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/seo_echarts.js"></script>
</body>
</html>