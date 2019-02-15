<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>吴中区劳务实名制云平台</title>

    <link rel="stylesheet" href="<%=basePath%>plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="<%=basePath%>css/global.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>css/index.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>plugins/font-awesome/css/font-awesome.min.css">
    <link href="<%=basePath%>css/none1.css" rel="stylesheet" type="text/css">

</head>
<body onbeforeunload="">
<div class="layui-box">
    <div class="layui-s">
        <div class="layui-g">
            <ul>
                <li style="width:62%; padding-left:2%"><p>S/N编号：</p><p id="sn_show">${sn}</p></li>
                <li style="width:12%;">
                    <div class="layui-c">
                        <h1 id="connect" onclick="connect()">连接设备</h1>
                        <%--<h1 onclick="testConnect()">连接状态</h1>--%>
                    </div>
                </li>
                <li style="width:12%;">
                    <div class="layui-t">
                        <h1 id="sync" onclick="loadAllUser()">同步信息</h1>
                    </div>
                </li>
                <li style="width:12%;">
                    <div class="layui-c">
                        <h1 id="clear" onclick="clearLog()">清空日志</h1>
                    </div>
                </li>
            </ul>
        </div>
        <div class="layui-x"  style="margin-top: 40px;overflow-x:hidden;">

            <%--
                <object> 标签用于包含对象，比如音频、视频、ActiveX、PDF 以及 Flash。是定义了一个嵌入的对象，向 XHTML 页面添加多媒体。
                由于浏览器的对象支持依赖于对象类型，而主流浏览器都使用不同的代码来加载相同的对象类型。所以 object 对象提供了解决方案。如果未显示 object 元素，
                就会执行位于 <object> 和 </object> 之间的代码。通过这种方式，我们能够嵌套多个 object 元素（每个对应一个浏览器）。

                <param> 标签定义用于对象的 run-time 设置。
                不要对图像使用 <object> 标签，请使用 <img> 标签代替。

                <object> 标签支持 HTML 中的全局属性，和事件属性。

                archive 属性：因为性能方面的原因，可以选择预先下载包含一个或多个档案中的对象集。即连接所需要的数据。它的值是一个用引号括起来的 URL 列表，其中每个
                    URL 都指向一个在显示或执行对象之前浏览器需要加载的档案文件。类似 c 标签的 import。

                classid 属性：用于指定浏览器中包含的对象的位置，通常是一个 Java 类。它的值是对象的绝对或相对的 URL。如果提供了 codebase 属性的话，相对 URL 是
                    相对于 codebase 属性指定的 URL而言的；否则它们是相对于当前文档的 URL。

                codebase 属性：是一个可选的属性，该属性的值是一个 URL，它指向的目录包含了 classid 属性所引用的对象。codebase URL会覆盖文档的基本 URL，但不会
                    永久替代它。如果不使用 codebase 属性，这个基本 URL就是默认值。

                即 codebase 属性与 classid 属性配合使用，可以指定对象的完整 URL。

                codetype 属性：用于标识程序代码类型。只有在浏览器无法根据 classid 属性决定 applet 的 MIME 类型，或者如果在下载某个对象时服务器没有传输正确的 MIME
                    类型的情况下，才需要使用 codetype 属性。

                codetype 属性与 type 属性类似。不同的是 codetype 用来标识程序代码类型，而 type 属性用来标识数据文件类型。

                declare 属性：可以定义一个对象，但同时防止浏览器进行下载和处理。与 name 属性一起使用时，这个工具类似于更为传统的编程语言中的某种前置声明，这样的声明能
                    够延迟下载对象的时间，直到这个对象确实在文档中得到了应用。

                data 属性：用于指定供对象处理的数据文件的 URL。该属性的值是文件的 URL，该 URL可能是相对于文件基本 URL 的绝对 URL 或相对 URL，或者是相对于用 codebase
                    属性提供的 URL 的绝对或相对 URL。浏览器通过插入到文档中的对象类型来决定数据的类型。

                    该属性类似于 <img> 标签中的 src 属性，因为它也是一个路径地址。它们之间的差别在于，data 属性允许包含几乎任何文件类型，而不仅仅是图像文件。
            --%>

            <object id="sdkobj" classid="CLSID:8BCD4411-D055-4DAF-BAA6-2942DDD370E0" name="sdkobj"
                    codebase="<%=basePath%>ocx/C_FaceServerSdk.ocx#version=1.0" type="application/x-oleobject"
                    width="32" height="32" style="border: solid 1px green;">
                <%--<param name="BorderStyle" value="1" />--%>
                <%--<param name="MousePointer" value="0" />--%>
                <%--<param name="Enabled" value="1" />--%>
                <%--<param name="Min" value="0" />--%>
                <%--<param name="Max" value="10" />--%>
            </object>

            <%--
                OCX 是对象类别扩充组件（Object Linking and Embedding (OLE) Control Extension）；是可执行的文件的一种，但不可直接被执行； 是 ocx 控件的扩展名，
                与 .exe、.dll 同属于PE文件。

                ActiveX控件.ActiveX控件是可重用的软件组件。使用它可以很快地在网络应用程序、桌面应用程序、以及开发工具中加入特殊的功能。ActiveX控件的使用者可以无需知道
                这些组件是如何开发的，便可以完成网页或应用程序的设计。

                对象链接和嵌入用户控件（OCX）是一种可以由在微软的Windows系统中运行的应用软件创建使用的特殊用途的程序。OCX提供操作滚动条移动和视窗恢复尺寸的功能。如果你有
                Windows系统，你会发现在你的Windows地址录里有大量OCX文件名后缀的文件。

                对象链接和嵌入（OLE）被设计来支持混合文档（包含多种资料类型，比如文本、绘画图像、声音、动画）。Windows桌面就是一个混合文档的范例，微软使用OLE来建立的。
                OLE和组件对象模型（COM继OLE后的更常用的概念）支持“即插即用”程序的发展，“即插即用”程序在系统中可以用任何语言写入并可以由任何应用程序动态地使用。这些程序被认
                为是他们在其上运行的container的组件和应用程序。这种基于组件的近似应用程序的发展缩减了程序调试时间并改善了程序的性能和品质。Windows应用发展程序（比如
                powerbuilder和Microsoft Access）具有OCX的优势。
            --%>
            <div id="log">
            </div>
        </div>

    </div>

    <div class="layui-table" style="margin-top: 0px;">
        <div class="layui-u">
            <ul>
                <li><p>姓名:</p><input id="name"></li>
                <li><p>身份证号:</p><input id="identity"></li>
                <li>
                    <div class="layui-li" style="width: 25%; float: left; margin-right: 15px;"><h1 id="search">搜索</h1></div>
                    <div class="layui-li" style="width: 25%; float: left;"><h1 id="refresh">刷新</h1></div>
                </li>
            </ul>
        </div>

        <table class="table">
            <thead>
            <tr>
                <th width="10%">姓名</th>
                <th width="10%">身份证号</th>
                <th width="10%">考勤机工号</th>
                <th width="10%">人员照片</th>
                <th width="10%">识别特征</th>
                <th width="10%">操作</th>
            </tr>
            </thead>
            <tbody id="list">
            </tbody>
        </table>
        <div class="tcdPageCode" id="page"></div>
    </div>
