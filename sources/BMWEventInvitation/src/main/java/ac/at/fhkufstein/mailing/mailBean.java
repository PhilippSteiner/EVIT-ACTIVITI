/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.mailing;

import ac.at.fhkufstein.bean.EmailTemplatesController;
import ac.at.fhkufstein.entity.EmailTemplates;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Philipp
 */
@ManagedBean(name="mailBean")
@RequestScoped
public class mailBean extends MailService{

    private EmailTemplatesController emailcontroller;
    public String to;
    public String subject;
    public String message;

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


        MailService.sendMail(to, subject, message, MailType.MAIL_TYPE_TEST);
    }

}
