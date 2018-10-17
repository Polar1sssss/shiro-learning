<%--
  Created by IntelliJ IDEA.
  User: hujtb
  Date: 2018/10/10
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <base href="<%=basePath%>">

    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

</head>

<body>
    <form action="shiro/login" method="post">
        username:<input type="text" name="username"/></br>
        password:<input type="password" name="pwd"/></br>
        <input type="submit" value="login"/>
    </form>
</body>
</html>
