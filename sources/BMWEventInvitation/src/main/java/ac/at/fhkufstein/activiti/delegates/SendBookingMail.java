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
public class SendBookingMail implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("################# sending booking mail #################");


        BmwEvent event = (BmwEvent) PersistenceService.loadByInteger(BmwEventController.class, execution.getVariable(InvitationProcess.DATABASE_EVENTID));

        // send notification for manual invitation

        String emailType = "booking";

        EmailTemplates mailTemplate = (EmailTemplates) PersistenceService.getManagedBeanInstance(EmailTemplatesController.class).getFacade().getEntityManager().createNamedQuery("EmailTemplates.findByEventIdAndType")
                .setParameter("eventId", event)
                .setParameter("type", emailType)
                .getSingleResult();

        NotificationService.parseTemplateNonJournalist(event.getTravelAgency(), mailTemplate, execution.getVariable(InvitationProcess.DATABASE_LOGIN_UID));


        String mailSentMessage = "Email wurde an das Reisebüro " + event.getTravelAgency().getCompanyName() + " gesendet.";

        MessageService.showInfo(FacesContext.getCurrentInstance(), mailSentMessage);
    }
}