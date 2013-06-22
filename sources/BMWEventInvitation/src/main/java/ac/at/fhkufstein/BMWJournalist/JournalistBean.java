/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.BMWJournalist;

import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwFlightController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwFlight;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.login.doLogin;
import ac.at.fhkufstein.login.login;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwEventFacade;
import ac.at.fhkufstein.session.BmwFlightFacade;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

@ManagedBean(name = "JournalistBean")
@SessionScoped
//@PersistenceContext
public class JournalistBean {

    private BmwUserController bmwUserController;
    private BmwParticipantsController bmwParticipantsController;
    private BmwFlightController bmwFlightController;
    private BmwEventController bmwEventController;
    private BmwUser cev;
    private List JournalistParticipants;
    private List<BmwEvent> journalistEvents;
    //private JournalistEvent selectedEvent;
    private BmwEvent selectedBmwEvent;
    private Integer UserID;
    private String von;
    private String bis;
    //private String status;
    //select stuff
    private List<SelectItem> auswahlmoeglichkeiten;
    private List<SelectItem> verfuegbareFluege;
    private String auswahl;
    private String flugauswahl;

    public JournalistBean() {

        //some inits


        bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);

        bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

        auswahlmoeglichkeiten = new ArrayList<SelectItem>();
        verfuegbareFluege = new ArrayList<SelectItem>();

        auswahlmoeglichkeiten.add(new SelectItem("Zusagen"));
        auswahlmoeglichkeiten.add(new SelectItem("Absagen"));
        auswahlmoeglichkeiten.add(new SelectItem("Vertretung schicken"));
        
        //FacesContext facesContext = FacesContext.getCurrentInstance();
        //this.UserID = (Integer) Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("eventID"));
        doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);
        
        this.UserID = currentlogin.getUid();
        
        System.out.println("User ID: "+this.UserID);
        
        //1317

        cev = bmwUserController.getFacade().find(UserID);

        try {

            EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();
            JournalistParticipants = em.createNamedQuery("BmwParticipants.findByUserId")
                    .setParameter("userId", cev)
                    .getResultList();
            
            System.out.println("Constructor: User Participants Anzahl: " + JournalistParticipants.size());

            Iterator<BmwParticipants> it = JournalistParticipants.iterator();

            this.journalistEvents = new ArrayList<BmwEvent>();

            while (it.hasNext()) {

                BmwEvent currentBMWEvent = it.next().getEventId();

                System.out.println("Constructor: JournalistenEvent : " + currentBMWEvent.getName());

                this.journalistEvents.add(currentBMWEvent);

            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List getJournalistParticipants() {
        return JournalistParticipants;
    }

    public void setJournalistParticipants(List JournalistParticipants) {
        this.JournalistParticipants = JournalistParticipants;
    }

    public List<BmwEvent> getJournalistEvents() {
        return journalistEvents;
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

    public List<SelectItem> getVerfuegbareFluege() {
        return verfuegbareFluege;
    }

    public void setVerfuegbareFluege(List<SelectItem> verfuegbareFluege) {
        this.verfuegbareFluege = verfuegbareFluege;
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

        //this.getAllFlightsForEvent();

        return "eventdetail";

    }

    public void einladungBeantworten() {

        System.out.println("Einladung beant mit: " + this.auswahl);

    }

    public void autoWaehlen() {

        System.out.println("+++++++++++++++++++++++++autowählen");
    }

    public void flugwaehlen() {

        System.out.println("++++++++++++++++++++++++++flugwählen mit flug: " + this.flugauswahl);


    }

    public void getAllFlightsForEvent() {

        this.bmwFlightController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwFlightController}", BmwFlightController.class);

        EntityManager em = ((BmwFlightFacade) bmwFlightController.getFacade()).getEntityManager();



        List<BmwFlight> currentFlightList = em.createNamedQuery("BmwFlight.findByEventId")
                .setParameter("eventId", this.selectedBmwEvent)
                .getResultList();

        System.out.println("Flights " + currentFlightList.size());


        for (int i = 0; i < currentFlightList.size(); i++) {
            String dateString = "" + currentFlightList.get(i).getDepartureTime().getDay() + "." + currentFlightList.get(i).getDepartureTime().getMonth() + "." + currentFlightList.get(i).getDepartureTime().getYear();
            String addstring = "" + currentFlightList.get(i).getDepartureLocation() + "-" + currentFlightList.get(i).getArrivalLocation() + ", " + dateString;

            this.verfuegbareFluege.add(new SelectItem(addstring));

        }
    }
    
        
        public void stateChangeListener(ValueChangeEvent event) {
        
        System.out.println("Listener Beantwortet mit: "+this.auswahl);
    
    }

    

}
