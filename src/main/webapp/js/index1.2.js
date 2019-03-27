

var basePath="";
$(document).ready(
    function() {
        basePath=getBasePath();

    });

//动态获取项目的根路径 basePath
function getBasePath(){
    //获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
    var urlPath = window.document.location.href;

    //获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
    var pathName = window.document.location.pathname;

    //获取主机地址，如： http://localhost:8080
    var pos = urlPath.indexOf(pathName);
    var localhostPath = urlPath.substring(0, pos);

    //获取带"/"的项目名，如：/ems
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    if(projectName=="page/"){
        pathName="";
    }

    //获取项目的basePath   http://localhost:8080/ems/
    // basePath=localhostPath+projectName+"/";
    // basePath=basePath.replace("page/","");
    basePath = localhostPath;
    return basePath;
};

//设置鼠标移到上方时，自动显示选项的效果
var $animate = $('#animate');
$(".index-header").mouseenter(function(){
    $animate.css("display","block").addClass('slideInDown animated infinite');
    setTimeout(removeClass, 500);
    $(".index-display > span").css("-webkit-transform","rotate(180deg)");
})
function removeClass(){
    $animate.removeClass("slideInDown");
}
$(".index-header").mouseleave(function(){
    $animate.css("display","none");
    $(".index-display > span").css("-webkit-transform","rotate(0deg)");
})

// 左侧栏js开始
// window.onload = function(){
//     var navs = document.getElementById('navs');
//     var navChild = navs.getElementsByTagName('li');
//     for(var i=0;i<navChild.length;i++){
//         navChild[i].onclick = function(){
//             if (this.className === "") {
//                 for(var n=0;n<navChild.length;n++){
//                     navChild[n].className = "";
//                 }
//                 this.className = "index-nav-display";
//             }else{
//                 for(var n=0;n<navChild.length;n++){
//                     navChild[n].className = "";
//                 }
//                 this.className = "";
//             }
//         }
//     }
// }
// $("#navs > li").each(function(){
//     if ($(this).find('.index-nav-child').length>0) {
//         $(this).children("a").addClass("index-nav-bg");
//         $(this).children(".index-nav-child").children("a").click(function(event){
//             $(this).addClass("index-nav-child-on").siblings("a").removeClass("index-nav-child-on");
//             event.stopPropagation();
//         })
//
//     }else{
//         $(this).children("a").addClass("");
//     }
// })

function leftToggle(){
    var leftwidth = $(".index-left").width();
    if (leftwidth === 200) {
        $(".index-left").animate({width:"0"});
        $(".index-right").animate({left:"0"});
        $(".right-top").animate({left:"0"});
        var html="<span class='fa fa-reorder' style='margin-left:20px;font-size:14px;color:#b8bbc2;cursor:pointer' onclick='leftToggle1()'></span>";
        $(".index-head > p").append(html);
    }
}
function leftToggle1(){
    var leftwidth = $(".index-left").width();
    if (leftwidth === 0) {
        $(".index-left").animate({width:"200"});
        $(".index-right").animate({left:"200"});
        $(".right-top").animate({left:"200"});
        $(".index-head > p > span").remove();
    }
}

// tab切换
$(window).on("load",function(){
    $(".rightbody-head > span").click(function(){
        $(this).addClass("span-on").siblings().removeClass("span-on");
        var index = $(this).index();
        $(".right-detail > li").eq(index).show().siblings(".right-detail > li").hide();
    })
})
$(window).on("load",function(){
    $(".rightbody-head1 > li").click(function(){
        $(this).children("span").addClass("span-on1").parent("li").siblings("li").children("span").removeClass("span-on1");
        var index = $(this).index();
        $(".right-detail1 > li").eq(index).show().siblings(".right-detail1 > li").hide();
    })
})

// 评价星星js开始
$(".evaluate-f1 > img").each(function(index){
    var star='images/blackstar.png';        //普通灰色星星图片的存储路径
    var starRed='images/redstar.png';       //红色星星图片存储路径
    this.id="star"+index;                   //遍历img元素，设置单独的id
    $(this).on("click",function(){          //设置鼠标滑动和点击都会触发事件
        $("#score").val(parseInt($(this).attr("id").substr(4))+1);
        $(".evaluate-f1 img").attr('src',basePath+star);    //当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
        $(this).attr('src',basePath+starRed);               //设置鼠标当前所在图片为打星颜色图
        $(this).prevAll().attr('src',basePath+starRed);     //设置鼠标当前的前面星星图片为打星颜色图
    });
});
// 评价星星js开始
$(".evaluate-right-f1 > img").each(function(index){
    var star='images/blackstar.png';    //普通灰色星星图片的存储路径
    var starRed='images/redstar.png';     //红色星星图片存储路径
    this.id=index;      //遍历img元素，设置单独的id
    $(this).on("click",function(){    //设置鼠标滑动和点击都会触发事件
        $(".evaluate-right-f1 img").attr('src',star);//当“回滚”、“改变主意”时，先复位所有图片为木有打星的图片颜色
        $(this).attr('src',starRed);        //设置鼠标当前所在图片为打星颜色图
        $(this).prevAll().attr('src',starRed);  //设置鼠标当前的前面星星图片为打星颜色图
    });
});
// 右侧隐藏菜单
function fixedboxD(){
    var boxA = $("#fixedbox").css("right");
    if (boxA === "-264px") {
        $(".fixed-box").animate({right:"10"});
    }else{
        $(".fixed-box").animate({right:"-264"});
    }
}


$(".fa-power-off").click(function () {
    if(confirm("确定退出登录？")){
        //点击确定后操作
        window.location.href=getBasePath()+"j_spring_security_logout";
    }

});
/*if(($("#siteID").val()==""||$("#siteID").val()==undefined)||($("#siteId").val()==""||$("#siteId").val()==undefined)){
    $("#hideA").show();
}*/