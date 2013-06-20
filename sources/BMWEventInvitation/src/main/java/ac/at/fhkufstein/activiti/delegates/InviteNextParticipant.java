/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.activiti.delegates;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.mailing.MailService;
import ac.at.fhkufstein.service.PersistenceService;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Persistence;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *
 * @author mike
 */
@ManagedBean
@RequestScoped
public class InviteNextParticipant implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {


        System.out.println("################# invite next participant #################");


        execution.getVariable(InvitationProcess.DATABASE_PARTICIPANTID);
        BmwEvent event = (BmwEvent) PersistenceService.loadByInteger(BmwEventController.class, execution.getVariable(InvitationProcess.DATABASE_EVENTID));

        BmwUser user = InvitationProcess.getNextParticipant(event);

        if (user == null) {
            String noUserMessage = "Es befindet sich kein User mehr in der Warteschlange.";

            System.out.println(noUserMessage);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(noUserMessage));
        } else {

            // save next Participant
            BmwParticipants participant = PersistenceService.getManagedBeanInstance(BmwParticipantsController.class).prepareCreate(null);
            participant.setEventId(event);
            participant.setUserId(user);
            participant.setPState("invited");


            PersistenceService.save(BmwParticipantsController.class, participant);

            // start new process
            InvitationProcess.startSingleProcess(event, participant);

            String nextParticipantInvited = "Der Teilnehmer " + participant.getUserId().getPersonenID().getVorname() + " " + participant.getUserId().getPersonenID().getNachname() + " wurde nachgeladen.";

            System.out.println(nextParticipantInvited);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(nextParticipantInvited));
        }
    }
}