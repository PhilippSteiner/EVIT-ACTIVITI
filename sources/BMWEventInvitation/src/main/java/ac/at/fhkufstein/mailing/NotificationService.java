/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.mailing;

import ac.at.fhkufstein.entity.BmwEmailTemplates;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.EmailTemplates;
import java.util.List;
import org.antlr.stringtemplate.StringTemplate;

/**
 *
 * @author Philipp
 * Diese Klasse wird verwendet um StringTemplates zu parsen und anschließend zu versenden.
 */
public class NotificationService {

    public static void parseTemplate(List<BmwUser> b, EmailTemplates e) {

        for (BmwUser u : b) {
            
            StringTemplate template = new StringTemplate(e.getEmailContent());
            template.setAttribute("email", u.getPersonenID().getEMail1());
            template.setAttribute("password", u.getPwd());
            template.setAttribute("username", u.getUsername());
            template.setAttribute("vorname", u.getPersonenID().getVorname());
            template.setAttribute("nachname", u.getPersonenID().getNachname());
            template.setAttribute("briefanrede", u.getPersonenID().getBriefanredeSie());
            String mailcontent = template.toString();
            
            MailService.sendMail(u.getPersonenID().getEMail1(), e.getSubject(), mailcontent, e.getType());
        }
    }
    
    public static void parseTemplate(BmwUser[] b, EmailTemplates e) {

        for (BmwUser u : b) {
            
            StringTemplate template = new StringTemplate(e.getEmailContent());
            template.setAttribute("email", u.getPersonenID().getEMail1());
            template.setAttribute("password", u.getPwd());
            template.setAttribute("username", u.getUsername());
            template.setAttribute("vorname", u.getPersonenID().getVorname());
            template.setAttribute("nachname", u.getPersonenID().getNachname());
            template.setAttribute("briefanrede", u.getPersonenID().getBriefanredeSie());
            //System.out.println(template.toString());
            String mailcontent = template.toString();
            
            MailService.sendMail(u.getPersonenID().getEMail1(), e.getSubject(), mailcontent, e.getType());
        }
    }
    
    public static void parseTemplate(BmwUser[] b, BmwEmailTemplates e) {

        for (BmwUser u : b) {
            
            StringTemplate template = new StringTemplate(e.getEmailContent());
            template.setAttribute("email", u.getPersonenID().getEMail1());
            template.setAttribute("password", u.getPwd());
            template.setAttribute("username", u.getUsername());
            template.setAttribute("vorname", u.getPersonenID().getVorname());
            template.setAttribute("nachname", u.getPersonenID().getNachname());
            template.setAttribute("briefanrede", u.getPersonenID().getBriefanredeSie());
            //System.out.println(template.toString());
            String mailcontent = template.toString();
            
            MailService.sendMail(u.getPersonenID().getEMail1(), e.getSubject(), mailcontent, e.getType());
        }
    }
    
    
    public static void parseTemplate(List<BmwUser> b, BmwEmailTemplates e) {

        for (BmwUser u : b) {
            
            StringTemplate template = new StringTemplate(e.getEmailContent());
            template.setAttribute("email", u.getPersonenID().getEMail1());
            template.setAttribute("password", u.getPwd());
            template.setAttribute("username", u.getUsername());
            template.setAttribute("vorname", u.getPersonenID().getVorname());
            template.setAttribute("nachname", u.getPersonenID().getNachname());
            template.setAttribute("briefanrede", u.getPersonenID().getBriefanredeSie());
            //System.out.println(template.toString());
            String mailcontent = template.toString();
            
            MailService.sendMail(u.getPersonenID().getEMail1(), e.getSubject(), mailcontent, e.getType());
        }
    }
    
    public static void parseTemplate(BmwUser u, BmwEmailTemplates e) {

            
            StringTemplate template = new StringTemplate(e.getEmailContent());
            template.setAttribute("email", u.getPersonenID().getEMail1());
            template.setAttribute("password", u.getPwd());
            template.setAttribute("username", u.getUsername());
            template.setAttribute("vorname", u.getPersonenID().getVorname());
            template.setAttribute("nachname", u.getPersonenID().getNachname());
            template.setAttribute("briefanrede", u.getPersonenID().getBriefanredeSie());
            String mailcontent = template.toString();
            
            MailService.sendMail(u.getPersonenID().getEMail1(), e.getSubject(), mailcontent, e.getType());
        
    }
    
    
}
