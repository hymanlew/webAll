<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8">

    <%--
        Edge 模式告诉 IE 以最高级模式渲染文档，也就是任何 IE 版本都以当前版本所支持的最高级标准模式渲染，避免版本升级造成的影响。
        简单的说，就是什么版本IE 就用什么版本的标准模式渲染，或强制 IE 使用 Chrome Frame 渲染
    --%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <%--
        viewport，是用于在移动设备上进行网页的重构或开发，是设置了当前页面在移动端的显示格式。 content属性值 :

         width：            可视区域的宽度，值可为数字或 关键词device-width（即设备宽度）
         height：           同width
         intial-scale：     页面首次被显示是可视区域的缩放级别，取值1.0则页面按实际尺寸显示，无任何缩放
         maximum-scale=1.0, minimum-scale=1.0;  可视区域的缩放级别，
         maximum-scale：     用户可将页面放大的程序，1.0将禁止用户放大到实际尺寸之上。
         user-scalable：     是否可对页面进行缩放，no 禁止缩放
    --%>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/js/index1.2.js"></script>
    <script type="text/javascript" src="/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/js/layui/layui.js"></script>

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="../../js/md5.js"></script>

    <title>登录</title>
</head>
<body>
<h2>Hello World!</h2>
<h2>Hello man!</h2>
<h2>
    <a href="javascript:getTargetUrl()">导出 EXCEL</a>
    <a href="javascript:getTargetUrl1()">去测试页面</a>
    <a href="javascript:getTargetUrl2()">测试JSON</a>
</h2>

<div>
    <span><input type="text" id="file" width="100px;"><button type="button" onclick="getFile()">选择</button></span>
    <button id="submit" type="button" onclick="upload('hidd')">提交</button>

    <%-- 上传文件时，input标签内的 name 值必须与 controller 中的 value值相同，否则上传不成功 --%>
    <input type="file" name="hidd" id="hidd" onchange="putval()" style="display: none">
</div>

<div>
    <form method="post" enctype="multipart/form-data" action="/do/importAndShow">
        <input type="file" name="file" id="importshow" />
        <input type="submit" value="上传并显示到网页" />
    </form>
    <button id="submitshow" type="button" onclick="importshow('file')">提交：上传并显示到网页</button>
</div>

<h2>上传图片----</h2>
<div>
    <button id="imgTitle">上传图片----</button>
    <%-- multiple 属性规定输入字段可选择多个值 --%>
    <input type="file" name="manyImages" id="manyImages" onchange="uploadImg(this);" style="display: none;"
       accept="image/jpg,image/jpeg,image/png,image/bmp" multiple="multiple"/>
</div>

<div id="show">
</div>

<h2>登录测试</h2>
<div>
    <form action="/test/login" id="form" method="post">
        <div class="login-f2-1">
            <div class="login-input">
                <%--<img src="images/ico-login1.png" alt="" />--%>
                <input type="text" class="inputa" name="username" value="" style=""/>
            </div>
            <div class="login-input">
                <img src="images/ico-login2.jpg" alt="" />
                <input type="password" class="inputb" name="password" value=""/>
            </div>
            <input id="btnLogin" value="登录" type="button" onclick="">
        </div>
    </form>
    <input type="hidden" value="${param.login_error}" id="log">
    <div class="login-f2-2">
        <div class="login-f2-2a">
            <div class="m-label">
                <span><input type="checkbox" class="m-checkbox" name="_spring_security_remember_me"><label class="label-click"></label></span>
            </div>
            <p>记住密码</p>
        </div>
        <a href="#">忘记密码？</a>
    </div>
