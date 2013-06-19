/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.bmweventinvitation;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.persistence.PersistenceService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class InvitationEvent {

    private InvitationProcess process;

    public InvitationEvent() {

        BmwEventController eventController = PersistenceService.getControllerInstance(BmwEventController.class);


        process = new InvitationProcess( eventController.getFacade().find(2), InvitationProcess.PROCESSES[0] );
    }

    public void startProcess() {
        try {
            getProcess().startProcess();
        } catch (Exception ex) {
            Logger.getLogger(InvitationEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resumeProcess() {
        getProcess().resumeProcess();
    }

    public String getStartFormKey() {
        return getProcess().getStartFormKey();
    }

    public String getNextFormKey() {
        return getProcess().getNextFormKey();
    }

    /**
     * @return the process
     */
    public InvitationProcess getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(InvitationProcess process) {
        this.process = process;
    }
}
