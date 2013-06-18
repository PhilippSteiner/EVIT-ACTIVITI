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
    public EmailTemplates email = new EmailTemplates();
    private String emailto = "philipp.steiner@snw.at";
    
    
    
    public EmailTemplates getEmail() {
        return email;
    }

    public void setEmail(EmailTemplates email) {
        this.email = email;
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
        
        super.sendMail(emailto, emailcontroller.getSelected().getSubject(), emailcontroller.getSelected().getEmailContent());
    }
    
}
