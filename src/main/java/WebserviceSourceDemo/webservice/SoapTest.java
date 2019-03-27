package WebserviceSourceDemo.webservice;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by WeiJinTechnology on 2017/8/17.
 */
public class SoapTest {
    /**
     * 获得腾讯QQ在线状态
     *
     * 输入参数：QQ号码 String，默认QQ号码：8698053。返回数据：String，Y = 在线；N = 离线；E = QQ号码错误；A = 商业用户验证失败；V = 免费用户超过数量
     * @throws Exception
     */
    static HashMap<String, Object> result = new HashMap<String, Object>();
    public static void main(String[] args) throws Exception {
        // 调用请求STA

        String soapRequestData =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "  <soap:Body>" +
                "    <GetEnterprises xmlns=\"http://218.4.84.171:5445/\">" +
                "      <dataNumber>6fdc3fa2-6cd2-4e59-bb6e-97aecbe8b14c20170817</dataNumber>" +
                "      <bagsBH>913205058377527060</bagsBH>" +
                "    </GetEnterprises>" +
                "  </soap:Body>" +
                "</soap:Envelope>";

        /**
         * 需要注意的是在 body 中第一个标签的 xmlns 的值要与服务商提供的值一致才可以，不能直接设为其 ip 地址和 port 端口。
         * 因为有时是按照条件来获取数据时的，就必须与服务商提供的值一致，否则条件就不成立，会查到所有的数据。
         */
        System.out.println(soapRequestData);

        PostMethod postMethod = new PostMethod("http://218.4.84.171:5445/AppWebService/GHBackBone_DataDocking.asmx");

        byte[] b = soapRequestData.getBytes("utf-8");
        InputStream is = new ByteArrayInputStream(b,0,b.length);
        RequestEntity re = new InputStreamRequestEntity(is,b.length,"text/xml; charset=utf-8");
        postMethod.setRequestEntity(re);

        HttpClient httpClient = new HttpClient();
        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode====="+statusCode);
        soapRequestData =  postMethod.getResponseBodyAsString();
        BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
        StringBuffer stringBuffer = new StringBuffer();
        String str = "";
        while((str = reader.readLine())!=null){
            stringBuffer.append(str);
        }
        String ts = stringBuffer.toString();
        System.out.println(soapRequestData);
        System.out.println("==========================");

        String xml=escape(ts);
        xml=xml.replace("<?xml version=\"1.0\"?>","");
        System.out.println("xml = "+xml);
        // 调用请求END

        String xml1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
                "<Message>" +
                "<Head>" +
                "<_TransactionId>1</_TransactionId>" +
                "<requestHeader><version>1.0</version><serialNo></serialNo><requestId></requestId><refSerialNo></refSerialNo><requestTime>2015-04-28 10:58:23.040</requestTime><timeOutTime></timeOutTime><channelId></channelId><channelSerialNo></channelSerialNo>" +
                "</requestHeader>" +
                "<control><requesterName></requesterName><requesterRole>123010003</requesterRole><requesterInsitution></requesterInsitution><requesterId></requesterId><requesterTime>2015-04-28 10:58:23.040</requesterTime><requesterLanguage>124010002</requesterLanguage><requesterLocale></requesterLocale><pageStartIndex></pageStartIndex><pageEndIndex></pageEndIndex><availableResultsCount></availableResultsCount><returnAvailableResultCount></returnAvailableResultCount><isAuthorize></isAuthorize><transCode></transCode>" +
                "</control>" +
                "</Head>" +
                "<Body>" +
                "<adminSysId>1001920073</adminSysId>" +
                "<adminSysTypeCode>0</adminSysTypeCode>" +
                "<custMsgList>" +
                "<body001>body001</body001><body002>body002</body002>" +
                "</custMsgList>" +
                "</Body>" +
                "</Message>";
        System.out.println("=============================================");
        System.out.println("xml1 = "+xml1);

        /**
         * StringReader 和 StringWriter ：
         * 这两个类都是 Reader 和 Writer 的装饰类，使它们拥有了对 String 类型数据进行操作的能力。
         */
        StringReader read = new StringReader(xml);

        /**
         * InputSource 类：它是 XML 实体的单一输入源，并允许 SAX 应用程序封装有关单个对象中的输入源的信息，它可包括公共标识
         * 符、系统标识符、字节流（可能带有指定的编码）、基本 URI 和/ 或字符流。
         *
         * SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入。如果有字符流可用，则解析器将直接读取该流，而忽略该流中
         * 找到的任何文本编码声明。如果没有字符流，但却有字节流，则解析器将使用该字节流，从而使用在 InputSource 中指定的编码，
         * 或者另外（如果未指定编码）通过使用某种诸如 XML 规范中的算法算法自动探测字符编码。如果既没有字符流，又没有字节流可用
         * ，则解析器将尝试打开到由系统标识符标识的资源的 URI 连接。
         *
         * InputSource 对象属于该应用程序：SAX 解析器将不会以任何方式修改它（它可以在必要时修改副本）。但是作为解析终止清除
         * 的一部分，对字节流和字符流的标准处理就是关闭这二者，因此在将此类流传递给解析器后应用程序不应尝试重新使用它们。
         *
         * 也可以使用 ：
         *  InputStreamReader inputStream = new InputStreamReader(new ByteArrayInputStream(xml.getBytes()));
         *  InputSource source = new InputSource(inputStream);
         */
        InputSource source = new InputSource(read);

        SAXBuilder sb = new SAXBuilder();
        try {
            /**
             * 在解析 xml 文件时，最好还是使用 dom4j 包：
             * org.dom4j.Document doc = reader1.read(source);
             * org.dom4j.Element root = doc.getRootElement();
             * parseDom4j(root);
             */
            Document doc = (Document) sb.build(source);
            Element root = doc.getRootElement();
            result.put(root.getName(),root.getText());
            parse(root);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result = "+result);
    }
    public static HashMap<String,Object> parse(Element root){

        List nodes = root.getChildren();
        int len = nodes.size();
        if(len==0){
            result.put(root.getName(),root.getText());
        } else {
            for(int i=0;i<len;i++){
                Element element = (Element) nodes.get(i);//循环依次得到子元素
                result.put(element.getName(),element.getText());
                parse(element);
            }
        }
        return result;
    }

    public static HashMap<String,Object> parseDom4j(org.dom4j.Element root){

        /**
         * 在解析 xml 文件时，最好还是使用 dom4j 包：
         * org.dom4j.Document doc = reader1.read(source);
         * org.dom4j.Element root = doc.getRootElement();
         *
         * root.selectNodes("name"); 是获得所有名字为 name 的属性（注意不是元素），返回一个 list，然后可以遍历得到每个属性的值。
         *
         */
        //List nodes = root.selectNodes("xx");
        //List<org.dom4j.Element> tables = dataSet.elements("Table");
        //
        //for(org.dom4j.Element e:tables){
        //    org.dom4j.Element namee = e.element("DanWeiName");
        //    String name = namee.getText();
        //    org.dom4j.Element nume = e.element("UnitOrgNum");
        //    String num = nume.getText();
        //    org.dom4j.Element statue = e.element("AuditStatus");
        //    String statu = statue.getText();
        //
        //    PropSet propSet = new PropSet();
        //    propSet.setObj1(name);
        //    propSet.setObj2(num);
        //    propSet.setObj3(statu);
        //    result.put("data",propSet);
        //}
        return result;
    }

    public static String escape(String str){
        str= "" + str;
             return str.replace("&lt;", "<").replace("&gt;", ">");
    }

    public static void testDemo(){
        /**
         * StringReader 和 StringWriter ：
         * 这两个类其实都是 Reader 和 Writer 的装饰类，使它们拥有了对 String 类型数据进行操作的能力。
         *
         * 这两个类为什么会存在的，因为这与直接使用String类来进行数据操作，后来在网上看到别人的解释，如果你遇到一个情景是你
         * 必须使用一个Reader或者Writer来作为参数传递参数，但你的数据源又仅仅是一个String类型数据，无需从文件中写出，那么此
         * 时就可以用到它们。
         *
         * 并且值得注意的是 StringWriter 中，写入的数据只是存在于缓存中，并不会写入实质的存储介质之中。它主要用于将其回收在
         * 字符串缓冲区中的输出来构造字符串。
         *
         */
        try (StringReader sr = new StringReader("just a test~"); StringWriter sw = new StringWriter()) {
            int c = -1;
            while((c = sr.read()) != -1){
                sw.write(c);
            }
            // 这里展示了即使关闭了StringWriter流，但仍然能获取到数据，因为其close方法是一个空实现。
            // 方法在关闭该流后仍可被调用，而不会产生任何 IOException。
            sw.close();
            System.out.println(sw.getBuffer().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 解析 xml 文件的另一种方式：
         *
         *  File file = new File("D://123test.xml");
         *  FileOutputStream outputStream = new FileOutputStream(file);
         *  outputStream.write(xml.getBytes());
         *  outputStream.flush();
         *  outputStream.close();
         *
         *  StringReader read = new StringReader(xml);
         *  InputSource source = new InputSource(read);
         *  SAXReader reader1 = new SAXReader();
         *  org.dom4j.Document doc = reader1.read(file);
         *
         org.dom4j.Element root = doc.getRootElement();
         //result.put(root.getName(),root.getText());
         parseDom4j(root,result);
         */
    }
}
