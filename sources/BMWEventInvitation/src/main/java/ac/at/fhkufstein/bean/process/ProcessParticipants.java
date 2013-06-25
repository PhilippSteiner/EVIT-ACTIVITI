/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.bean.process;

import ac.at.fhkufstein.EventFunc.Participants;
import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.service.MessageService;
import ac.at.fhkufstein.service.PersistenceService;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author mike
 */
@ManagedBean(name = "processParticipants")
@ViewScoped
public class ProcessParticipants {

    private static final String ACTIVITI_ADD_ACTIVITY = "addJournalists";

    public void saveSelected() {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Participants participants = PersistenceService.getManagedBeanInstance(Participants.class);
        participants.saveSelected();
        facesContext.addMessage(null, new FacesMessage("Journalisten", "Journalisten eingeladen!"));

        // @todo Invite Participants which have been invited after Start

        InvitationProcess process = new InvitationProcess(participants.getCurrent(), InvitationProcess.PROCESSES[0]);
        if(process.getCurrentActivity() != null && process.getCurrentActivity().equals(ACTIVITI_ADD_ACTIVITY)) {

            process.resumeProcess();

            MessageService.showInfo("Der Prozess wird fortgefahren.");
        } else {
            MessageService.showError("Der Prozess kann nicht fortgesetzt werden." + (process.getCurrentActivity() != null ? " current Activity: "+process.getCurrentActivity()  : ""));
        }
    }
}
