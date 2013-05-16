package ac.at.fhkufstein.activiti;

import ac.at.fhkufstein.entity.Event;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.activiti.engine.runtime.ProcessInstance;
import java.util.Iterator;

public class InvitationProcess {

    final static public String PROCESS_DEFINITION = "InvitationProcess";
    final static public String PROCESS_FILE = "diagrams/"+PROCESS_DEFINITION+".bpmn";
    private String pid;
    private String processDefinitionId;
    private String currentActivity;
    private Event event;

    public InvitationProcess(Event event) {
        this.event = event;
    }

    public void startProcess() throws Exception {

        ProcessInstance processInstance = Services.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION);
        event.setProcessId(Integer.valueOf(processInstance.getId()));
        setProcessDefinitionId(processInstance.getProcessDefinitionId());

        System.out.println("Proccess Instance #"+pid+" started");
    }

    public String getStartFormKey() {

        String processDefinitionId = Services.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(PROCESS_DEFINITION).latestVersion().singleResult().getId();
        String formKey = Services.getFormService().getStartFormKey(processDefinitionId);

        System.out.println("start task with form "+formKey);

        return formKey;
    }

    public String getNextFormKey() {

        if(pid == null) {

            try {
                startProcess();
            } catch (Exception ex) {
                Logger.getLogger(InvitationProcess.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            resumeProcess();
        }

        String formKey = Services.getFormService().getTaskFormKey(processDefinitionId, getCurrentActivity());

        System.out.println("task with form "+formKey);

        return formKey;
    }

    public void resumeProcess() {
        System.out.println("resume Process with Id "+pid);
        Services.getRuntimeService().signal(pid);
    }

    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean processStarted() {
        return pid != null;
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
        return Services.getRuntimeService().createProcessInstanceQuery().processInstanceId(pid).singleResult().getActivityId();
    }
}