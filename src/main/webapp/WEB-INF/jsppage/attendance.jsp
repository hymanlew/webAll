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
    <title>人员考勤管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="<%=basePath%>css/font-awesome.min.css" media="all">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/pagination.css" media="screen">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>jedate/skin/jedate.css">
    <link rel="stylesheet" href="<%=basePath%>css/time.css">
    <link rel="stylesheet" href="<%=basePath%>css/index1.2.css" media="all">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>jedate/jquery.jedate.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/zzsc.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/date.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/menu.js"></script>
</head>
<style>
    .rightbody-head1{background: #fff; border-bottom:solid 1px #eeeeee;}
    .rightbody-head1 p{float: left;line-height: 46px; margin: 0 5px;}
    .rightbody-head1 h1{ float: left; font-size: 16px; line-height: 46px; font-weight: bold; margin: 0 40px 0 0 ;}
    .piclist li{ width: 80px;}
    .piclist a{ width: 80px;}
    .box{ width: 100%;}
    .time-small li{ width: 80px;}
    .time-small{ width: 100%; padding-left:6px; overflow: hidden;}
</style>
<body style="overflow-x: hidden;">

<!-- 头部通用开始 -->
<div class="index-head">
    <p style="width:355px;">吴中区劳务实名监管平台<img src="<%=basePath%>images/logo.png"></p>
    <div class="index-display">
        <em class="fa fa-power-off"></em>
    </div>
    <div class="index-header" id="indexheader">
        <div class="index-display">
            <p>hi,${user.theName}</p>
            <span class="fa fa-chevron-down"></span>
        </div>
        <div class="header-child" id="animate">
            <a href="javascript:void(0)" onclick="personal()"><span class="fa fa-user-circle"></span>个人信息</a>
            <a href="javascript:void(0)" onclick="setting()"><span class="fa fa-cog"></span>基本设置</a>
        </div>
    </div>
    <c:import url="../index_href.jsp"></c:import>
</div>
<!-- 头部通用结束 -->
<!-- 左侧栏通用开始 -->
<div class="index-left" id="index-left">
    <div class="toggle-btn" onclick="leftToggle()">
        <span class="fa fa-reorder fa-rotate-90"></span>
    </div>
    <div class="index-left-scroll">
        <ul class="navs" id="navs">
        </ul>
    </div>
</div>
<!-- 左侧栏通用开始 -->
<!-- 右侧头部通用开始 -->
<div class="right-top">
    <p style="width: 50%;">人员考勤信息</p>
    <a class="fanhui" id="fanhui" style="float: right; top: 4px; right: 220px;">返回</a>
    <a href="javascript:history.back(-1)" class="fanhui" id="back" style="float: right; top: 4px; right: 220px;">返回</a>
</div>
<!-- 右侧头部通用结束 -->
<!-- 右侧body开始 -->
<div class="index-right">
    <div class="rightbody">
        <div class="rightbody-head1" id="siteInfo">
            <p >企业名称：</p><h1>${site.construction.name}</h1>
            <p>项目名称：</p><h1>${site.name}</h1>
        </div>
        <ul class="right-detail">
            <li>
                <div class="detail-f1">

                    <%--<div class="detail-style-1" id="constructionName_area">
                        <p>企业名称：</p>
                        <input type="" name="" id="constructionName">
                    </div>--%>
                    <%--<div class="detail-style-1" id="caetgory_area">
                        <p>项目类别：</p>
                        <select type="" name="" id="category">
                        </select>
                    </div>--%>
                    <%--<div class="detail-style-1" id="build_area">
                        <p>建设单位：</p>
                        <input type="" name="" id="build">
                    </div>--%>

                    <div class="detail-style-6">
                        <p>姓名：</p>
                        <input type="text" id="aname" style=" background: none; ">
                    </div>
                    <div class="detail-style-6">
                        <p>证件号：</p>
                        <input type="text" id="aidentity" style="width: 170px; background: none; ">

                    </div>
                    <div class="detail-style-6">
                        <p>考勤时间：</p>
                        <input type="text" id="inpstart" readonly>
                        <h1>至</h1>
                        <input type="text" id="inpend" readonly>

                    </div>

                    <div class="detail-style-1">
                        <select id="typeList">
                            <option value="">选择工种</option>
                        </select>
                    </div>
                    <div class="detail-style-1" id="build_area">
                        <select id="group">
                            <option value="">选择分组</option>
                        </select>
                    </div>
                    <%-- <div class="detail-style-1">
                         <p>施工单位：</p>
                         <input type="" name="">
                     </div>--%>
                    <a href="javascript:;" class="detail-style-3"id="export" style="margin-right: 85px;"><i class="fa fa-download"></i>导出</a>
                    <a href="javascript:;" class="detail-style-4" id="refresh"><i class="fa fa-refresh" ></i>刷新</a>
                    <button class="detail-style-5" id="search">搜索</button>
                    <a class="detail-style-5" id="grouping" href="<%=basePath%>page/groupManagerV12?siteId=${siteID}">分组</a>

                </div>
                <table class="table">
                    <thead>
                    <tr>
                        <th style="width: 5%;">序号</th>
                        <th style="width: 5%;"> 姓名</th>
                        <th style="width: 5%;">头像</th>
                        <th style="width: 10%;">工种</th>
                        <th style="width: 5%;">分组</th>
                        <th style="width: 5%;">状态</th>
                        <th>
                            <p id="table_title">2016年10月打卡记录</p>
                            <div class="box">
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
                                <div class="og_prev"></div>
                                <div class="og_next"></div>
                            </div>
                        </th>
                    </tr>
                    </thead>
                    <tbody id="attendance_table">

                    </tbody>
                </table>
                <div class="M-box" id="attendancePager" style="bottom:0; z-index:0; position:relative; margin-top:40px;left: 110%"></div>
            </li>

        </ul>
    </div>
    <div class="rightbody1">

        <ul class="rightbody-head1">
            <li><span class="span-on1">工人信息</span></li>
            <li><span>参建项目</span></li>
            <li><span>考勤信息</span></li>
            <li><span>工资信息</span></li>
            <li><span>信用评价</span></li>
        </ul>
        <ul class="right-detail1">
            <li>
                <div class="attendance-f1">
                    <p><span class="fa fa-paste"></span>基本信息</p>
                    <div class="attendance-f2">
                        <p>照片：</p>
                        <span><img id="portrait" src="<%=basePath%>images/moren.jpg"></span>
                    </div>
                    <div class="attendance-f2">
                        <p>姓名：</p>
                        <h1 id="name"></h1>
                    </div>
                    <div class="attendance-f2">
                        <p>性别：</p>
                        <h1 id="sex">男</h1>
                    </div>
                    <div class="attendance-f2">
                        <p>出生日期：</p>
                        <h1 id="birthday"></h1>
                    </div>
                    <div class="attendance-f2">
                        <p>证件号码：</p>
                        <h1 id="identity"></h1>
                    </div>
                    <div class="attendance-f2">
                        <p>户籍地址：</p>
                        <h1 id="home"></h1>
                    </div>
                    <div class="attendance-f2">
                        <p>政治面貌：</p>
                        <h1 id="party">群众</h1>
                    </div>
                    <div class="attendance-f2">
                        <p>名族：</p>
                        <h1 id="nation">汉族</h1>
                    </div>
                    <div class="attendance-f2">
                        <p>所属项目工地：</p>
                        <h1 id="siteName">苏州地铁四号线建设项目</h1>
                    </div>
                    <div class="attendance-f2">
                        <p>所属劳务公司：</p>
                        <h1 id="companyName">苏州地铁四号线建设项目</h1>
                    </div>
                    <div class="attendance-f2">
                        <p>工种：</p>
                        <h1 id="workType">苏州地铁四号线建设项目</h1>
                        <button class="detail-style-5" id="showWorkType" style="float: none; background:#4975c9; margin: 0 30px; border-radius: 6px;">修改工种</button>
                    </div>
                    <div class="attendance-f2">
                        <p>技能证书：</p>
                        <h1 id="certificate"></h1>
                    </div>
                </div>
                <div class="attendance-f3" id="cardList"  style="height: 564px;">
                    <div class="attendance-f1-1">
                        <p><span class="fa fa-credit-card"></span>工资卡①信息</p>
                        <div class="attendance-f2">
                            <p>开户行：</p>
                            <h1>苏州银行</h1>
                        </div>
                        <div class="attendance-f2">
                            <p>银行卡号：</p>
                            <h1>62276654335567778</h1>
                        </div>
                        <div class="attendance-f2">
                            <p>添加日期：</p>
                            <h1>2019-09-09</h1>
                        </div>
                    </div>
                    <div class="attendance-f1-1">
                        <p><span class="fa fa-credit-card"></span>工资卡②信息</p>
                        <div class="attendance-f2">
                            <p>开户行：</p>
                            <h1>苏州银行</h1>
                        </div>
                        <div class="attendance-f2">
                            <p>银行卡号：</p>
                            <h1>62276654335567778</h1>
                        </div>
                        <div class="attendance-f2">
                            <p>添加日期：</p>
                            <h1>2019-09-09</h1>
                        </div>
                    </div>
                </div>
                <div class="attendance-f1" style="clear: both;margin-top: 10px;min-height: 200px;">
                    <p><span class="fa fa-edit"></span>编辑</p>
                    <div class="detail-style-1" style="margin-top: 20px;">
                        <p style="margin-right: 10px;">状态：</p>
                        <select style="border-radius: 5px;width:100px;" id="quit">
                            <option value="0">在岗</option>
                            <option value="1">离职</option>
                        </select>
                    </div>
                    <div class="judge-box" id="quitBox">
                        <div class="judge">
                            <p><span class="fa fa-exclamation-circle"></span>是否确定把人员设置为离职？</p>
                            <div class="btnbox">
                                <a href="javascript:noQuit()">No</a>
                                <a href="javascript:chooseLeaving()">Yes</a>
                            </div>
                        </div>
                        <span class="angle"></span>
                    </div>
                    <div class="detail-style-1" style="margin-top: 20px;margin-left: 300px;display: none" id="leavingBox">
                        <p style="margin-right: 10px;">离职日期：</p>
                        <input type="text" id="leaving" readonly>

                        <a href="javascript:quitSite()" class="sure" id="sure">确定</a>
                    </div>
                </div>
            </li>
            <li>
                <div class="detail-f1">

                    <div class="detail-style-1">
                        <p>项目名称：</p>
                        <input type="" name="" id="siteName_record">
                    </div>
                    <div class="detail-style-1">
                        <p>建设单位：</p>
                        <input type="" name="" id="build_record">
                    </div>
                    <div class="detail-style-1">
                        <p>施工单位：</p>
                        <input type="" name="" id="constructionName_record">
                    </div>
                    <a href="javascript:;" class="detail-style-3" id="export_record"><i class="fa fa-download"></i>导出</a>
                    <a href="javascript:;" class="detail-style-4" id="refresh_record"><i class="fa fa-refresh"></i>刷新</a>
                    <button class="detail-style-5" id="search_record">搜索</button>

                </div>
                <table class="table">
                    <thead>
                    <tr>
                        <th>项目名称</th>
                        <th>建设单位</th>
                        <th>施工单位</th>
                        <th>合同备案编号</th>
                        <th>工程地址</th>
                    </tr>
                    </thead>
                    <tbody id="recordList">

                    </tbody>
                </table>
                <div class="M-box" style="bottom:0; z-index:0; position:relative; margin-top:40px;left: 110%"></div>
            </li>
            <li>
                <div class="detail-f1">

                    <div class="detail-style-6">
                        <p>考勤时间：</p>
                        <input type="text" id="inpstart1" readonly>
                        <h1>至</h1>
                        <input type="text" id="inpend1" readonly>
                    </div>
                    <div class="detail-style-1">
                        <p>考勤项目：</p>
                        <select type="" name="" id="siteList_attendance">

                        </select>
                    </div>
                    <a href="javascript:;" class="detail-style-3" id="export_attendance"><i class="fa fa-download"></i>导出</a>
                    <a href="javascript:;" class="detail-style-4" id="refresh_attendance"><i class="fa fa-refresh"></i>刷新</a>
                    <button class="detail-style-5" id="search_attendance">搜索</button>

                </div>
                <table class="table1">
                    <thead>
                    <tr>
                        <th colspan="14" id="attendance_title">2017年5月考勤记录</th>
                    </tr>
                    </thead>
                    <tbody id="attendanceDetail">

                    </tbody>
                </table>
                <div class="M-box" style="bottom:0; z-index:0; position:relative; margin-top:40px;left: 110%"></div>
            </li>
            <li>
                <div class="detail-f1">

                    <div class="detail-style-7">
                        <p>发薪日期：</p>
                        <input type="" name="" id="customymd" type="text" placeholder="请选择"  readonly>
                    </div>
                    <div class="detail-style-1">
                        <p>考勤项目：</p>
                        <select type="" name="" id="siteList_salary">

                        </select>
                    </div>
                    <div class="detail-style-1">
                        <p>开户行：</p>
                        <select type="" name="" id="bankList_salary">

                        </select>
                    </div>
                    <a href="javascript:;" class="detail-style-3" id="export_salary"><i class="fa fa-download"></i>导出</a>
                    <a href="javascript:;" class="detail-style-4" id="refresh_salary"><i class="fa fa-refresh"></i>刷新</a>
                    <button class="detail-style-5" id="search_salary">搜索</button>

                </div>
                <table class="table2">
                    <thead>
                    <tr>
                        <th>发薪日期</th>
                        <th>参建项目</th>
                        <th>实发金额</th>
                        <th>开户行</th>
                        <th>卡号</th>
                    </tr>
                    </thead>
                    <tbody id="salaryRecordList">
                    <tr>
                        <td><span>2017年5月31日</span></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><span>2017年5月31日</span></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><span>2017年5月31日</span></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
                <div class="M-box" style="bottom:0; z-index:0; position:relative; margin-top:40px;left: 110%"></div>
            </li>
            <li>
                <div class="attendance-f4">
                    <p><span class="fa fa-paste"></span>总体评价</p>
                    <div class="attendance-f4-box">
                        <div class="attendance-f4-1">
                            <img src="<%=basePath%>images/blackstar.png" alt="" id="star0">
                            <img src="<%=basePath%>images/blackstar.png" alt="" id="star1">
                            <img src="<%=basePath%>images/blackstar.png" alt="" id="star2">
                            <img src="<%=basePath%>images/blackstar.png" alt="" id="star3">
                            <img src="<%=basePath%>images/blackstar.png" alt="" id="star4">
                        </div>
                        <p id="finalDiscuss">总体评价：===</p>
                    </div>
                </div>
                <div class="attendance-f5">
                    <p><span class="fa fa-paste"></span>总体评价</p>
                    <a href="javascript:showDiscussDialog();" id="addDiscuss">新增评价</a>
                    <div id="discussList">

                    </div>

                </div>
            </li>
        </ul>
    </div>
</div>
<!-- 右侧body结束 -->

<!-- 弹窗 -->
<div id="myModal" class="modal">
    <!-- 弹窗内容 -->
    <div class="modal-content">
        <div class="modal-header" style="background: #4975c9;position: fixed; width: 586px;">
            <span class="fa fa-times-circle close" style="color: #fff; "></span>
            <h1 style="color: #fff; border:none;">工地详情</h1>
        </div>
        <%--<div class="modal-header" style=" background:#4975c9;">--%>
        <%--<span class="fa fa-times-circle close" style="color:#fff;"></span>--%>
        <%--<h1 style="color:#fff; border:none;font-size: 20px;">工地详情</h1>--%>
        <%--</div>--%>
        <div class="modal-body" style="margin-top: 50px;">
            <form>
                <div class="modal-style1">
                    <p>工地名称</p>
                    <input disabled="disabled" id="name_detail">
                </div>
                <div class="modal-style1">
                    <p>工地地址</p>
                    <input  disabled="disabled" id="address_detail">
                </div>
                <div class="modal-style1">
                    <p>合同编号</p>
                    <input  disabled="disabled" id="contractNum_detail">
                </div>
                <div class="modal-style1">
                    <p>项目类别</p>
                    <input  disabled="disabled" id="siteCategory_detail" >
                </div>
                <div class="modal-style1">
                    <p>工程造价</p>
                    <input  disabled="disabled" id="money_detail" >
                </div>
                <div class="modal-style1">
                    <p>计划开工日期</p>
                    <input  disabled="disabled" id="contract_start_detail" >
                </div>
                <div class="modal-style1">
                    <p>计划竣工日期</p>
                    <input  disabled="disabled" id="contract_end_detail" >
                </div>
                <div class="modal-style1">
                    <p>联系人</p>
                    <input  disabled="disabled" id="linkman_detail">
                </div>
                <div class="modal-style1">
                    <p>联系电话</p>
                    <input  disabled="disabled" id="linkman_phone_detail">
                </div>
            </form>
        </div>
    </div>
</div>

<div id="changeWorkTypeModel" class="modala">
    <div class="modal-contenta">
        <div class="modal-header" style="height:40px;background:#4975c9;">
            <span class="fa fa-times-circle close" onclick="closeTypeWindow()" style="color: #fff;"></span>
            <h1 style="border: none;color: #fff; ">更改员工工种</h1>
        </div>
        <div class="modal-bodya">
            <form>
                <div class="modal-stylea">
                    <p>工种名称：</p>
                    <select id="workTypeList" style="width: 65%; height: 38px;   border: 1px solid #e8e8e8;">
                        <option></option>
                    </select>
                </div>
                <div class="modal-stylea">

                    <span onclick="changeWorkType()" style="margin-left:18%;background: #4975c9; text-align:center; margin-right: 6px; height: 30px; line-height: 30px; color: #fff;">确认</span>
                    <span onclick="closeTypeWindow()" style="background: #888; text-align:center; margin-right: 6px; height: 30px; line-height: 30px; color: #fff;">取消</span>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="time-k">
    <div class="time">
        <div class="time-t" style="background: #4975c9;">
            <img src="<%=basePath%>images/close.png" width="14" style="margin-top:13px; "/>
        </div>
        <div class="time-c" style="box-shadow:none;">
            <div class="time-c1">
                <p id="time_click">2016年10月1日打卡记录</p>
                <span>打卡地点</span>
            </div>
            <ul id="time_all">
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(".time-small li").click(function() {
        $(".time-k").show();
    });
    $(".time-t img").click(function() {
        $(this).parents(".time-k").hide();
    })

</script>
<!-- 评价弹窗 -->
<div id="myModala" class="modala">
    <!-- 弹窗内容 -->
    <div class="modal-contenta" style="width:450px;">
        <div class="modal-header">
            <span id="close" class="fa fa-times-circle closea"></span>
            <h1>评价</h1>
        </div>
        <div class="modal-bodya">
            <div class="evaluate">
                <div class="evaluate-box">
                    <div class="evaluate-f1">
                        <img src="<%=basePath%>images/blackstar.png" alt="">
                        <img src="<%=basePath%>images/blackstar.png" alt="">
                        <img src="<%=basePath%>images/blackstar.png" alt="">
                        <img src="<%=basePath%>images/blackstar.png" alt="">
                        <img src="<%=basePath%>images/blackstar.png" alt="">
                    </div>
                    <p>总体评价：</p>
                    <input type="text" name="" value="">
                </div>
                <textarea id="context" placeholder="请输入对此劳务人员的评价"></textarea>
            </div>
        </div>
        <button class="savebtn" id="disscussSave">保存</button>
    </div>
</div>

<script type="text/javascript">
    // 获取弹窗
    var modal = document.getElementById('myModal');

    // 打开弹窗的按钮对象
    var btn = document.getElementById("myBtn");

    // 获取 <span> 元素，用于关闭弹窗 that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // 点击按钮打开弹窗
    function detailbox(id) {
        modal.style.display = "block";
        $.ajax({
            type:"post",
            async:false,
            data:{
                'siteId':id
            },
            url:'<%=basePath%>site/getSite',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(site){
                $("#name_detail").val(site.name);
                $("#address_detail").val(site.address);
                $("#contractNum_detail").val(site.contractNum);
                if(site.siteCategory!=null){
                    $("#siteCategory_detail").val(site.siteCategory.name);
                }
                if(site.money!=null){
                    $("#money_detail").val(site.money+"万元");
                }
                $("#contract_start_detail").val(getFormatDateByLong(site.contractStartTime,"yyyy-MM-dd"));
                $("#contract_end_detail").val(getFormatDateByLong(site.contractEndTime,"yyyy-MM-dd"));
                $("#linkman_detail").val(site.linkman);
                $("#linkman_phone_detail").val(site.linkmanPhone);
            }
        });
    }

    // 点击 <span> (x), 关闭弹窗
    span.onclick = function() {
        modal.style.display = "none";
    }

    // 在用户点击其他地方时，关闭弹窗
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    $(window).on("load",function(){
        $(".rightbody-head1 > li").click(function(){
            $(this).children("span").addClass("span-on1").parent("li").siblings("li").children("span").removeClass("span-on1");
            var text=$(this).children("span").text();
            if(text=="参建项目"){
                getSiteRecord();
            }
            if(text=="考勤信息"){
                $("#inpstart1").val(getFirstDayOfMonth(getFormatDate(new Date(),"yyyy-MM-dd")));//默认当月第一天
                $("#inpend1").val(getFormatDate(new Date(),"yyyy-MM-dd"));//默认当月第一天
                getSiteList_attendance();
                getEmployeeAttendance();
            }
            if(text=="工资信息"){
                $("#customymd").val("");
                getSiteList_salary();
                getBankList();
            }
            if(text=="信用评价"){
                getDiscussList();
                getAvgScore();
            }

            var index = $(this).index();
            $(".right-detail1 > li").eq(index).show().siblings(".right-detail1 > li").hide();

        })
    })
</script>
<!-- 分页插件开始 -->
<script src="<%=basePath%>js/jquery.pagination.js"></script>
<script type="text/javascript">
    /*$('.M-box').pagination({
        pageCount:40,
        jump:true,
        coping:true,
        homePage:'首页',
        endPage:'末页',
        prevContent:'<',
        nextContent:'>'
    });*/

</script>
<!-- 分页插件结束 -->
<script type="text/javascript" src="<%=basePath%>js/index1.2.js"></script>
<!-- 时间js开始 -->
<script>
    var start = {
        format: 'YYYY-MM-DD',
        minDate: '2014-01-01', //设定最小日期为当前日期
        isinitVal:true,
        festival:false,
        ishmsVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        choosefun: function(elem, val, date){
            end.minDate = date; //开始日选好后，重置结束日的最小日期
//            endDates();
        }
    };
    var end = {
        format: 'YYYY-MM-DD',
        minDate: '2014-01-01', //设定最小日期为当前日期
        festival:false,
        maxDate: $.nowDate({DD:0}), //最大日期
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
    $("#inpstart").val("");
    $('#inpend').jeDate(end);
    $("#leaving").jeDate(start);

    var start1 = {
        format: 'YYYY-MM-DD',
        minDate: '2014-06-16', //设定最小日期为当前日期
        isinitVal:true,
        festival:false,
        ishmsVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        choosefun: function(elem, val, date){
            end1.minDate = date; //开始日选好后，重置结束日的最小日期
//            endDates1();
        }
    };
    var end1= {
        format: 'YYYY-MM-DD',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        festival:false,
        maxDate: '2099-06-16', //最大日期
        choosefun: function(elem, val, date){
            start1.maxDate = date; //将结束日的初始值设定为开始日的最大日期
        }
    };
    //这里是日期联动的关键
    function endDates1() {
        //将结束日期的事件改成 false 即可
        end1.trigger = false;
        $("#inpend1").jeDate(end1);
    }
    $('#inpstart1').jeDate(start1);
    $("#inpstart1").val("");

    $('#inpend1').jeDate(end1);

    $('#customymd').jeDate({
        isinitVal:true,
        format:"YYYY-MM"
    });
</script>
<!-- 时间js结束 -->
<!-- 点击人员姓名切换div开始 -->
<script type="text/javascript">
    function dianji(id){
        //保存工人id
        $("#employeeId").val(id);
        $("#back").hide();
        $("#fanhui").show();
        showInfo(id);
        $(".rightbody1").show().siblings(".rightbody").hide();
    }

    function showInfo(id) {
        //获取工人的基本信息
        $.ajax({
            type:"post",
            async:false,
            data:{
                'employeeId':id
            },
            url:'<%=basePath%>employee/getEmployee',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(employee){
                $("#portrait").attr("src","<%=basePath%>"+employee.photo);
                $("#name").html(employee.name);
                if(employee.sex==0){
                    $("#sex").html("女");
                }else{
                    $("#sex").html("男");
                }
                $("#birthday").html(getFormatDateByLong(employee.birthday,"yyyy-MM-dd"));
                $("#identity").html(employee.identity);
                if (employee.area!=null){
                    $("#home").html(employee.area.district);
                }
                if(employee.party==1){
                    $("#party").html("党员");
                }else{
                    $("#party").html("群众");
                }
                if(employee.site!=null){
                    $("#siteName").html(employee.site.name);
                }else{
                    $("#siteName").html("");
                }
                if (employee.company!=null){
                    $("#companyName").html(employee.company.name);
                }
                $("#workType").html(employee.typeEntity.name);
            }
        });
        //获取工人的银行卡
        $.ajax({
            type:"post",
            async:false,
            data:{
                'employeeId':id
            },
            url:'<%=basePath%>bankCard/getByPager',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(map){
                var list=map.list;
                var html="";
                if(list!=null&&list.length>0){
                    for(var i=0;i<list.length;i++){
                        var card=list[i];
                        html+='<div class="attendance-f1-1">';
                        html+='<p><span class="fa fa-credit-card"></span>工资卡'+(i+1)+'信息</p>';
                        html+='<div class="attendance-f2">';
                        html+='<p>开户行：</p>';
                        html+='<h1>'+card.bank.name+'</h1>';
                        html+='</div>';
                        html+='<div class="attendance-f2">';
                        html+='<p>银行卡号：</p>';
                        html+='<h1>'+card.num+'</h1>';
                        html+='</div>';
                        html+='<div class="attendance-f2">';
                        html+='<p>添加日期：</p>';
                        html+='<h1>'+getFormatDateByLong(card.time,"yyyy-MM-dd")+'</h1>';
                        html+='</div>';
                        html+='</div>';
                    }
                    $("#cardList").html(html);
                }else{
                    $("#cardList").html("暂无该人员的银行卡信息");
                }
            }
        });
        //获得技能证书
        $.ajax({
            type:"post",
            async:false,
            data:{
                'employeeId':id
            },
            url:'<%=basePath%>certificate/getByPager',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(map){
                var list=map.list;
                if(list!=null&&list.length>0){
                    $("#certificate").html(list[0].certificate.name);
                }
            }
        });
        testQuit();//判断是否离职
    }

    <!-- 更改员工工种 -->
    $("#showWorkType").click(function(){
        getTypelist();
        $("#changeWorkTypeModel").show();
    })

    function changeWorkType() {
        if($("#workTypeList").val()==""){
            alert("请选择工种");
            return;
        }

        var type = $("#workTypeList").find("option:selected").text();
        $.ajax({
            type:"post",
            async:false,
            data:{
                "employeeId": $("#employeeId").val(),
                "type": $("#workTypeList").find("option:selected").val()
            },
            url:"<%=basePath%>employee/update",
            dataType: "json",
            error: function (jqXHR) {
                console.log(jqXHR.msg);
            },
            success: function (data) {
                $("#workType").html(type);
                if(data.code == 200){
                    closeTypeWindow();
                }
            }
        });
    }

    function getTypelist() {
        $.ajax({
            type:"post",
            async:false,
            data:{ },
            url:'<%=basePath%>type/findAll_allow',
            dataType:"json",
            error:function (jqXHR, textStatus, errorThrow) {
                console.log(jqXHR.status);
                console.log(jqXHR.readyState);
                console.log(textStatus);
            },
            success:function (map) {
                var data = map.list;
                var html ="";
                if(data!=null && data.length>0){
                    html+="<option value=''>==请选择==</option>";
                    for(var i=0;i<data.length;i++){
                        var type = data[i];
                        html += "<option value='"+type.pro+"'>"+type.name+"</option>";
                    }
                }
                $("#workTypeList").html(html);
            }
        });
    }

    function closeTypeWindow() {
        $("#changeWorkTypeModel").hide();
    }
</script>
<!-- 点击人员姓名切换div结束 -->
<!-- 点击返回项目工地开始 -->
<script type="text/javascript">

    $("#fanhui").click(function(){
        var tab=$(".rightbody-head1 > li")[0];
        $(tab).click();//默认返回第一个，点员工信息进来后还是第一个。
        $(".rightbody").show().siblings(".rightbody1").hide();
        $("#back").show();
        $("#fanhui").hide();
    })

    $(function () {
        if(($("#siteID").val()!=""&&$("#siteID").val()!=undefined)){
            $("#hideA").hide();
            $("#groupMenu").show();
        }else{
            $("#grouping").hide();
            $("#showWorkType").hide();
        }
        $("#fanhui").hide();
        getByPager();
        differentShow();
        getAllGroup();
        getAllType();
    });

    function getAllGroup(){
        var siteId=${site.id};
        //alert(siteId);
        $.ajax({
            type:"post",
            async:false,
            data:{
                "siteId":siteId
            },
            url:'<%=basePath%>group/select',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(map){
                var list=map.list;
                var html='<option value="">选择分组(没有则不选)</option>';
                for(var i=0;i<list.length;i++){
                    var group=list[i];
                    //alert(group.id);
                    html+='<option  value="'+group.id+'">'+group.name+'</option>';
                }
                $("#group").html(html);
            }
        });
        $("#group").change(function () {
            $("#groupchoose").val($("#group").val());
        });
    }

    //显示工种列表
    function getAllType(){
        $.ajax({
            type:"post",
            async:false,
            data:{

            },
            url:'<%=basePath%>type/findAll_allow',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(map){
                var list=map.list;
                var html='<option value="">选择工种</option>';
                for(var i=0;i<list.length;i++){
                    var type=list[i];
                    html+='<option  value="'+type.pro+'">'+type.name+'</option>';
                }
                $("#typeList").html(html);
            }
        });
        $("#typeList").change(function () {
            $("#choose").val($("#typeList").val());
        });
    }

    $('#search').on('click', function() {
        $("#pageNumber").val(1);
        getByPager();
    });

    $('#search_record').on('click', function() {
        $("#pageNumber").val(1);
        getSiteRecord();
    });
    $("#search_attendance").on('click',function () {
        $("#pageNumber").val(1);
        getEmployeeAttendance();
    })
    $("#search_salary").on('click',function () {
        $("#pageNumber").val(1);
        getSalaryDetail();
    })
    $('#refresh').on('click', function() {
        $("#constructionName").val("");
        $("#address").val("");
        $("#address").val("");
        $("#category").val("");
        $("#build").val("");
        $("#inpstart").val("");
        $("#inpend").val("");
        getByPager();
    });
    $('#refresh_record').on('click', function() {
        $("#siteName_record").val("");
        $("#build_record").val("");
        $("#constructionName_record").val("");
        getSiteRecord();
    });
    $('#refresh_attendance').on('click', function() {
        $("#inpstart1").val("");
        $("#inpend1").val("");
        $("#siteList_attendance").prop('selectedIndex', 0);
        getEmployeeAttendance();
    });
    $('#refresh_salary').on('click', function() {
        $("#bankList_salary").val("");
        $("#siteList_salary").prop('selectedIndex', 0);
        getSalaryDetail();
    });

    $("#export").on("click",function(){
        var siteId=${site.id};
        var constructionId = $("#constructionID").val();
        var companyId = $("#companyID").val();
        var name = $("#aname").val();
        var beginTime=$("#inpstart").val();
        var endDate = $("#inpend").val();
        var identity = $("#aidentity").val();
        var type = $("#choose").val();  //工种号
        var groupId=$("#groupchoose").val();    //分组
        window.location.href="<%=basePath%>attendance/exportAttendanceExcel?beginTime="+beginTime+"&siteId="+siteId+"&constructionId="+constructionId+"&companyId="+companyId+"&name="+name+"&endDate="+endDate+"&identity="+identity+"&type="+type+"&groupId="+groupId
    });
    $("#export_record").on("click",function(){
        var siteName=$("#siteName_record").val();
        var constructionName=$("#constructionName_record").val();
        var build=$("#build_record").val();
        var employeeId=$("#employeeId").val();
        window.location.href="<%=basePath%>siteRecord/exportExcel?siteName="+siteName+"&constructionName="+constructionName+"&build="+build+"&employeeId="+employeeId;
    });

    $("#export_attendance").on("click",function(){
        var employeeId=$("#employeeId").val();
        var siteId=$("#siteList_attendance").val();
        var beginTime=$("#inpstart1").val();
        var endTime=$("#inpend1").val();
        window.location.href="<%=basePath%>attendance/exportEmployeeAttendence?siteId="+siteId+"&beginTime="+beginTime+"&endTime="+endTime+"&employeeId="+employeeId;
    });
    $("#export_salary").on("click",function(){
        var employeeId=$("#employeeId").val();
        var siteId=$("#siteList_salary").val();
        var bankId=$("#bankList_salary").val();
        var beginTime=$("#customymd").val()+"-01";
        var endTime=getNextMonth(beginTime);
        window.location.href="<%=basePath%>salary/exportExcel?siteId="+siteId+"&beginTime="+beginTime+"&endTime="+endTime+"&employeeId="+employeeId+"&bankId="+bankId;
    });

    //查询
    function getByPager(){
        var start = $("#inpstart").val();
        var end = $("#inpend").val();

        if (start == "" && end != "") {
            var date = new Date(end).format();
            var d = new Date(date);
            d.setDate(d.getDate() - 14);
            var month = d.getMonth() + 1;
            var day = d.getDate();
            start = d.getFullYear() + '-' + month + '-' + day;
            var array = getAllDay(start, date);
            getPage(array);
        } else if (start == "" && end == "") {
            var date = new Date();
            date.setDate(date.getDate() - 14);
            var array = getAllDay(date.format(),new Date().format());
            getPage(array);
        } else if (start != "" && end == "") {
            var date = new Date(start).format();
            var d = new Date(date);
            d.setDate(d.getDate() + 6);
            var month = d.getMonth() + 1;
            var day = d.getDate();
            end = d.getFullYear() + '-' + month + '-' + day;
            var array = getAllDay(date, end);
            getPage(array);
        } else if (start != "" && end != "") {
            var array = getAllDay(new Date(start).format(),
                new Date(end).format());
            getPage(array);
        }

        function getPage(array){//根据日期list修改表格标题并查询
            if(array.length%days!=0){
                var n = days-array.length%days;
                for(var i=0;i<n;i++){
                    var date = new Date(array[array.length-1]);
                    date.setDate(date.getDate() + 1);
                    var month = date.getMonth() + 1;
                    var day = date.getDate();
                    array.push(date.getFullYear() + '-' + month + '-' + day);
                }
            }
            arr = array;
            var code = "";
            var head = array[0]+"  至  "+array[array.length-1]+" 的打卡记录";
            // 					var head = new Date(array[0]).getFullYear()+"年"+(new Date(array[0]).getMonth()+1)+"月"+new Date(array[0]).getDate()+"日至"+new Date(array[array.length-1]).getFullYear()+"年"+(new Date(array[array.length-1]).getMonth()+1)+"月"+new Date(array[array.length-1]).getDate()+"日打卡记录";
            $("#table_title").html(head);
            for(var i=0;i<array.length;i++){
                var ar = array[i].split("-");

                code += "<li><a target='_blank'>"+ar[2]+"日</a></li>";

            }
            console.log(code)
            $("#dayList").html(code);
            rollToStart();
            findAttendance(array[0],$("#pageNumber").val(),$("#pageSize").val());
        }

    }




    //查找考勤信息
    function findAttendance(beginDate,pageNumber,pageSize){
        $("#begin_time").val(beginDate);
        var name = $("#aname").val();
        var identity = $("#aidentity").val();
        var type = $("#choose").val();  //工种号
        var areaId = $("#areaID").val();
        var siteId = ${site.id};
        var constructionId = $("#constructionID").val();
        var companyId = $("#companyID").val();
        var constructionName=$("#constructionName").val();
        var address=$("#address").val();
        var build=$("#build").val();
        var category=$("#category").val();
        // var groupId=$("#group").val();
        var groupId=$("#groupchoose").val();    //分组
        var areaIds=$("#s_areaID").val();
        $.ajax({
            type:"post",
            async:false,
            data:{
                'name' : name,
                'identity':identity,
                'type' : type,
                'constructionId':constructionId,
                'beginTime':beginDate,
                'areaId' : areaId,
                'areaIds':areaIds,
                'siteId' : siteId,
                'companyId' : companyId,
                'constructionName':constructionName,
                "groupId":groupId,
                'address':address,
                'build':build,
                'category':category,
                'pageNumber' : pageNumber,
                'pageSize' : pageSize
            },
            url:'<%=basePath%>attendance/findAttendancePager',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(map){
                data = map.list;
                var count = map.count;
                $("#count").val(count);
                var pageCount=0;
                var pageSize=$("#pageSize").val();
                pageCount=count/pageSize;
                var code = "";

                //如果分页数是1页以上，才显示分页插件
                $('#attendancePager').html("");
                if(pageCount>1) {
                    $('#attendancePager').show();
                    var current = $("#pageNumber").val();
                    $('#attendancePager').pagination({
                        pageCount: pageCount,
                        jump: true,
                        current: current,
                        coping: true,
                        count: 1,
                        homePage: '首页',
                        endPage: '末页',
                        prevContent: '<',
                        nextContent: '>',
                        callback: function (page) {
                            var current = page.getCurrent();
                            $("#pageNumber").val(current);
                            getByPager();
                        }
                    });
                }else{
                    $('#attendancePager').hide();
                }
//                attendancePager.pageCount=pageCount;
//                attendancePager.current=current;
//                attendancePager.init();
                //console.log(attendancePager);
                for(var i=0;i<data.length;i++){
                    code+="<tr>";
                    code+='<td>'+((pageNumber-1)*pageSize+i+1)+'</td>';
                    code+='<td><a href="javascript:;" onclick="dianji('+data[i].employee.id+')" class="hrefbox">'+data[i].employee.name+'</a></td>';
                    code+='<td><span class="photobox"><img src="<%=basePath%>'+data[i].employee.photo+'"></span></td>';
                    if(data[i].employee.typeEntity!=null){
                        code += "<td >"+data[i].employee.typeEntity.name+"</td>";
                    }else{
                        code += "<td >"+data[i].employee.type+"1111</td>";
                    }
                    if(data[i].employee.group!=null){
                        code += "<td >"+data[i].employee.group.name+"</td>";
                    }else{
                        code += "<td >无分组</td>";
                    }
                    if(data[i].employee.leaving!=0){
                        code += "<td >已离场</td>";
                    }else{
                        code += "<td >在场</td>";
                    }

                    code+='<td colspan="4">';
                    code+='<ul class="time-small">';
                    var list = data[i].list;
                    for(var j=list.length-1;j>0;j--){
                        var attendanceId = null;
                        if(list[j][0]!=null){
                            attendanceId = list[j][0].id;
                        }else if(list[j][1]!=null){
                            attendanceId = list[j][1].id;
                        }
                        var dateStr=getAfterDay(beginDate,list.length-1-j,"yyyy年MM月dd日");

                        code +="<li onclick='showAll("+attendanceId+",\""+dateStr+"\")'>";
                        if(list[j][0]==null){
                            code +="<span></span>";
                        }else{
                            if((new Date(list[j][0].dateTime).getMinutes()+"").length<2){
                                code +="<span>"+new Date(list[j][0].dateTime).getHours()+":0"+new Date(list[j][0].dateTime).getMinutes()+"</span>";
                            }else{
                                code +="<span>"+new Date(list[j][0].dateTime).getHours()+":"+new Date(list[j][0].dateTime).getMinutes()+"</span>";
                            }
                        }
                        if(list[j][1]==null){
                            code +="<span></span>";
                        }else{
                            if((new Date(list[j][1].dateTime).getMinutes()+"").length<2){
                                code +="<span>"+new Date(list[j][1].dateTime).getHours()+":0"+new Date(list[j][1].dateTime).getMinutes()+"</span>";
                            }else{
                                code +="<span>"+new Date(list[j][1].dateTime).getHours()+":"+new Date(list[j][1].dateTime).getMinutes()+"</span>";
                            }

                        }
                        code +="</li>";
                    }
                    code+='</ul>';
                    code+='</td>';

                    code+="</tr>";

                }
                $("#attendance_table").html(code);
//                findEmployeeCount(count);
            }
        });
    }
    function showAll(attendanceId,dateStr){

        $.post("<%=basePath%>attendance/findAttendanceByEmployeeAndDay",
            {
                'attendanceId':attendanceId
            },
            function(data) {
                console.log(data);
                var code = "";
                var index = Math.ceil((data.length)/2);
                for(var i=0;i<index;i++){
                    code += "<li>";
                    code += "<div class='time-c2-a'>";
                    if(i==(index-1)){
                        for(var j=2*i;j<data.length;j++){
                            $("#time_click").html(dateStr+"日打卡记录");
                            if((new Date(data[j].time).getMinutes()+"").length<2){
                                code += "<span>"+new Date(data[j].time).getHours()+":0"+new Date(data[j].time).getMinutes()+"</span>";
                            }else{
                                code += "<span>"+new Date(data[j].time).getHours()+":"+new Date(data[j].time).getMinutes()+"</span>";
                            }
                        }
                    }else{
                        for(var j=2*i;j<2*(i+1);j++){
                            $("#time_click").html(dateStr+"日打卡记录");
                            if((new Date(data[j].time).getMinutes()+"").length<2){
                                code += "<span>"+new Date(data[j].time).getHours()+":0"+new Date(data[j].time).getMinutes()+"</span>";
                            }else{
                                code += "<span>"+new Date(data[j].time).getHours()+":"+new Date(data[j].time).getMinutes()+"</span>";
                            }
                        }
                    }
                    code += "</div>";
                    code += "<div class='time-c2-b'>";
                    if(i==(index-1)){
                        for(var j=2*i;j<data.length;j++){
                            code += "<span>"+data[j].address+"</span>";
                        }
                    }else{
                        for(var j=2*i;j<2*(i+1);j++){
                            code += "<span>"+data[j].address+"</span>";
                        }
                    }
                    code += "</div>";
                    code += "</li>";
                }
                $("#time_click").html(dateStr+"日打卡记录");
                $("#time_all").html(code);
                $(".time-k").show();
            });

    }

    // 获取间隔天数
    Date.prototype.format = function() {
        var date = '';
        date += this.getFullYear() + '-';
        date += (this.getMonth() + 1) + "-";
        date += this.getDate();
        return (date);
    };
    //拿到两天之间的所有日期
    function getAllDay(begin, end) {
        var ab = begin.split("-");
        var ae = end.split("-");
        var db = new Date();
        db.setUTCFullYear(ab[0], ab[1] - 1, ab[2]);
        var de = new Date();
        de.setUTCFullYear(ae[0], ae[1] - 1, ae[2]);
        var unixDb = db.getTime();
        var unixDe = de.getTime();
        var array = [];
        for ( var k = unixDb; k <= unixDe;) {
            array.push((new Date(parseInt(k))).format());
            k = k + 24 * 60 * 60 * 1000;
        }
        ;
        return array;
    }


    $('.og_next').click(function(){
        // var  numJ=1;
        i= Math.ceil(($('.mainlist li').length)/days);
        if($('.swaplist,.mainlist').is(':animated')){
            $('.swaplist,.mainlist').stop(true,true);
        }
        // console.log(numJ,i);
        // alert(i);
        if(numJ<i){
            // alert(numJ);
            // alert(1111);
            if($('.mainlist li').length>4){//多于4张图片
                ml = parseInt($('.mainlist').css('left'));//默认图片ul位置
                sl = parseInt($('.swaplist').css('left'));//交换图片ul位置
                if(ml<=0 && ml>w*-1){//默认图片显示时
                    $('.swaplist').css({left: '100%'});//交换图片放在显示区域右侧
                    $('.mainlist').animate({left: ml - 350 + 'px'},'slow');//默认图片滚动
                    if(ml==(w-350)*-1){//默认图片最后一屏时
                        $('.swaplist').animate({left: '0px'},'slow');//交换图片滚动
                    }
                }else{//交换图片显示时
                    $('.mainlist').css({left: '1000px'})//默认图片放在显示区域右
                    $('.swaplist').animate({left: sl - 350 + 'px'},'slow');//交换图片滚动
                    if(sl==(w-350)*-1){//交换图片最后一屏时
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
        findAttendance(str,$("#pageNumber").val(),$("#pageSize").val());
        // alert()
    })
    // var s=i;
    $('.og_prev').click(function(){

        i= Math.ceil(($('.mainlist li').length)/days);
        if($('.swaplist,.mainlist').is(':animated')){
            $('.swaplist,.mainlist').stop(true,true);
        }
        if(numJ>1){
            if($('.mainlist li').length>4){
                ml = parseInt($('.mainlist').css('left'));
                sl = parseInt($('.swaplist').css('left'));
                if(ml<=0 && ml>w*-1){
                    $('.swaplist').css({left: w * -1 + 'px'});
                    $('.mainlist').animate({left: ml + 350 + 'px'},'slow');
                    if(ml==0){
                        $('.swaplist').animate({left: (w - 350) * -1 + 'px'},'slow');
                    }
                }else{
                    $('.mainlist').css({left: (w - 350) * -1 + 'px'});
                    $('.swaplist').animate({left: sl + 350 + 'px'},'slow');
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

    //项目-建设-施工表格信息
    function getSiteRecord(){
        var siteName=$("#siteName_record").val();
        var constructionName=$("#constructionName_record").val();
        var build=$("#build_record").val();
        var employeeId=$("#employeeId").val();
        $.ajax({
            type:"post",
            async:false,
            data:{
                'siteName':siteName,
                'constructionName':constructionName,
                'build':build,
                'employeeId':employeeId
            },
            url:'<%=basePath%>siteRecord/getByPager',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(map){
                var list=map.list;
                var html="";
                if(list!=null&&list.length>0){
                    for(var i=0;i<list.length;i++){
                        var record=list[i];
                        html+='<tr>';
                        html+='<td><a href="javascript:;" onclick="detailbox('+record.site.id+')" class="hrefbox">'+record.site.name+'</a></td>';
                        if (record.site.build!=null){
                            html+='<td>'+record.site.build+'</td>';
                        }else {
                            html+='<td></td>';
                        }
                        if (record.site.construction.name!=null){
                            html+='<td>'+record.site.construction.name+'</td>';
                        }else {
                            html+='<td></td>';
                        }
                        if (record.site.contractNum!=null){
                            html+='<td>'+record.site.contractNum+'</td>';
                        }else {
                            html+='<td></td>';
                        }
                        if (record.site.address!=null){
                            html+='<td>'+record.site.address+'</td>';
                        }else {
                            html+='<td></td>';
                        }
                        html+='</tr>';
                    }
                }
                $("#recordList").html(html);
            }
        });
    }

    //工地的考勤记录
    function getSiteList_attendance() {
        var employeeId=$("#employeeId").val();
        $.ajax({
            type:"post",
            async:false,
            data:{
                'employeeId':employeeId
            },
            url:'<%=basePath%>attendance/getSites',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(list){
                if(list!=null&&list.length>0){
                    var html="";
                    var siteId=$("#siteID").val();
                    var constructionId=$("#constructionID").val();
                    for(var i=0;i<list.length;i++){
                        var site=list[i];

                        if(siteId=="" || siteId==undefined){
                            if(constructionId=="" || constructionId==undefined){
                                html+="<option value='"+site.id+"'>"+site.name+"</option>";
                            }else if(constructionId!=""&&site.constructionId==constructionId){//如果是建筑公司登录，则只能看自己下属工地的考勤记录
                                html+="<option value='"+site.id+"'>"+site.name+"</option>";
                            }
                        }else if(siteId!=""&&siteId==site.id){//如果是工地登录，则只显示自己
                            html+="<option value='"+site.id+"'>"+site.name+"</option>";
                        }
                    }
                    $("#siteList_attendance").html(html);
                    $("#siteList_salary").html(html);
                }

            }
        });
    }
    //获取工人个人的考勤信息
    function getEmployeeAttendance() {
        var employeeId=$("#employeeId").val();
        var siteId=$("#siteList_attendance").val();
        var beginTime=$("#inpstart1").val();
        var endTime=$("#inpend1").val();

        if(beginTime!=""&&endTime!=""){
            $("#attendance_title").html(beginTime+"&nbsp;&nbsp;至&nbsp;&nbsp;"+endTime+"&nbsp;&nbsp;考勤记录");
        }
        if(beginTime!=""&&endTime==""){
            $("#attendance_title").html("从&nbsp;&nbsp;"+beginTime+"&nbsp;&nbsp;起考勤记录");
        }

        if(beginTime==""&&endTime!=""){
            $("#attendance_title").html(getFirstDayOfMonth(getFormatDate(new Date(),"yyyy-MM-dd"))+"&nbsp;&nbsp;至&nbsp;&nbsp;"+endTime+"&nbsp;&nbsp;考勤记录");
        }
        if(beginTime==""&&endTime==""){
            $("#attendance_title").html(getFirstDayOfMonth(getFormatDate(new Date(),"yyyy-MM-dd"))+"&nbsp;&nbsp;至今考勤记录");
        }
        if(siteId!=""&&siteId!=null){
            $.ajax({
                type:"post",
                async:false,
                data:{
                    'employeeId':employeeId,
                    "siteId":siteId,
                    "beginTime":beginTime,
                    "endTime":endTime
                },
                url:'<%=basePath%>attendance/getEmployeeAttendance',
                dataType : "json",
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    console.log(XMLHttpRequest.status+"1");
                    console.log(XMLHttpRequest.readyState);
                    console.log(textStatus);
                },
                success : function(list){
                    var html="";
                    if(list!=null&&list.length>0){
                        var len=0;//表格行数，一行14天
                        if(list.length%14==0){
                            len=list.length/14;
                        }else{
                            len=parseInt(list.length/14)+1;
                        }
                        for(var i=0;i<len;i++){
                            //每一行内的html拼接操作，一行实际包含2个<tr>
                            var dayHtml="<tr>";//日期
                            var timeHtml="<tr>"
                            var detail=null;
                            if(i!=len-1){
                                for(var j=14*i;j<14*(i+1);j++){
                                    detail=list[j];
                                    dayHtml+="<td>";
                                    dayHtml+="<h2>"+detail.date+"</h2>";
                                    dayHtml+="<h2>"+getWeekDay(detail.weekday)+"</h2>";
                                    dayHtml+="</td>";
                                    timeHtml+="<td>";
                                    if(detail.attendanceList[1]!=null){
                                        timeHtml+="<span>"+new Date(detail.attendanceList[1].time).getHours()+":"+new Date(detail.attendanceList[1].time).getMinutes()+"</span>";
                                    }else{
                                        timeHtml+="<span>&nbsp;</span>";
                                    }
                                    if(detail.attendanceList[0]!=null){
                                        timeHtml+="<span>"+new Date(detail.attendanceList[0].time).getHours()+":"+new Date(detail.attendanceList[0].time).getMinutes()+"</span>";
                                    }else{
                                        timeHtml+="<span>&nbsp;</span>";
                                    }
                                    timeHtml+="</td>";
                                }
                                dayHtml+="</tr>";
                                timeHtml+="</tr>"
                                html=html+dayHtml+timeHtml;
                            }else{
                                for(var j=14*i;j<list.length;j++){
                                    detail=list[j];
                                    //console.log(detail);
                                    dayHtml+="<td>";
                                    dayHtml+="<h2>"+detail.date+"</h2>";
                                    dayHtml+="<h2>"+getWeekDay(detail.weekday)+"</h2>";
                                    dayHtml+="</td>";
                                    timeHtml+="<td>";
                                    if(detail.attendanceList[1]!=null){
                                        timeHtml+="<span>"+new Date(detail.attendanceList[1].time).getHours()+":"+new Date(detail.attendanceList[1].time).getMinutes()+"</span>";
                                    }else{
                                        timeHtml+="<span>&nbsp;</span>";
                                    }
                                    if(detail.attendanceList[0]!=null){
                                        timeHtml+="<span>"+new Date(detail.attendanceList[0].time).getHours()+":"+new Date(detail.attendanceList[0].time).getMinutes()+"</span>";
                                    }else{
                                        timeHtml+="<span>&nbsp;</span>";
                                    }

                                    timeHtml+="</td>";
                                }
                                dayHtml+="</tr>";
                                timeHtml+="</tr>"
                                html=html+dayHtml+timeHtml;
                            }
                        }
                    }
                    $("#attendanceDetail").html(html);
                }
            });
        }
    }

    function getWeekDay(weekDay){
        if(weekDay==1)
            return "星期日";
        if(weekDay==2)
            return "星期一";
        if(weekDay==3)
            return "星期二";
        if(weekDay==4)
            return "星期三";
        if(weekDay==5)
            return "星期四";
        if(weekDay==6)
            return "星期五";
        if(weekDay==7)
            return "星期六";

    }

    function getSiteList_salary(){
        var employeeId=$("#employeeId").val();
        $.ajax({
            type:"post",
            async:false,
            data:{
                'employeeId':employeeId
            },
            url:'<%=basePath%>siteRecord/getByPager',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(map){
                var list=map.list;
                var html="";
                if(list!=null&&list.length>0){
                    var siteId=$("#siteID").val();
                    var constructionId=$("#constructionID").val();
                    for(var i=0;i<list.length;i++){
                        var site=list[i].site;
                        if(siteId=="" || siteId==undefined){
                            if(constructionId=="" || constructionId==undefined){
                                html+="<option value='"+site.id+"'>"+site.name+"</option>";
                            }else if(constructionId!=""&&site.constructionId==constructionId){//如果是建筑公司登录，则只能看自己下属工地的考勤记录
                                html+="<option value='"+site.id+"'>"+site.name+"</option>";
                            }
                        }else if(siteId!=""&&siteId==site.id){//如果是工地登录，则只显示自己
                            html+="<option value='"+site.id+"'>"+site.name+"</option>";
                        }
                    }

                }
                $("#siteList_salary").html(html);
                getSalaryDetail();
            }
        });
    }

    function getBankList() {
        $.ajax({
            type:"post",
            async:false,
            data:{

            },
            url:'<%=basePath%>bankCard/getBankList',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(list){
                var html="";
                if(list!=null&&list.length>0){
                    html+="<option value=''>==请选择==</option>";
                    for(var i=0;i<list.length;i++){
                        var bank=list[i];
                        html+="<option value='"+bank.id+"'>"+bank.name+"</option>"
                    }
                }
                $("#bankList_salary").html(html);
            }
        });
    }
    //获得详细的工资信息，默认显示当前年份内的所有工资信息
    function getSalaryDetail(){
        var employeeId=$("#employeeId").val();
        var siteId=$("#siteList_salary").val();
        var bankId=$("#bankList_salary").val();
        var date = new Date();
        var beginTime= new Date(date.getUTCFullYear(),0,1).format();
        var endTime = null;

        if($("#customymd").val()!=""){
            beginTime=$("#customymd").val()+"-01";
            endTime=getNextMonth(beginTime);
        }
        $.ajax({
            type:"post",
            async:false,
            data:{
                "employeeId":employeeId,
                "siteId":siteId,
                "bankId":bankId,
                "beginTime":beginTime,
                "endTime":endTime
            },
            url:'<%=basePath%>salary/findSalaryRecordPager',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(map){
                var list=map.list;
                var html="";
                if(list!=null&&list.length>0){
                    for(var i=0;i<list.length;i++){
                        var record=list[i];
                        html+="<tr>";
                        html+="<td><span>"+getFormatDateByLong(record.time,"yyyy年MM月dd日")+"</span></td>";
                        html+="<td>"+record.salaryRecordSite.site.name+"</td>";
                        html+="<td>"+record.money+"</td>";
                        html+="<td>"+record.bank+"</td>";
                        html+="<td>"+record.card+"</td>";
                        html+="</tr>";
                    }
                }
                $("#salaryRecordList").html(html);
            }
        });
    }

    function getDiscussList(){
        var employeeId=$("#employeeId").val();
        $.ajax({
            type:"post",
            async:false,
            data:{
                "employeeId":employeeId
            },
            url:'<%=basePath%>discuss/getDiscuss',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(map){
                var list=map.list;
                var html="";
                if(list!=null&&list.length>0){
                    for(var i=0;i<list.length;i++){
                        var discuss=list[i];
                        html+='<div class="attendance-f5-box">';
                        html+='<div class="attendance-f5-1">';
                        html+='<span><img src="<%=basePath%>'+discuss.user.photo+'"></span>';
                        html+='<p>'+discuss.user.theName+'</p>';
                        html+='<h1>'+getFormatDateByLong(discuss.time,"yyyy-MM-dd")+'</h1>';
                        html+='</div>';
                        html+='<textarea disabled>'+discuss.context+'</textarea>';
                        html+='</div>';
                    }
                }
                $("#discussList").html(html);
            }
        });
    }

    function getAvgScore(){
        var employeeId=$("#employeeId").val();
        $.ajax({
            type:"post",
            async:false,
            data:{
                "employeeId":employeeId
            },
            url:'<%=basePath%>discuss/getAvgScore',
            dataType : "json",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(s){
                var num = s/10;
                var star="<%=basePath%>images/blackstar.png";    //普通灰色星星图片的存储路径
                var starRed="<%=basePath%>images/redstar.png";     //红色星星图片存储路径
                for(var i=0;i<5;i++){
                    $("#star"+i).attr('src',star);  //设置鼠标当前的前面星星图片为打星颜色图
                }
                for(var k=0;k<num;k++){
                    $("#star"+k).attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图
                }
                if(num>=3){
                    $("#finalDiscuss").text("总体评价：良好");
                }else{
                    $("#finalDiscuss").text("总体评价：较差");
                }
            }
        });
    }

    function showDiscussDialog(){
        $("#myModala").show();
    }
    $(".closea").click(
        $("#myModala").hide()
    );
    var modala = document.getElementById('myModala');
    var spana = document.getElementsByClassName("closea")[0];


    // 点击 <span> (x), 关闭弹窗
    spana.onclick = function() {
        $("#myModala").hide();
    }

    // 在用户点击其他地方时，关闭弹窗
    window.onclick = function(event) {
        if (event.target == modala) {
            modala.style.display = "none";
        }
    }

    $("#disscussSave").click(function(){
        saveDiscuss();
    });

    function saveDiscuss(){
        var employeeId=$("#employeeId").val();
        var score=$("#score").val();
        var context=$("#context").val();
        $.ajax({
            type:"post",
            async:false,
            data:{
                "employeeId":employeeId,
                "score":score,
                "context":context
            },
            url:'<%=basePath%>discuss/publish',
            dataType : "text",
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status+"1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);
            },
            success : function(s){
                if(s=="success"){
                    alert("保存成功");
                    $("#myModala").hide();
                    getDiscussList();
                }
            }
        });
    }

    //不同显示
    function differentShow() {
        var siteId=$("#siteID").val();
        var constructionId=$("#constructionID").val();
        /*if(siteId!=""){
            $("#constructionName_area").hide();
            $("#caetgory_area").hide();
            $("#build_area").hide();
        }
        if(constructionId!=""){
            $("#constructionName_area").hide();
            $("#caetgory_area").hide();
            $("#build_area").hide();
        }*/
        //非住建局无法评论
        if(siteId!=""||constructionId!=""){
            $("#addDiscuss").hide();
        }
    }
</script>


<!-- 点击返回项目工地开始 -->


<!-- 个人信息弹窗 -->
<div id="modala" class="modala">
    <!-- 弹窗内容 -->
    <div class="modal-contenta">
        <div class="modal-header">
            <span class="fa fa-times-circle close" onclick="closeModal()"></span>

        </div>
        <div class="modal-bodya">
            <form>
                <div class="modal-stylea">
                    <p>用户名：</p>
                    <input type="" name="" value="${user.theName}" disabled>
                </div>
                <div class="modal-stylea">
                    <p>头像：</p>
                    <span><img src="<%=basePath%>${user.photo}"></span>
                </div>
            </form>
        </div>
        <!-- <div class="modal-footer">
            <button class="cancel" onclick="closeModal()">关闭</button>
            <button class="save">确定</button>
        </div> -->
    </div>
</div>
<script type="text/javascript">
    function personal(){
        $("#modala").show();
    }
    function closeModal(){
        $("#modala,#modalb").hide();
    }
    $("#modala").click(function(){
        $(this).hide();
    })
    $(".modal-contenta").click(function(e){
        e.stopPropagation();
    })
</script>
<!-- 基本设置弹窗 -->
<div id="modalb" class="modala">
    <!-- 弹窗内容 -->
    <div class="modal-contenta">
        <div class="modal-header">
            <span class="fa fa-times-circle close" onclick="closeModal()"></span>

        </div>
        <div class="modal-bodya">
            <form>
                <div class="modal-stylea">
                    <p>旧密码：</p>
                    <input type="password" name="" id="oldPwd">
                </div>
                <div class="modal-stylea">
                    <p>设置新密码：</p>
                    <input type="password" name="" id="newPwd">
                </div>
                <div class="modal-stylea">
                    <p>确认密码：</p>
                    <input type="password" name="" id="rptPwd">
                </div>
                <div class="modal-stylea">
                    <p>头像：</p>
                    <span><img src="<%=basePath%>${user.photo}"id="img"></span>
                    <a href="#">
                        重新上传
                        <input type="file" name="image" class="file" id="image">
                    </a>
                    <input type="hidden" id="save_doc">
                </div>
                <a href="javascript:resetPwd()" class="confirm">确认修改</a>
            </form>
        </div>
        <!-- <div class="modal-footer">
            <button class="cancel" onclick="closeModal()">关闭</button>
            <button class="save">确定</button>
        </div> -->
    </div>
</div>
<script type="text/javascript">
    function setting(){
        $("#modalb").show();
    }
    $("#modalb").click(function(){
        $(this).hide();
    })
    $(".modal-contenta").click(function(e){
        e.stopPropagation();
    })
    $("#image").change(function () {

        img_upload($(this),"image");
    });

    function img_upload(fileObj,sign){
        console.log(fileObj);
        console.log(fileObj[0].value);
        var allowExtention = ".jpg,.bmp,.gif,.png";
        var extention = fileObj[0].value.substring(fileObj[0].value.lastIndexOf(".") + 1).toLowerCase();
        if(allowExtention.indexOf(extention) > -1){
            $.ajaxFileUpload({
                url: '<%=basePath%>user/upload',
                type: 'post',
                secureuri: false,
                fileElementId: sign,
                dataType: 'json',
                success: function(data, status){
                    $("#save_doc").val(data);
                    $("#img").prop("src","<%=basePath%>"+data);
                    $("#portrait").prop("src","<%=basePath%>"+data);
                },
                error: function(data, status, e){
                }
            });
        }else{
            fileObj[0].value = ""; //清空选中文件
        }
    }
    function resetPwd() {
        var oldPwd=$("#oldPwd").val();
        var newPwd=$("#newPwd").val();
        var rptPwd=$("#rptPwd").val();
        var image = $("#save_doc").val();
        if((oldPwd.trim()!="")||(newPwd.trim()!="")||(rptPwd.trim()!="")){
            if(oldPwd.trim()==""){
                alert("旧密码为空");
                return;
            }
            if(newPwd.trim()==""){
                alert("新密码为空");
                return;
            }
            if(rptPwd.trim()==""){
                alert("重复密码为空");
                return;
            }
            if(newPwd!=rptPwd){
                alert("新密码与重复密码不一致");
                return;
            }
        }
        $.ajax({
            type:"post",
            async:false,
            data:{
                "oldpassword":oldPwd,
                "password":newPwd,
                "image":image
            },
            url:'<%=basePath%>user/updatePassword',
            dataType : "text",
            error : function(XMLHttpRequest, textStatus,
                             errorThrown) {
                console.log(XMLHttpRequest.status + "1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);

            },
            success : function(data) {
                if(data=="success"){
                    if((oldPwd.trim()!="")||(newPwd.trim()!="")||(rptPwd.trim()!="")){
                        alert("修改成功，请重新登录");
                        window.location.href="<%=basePath%>j_spring_security_logout";
                    }else{
                        alert("头像修改成功");
                    }

                }else{
                    alert("旧密码错误");
                    $("#oldPwd").val("");
                    $("#newPwd").val("");
                    $("#rptPwd").val("");
                }
            }
        });
    }


    function testQuit(){
        var employeeId=$("#employeeId").val();
        var siteId=$("#siteID").val();
        $.ajax({
            type:"post",
            async:false,
            data:{
                "employeeId":employeeId,
                "siteId":siteId
            },
            url:'<%=basePath%>employee/isQuit',
            dataType : "text",
            error : function(XMLHttpRequest, textStatus,
                             errorThrown) {
                console.log(XMLHttpRequest.status + "1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);

            },
            success : function(flag) {
                if(flag=="true"){//0没有离职，1离职,true 离职，false 未离职
                    $("#quit").val("1");
                    $("#quit").attr("disabled",true);
                    $("#quitBox").hide();
                    $("#leavingBox").hide();
                }else{
                    $("#quit").val("0");
                    if($("#siteID").val()==""||($("#siteID").val()==undefined)){
                        $("#quitBox").hide();
                        $("#leavingBox").hide();
                    }else{
                        $("#quitBox").show();
                    }
                }
            }
        });
    }

    function quitSite(){
        if(confirm("确定该员工离职?")){
            //点击确定后操作
            var leaving=$("#leaving").val();
            var employeeId=$("#employeeId").val();
            $.ajax({
                type:"post",
                async:false,
                data:{
                    "employeeId":employeeId,
                    "leaving":leaving
                },
                url:'<%=basePath%>employee/quitSite',
                dataType : "json",
                error : function(XMLHttpRequest, textStatus,
                                 errorThrown) {
                    console.log(XMLHttpRequest.status + "1");
                    console.log(XMLHttpRequest.readyState);
                    console.log(textStatus);

                },
                success : function(resultObj) {
                    alert(resultObj.msg);
                    $("#quitBox").hide();
                    showInfo($("#employeeId").val());
                }
            });
        }

    }
    function chooseLeaving(){
        $("#leavingBox").show();
    }

    function noQuit(){
        $("#quitBox").hide();
    }
    function getAfterDay(d,num){
        if(d==null||d=="")
            return;
        d = new Date(d);
        d = +d + 1000*60*60*24*num;
        d = new Date(d);
        //return d;
        //格式化
        return getFormatDate(d,"yyyy-MM-dd");
    }
</script>
<%--<script type="text/javascript" src="<%=basePath%>js/proTree.js" ></script>
<script type="text/javascript">
    $(function () {
        var areaId="${areaId}";
        $.ajax({
            type: "post",
            async: false,
            data: {"areaId":areaId},
            url: '<%=basePath%>area/findAreaByParent_allow',
            error: function (XMLHttpRequest, textStatus,
                             errorThrown) {
                console.log(XMLHttpRequest.status + "1");
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);

            },
            success: function (data) {
                var list = data.list;
                for (var i = 0; i < list.length; i++) {
                    var newArry = {
                        id: list[i].id,
                        name: list[i].district,
                        pid: list[i].parent
                    };
                    Arr.push(newArry);
                    if (list[i].areaList!=null){
                        var areaList=list[i].areaList;
                        for (var j=0;j<areaList.length;j++){
                            var newArry1 ={
                                id: areaList[j].id,
                                name: areaList[j].district,
                                pid: areaList[j].parent
                            };
                            Arr.push(newArry1);
                        }
                    }
                }
                /*alert(arr[2].id)
                 alert(arr[2].name)*/
                //console.log(Arr)
            }
        })
        //标题的图标是集成bootstrap 的图标  更改 请参考bootstrap的字体图标替换自己想要的图标
        $(".innerUl").ProTree({
            arr: Arr,
            index:2,
            simIcon: "fa fa-file-o",//单个标题字体图标 不传默认glyphicon-file
            mouIconOpen: "fa fa-folder-open-o",//含多个标题的打开字体图标  不传默认glyphicon-folder-open
            mouIconClose:"fa fa-folder-o",//含多个标题的关闭的字体图标  不传默认glyphicon-folder-close
            callback: function(id,name) {
                $("#region").html(name);
                $("#regionList").hide();
                $.ajax({
                    type: "post",
                    async: false,
                    data: {
                        "parent":id
                    },
                    url: '<%=basePath%>area/switch',
                    error: function (XMLHttpRequest, textStatus,
                                     errorThrown) {
                        console.log(XMLHttpRequest.status + "1");
                        console.log(XMLHttpRequest.readyState);
                        console.log(textStatus);

                    },
                    success: function (resultObj) {
                        alert(resultObj.msg);
                        console.log(resultObj.data);
                        window.location.reload();
                    }
                });
            }

        })
    })
    //后台传入的 标题列表
    var Arr = [{
        id: "${areaId}",
        name: "${region}",
        pid: 0
    }];

</script>--%>
<input type="hidden" id="pageNumber" value="1"/>
<input type="hidden" id="count" value="1"/>
<input type="hidden" id="pageSize" value="8"/>
<input type="hidden" id="begin_time" value="">
<input type="hidden" id="choose" value="">
<input type="hidden" id="score" value="">
<input type="hidden" id="groupchoose" value="">
<input type="hidden" id="sitechoose" value="">
<%--分页控制器是否被创建--%>
<input type="hidden" id="paged" value="false">

<input type="hidden" id="employeeId" value="${employeeId }">
<input type="hidden" id="companyID" value="${companyID }">
<input type="hidden" id="s_areaID" value="${areaIDs }">
<%--<input type="hidden" id="siteID" value="${siteID }">--%>
<input type="hidden" id="constructionID" value="${constructionID }">
<input type="hidden" id="constructionID1" value="${constructionID }">
<input type="hidden" id="streetId" value="${streetId }">
<input type="hidden" id="realSiteId" value="${realSiteId }">
</body>
</html>