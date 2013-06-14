package ac.at.fhkufstein.activiti;

import ac.at.fhkufstein.entity.BmwEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.activiti.engine.runtime.ProcessInstance;
import java.util.Iterator;

public class InvitationProcess {

    final static public String DATABASE_EVENTID = "eventID";
    final static public String PROCESS_DEFINITION = "InvitationProcess";
    final static public String PROCESS_FILE = "diagrams/" + PROCESS_DEFINITION + ".bpmn";
    private String processDefinitionId;
    private BmwEvent event;

    public InvitationProcess(BmwEvent event) {
        this.event = event;
    }

    public void startProcess() throws Exception {

        ProcessInstance processInstance = Services.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION);
        event.setProcessId(Integer.valueOf(processInstance.getId()));

        Services.getRuntimeService().setVariable(processInstance.getId(), DATABASE_EVENTID, event.getId());

        setProcessDefinitionId(processInstance.getProcessDefinitionId());

        System.out.println("Proccess Instance #" + event.getProcessId() + " started");
    }

    public String getStartFormKey() {

        String processDefinitionId = Services.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(PROCESS_DEFINITION).latestVersion().singleResult().getId();
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
}