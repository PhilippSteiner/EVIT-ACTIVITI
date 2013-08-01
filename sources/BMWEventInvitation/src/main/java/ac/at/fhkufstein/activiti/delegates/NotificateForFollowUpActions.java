/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.activiti.delegates;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.EmailTemplatesController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.EmailTemplates;
import ac.at.fhkufstein.mailing.NotificationService;
import ac.at.fhkufstein.service.MessageService;
import ac.at.fhkufstein.service.PersistenceService;
import javax.faces.context.FacesContext;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *
 * @author mike
 */
public class NotificateForFollowUpActions implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("################# sending notification for follow up actions #################");


        BmwEvent event = (BmwEvent) PersistenceService.loadByInteger(BmwEventController.class, execution.getVariable(InvitationProcess.DATABASE_EVENTID));

        // send notification mail for follow up actions

        String emailType = "followup";

        EmailTemplates mailTemplate = (EmailTemplates) PersistenceService.getManagedBeanInstance(EmailTemplatesController.class).getFacade().getEntityManager().createNamedQuery("EmailTemplates.findByEventIdAndType")
                .setParameter("eventId", event)
                .setParameter("type", emailType)
                .getSingleResult();

        NotificationService.parseTemplateByMailAddress(event.getResponsibleUser(), mailTemplate, execution.getVariable(InvitationProcess.DATABASE_LOGIN_UID));


        String mailSentMessage = "Notification für FollowUp Actions wurde an den Mitarbeiter " + event.getResponsibleUser() + " gesendet.";

        MessageService.showInfo(FacesContext.getCurrentInstance(), mailSentMessage);
    }
}