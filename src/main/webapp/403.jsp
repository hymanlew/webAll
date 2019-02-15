<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/11/011
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="base" scope="request" value="${pageContext.request.contextPath}/" ></c:set>

<html>
<head>
    <title>无权访问</title>
</head>
<body>

<h3>你无权访问！</h3>
<h3><a href="${base}login.jsp">去登录页面</a></h3>
</body>
</html>
