package webserviceDemoClient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StaticHttpClient {
    public static void main(String[] args) throws Exception{

        String soapRequestData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"

                        + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                +
                         "  <soap:Body>"

                        + " <transword xmlns=\"http://www.hyman.com\">"
                        + "hello"
                        + " </transword>" + "</soap:Body>" + "</soap:Envelope>";
        System.out.println(soapRequestData);

        // 生成一个HttpClient对象，并发出postMethod请求
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://127.0.0.1:8088/serviceTest?wsdl");

        // 然后把Soap请求数据添加到PostMethod中
        byte[] bytes = soapRequestData.getBytes("utf-8");
        InputStream inputStream = new ByteArrayInputStream(bytes, 0, bytes.length);

        RequestEntity re = new InputStreamRequestEntity(inputStream, bytes.length,"text/xml; charset=utf-8");
        postMethod.setRequestEntity(re);
        postMethod.setRequestHeader("SOAPAction","http://127.0.0.1:8088/transword");

        int statusCode = httpClient.executeMethod(postMethod);

        InputStream responseBody = postMethod.getResponseBodyAsStream();
        InputStreamReader in = new InputStreamReader(responseBody,"utf-8");
        // 缓冲字符输入流
        BufferedReader br = new BufferedReader(in);

        StringBuffer stringBuffer = new StringBuffer();
        String line ="";
        while((line=br.readLine())!=null){
            System.out.println("line : "+line);
            stringBuffer.append(line);
        }
        in.close();

        postMethod.releaseConnection();
        String result = stringBuffer.toString().trim();

        // 此时的 result 字符串还只是服务端响应的 soap 格式的 xml 文件，里面的数据还需要分解出来才能使用。
        System.out.println("result : "+result);

        if(statusCode == 200) {
             System.out.println("调用成功！");
                String soapResponseData = postMethod.getResponseBodyAsString();
                System.out.println(soapResponseData);
            }
        else {
                System.out.println("调用失败！错误码：" + statusCode);
            }

    }
}

