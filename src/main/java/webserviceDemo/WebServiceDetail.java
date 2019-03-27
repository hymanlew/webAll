package webserviceDemo;

/**
 * JAVA 中共有三种WebService 规范，分别是JAX-WS（JAX-RPC）（普遍使用的）、JAXM & SAAJ、JAX-RS。
 *
 * 1. JAX-WS 其全称为 Java API for XML-Based Webservices ，它取代了早期的基于SOAP 的JAVA 的Web 服务规范JAX-RPC，是它的扩展。
 *      传输是基于 soap 的协议，此规范是一组XML web services的JAVA API，JAX-WS允许开发者可以选择 RPC-oriented 或者message-oriented
 *      来实现自己的web services。。
 *
 *      JAX-WS 允许Java应用程序可以通过已知的描述信息调用一个基于Java的Web服务，描述信息与Web服务的WSDL描述相一致。即一个远程调用可以转
 *      换为一个基于XML的协议，如SOAP。开发者在使用JAX-WS的过程中，不需要编写任何生成、处理SOAP消息的代码，JAX-WS在运行时自动将API的调用
 *      转换为相应的SOAP消息。
 *
 *      在服务器端，用户只需要通过Java语言定义远程调用所需实现的接口，并提供相应实现，通过调用JAX-WS的服务发布接口即可将其发布为WebService
 *      接口。在客户端，用户可以通过JAX-WS的API创建一个代理来实现对于远程服务器端的调用。
 *
 *      当然 JAX-WS也提供了一组针对底层消息进行操作的API调用，你可以通过Dispatch 直接使用SOAP消息或XML消息发送请求或者使用Provider处理
 *      SOAP或XML消息。通过web service所提供的互操作环境，我们可以用JAX-WS轻松实现JAVA平台与其他编程环境（.net等）的互操作。
 *
 *      代码即 webserviceDemo.SendService（服务端），webserviceDemoClient（包）。

 *
 * 2. JAXM（JAVA API For XML Message）主要定义了包含了发送和接收消息所需的API，SAAJ（SOAP With Attachment APIFor Java，JSR 67）
 *      是与JAXM 搭配使用的API，为构建SOAP 包和解析SOAP 包提供了重要的支持，支持附件传输等，JAXM&SAAJ 与JAX-WS 都是基于SOAP 的Web 服
 *      务，相比之下JAXM&SAAJ 暴漏了SOAP更多的底层细节，编码比较麻烦，而JAX-WS 更加抽象，隐藏了更多的细节，更加面向对象，实现起来你基本上
 *      不需要关心SOAP 的任何细节。
 *
 * 3. JAX-RS 是JAVA EE6 引入的一个新技术，即Java API for RESTful Web Services，是一个Java 编程语言的应用程序接口，支持按照表述性状
 *      态转移（REST）架构风格创建Web服务。JAX-RS使用了Java SE5引入的Java注解来简化Web服务的客户端和服务端的开发和部署。
 *
 *      是 JAVA 针对REST(Representation State Transfer)风格制定的一套Web 服务规范，由于推出的较晚，该规范并未随JDK1.6 一起发行。
 *      Rest定义可以自行搜索，jax-RS可以发布 rest风格webservice，因为rest的webservice不采用soap传输，直接采用http传输，可以返回xml或
 *      json，比较轻量。
 *
 *      JAX-RS提供了一些注解将一个资源类，一个POJO Java类，封装为Web资源。包括：
 *      @Path，标注资源类或者方法的相对路径
 *      @GET，@PUT，@POST，@DELETE，标注方法是HTTP请求的类型。
 *      @Produces，标注返回的MIME媒体类型
 *      @Consumes，标注可接受请求的MIME媒体类型
 *      @PathParam，@QueryParam，@HeaderParam，@CookieParam，@MatrixParam，@FormParam,分别标注方法的参数来自于HTTP请求的不同位置，
 *
 *      例如@PathParam来自于URL的路径，@QueryParam来自于URL的查询参数，@HeaderParam来自于HTTP请求的头信息，@CookieParam来自于HTTP请求的Cookie。
 *      基于JAX-RS实现的框架有Jersey，RESTEasy等。这两个框架创建的应用可以很方便地部署到Servlet 容器中，比如Tomcat，JBoss等。值得一提
 *      的是RESTEasy是由JBoss公司开发的，所以将用RESTEasy框架实现的应用部署到JBoss服务器上，可以实现很多额外的功能。
 *
 */
