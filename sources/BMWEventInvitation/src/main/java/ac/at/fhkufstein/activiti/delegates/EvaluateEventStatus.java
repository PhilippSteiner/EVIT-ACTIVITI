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
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *
 * @author mike
 */
public class EvaluateEventStatus implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {


        System.out.println("################# evaluate event status #################");


        BmwParticipants participant = (BmwParticipants) PersistenceService.loadByInteger(BmwParticipantsController.class, execution.getVariable(InvitationProcess.DATABASE_PARTICIPANTID));
        BmwEvent event = (BmwEvent) PersistenceService.loadByInteger(BmwEventController.class, execution.getVariable(InvitationProcess.DATABASE_EVENTID));

        if(new Date().getTime() >= event.getCloseInvitation().getTime()) {
            execution.setVariable(InvitationProcess.ACTIVITI_EVENT_IS_OPEN, false);
        } else {
            execution.setVariable(InvitationProcess.ACTIVITI_EVENT_IS_OPEN, true);
        }

    }
}