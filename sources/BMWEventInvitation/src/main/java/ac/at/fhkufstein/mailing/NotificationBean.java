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

/**
 *
 * @author Philipp
 */
@ManagedBean(name="notification")
@RequestScoped
public class NotificationBean {

    public BmwUserController bmwUserController;
    public EmailTemplatesController templateController;
    public List<BmwUser> bmwUser = null;
    public EmailTemplates emailTemplate = new EmailTemplates();
    /**
     * Creates a new instance of NotificationBean
     */
    public NotificationBean() {
        
        bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
        templateController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{emailTemplateController}", EmailTemplatesController.class);
        
        bmwUser = bmwUserController.getItems();
        //emailTemplate = templateController.getItems().get(0);
        emailTemplate.setEmailContent("Email Addresse: und hier gehts weiter");
        System.out.println("init");
    }
    
    public void test(){
    
        NotificationService.parseTemplate(bmwUser, emailTemplate);
    }
    
    
}
