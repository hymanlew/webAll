package webserviceDemo;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.List;

// 该Service没有Input，只有一个Output，该Output为一个List<String>类型。
@WebService
public class CollectionWS {

    @WebMethod
    public List<String> rtnMethod(){

        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        return list;
    }

    public static void main(String[] args) {

        Endpoint.publish("http://127.0.0.1/collection",new CollectionWS());
        System.out.println("Publish Success");
    }
}

/**
 * http://127.0.0.1/collection?wsdl
 */
