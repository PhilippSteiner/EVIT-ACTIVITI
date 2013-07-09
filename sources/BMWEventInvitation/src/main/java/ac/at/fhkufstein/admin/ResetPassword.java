/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.admin;

import ac.at.fhkufstein.bean.BmwEmailTemplatesController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwEmailTemplates;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.EmailTemplates;
import ac.at.fhkufstein.mailing.NotificationService;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
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
    
    private boolean sendemail;
    private BmwEmailTemplates resetTemplate;
    private BmwEmailTemplatesController templateController;
    
    /**
     * Creates a new instance of ResetPassword
     */
    public ResetPassword() {
        
        bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
        templateController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwEmailTemplatesController}", BmwEmailTemplatesController.class);
        
        resetTemplate = templateController.getItems().get(1);
    }
    
    
    public void resetSendPassword(){
        FacesContext context = FacesContext.getCurrentInstance();

        if (selected.length == 0) {
            context.addMessage(null, new FacesMessage("Passwort zurücksetzen", "Keinen Benutzer ausgewählt!"));
        } else {   
        
        for(BmwUser a: selected){
                System.out.println(a.getPersonenID());
                a.setPwd(getRandomPassword());
                bmwUserController.setSelected(a);
                bmwUserController.save(null);
                System.out.println("Passwort zurückgesetzt");
                context.addMessage(null, new FacesMessage("Passwort zurücksetzen", "Passwort zurückgesetzt!"));
                
            }
            
            if(sendemail){
                    NotificationService.parseTemplate(selected, resetTemplate);
                    context.addMessage(null, new FacesMessage("Passwort zurücksetzen", "Passwort versendet!"));
                }
        
        }

       
    
    }
    
    public static String getRandomPassword() {
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
