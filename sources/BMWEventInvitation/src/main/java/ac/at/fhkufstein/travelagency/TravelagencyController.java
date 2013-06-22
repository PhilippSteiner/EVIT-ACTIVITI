/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.travelagency;

import ac.at.fhkufstein.bean.BmwFlightController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.BmwTravelController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwFlight;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwTravel;
import ac.at.fhkufstein.service.PersistenceService;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
@ManagedBean(name = "travelagencyController")
@ViewScoped
public class TravelagencyController {

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
}
