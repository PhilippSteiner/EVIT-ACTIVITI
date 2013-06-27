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

/**
 *
 * @author mike
 */
@ManagedBean(name = "processJournalist")
@ViewScoped
public class ProcessJournalist {

    private final String ACTIVITI_ANSWER_INVITATION = "answerInvitation";
    private final String ACTIVITI_SUPPLY_TRAVEL_INFOS = "supplyTravelInfos";

    public void answerInvitation(BmwParticipants participant, boolean invitationAccepted, boolean willBeSubstituted) {

        InvitationProcess process = new InvitationProcess(participant, InvitationProcess.PROCESSES[1]);
        if(process.getCurrentActivity() != null && process.getCurrentActivity().equals(ACTIVITI_ANSWER_INVITATION)) {

            process.setVariable(InvitationProcess.ACTIVITI_INVITATION_ACCEPTED, invitationAccepted);
            process.setVariable(InvitationProcess.ACTIVITI_WILL_BE_SUBSTITUTED, willBeSubstituted);

            process.resumeProcess();

            MessageService.showInfo(FacesContext.getCurrentInstance(), "Der Prozess wird fortgefahren.");
        } else {
            MessageService.showError(FacesContext.getCurrentInstance(), "Der Prozess kann nicht fortgesetzt werden." + (process.getCurrentActivity() != null ? " current Activity: "+process.getCurrentActivity()  : ""));
        }
    }

    public void supplyTravelInfos(BmwParticipants participant, boolean takesFlight, boolean takesPredefinedFlight) {

        InvitationProcess process = new InvitationProcess(participant, InvitationProcess.PROCESSES[1]);
        if(process.getCurrentActivity() != null && process.getCurrentActivity().equals(ACTIVITI_SUPPLY_TRAVEL_INFOS)) {

            process.setVariable(InvitationProcess.ACTIVITI_TAKES_FLIGHT, takesFlight);
            process.setVariable(InvitationProcess.ACTIVITI_TAKES_PREDEFINED_FLIGHT, takesPredefinedFlight);

            process.resumeProcess();

            MessageService.showInfo(FacesContext.getCurrentInstance(), "Der Prozess wird fortgefahren.");
        } else {
            MessageService.showError(FacesContext.getCurrentInstance(), "Der Prozess kann nicht fortgesetzt werden." + (process.getCurrentActivity() != null ? " current Activity: "+process.getCurrentActivity()  : ""));
        }
    }

}
