<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="base" scope="request" value="${pageContext.request.contextPath}/" ></c:set>

<html>
<head>
    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/js/checkbrowser.js"></script>
    <title>Login Page</title>
</head>
<body onload='document.f.username.focus();'>

<h3>hello,man</h3>
已退出
<h3><a href="${base}login.jsp">去登录页面</a></h3>

<div id="divInfo">
</div>
<script type="text/javascript">
    $(function () {
        browserCheck();
    });

    function browserCheck() {
        var browser = new browserInfo();
        var html = "<caption><p>" + navigator.userAgent + "</p></caption><table>"
            + "<thead><tr>"
            + "<th>浏览器</th>"
            + "<th>版本</th>"
            + "<th>内核</th>"
            + "<th>操作系统</th>"
            + "<th>设备</th>"
            + "<th>语言</th>"
            + "</tr></thead>"
            + "<tr>"
            + "<td>" + browser.browser + "</td>"
            + "<td>" + browser.version + "</td>"
            + "<td>" + browser.engine + "</td>"
            + "<td>" + browser.os + " " + browserInfo.osVersion + "</td>"
            + "<td>" + browser.device + "</td>"
            + "<td>" + browser.language + "</td>"
            + "</tr></table>";
        document.getElementById("divInfo").outerHTML = html;

        var is360 = _mime("type", "application/vnd.chromium.remoting-viewer");
        if(is360){
            console.log("browser :"+360);
        }
    }

</script>
</body>
</html>
