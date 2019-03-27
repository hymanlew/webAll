
package webserviceDemoClient.collection;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the webserviceDemoClient.collection package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RtnMethodResponse_QNAME = new QName("http://webserviceDemo/", "rtnMethodResponse");
    private final static QName _RtnMethod_QNAME = new QName("http://webserviceDemo/", "rtnMethod");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webserviceDemoClient.collection
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RtnMethod }
     * 
     */
    public RtnMethod createRtnMethod() {
        return new RtnMethod();
    }

    /**
     * Create an instance of {@link RtnMethodResponse }
     * 
     */
    public RtnMethodResponse createRtnMethodResponse() {
        return new RtnMethodResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RtnMethodResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceDemo/", name = "rtnMethodResponse")
    public JAXBElement<RtnMethodResponse> createRtnMethodResponse(RtnMethodResponse value) {
        return new JAXBElement<RtnMethodResponse>(_RtnMethodResponse_QNAME, RtnMethodResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RtnMethod }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webserviceDemo/", name = "rtnMethod")
    public JAXBElement<RtnMethod> createRtnMethod(RtnMethod value) {
        return new JAXBElement<RtnMethod>(_RtnMethod_QNAME, RtnMethod.class, null, value);
    }

}
