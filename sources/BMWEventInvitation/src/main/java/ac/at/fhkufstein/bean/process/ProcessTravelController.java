/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.bean.process;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.service.MessageService;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwEventFacade;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

/**
 *
 * @author mike
 */
@ManagedBean(name = "processTravelController")
@ViewScoped
public class ProcessTravelController implements Serializable {

    private static final String ACTIVITI_FLIGHTDATA_ACTIVITY = "supplyFlightInfos";

    public void processFlightData(BmwParticipants participant) {


        InvitationProcess process = new InvitationProcess(participant, InvitationProcess.PROCESSES[1]);

        participant.setPState("ticket");
        PersistenceService.save(BmwParticipantsController.class, participant);

        if (process.resumeProcess(ACTIVITI_FLIGHTDATA_ACTIVITY)) {

            MessageService.showInfo(FacesContext.getCurrentInstance(), "Die Flugdaten wurden eingegeben.");
            MessageService.showInfo(FacesContext.getCurrentInstance(), "Der Prozess wurde fortgefahren.");

        } else {
            MessageService.showError(FacesContext.getCurrentInstance(), "Die Flugdaten konnten nicht eingegeben werden.");
            MessageService.showError(FacesContext.getCurrentInstance(), "Der Prozess konnte nicht fortgesetzt werden.");
        }
    }
}
