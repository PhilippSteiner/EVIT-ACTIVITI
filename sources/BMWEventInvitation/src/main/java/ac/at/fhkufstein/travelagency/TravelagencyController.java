/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.travelagency;

import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwFlightController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.BmwTravelController;
import ac.at.fhkufstein.bean.process.ProcessTravelController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwFlight;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwTravel;
import ac.at.fhkufstein.service.MessageService;
import ac.at.fhkufstein.service.PersistenceService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author mike
 */
@ManagedBean(name = "travelagencyController")
@ViewScoped
public class TravelagencyController implements Serializable {

    private UploadedFile ticket;
    private Integer eventId;
    private HtmlInputHidden travelInputHidden;

    static {
    System.setProperty("ticket_upl_path", "/Volumes/Home/Uploads");
    }
    String dir = System.getProperty("ticket_upl_path") + "/";//Path where the files are saved

    public List<BmwEvent> getEvents() {

        List<BmwEvent> events = new ArrayList<BmwEvent>();

        try {
            List<BmwTravel> travels = PersistenceService.getManagedBeanInstance(BmwTravelController.class).getFacade().getEntityManager().createNamedQuery("BmwTravel.findAllFlightsWithPdfTicketUrl").getResultList();

            for (BmwTravel travel : travels) {

                List<BmwParticipants> participants = PersistenceService.getManagedBeanInstance(BmwParticipantsController.class).getFacade().getEntityManager().createNamedQuery("BmwParticipants.findByTravelId")
                        .setParameter("travelId", travel)
                        .getResultList();

                for (BmwParticipants participant : participants) {
                    if (!events.contains(participant.getEventId())) {
                        events.add(participant.getEventId());
                    }
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return events;
    }

    public List<BmwTravel> getTravels() {

        String paramEventId;
        if((paramEventId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("eventId"))!=null) {
            this.setEventId(Integer.parseInt(paramEventId));
        }
        List<BmwTravel> travels = new ArrayList<BmwTravel>();

        try {

            BmwEvent event = PersistenceService.getManagedBeanInstance(BmwEventController.class).getFacade().find(eventId);

            List<BmwParticipants> participants = PersistenceService.getManagedBeanInstance(BmwParticipantsController.class).getFacade().getEntityManager().createNamedQuery("BmwParticipants.findOpenFlightsByEventId")
                    .setParameter("eventId", event)
                    .getResultList();

            for (BmwParticipants participant : participants) {
                travels.add(participant.getTravelId());
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return travels;

    }

    /**
     * @return the ticket
     */
    public UploadedFile getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(UploadedFile ticket) {

        this.ticket = ticket;

        saveTicket(ticket);
    }

    public void saveTicket(UploadedFile ticket) {
        OutputStream output = null;

        try {

            Integer travelId = (Integer) travelInputHidden.getValue();
            File tempFile;
            BmwTravel travel = PersistenceService.getManagedBeanInstance(BmwTravelController.class).getFacade().find(travelId);
            System.out.println("Saving Ticket for Travel Id " + travel.getId());
            Boolean success = (tempFile = new File(dir)).mkdirs();//Create if needed
            if (success) {
                System.out.println("Folder "+tempFile.getPath()+" Created");
            }
            String travelDir = dir + travelId + "/";
            success = (tempFile = new File(travelDir)).mkdirs();//Create if needed
            if (success) {
                System.out.println("Folder "+tempFile.getPath()+" Created");
            }
            if (new File(travelDir, ticket.getFileName()).createNewFile()) {//create File
                System.out.println("File Created");
            } else {
                System.out.println("Cant Create");
            }
            InputStream input = ticket.getInputstream();//Get File contents
            output = new FileOutputStream(tempFile = new File(travelDir, ticket.getFileName()));
            try {
                IOUtils.copy(input, output);//write
            } finally {
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(output);
            }
            travel.setPdfTicketUrl(tempFile.getPath());

            BmwParticipants participant = (BmwParticipants) PersistenceService.getManagedBeanInstance(BmwParticipantsController.class).getFacade().getEntityManager().createNamedQuery("BmwParticipants.findByTravelId")
                    .setParameter("travelId", travel)
                    .getSingleResult();

            PersistenceService.save(BmwTravelController.class, travel);

            PersistenceService.getManagedBeanInstance(ProcessTravelController.class).processFlightData(participant);

            //Send Message
            MessageService.showInfo(FacesContext.getCurrentInstance(), "Erfolg", ticket.getFileName() + " wurde erfolgreich hochgeladen.");


        } catch (FileNotFoundException ex) {
            Logger.getLogger(TravelagencyController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TravelagencyController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (javax.persistence.NonUniqueResultException ex) {
            Logger.getLogger(TravelagencyController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(output!=null) {
                output.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(TravelagencyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the eventId
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

     public void setTravel(BmwTravel travel) {
        System.out.println("travel");
    }

     public void dom() {
    System.out.println("dom");

}

    /**
     * @return the travelInputHidden
     */
    public HtmlInputHidden getTravelInputHidden() {
        return travelInputHidden;
    }

    /**
     * @param travelInputHidden the travelInputHidden to set
     */
    public void setTravelInputHidden(HtmlInputHidden travelInputHidden) {
        this.travelInputHidden = travelInputHidden;
    }
}
