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
public class NotifyForSpecialFlight implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {


        System.out.println("################# sending special flight notification mails #################");


        BmwParticipants participant = (BmwParticipants) PersistenceService.loadByInteger(BmwParticipantsController.class, execution.getVariable(InvitationProcess.DATABASE_PARTICIPANTID));
        BmwEvent event = (BmwEvent) PersistenceService.loadByInteger(BmwEventController.class, execution.getVariable(InvitationProcess.DATABASE_EVENTID));

        try {

                // @todo implementMailFunction
//            MailService.sendMail(null, null, null);

                String mailSentMessage = "Es wurde eine Notification über eine Spezialflug des Teilnehmers " + participant.getUserId().getPersonenID().getVorname() + " " + participant.getUserId().getPersonenID().getNachname() + " an den Mitarbeiter " + event.getResponsibleUser() + " gesendet.";

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
}