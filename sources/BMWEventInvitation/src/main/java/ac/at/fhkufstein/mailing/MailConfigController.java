/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.mailing;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author mike
 */
@ManagedBean
@ViewScoped
public class MailConfigController {

    private static String resourceFolder = MailConfigController.class.getResource("/"+MailConfigController.class.getName().replace(".", "/").replace(MailConfigController.class.getSimpleName(), "")).getFile();
    private final static String FILE = "mailconfig.xml";

    /**
     * Creates a new instance of MailConfigController
     */
    public MailConfigController() {
    }

    public static void marshalConfig(MailConfig config) {
        try {
            JAXBContext context = JAXBContext.newInstance(MailConfig.class);

            Marshaller marshaller = context.createMarshaller();

            marshaller.marshal(config, new File(resourceFolder+FILE));

        System.out.println("Mailconfiguration marhsalled");
        } catch (JAXBException ex) {
            Logger.getLogger(MailConfigController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static MailConfig unmarshalConfig() {

        MailConfig mailConfig = null;

        try {
            JAXBContext context = JAXBContext.newInstance(MailConfig.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            mailConfig = (MailConfig) unmarshaller.unmarshal(new File(resourceFolder+FILE));

        } catch (JAXBException ex) {
            Logger.getLogger(MailConfigController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mailConfig;
    }

    /**
     * @return the mailConfig
     */
    public MailConfig getMailConfig() {
        return MailConfig.getInstance();
    }

    /**
     * @param mailConfig the mailConfig to set
     */
    public void setMailConfig(MailConfig mailConfig) {
        marshalConfig(mailConfig);
    }

    public void save() {
        marshalConfig(MailConfig.getInstance());
        System.out.println("Mailconfiguration saved to "+resourceFolder+FILE);
    }
}
