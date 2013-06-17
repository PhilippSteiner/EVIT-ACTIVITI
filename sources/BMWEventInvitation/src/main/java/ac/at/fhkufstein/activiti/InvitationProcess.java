package ac.at.fhkufstein.activiti;

import ac.at.fhkufstein.entity.BmwEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.activiti.engine.runtime.ProcessInstance;
import java.util.Iterator;

public class InvitationProcess {

    final static public String DATABASE_EVENTID = "eventID";
    final static public String DATABASE_PARTICIPANTID = "participantID";
    final static public String ACTIVITI_CANCEL_INVITATION_TIME = "cancelInvitationTime";
    final static public String ACTIVITI_REMINDER_SENT = "reminderSent";
    final static public String ACTIVITI_EVENT_IS_OPEN = "eventIsOpen";
    final static public String ACTIVITI_INVITATION_ACCEPTED = "invitationAccepted";
    final static public String[] PROCESSES = {"InvitationProcess", "Journalist_Invitation_Response"};
    final static public String PROCESS_FILE_LOCATION = "diagrams/";
    final static public String SUFFIX = ".bpmn";
    private String processDefinition;
    private String processDefinitionId;
    private ProcessInstance processInstance;
    private BmwEvent event;

    public InvitationProcess(BmwEvent event, String processDefinition) {
        this.event = event;
        this.processDefinition = processDefinition;
    }

    public void startProcess() throws Exception {

        setProcessInstance(Services.getRuntimeService().startProcessInstanceByKey(PROCESS_FILE_LOCATION+processDefinition+SUFFIX));

        // nur bei Hauptprozess
        if(processDefinition.equals(PROCESSES[0])) {
            event.setProcessId(Integer.valueOf(getProcessInstance().getId()));
        }

        setVariable(DATABASE_EVENTID, event.getId());

        setProcessDefinitionId(getProcessInstance().getProcessDefinitionId());

        System.out.println("Proccess Instance #" + event.getProcessId() + " started");
    }

    public void setVariable(String name, Object value) {
        Services.getRuntimeService().setVariable(getProcessInstance().getId(), name, value);
    }

    public void getVariable(String name) {

    }

    public String getStartFormKey() {

        String processDefinitionId = Services.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(processDefinition).latestVersion().singleResult().getId();
        String formKey = Services.getFormService().getStartFormKey(processDefinitionId);

        System.out.println("start task with form " + formKey);

        return formKey;
    }

    public String getNextFormKey() {

        if (event.getProcessId() == null) {
            try {
                startProcess();
            } catch (Exception ex) {
                Logger.getLogger(InvitationProcess.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            resumeProcess();
        }

        String formKey;

        try {
            formKey = Services.getFormService().getTaskFormKey(processDefinitionId, getCurrentActivity());

            System.out.println("task with form " + formKey);
        } catch (org.activiti.engine.ActivitiIllegalArgumentException ex) {
            formKey = "noformkey";
        }

        return formKey;
    }

    public void resumeProcess() {
        System.out.println("resume Process with Id " + event.getProcessId());
        Services.getRuntimeService().signal(String.valueOf(event.getProcessId()));
    }

    public boolean processStarted() {
        return event.getProcessId() != null;
    }

    /**
     * @return the processDefinitionId
     */
    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    /**
     * @param processDefinitionId the processDefinitionId to set
     */
    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    /**
     * @return the currentActivity
     */
    public String getCurrentActivity() {
        try {
            return Services.getRuntimeService().createProcessInstanceQuery().processInstanceId(String.valueOf(event.getProcessId())).singleResult().getActivityId();

        } catch (NullPointerException ex) {
            return null;
        }
    }

    /**
     * @return the processInstance
     */
    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    /**
     * @param processInstance the processInstance to set
     */
    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }
}