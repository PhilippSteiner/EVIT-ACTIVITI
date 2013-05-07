package at.ac.fhkufstein.invitationprocess;

import javax.faces.bean.SessionScoped;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

@SessionScoped
public class ProcessInvitation {

    final private String PROCESS_DEFINITION = "InvitationProcess";
    final private String PROCESS_FILE = "diagrams/"+PROCESS_DEFINITION+".bpmn";

    public void startProcess() throws Exception {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource(PROCESS_FILE)
                .deploy();


        RuntimeService runtimeService = processEngine.getRuntimeService();


        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION);
        final String pid = processInstance.getId();

    }

}