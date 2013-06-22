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
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.login.doLogin;
import ac.at.fhkufstein.service.PersistenceService;
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
    private String vorname;
    private String nachname;
    private String email;
    private String einladungsoberflaeche = "einladung";
    private BmwParticipants currentParticipant;
    private int shouldreloadstatus;

    public ResponseBean() {

        System.out.println("Response Bean Constructor ausgeführt");

        auswahlmoeglichkeiten = new ArrayList<SelectItem>();
        verfuegbareFluege = new ArrayList<SelectItem>();

        this.auswahl = "nix";

        if (shouldreloadstatus != 1) {

            System.out.println("IS RELOADING STATUS");

                        BmwParticipantsController bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

            JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);

            
            doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);
            
            EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();
        

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

            shouldreloadstatus = 0;

        }
    }

    @PostConstruct
    private void initialize() {
        auswahlmoeglichkeiten.add(new SelectItem("Zusagen"));
        auswahlmoeglichkeiten.add(new SelectItem("Absagen"));
        auswahlmoeglichkeiten.add(new SelectItem("Vertretung schicken"));

        verfuegbareFluege.add(new SelectItem("Flug 1"));
        verfuegbareFluege.add(new SelectItem("Flug 2"));
        verfuegbareFluege.add(new SelectItem("Flug 3"));

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

    public String getEinladungsoberflaeche() {
        return einladungsoberflaeche;
    }

    public void setEinladungsoberflaeche(String einladungsoberflaeche) {
        this.einladungsoberflaeche = einladungsoberflaeche;
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

        } else if (auswahl.equals("nix")) {

            System.out.print("is nix *****************************************************************");

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
            System.out.print("Name: "+currentPartipantsStati.getUserId().getUsername());
            System.out.print("Status:"+currentPartipantsStati.getPState());

            this.step = "wiz2";

            bmwParticipantsController.setSelected(currentPartipantsStati);
            
            currentPartipantsStati.setPState("zugesagt");
            bmwParticipantsController.save(null);

            System.out.print("State saved to: " + currentPartipantsStati.getPState());

        } else if (auswahl.equals("Absagen")) {

            System.out.print("Absagen *****************************************************************");

            this.step = "wiz2";

        } else {

            this.step = "wiz2";

        }
    }

    public void flugauswaehlen() {

        System.out.println("Flug ausgewaehlt: " + this.flugauswahl);

        /* if (this.flugauswahl == null) {
         //nachricht noch ausgeben
         } else {*/

        this.step = "wiz3";

        //}

    }

    public void selbstanreisewaehlen() {

        System.out.println("Selbstanreise");

        this.step = "wiz3";

    }

    public void vertretungAngeben() {

        String vertretungsString = " Daten für Vertetung: Vorname: " + this.vorname + " Nachname: " + this.nachname + " Email: " + this.email;

        System.out.println(vertretungsString);

        this.step = "wiz2";

    }
}
