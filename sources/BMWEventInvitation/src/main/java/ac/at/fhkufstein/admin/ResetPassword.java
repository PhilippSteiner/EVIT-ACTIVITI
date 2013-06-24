/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.admin;

import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.EmailTemplates;
import ac.at.fhkufstein.mailing.NotificationService;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author Philipp
 * Managed Bean zum zurücksetzen des Passwortes einzelner bzw. mehrerer BMWUser und aussendemöglickeit via E-Mail
 */
@ManagedBean(name="resetPassword")
@RequestScoped
public class ResetPassword implements Serializable{

    private BmwUserController bmwUserController;
    private BmwUser[] selected;
    private EmailTemplates resetPasswordTemplate = new EmailTemplates();
    private boolean sendemail;
    
    /**
     * Creates a new instance of ResetPassword
     */
    public ResetPassword() {
        
        bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
    
        resetPasswordTemplate.setSubject("Password Reset");
        resetPasswordTemplate.setType("Password Reset");
        resetPasswordTemplate.setEmailContent("<html><body>Sehr geehrte Damen und Herren,<div><br/></div><div>Anbei befindet sich Ihr neues Passwort und ihr Benutzername.</div><div><br/></div><div>Benutzer: $email$</div><div>Passwort: $password$</div><div><br/></div><div>Mit freundlichen Grüßen,</div><div>BMW Group Austria</div></body></html>");
    
    }
    
    
    public void resetSendPassword(){
            for(BmwUser a: selected){
                System.out.println(a.getPersonenID());
                a.setPwd(getRandomPassword());
                bmwUserController.setSelected(a);
                bmwUserController.save(null);
                System.out.println("Passwort zurückgesetzt");
                
                
            }
            
            if(sendemail){
                    NotificationService.parseTemplate(selected, resetPasswordTemplate);
                }
        
        }

       
    
    
    
    public String getRandomPassword() {
        StringBuilder password = new StringBuilder(8);
        password.append(RandomStringUtils.randomAlphanumeric(8));
        return password.toString();
    }

    public boolean isSendemail() {
        return sendemail;
    }

    public void setSendemail(boolean sendemail) {
        this.sendemail = sendemail;
    }
    
    

    public BmwUser[] getSelected() {
        return selected;
    }

    public void setSelected(BmwUser[] selected) {
        this.selected = selected;
    }
    
    
}
