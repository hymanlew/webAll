<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/29/029
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>拍照功能</title>
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
</head>
<body>
    <div>
        <p>Video</p>
        <video src="movie.mp4" controls="controls">
            您的浏览器不支持 video 标签。
        </video>
        <p>摄像头</p>
        <video id="video_stream" style="width:100%;  height:100%;" autoplay></video>
    </div>
    <div>
        <canvas id="canvas" style="width:100%;  height:100%;"></canvas>
        <button id="takephoto">拍照</button>
    </div>
</body>
<script type="text/javascript">

    // Normalizes window.URL    将 URL 标准化（以适应各种类型浏览器的请求）：
    window.URL = window.URL || window.webkitURL || window.msURL || window.oURL;

    // window.webkitURL 和 window.URL是一样的，window.URL标准定义，window.webkitURL是webkit内核的实现（一般手机上就是使用这个），
    // 还有火狐等浏览器的实现。


    // Normalizes navigator.getUserMedia    获取浏览器内的媒体信息，并标准化：
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia
        || navigator.mozGetUserMedia || navigator.msGetUserMedia;


    var video = document.getElementById("video_stream");

    // 浏览器事件监听方法，可以使用 removeEventListener() 方法来移除事件的监听。
    // 第一个参数是事件的类型 (如 "click" 或 "mousedown")
    // 第二个参数是事件触发后调用的函数。
    // 第三个参数是个布尔值用于描述事件是冒泡还是捕获。该参数是可选的。
    // 注意:不要使用 "on" 前缀。 例如，使用 "click" ,而不是使用 "onclick"。

    // 1、当 onload 事件触发时，页面上所有的DOM，样式表，脚本，图片，flash都已经加载完成了。
    // 2、当 DOMContentLoaded 事件触发时，仅当DOM加载完成，不包括样式表，图片，flash。
    window.addEventListener("DOMContentLoaded",function () {
        // 设置video监听器，获取摄像头影像
        accessLocalWebCam();
    });

    // URL对象是硬盘（SD卡等）指向文件的一个路径，如果我们做文件上传的时候，想在没有上传服务器端的情况下看到上传图片的效果图的时候就可是以
    // 通过var url=window.URL.createObjectURL(obj.files[0]);获得一个http格式的url路径，通过这个URL,可以获取到所指定文件的完整内容。
    // 即是创建一个新的对象URL,该对象URL可以代表某一个指定的File对象或Blob对象.
    function successCallback(stream) {
        video.src = (window.URL && window.URL.createObjectURL? window.URL.createObjectURL(stream):stream);
    }

    function errorCallback(err) {
        alert(err);
    }

    function accessLocalWebCam() {
        try{
            navigator.getUserMedia({video:true},successCallback,errorCallback);
        }catch (err){
            alert(err);
            // Tries it with old spec of string syntax，尝试使用旧的字符串语法规范。
            navigator.getUserMedia("video",successCallback,errorCallback);
        }
    }


    var canvas = document.getElementById("canvas");
    var context = canvas.getContext("2d");
    // 点击按钮进行拍照
    document.getElementById("takephoto").addEventListener("click",function () {
        context.drawImage(video,0,0,300,150);
    });

    // <video> 标签定义视频，比如电影片段或其他视频流。
    // 可以在开始标签和结束标签之间放置文本内容，这样老的浏览器就可以显示出不支持该标签的信息。
    //
    // 属性	        值	        描述
    // autoplay	    autoplay	如果出现该属性，则视频在就绪后马上播放。
    // controls	    controls	如果出现该属性，则向用户显示控件，比如播放按钮。
    // height	    pixels	    设置视频播放器的高度。
    // loop	        loop	    如果出现该属性，则当媒介文件完成播放后再次开始播放。
    // preload	    preload     如果出现该属性，则视频在页面加载时进行加载，并预备播放。但如果使用 "autoplay"，则忽略该属性。
    // src	        url	        要播放的视频的 URL。
    // width	    pixels	    设置视频播放器的宽度。

    // <canvas> 标签定义图形，比如图表和其他图像。
    // <canvas> 标签只是图形容器，您必须使用脚本来绘制图形。


    // 人证比对（照片比对）
    // canvas.toDataURL(type, encoderOptions);
    //
    // type            可选   图片格式，默认为 image/png
    // encoderOptions  可选   在指定图片格式为 image/jpeg 或 image/webp的情况下，可以从 0 到 1 的区间内选择图片的质量。如果超出取值范围，
    //                       将会使用默认值 0.92。其他参数会被忽略。
    //
    // 返回值                 包含 data URI 的DOMString。

    // 该方法返回一个包含图片展示的 data URI 。可以使用 type 参数其类型，默认为 PNG 格式。图片的分辨率为96dpi。
    // 如果画布的高度或宽度是0，那么会返回字符串“data:,”。
    // 如果传入的类型非“image/png”，但是返回的值以“data:image/png”开头，那么该传入的类型是不支持的。
    // Chrome支持“image/webp”类型。

    var dataURL = canvas.toDataURL();
    // 将图片 URL 发送到控制器
    $.ajax({
        type:"post",
        url:"Controller/method",
        data:{
            "dataurl":dataURL
        },
        dataType:"json",
        error: function (eror) {
            console.log(eror.msg);
        },
        sucess:function (msg) {
            console.log(msg.msg);
        }
    });


</script>
</html>
