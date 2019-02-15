<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/11/011
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

    /**
     * getScheme获取的是使用的协议： http 或https
     *
     * getProtocol获取的是协议的名称： HTTP/1.11
     *
     * getServerName获取的是域名 ： xxx.com
     *
     * getLocalName获取到的是IP
     */
%>
<html>
<head>
    <%--
        <base> 标签为页面上的所有链接规定默认地址或默认目标。
        通常情况下，浏览器会从当前文档的 URL 中提取相应的元素来填写相对 URL 中的空白。

        而使用 <base> 标签可以改变这一点。浏览器随后将不再使用当前文档的 URL，而使用指定的基本 URL 来解析所有的相对 URL。
        这其中包括 <a>、<img>、<link>、<form> 标签中的 URL。

        target，规定在何处打开链接文档。有 _blank，_parent，_self，_top，framename
        alt，是规定图像的替代文本
    --%>
    <%--<base href="<%=basePath%>">--%>

    <base href="http://www.w3school.com.cn/i/">
    <base target="_blank" />
    <title>Title</title>
</head>
<body>
    <img src="eg_smile.gif" alt="测试图片"/>
    <a href="http://www.w3school.com.cn">W3School</a>

    <div>
        1.定义语言
        格式：
        〈meta http－equiv=″Content－Type″ content=″text/html; charset=gb2312″〉
        这是META最常见的用法,在制作网页时,在纯HTML代码下都会看到它,它起的作用是定义你网页的语言,当访客浏览你的网页时,浏览器

        会自动识别并设置网页中的语言,如果你网页设置的是GB码,而访客没有安装GB码,这时网页只会呈现访客人所设置的浏览器默认语言。

        同样的,如果该网页是英语,那么 charset=en。

        2.描述网页
        格式:
        〈meta name=″Keywords″ CONTENT=″china,enterprise,business,net″〉
        META也常用来描述网页,以供某些搜索站台机器人的使用。大家知道,搜索站台分为两大类,一类为完全人工登录,比如Yahoo；另一类

        为机器人搜 索,以机器人搜索的搜索站台会包含更多的内容。机器人会搜索网页META标签中所设置的描述关键字,把它们加入到搜索数据库

        中,用来索引你的网页。而这个 标签很少有人注意到。在格式中,Content列出了你所设置的关键字,这其中的内容可以自行设置,其间用逗号

        相隔。这里有个技巧,你可以重复某一个单 词,这样可以提高自己网站的排行位置,如：〈meta name=″Keywords″ CONTENT=″china,china,china,china〉

        3.自动刷新页面
        格式:
        〈meta HTTP－EQUIV=″refresh″ CONTENT=″8;URL=网址或者是 action|servlet″〉
        大家在浏览某一个网页时,会发现有些页在数秒后自动转到另外一页,这就是META的刷新作用,在CONTENT中,8代表设置的秒数,而URL就
        是过8秒后自动连接的网页地址。

        这个代码一般也用在实时性很强的应用中，需要定期刷新的，如新闻页面，论坛等，不过一般不会用这个，都用新的技术比如ajax等

        也有人在用 <meta http-equiv=”refresh” content=”0; url=”>，hao123网址之家www.hao123.com也在用这个代码
        <meta http-equiv=”Refresh” content=”0; url=http://www.hao123.com/?1394586955″/><meta property=”shurufa:url-navigate” content=”985″ />


        4.网页定级评价
        格式:
        〈meta http－equiv=″PICS－Label″ content=′(PICS－1.1  ″http://www.163.com″  l gen true comment  ″RSACi  North  America  Server″

        for  ″http://www.163.com″  on  ″1996.04.16T08:15－0500″  r (n 0 s 0 v 0 l 0))′〉

        在IE 4.0浏览器Internet选项中有一个内容设置,它可以防止浏览一些受限制的网站,而之所以浏览器会自动识别某些网站是否受限制,就是因为

        在网站 META标签中已经设置好了该网站的级别,而该级别的评定是由美国RSAC,即娱乐委员会的评级机构评定的,如果你需要评价自己的网站,可

        以连接到网站 http://www.163.com/,按要求提交表格,那么RSAC会提供一段META代码给你,复制到自己网页里就可以了。

        5.控制页面缓冲
        格式:
        〈meta   HTTP－EQUIV=″expires″   CONTENT=″TUE,11.NOV  1998  00:00  GMT″〉
        META可以设置网页到期的时间,也就是说,当你在IE 4.0浏览器中设置浏览网页时首先查看本地缓冲里的页面,那么当浏览某一网页,而本地缓冲又有时,

        那么浏览器会自动浏览缓冲区里的页面,直到META中设 置的时间到期,这时候,浏览器才会去取得新页面。

        6.META的属性
        meta                响应页面事件标签
        HTTP－EQUIV=″….″    是页面提交方式，HTTP响应的标题头；
        name=″…..″          META信息的名称；
        content=″….″        META信息的具体内容；
        scheme=″…″          META信息的图解。


        控制页面进入和退出的特效：
        进入页面<meta http-equiv=”Page-Enter” content=”revealTrans(duration=x, transition=y)”>
        推出页面<meta http-equiv=”Page-Exit” content=”revealTrans(duration=x, transition=y)”>

        这个是页面被载入和调出时的一些特效。duration表示特效的持续时间，以秒为单位。transition表示使用哪种特效，取值为1-23:
        0 矩形缩小
        1 矩形扩大
        2 圆形缩小
        3 圆形扩大
        4 下到上刷新
        5 上到下刷新
        6 左到右刷新
        7 右到左刷新
        8 竖百叶窗
        9 横百叶窗
        10 错位横百叶窗
        11 错位竖百叶窗
        12 点扩散
        13 左右到中间刷新
        14 中间到左右刷新
        15 中间到上下
        16 上下到中间
        17 右下到左上
        18 右上到左下
        19 左上到右下
        20 左下到右上
        21 横条
        22 竖条
        23 以上22种随机选择一种

    </div>
</body>
</html>
