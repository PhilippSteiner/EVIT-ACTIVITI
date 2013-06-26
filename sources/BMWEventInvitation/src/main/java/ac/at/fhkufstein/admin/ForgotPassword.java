/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.admin;

import ac.at.fhkufstein.bean.BmwEmailTemplatesController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwEmailTemplates;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.mailing.NotificationService;
import ac.at.fhkufstein.service.PersistenceService;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Philipp
 */
@ManagedBean(name = "forgotPassword")
@RequestScoped
public class ForgotPassword {

    private BmwUserController userController;
    private String email;
    private List<BmwUser> list;
    private BmwUser[] user;
    private BmwEmailTemplates forgotTemplate;
    private BmwEmailTemplatesController templateController;
    private boolean found = Boolean.TRUE;

    /**
     * Creates a new instance of ForgotPassword
     */
    public ForgotPassword() {

        userController = PersistenceService.getManagedBeanInstance(BmwUserController.class);
        list = userController.getItems();
        templateController = PersistenceService.getManagedBeanInstance(BmwEmailTemplatesController.class);
        forgotTemplate = templateController.getItems().get(2);
    }

    public void send() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        for (BmwUser a : list) {
            if (a.getPersonenID().getEMail1().equals(email)) {
                System.out.println(a.getPersonenID());
                System.out.println(forgotTemplate.getId());
                NotificationService.parseTemplate(a, forgotTemplate);
                facesContext.addMessage(null, new FacesMessage("Passwort vergessen", "Passwort versendet!"));
                found = Boolean.FALSE;
                break;
            }
        }

        if (found) {
            facesContext.addMessage(null, new FacesMessage("Passwort vergessen", "Diese E-mail Adreses ist nicht bekannt!"));
        } else {

            
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
