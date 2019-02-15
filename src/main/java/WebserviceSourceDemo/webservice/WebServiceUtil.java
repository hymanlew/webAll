package WebserviceSourceDemo.webservice;

import com.hyman.entity.ReceiveInterfaceData;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.dom4j.io.SAXReader;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.*;

/**
 * Created by WeiJinTechnology on 2017/8/17.
 * 访问webService接口的工具
 */
public class WebServiceUtil {
    private static final String baseUrl="http://218.4.84.171:5445/AppWebService/GHBackBone_DataDocking.asmx";
    private static final String basePosition="http://218.4.84.171:5445/";
    public static final String getEnterprise="GetEnterprises";//获取建筑公司信息
    public static final String getProject="GetProject";//获取工地项目信息
    public static final String addAttendance="InsertIntoKqxx";//获取工地项目信息

    public static Map<String,String> sendPost(String method,LinkedHashMap<String,String>map){
        if(method.isEmpty()){
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

    public static Map<String,String> sendPost(String url,String position,String method,LinkedHashMap<String,String>map){
        if(method.isEmpty()){
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
            soapRequestData +="</"+method+">" ;

            soapRequestData +="</soap:Body>" +
                    "</soap:Envelope>";
//            System.out.println(soapRequestData);
            PostMethod postMethod = new PostMethod(url);

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
        String imgFile = path;//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            File file=new File(imgFile);
            if(!file.exists()){
//                imgFile="/usr/local/tomcat/webapps/building/images/tx.jpg";
                imgFile="I://tx.jpg";
                file=new File(imgFile);
            }
            System.out.println("图片存在："+file.exists());
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String result=encoder.encode(data);
//        System.out.println(result);
        return result;//返回Base64编码过的字节数组字符串
    }

    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GB2312
                String s = encode;
                return s;      //是的话，返回“GB2312“，以下代码同理
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是ISO-8859-1
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {   //判断是不是UTF-8
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GBK
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";        //如果都不是，说明输入的内容不属于常见的编码格式。
    }

    public static Map<String,List> sendPostAndParseResponse(String url,String position,String method,LinkedHashMap<String,String>map,String requestHeader){
        if(method.isEmpty()){
            throw new RuntimeException("方法名不能为空");
        }
        if(map==null){
            throw new RuntimeException("参数map不能为空，没有参数也要传一个map");
        }
        Map<String,List>result=new HashMap();
        try {
            String soapRequestData =
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                            "  <soap:Body>" ;
            soapRequestData +="<"+method+" xmlns=\""+position+"\">" ;
            for(String key:map.keySet()){
                soapRequestData+="<"+key+">"+map.get(key)+"</"+key+">" ;
            }
            soapRequestData +="</"+method+">" ;

            soapRequestData +="</soap:Body>" +
                    "</soap:Envelope>";

            /**
             * 需要注意的是在 body 中第一个标签的 xmlns 的值（即 position）要与服务商提供的值一致才可以，不能直接设为其 ip 地址和 port 端口。
             * 因为有时是按照条件来获取数据时的，就必须与服务商提供的值一致，否则条件就不成立，会查到所有的数据。
             */
            PostMethod postMethod = new PostMethod(url);

            byte[] b = soapRequestData.getBytes("utf-8");
            InputStream is = new ByteArrayInputStream(b,0,b.length);
            RequestEntity re = new InputStreamRequestEntity(is,b.length,"text/xml; charset=utf-8");
            postMethod.setRequestEntity(re);
            postMethod.setRequestHeader("SOAPAction",requestHeader);

            HttpClient httpClient = new HttpClient();
            int statusCode = httpClient.executeMethod(postMethod);
            System.out.println("statusCode====="+statusCode);
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(),"utf-8"));//读取返回的数据流
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while((str = reader.readLine())!=null){
                stringBuffer.append(str);
            }
            reader.close();
            String ts = stringBuffer.toString().trim();

            String xml=escape(ts);
            xml=xml.replace("<?xml version=\"1.0\" encoding=\"gb2312\"?>","");
            xml=xml.replace("<![CDATA[","");
            xml=xml.replace("]]>","");
            // 调用请求END
            xml = xml.trim();
            System.out.println("xml="+xml);

            StringReader read = new StringReader(xml);
            InputSource source = new InputSource(read);

            SAXReader reader1 = new SAXReader();
            org.dom4j.Document doc = reader1.read(source);
            org.dom4j.Element root = doc.getRootElement();
            parseDom4j(root,result);
        }catch (Exception e){
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public static Map<String,List> parseDom4j(org.dom4j.Element root, Map<String,List>result){

        List<org.dom4j.Element> elements = root.elements();
        List<ReceiveInterfaceData> list = null;
        if("NewDataSet".equals(root.getName())){
            list = new ArrayList<>();
        }
        for(org.dom4j.Element e : elements){
            // 只获取 table 内的数据
            if("Table".equals(e.getName())){
                org.dom4j.Element namee = e.element("DanWeiName");
                org.dom4j.Element nume = e.element("UnitOrgNum");
                org.dom4j.Element statue = e.element("AuditStatus");
                String name = namee.getText();
                String num = nume.getText();
                String statu = statue.getText();

                ReceiveInterfaceData data = new ReceiveInterfaceData();
                data.setName(name);
                data.setNum(num);
                data.setStatus(statu);
                list.add(data);

                result.put("data",list);
            }else {
                parseDom4j(e,result);
            }
        }
        return result;
    }

    public static void main(String []args){
        String xml="";
       /*  xml+="<![CDATA[ " +
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
                "<Image>"+GetImageStr("I://tx.jpg")+"</Image>" +
                "</Worker>" +
                "</DataTable>  ]]>";*/
        xml+="<?xml version=\"1.0\"?>" +
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
                "<Image>"+GetImageStr("I://tx.jpg")+"</Image>" +
                "</Worker>" +
                "</DataTable>  ";
        System.out.println(xml);
        LinkedHashMap<String,String>map=new LinkedHashMap<>();
        map.put("xml",xml);
        map.put("qyCode","bf3ca58b-e254-47ca-8d44-67cfd1632c0e20171102");
//        Map<String,String>map1=sendPost("http://218.4.84.171:5445/AppWebService/GHBackBone_City.asmx","http://tempuri.org/","UploadWorker",map);
        Map<String,String>map1=sendPost("http://218.4.84.171:5445/AppWebServiceTest/GHBackBone_UploadToCity.asmx","http://218.4.84.171:5445/","UploadWorker",map);
        System.out.println(map1);

//        LinkedHashMap<String,String>map=new LinkedHashMap<>();
//        map.put("dataNumber","6fdc3fa2-6cd2-4e59-bb6e-97aecbe8b14c20170817");
//        map.put("bagsBH","913205058377527060");
//        map.put("xml",xml);
//        Map<String,String>map1=sendPost(getProject,map);
//        System.out.println(map1);

    }

}
