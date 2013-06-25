/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.BMWJournalist;

import ac.at.fhkufstein.login.doLogin;
import ac.at.fhkufstein.service.PersistenceService;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Philipp
 */
@ManagedBean(name="journalisteinstellungen")
@RequestScoped
public class JournalistEinstellungen {

        private Integer userID;
        private String oldpw;
    /**
     * Creates a new instance of JournalistEinstellungen
     */
    public JournalistEinstellungen() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);
        userID = currentlogin.getUid();
    }
    
    
    
    public void changePassword(){
        
        
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
    
    
    
    
}
