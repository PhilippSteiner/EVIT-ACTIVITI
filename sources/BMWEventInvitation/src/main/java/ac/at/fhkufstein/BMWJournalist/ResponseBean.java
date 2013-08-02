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
import ac.at.fhkufstein.bean.BmwTravelController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.bean.process.ProcessJournalist;
import ac.at.fhkufstein.entity.BmwFlight;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwTravel;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.ParticipantStatus;
import ac.at.fhkufstein.login.doLogin;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwFlightFacade;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import ac.at.fhkufstein.session.BmwTravelFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
@SessionScoped
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
    private BmwTravelController bmwTravelController;
    private BmwParticipantsController bmwParticipantsController;
    private String kommentar;
    private String noresponsemessage;
    private String responseview;

    public ResponseBean() {

        this.einladungsoberflaeche = "einladung";
        this.rightside = "detailrightside";

        System.out.println("Response Bean Constructor ausgeführt");

        auswahlmoeglichkeiten = new ArrayList<SelectItem>();
        verfuegbareFluege = new ArrayList<SelectItem>();

        this.auswahl = "nix";

        System.out.println("IS RELOADING STATUS");

        this.bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);
        this.bmwTravelController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwTravelController}", BmwTravelController.class);
        this.bmwFlightController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwFlightController}", BmwFlightController.class);

        JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);

        doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

        EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();

        System.out.println("Parameter Event: " + currentJournalistBean.getSelectedBmwEvent().getName());
        System.out.println("Parameter UserID: " + PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()));

        BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                .getSingleResult();

        String prestatus = currentPartipantsStati.getPState();

        System.out.println("Check state in Constructor: " + prestatus);

        if (prestatus.equals("eingeladen")) {

            this.step = "wiz1";

            this.responseview = "response";
        }

        if (prestatus.equals("zugesagt")) {

            this.step = "wiz2";

            this.responseview = "response";
        }

        if (prestatus.equals("selbstanreise")) {

            this.step = "wiz3";

            this.responseview = "response";
        }

        if (prestatus.equals("flugausgewaehlt")) {

            this.step = "wiz3";

            this.responseview = "response";
        }

        if (prestatus.equals("vertreten")) {

            this.noresponsemessage = "  Sie haben eine Vertretung für diesen Event angegeben";
            this.responseview = "noresponse";
        }

        if (prestatus.equals("abgesagt")) {

            this.noresponsemessage = "  Sie haben für diesen Event abgesagt";
            this.responseview = "noresponse";
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

        return this.getAllFlightsForEvent();

    }

    public void setVerfuegbareFluege(List<SelectItem> verfuegbareFluege) {
        this.verfuegbareFluege = verfuegbareFluege;
    }

    public String getFlugauswahl() {
        return flugauswahl;
    }

    public void setFlugauswahl(String flugauswahl) {
        this.flugauswahl = flugauswahl;

        System.out.println("Set Flugauswahl: " + this.getFlugauswahl());

    }

    public String getStep() {

        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public void setVorname(String vorname) {

        System.out.println("Vorname changed to: " + vorname);

        this.vorname = vorname;
    }

    public void setNachname(String nachname) {

        System.out.println("Nachname changed to: " + nachname);

        this.nachname = nachname;
    }

    public void setEmail(String email) {

        System.out.println("Email changed to: " + email);

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

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public String getNoresponsemessage() {
        return noresponsemessage;
    }

    public void setNoresponsemessage(String noresponsemessage) {
        this.noresponsemessage = noresponsemessage;
    }

    public String getResponseview() {
        return responseview;
    }

    public void setResponseview(String responseview) {
        this.responseview = responseview;
    }

    //////*********************************************
    public void stateChangeListener(ValueChangeEvent event) {

        System.out.println("LISTENER: Beantwortet mit: " + event.getNewValue());

        this.auswahl = event.getNewValue().toString();
    }

    public void stateFlugChangeListener(ValueChangeEvent event) {

        System.out.println("LISTENER: Flugauswahl Beantwortet mit: " + event.getNewValue());

        this.flugauswahl = event.getNewValue().toString();
    }

    public String beantworten() {

        System.out.println("Beantwortet mit: " + this.auswahl);

        if (this.auswahl.equals("Vertretung schicken")) {

            this.einladungsoberflaeche = "einladung_select";

            return "do nothing";

        } else if (auswahl.equals("nix")) {

            System.out.print("is nix *****************************************************************");

            return "do nothing";

        } else if (auswahl.equals("Zusagen")) {

            JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);


            doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

            EntityManager em = ((BmwParticipantsFacade) this.bmwParticipantsController.getFacade()).getEntityManager();


            BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                    .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                    .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                    .getSingleResult();

            //BmwParticipants zusagenParticipant = (BmwParticipants) currentPartipantsStati.get(0);

            System.out.print("Zusagen *****************************************************************");
            System.out.print("Name: " + currentPartipantsStati.getUserId().getUsername());
            System.out.print("Status:" + currentPartipantsStati.getPState());

            this.step = "wiz2";

            this.bmwParticipantsController.setSelected(currentPartipantsStati);

            currentPartipantsStati.setPState(ParticipantStatus.ACCEPTED);
            this.bmwParticipantsController.save(null);

            System.out.print("State saved to: " + currentPartipantsStati.getPState());


        // Prozess weiterführen
        PersistenceService.getManagedBeanInstance(ProcessJournalist.class).answerInvitation(currentPartipantsStati, true, false);

           // context.addMessage(null, new FacesMessage(currentPartipantsStati.getEventId().getName(), "Zugesagt"));

            return "do nothing";

        } else if (auswahl.equals("Absagen")) {

            //BmwParticipantsController bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

            JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);


            doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

            EntityManager em = ((BmwParticipantsFacade) this.bmwParticipantsController.getFacade()).getEntityManager();


            BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                    .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                    .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                    .getSingleResult();

            //BmwParticipants zusagenParticipant = (BmwParticipants) currentPartipantsStati.get(0);

            System.out.print("Abgesagt *****************************************************************");
            System.out.print("Name: " + currentPartipantsStati.getUserId().getUsername());
            System.out.print("Status:" + currentPartipantsStati.getPState());

            this.bmwParticipantsController.setSelected(currentPartipantsStati);

            currentPartipantsStati.setPState(ParticipantStatus.REFUSED);
            this.bmwParticipantsController.save(null);

            System.out.print("State saved to: " + currentPartipantsStati.getPState());

            // Prozess weiterführen
            PersistenceService.getManagedBeanInstance(ProcessJournalist.class).answerInvitation(currentPartipantsStati, false, false);

            this.outcomemessage = "Viele Dank für Ihre Antwort";

            this.eventdetailview = "eventoutcome";

           // context.addMessage(null, new FacesMessage(currentPartipantsStati.getEventId().getName(), "Abgesagt"));

            return "eventoutcome";


        } else {

            return "do nothing";
        }
    }

    public void flugauswaehlen() {

        System.out.println("Flug ausgewaehlt: " + this.getFlugauswahl());

        if (this.getFlugauswahl() == null || this.getFlugauswahl().equals("Noch keine Flüge verfügbar")) {

            System.out.println("Keine Fluege, nix ausgewählt");

        } else {

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

            this.bmwParticipantsController.setSelected(currentPartipantsStati);

            currentPartipantsStati.setPState(ParticipantStatus.FLIGHT_SELECTED);
            this.bmwParticipantsController.save(null);

            System.out.print("##############State saved to: " + currentPartipantsStati.getPState());

            //FLUG-TRAVEL DATA STUFF

            //get flug by name

            if (this.getFlugauswahl() == null) {

                System.out.print("Flug noch null");

            } else {

                System.out.print("Flug nicht mehr null");

                String[] splitResult = this.getFlugauswahl().split(
                        ",");

                System.out.print("Lenght SplitString: " + splitResult.length);

                String flightnumber = splitResult[2];

                System.out.print("Flightnumber: " + flightnumber);

                this.bmwFlightController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwFlightController}", BmwFlightController.class);

                EntityManager flugem = ((BmwFlightFacade) this.bmwFlightController.getFacade()).getEntityManager();

                BmwFlight myflight = (BmwFlight) flugem.createNamedQuery("BmwFlight.findByFlightNumber")
                        .setParameter("flightNumber", flightnumber)
                        .getSingleResult();

                System.out.print("Got Flight" + myflight.getDepartureLocation() + "from Flightnumber: " + flightnumber);

                //create tavel *************************************************************

                System.out.print("Kommentar: " + this.getKommentar());

                BmwTravel mytravel = new BmwTravel();
                mytravel.setFlightId(myflight);
                mytravel.setComment(this.getKommentar());
                mytravel.setType("typeFlug");
                mytravel.setArrivalDatetime(myflight.getArrivalTime());

                this.bmwTravelController.setSelected(mytravel);

                this.bmwTravelController.save(null);

                //Get Travel back from DB***************************************************

                EntityManager travelEM = ((BmwTravelFacade) bmwTravelController.getFacade()).getEntityManager();

                List<BmwTravel> myTravelDBList = travelEM.createNamedQuery("BmwTravel.findByFlight")
                        .setParameter("flightId", myflight)
                        .getResultList();

                System.out.print("Size of Travellist again:" + myTravelDBList.size());

                int pointer = (myTravelDBList.size() - 1);

                System.out.print("Pointer :" + pointer);

                BmwTravel myTravelfromDB = new BmwTravel();

                myTravelfromDB = myTravelDBList.get(pointer);

                System.out.print("myTravelfromDB Flight :" + myTravelfromDB.getFlightId().getDepartureLocation());

                //Get partitipant change travel id at participant******************************

                //BmwParticipantsController bmwParticipantsFlightController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsFlightController}", BmwParticipantsController.class);

                JournalistBean currentFlightJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);

                doLogin currentFlightlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

                EntityManager particiEM = ((BmwParticipantsFacade) this.bmwParticipantsController.getFacade()).getEntityManager();

                BmwParticipants toChangeParticipant = (BmwParticipants) particiEM.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                        .setParameter("id", currentFlightJournalistBean.getSelectedBmwEvent())
                        .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentFlightlogin.getUid()))
                        .getSingleResult();

                //BmwParticipants zusagenParticipant = (BmwParticipants) currentPartipantsStati.get(0);

                System.out.print("Abgesagt *****************************************************************");
                System.out.print("Name: " + toChangeParticipant.getUserId().getUsername());
                System.out.print("Status:" + toChangeParticipant.getPState());

                this.bmwParticipantsController.setSelected(toChangeParticipant);


                toChangeParticipant.setTravelId(myTravelfromDB);
                this.bmwParticipantsController.save(null);


            // Prozess weiterführen
            PersistenceService.getManagedBeanInstance(ProcessJournalist.class).supplyTravelInfos(toChangeParticipant, true, true);


                this.step = "wiz3";

            }

        }

    }

    public void selbstanreisewaehlen() {

        this.step = "wiz3";

        JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);


        doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

        EntityManager em = ((BmwParticipantsFacade) this.bmwParticipantsController.getFacade()).getEntityManager();


        BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                .getSingleResult();

        System.out.print("Name: " + currentPartipantsStati.getUserId().getUsername());
        System.out.print("Status:" + currentPartipantsStati.getPState());

        this.bmwParticipantsController.setSelected(currentPartipantsStati);

        currentPartipantsStati.setPState(ParticipantStatus.ARRIVAL_ON_ONES_OWN);
        this.bmwParticipantsController.save(null);

        System.out.print("State saved to: " + currentPartipantsStati.getPState());

        System.out.println("Selbstanreise");

        // Prozess weiterführen
        PersistenceService.getManagedBeanInstance(ProcessJournalist.class).supplyTravelInfos(currentPartipantsStati, false, false);

        this.step = "wiz3";

        //context.addMessage(null, new FacesMessage("Selbstanreise", "Selbstanreise ausgewählt."));

    }

    public String vertretungAngeben() {

        this.eventdetailview = "eventoutcome";
        this.outcomemessage = "Sie haben als Vertretungswunsch" + this.getKommentar() + "angegeben.";


        String vertretungsString = this.getKommentar();

        //String vertretungsString = "Wolfgang Tevez fish@mail.com";

        System.out.println(vertretungsString);

        //BmwParticipantsController bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

        JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);


        doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

        EntityManager em = ((BmwParticipantsFacade) this.bmwParticipantsController.getFacade()).getEntityManager();


        BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                .getSingleResult();



        //BmwParticipants zusagenParticipant = (BmwParticipants) currentPartipantsStati.get(0);

        System.out.print("Abgesagt *****************************************************************");
        System.out.print("Name: " + currentPartipantsStati.getUserId().getUsername());
        System.out.print("Status:" + currentPartipantsStati.getPState());

        this.bmwParticipantsController.setSelected(currentPartipantsStati);

        currentPartipantsStati.setPState(ParticipantStatus.SUBSTITUTE);
        currentPartipantsStati.setRepComment(vertretungsString);
        this.bmwParticipantsController.save(null);


        System.out.print("State saved to: " + currentPartipantsStati.getPState());
        System.out.print("Comment saved to: " + currentPartipantsStati.getRepComment());

        // Prozess weiterführen
        PersistenceService.getManagedBeanInstance(ProcessJournalist.class).answerInvitation(currentPartipantsStati, false, true);

        this.outcomemessage = "Sie haben als Vertretungswunsch" + this.getVorname() + " " + this.getNachname() + ", " + this.getEmail() + "angegeben.";

        this.eventdetailview = "eventoutcome";

        return "eventoutcome";

    }

    public List<SelectItem> getAllFlightsForEvent() {

        List<SelectItem> returnFluege = new ArrayList<SelectItem>();

        this.bmwFlightController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwFlightController}", BmwFlightController.class);

        EntityManager em = ((BmwFlightFacade) this.bmwFlightController.getFacade()).getEntityManager();

        JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);

        List<BmwFlight> currentFlightList = em.createNamedQuery("BmwFlight.findByEventId")
                .setParameter("eventId", currentJournalistBean.getSelectedBmwEvent())
                .getResultList();

        System.out.println("Flights " + currentFlightList.size());

        if (currentFlightList.isEmpty()) {

            returnFluege.add(new SelectItem("Noch keine Flüge verfügbar"));

        } else {

            for (int i = 0; i < currentFlightList.size(); i++) {

                String dateString = "" + currentFlightList.get(i).getDepartureTime().getDay() + "." + currentFlightList.get(i).getDepartureTime().getMonth() + "." + currentFlightList.get(i).getDepartureTime().getYear();
                String addstring = "" + currentFlightList.get(i).getDepartureLocation() + "-" + currentFlightList.get(i).getArrivalLocation() + ", " + dateString + "," + currentFlightList.get(i).getFlightNumber();

                returnFluege.add(new SelectItem(addstring));

                System.out.println("Last Object" + returnFluege.get(i).getLabel());

            }
        }

        return returnFluege;
    }

    public String getTicketPath() {

        JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);


        doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

        EntityManager em = ((BmwParticipantsFacade) this.bmwParticipantsController.getFacade()).getEntityManager();


        BmwParticipants currentPartipant = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                .getSingleResult();

        if (currentPartipant.getTravelId().getPdfTicketUrl() == null) {

            return "Noch kein Ticket verfügbar";

        } else {

            return currentPartipant.getTravelId().getPdfTicketUrl().toString();

        }
    }
}
