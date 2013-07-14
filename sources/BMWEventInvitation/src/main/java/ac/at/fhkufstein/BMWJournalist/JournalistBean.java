/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.BMWJournalist;

import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.login.doLogin;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "JournalistBean")
@SessionScoped
public class JournalistBean {

    private BmwUserController bmwUserController;
    private BmwParticipantsController bmwParticipantsController;
    private List <BmwParticipants>JournalistParticipants;
    private List<BmwEvent> journalistEvents;
    private BmwEvent selectedBmwEvent;
    private String von;
    private String bis;
    private List<SelectItem> auswahlmoeglichkeiten;
    private String auswahl;
    private String flugauswahl;
 
    private StreamedContent dbImage;

    public JournalistBean() {

        bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);

        bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

        auswahlmoeglichkeiten = new ArrayList<SelectItem>();

        auswahlmoeglichkeiten.add(new SelectItem("Zusagen"));
        auswahlmoeglichkeiten.add(new SelectItem("Absagen"));
        auswahlmoeglichkeiten.add(new SelectItem("Vertretung schicken"));
        
    }

    public List getJournalistParticipants() {
        return JournalistParticipants;
    }

    public void setJournalistParticipants(List JournalistParticipants) {
        this.JournalistParticipants = JournalistParticipants;
    }

    public List<BmwEvent> getJournalistEvents() {
             
        return this.PreEvents();
    }

    public void setJournalistEvents(List<BmwEvent> journalistEvents) {
        this.journalistEvents = journalistEvents;
    }

    public BmwEvent getSelectedBmwEvent() {
        return selectedBmwEvent;
    }

    public void setSelectedBmwEvent(BmwEvent selectedBmwEvent) {
        this.selectedBmwEvent = selectedBmwEvent;
    }

    public String getVon() {
        return von;
    }

    public void setVon(String von) {
        this.von = von;
    }

    public String getBis() {
        return bis;
    }

    public void setBis(String bis) {
        this.bis = bis;
    }

    public List<SelectItem> getAuswahlmoeglichkeiten() {
        return auswahlmoeglichkeiten;
    }

    public void setAuswahlmoeglichkeiten(List<SelectItem> auswahlmoeglichkeiten) {
        this.auswahlmoeglichkeiten = auswahlmoeglichkeiten;
    }

   
    public String getAuswahl() {
        return auswahl;
    }

    public void setAuswahl(String auswahl) {
        this.auswahl = auswahl;
        
    }

    public String getFlugauswahl() {
        return flugauswahl;
    }

    public void setFlugauswahl(String flugauswahl) {
        this.flugauswahl = flugauswahl;
    }

    public StreamedContent getDbImage() {
        
        InputStream dbStream = getClass().getClassLoader().getResourceAsStream("C:\\BMW_Bilder\test.png");
        
        this.dbImage = new DefaultStreamedContent(dbStream, "image/png");
        
        return dbImage;
    }

    public void setDbImage(StreamedContent dbImage) {
        this.dbImage = dbImage;
    }
    
    

    //######################################################################################################################################
    //######################################################################################################################################
    //######################################################################################################################################
    //######################################################################################################################################
    //######################################################################################################################################
    //######################################################################################################################################
    //######################################################################################################################################
    //######################################################################################################################################
    
    public String didSelectEventRow() {

        System.out.println("Selected Event from did select: " + this.selectedBmwEvent.getName());

        this.von = "" + this.selectedBmwEvent.getStartEventdate().getDay() + "." + this.selectedBmwEvent.getStartEventdate().getMonth();
        this.bis = "" + this.selectedBmwEvent.getEndEventdate().getDay() + "." + this.selectedBmwEvent.getEndEventdate().getMonth() + "." + this.selectedBmwEvent.getEndEventdate().getYear();

        ResponseBean responseBean = PersistenceService.getManagedBeanInstance(ResponseBean.class);
        
        responseBean.setEventdetailview("eventdetail");
        
        return "eventdetailframe";

    }
    
        public String didSelectPastEventRow() {

        System.out.println("Selected Event from did select: " + this.selectedBmwEvent.getName());

        this.von = "" + this.selectedBmwEvent.getStartEventdate().getDay() + "." + this.selectedBmwEvent.getStartEventdate().getMonth();
        this.bis = "" + this.selectedBmwEvent.getEndEventdate().getDay() + "." + this.selectedBmwEvent.getEndEventdate().getMonth() + "." + this.selectedBmwEvent.getEndEventdate().getYear();
        
        return "eventsolo";

    }


        public void stateChangeListener(ValueChangeEvent event) {
        
        System.out.println("Listener Beantwortet mit: "+this.auswahl);
    
    }

    public List<BmwEvent> PreEvents(){
        
        this.journalistEvents = new ArrayList<BmwEvent>();
        
        if(!(this.journalistEvents.isEmpty())) {
        
            this.journalistEvents.clear();
            
        }
    
        doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);
        
        System.out.println("User ID: "+currentlogin.getUid());
        
        //1317

        BmwUser cev = bmwUserController.getFacade().find(currentlogin.getUid());

            EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();
            List <BmwParticipants> JournalistParticipants = em.createNamedQuery("BmwParticipants.findByUserId")
                    .setParameter("userId", cev)
                    .getResultList();
            
            System.out.println("Constructor: User Participants Anzahl: " + JournalistParticipants.size());
            
            Date d = new Date(System.currentTimeMillis());
            
            for(int k = 0; k < JournalistParticipants.size() ; k++){
                
                BmwEvent currentBMWEvent = JournalistParticipants.get(k).getEventId();
                
                System.out.println("Constructor: State of Participant : " + JournalistParticipants.get(k).getPState());
                
                if(currentBMWEvent.getEndEventdate().compareTo(d)>0){
                
                    System.out.println("Event in der Vergangenheit: Add Event : " + currentBMWEvent.getName());
                    
                    this.journalistEvents.add(currentBMWEvent);
                    
                }
               
            }
        
        return this.journalistEvents;
        
    }
}
