/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.BMWJournalist;

import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.login.doLogin;
import ac.at.fhkufstein.service.PersistenceService;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Philipp
 */
@ManagedBean(name = "journalisteinstellungen")
@RequestScoped
public class JournalistEinstellungen {

    private Integer userID;
    private String oldpw, newpw, newpwb;
    private BmwUserController userController;
    private BmwUser bmwUser;

    /**
     * Creates a new instance of JournalistEinstellungen
     */
    public JournalistEinstellungen() {

        userController = PersistenceService.getManagedBeanInstance(BmwUserController.class);
        doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);
        userID = currentlogin.getUid();


    }

    public void changePassword() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        bmwUser = userController.getFacade().find(userID);
        System.out.println(bmwUser.getPwd());
        System.out.println(oldpw);
        if (bmwUser.getPwd().toString().equals(oldpw)) {
            if (newpw.equals(newpwb)){
                bmwUser.setPwd(newpw);
                userController.setSelected(bmwUser);
                userController.save(null);
                facesContext.addMessage(null, new FacesMessage("Passwort zurücksetzen", "Passwort geändert!"));
            } else {
                facesContext.addMessage(null, new FacesMessage("Passwort zurücksetzen", "Passwörter stimmen nicht überein!"));
            }
        } else{
        facesContext.addMessage(null, new FacesMessage("Passwort zurücksetzen", "Falsches Passwort!"));
        }

    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getOldpw() {
        return oldpw;
    }

    public void setOldpw(String oldpw) {
        this.oldpw = oldpw;
    }

    public String getNewpw() {
        return newpw;
    }

    public void setNewpw(String newpw) {
        this.newpw = newpw;
    }

    public String getNewpwb() {
        return newpwb;
    }

    public void setNewpwb(String newpwb) {
        this.newpwb = newpwb;
    }
}
