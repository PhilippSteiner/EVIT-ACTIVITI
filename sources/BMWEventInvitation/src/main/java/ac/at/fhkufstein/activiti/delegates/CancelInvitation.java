/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.activiti.delegates;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.activiti.Services;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.EmailTemplatesController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.EmailTemplates;
import ac.at.fhkufstein.entity.ParticipantStatus;
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
public class CancelInvitation implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {


        System.out.println("################# sending cancel invitation mails #################");


        BmwParticipants participant = (BmwParticipants) PersistenceService.loadByInteger(BmwParticipantsController.class, execution.getVariable(InvitationProcess.DATABASE_PARTICIPANTID));
        BmwEvent event = (BmwEvent) PersistenceService.loadByInteger(BmwEventController.class, execution.getVariable(InvitationProcess.DATABASE_EVENTID));

        // make sure that the state has not changed
        if(!participant.getPState().equals(ParticipantStatus.INVITED.toString())) {
            MessageService.showInfo(FacesContext.getCurrentInstance(), "Participant has not state invited, so no invitation will be cancelled");
            return;
        }
        
        participant.setPState(ParticipantStatus.REFUSED);
        PersistenceService.save(BmwParticipantsController.class, participant);


        // send cancel mail<

        String emailType = "storno";

        EmailTemplates mailTemplate = (EmailTemplates) PersistenceService.getManagedBeanInstance(EmailTemplatesController.class).getFacade().getEntityManager().createNamedQuery("EmailTemplates.findByEventIdAndType")
                .setParameter("eventId", event)
                .setParameter("type", emailType)
                .getSingleResult();

        NotificationService.parseTemplate(participant.getUserId(), mailTemplate, execution.getVariable(InvitationProcess.DATABASE_LOGIN_UID));


        String mailSentMessage = "Es wurde Die Einladung des Teilnehmers " + participant.getUserId().getPersonenID().getVorname() + " " + participant.getUserId().getPersonenID().getNachname() + " storniert.";

        MessageService.showInfo(FacesContext.getCurrentInstance(), mailSentMessage);

    }

}