/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.bean.process;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.activiti.Services;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.service.MessageService;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.NotSupportedException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author mike
 */
@ManagedBean(name = "processParticipants")
@ViewScoped
public class ProcessParticipants implements Serializable {

    private final String ACTIVITI_ADD_ACTIVITY = "addJournalists";
    private final String ACTIVITI_ADD_ANOTHER_ACTIVITY = "releaseEvent";
    private final String ACTIVITI_SELECTIVE_INVITATION = "selectiveInvitation";

    public void confirmInvitedParticipants(BmwEvent event) {

        // @todo Invite Participants which have been invited after Start

        InvitationProcess process = new InvitationProcess(event, InvitationProcess.PROCESSES[0]);

        if (!Services.getRuntimeService().createExecutionQuery().processInstanceId(String.valueOf(event.getProcessId())).activityId(ACTIVITI_ADD_ACTIVITY).list().isEmpty()) {

            if (process.resumeProcess(ACTIVITI_ADD_ACTIVITY)) {

                MessageService.showInfo(FacesContext.getCurrentInstance(), "Prozess", "Der Prozess wird fortgefahren.");

            } else {
                MessageService.showError(FacesContext.getCurrentInstance(), "Prozess", "Der Prozess kann nicht fortgesetzt werden. Current Activity: " + ACTIVITI_ADD_ACTIVITY);
            }
            /* Prozess soll nicht fortgesetzt werden wenn bereits mindestens ein Journalist als Teilnehmer zum 
             * Event hinzugefügt worden ist und weitere Journalisten als Teilnehmer hinzugefügt werden.
             * Der Prozess wird dann einfach beim "release" des Events fortfahren!
             */
        } else if (!Services.getRuntimeService().createExecutionQuery().processInstanceId(String.valueOf(event.getProcessId())).activityId(ACTIVITI_ADD_ANOTHER_ACTIVITY).list().isEmpty()) {
            MessageService.showInfo(FacesContext.getCurrentInstance(), "Prozess", "Der Prozess ist bereits im nächsten Abschnitt.");  
        }
    }
    
    public void saveNow(BmwParticipantsController participantController) {
        try {

            
            // to get the id of the inserted event immediately a transacion has to be executed
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            if (transaction.getStatus() == Status.STATUS_NO_TRANSACTION) {
                transaction.begin();
            }

            try {
                EntityManager em = ((BmwParticipantsFacade) participantController.getFacade()).getEntityManager();
                em.persist(participantController.getSelected());
                transaction.commit();
            } catch (Exception ex) {
                Logger.getLogger(ProcessParticipants.class.getName()).log(Level.SEVERE, null, ex);
                transaction.rollback();
            }

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Participant speichern", "Participant erfolgreich erstellt!"));

        } catch (NamingException ex) {
            Logger.getLogger(ProcessParticipants.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException ex) {
            Logger.getLogger(ProcessParticipants.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(ProcessParticipants.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ProcessParticipants.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(ProcessParticipants.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void inviteParticpantManually(BmwEvent event, BmwParticipants participant) {

        InvitationProcess process = new InvitationProcess(event, InvitationProcess.PROCESSES[0]);
        if ((Boolean) process.getVariable(InvitationProcess.ACTIVITI_INVITATION_STARTED)) {
            // Teilnehmer wird nachgeladen
            InvitationProcess.startSingleProcess(event, participant);

            MessageService.showInfo(FacesContext.getCurrentInstance(), "Der Teilnehmer " + participant + " wurde nachgeladen.");

        }
    }

    public void confirmSelectiveInvitation(BmwParticipants participant, BmwParticipants invitedParticipant) {

        InvitationProcess process = new InvitationProcess(participant, InvitationProcess.PROCESSES[1]);

        process.setVariable(InvitationProcess.DATABASE_NEXT_PARTICIPANTID, invitedParticipant.getId());

        if (process.resumeProcess(ACTIVITI_SELECTIVE_INVITATION)) {

            MessageService.showInfo(FacesContext.getCurrentInstance(), "Der Prozess wird fortgefahren.");

        } else {
            MessageService.showError(FacesContext.getCurrentInstance(), "Der Prozess kann nicht fortgesetzt werden." + (process.getCurrentActivity() != null ? " current Activity: " + process.getCurrentActivity() : ""));
        }

    }
}
