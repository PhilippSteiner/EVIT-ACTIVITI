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
    @Resource
    UserTransaction ut;

    public void saveNew(ActionEvent event) {
        try {

            BmwEventController eventController = PersistenceService.getManagedBeanInstance(BmwEventController.class);

            // to get the id of the inserted event immediately a transacion has to be executed
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();

            try {
                EntityManager em = ((BmwEventFacade) eventController.getFacade()).getEntityManager();
                em.persist(eventController.getSelected());
                transaction.commit();
            } catch (Exception ex) {
                Logger.getLogger(ProcessTravelController.class.getName()).log(Level.SEVERE, null, ex);
                transaction.rollback();
            }

            startEventProcess(eventController.getSelected());

        } catch (NamingException ex) {
            Logger.getLogger(ProcessTravelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException ex) {
            Logger.getLogger(ProcessTravelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(ProcessTravelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ProcessTravelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(ProcessTravelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void save(ActionEvent event) {

        BmwEventController eventController = PersistenceService.getManagedBeanInstance(BmwEventController.class);

        eventController.save(event);

        if (eventController.getSelected().getProcessId() == null) {
            startEventProcess(eventController.getSelected());
        }

    }

    private void startEventProcess(BmwEvent event) {
        try {

            if (event.getId() == null) {
                throw new Exception("Das Event wurde noch nicht gespeichert.");
            }

            new InvitationProcess(event, InvitationProcess.PROCESSES[0]).startProcess();

            MessageService.showInfo(FacesContext.getCurrentInstance(), "Der Prozess für dieses Event wurde gestartet.");
        } catch (Exception ex) {
            Logger.getLogger(ProcessTravelController.class.getName()).log(Level.SEVERE, null, ex);

            MessageService.showError(FacesContext.getCurrentInstance(), "Der Prozess für dieses Event konnte nicht gestartet werden.");

        }
    }

    public void processFlightData(BmwParticipants participant) {


        InvitationProcess process = new InvitationProcess(participant, InvitationProcess.PROCESSES[1]);

        if (process.getCurrentActivity() != null && process.getCurrentActivity().equals(ACTIVITI_FLIGHTDATA_ACTIVITY)) {



            participant.setPState("ticket");
            PersistenceService.save(BmwParticipantsController.class, participant);

            process.resumeProcess();

            MessageService.showInfo(FacesContext.getCurrentInstance(), "Die Flugdaten wurden eingegeben.");
            MessageService.showInfo(FacesContext.getCurrentInstance(), "Der Prozess wurde fortgefahren.");
        } else {
            MessageService.showError(FacesContext.getCurrentInstance(), "Die Flugdaten konnten nicht eingegeben werden.");
            MessageService.showError(FacesContext.getCurrentInstance(), "Der Prozess konnte nicht fortgesetzt werden.");
        }
    }
}
