<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/16/016
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>js 中的 boolean判断条件</title>
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
</head>
<body>
<h2>测试</h2>
<h3>

从下面的测试结果来看，在js中：
    定义未赋值的变量、数字0、null、undefined、空字符串""、''、以及布尔值false在条件判断中都为false；
    除此之外的值就为 true，包括空数组[]、空值对象{};

    同时未定义的变量进行判断则会报错。

</h3>

<script type="text/javascript">
    $(function(){
        var a,b=-1,c= 1,d= 0,e=null,f=undefined,g='',h="",i = false,j=true,k=[],l={};

        if (a) {
            console.log("a is true");
        }else{
            // a 未定义值，undefined，所以为 false
            console.log("a is false");
        }
        if (b) {
            // b 有值，为 true
            console.log("b is true");
        }else{
            console.log("b is false");
        }
        if (c) {
            // c 有值，为 true
            console.log("c is true");
        }else{
            console.log("c is false");
        }
        if (d) {
            console.log("d is true");
        }else{
            // d 有值，但为0，则是 false
            console.log("d is false");
        }
        if (e) {
            console.log("e is true");
        }else{
            // null，则是 false
            console.log("e is false");
        }
        if (f) {
            console.log("f is true");
        }else{
            // undefined，则是 false
            console.log("f is false");
        }
        if (g) {
            console.log("g is true");
        }else{
            // 空串，为 false
            console.log("g is false");
        }
        if (h) {
            console.log("h is true");
        }else{
            // 空串，为 false
            console.log("h is false");
        }
        if (i) {
            console.log("i is true");
        }else{
            // boolean false，为 false
            console.log("i is false");
        }
        if (j) {
            // boolean true，为 true
            console.log("j is true");
        }else{
            console.log("j is false");
        }
        if (k) {
            // 空数组，为 true
            console.log("k is true");
        }else{
            console.log("k is false");
        }
        if (l) {
            // 空值对象（大括号），为 true
            console.log("l is true");
        }else{
            console.log("l is false");
        }
        // 未声明的变量报错，未定义异常，not defined
        if (m) {
            console.log("m is true");
        }else{
            console.log("m is false");
        }
    });
</script>

</body>
</html>
