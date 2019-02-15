
package WebserviceSourceDemo.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UploadBlacklist_WokerResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "uploadBlacklistWokerResult"
})
@XmlRootElement(name = "UploadBlacklist_WokerResponse")
public class UploadBlacklistWokerResponse {

    @XmlElement(name = "UploadBlacklist_WokerResult")
    protected String uploadBlacklistWokerResult;

    /**
     * ��ȡuploadBlacklistWokerResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUploadBlacklistWokerResult() {
        return uploadBlacklistWokerResult;
    }

    /**
     * ����uploadBlacklistWokerResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUploadBlacklistWokerResult(String value) {
        this.uploadBlacklistWokerResult = value;
    }

}
