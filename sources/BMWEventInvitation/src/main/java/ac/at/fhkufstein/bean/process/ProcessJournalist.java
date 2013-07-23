/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.bean.process;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.service.MessageService;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 *
 * @author mike
 */
@ManagedBean(name = "processJournalist")
@ViewScoped
public class ProcessJournalist implements Serializable {

    private final String ACTIVITI_ANSWER_INVITATION = "answerInvitation";
    private final String ACTIVITI_SUPPLY_TRAVEL_INFOS = "supplyTravelInfos";

    public void answerInvitation(BmwParticipants participant, boolean invitationAccepted, boolean willBeSubstituted) {

        InvitationProcess process = new InvitationProcess(participant, InvitationProcess.PROCESSES[1]);

        process.setVariable(InvitationProcess.ACTIVITI_INVITATION_ACCEPTED, invitationAccepted);
        process.setVariable(InvitationProcess.ACTIVITI_WILL_BE_SUBSTITUTED, willBeSubstituted);

        if (process.resumeProcess(ACTIVITI_ANSWER_INVITATION)) {

            MessageService.showInfo(FacesContext.getCurrentInstance(), "Der Prozess wird fortgefahren.");

        } else {

            MessageService.showError(FacesContext.getCurrentInstance(), "Der Prozess kann nicht fortgesetzt werden." + (process.getCurrentActivity() != null ? " current Activity: " + process.getCurrentActivity() : ""));
            System.out.println("Activity ID direct: " + process.getProcessInstance().getActivityId());
            System.out.println("Process is ended: " + process.getProcessInstance().isEnded());
            System.out.println("Process is suspended: " + process.getProcessInstance().isSuspended());
        }
    }

    public void supplyTravelInfos(BmwParticipants participant, boolean takesFlight, boolean takesPredefinedFlight) {

        InvitationProcess process = new InvitationProcess(participant, InvitationProcess.PROCESSES[1]);

        process.setVariable(InvitationProcess.ACTIVITI_TAKES_FLIGHT, takesFlight);
        process.setVariable(InvitationProcess.ACTIVITI_TAKES_PREDEFINED_FLIGHT, takesPredefinedFlight);

        if (process.resumeProcess(ACTIVITI_SUPPLY_TRAVEL_INFOS)) {

            MessageService.showInfo(FacesContext.getCurrentInstance(), "Der Prozess wird fortgefahren.");

        } else {
            MessageService.showError(FacesContext.getCurrentInstance(), "Der Prozess kann nicht fortgesetzt werden." + (process.getCurrentActivity() != null ? " current Activity: " + process.getCurrentActivity() : ""));
        }
    }
}
