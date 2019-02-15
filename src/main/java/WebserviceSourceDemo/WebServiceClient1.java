package WebserviceSourceDemo;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WebServiceClient1 {

    // asmx 或者 wsdl的 url路径地址
    private static final String baseUrl="http://127.0.0.1/collection";

    // 网站域名，用于声明在 xmlns中，如 www.baidu.com
    private static final String basePosition="http://218.4.84.171:5445/";


    public static Map<String,String> sendPost(String method, LinkedHashMap<String,String>map){
        if(method==""){
            throw new RuntimeException("方法名不能为空");
        }
        if(map==null){
            throw new RuntimeException("参数map不能为空，没有参数也要传一个map");
        }
        Map<String,String>result=new HashMap();
        try {
            String soapRequestData =
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                            "  <soap:Body>" ;
            soapRequestData +="<"+method+" xmlns=\""+basePosition+"\">" ;
            for(String key:map.keySet()){
                soapRequestData+="<"+key+">"+map.get(key)+"</"+key+">" ;
            }
            soapRequestData +="</"+method+">" ;

            soapRequestData +="  </soap:Body>" +
                    "</soap:Envelope>";
            PostMethod postMethod = new PostMethod(baseUrl);

            byte[] b = soapRequestData.getBytes("utf-8");
            InputStream is = new ByteArrayInputStream(b,0,b.length);
            RequestEntity re = new InputStreamRequestEntity(is,b.length,"text/xml; charset=utf-8");
            postMethod.setRequestEntity(re);

            HttpClient httpClient = new HttpClient();
            int statusCode = httpClient.executeMethod(postMethod);
            System.out.println("statusCode====="+statusCode);
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));//读取返回的数据流
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while((str = reader.readLine())!=null){
                stringBuffer.append(str);
            }
            String ts = stringBuffer.toString();

            String xml=escape(ts);
            xml=xml.replace("<?xml version=\"1.0\"?>","");
            System.out.println("xml="+xml);
            // 调用请求END

            StringReader read = new StringReader(xml);
            InputSource source = new InputSource(read);
            SAXBuilder sb = new SAXBuilder();
            Document doc = (Document) sb.build(source);
            Element root = doc.getRootElement();
            result.put(root.getName(),root.getText());
            parse(root,result);
        }catch (Exception e){
            e.printStackTrace();
            return result;
        }

        return result;
    }

    /**
     * @param url       asmx 或者 wsdl的 url路径地址
     * @param position  网站域名，用于声明在 xmlns中，如 www.baidu.com
     * @param method    需要调用的方法，接口
     * @param map       用于传输的数据
     * @return
     */
    public static Map<String,String> sendPost(String url,String position,String method,LinkedHashMap<String,String>map){
        if(method==""){
            throw new RuntimeException("方法名不能为空");
        }
        if(map==null){
            throw new RuntimeException("参数map不能为空，没有参数也要传一个map");
        }
        Map<String,String>result=new HashMap();
        try {
            String soapRequestData =
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                            "  <soap:Body>" ;
            soapRequestData +="<"+method+" xmlns=\""+position+"\">" ;
            for(String key:map.keySet()){
                soapRequestData+="<"+key+">"+map.get(key)+"</"+key+">" ;
            }

            // 企业注册码，这个是特定环境下（即对方需要接收的数据）才用到的
            soapRequestData +="<qyCode>bf3ca58b-e254-47ca-8d44-67cfd1632c0e20171102</qyCode>";
            soapRequestData +="</"+method+">" ;

            soapRequestData +="</soap:Body>" +
                    "</soap:Envelope>";
            System.out.println(soapRequestData);
            PostMethod postMethod = new PostMethod(url);

            System.out.println(soapRequestData);

            byte[] b = soapRequestData.getBytes("utf-8");
            InputStream is = new ByteArrayInputStream(b,0,b.length);

            RequestEntity re = new InputStreamRequestEntity(is,b.length,"text/xml; charset=utf-8");
            postMethod.setRequestEntity(re);
            HttpClient httpClient = new HttpClient();
            int statusCode = httpClient.executeMethod(postMethod);
            System.out.println("statusCode====="+statusCode);
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));//读取返回的数据流
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while((str = reader.readLine())!=null){
                stringBuffer.append(str);
            }
            String ts = stringBuffer.toString();

            String xml=escape(ts);
            xml=xml.replace("<?xml version=\"1.0\"?>","");
            System.out.println("xml="+xml);
            // 调用请求END

            StringReader read = new StringReader(xml);
            InputSource source = new InputSource(read);
            SAXBuilder sb = new SAXBuilder();
            Document doc = (Document) sb.build(source);
            Element root = doc.getRootElement();
            result.put(root.getName(),root.getText());
            parse(root,result);
        }catch (Exception e){
            e.printStackTrace();
            return result;
        }
        return result;
    }
    public static Map<String,String> parse(Element root,Map<String,String>result){
        List nodes = root.getChildren();
        int len = nodes.size();
        if(len==0){
            result.put(root.getName(),root.getText());
        } else {
            for(int i=0;i<len;i++){
                Element element = (Element) nodes.get(i);//循环依次得到子元素
                result.put(element.getName(),element.getText());
                parse(element,result);
            }
        }
        return result;
    }

    //替换转义符号
    public static String escape(String str){
        str= "" + str;
        return str.replace("&lt;", "<").replace("&gt;", ">");
    }

    public static String GetImageStr(String path)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = path;  //待处理的图片路径
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            File file=new File(imgFile);
            if(!file.exists()){
                //imgFile="/usr/local/tomcat/webapps/building/images/tx.jpg";
                imgFile="E://dog.jpg";
                file=new File(imgFile);
            }
            System.out.println("图片存在："+file.exists());
            in = new FileInputStream(imgFile);

            /**
             * inputStream.available()的使用方法：
             * 如果服务器发来的字节流没有作任何标记，只是在发完一次数据后flush()，则在客户端可以用此方法来判断流的长度，但一定要在调用
             * read()至少一次之后，不然就只能得到零值。
             */
            data = new byte[in.available()];

            // 读取数据
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        /**
         * Base64是网络上最常见的用于传输 8Bit字节代码的加密编码方式之一，在发送电子邮件时，服务器认证的用户名和密码需要用Base64编码，附
         * 件也需要用Base64编码。
         */
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String result=encoder.encode(data);

        return result;//返回Base64编码过的字节数组字符串
    }

    public static void main(String []args){
        String xml="";
        xml+="<![CDATA[ " +
                "<DataTable>" +
                "<Worker>" +
                "<WorkerName>徐涛</WorkerName>" +
                "<Zjlxid>1</Zjlxid>" +
                "<Zjhm>321321199509072432</Zjhm>" +
                "<Xb>男</Xb>" +
                "<Zz>地址</Zz>" +
                "<Csrq>1995-09-07</Csrq>" +
                "<Mz></Mz>" +
                "<Sfzyxqrq>2011-09-07</Sfzyxqrq>" +
                "<Sfzyxzrq>2021-09-07</Sfzyxzrq>" +
                "<SfzFzjg></SfzFzjg>" +
                "<XlID>3</XlID>" +
                "<Zzmm></Zzmm>" +
                "<Hyzk></Hyzk>" +
                "<ProvinceCode>320000</ProvinceCode>" +
                "<CityCode></CityCode>" +
                "<CountyCode></CountyCode>" +
                "<Lxdh>15851164487</Lxdh>" +
                "<Image>"+GetImageStr("E://dog.jpg")+"</Image>" +
                "</Worker>" +
                "</DataTable>  ]]>";

        System.out.println(xml);
        LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
        map.put("xml",xml);
        Map<String,String>map1=sendPost("http://218.4.84.171:5445/AppWebServiceTest/GHBackBone_UploadToCity.asmx","http://tempuri.org/","UploadWorker",map);
//        Map<String,String>map1=sendPost("http://218.4.84.171:5445/AppWebService/GHBackBone_UploadToCity.asmx","http://218.4.84.171:5445/","UploadWorker",map);
        System.out.println(map1);



//        LinkedHashMap<String,String>map=new LinkedHashMap<>();
//        map.put("dataNumber","6fdc3fa2-6cd2-4e59-bb6e-97aecbe8b14c20170817");
//        map.put("bagsBH","913205058377527060");
//        map.put("xml",xml);
//        Map<String,String>map1=sendPost(getProject,map);
//        System.out.println(map1);
    }
}

