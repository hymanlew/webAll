
var days=7;     //设置一页显示的天数
var  numJ=1;
$(document).ready(function(e) {     //加载完成后运行

    /*** 标题日期不需要自动滚动，去掉即可 ***/
    // time = window.setInterval(function(){
    // 	$('.og_next').click();
    // },3500);

    linum = $('.mainlist li').length;//图片数量
    w = linum * 100 + 800;//ul宽度
    $('.piclist').css('width', w + 'px');//ul宽度
    $('.swaplist').html($('.mainlist').html());//复制内容
    var i= Math.ceil(($('.mainlist li').length)/days);
    // alert(i);

	/*$('.og_next').click(function(){
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
	 if($('.mainlist li').length>4){                //多于4张图片
	 ml = parseInt($('.mainlist').css('left'));     //默认图片ul位置
	 sl = parseInt($('.swaplist').css('left'));     //交换图片ul位置
	 if(ml<=0 && ml>w*-1){                          //默认图片显示时
	 $('.swaplist').css({left: '100%'});            //交换图片放在显示区域右侧
	 $('.mainlist').animate({left: ml - 350 + 'px'},'slow');    //默认图片滚动
	 if(ml==(w-350)*-1){                             //默认图片最后一屏时
	 $('.swaplist').animate({left: '0px'},'slow');   //交换图片滚动
	 }
	 }else{//交换图片显示时
	 $('.mainlist').css({left: '1000px'})            //默认图片放在显示区域右
	 $('.swaplist').animate({left: sl - 350 + 'px'},'slow');    //交换图片滚动
	 if(sl==(w-350)*-1){                            //交换图片最后一屏时
	 $('.mainlist').animate({left: '0px'},'slow');  //默认图片滚动
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
	 })*/
});

$(document).ready(function(){
    $('.og_prev,.og_next').hover(function(){
        $(this).fadeTo('fast',1);
    },function(){
        $(this).fadeTo('fast',1);
    })

})

//设置点击后向左，向右滑动的效果
function rollToStart() {
    while(numJ!=1){
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
    }

}