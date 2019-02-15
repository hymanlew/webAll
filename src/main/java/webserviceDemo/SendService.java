package webserviceDemo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * 新建一个java类用来提供WebService服务，即此类，通过 javax.jws.WebService 注解提供服务（JDK 自带的），
 * 通过 javax.xml.ws.Endpoint 类的publish方法来发布服务并提供发布的地址以及发布的服务类，代码如下：
 *
 * 使用@WebService注解标注实现类，指定将此类发布成一个webservice
 *
 * WebService的注解包括：

 •    @WebService-定义服务   --类上
 •    @WebMethod-定义方法   － 方法
 •    @WebResult-定义返回值– 返回值
 •    @WebParam-定义参数– 参数
 *
 *
 */

//serviceName： 对外发布的服务名，指定 Web Service 的服务名称：wsdl:service。缺省值为 Java 类的简单名称 + Service。（字符串）
//targetNamespace：指定你想要的名称空间，认是使用接口实现类的包名的反缀

//@WebService
@WebService(serviceName="myService",targetNamespace="http://www.hyman.com")
public class SendService {

    @WebMethod(operationName = "transword") //为方法定义别名，用于客户端调用，默认是方法名
    @WebResult(name = "result")             //定义返回值变量的名称
    public String transWords(@WebParam(name="words") String words){
        String res = "";
        for(char c:words.toCharArray()){
            res += c+",";
        }
        return "test:"+res;
    }

    public String dotest(String name){
        return  "hello: " + name;
    }

    /**
     *
     * 类中所有 public的，非静态方法都会被发布，即声明此方法为 soap方法。该方法有两个参数，一个input的String，一个output的String。
     *
     * 静态方法，非 public和 final方法不能被发布；
     * 在方法上添加 exclude=true 后，则此方法不会被发布
     */
    @WebMethod(exclude=true)
    public String HelloWord(String name){
        return"Hello: "+name;
    }

    public static void main(String[] args) {

        /**
         * 使用Endpoint(终端)类发布webservice
         * Endpoint – 此类为端点服务类，它的方法publish用于将一个已经添加了@WebService注解对象绑定到一个地址的端口上。
         * EndPoint是jdk提供的一个专门用于发布服务的类，该类的publish方法接收两个参数，一个是本地的服务地址，二是提供服务的类。
         * 位于 javax.xml.ws.Endpoint包中
          */

        // 测试关闭发布用的
        //Endpoint endpoint =Endpoint.publish("http://127.0.0.1/serviceTest",new SendService());
        Endpoint.publish("http://127.0.0.1/serviceTest",new SendService());
        System.out.println("Publish Success");

        //服务发布后不需要关闭，因为只有开启服务时外界才能连接上它。并且浏览器访问 wsdl 文档，或者用 wsimport 生成客户端代码时都要保持开启状态。
        //endpoint.stop();
    }
}

/**
 * 启动main方法运行这个类，打印出结果Publish Success，浏览器访问 http://127.0.0.1/serviceTest?wsdl 出现 xml文本，则代表发布成功。
 * 但需要注意把发布关闭的步骤注释掉，否则连接不上。
 *
 *
 * 通过Java类编译Webservice：
 * JAX-WS 2.0 有两种开发过程：自顶向下和自底向上。自顶向下方式指通过一个 WSDL 文件来创建Web Service，自底向上是从 Java 类出发创建 Web
 * Service。两种开发过程最终形成的文件包括：
 *
 * 1．SEI。一个SEI对应WSDL中WebService的一个port，在Java中是一个Java接口。
 * 2．SEI实现类。
 * 3．WSDL和XSD文件。
 *
 *
 * 因此，我们选用Server端通过Java Class生成webservice，而客户端通过wsdl生成Java调用类的做法。
 * JAXWS为我们提供了两个工具：
 *
 * wsimport是JDK自带的，可以根据WSDL文档生成客户端调用代码的工具。无论服务器端WebService使用什么语言编写的，都将在客户端生成Java代码。
 * 所以服务器用什么语言编写的并不重要。
 * 但是 Wsimport 仅支持SOAP1.1客户端的生成；
 *
 * 在终端中执行 wsimport命令，先进入好需要生成 java文件的目录，执行 wsimport -s . http://127.0.0.1/serviceTest?wsdl
 * 还可以自定义输出文件的位置：wsimport -s . -d E:hyman.test http://127.0.0.1/serviceTest?wsdl
 *
 * wsimport.exe命令参数熟知：
 *
 *  -d:指定放置生成的输出文件的位置，根目录开始
 *  -s：生成Java文件
 *  -p：自定义包结构，指定目标程序包
 *
 *  如果是用 eclipse 工具，则可以直接 new -> other -> web service client，在 service definition 中输入 wsdl 地址，output 项目
 *  目录即可。
 *
 *  然后将生成的包及.java文件复制到新建的项目中，并创建访问服务的类（MyClient）。
 *
 *
 *  wsgen：是主要用于Server端通过Java类编译成 Webservice及相关的 wsdl文件
 *
 *  wsdl参数代表生成webservice

 - s参数代表生成的.java文件置于何处

 -d 参数代表生成的编译class文件置于何处（这个可以忽略，我们利用eclipse编译）

 -r 参数代表生成的.wsdl文件与.xsd文件生成在何处

 -cp参数代表classpath，即需要发布的类的目录（是从根目录开始的，从盘符开始）
 *
 * 即 wsgen -cp 根级开始的类目录 -wsdl -s -d -r
 *
 *
 * 通过Server端的WSDL生成供JAVA调用的客户端：
 *
 * 同步与异步：
 * 同步调用，很好理解，即一来一回，Client端request到Server端，Sever端立刻回一个response。
 * 异步调用，就是客户端调用一次服务端后，服务端处理事务并不是即时返回的，比如说传一个600MB文件给服务端，服务端在处理接收和解析文件时，客户端
 * 不会马上得到一个响应，它会等待一段时间，等服务器处理完后，再通知客户端“我处理完了”。
 *
 * 利用wsimport产生客户端，上面的 wsimport 是产生同步客户端的命令。
 */
