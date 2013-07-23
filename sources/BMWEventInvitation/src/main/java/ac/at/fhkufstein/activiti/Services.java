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
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

/**
 *
 * @author mike
 */
@ApplicationScoped
public class Services {

    private static ProcessEngine processEngine;
    private final static boolean DEPLOY_PROCESS = false;

    static {
        instantiateProcessEngine();
    }

    private static void instantiateProcessEngine() {
        processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

        for (String process : InvitationProcess.PROCESSES) {

            if (DEPLOY_PROCESS) {
                for (ProcessDefinition definition : getRepositoryService().createProcessDefinitionQuery().processDefinitionName(process).list()) {
                    if (!definition.isSuspended()) {
                        getRepositoryService().suspendProcessDefinitionById(definition.getId());
                        System.err.println("Processdefintion " + definition.getName() + "(" + definition.getId() + ")" + " suspended");
                    }
                }

                deployProcess(process);

            } else {
                boolean doDeploy = true;
                for (ProcessDefinition definition : getRepositoryService().createProcessDefinitionQuery().processDefinitionName(process).list()) {
                    if (!definition.isSuspended()) {
                        doDeploy = false;
                        break;
                    }
                }
                if (doDeploy) {
                    deployProcess(process);
                }
            }

        }
    }

    public static void deployProcess(String processDefinition) {
        getRepositoryService().createDeployment()
                .addClasspathResource(InvitationProcess.PROCESS_FILE_LOCATION + processDefinition + InvitationProcess.SUFFIX)
                .name(processDefinition)
                .deploy();

        System.out.println("Deployment " + processDefinition + " created");
    }

    public static RuntimeService getRuntimeService() {
        if (processEngine == null) {
            instantiateProcessEngine();
        }
        return processEngine.getRuntimeService();
    }

    public static RepositoryService getRepositoryService() {
        if (processEngine == null) {
            instantiateProcessEngine();
        }
        return processEngine.getRepositoryService();
    }

    public static TaskService getTaskService() {
        if (processEngine == null) {
            instantiateProcessEngine();
        }
        return processEngine.getTaskService();
    }

    public static FormService getFormService() {
        if (processEngine == null) {
            instantiateProcessEngine();
        }
        return processEngine.getFormService();
    }
}
