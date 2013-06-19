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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

@ManagedBean(name = "JournalistBean")
@RequestScoped
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
    private List PartipantsStati;
    private List EventFlightListString;
    private String status;
    private String einladungauswahl;
    private String flugauswahl;
    private Map<String, String> einladungsauswahlmöglichkeiten = new HashMap<String, String>();

    public JournalistBean() {

        
        einladungsauswahlmöglichkeiten.put("Vertretung", "Vertretung");
        einladungsauswahlmöglichkeiten.put("Absagen", "Absagen");
        einladungsauswahlmöglichkeiten.put("Zusagen", "Zusagen");
        einladungsauswahlmöglichkeiten.put("Bitte Auswählen", "Bitte Auswählen");
        

        bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);

        bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

        //FacesContext facesContext = FacesContext.getCurrentInstance();
        //this.UserID = (Integer) Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("eventID"));
        this.UserID = 1317;
        //1317

        cev = bmwUserController.getFacade().find(UserID);

        try {

            EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();
            JournalistParticipants = em.createNamedQuery("BmwParticipants.findByUserId")
                    .setParameter("userId", cev)
                    .getResultList();
            System.out.println("fuckin size" + JournalistParticipants.size());

            Iterator<BmwParticipants> it = JournalistParticipants.iterator();

            this.journalistEvents = new ArrayList<BmwEvent>();
            this.EventFlightListString = new ArrayList<String>();

            while (it.hasNext()) {

                BmwEvent currentBMWEvent = it.next().getEventId();

                System.out.println("fuking event name: " + currentBMWEvent.getName() + "at");

                /*String von = "" + currentBMWEvent.getStartEventdate().getDay() + "" + currentBMWEvent.getStartEventdate().getMonth() + "" + currentBMWEvent.getStartEventdate().getYear();
                 String bis = "" + currentBMWEvent.getEndEventdate().getDay() + "" + currentBMWEvent.getEndEventdate().getMonth() + "" + currentBMWEvent.getEndEventdate().getYear();*/

                //System.out.println("fuking event name: " +currentEvent.getName()+"at");

                this.journalistEvents.add(currentBMWEvent);

                System.out.println("fuking event name again: " + currentBMWEvent.getName());

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

    public List getPartipantsStati() {
        return PartipantsStati;
    }

    public void setPartipantsStati(List PartipantsStati) {
        this.PartipantsStati = PartipantsStati;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEinladungauswahl() {
        return einladungauswahl;
    }

    public void setEinladungauswahl(String einladungauswahl) {
        this.einladungauswahl = einladungauswahl;
    }

    public String getFlugauswahl() {
        return flugauswahl;
    }

    public void setFlugauswahl(String flugauswahl) {
        this.flugauswahl = flugauswahl;
    }

    public Map<String, String> getEinladungsauswahlmöglichkeiten() {
        return einladungsauswahlmöglichkeiten;
    }

    public void setEinladungsauswahlmöglichkeiten(Map<String, String> einladungsauswahlmöglichkeiten) {
        this.einladungsauswahlmöglichkeiten = einladungsauswahlmöglichkeiten;
    }

    public List getEventFlightListString() {
        return EventFlightListString;
    }

    public void setEventFlightListString(List EventFlightListString) {
        this.EventFlightListString = EventFlightListString;
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

        this.fetchJournalistStatus();
        this.getAllFlightsForEvent();

        return "eventdetail";

    }

    public void fetchJournalistStatus() {

        bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

        EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();

        this.PartipantsStati = em.createNamedQuery("BmwParticipants.findByEventId")
                .setParameter("id", this.selectedBmwEvent)
                .getResultList();

        BmwParticipants currentParticipant = (BmwParticipants) PartipantsStati.get(0);

        this.status = currentParticipant.getPState();

        System.out.println("state: " + this.status);

        this.status = "/BMW_Journalist/" + currentParticipant.getPState() + ".xhtml";
    }

    public void einladungBeantworten() {

        System.out.println("Einladung beant mit : " + this.einladungauswahl);

        

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

        this.EventFlightListString.add("Bitte Flug auswählen");

        for (int i = 0; i < currentFlightList.size(); i++) {
            String dateString = "" + currentFlightList.get(i).getDepartureTime().getDay() + "." + currentFlightList.get(i).getDepartureTime().getMonth() + "." + currentFlightList.get(i).getDepartureTime().getYear();
            String addstring = "" + currentFlightList.get(i).getDepartureLocation() + "-" + currentFlightList.get(i).getArrivalLocation() + ", " + dateString;

            this.EventFlightListString.add(addstring);

        }
    }
}
