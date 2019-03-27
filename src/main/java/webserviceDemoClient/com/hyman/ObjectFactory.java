
package webserviceDemoClient.com.hyman;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.hyman package. 
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

    private final static QName _Transword_QNAME = new QName("http://www.hyman.com", "transword");
    private final static QName _TranswordResponse_QNAME = new QName("http://www.hyman.com", "transwordResponse");
    private final static QName _DotestResponse_QNAME = new QName("http://www.hyman.com", "dotestResponse");
    private final static QName _Dotest_QNAME = new QName("http://www.hyman.com", "dotest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.hyman
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DotestResponse }
     * 
     */
    public DotestResponse createDotestResponse() {
        return new DotestResponse();
    }

    /**
     * Create an instance of {@link Transword }
     * 
     */
    public Transword createTransword() {
        return new Transword();
    }

    /**
     * Create an instance of {@link TranswordResponse }
     * 
     */
    public TranswordResponse createTranswordResponse() {
        return new TranswordResponse();
    }

    /**
     * Create an instance of {@link Dotest }
     * 
     */
    public Dotest createDotest() {
        return new Dotest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Transword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hyman.com", name = "transword")
    public JAXBElement<Transword> createTransword(Transword value) {
        return new JAXBElement<Transword>(_Transword_QNAME, Transword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TranswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hyman.com", name = "transwordResponse")
    public JAXBElement<TranswordResponse> createTranswordResponse(TranswordResponse value) {
        return new JAXBElement<TranswordResponse>(_TranswordResponse_QNAME, TranswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DotestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hyman.com", name = "dotestResponse")
    public JAXBElement<DotestResponse> createDotestResponse(DotestResponse value) {
        return new JAXBElement<DotestResponse>(_DotestResponse_QNAME, DotestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Dotest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hyman.com", name = "dotest")
    public JAXBElement<Dotest> createDotest(Dotest value) {
        return new JAXBElement<Dotest>(_Dotest_QNAME, Dotest.class, null, value);
    }

}
