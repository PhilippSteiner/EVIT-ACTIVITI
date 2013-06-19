/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.mailing;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author mike
 */
public class MailService {

    private static Email initMail() throws EmailException {

        MailConfig config = MailConfig.getInstance();
        Email mail = new SimpleEmail();
        mail.setHostName(config.getHostname()); //mail.wi.fh-kufstein.ac.at
        mail.setSmtpPort(config.getSmtpPort());

        // if Authentification is required
//        email.setAuthenticator(new DefaultAuthenticator("username", "password"));
//        email.setSSLOnConnect(true);

        mail.setFrom(config.getSenderMailAddress());

        return mail;
    }

    private static void addTos(Email mail, String[] tos) throws EmailException {
        for (String to : tos) {
            mail.addTo(to);
        }
    }

    /**
     * send Mail
     * @param to
     * @param subject
     * @param message
     * @return if success it returns true otherwise false
     */
    public static boolean sendMail(String to, String subject, String message) {
        try {
            
            Email mail = initMail();

            mail.addTo(to);

            mail.setSubject(subject);
            mail.setMsg(message);

            mail.send();
            return true;
        } catch (EmailException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }
}
