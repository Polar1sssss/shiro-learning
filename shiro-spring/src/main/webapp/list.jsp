<%--
  Created by IntelliJ IDEA.
  User: hujtb
  Date: 2018/10/11
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <base href="<%=basePath%>">

    <title>List page</title>

</head>

<body>
    <h1>List page</h1>

    Welcome: <shiro:principal></shiro:principal>
    </br>

    <!--根据不同的角色显示不同超链接-->
    <shiro:hasRole name="admin">
        <a href="admin.jsp">Admin Page</a></br>
    </shiro:hasRole>
    <shiro:hasRole name="user">
        <a href="user.jsp">User Page</a></br>
    </shiro:hasRole>

    <a href="shiro/testShiroAnnoation">Test ShiroAnnoation</a>

    <a href="shiro/logout">Logout</a>
</body>
</html>
