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
import ac.at.fhkufstein.persistence.PersistenceService;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *
 * @author mike
 */
@ManagedBean
@RequestScoped
public class SendInvitationmail implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {


        System.out.println("################# sending invitation mails #################");


        BmwParticipants participant = (BmwParticipants) PersistenceService.loadByInteger(BmwParticipantsController.class, execution.getVariable(InvitationProcess.DATABASE_PARTICIPANTID));
        BmwEvent event = (BmwEvent) PersistenceService.loadByInteger(BmwEventController.class, execution.getVariable(InvitationProcess.DATABASE_EVENTID));

        try {
            if ((Boolean) execution.getVariable(InvitationProcess.ACTIVITI_INVITATION_SENT) == false) {

                // @todo implementMailFunction
//            MailService.sendMail(null, null, null);

                execution.setVariable(InvitationProcess.ACTIVITI_INVITATION_SENT, true);

                Long sendReminderTime = InvitationProcess.getDueTime(event, participant, event.getUrgencyDayLimit());

                execution.setVariable(InvitationProcess.ACTIVITI_CANCEL_INVITATION_TIME, InvitationProcess.getActivitiDateFormat().format(new Date(sendReminderTime)));

                String mailSentMessage = "Es wurde eine Einladungsmail an den Teilnehmer " + participant.getUserId().getPersonenID().getVorname() + " " + participant.getUserId().getPersonenID().getNachname() + " gesendet.";

                System.out.println(mailSentMessage);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(mailSentMessage));
            } else {

                // @todo implementMailFunction
//            MailService.sendMail(null, null, null);

                execution.setVariable(InvitationProcess.ACTIVITI_REMINDER_SENT, true);

                Long cancelInvitationTime = InvitationProcess.getDueTime(event, participant, event.getCancelInvitation());

                execution.setVariable(InvitationProcess.ACTIVITI_CANCEL_INVITATION_TIME, InvitationProcess.getActivitiDateFormat().format(new Date(cancelInvitationTime)));

                String mailSentMessage = "Es wurde eine Urgenzmail an den Teilnehmer " + participant.getUserId().getPersonenID().getVorname() + " " + participant.getUserId().getPersonenID().getNachname() + " gesendet.";

                System.out.println(mailSentMessage);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(mailSentMessage));
            }

        } catch (Exception ex) {
            String mailSentMessage = ex.getMessage();

            System.err.println(mailSentMessage);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mailSentMessage));
        }

    }
}