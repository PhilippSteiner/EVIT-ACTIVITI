/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.BMWJournalist;

import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.login.doLogin;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwEventFacade;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import java.util.ArrayList;
import java.util.Date;
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
public class JournalistPastEventBean {

    private BmwEventController bmwEventController;
    private BmwUserController bmwUserController;
    private BmwParticipantsController bmwParticipantsController;
    
    private List <BmwEvent> journalistPastEvents;

    /**
     * Creates a new instance of JournalistPastEventBean
     */
    public JournalistPastEventBean() {

        bmwEventController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwEventController}", BmwEventController.class);
        
        bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);

        bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);
        
        this.journalistPastEvents = new ArrayList<BmwEvent>();
    }

    public List<BmwEvent> getJournalistPastEvents() {
        
        return this.PastEvents();
    }

    public void setJournalistPastEvents(List<BmwEvent> journalistPastEvents) {
        this.journalistPastEvents = journalistPastEvents;
    }

    
    public List PastEvents(){
    
        doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);
        
        
        System.out.println("User ID: "+currentlogin.getUid());
        
        //1317

        BmwUser cev = bmwUserController.getFacade().find(currentlogin.getUid());

            EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();
            List <BmwParticipants> JournalistParticipants = em.createNamedQuery("BmwParticipants.findByUserId")
                    .setParameter("userId", cev)
                    .getResultList();
            
            System.out.println("Constructor: User Participants Anzahl: " + JournalistParticipants.size());
            
            Date now = new Date();
            
            for(int k = 0; k < JournalistParticipants.size() ; k++){
                
                BmwEvent currentBMWEvent = JournalistParticipants.get(k).getEventId();
                
                System.out.println("Constructor: State of Participant : " + JournalistParticipants.get(k).getPState());
                
                if(currentBMWEvent.getEndEventdate().compareTo(now)>0){
                
                    System.out.println("Event in der Vergangenheit: Add Event : " + currentBMWEvent.getName());
                    
                    journalistPastEvents.add(currentBMWEvent);
                    
                }
               
            }
        
        return journalistPastEvents;
        
    }
}
