<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
          prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>人证比对</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="<%=basePath%>css/font-awesome.min.css" media="all">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/pagination.css" media="screen">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>jedate/skin/jedate.css">
    <link rel="stylesheet" href="<%=basePath%>css/index1.2.css" media="all">
    <link href="<%=basePath%>css/bidui.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>jedate/jquery.jedate.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/menu.js"></script>
</head>

<body style="overflow-x: hidden;">

<!-- 右侧头部通用开始 -->
<div class="right-top" style="top: 0px;left: 0px;">
    <img src="images/logo-d.png" width="20">
    <p>人证比对</p>
</div>
<!-- 右侧头部通用结束 -->
<!-- 右侧body开始 -->
<div class="index-right" style="top: 0px;left: 0px;">
    <div class="rightbody">
        <div class="right-detail">
            <p class="condition"><span class="fa fa-search"></span>按条件搜索</p>
            <div class="detail-f1">

                    <div class="detail-style-1">
                        <p>姓名：</p>
                        <input type="" name="" id="name">
                    </div>



                    <button class="detail-style-5" id="search">搜索</button>
            </div>
            <table class="table">
                <thead>
                <tr>
                    <th>姓名</th>
                    <th>身份证号</th>
                    <th>比对结果</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="employeeList">

                </tbody>
            </table>
            <div class="M-box" style="bottom:0; z-index:0; position:relative; margin-top:40px;left: 110%"></div>
        </div>
    </div>
    <div class="right_r">
        <div class="right_t">
            <div class="right_mo">
                <video id="video_stream" style="width:100%;  height:100%;" autoplay></video>
            </div>
            <div class="right_we"  id = "photo" style="width: 40%;margin-left: 30%; float: left;"><p>拍摄</p></div>
            <p style="float: left;height:10%;width:25%;color: red;font-weight: bold; font-size: 12px;display:flex;justify-content:center;align-items:center;" id="example" >照片示例？</p>
            <p style="text-align: center; color: red; height:8%;width: 100%;float: left;">请勿佩戴眼镜，免冠正脸拍摄，</p>
            <p style="text-align: center; color: red;height: 8%;width: 100%;float: left;">拍摄画面内无其他人员。</p>
        </div>
        <div class="right_t1">
            <div class="right_mo" >
                <canvas id="canvas" style="width:100%;  height:100%;"></canvas>
            </div>
            <input style="width: 88%; height:6%;border: solid 1px #e6e6e6; margin-left: -20px; padding-left: 10px;" placeholder="请输入图片地址" id="url">
            <p id="choose" style="height: 10%;">您选择了：</p>
            <div class="right_we" id="compare"><p>人证比对</p></div>
        </div>
    </div>

</div>
<!-- 右侧body结束 -->
<!-- 弹窗 -->

<input type="hidden" id="siteID" value="${siteID}">
<input type="hidden" id="dataNumber" value="${dataNumber}">
<input type="hidden" id="identity" value="">
<input type="hidden" id="pageNum" value="1">
<input type="hidden" id="pageSize" value="10">
<input type="hidden" id="noPic" value="true">
<script type="text/javascript">
    // 获取弹窗
    var modal = document.getElementById('myModal');

    // 打开弹窗的按钮对象
    var btn = document.getElementById("myBtn");

    // 获取 <span> 元素，用于关闭弹窗 that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // 点击按钮打开弹窗
    function detailbox() {
        modal.style.display = "block";
    }

    // 在用户点击其他地方时，关闭弹窗
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>
<!-- 分页插件开始 -->
<script src="<%=basePath%>js/jquery.pagination.js"></script>
<script type="text/javascript">
</script>

<!-- 分页插件结束 -->
<script type="text/javascript" src="<%=basePath%>js/index.js"></script>
<!-- 时间js开始 -->
<script>
    var start = {
        format: 'YYYY-MM-DD',
        minDate: '2014-06-16', //设定最小日期为当前日期
        isinitVal:true,
        festival:false,
        ishmsVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        choosefun: function(elem, val, date){
            end.minDate = date; //开始日选好后，重置结束日的最小日期
            endDates();
        }
    };
    var end = {
        format: 'YYYY-MM-DD',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        festival:false,
        maxDate: '2099-06-16', //最大日期
        choosefun: function(elem, val, date){
            start.maxDate = date; //将结束日的初始值设定为开始日的最大日期
        }
    };
    //这里是日期联动的关键
    function endDates() {
        //将结束日期的事件改成 false 即可
        end.trigger = false;
        $("#inpend").jeDate(end);
    }
    $('#inpstart').jeDate(start);
    $('#inpend').jeDate(end);
    $('#customymd').jeDate({
        isinitVal:true,
        format:"YYYY-MM-DD"
    });
