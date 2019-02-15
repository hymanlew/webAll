<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="base" scope="request" value="${pageContext.request.contextPath}/" ></c:set>

<html>
<head>
    <meta charset="utf-8">
    <title>spring secuirty 自定义登录页面</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <title>Login Page</title>
</head>

<%--
    当元素获得焦点时，发生 focus 事件。当通过鼠标点击选中元素或通过 tab 键定位到元素时，该元素就会获得焦点。
    focus() 方法触发 focus 事件，或规定当发生 focus 事件时运行的函数。
    即默认为选中状态，指定表单名。
--%>
<body onload="document.f.username.focus();">
<h3>hello,man</h3>

<%--
    要特别注意，security默认登录和退出时只对 get请求方法有效，并且还必须要使用 post方式请求，它也会自动转为 get。
    否则它不会进行拦截。
--%>
<div class="container-fluid">
    <div class="row-fluid">
        <%--<form class="form-horizontal col-md-4" role="form" action='${base}security/login' method="post">
             并且该路径必须与 security 指定的方法保持一致（这个路径不重要，是显示在浏览器上的路径）。否则会 302 重定向，或 404
            （它与 successHandler 有关）。
        --%>
        <form class="form-horizontal col-md-4" name="f" action='/login' method="post">
            <fieldset>
                <legend>自定义登录表单</legend>
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="username" name="username"
                               placeholder="请输入用户名">
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="请输入密码">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">登录</button>
                        <span><input type="checkbox" class="m-checkbox" name="_spring_security_remember_me"><label class="label-click"></label></span>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>
</body>

<script language="javascript">
    document.onkeyup = function(e){      //onkeyup是javascript的一个事件、当按下某个键弹起 var _key;                                                 //的时触发
        if (e == null) { // ie
            _key = event.keyCode;
        } else { // firefox              //获取你按下键的keyCode
            _key = e.which;          //每个键的keyCode是不一样的
        }

        if(_key == 13){   //判断keyCode是否是13，也就是回车键(回车的keyCode是13)
            //if (validator(document.loginform)){ //这个因该是调用了一个验证函数
            document.getElementById('btnLogin').click()    //验证成功触发一个Id为btnLogin的
            //}                                                                        //按钮的click事件，达到提交表单的目的
        }
    }
</script>
</html>
