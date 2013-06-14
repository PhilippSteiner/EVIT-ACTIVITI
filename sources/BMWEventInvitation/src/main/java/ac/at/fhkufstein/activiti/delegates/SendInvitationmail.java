/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.activiti.delegates;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
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


        BmwEventController eventController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwEventController}", BmwEventController.class);
        BmwEvent event = eventController.getFacade().find( Integer.parseInt(String.valueOf( execution.getVariable(InvitationProcess.DATABASE_EVENTID) )) );


        for(BmwParticipants participant : event.getBmwParticipantsCollection()) {


            System.out.println("################# mailing invitation to person #"+participant.getUserId().getUid()+" #################");
        }
    }
}