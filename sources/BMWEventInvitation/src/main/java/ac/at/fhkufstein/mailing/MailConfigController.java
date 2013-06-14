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

    /**
     * Creates a new instance of MailConfigController
     */
    public MailConfigController() {
    }

    public void marshalConfig(MailConfig config) {
        try {
            JAXBContext context = JAXBContext.newInstance(MailConfig.class);

            Marshaller marshaller = context.createMarshaller();

            marshaller.marshal(config, new File("ac/at/fhkufstein/mailing/mailconfig.xml"));

        } catch (JAXBException ex) {
            Logger.getLogger(MailConfigController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public MailConfig unmarshalConfig() {

        MailConfig mailConfig = MailConfig.getInstance();

        try {
            JAXBContext context = JAXBContext.newInstance(MailConfig.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            mailConfig = (MailConfig) unmarshaller.unmarshal(new File("ac/at/fhkufstein/mailing/mailconfig.xml"));

        } catch (JAXBException ex) {
            Logger.getLogger(MailConfigController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mailConfig;
    }

    /**
     * @return the mailConfig
     */
    public MailConfig getMailConfig() {
        return unmarshalConfig();
    }

    /**
     * @param mailConfig the mailConfig to set
     */
    public void setMailConfig(MailConfig mailConfig) {
        marshalConfig(mailConfig);
    }

    public void save() {
        marshalConfig(MailConfig.getInstance());
    }
}
