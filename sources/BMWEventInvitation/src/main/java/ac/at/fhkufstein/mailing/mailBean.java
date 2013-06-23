/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.mailing;

import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.bean.EmailTemplatesController;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.EmailTemplates;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import ac.at.fhkufstein.mailing.MailType;

/**
 *
 * @author Philipp
 */
@ManagedBean(name="mailBean")
@RequestScoped
public class mailBean extends MailService{

    private EmailTemplatesController emailcontroller;
    private BmwUserController bmwusercontroller;
    public String to;
    public String subject;
    public String message;
    public String type;
    public EmailTemplates emailTemplate;

    public EmailTemplates getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(EmailTemplates emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public BmwUser[] selected;
    
    public MailType[] getTypes(){
        
        return MailType.values();
    }

    public BmwUser[] getSelected() {
        return selected;
    }

    public void setSelected(BmwUser[] selected) {
        this.selected = selected;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Creates a new instance of mailBean
     */
    public mailBean() {
        emailcontroller = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{emailTemplatesController}", EmailTemplatesController.class);
        
    }

    public List<EmailTemplates> getEmails(){
        return emailcontroller. getItems();
    }

    public void send(){
        MailService.sendMail(to, subject, message, type);
    }
    
    
}
