<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 这个只是主机名后面的一级 url，不是代表整个主机路径地址，所以如果是远程主机的话，还是要使用其他页面中的 basePath 路径 --%>
<c:set var="base" scope="request" value="${pageContext.request.contextPath}/"></c:set>

<html>
<head>
    <title>Login Page</title>
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
</head>
<body>

<h3>Login faile</h3>
登录失败

<h3><a href="${base}login.jsp">去登录页面</a></h3>

<h3><a href="${base}test/others">其他信息介绍</a></h3>

<div>
    测试路径为： href ="test.jsp?p=fuck"

    basePath 方法：http://localhost:8080/test/

    getContextPath：/test   得到当前应用的根目录

    getServletPath：/test.jsp

    getRequestURI：/test/test.jsp

    getRequestURL：http://localhost:8080/test/test.jsp

    getRealPath：D:\Tomcat 6.0\webapps\test\

    getServletContext().getRealPath：D:\Tomcat 6.0\webapps\test\

    getQueryString：p=fuck
</div>

<%-- 安全框架直接返回的错误信息 --%>
<input type="hidden" id="failureMsg" value="${failureMsg}">
</body>

<script type="text/javascript">
    $(function () {

        var failureMsg = $("#failureMsg").val();
        if (failureMsg != "") {
            console.log("failureMsg");
            alert(failureMsg);
        }
    });
</script>

</html>
