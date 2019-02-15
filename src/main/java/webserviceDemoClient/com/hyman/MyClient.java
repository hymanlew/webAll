package webserviceDemoClient.com.hyman;

import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 创建访问服务的类:
 */
public class MyClient {

    public static void main(String[] args) {
        /**
         * 但是依据 wsdl 自动生成的 SendService.java文件（Wsimport仅支持SOAP1.1客户端的生成），已经是接口类型的了，并且是不能变的。
         * 所以把原来的 java文件移到 webserviceDemo 目录中。
         *
         * 当然也可以把生成的 java 文件放到新的目录中，并执行。
         */

        //创建服务访问点集合的对象
        MyService service = new MyService();

        //获取服务实现类， 根据服务访问点的集合中的服务访问点的绑定对象来获得绑定的服务类
        //获得服务类的方式是get+服务访问点的name：getWSServerPort
        SendService send = service.getSendServicePort();
        String result = send.transword("hello");
        System.out.println(result);

        result = send.dotest("test");
        System.out.println(result);
    }

    public void OtherSetting(){

        // 在本程序中没有用到
        // 发送 POST 请求，需要添加 commons-httpclient 插件
        PostMethod postMethod = new PostMethod();

    }
}
