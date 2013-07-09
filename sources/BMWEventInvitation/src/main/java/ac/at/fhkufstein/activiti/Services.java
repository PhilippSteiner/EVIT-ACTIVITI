/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.activiti;

import javax.faces.bean.ApplicationScoped;
import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

/**
 *
 * @author mike
 */
@ApplicationScoped
public class Services {

    private static ProcessEngine processEngine;

    static {
        instantiateProcessEngine();

//        if(getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(InvitationProcess.PROCESS_DEFINITION).list().isEmpty()) {
        for (String process : InvitationProcess.PROCESSES) {
            getRepositoryService().createDeployment()
                    .addClasspathResource(InvitationProcess.PROCESS_FILE_LOCATION + process + InvitationProcess.SUFFIX)
                    .deploy();
        }
//        }

    }
    
    public static void testProcess() {
        Services.getRuntimeService().createProcessInstanceQuery().processInstanceId("83923").singleResult();
        
        System.out.println("Process tested");
    }

    private static void instantiateProcessEngine() {
        processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
    }

    public static RuntimeService getRuntimeService() {
        return processEngine.getRuntimeService();
    }

    public static RepositoryService getRepositoryService() {
        return processEngine.getRepositoryService();
    }

    public static TaskService getTaskService() {
        return processEngine.getTaskService();
    }

    public static FormService getFormService() {
        return processEngine.getFormService();
    }
}
