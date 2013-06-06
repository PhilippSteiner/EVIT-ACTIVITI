/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.bmweventinvitation;

import ac.at.fhkufstein.activiti.InvitationProcess;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class InvitationEvent {

    private InvitationProcess process;

    public InvitationEvent() {
        //process = new InvitationProcess();
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
