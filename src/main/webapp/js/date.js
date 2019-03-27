
// prototype：标准，规范，设置 date的默认使用格式，日期格式标准化
// 也可以自定义为指定的格式或逻辑，日期格式标准化，即为 year - month - date（代码放在页面中），即在页面中重写该方法
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),    //季度
        "S": this.getMilliseconds()                     //毫秒
    }

    // 正则表达式
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}



/**
 *转换日期对象为日期字符串
 * @param date 日期对象
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatDate(date, isFull) {
    var pattern = "";
    if (isFull == true || isFull == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    } else {
        pattern = "yyyy-MM-dd";
    }
    return getFormatDate(date, pattern);
}

/**
 * 将当前日期对象设置为指定时间格式为日期字符串
 */
function getSmpFormatNowDate(isFull) {
    return getSmpFormatDate(new Date(), isFull);
}

/**
 * 转换 long值（毫秒数）为日期字符串
 * @param l long值
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */

function getFormatDateByLong(l, pattern) {
    return getFormatDate(new Date(l), pattern);
}
/**
 *转换日期对象为日期字符串
 * @param l long值
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */
function getFormatDate(date, pattern) {
    if (date == undefined) {
        date = new Date();
    }
    if (pattern == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    }
    // 调用 Date.prototype.format 函数
    return date.format(pattern);
}

/**
 * 修改日期
 * @param date
 * @returns {*}
 */
function getPreMonth(date) {
    if(date==null||date==""){
        return "";
    }
    var arr = date.split('-');
    var year = arr[0]; //获取当前日期的年份
    var month = arr[1]; //获取当前日期的月份
    var day = arr[2]; //获取当前日期的日

    var date1 = new Date(year, month, 0);
    var day1 = date1.getDate(); // getDate() 方法获取当前日期，就是今天，day
    var year2 = year;
    var month2 = parseInt(month) - 1;
    if (month2 == 0) {
        year2 = parseInt(year2) - 1;
        month2 = 12;
    }

    var date2 = new Date(year2, month2, 0); // 如果 day设置为 0，则 getDate() 返回的日期为最后一天
    var day2 = days2.getDate();
    if (day > day2) {
        day = day2;
    }
    if (month2 < 10) {
        month2 = '0' + month2;
    }
    var t2 = year2 + '-' + month2 + '-' + day2;
    return t2;
}


function getNextDay(d){
    if(d==null||d=="")
        return;
    d = new Date(d);
    d = +d + 1000*60*60*24;
    d = new Date(d);
    //格式化
    return getFormatDate(d,"yyyy-MM-dd");

}

function getFirstDayOfMonth(date){
    if(date==null||date==""){
        return "";
    }
    var arr = date.split('-');
    var year = arr[0]; //获取当前日期的年份
    var month = arr[1]; //获取当前日期的月份
    if (month < 10) {
        month = '0' + month;
    }
    var day = arr[2]; //获取当前日期的日
    var t2 = year + '-' + month + '-' + '01';
    return t2;
}
//alert(getSmpFormatDate(new Date(1279849429000), true));
//alert(getSmpFormatDate(new Date(1279849429000),false));
//alert(getSmpFormatDateByLong(1279829423000, true));
// alert(getSmpFormatDateByLong(1279829423000,false));
//alert(getFormatDateByLong(1279829423000, "yyyy-MM"));
//alert(getFormatDate(new Date(1279829423000), "yy-MM"));
//alert(getFormatDateByLong(1279849429000, "yyyy-MM hh:mm"));
