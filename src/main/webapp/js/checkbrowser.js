

var browserInfo = function (userAgent) {

    // navigator.userAgent.indexOf来判断浏览器类型
    // 是浏览器对象
    var u = userAgent || navigator.userAgent;
    var self = this;
    var match = {
        //内核
        'Trident': u.indexOf('Trident') > 0 || u.indexOf('NET CLR') > 0,
        'Presto': u.indexOf('Presto') > 0,
        'WebKit': u.indexOf('AppleWebKit') > 0,
        'Gecko': u.indexOf('Gecko/') > 0,
        //浏览器
        'Safari': u.indexOf('Safari') > 0,
        'Chrome': u.indexOf('Chrome') > 0 || u.indexOf('CriOS') > 0,
        'IE': u.indexOf('MSIE') > 0 || u.indexOf('Trident') > 0,
        'Edge': u.indexOf('Edge') > 0,
        'Firefox': u.indexOf('Firefox') > 0,
        'Opera': u.indexOf('Opera') > 0 || u.indexOf('OPR') > 0,
        'Vivaldi': u.indexOf('Vivaldi') > 0,
        'UC': u.indexOf('UC') > 0 || u.indexOf(' UBrowser') > 0,
        'QQBrowser': u.indexOf('QQBrowser') > 0,
        'QQ': u.indexOf('QQ/') > 0,
        'Baidu': u.indexOf('Baidu') > 0 || u.indexOf('BIDUBrowser') > 0,
        'Maxthon': u.indexOf('Maxthon') > 0,
        'LBBROWSER': u.indexOf('LBBROWSER') > 0,
        '2345Explorer': u.indexOf('2345Explorer') > 0,
        'Sogou': u.indexOf('MetaSr') > 0 || u.indexOf('Sogou') > 0,
        'Wechat': u.indexOf('MicroMessenger') > 0,
        'Taobao': u.indexOf('AliApp(TB') > 0,
        'Alipay': u.indexOf('AliApp(AP') > 0,
        'Weibo': u.indexOf('Weibo') > 0,
        'Suning': u.indexOf('SNEBUY-APP') > 0,
        'iQiYi': u.indexOf('IqiyiApp') > 0,
        //操作系统平台
        'Windows': u.indexOf('Windows') > 0,
        'Linux': u.indexOf('Linux') > 0,
        'Mac': u.indexOf('Macintosh') > 0,
        'Android': u.indexOf('Android') > 0 || u.indexOf('Adr') > 0,
        'WP': u.indexOf('IEMobile') > 0,
        'BlackBerry': u.indexOf('BlackBerry') > 0 || u.indexOf('RIM') > 0 || u.indexOf('BB') > 0,
        'MeeGo': u.indexOf('MeeGo') > 0,
        'Symbian': u.indexOf('Symbian') > 0,
        'iOS': u.indexOf('like Mac OS X') > 0,
        //移动设备
        'Mobile': u.indexOf('Mobi') > 0 || u.indexOf('iPh') > 0 || u.indexOf('480') > 0,
        'Tablet': u.indexOf('Tablet') > 0 || u.indexOf('iPad') > 0 || u.indexOf('Nexus 7') > 0
    };
    if (match.Mobile) {
        match.Mobile = !(u.indexOf('iPad') > 0);
    }
    //基本信息
    var hash = {
        engine: ['WebKit', 'Trident', 'Gecko', 'Presto'],
        browser: ['Safari', 'Chrome', 'IE', 'Edge', 'Firefox', 'Opera', 'Vivaldi', 'UC', 'QQBrowser', 'QQ', 'Baidu', 'Maxthon', 'Sogou', 'LBBROWSER', '2345Explorer', 'Wechat', 'Taobao', 'Alipay', 'Weibo', 'Suning', 'iQiYi'],
        os: ['Windows', 'Linux', 'Mac', 'Android', 'iOS', 'WP', 'BlackBerry', 'MeeGo', 'Symbian'],
        device: ['Mobile', 'Tablet']
    };
    self.device = 'PC';
    self.language = (function () {
        var g = (navigator.browserLanguage || navigator.language);
        var arr = g.split('-');
        if (arr[1]) {
            arr[1] = arr[1].toUpperCase();
        }
        return arr.join('-');
    })();

    for (var s in hash) {
        for (var i = 0; i < hash[s].length; i++) {
            var value = hash[s][i];
            if (match[value]) {
                self[s] = value;
            }
        }
    }

    // s 是遍历每一个元素，即 engine，browser。。。


    //系统版本信息
    var osVersion = {
        'Windows': function () {
            var v = u.replace(/^.*Windows NT ([\d.]+);.*$/, '$1');
            var hash = {
                '6.4': '10',
                '6.3': '8.1',
                '6.2': '8',
                '6.1': '7',
                '6.0': 'Vista',
                '5.2': 'XP',
                '5.1': 'XP',
                '5.0': '2000'
            };
            return hash[v] || v;
        },
        'Android': function () {
            return u.replace(/^.*Android ([\d.]+);.*$/, '$1');
        },
        'iOS': function () {
            return u.replace(/^.*OS ([\d_]+) like.*$/, '$1').replace(/_/g, '.');
        },
        'Mac': function () {
            return u.replace(/^.*Mac OS X ([\d_]+).*$/, '$1').replace(/_/g, '.');
        }
    }
    self.osVersion = '';
    if (osVersion[self.os]) {
        self.osVersion = osVersion[self.os]();
    }
    //浏览器版本信息
    var version = {
        'Chrome': function () {
            return u.replace(/^.*Chrome\/([\d.]+).*$/, '$1');
        },
        'IE': function () {
            var v = u.replace(/^.*MSIE ([\d.]+).*$/, '$1');
            if (v == u) {
                v = u.replace(/^.*rv:([\d.]+).*$/, '$1');
            }
            return v != u ? v : '';
        },
        'Edge': function () {
            return u.replace(/^.*Edge\/([\d.]+).*$/, '$1');
        },
        'Firefox': function () {
            return u.replace(/^.*Firefox\/([\d.]+).*$/, '$1');
        },
        'Safari': function () {
            return u.replace(/^.*Version\/([\d.]+).*$/, '$1');
        },
        'Opera': function () {
            return u.replace(/^.*Opera\/([\d.]+).*$/, '$1');
        },
        'Vivaldi': function () {
            return u.replace(/^.*Vivaldi\/([\d.]+).*$/, '$1');
        },
        'Maxthon': function () {
            return u.replace(/^.*Maxthon\/([\d.]+).*$/, '$1');
        },
        'QQBrowser': function () {
            return u.replace(/^.*QQBrowser\/([\d.]+).*$/, '$1');
        },
        'QQ': function () {
            return u.replace(/^.*QQ\/([\d.]+).*$/, '$1');
        },
        'Baidu': function () {
            return u.replace(/^.*BIDUBrowser[\s\/]([\d.]+).*$/, '$1');
        },
        'UC': function () {
            return u.replace(/^.*UC?Browser\/([\d.]+).*$/, '$1');
        },
        '2345Explorer': function () {
            return u.replace(/^.*2345Explorer\/([\d.]+).*$/, '$1');
        },
        'Wechat': function () {
            return u.replace(/^.*MicroMessenger\/([\d.]+).*$/, '$1');
        },
        'Taobao': function () {
            return u.replace(/^.*AliApp\(TB\/([\d.]+).*$/, '$1');
        },
        'Alipay': function () {
            return u.replace(/^.*AliApp\(AP\/([\d.]+).*$/, '$1');
        },
        'Weibo': function () {
            return u.replace(/^.*weibo__([\d.]+).*$/, '$1');
        },
        'Suning': function () {
            return u.replace(/^.*SNEBUY-APP([\d.]+).*$/, '$1');
        },
        'iQiYi': function () {
            return u.replace(/^.*IqiyiVersion\/([\d.]+).*$/, '$1');
        }
    };
    self.version = '';
    if (version[self.browser]) {
        self.version = version[self.browser]();
    }
};


// 首先，我们要了解浏览器是如何处理内容的。在浏览器中显示的内容有 HTML、有 XML、有 GIF、还有 Flash ……那么，浏览器是如何区分它们，决定
// 什么内容用什么形式来显示呢？答案是 MIME Type，也就是该资源的媒体类型。

// 媒体类型通常是通过 HTTP 协议，由 Web 服务器告知浏览器的，更准确地说，是通过 Content-Type 来表示的，例如: Content-Type: text/HTML
//
//
// 常见的MIME类型：
// 超文本标记语言文本 .html,.html text/html
// 普通文本 .txt text/plain
// RTF文本 .rtf application/rtf
// GIF图形 .gif image/gif
// JPEG图形 .ipeg,.jpg image/jpeg
// au声音文件 .au audio/basic
// MIDI音乐文件 mid,.midi audio/midi,audio/x-midi
// RealAudio音乐文件 .ra, .ram audio/x-pn-realaudio
// MPEG文件 .mpg,.mpeg video/mpeg
// AVI文件 .avi video/x-msvideo
// GZIP文件 .gz application/x-gzip
// TAR文件 .tar application/x-tar


// 区分360，chrome（logout.jsp）
function _mime(option, value) {
    var mimeTypes = navigator.mimeTypes;
    for (var mt in mimeTypes) {
        if (mimeTypes[mt][option] == value) {
            return true;
        }
    }
    return false;
}
