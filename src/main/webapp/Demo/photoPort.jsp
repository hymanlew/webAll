<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/29/029
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>摄像头和视频控制接口</title>
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
</head>
<body>
    <div class="demo-wrapper">
        <h1>HTML5 获取摄像头和视频控制接口</h1>
        <p class="demo-intro">使用Opera Next 或 Chrome Canary，这个页面可以用来自拍。</p>

        <video id="video" width="640" height="480" autoplay=""></video>
        <button id="snap" class="sexyButton">Snap Photo</button>
        <canvas id="canvas" width="640" height="480"></canvas>
    </div>
</body>
<script>

    // 设置事件监听器
    window.addEventListener("DOMContentLoaded",function () {
        // 获取存放图形及 video的元素
        var canvas = document.getElementById("canvas"),
            context = canvas.getContext("2d"),
            video = document.getElementById("video"),
            videoObj = {
                "video" : true
            },
            errback = function (err) {
                alert("video error:"+err);
            }

        // 设置video监听器，获取摄像头影像
        if(navigator.getUserMedia){     // Standard 标准的
            navigator.getUserMedia(videoObj,function (stream) {
                video.src = stream;
                video.play();
            },errback);

        }else if(navigator.webkitGetUserMedia){     // WebKit-prefixed
            navigator.webkitGetUserMedia(videoObj,function (stream) {
                video.src = window.webkitURL.createObjectURL(stream);
                video.play();
            },errback);
        }
    },false);
</script>
</html>
