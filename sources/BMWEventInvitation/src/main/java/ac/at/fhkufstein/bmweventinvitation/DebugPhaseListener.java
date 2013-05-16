/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.bmweventinvitation;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author mike
 */
public class DebugPhaseListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent pe) {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        InvitationEvent invitationEvent = (InvitationEvent) FacesContext.getCurrentInstance().getApplication()
                .getELResolver().getValue(elContext, null, "invitationEvent");
        String str = invitationEvent.getProcess().getPid() == null ? "not started" : invitationEvent.getProcess().getPid() + " last activity: " + invitationEvent.getProcess().getCurrentActivity();

        FacesContext.getCurrentInstance().addMessage("status", new FacesMessage("process " + str));
        System.out.println("process " + str);

    }

    @Override
    public void beforePhase(PhaseEvent pe) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.INVOKE_APPLICATION;
    }
}
