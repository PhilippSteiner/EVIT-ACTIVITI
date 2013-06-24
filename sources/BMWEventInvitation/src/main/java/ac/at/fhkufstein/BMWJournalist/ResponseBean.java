package ac.at.fhkufstein.BMWJournalist;

/*
 * Copyright 2013 Blue Lotus Software, LLC..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 * @author Alex
 */
import ac.at.fhkufstein.bean.BmwFlightController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwFlight;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.login.doLogin;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwFlightFacade;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

@ManagedBean(name = "ResponseBean")
@ViewScoped
public class ResponseBean implements Serializable {

    //private static final long serialVersionUID = -1776353555799643520L;
    //private BmwParticipantsController bmwParticipantsController;
    private List PartipantsStati;
    private List<SelectItem> auswahlmoeglichkeiten;
    private List<SelectItem> verfuegbareFluege;
    private List<String> vertretungen;
    private String auswahl;
    private String flugauswahl;
    private String step;
    private String vorname = "";
    private String nachname = "";
    private String email = "";
    private String einladungsoberflaeche;
    private String rightside;
    private BmwParticipants currentParticipant;
    private String eventdetailview = "eventdetail";
    
    private String outcomemessage;
    
    private BmwFlightController bmwFlightController;

    public ResponseBean() {
        
        this.einladungsoberflaeche = "einladung";
        this.rightside = "detailrightside";

        System.out.println("Response Bean Constructor ausgeführt");

        auswahlmoeglichkeiten = new ArrayList<SelectItem>();
        verfuegbareFluege = new ArrayList<SelectItem>();

        this.auswahl = "nix";

            System.out.println("IS RELOADING STATUS");

            BmwParticipantsController bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

            JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);


            doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

            EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();

