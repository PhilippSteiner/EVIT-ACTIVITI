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
import ac.at.fhkufstein.service.PersistenceService;
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
public class NotifyForManualInvitation implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {


        System.out.println("################# sending manual invitation notification mails #################");


        BmwParticipants participant = (BmwParticipants) PersistenceService.loadByInteger(BmwParticipantsController.class, execution.getVariable(InvitationProcess.DATABASE_PARTICIPANTID));
        BmwEvent event = (BmwEvent) PersistenceService.loadByInteger(BmwEventController.class, execution.getVariable(InvitationProcess.DATABASE_EVENTID));

        try {

            // send notification for manual invitation

            send(event);



            String mailSentMessage = "Es wurde eine Notification über eine manuelle Nachladung an den Mitarbeiter " + event.getResponsibleUser() + " gesendet. Momentaner Teilnehmer: " + participant.getUserId().getPersonenID().getVorname() + " " + participant.getUserId().getPersonenID().getNachname();

            System.out.println(mailSentMessage);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(mailSentMessage));


        } catch (Exception ex) {
            String mailSentMessage = ex.getMessage();

            System.err.println(mailSentMessage);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mailSentMessage));
        }

    }

    public static void send(BmwEvent event) {

        String emailType = "manuel";

        EmailTemplates mailTemplate = (EmailTemplates) PersistenceService.getManagedBeanInstance(EmailTemplatesController.class).getFacade().getEntityManager().createNamedQuery("EmailTemplates.findByEventIdAndType")
                .setParameter("eventId", event)
                .setParameter("type", emailType)
                .getSingleResult();

        NotificationService.parseTemplateByMailAddress(event.getResponsibleUser(), mailTemplate);
    }
}