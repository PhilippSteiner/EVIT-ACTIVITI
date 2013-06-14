/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.mailing;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mike
 */
@XmlRootElement(name = "MailConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class MailConfig {

    @XmlElement(name = "hostname")
    private String hostname;
    @XmlElement(name = "smtpPort")
    private int smtpPort;
    @XmlElement(name = "senderMailAddress")
    private String senderMailAddress;

    private static MailConfig instance;

    /**
     * Creates a new instance of MailConfig
     */
    private MailConfig() {
    }

    public static MailConfig getInstance() {
        if(instance == null) {
            instance = new MailConfig();
        }
        return instance;
    }

    /**
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @param hostname the hostname to set
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * @return the smtpPort
     */
    public int getSmtpPort() {
        return smtpPort;
    }

    /**
     * @param smtpPort the smtpPort to set
     */
    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    /**
     * @return the senderMailAddress
     */
    public String getSenderMailAddress() {
        return senderMailAddress;
    }

    /**
     * @param senderMailAddress the senderMailAddress to set
     */
    public void setSenderMailAddress(String senderMailAddress) {
        this.senderMailAddress = senderMailAddress;
    }
}
