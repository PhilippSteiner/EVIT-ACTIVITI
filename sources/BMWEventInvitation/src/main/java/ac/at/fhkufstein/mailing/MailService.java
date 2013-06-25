/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.mailing;

import ac.at.fhkufstein.bean.BmwEmailHistoryController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.bean.EmailTemplatesController;
import ac.at.fhkufstein.bean.PersonenController;
import ac.at.fhkufstein.entity.BmwEmailHistory;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.Personen;
import ac.at.fhkufstein.service.MessageService;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwUserFacade;
import ac.at.fhkufstein.session.PersonenFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import javax.mail.internet.InternetAddress;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import java.util.List;
import java.util.Date;
import java.util.Iterator;
import javax.faces.context.FacesContext;

/**
 *
 * @author mike
 */
public class MailService {

    public final static String MAIL_STATUS_SENT = "sent";

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
     *
     * @param to
     * @param subject
     * @param message
     * @return if success it returns true otherwise false
     */
    public static boolean sendMail(String to, String subject, String message, String mailType) {
        try {

            Email mail = initMail();

            mail.addTo(to);

            mail.setSubject(subject);
            //mail.setMsg(message);
            mail.setContent(message, "text/html; charset=utf-8");//Für HTML und Textemails

            mail.send();
            
            MessageService.showInfo("Die Mail wurde gesendet.");

            saveMailInHistory(mail, message, mailType);

            return true;
        } catch (EmailException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);

            MessageService.showError("Die Mail konnte nicht gesendet werden.");
        }

        return false;

    }

    public static void saveMailInHistory(Email mail, String content, String mailType) {

        try {

            BmwEmailHistoryController emailHistoryController = PersistenceService.getManagedBeanInstance(BmwEmailHistoryController.class);
            BmwEmailHistory emailHistory = emailHistoryController.prepareCreate(null);


            try {
                Object uid = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid");
                if (uid != null) {
                    emailHistory.setUserFrom((Integer) uid);
                }
            } catch (java.lang.ClassCastException ex) {
                ex.printStackTrace();
            }

            emailHistory.setEmailFrom(mail.getFromAddress().getAddress());

            if (!mail.getToAddresses().isEmpty()) {

                emailHistory.setEmailTo(getInternetAddressString(mail.getToAddresses()));

                emailHistory.setUserTo(getUserByMail(((InternetAddress) mail.getToAddresses().get(0)).getAddress()));
            }


            emailHistory.setEmailCc(getInternetAddressString(mail.getCcAddresses()));
            emailHistory.setEmailBcc(getInternetAddressString(mail.getBccAddresses()));

            emailHistory.setDatetimeSent(new Date());
            emailHistory.setSentStatus(MAIL_STATUS_SENT);

            emailHistory.setEmailSubject(mail.getSubject());
            emailHistory.setEmailContent(content);

            emailHistory.setEmailType(mailType);

            emailHistoryController.saveNew(null);

            MessageService.showInfo("Die Mail wurde erfolgreich gespeichert.");

        } catch (Exception ex) {
            ex.printStackTrace();

            MessageService.showError("Die Mail konnte nicht gespeichert werden.");
        }

    }

    private static String getInternetAddressString(List<InternetAddress> addresses) {
        StringBuilder addressString = new StringBuilder();

        for (Iterator it = addresses.iterator(); it.hasNext();) {
            if (addressString.length() > 0) {
                addressString.append("; ");
            }
            addressString.append(((InternetAddress) it.next()).getAddress());
        }

        return addressString.toString();
    }

    private static BmwUser getUserByMail(String mailAddress) {

        BmwUserController userController = PersistenceService.getManagedBeanInstance(BmwUserController.class);
        BmwUser user = null;


        try {
            user = ((BmwUserFacade) userController.getFacade()).getEntityManager().createNamedQuery("BmwUser.findByEmail", BmwUser.class)
                    .setParameter("email", mailAddress)
                    .getSingleResult();

            return user;

        } catch (Exception userEx) {

            PersonenController personenController = PersistenceService.getManagedBeanInstance(PersonenController.class);
            Personen person;

            try {
                person = ((PersonenFacade) personenController.getFacade()).getEntityManager().createNamedQuery("Personen.findByMails", Personen.class)
                        .setParameter("eMail1", mailAddress)
                        .setParameter("eMail2", mailAddress)
                        .getSingleResult();

                user = ((BmwUserFacade) userController.getFacade()).getEntityManager().createNamedQuery("BmwUser.findByPersonenID", BmwUser.class)
                        .setParameter("personenID", person)
                        .getSingleResult();

                return user;

            } catch (Exception personEx) {
            }
        }
        return user;
    }
}