</div>
</body>

<input hidden id="num" value="${num}">
<input hidden id="pageNum" value="1">
<input hidden id="pageSize" value="7">
<input hidden id="count" value="">

<input type="hidden" id="siteId" value="${siteId}">
<input type="hidden" id="gateId" value="${gateId}">
<input type="hidden" id="port" value="${port}">
<input type="hidden" id="sn" value="${sn}">

<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script src="<%=basePath%>js/jquery.page.js"></script>
<script type="text/javascript" src="<%=basePath%>js/laydate.js"></script>
<script>

    var allEmployee;    //存放所有员工的数组
    var deleteList;     //删除队列
    var deleteNum=0;    //删除计数器
    var addList;        //添加队列
    var addNum=0;       //添加计数器
    var updateList;     //更新队列
    var updateNum=0;    //更新计数器
    var pagerInit=0;

        $(function () {
            getByPager();
            Init();
            allEmployee=new Array();

            function addEmployee(num,name,face){
                var employee={"num":num,"name":name,"gateId":$("#gateId").val(),"face":face}
                allEmployee.push(employee);
            }



            try {
                var sdkobj = document.getElementById("sdkobj");
                sdkobj.attachEvent("OnEventCConnect",
                    function (strDevSn, lOpCode, lUserData, lExtendParam, strIP, lPort) {
                        $("#log").append("<p>设备"+strDevSn+"已连接</p>");
                    });



                //获取人脸信息
                sdkobj.attachEvent("OnEventCGetSingleUserFeature", function (
                    strDevSn, lOpCode, lUserData, lExtendParam, lUserID, lUserType,lFeatureLen, strBase64Feature) {
//                    console.log()
//                $("#user_feature").val("");
//                $("#user_feature").val(strBase64Feature);
                });




                <%--//考勤信息的返回--%>
                <%--sdkobj.attachEvent("OnEventCTrapAccess", function (  strDevSn,  lOpCode,  lUserData,  lExtendParam,  lUserID,  strCardNo,  strRecTime,  lRecType,  lScore,  lStatus,  lPhotoType,  lPhotoLen,  lReason,  strBase64PhotoData) {--%>
<%--//                alert("当前考勤的人员编号："+lUserID+"，考勤时间："+strRecTime+"出入类型:"+lRecType);--%>

                    <%--$.ajax({--%>
                        <%--type:"post",--%>
                        <%--url:"<%=basePath%>face/upload",--%>
                        <%--dataType : "json",--%>
                        <%--data : {--%>
                            <%--"gateId":$("#gateId").val(),--%>
                            <%--"num":lUserID,--%>
                            <%--"time":strRecTime--%>
                        <%--},--%>
                        <%--error : function(XMLHttpRequest, textStatus, errorThrown) {--%>
                            <%--alert(XMLHttpRequest.status);--%>
                            <%--alert(XMLHttpRequest.readyState);--%>
                            <%--alert(textStatus);--%>
                        <%--},--%>
                        <%--success : function(resultObj) {--%>
<%--//                        //console.log(resultObj.msg);--%>
                        <%--}--%>
                    <%--});--%>
                <%--});--%>



                //获取所有用户
                sdkobj.attachEvent("OnEventCGetAllUsersInfo", function (
                    strDevSn, lOpCode, lUserData, lExtendParam,lCurrentItem, lTotalItems, lUserType, lUserID, strCardNo, strUserName, lVerifyMode,lRegStatus, lPhotoLen, strBase64PhotoData) {

                    if(lTotalItems!=0){
                        addEmployee(lUserID,strUserName,strBase64PhotoData);
                        $("#log").append("<p>正在读取设备中人员("+lCurrentItem+"/"+lTotalItems+")</p>");
                        if(lCurrentItem==lTotalItems){
                            sync();
                        }
                    }else{
                        sync();
                    }


                });




                //删除用户事件
                sdkobj.attachEvent("OnEventCDelSingleUser", function ( strDevSn,  lOpCode,  lUserData,  lExtendParam,  lUserID) {
                    if (lOpCode == 0) {
//                    ////console.log("删除成功！");
                        if(deleteList!=null&&deleteList.length>0){
                            deleteNum++;
                            deleteUserList(deleteList,deleteNum);
                        }
                    }
                    else {
                        ////console.log("删除失败!");
                    }
                });

                //添加用户事件
                sdkobj.attachEvent("OnEventCAddUserInfo", function (  strDevSn,  lOpCode,  lUserData,  lExtendParam,  lUserID) {
                    if (lOpCode == 0) {
                        //console.log("添加成功！"+addNum);
                        if(addList!=null&&addList.length>0){
                            addNum++;
                            addUserList(addList,addNum);
                        }
                    }
                    else {
                        //console.log("添加失败!");
                    }
                });
                //更新用户事件
                sdkobj.attachEvent("OnEventCModifyUserInfo", function (  strDevSn,  lOpCode,  lUserData,  lExtendParam,  lUserID) {
                    if (lOpCode == 0) {
                        //console.log("添加成功！"+addNum);
                        if(updateList!=null&&updateList.length>0){
                            updateNum++;
                            updateUserList(updateList,updateNum);
                        }
                    }
                    else {
                        //console.log("添加失败!");
                    }
                });
                //更新用户比对特征事件
                sdkobj.attachEvent("OnEventCModifyUserFeature", function (  strDevSn,  lOpCode,  lUserData,  lExtendParam,  lUserID) {
                    if (lOpCode == 0) {
                        if(updateList!=null&&updateList.length>0){
                            updateNum++;
                            updateUserFeature(updateList,updateNum);
                        }
                    }
                    else {
                    }
                });

                //录入人员监听器
                sdkobj.attachEvent("OnEventCEnrollUser", function (strDevSn, lOpCode, lUserData, lExtendParam, lUserID, strCardNo,
                                                                   lFeatureLen, strBase64FeatureData, lPhotoType, lPhotoLen, strBase64PhotoData) {
                    $.ajax({
                        type : "post",
                        url : "<%=basePath%>face/addFaceFeature",
                        dataType : "json",
                        data : {
                            "gateId":$("#gateId").val(),
                            "num":lUserID,
                            "face":strBase64PhotoData,
                            "feature":strBase64FeatureData
                        },
                        error : function(XMLHttpRequest, textStatus, errorThrown){
                            console.log(XMLHttpRequest.status);
                            console.log(XMLHttpRequest.readyState);
                            console.log(textStatus);
                        },
                        success : function(resultObj) {
                          alert(resultObj.msg);
                        }
                    });
                });

            }
            catch (exc) {
//            alert("IE 切换到兼容模式！");
                console.log(exc);
            }
        })

        function getByPager() {
            var name=$("#name").val();
            var identity=$("#identity").val();
            var num=$("#num").val();
            if (num=""){
                num=null;
            }
            var pageNum=$("#pageNum").val();
            var pageSize=$("#pageSize").val();
            //alert(pageSize)
            $.post("<%=basePath%>face/getByPager",{"name":name,"identity":identity,"gateId":$("#gateId").val(),"pageNum":pageNum,"pageSize":pageSize}, function (data) {
                var count=data.count;
                var list=data.list;
                $("#count").val(count);
                //alert($("#count").val())
                if($("#pageNum").val()=="1"){

                }
                var html="";

                for (var i=0;i<list.length;i++){
                    html+="<tr><td>"+list[i].employee.name+"</td>";
                    html+="<td>"+list[i].employee.identity+"</td>";
                    html+="<td>"+list[i].num+"</td>";
                    if (list[i].employee.face!=null&&list[i].employee.face!='0AAA'){
                        html+="<td>已上传</td>"
                    }else {
                        html+="<td>未上传</td>"
                    }
                    if (list[i].employee.feature!=null){
                        html+="<td>已上传</td>"
                    }else {
                        html+="<td>未上传</td>"
                    }
                    html+='<td><div class="layui-pa uploadBtn" id="'+list[i].num+'" >拍摄</div></td>';
                    html+="</tr>";
                }
                $("#list").html(html);
                $(".uploadBtn").click(function(){
                    EnrollUser($(this).attr("id"));
                })
                var pageCount=0;
                if(count%7==0){
                    pageCount=count/7;
                }else{
                    pageCount=count/7+1;
                }
                if(pagerInit==0){
                    $("#page").createPage({
                        pageCount:pageCount,
                        current:$("#pageNum").val(),
                        backFn:function(p){
                            $("#pageNum").val(p)
                            getByPager();
                        }
                    });
                    pagerInit++;
                }

            })
        }

        $("#search").click(function () {
            getByPager();
        })
         $("#refresh").click(function () {
             $("#name").val("");
             $("#identity").val("");
             $("#pageNum").val(1);
            getByPager();
        })

        //获取总页数
        function getTotalPages(count,pageSize) {
            var temp = count%pageSize;
            var pages = 0;
            if(temp==0){
                pages = count/pageSize;
            }else {
                pages=count/pageSize+1;
            }
            return pages;
        }

        var strSn = "";
        function Init() {
            var sdkobj = document.getElementById("sdkobj");
            var result = sdkobj.C_ServerInit($("#port").val(),123);
            if (result == "0") {
                $("#log").append("<p>系统初始化成功！</p><p>设备等待连接...</p>")
                return;
            }else{
                $("#log").append("<p>系统初始化失败！</p>")
            }
        }
        function finish() {
            var sdkobj = document.getElementById("sdkobj");
            var result = sdkobj.C_ServerUnit ();
            if (result == "0") {
                $("#log").append("<p>系统退出！</p>")
                return;
            }else{
                $("#log").append("<p>系统退出！</p>")
            }
        }
        function clearLog(){
            $("#log").html("");
        }

        function sync(){
            var request={"siteId":$("#siteId").val(),"gateId":$("#gateId").val(),"jointList":allEmployee};
            $("#log").append("<p>正在上传人员数据与系统比对...</p>");
            $.ajax({
                type : "post",
                url : "<%=basePath%>face/sync",
                dataType : "json",
                contentType : 'application/json;charset=UTF-8',
                data : JSON.stringify(request),
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    console.log(XMLHttpRequest.status);
                    console.log(XMLHttpRequest.readyState);
                    console.log(textStatus);
                    allEmployee=new Array();
                },
                success : function(resultObj) {
                    var sdkobj = document.getElementById("sdkobj");

                    var response=resultObj.data;
                    //console.log(response);
                    deleteList=response.deleteList;
                    addList=response.addList;
                    updateList=response.updateList;
                    $("#log").append("<p>数据对比完成...</p>");
                    $("#log").append("<p>设备上需要删除人员共"+deleteList.length+"名</p>");
                    $("#log").append("<p>设备上需要新增人员共"+addList.length+"名</p>");
                    $("#log").append("<p>设备上需要更新人员共"+updateList.length+"名</p>");
                    $("#log").append("<p>处理开始</p>");
                    deleteUserList(deleteList,deleteNum);

                }
            });
        }

        function GetDeviceList() {
            var sdkobj = document.getElementById("sdkobj");
            var devList = sdkobj.C_GetDevicesList();
            if (devList == "") {
                alert("无设备!");
                return;
            }

            alert("设备列表:" + devList);


        }
        function testConnect() {
            var sdkobj = document.getElementById("sdkobj");
            var flag = sdkobj.C_IsDeviceConnected("${sn}");
            if (flag) {
                alert("设备已连接");
                return;
            }else{
                alert("设备未连接");
                return;
            }

        }

        function connect() {
            var sdkobj = document.getElementById("sdkobj");

            strSn = sdkobj.C_Connect("${ip}", 30001, "admin", "88888888");   //开始连接设备
            if (strSn == "") {
                alert("连接失败!");
            } else {
                alert("链接成功! Sn=" + strSn);
                $("#log").append("<p>设备"+strSn+"已连接</p>");
            }
        }
        function Disconnect() {
            var sdkobj = document.getElementById("sdkobj");
            strSn = sdkobj.C_DisConnect($("#sn").val());   //开始连接设备
            if (strSn == "") {
                alert("断开连接失败!");
            } else {
                alert("断开链接成功! Sn=" + strSn);
            }

        }





        function loadAllUser() {
            var sdkobj = document.getElementById("sdkobj");
            allEmployee=new Array();
            var rlt = sdkobj.C_GetAllUsersInfo($("#sn").val());
            if (rlt != 0) {
                alert("返回值:" + rlt);
            }else{
                $("#log").append("<p>开始读取设备内人员数据...</p>")
            }
        }



        //批量删除指定列表用户
        function deleteUserList(list,index){
            if(index<list.length){
                $("#log").append("<p>正在删除与系统不符的人员数据("+(index+1)+"/"+list.length+")</p>");
                document.getElementById("log").scrollIntoView();
                var sdkobj = document.getElementById("sdkobj");
                var rlt = sdkobj.C_DelSingleUser( $("#sn").val() , list[index].num, 0);
            }else{
                //最后一次操作完成后，index=length，此时重置列表和计数器
                if(list.length!=0){
                    $("#log").append("<p>已完成人员的删除操作</p>");
                    document.getElementById("log").scrollIntoView();
                }
                deleteList=null;
                deleteNum=0;
                if(addList!=null){//如果添加队列此时不为空，则进行添加
                    addUserList(addList,addNum);
                }
            }
        }
        //批量添加指定列表用户
        function addUserList(list,index){
            if(index<list.length){
                $("#log").append("<p>正在新增系统中存在的人员数据("+(index+1)+"/"+list.length+")</p>");
                document.getElementById("log").scrollIntoView();
                var sdkobj = document.getElementById("sdkobj");
                var rlt="";
                if(list[index].employee.face==null){
                    rlt = sdkobj.C_AddUser($("#sn").val() , 0, list[index].num, "",  list[index].employee.name, 1, 0,0, 0);
//                    console.log(list[index].employee.name+":"+list[index].employee.face)
                }else{
                    rlt = sdkobj.C_AddUser($("#sn").val() , 0, list[index].num, "",  list[index].employee.name, 1, 1,0, list[index].employee.face);
//                    console.log(list[index].employee.name+":"+list[index].employee.face);
                }

            }else{
                //最后一次操作完成后，index=length,此时充值列表和计数器
                if(list.length!=0){
                    $("#log").append("<p>已完成人员信息的添加操作</p>");
                    document.getElementById("log").scrollIntoView();
                }
                addList=null;
                addNum=0;
                if(updateList!=null){//如果添加队列此时不为空，则进行添加
                    updateUserList(updateList,updateNum);
                }
            }
        }
        //批量更新指定列表用户
        function updateUserList(list,index){

            if(index<list.length){
                $("#log").append("<p>正在更新系统中存在的人员数据("+(index+1)+"/"+list.length+")</p>");
                document.getElementById("log").scrollIntoView();
                var sdkobj = document.getElementById("sdkobj");
                var rlt="";
                if(list[index].employee.face==null){
                    rlt = sdkobj.C_ModifyUser($("#sn").val() , 0, list[index].num, "",  list[index].employee.name, 1, 0,0, 0);
                }else{
                    rlt = sdkobj.C_ModifyUser($("#sn").val() , 0, list[index].num, "",  list[index].employee.name, 1, 1,0, list[index].employee.face);
                }

            }else{
                if(list.length!=0){
                    $("#log").append("<p>已完成人员信息的更新操作</p>");
                    document.getElementById("log").scrollIntoView();
                }
                updateNum=0;
                if(updateList!=null){//如果添加队列此时不为空，则进行添加
                    updateUserFeature(updateList,updateNum);
                }
            }
        }

        function EnrollUser(id) {

            var sdkobj = document.getElementById("sdkobj");
            var rlt = sdkobj.C_EnrollUser($("#sn").val(), id,0);
            if (rlt != 0) {
                alert("返回值:" + rlt);
            }
        }

        function updateUserFeature(list,index){

            if(index<list.length){
                $("#log").append("<p>正在更新系统人员比对特征("+(index+1)+"/"+list.length+")</p>");
                document.getElementById("log").scrollIntoView();
                var sdkobj = document.getElementById("sdkobj");
                var rlt="";
//                console.log("人员:"+list[index].employee.name+",比对特征:"+list[index].employee.feature);
                rlt = sdkobj.C_ModifyUserFeature( $("#sn").val() , list[index].num,  0,  list[index].employee.feature+"");
            }else{
                if(list.length!=0){
                    $("#log").append("<p>已完成人员比对特征的更新操作</p>");
                    document.getElementById("log").scrollIntoView();
                }
                updateList=null;
                updateNum=0;
                $("#log").append("<p>同步操作完成</p>");
                alert("同步操作完成");
                getByPager();
            }
        }

        function getError(){
            var sdkobj = document.getElementById("sdkobj");
            var rlt=sdkobj.C_SdkGetLastError($("#sn").val());
            console.log(rlt);
        }

</script>
</html>