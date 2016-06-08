<%--
  Created by IntelliJ IDEA.
  User: subDong
  Date: 2015/2/11
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录跳转中...</title>
</head>

<script type="text/javascript">
    window.onload=function(){
        var params = window.location.search;
        var param=params.split("=")[1]
        document.getElementById("j_username").value=param;
        document.getElementById("seoForm").submit();
    }
</script>
<body>
<h1>登录跳转中....</h1>

<form action="../j_spring_security_check" method="post" id="seoForm">
        <input id="j_username" name="j_username" type="hidden" value="${userName}"/>
        <input id="j_password" name="j_password" type="hidden"/>
</form>
</body>
</html>
