/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.BMWJournalist;

import ac.at.fhkufstein.bean.BmwEmailHistoryController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwEmailHistory;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.login.doLogin;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwEmailHistoryFacade;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author Alex
 */
@ManagedBean
@RequestScoped
public class JournalistEmailBean {
    
    private BmwUser cev;
    private BmwUserController bmwUserController;
    private BmwEmailHistoryController bmwEmailHistoryController;
    private List <BmwEmailHistory>userEmails;
    private BmwEmailHistory selectedEmails;

    
    public JournalistEmailBean() {
        
        bmwEmailHistoryController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwEmailHistoryController}", BmwEmailHistoryController.class);
        
        bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
        
    }

    public BmwUser getCev() {
        return cev;
    }

    public void setCev(BmwUser cev) {
        this.cev = cev;
    }

    public List<BmwEmailHistory> getUserEmails() {
        
        this.getAllUserEmails();
        
        return userEmails;
    }

    public void setUserEmails(List<BmwEmailHistory> userEmails) {
        this.userEmails = userEmails;
    }

    public BmwEmailHistory getSelectedEmails() {
        return selectedEmails;
    }

    public void setSelectedEmails(BmwEmailHistory selectedEmails) {
        this.selectedEmails = selectedEmails;
    }
    
    
    public void getAllUserEmails(){
    
            doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);
        
            System.out.println("User ID: "+currentlogin.getUid());

            this.cev = this.bmwUserController.getFacade().find(currentlogin.getUid());
            
            System.out.println("Emails for User: "+this.cev.getUid());


            EntityManager em = ((BmwEmailHistoryFacade) bmwEmailHistoryController.getFacade()).getEntityManager();
            
            this.userEmails = em.createNamedQuery("BmwEmailHistory.findByUserTo")
                    .setParameter("userTo", cev)
                    .getResultList();
            
            System.out.println("Constructor: User Participants Anzahl: " + userEmails.size());

    }
    
}
