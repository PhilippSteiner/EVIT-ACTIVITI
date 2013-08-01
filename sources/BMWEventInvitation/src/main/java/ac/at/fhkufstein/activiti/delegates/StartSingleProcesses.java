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
import java.util.Iterator;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *
 * @author mike
 */
public class StartSingleProcesses implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {


        System.out.println("################# starting journalist processes #################");


        execution.setVariable(InvitationProcess.ACTIVITI_INVITATION_STARTED, true);

        BmwEventController eventController = PersistenceService.getManagedBeanInstance(BmwEventController.class);
        BmwEvent event = eventController.getFacade().find( Integer.parseInt(String.valueOf( execution.getVariable(InvitationProcess.DATABASE_EVENTID) )) );

        for (Iterator it = PersistenceService.getManagedBeanInstance(BmwParticipantsController.class).getFacade().getEntityManager().createNamedQuery("BmwParticipants.findByEventId").setParameter("id", event).getResultList().iterator(); it.hasNext();) {

            BmwParticipants participant = (BmwParticipants) it.next();

            System.out.println("start process for participant with id:  "+participant.getId());
            
            InvitationProcess.startSingleProcess(event, participant);
        }

    }
}