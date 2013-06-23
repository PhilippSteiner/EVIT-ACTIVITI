/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.admin;

import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.EmailTemplates;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author Philipp
 */
@ManagedBean(name="resetPassword")
@RequestScoped
public class ResetPassword implements Serializable{

    private BmwUserController bmwUserController;
    private BmwUser[] selected;
    private EmailTemplates resetPasswordTemplate = new EmailTemplates();
    
    /**
     * Creates a new instance of ResetPassword
     */
    public ResetPassword() {
        
        bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
    
        resetPasswordTemplate.setSubject("Password Reset");
        resetPasswordTemplate.setType("Password Reset");
        resetPasswordTemplate.setEmailContent("Sehr geehrte Damen und Herren, Anbei ihr Passwort: $password$");
    
    }
    
    
    public void resetSendPassword(){
            for(BmwUser a: selected){
                System.out.println(a.getPersonenID());
                a.setPwd(getRandomPassword());
                bmwUserController.setSelected(a);
                bmwUserController.save(null);
            }
        
        }

       
    
    
    
    public String getRandomPassword() {
        StringBuilder password = new StringBuilder(8);
        password.append(RandomStringUtils.randomAlphanumeric(8));
        return password.toString();
    }
    
    

    public BmwUser[] getSelected() {
        return selected;
    }

    public void setSelected(BmwUser[] selected) {
        this.selected = selected;
    }
    
    
}
