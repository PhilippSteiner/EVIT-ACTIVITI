/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.activiti.delegates;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.EmailTemplatesController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.EmailTemplates;
import ac.at.fhkufstein.mailing.NotificationService;
import ac.at.fhkufstein.service.MessageService;
import ac.at.fhkufstein.service.PersistenceService;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *
 * @author mike
 */
public class SendInvitationmail implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws NamingException, Exception {


        System.out.println("################# sending invitation mails #################");

        BmwParticipants participant = (BmwParticipants) PersistenceService.loadByInteger(BmwParticipantsController.class, execution.getVariable(InvitationProcess.DATABASE_PARTICIPANTID));
        BmwEvent event = (BmwEvent) PersistenceService.loadByInteger(BmwEventController.class, execution.getVariable(InvitationProcess.DATABASE_EVENTID));

        if ((Boolean) execution.getVariable(InvitationProcess.ACTIVITI_INVITATION_SENT) == false) {

            //erstmalige Einladung

            // send invitation mail
            String emailType = "invite";

            EmailTemplates mailTemplate = (EmailTemplates) PersistenceService.getManagedBeanInstance(EmailTemplatesController.class).getFacade().getEntityManager().createNamedQuery("EmailTemplates.findByEventIdAndType")
                    .setParameter("eventId", event)
                    .setParameter("type", emailType)
                    .getSingleResult();

            NotificationService.parseTemplate(participant.getUserId(), mailTemplate, execution.getVariable(InvitationProcess.DATABASE_LOGIN_UID));

            participant.setInvitationDate(new Date());
            PersistenceService.save(BmwParticipantsController.class, participant);
            //Event Progress wird entsprechend weitergeschalten
            event.setProgress(40);
            PersistenceService.save(BmwEventController.class, event);

            execution.setVariable(InvitationProcess.ACTIVITI_INVITATION_SENT, true);

            Long sendReminderTime = InvitationProcess.getDueTime(event, participant, event.getSendReminder());

            // @todo uncommented for testing only
            //execution.setVariable(InvitationProcess.ACTIVITI_CANCEL_INVITATION_TIME, InvitationProcess.formatActivitiDate(sendReminderTime));

            String mailSentMessage = "Es wurde eine Einladungsmail an den Teilnehmer " + participant.getUserId().getPersonenID().getVorname() + " " + participant.getUserId().getPersonenID().getNachname() + " gesendet.";

            MessageService.showInfo(FacesContext.getCurrentInstance(), mailSentMessage);
        } else {

            // Urgenzmail

            // send urgenz mail
            String emailType = "urgenz";

            EmailTemplates mailTemplate = (EmailTemplates) PersistenceService.getManagedBeanInstance(EmailTemplatesController.class).getFacade().getEntityManager().createNamedQuery("EmailTemplates.findByEventIdAndType")
                    .setParameter("eventId", event)
                    .setParameter("type", emailType)
                    .getSingleResult();

            NotificationService.parseTemplate(participant.getUserId(), mailTemplate, execution.getVariable(InvitationProcess.DATABASE_LOGIN_UID));



            execution.setVariable(InvitationProcess.ACTIVITI_REMINDER_SENT, true);

            Long cancelInvitationTime = InvitationProcess.getDueTime(event, participant, event.getCancelInvitation());

            // @todo uncommented for testing only
            //execution.setVariable(InvitationProcess.ACTIVITI_CANCEL_INVITATION_TIME, InvitationProcess.formatActivitiDate(cancelInvitationTime));

            String mailSentMessage = "Es wurde eine Urgenzmail an den Teilnehmer " + participant.getUserId().getPersonenID().getVorname() + " " + participant.getUserId().getPersonenID().getNachname() + " gesendet.";

            MessageService.showInfo(FacesContext.getCurrentInstance(), mailSentMessage);
        }

    }
}