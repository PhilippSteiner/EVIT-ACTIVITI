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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Philipp
 */
@ManagedBean(name = "sendLogin")
@RequestScoped
public class SendLogin {

    private BmwUser[] selected = null;
    private BmwEmailTemplates loginTemplate;
    private BmwUserController bmwUserController;
    private BmwEmailTemplatesController templateController;

    /**
     * Creates a new instance of SendLogin
     */
    public SendLogin() {

        bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
        templateController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwEmailTemplatesController}", BmwEmailTemplatesController.class);
       
        loginTemplate = templateController.getItems().get(0);
                
    }

    public void sendLogin() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selected.length == 0) {
            context.addMessage(null, new FacesMessage("Registration versenden", "Keinen Benutzer ausgewählt!"));
        } else {

            NotificationService.parseTemplate(selected, loginTemplate);
            context.addMessage(null, new FacesMessage("Registration versenden", "E-mail erfolgreich versendet!"));
            for(BmwUser a:selected){
                a.setLoginSent(Boolean.TRUE);
                bmwUserController.setSelected(a);
                bmwUserController.save(null);
                System.out.println("True");
            }
        }
        

    }

    public BmwUser[] getSelected() {
        return selected;
    }

    public void setSelected(BmwUser[] selected) {
        this.selected = selected;
    }
}
