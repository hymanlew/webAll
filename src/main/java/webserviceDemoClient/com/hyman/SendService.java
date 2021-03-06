
package webserviceDemoClient.com.hyman;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SendService", targetNamespace = "http://www.hyman.com")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SendService {


    /**
     * 
     * @param words
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "transword", targetNamespace = "http://www.hyman.com", className = "com.hyman.Transword")
    @ResponseWrapper(localName = "transwordResponse", targetNamespace = "http://www.hyman.com", className = "com.hyman.TranswordResponse")
    @Action(input = "http://www.hyman.com/SendService/transwordRequest", output = "http://www.hyman.com/SendService/transwordResponse")
    public String transword(
            @WebParam(name = "words", targetNamespace = "")
                    String words);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dotest", targetNamespace = "http://www.hyman.com", className = "com.hyman.Dotest")
    @ResponseWrapper(localName = "dotestResponse", targetNamespace = "http://www.hyman.com", className = "com.hyman.DotestResponse")
    @Action(input = "http://www.hyman.com/SendService/dotestRequest", output = "http://www.hyman.com/SendService/dotestResponse")
    public String dotest(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0);

}