public class WebServiceDetail {
//    http://localhost:8080/service

    /**
     * SOAP :
     *
     (1)、定义
     SOAP即简单对象访问协议（Simple Object Access Protocol），使用http发送XML格式的数据，他不是webservice的专有协议


     (2)、结构 SOAP = HTTP + XML

     (3)、协议的格式：

     Envelope：必须有，此元素将整个 XML 文档标识为一条SOAP消息
     Header：可选元素，包含头部信息
     Body：必须有，包含所有调用和响应信息
     Fault：可选元素，提供有关在处理此消息时所发生的错误信息


     (4)、版本：
     A、SOAP1.1
     ●   请求
     [html] view plain copy
     POST /weather HTTP/1.1
     Accept: text/xml, multipart/related
     Content-Type: text/xml; charset=utf-8
     SOAPAction: "http://jaxws.ws.itcast.cn/WeatherInterfaceImpl/queryWeatherRequest"
     User-Agent: JAX-WS RI 2.2.4-b01
     Host: 127.0.0.1:54321
     Connection: keep-alive
     Content-Length: 211

     <?xml version="1.0" ?>
     <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
     <S:Body><ns2:queryWeather xmlns:ns2="http://jaxws.ws.itcast.cn/"><arg0>北京</arg0></ns2:queryWeather>
     </S:Body>
     </S:Envelope>

     ●   响应
     [html] view plain copy
     HTTP/1.1 200 OK
     Transfer-encoding: chunked
     Content-type: text/xml; charset=utf-8
     Date: Fri, 04 Dec 2015 03:45:56 GMT

     <?xml version="1.0" ?>
     <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
     <S:Body>
     <ns2:queryWeatherResponse xmlns:ns2="http://jaxws.ws.itcast.cn/"><return>晴</return></ns2:queryWeatherResponse>
     </S:Body>
     </S:Envelope>


     B、SOAP1.2
     ●   请求
     [html] view plain copy
     POST /weather HTTP/1.1
     Accept: application/soap+xml, multipart/related
     Content-Type: application/soap+xml; charset=utf-8;
     action="http://jaxws.ws.itcast.cn/WeatherInterfaceImpl/queryWeatherRequest"
     User-Agent: JAX-WS RI 2.2.4-b01
     Host: 127.0.0.1:54321
     Connection: keep-alive
     Content-Length: 209

     <?xml version="1.0" ?>
     <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
     <S:Body><ns2:queryWeather xmlns:ns2="http://jaxws.ws.itcast.cn/"><arg0>北京</arg0></ns2:queryWeather>
     </S:Body>
     </S:Envelope>

     ●   响应
     [html] view plain copy
     HTTP/1.1 200 OK
     Transfer-encoding: chunked
     Content-type: application/soap+xml; charset=utf-8
     Date: Fri, 04 Dec 2015 03:55:49 GMT

     <?xml version='1.0' encoding='UTF-8'?>
     <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
     <S:Body>
     <ns2:queryWeatherResponse xmlns:ns2="http://jaxws.ws.itcast.cn/"><return>晴</return></ns2:queryWeatherResponse>
     </S:Body>
     </S:Envelope>

     C、SOAP1.1 和 SOAP1.2的区别
     ●   相同点
     请求方式都是POST

     协议格式都一样，都有envelope和body

     ●   不同点
     ①、数据格式不同
     SOAP1.1：text/xml;charset=utf-8
     SOAP1.2：application/soap+xml;charset=utf-8
     ②、命名空间不同
     */

}
