<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/11/011
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- spring security标签库 --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

    /**
     * getScheme：返回当前请求所使用的协议。 一般的应用返回 "http"，对于ssl则返回"https"
     *
     * getProtocol获取的是协议的名称： HTTP/1.11
     *
     * getServerName：获取服务器名字，如果是在本地的话就是localhost，远程则是 xxx.com
     *
     * getLocalName获取到的是IP
     *
     * getContextPath（）：得到当前应用的根目录
     */
%>
<html>
<head>
    <base href="<%=basePath%>">
    <base target="_blank" />
    <title>Title</title>

    <%-- 禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览。 --%>
    <meta http-equiv="pragma" content="no-cache">

    <%-- 设定页面使用即为简体中文，网页解析器必须支持 UTF-8, UTF16的(Unicode) 统一编码，也可以用”GB2312″或者”GBK”这写都是解决中文乱码问题!! --%>
    <meta http-equiv=”content-Type” content=”text/html; charset=utf-8″>
    <meta http-equiv="cache-control" content="no-cache">

    <%--  http-equiv=”expires” content=”web,26 Feb 2007 10:20:31 GMT” ，这个是网页过期时间--%>
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <%-- 进入与退出页面的特效 --%>
    <meta http-equiv=”Page-Enter” content=”revealTrans(duration=4, transition=3)”>
    <meta http-equiv=”Page-Exit” content=”revealTrans(duration=4, transition=8)”>

    <%--
        media 属性规定被链接文档将显示在什么设备上，被用于为不同的媒介类型规定不同的样式。
        screen	    计算机屏幕（默认）。
        tty	        电传打字机以及类似的使用等宽字符网格的媒介。
        tv	        电视机类型设备（低分辨率、有限的滚屏能力）。
        projection	放映机。
        handheld	手持设备（小屏幕、有限带宽）。
        print	    打印预览模式/打印页面。
        braille	    盲人点字法反馈设备。
        aural	    语音合成器。
        all	        适用于所有设备。
    --%>
    <link rel="stylesheet" href="/css/index.css" media="all">
</head>
<body>
Dear <strong>${user}</strong>, Welcome to Home Page.
<a href="<c:url value="/403.jsp" />"> 403页面 </a>

<h3><a href="httpMETA.jsp">HttpMETA 介绍</a></h3>

<div>
    <sec:authorize access="hasRole('ADMIN')">
        <label><a href="#">Start backup</a> | This part is visible only to one who is both ADMIN & DBA</label>
    </sec:authorize>
</div>
</body>
</html>
