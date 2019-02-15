<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/22/022
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Title</title>

    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/js/index1.2.js"></script>

</head>
<body>
    <h3><a href="javascript:getTargetUrl()">退出</a></h3>

    <h3>测试</h3>
    <a href="javascript:getTargetUrl1()">去测试页面</a>

    <h3>测试日期滚动条</h3>
    <div class="picbox">
        <ul class="piclist mainlist" id="dayList">
            <li><a href="#" target="_blank">1日</a></li>
            <li><a href="#" target="_blank">2日</a></li>
            <li><a href="#" target="_blank">3日</a></li>
            <li><a href="#" target="_blank">4日</a></li>
            <li><a href="#" target="_blank">5日</a></li>
            <li><a href="#" target="_blank">6日</a></li>
            <li><a href="#" target="_blank">7日</a></li>
            <li><a href="#" target="_blank">8日</a></li>
            <li><a href="#" target="_blank">9日</a></li>
            <li><a href="#" target="_blank">10日</a></li>
            <li><a href="#" target="_blank">11日</a></li>
            <li><a href="#" target="_blank">12日</a></li>
            <li><a href="#" target="_blank">13日</a></li>
            <li><a href="#" target="_blank">14日</a></li>
            <li><a href="#" target="_blank">15日</a></li>
            <li><a href="#" target="_blank">16日</a></li>
            <li><a href="#" target="_blank">17日</a></li>
            <li><a href="#" target="_blank">18日</a></li>
            <li><a href="#" target="_blank">19日</a></li>
            <li><a href="#" target="_blank">20日</a></li>
            <li><a href="#" target="_blank">21日</a></li>
            <li><a href="#" target="_blank">22日</a></li>
            <li><a href="#" target="_blank">23日</a></li>
            <li><a href="#" target="_blank">24日</a></li>
            <li><a href="#" target="_blank">25日</a></li>
            <li><a href="#" target="_blank">26日</a></li>
            <li><a href="#" target="_blank">27日</a></li>
            <li><a href="#" target="_blank">28日</a></li>
            <li><a href="#" target="_blank">29日</a></li>
            <li><a href="#" target="_blank">30日</a></li>
        </ul>
    </div>
</body>

<script type="text/javascript">

    function getTargetUrl() {
        var url= getBasePath()+"/security/logout";
        location.href = url;
    }

    function getTargetUrl1() {
        var url= getBasePath()+"/test/takephotos";
        location.href = url;
    }


    // 向右滚动日期
    $('.og_next').click(function(){
        i= Math.ceil(($('.mainlist li').length)/days);
        if($('.swaplist,.mainlist').is(':animated')){
            $('.swaplist,.mainlist').stop(true,true);
        }
        console.log(numJ,i);
        // alert(i);
        if(numJ<i){
            // alert(numJ);
            // alert(1111);
            if($('.mainlist li').length>7){//多于4张图片
                // console.log("length :"+$('.mainlist li').length);
                ml = parseInt($('.mainlist').css('left'));//默认图片ul位置
                sl = parseInt($('.swaplist').css('left'));//交换图片ul位置
                if(ml<=0 && ml>w*-1){//默认图片显示时
                    $('.swaplist').css({left: '100%'});//交换图片放在显示区域右侧
                    // 1是1400X1050以上分辨率，-1是低于这个的分辨率
                    if(windowSize>0){
                        $('.mainlist').animate({left: ml - 1050 + 'px'},'slow');//默认图片滚动
                    }else if(windowSize<0){
                        $('.mainlist').animate({left: ml - 717 + 'px'},'slow');//默认图片滚动
                    }
                    if(ml==(w-1050)*-1){//默认图片最后一屏时
                        $('.swaplist').animate({left: '0px'},'slow');//交换图片滚动
                    }
                }else{//交换图片显示时
                    $('.mainlist').css({left: '1000px'})//默认图片放在显示区域右
                    $('.swaplist').animate({left: sl - 1050 + 'px'},'slow');//交换图片滚动
                    if(sl==(w-1050)*-1){//交换图片最后一屏时
                        $('.mainlist').animate({left: '0px'},'slow');//默认图片滚动
                    }
                }
            }
            numJ++;
        }
        var d = new Date(arr[arr.length - 1]);
        var begin = $("#begin_time").val();
        var date = new Date(begin);
        date.setDate(date.getDate() + days);
        if (date > d) {
            return;
        }
        var str = date.getFullYear() + "-"
            + (date.getMonth() + 1) + "-" + date.getDate();
        console.log("str :"+str);
        findAttendance(str,$("#pageNumber").val(),$("#pageSize").val());
        // alert()
    })

    // 向左滚动日期
    $('.og_prev').click(function(){
        i= Math.ceil(($('.mainlist li').length)/days);
        if($('.swaplist,.mainlist').is(':animated')){
            $('.swaplist,.mainlist').stop(true,true);
        }
        if(numJ>1){
            if($('.mainlist li').length>7){
                ml = parseInt($('.mainlist').css('left'));
                sl = parseInt($('.swaplist').css('left'));
                if(ml<=0 && ml>w*-1){
                    $('.swaplist').css({left: w * -1 + 'px'});
                    // 1是1400X1050以上分辨率，-1是低于这个的分辨率
                    if(windowSize>0){
                        $('.mainlist').animate({left: ml + 1050 + 'px'},'slow');
                    }else if(windowSize<0){
                        $('.mainlist').animate({left: ml + 717 + 'px'},'slow');
                    }
                    if(ml==0 ){
                        $('.swaplist').animate({left: (w - 1050) * -1 + 'px'},'slow');
                    }
                }else{
                    $('.mainlist').css({left: (w - 1050) * -1 + 'px'});
                    $('.swaplist').animate({left: sl + 1050 + 'px'},'slow');
                    if(sl==0){
                        $('.mainlist').animate({left: '0px'},'slow');
                    }
                }
            }
            numJ--;
        }
        var d = new Date(arr[0]);
        var begin = $("#begin_time").val();
        var date = new Date(begin);
        date.setDate(date.getDate() - days);
        if (date < d) {
            return;
        }
        var str = date.getFullYear() + "-"
            + (date.getMonth() + 1) + "-" + date.getDate();
        findAttendance(str,$("#pageNumber").val(),$("#pageSize").val());
    })
</script>
<script type="text/javascript">
    var windowSize = 1;
    if(window.screen.width<1400 && window.screen.height<800){
        windowSize = -1;
        console.log("1 :"+window.screen.width+", "+window.screen.height);
        console.log($(".piclist a"));
        // document.write("<link href='"+basePath+"/css/differentResolution/lowForAttendance.css' rel='stylesheet' type='text/css'>");
        $(".table").css({"margin":"0","padding":"0"});
        $("#dayList").css("width","0");

        console.log($("#dayList").css("width"));
        $("#dayList").css("width","100%");

        $(".piclist li").css("width","47px");
        $(".piclist a").css("width","43px");
        $(".time-small li").css({"width":"46px","margin-right":"1px"});

        // $(".piclist li").each(function (index) {
        //     $(this).css("width","47px");
        // });
        // $(".piclist a").each(function (index) {
        //     $(this).css("width","43px");
        // });
        // $(".time-small li").each(function (index) {
        //     $(this).css({"width":"46px","margin-right":"1px"});
        // });

        // var lis = document.getElementById("dayList").children;
        // console.log(lis);
        // for(var i=0;i<lis.length;i++){
        //     lis[i].style.width="47px";
        //     console.log($(".piclist li").css("width"));
        // }
    }
</script>
</html>
