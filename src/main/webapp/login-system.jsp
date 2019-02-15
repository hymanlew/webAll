<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="base" scope="request" value="${pageContext.request.contextPath}/" ></c:set>

<html>
<head><title>Login Page</title></head>

<%--
    当元素获得焦点时，发生 focus 事件。当通过鼠标点击选中元素或通过 tab 键定位到元素时，该元素就会获得焦点。
    focus() 方法触发 focus 事件，或规定当发生 focus 事件时运行的函数。
    即默认为选中状态，指定表单名。
--%>
<body onload='document.f.username.focus();'>

<h3>hello,man</h3>

<%--
    要特别注意，security默认登录和退出时只对 get请求方法有效，并且还必须要使用 post方式请求，它也会自动转为 get。
    否则它不会进行拦截。
--%>
<form name='f' action='${base}security/login' method='post'>
    <table>

        <%--
            登录名参数必须被命名为username，密码参数必须被命名为password
        --%>
        <tr>
            <td>User:</td>
            <td><input type='text' name='username' value=''></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password'/></td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="Login"/></td>
        </tr>
        <input name="_csrf" type="hidden" value="e988c864-659f-4ca9-b1a4-88b97305f227"/>
    </table>
</form>

</body>
</html>