</script>
<!-- 时间js结束 -->
<!-- 点击人员姓名切换div开始 -->
<script type="text/javascript">
    function dianji(){
        $(".rightbody1").show().siblings(".rightbody").hide();
    }
</script>
<!-- 点击人员姓名切换div结束 -->
<!-- 点击返回项目工地开始 -->
<script type="text/javascript">
    $("#fanhui").click(function(){
        $(".rightbody").show().siblings(".rightbody1").hide();
    })
</script>
<!-- 点击返回项目工地开始 -->
<script type="text/javascript">

    $(function () {
        $("#search").click(function () {
            $("#pageNum").val(1);
            getEmployee();
        });
        $("#compare").click(function () {
            compare();
        })
        getEmployee();
        $("#url").change(function () {
            var cvs = document.getElementById("canvas");
            var ctx = cvs.getContext('2d');
            if($("#url").val()==""){
                ctx.clearRect(0, 0, cvs.width, cvs.height);
                $("#noPic").val("true");
                return;
            }

            var imgObj = new Image();
//            imgObj.src = "http://218.4.84.171:5445/AppGiantHopeSzSq/GiantHopePage/Module_Worker/ImageShow.aspx?WorkerID=52995&PhotoPathOfIDCardNo=";
            imgObj.src = $("#url").val();
            imgObj.onload = function(){
                ctx.drawImage(this, 50, 0, 200,150);//this即是imgObj,保持图片的原始大小：470*480
                $("#noPic").val("false");
            }
        });
        $("#example").click(function () {
            window.open("<%=basePath%>face_example.jsp");
        })
    });


    // MediaDevices.getUserMedia() 会提示用户给予使用媒体输入的许可，媒体输入会产生一个MediaStream，里面包含了请求的媒体类型的轨道。
    // 此流可以包含一个视频轨道（来自硬件或者虚拟视频源，比如相机、视频采集设备和屏幕共享服务等等）、一个音频轨道（同样来自硬件或虚拟音频源，
    // 比如麦克风、A/D转换器等等），也可能是其它轨道类型。

    // 它返回一个 Promise 对象，成功后会resolve回调一个 MediaStream 对象。若用户拒绝了使用权限，或者需要的媒体源不可用，promise会
    // reject回调一个  PermissionDeniedError 或者 NotFoundError 。

    // constraints 参数是一个包含了video 和 audio两个成员的 MediaStreamConstraints对象，指定了请求的媒体类型和相对应的参数，用于说
    // 明请求的媒体类型。必须至少一个类型或者两个同时可以被指定。如果浏览器无法找到指定的媒体类型或者无法满足相对应的参数要求，那么返回的
    // Promise对象就会处于rejected［失败］状态，NotFoundError作为rejected［失败］回调的参数。

    // 想要获取一个最接近 1280x720 的相机分辨率
    var constraints = { audio: true, video: { width: 1280, height: 720 } };

    navigator.mediaDevices.getUserMedia(constraints)
        .then(function(mediaStream) {
            var video = document.querySelector('video');
            video.srcObject = mediaStream;
            video.onloadedmetadata = function(e) {
                video.play();
            };
        })
        .catch(function(err) { console.log(err.name + ": " + err.message); }); // 总是在最后检查错误



    // 以下方法提醒用户需要使用音频（0或者1）和（0或者1）视频输入设备，比如相机，屏幕共享，或者麦克风。如果用户给予许可，successCallback
    // 回调就会被调用，MediaStream对象作为回调函数的参数。如果用户拒绝许可或者没有媒体可用，errorCallback就会被调用，类似的，Permission
    // DeniedError 或者NotFoundError对象作为它的参数。注意，有可能以上两个回调函数都不被调用，因为不要求用户一定作出选择（允许或者拒绝）。

    // 该特性已经从 Web 标准中删除，虽然一些浏览器目前仍然支持它，但也许会在未来的某个时间停止支持，请尽量不要使用该特性（用上面的方法代替）。
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia
        || navigator.mozGetUserMedia || navigator.msGetUserMedia;

    window.addEventListener("DOMContentLoaded", function() {
        accessLocalWebCam();
    });
    var video = document.getElementById("video_stream");
    var context = canvas.getContext("2d");


    // 分析并调用当前不同浏览器所产生的媒体流 url对象
    window.URL = window.URL || window.webkitURL || window.msURL || window.oURL;


    function successsCallback(stream) {
        document.getElementById('video_stream').src = (window.URL && window.URL.createObjectURL) ? window.URL.createObjectURL(stream) : stream;
    }

    function errorCallback(err) {
        console.log(err);
    }
    function accessLocalWebCam() {
        try {
            // Tries it with spec syntax
            navigator.getUserMedia({ video: true }, successsCallback, errorCallback);

        } catch (err) {
            console.log(err);
            // Tries it with old spec of string syntax
            navigator.getUserMedia('video', successsCallback, errorCallback);

        }
    }

    document.getElementById("photo").addEventListener("click", function() {
        context.drawImage(video, 0, 0, 300,150);
        $("#noPic").val("false");
    });
    function compare(){
        var identity=$("#identity").val();
        if($("#url").val()==""){
            if(identity==null||identity==""){
                alert("请选择需要比对的人员！");
                return;
            }
            if($("#noPic").val()=="true"){
                alert("缺少用于比对的照片");
                return;
            }
            var dataURL=canvas.toDataURL('image/jpeg'); //转换图片为dataURL
            console.log(dataURL);
            $.ajax({
                type:"post",
                url:"<%=basePath%>face/faceCompare_allow",
                dataType : "json",
                data : {
                    "face":dataURL,
                    "identity":identity,
                    "dataNumber":$("#dataNumber").val()
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                },
                success : function(resultObj) {
                    alert(resultObj.msg);
                }
            });
        }else{
            $.ajax({
                type:"post",
                url:"<%=basePath%>face/faceCompareRemote_allow",
                dataType : "json",
                data : {
                    "url":$("#url").val(),
                    "identity":identity,
                    "dataNumber":$("#dataNumber").val()
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                },
                success : function(resultObj) {
                    alert(resultObj.msg);
                    $("#url").val("");
                    var cvs = document.getElementById("canvas");
                    var ctx = cvs.getContext('2d');
                    ctx.clearRect(0, 0, cvs.width, cvs.height);
                    $("#noPic").val("true");

                }
            });
        }

    }
    function getEmployee(){
        var name=$("#name").val();
        $.ajax({
            type:"post",
            url:"<%=basePath%>employee/getComparePager_allow",
            dataType : "json",
            data : {
                "siteId":$("#siteID").val(),
                "name":name,
                "pageNum":$("#pageNum").val(),
                "pageSize":$("#pageSize").val()
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            },
            success : function(map) {
                var html="";
                var list=map.list;
                var count=map.count;
                if(list!=null&&list.length>0){
                    for(var i=0;i<list.length;i++){
                        var employee=list[i];
                        html+=' <tr>';
                        html+=' <td><a href="javascript:;" onclick="dianji()" class="hrefbox">'+employee.name+'</a></td>';
                        html+='<td>'+employee.identity+'</td>';
                        if(employee.compare==1){
                            html+='<td>比对通过</td>';
                        }
                        if(employee.compare==2){
                            html+='<td>未比对</td>';
                        }
                        if(employee.compare==3){
                            html+='<td>比对未通过</td>';
                        }
                        html+='<td>';
                        html+='<ul class="an_n">';
                        html+='<li onclick="checkCompare(\''+employee.identity+'\')">比对结果检查</li>';
                        html+='<li onclick="chooseEmployee(\''+employee.name+'\',\''+employee.identity+'\')">人证比对</li>';
                        html+='</ul>';
                        html+='</td>';
                        html+='</tr>';
                    }
                    $("#employeeList").html(html);
                    var pageCount=0;
                    var pageSize=parseInt($("#pageSize").val());
                    pageCount=Math.ceil(count/pageSize);

                    //如果分页数是1页以上，才显示分页插件
                    $('.M-box').html("");
                    if(pageCount>1) {
                        var current = $("#pageNum").val();
                        $('.M-box').pagination({
                            pageCount: pageCount,
                            jump: true,
                            current: current,
                            coping: true,
                            homePage: '首页',
                            endPage: '末页',
                            prevContent: '<',
                            nextContent: '>',
                            callback: function (page) {
                                var current = page.getCurrent();
                                $("#pageNum").val(current);
                                getEmployee();
                            }
                        });
                    }
                }
            }
        });
    }


    function chooseEmployee(name,identity){
        $("#identity").val(identity);
        $("#choose").html("您选择了:"+name);
    }
    function checkCompare(identity) {
        $.ajax({
            type:"post",
            url:"<%=basePath%>face/checkCompare_allow",
            dataType : "json",
            data : {
                "identity":identity,
                "dataNumber":$("#dataNumber").val()
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            },
            success : function(resultObj) {
                alert(resultObj.msg);
            }
        });
    }
</script>
</body>
</html>
