/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.bean.process;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.service.MessageService;
import ac.at.fhkufstein.service.PersistenceService;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author mike
 */
@ManagedBean(name = "processBmwEventController")
@ViewScoped
public class ProcessBmwEventController implements Serializable {

    private static final String ACTIVITI_RELEASE_ACTIVITY = "releaseEvent";

    public void saveNew(ActionEvent event) {

        BmwEventController eventController = PersistenceService.getManagedBeanInstance(BmwEventController.class);

        eventController.saveNew(event);

        startEventProcess(eventController.getSelected());

    }

    public void save(ActionEvent event) {

        BmwEventController eventController = PersistenceService.getManagedBeanInstance(BmwEventController.class);

        eventController.save(event);

        if(eventController.getSelected().getProcessId() == null) {
            startEventProcess(eventController.getSelected());
        }

    }

    private void startEventProcess(BmwEvent event) {
         try {
            new InvitationProcess( event, InvitationProcess.PROCESSES[0] ).startProcess();

             MessageService.showInfo("Der Prozess für dieses Event wurde gestartet.");
        } catch (Exception ex) {
            Logger.getLogger(ProcessBmwEventController.class.getName()).log(Level.SEVERE, null, ex);

            MessageService.showError("Der Prozess für dieses Event konnte nicht gestartet werden.");

        }
    }

    public void release(BmwEvent event) {

        InvitationProcess process = new InvitationProcess(event, InvitationProcess.PROCESSES[0]);
        if(process.getCurrentActivity() != null && process.getCurrentActivity().equals(ACTIVITI_RELEASE_ACTIVITY)) {

            //@todo not usable at the moment
//            event.setReleaseEvent(true);

            process.resumeProcess();

            MessageService.showInfo("Das Event wurde freigegeben.");
            MessageService.showInfo("Der Prozess wurde fortgefahren.");
        } else {
            MessageService.showError("Das Event konnte nicht freigegeben werden.");
            MessageService.showError("Der Prozess konnte nicht fortgesetzt werden.");
        }
    }


}
