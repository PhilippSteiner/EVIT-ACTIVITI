/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.activiti.delegates;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.process.ProcessBmwEventController;
import ac.at.fhkufstein.bean.process.ProcessParticipants;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.ParticipantStatus;
import ac.at.fhkufstein.service.MessageService;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.Status;
import javax.transaction.UserTransaction;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *
 * @author mike
 */
public class InviteNextParticipant implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {


        System.out.println("################# invite next participant #################");


        execution.getVariable(InvitationProcess.DATABASE_PARTICIPANTID);
        BmwEvent event = (BmwEvent) PersistenceService.loadByInteger(BmwEventController.class, execution.getVariable(InvitationProcess.DATABASE_EVENTID));

        BmwUser user = InvitationProcess.getNextParticipant(event);

        if (user == null) {
            String noUserMessage = "Es befindet sich kein User mehr in der Warteschlange.";

            MessageService.showInfo(FacesContext.getCurrentInstance(), noUserMessage);
        } else {

            // save next Participant
            BmwParticipantsController participantController = PersistenceService.getManagedBeanInstance(BmwParticipantsController.class);
            BmwParticipants participant = participantController.prepareCreate(null);
            participant.setEventId(event);
            participant.setUserId(user);
            participant.setPState(ParticipantStatus.INVITED);


            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            if (transaction.getStatus() == Status.STATUS_NO_TRANSACTION) {
                transaction.begin();
            }

            try {
                EntityManager em = ((BmwParticipantsFacade) participantController.getFacade()).getEntityManager();
                em.persist(participantController.getSelected());
                transaction.commit();
            } catch (Exception ex) {
                Logger.getLogger(ProcessBmwEventController.class.getName()).log(Level.SEVERE, null, ex);
                transaction.rollback();
            }

            // start new process
            InvitationProcess.startSingleProcess(event, participant);

            String nextParticipantInvited = "Der Teilnehmer " + participant.getUserId().getPersonenID().getVorname() + " " + participant.getUserId().getPersonenID().getNachname() + " wurde nachgeladen.";

            MessageService.showInfo(FacesContext.getCurrentInstance(), nextParticipantInvited);
        }
    }
}