            System.out.println("Parameter Event: "+currentJournalistBean.getSelectedBmwEvent().getName());
            System.out.println("Parameter UserID: "+PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()));

            BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                    .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                    .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                    .getSingleResult();

            String prestatus = currentPartipantsStati.getPState();

            System.out.println("state is null, now: " + prestatus);

            if (prestatus.equals("eingeladen")) {

                this.step = "wiz1";
            }

            if (prestatus.equals("zugesagt")) {

                this.step = "wiz2";
            }
            
            if (prestatus.equals("selbstanreise")) {

                this.step = "wiz3";
            }
            
            if (prestatus.equals("flugausgewaehlt")) {

                this.step = "wiz3";
            }
    }

    @PostConstruct
    private void initialize() {
        
        auswahlmoeglichkeiten.add(new SelectItem("Zusagen"));
        auswahlmoeglichkeiten.add(new SelectItem("Absagen"));
        auswahlmoeglichkeiten.add(new SelectItem("Vertretung schicken"));

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
        System.out.println("Set Antwort:" + this.auswahl);
    }

    public List<SelectItem> getVerfuegbareFluege() {
        
        this.setVerfuegbareFluege(this.getAllFlightsForEvent());
        
        return verfuegbareFluege;
    }

    public void setVerfuegbareFluege(List<SelectItem> verfuegbareFluege) {
        this.verfuegbareFluege = verfuegbareFluege;
    }

    public String getFlugauswahl() {
        return flugauswahl;
    }

    public void setFlugauswahl(String flugauswahl) {
        this.flugauswahl = flugauswahl;
        

        System.out.println("Set Flugauswahl: " + this.flugauswahl);

    }

    public String getStep() {

        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getEmail() {
        return email;
    }

    public String getRightside() {
        return rightside;
    }

    public void setRightside(String rightside) {
        this.rightside = rightside;
    }
    

    public String getEinladungsoberflaeche() {
        return einladungsoberflaeche;
    }

    public void setEinladungsoberflaeche(String einladungsoberflaeche) {
        this.einladungsoberflaeche = einladungsoberflaeche;
    }

    public String getOutcomemessage() {
        return outcomemessage;
    }

    public void setOutcomemessage(String outcomemessage) {
        this.outcomemessage = outcomemessage;
    }

    public String getEventdetailview() {
        return eventdetailview;
    }

    public void setEventdetailview(String eventdetailview) {
        this.eventdetailview = eventdetailview;
    }
    
    
    
    

    //////*********************************************
    public void stateChangeListener(ValueChangeEvent event) {

        System.out.println("Listener Beantwortet mit: " + event.getNewValue());

        this.auswahl = event.getNewValue().toString();
    }

    public void stateFlugChangeListener(ValueChangeEvent event) {

        System.out.println("Flugauswahl Beantwortet mit: " + event.getNewValue());

        this.flugauswahl = event.getNewValue().toString();
    }

    public void beantworten() {

        System.out.println("Beantwortet mit: " + this.auswahl);

        if (this.auswahl.equals("Vertretung schicken")) {

            this.einladungsoberflaeche = "einladung_select";
            
            //return "do nothing";

        } else if (auswahl.equals("nix")) {

            System.out.print("is nix *****************************************************************");
            
            //return "do nothing";

        } else if (auswahl.equals("Zusagen")) {

            BmwParticipantsController bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

            JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);


            doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

            EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();


            BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                    .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                    .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                    .getSingleResult();

            //BmwParticipants zusagenParticipant = (BmwParticipants) currentPartipantsStati.get(0);

            System.out.print("Zusagen *****************************************************************");
            System.out.print("Name: " + currentPartipantsStati.getUserId().getUsername());
            System.out.print("Status:" + currentPartipantsStati.getPState());

            this.step = "wiz2";

            bmwParticipantsController.setSelected(currentPartipantsStati);

            currentPartipantsStati.setPState("zugesagt");
            bmwParticipantsController.save(null);

            System.out.print("State saved to: " + currentPartipantsStati.getPState());
            
            //return "do nothing";

        } else if (auswahl.equals("Absagen")) {

            BmwParticipantsController bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

            JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);


            doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

            EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();


            BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                    .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                    .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                    .getSingleResult();

            //BmwParticipants zusagenParticipant = (BmwParticipants) currentPartipantsStati.get(0);

            System.out.print("Abgesagt *****************************************************************");
            System.out.print("Name: " + currentPartipantsStati.getUserId().getUsername());
            System.out.print("Status:" + currentPartipantsStati.getPState());

            bmwParticipantsController.setSelected(currentPartipantsStati);

            currentPartipantsStati.setPState("abgesagt");
            bmwParticipantsController.save(null);

            System.out.print("State saved to: " + currentPartipantsStati.getPState());
            
            this.outcomemessage = "Viele Dank für Ihre Antwort";
            
            //return "/BMW_Journalist/eventoutcome.xhtml";
            
            this.setEventdetailview("eventoutcome");

        } else {
            
            //return "do nothing";
        }
    }

    public void flugauswaehlen() {

        System.out.println("Flug ausgewaehlt: " + this.flugauswahl);
        
        //STATUS ÄNDERN
            
        BmwParticipantsController bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

            JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);


            doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

            EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();


            BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                    .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                    .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                    .getSingleResult();

            //BmwParticipants zusagenParticipant = (BmwParticipants) currentPartipantsStati.get(0);

            System.out.print("Abgesagt *****************************************************************");
            System.out.print("Name: " + currentPartipantsStati.getUserId().getUsername());
            System.out.print("Status:" + currentPartipantsStati.getPState());

            bmwParticipantsController.setSelected(currentPartipantsStati);

            currentPartipantsStati.setPState("flugausgewaehlt");
            bmwParticipantsController.save(null);

            System.out.print("State saved to: " + currentPartipantsStati.getPState());

        /* if (this.flugauswahl == null) {
         //nachricht noch ausgeben
         } else {*/

        this.step = "wiz3";

        //}

    }

    public void selbstanreisewaehlen() {
        
                    BmwParticipantsController bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

            JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);


            doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

            EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();


            BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                    .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                    .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                    .getSingleResult();

            //BmwParticipants zusagenParticipant = (BmwParticipants) currentPartipantsStati.get(0);

            System.out.print("Abgesagt *****************************************************************");
            System.out.print("Name: " + currentPartipantsStati.getUserId().getUsername());
            System.out.print("Status:" + currentPartipantsStati.getPState());

            bmwParticipantsController.setSelected(currentPartipantsStati);

            currentPartipantsStati.setPState("selbstanreise");
            bmwParticipantsController.save(null);

            System.out.print("State saved to: " + currentPartipantsStati.getPState());

        System.out.println("Selbstanreise");

        this.step = "wiz3";

    }

    public void vertretungAngeben() {

        //String vertretungsString = " Daten für Vertetung: Vorname: " + this.getVorname() + " Nachname: " + this.getNachname() + " Email: " + this.getEmail();

        String vertretungsString = "Wolfgang Tevez fish@mail.com";
        
        System.out.println(vertretungsString);

        BmwParticipantsController bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

        JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);


        doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

        EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();


        BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                .getSingleResult();



        //BmwParticipants zusagenParticipant = (BmwParticipants) currentPartipantsStati.get(0);

        System.out.print("Abgesagt *****************************************************************");
        System.out.print("Name: " + currentPartipantsStati.getUserId().getUsername());
        System.out.print("Status:" + currentPartipantsStati.getPState());

        bmwParticipantsController.setSelected(currentPartipantsStati);

        currentPartipantsStati.setPState("vertretung");
        currentPartipantsStati.setRepComment(vertretungsString);
        bmwParticipantsController.save(null);

        System.out.print("State saved to: " + currentPartipantsStati.getPState());
        System.out.print("Comment saved to: " + currentPartipantsStati.getRepComment());

        this.outcomemessage = "Sie haben als Vertretungswunsch Max Mustermann angegeben.";
        
        this.eventdetailview = "eventoutcome";
        
        //return "eventoutcome.xhtml";

    }
    
    public List getAllFlightsForEvent() {
        
        List <SelectItem> returnFluege = new ArrayList<SelectItem>();

        this.bmwFlightController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwFlightController}", BmwFlightController.class);

        EntityManager em = ((BmwFlightFacade) bmwFlightController.getFacade()).getEntityManager();

        JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);

        List<BmwFlight> currentFlightList = em.createNamedQuery("BmwFlight.findByEventId")
                .setParameter("eventId", currentJournalistBean.getSelectedBmwEvent())
                .getResultList();

        System.out.println("Flights " + currentFlightList.size());


        for (int i = 0; i < currentFlightList.size(); i++) {
            String dateString = "" + currentFlightList.get(i).getDepartureTime().getDay() + "." + currentFlightList.get(i).getDepartureTime().getMonth() + "." + currentFlightList.get(i).getDepartureTime().getYear();
            String addstring = "" + currentFlightList.get(i).getDepartureLocation() + "-" + currentFlightList.get(i).getArrivalLocation() + ", " + dateString;

            returnFluege.add(new SelectItem(addstring));

        }
        
        return returnFluege;
    }
    
    /*public String goBackToEvents(){
    
        return "/BMW_Journalist/journalistmenue.xhtml";
    
    }*/


}
