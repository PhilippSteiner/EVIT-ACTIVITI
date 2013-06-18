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
import ac.at.fhkufstein.mailing.MailService;
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
    public void execute(DelegateExecution execution) throws Exception {


        System.out.println("################# sending invitation mails #################");


        execution.getVariable(InvitationProcess.DATABASE_PARTICIPANTID);
        BmwParticipantsController participantController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);
        BmwParticipants participant = participantController.getFacade().find( Integer.parseInt(String.valueOf( execution.getVariable(InvitationProcess.DATABASE_PARTICIPANTID) )) );


//        MailService.sendMail(null, null, null);

        
        String mailSentMessage = "Email wurde an Teilnehmer "+participant.getUserId().getPersonenID().getVorname()+" "+participant.getUserId().getPersonenID().getNachname()+" gesendet.";

        System.out.println(mailSentMessage);
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(mailSentMessage));
    }
}