package webserviceDemoClient.collection;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.List;

public class MyClient {
    public static void main(String[] args) throws IOException {
        CollectionWSService service = new CollectionWSService();
        CollectionWS ws = service.getCollectionWSPort();
        List<String> list = ws.rtnMethod();
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }

        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("http://127.0.0.1/collection?wsdl");
        int state = client.executeMethod(postMethod);
        System.out.println(state);

    }
}