</div>
</body>
<script type="text/javascript">

    var form;
    layui.use(['form', 'layedit', 'laydate'], function() {
        form = layui.form(),
            layer = layui.layer,
            layedit = layui.layedit,
            laydate = layui.laydate;
    });
    // $(function () {
    //     $("#hidd").hide();
    // });
    function getTargetUrl() {
        var url= getBasePath()+"/do/exportExcel";
        location.href = url;
    }

    function getTargetUrl1() {
        var url= getBasePath()+"/test/test";
        location.href = url;
    }

    function getTargetUrl2() {
        var url= getBasePath()+"/do/takejson";
        location.href = url;
    }

    $("#file").on("click",getFile);
    function getFile(ev) {
        $("#hidd").trigger("click");

    }
    function putval() {
        $("#file").val($("#hidd").val());
    }

    // secureuri，不设置为安全传输
    // fileElementId，上传文件的input框的id、name属性名
    // 在 on事件中调用函数时，不能直接给值，否则 jsp会自动加载运行该函数。如：on("click",upload("hidd"));

    // function upload() {
    //     upload1("hidd");
    // }
    function upload(sign) {
        console.log(sign);

        var allType = ".xlsx,.xls";
        var last = $("#file").val().lastIndexOf(".");
        console.log("后缀 :"+last);

        var filetype = $("#file").val().substring(last+1).toLowerCase();
        console.log("filetype :"+filetype);

        if(allType.indexOf(filetype)>-1){
            console.log("index :"+allType.indexOf(filetype));
            $.ajaxFileUpload({
                type: "POST",
                url: getBasePath() + "/do/importExcel",
                secureuri: false,
                fileElementId: sign,
                dataType: 'json',
                success :function (msg) {
                    console.log(msg);
                    alert(msg);
                },
                error: function(data, status, e){
                    console.log(data);
                    alert(e);
                }
            });
        }else{
            alert("仅支持" + allType + "为后缀名的文件!");
        }
    }

    function importshow(sign) {
        console.log(sign);

        var allType = ".xlsx,.xls";
        var last = $("#importshow").val().lastIndexOf(".");
        console.log("后缀 :"+last);

        var filetype = $("#importshow").val().substring(last+1).toLowerCase();
        console.log("filetype :"+filetype);

        if(allType.indexOf(filetype)>-1){
            console.log("index :"+allType.indexOf(filetype));

            $.ajaxFileUpload({
                url: getBasePath() + "/do/importAndShow",
                secureuri: false,
                fileElementId: sign,
                dataType: 'json',
                success :function (msg) {

                    // 需要注意 fileupload插件返回的数据不能直接在原来的页面中进行修改,因为它是不解析 json 数据。并且函数是否
                    // 成功也要考虑到文件的大小。
                    msg = $.parseJSON(msg.replace(/<.*?>/ig,""));

                    // 并且只有先接收一个文件路径,然后再调用函数去修改页面.
                    var list = msg.list;
                    var html = "";

                    if(list!=null && list.length>0){
                        for(var i=0;i<list.length;i++){

                            for(var k=0;k<i.length;k++){
                                html += "<p>"+k+"</p>";

                                // 在 ajaxFileUpload插件中调用函数，不支持将变量直接传递给参数，即语法错误：invalid or unexpected token
                                // html += '<li><img src="'+list[i]+'" style="width: 250px;height: 190px" onclick="showImages('+list[i]+')"></li>';

                                // 而是需要采用这种形式方可，即只能传递当前对象
                                html += '<li><img src="'+list[i]+'" style="width: 250px;height: 190px" onclick="showImages(this)"></li>';
                            }
                        }
                        $("#show").html(html);
                    }else{
                        layer.alert('上传失败!');
                        console.log(msg);
                    }
                },
                error: function(data, status, e){
                    console.log(data);
                    alert(e);
                }
            });
        }else{
            alert("仅支持" + allType + "为后缀名的文件!");
        }
    }

    function showImages(url){
        var imag = $(url).attr("src");
        window.open(imag);
    }

    $("#imgTitle").on("click",function (eventObject) {
        $("#manyImages").trigger("click");
    });

    // 上传多个文件
    function uploadImg(image) {
        var file = $(image).val();
        console.log(image);
        console.log(file);
        if(file==undefined || file=="" || file==null){
            layer.alert('请重新添加附件图片!');
            return false;
        }else if(!/\.(?:png|jpg|bmp|gif|PNG|JPG|BMP|GIF)$/.test(file)){
            layer.alert('请重新添加附件图片!');
            $(image).val('');
            return false;
        }else{
            console.log("1");

            $.ajaxFileUpload({
                url: getBasePath() + "/do/manyImages",
                type: "post",
                secureuri: false,
                fileElementId: "manyImages",
                dataType: "json",
                contentType: false,
                processData: false,
                success: function (data) {
                    data = $.parseJSON(data.replace(/<.*?>/ig,""));
                    console.log(data);
                    if(data!=null){
                        var list = data.list;
                        for(var i=0;i<list.length;i++){
                            console.log(list[i]);
                        }
                    }else{
                        layer.alert('上传失败!');
                        $(image).val('');
                    }

                    var html = "";
                    var reader = new FileReader();
                    reader.onload = function(ev) {
                        // ev.target.result 是图片的 base64 位编码数据
                        // ev.target 是一个图片的 base64 位编码数据对象，不能直接使用
                        // 另外 FileReader只能接收并处理 text/html 格式的数据，不能处理 json，所以以下代码无效
                        html += '<li><img src="'+ev.target.result+'"></li>';

                        // 应该改为这种格式，就能正常显示图片了
                        $("#detailImgs").html('<li><img src="' + ev.target.result + '" /></li>');

                        // 应用于 IE
                        prevDiv.html('<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>');
                        var imgstr = ev.target.result; //这就是base64字符串

                    }
                    reader.readAsDataURL(file.files[0]);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layer.alert('上传失败，请检查网络后重试!');
                    $(image).val('');
                }
            });
        }
    }

    function test() {
        // 这两项在 chrome可以正常运行
        console.log($("input[name=manyImages]").val());
        console.log($("#manyImages").val());
    }
</script>

<%-- 回车实现自动执行登录功能 --%>
<script language="javascript">

    $("#btnLogin").click(function () {
        var data = $("#form").serialize();
        $.ajax({
            type:"post",
            data:data,
            dataType:"json",
            url:getBasePath()+"/test/login",
            success:function (json) {
                console.log(json);
                if(json.obj1==null || json.obj1==0){
                    alert("test alert");
                }else{
                    window.location = getBasePath()+"/test/takephotos";
                }
            }
        });
    });

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
