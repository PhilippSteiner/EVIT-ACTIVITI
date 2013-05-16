package org.activiti.designer.myprocess;

import ac.at.fhkufstein.webprocess.testbean;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.SessionScoped;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

@SessionScoped
public class ProcessMyProcess {

    private String filename = "diagram/MyProcess.bpmn";
    private ProcessInstance myProcess;

    public void startProcess() throws Exception {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource(filename)
                .deploy();


        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("name", "Activiti");

        testbean.addInfo("start Process");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variableMap);


        assertNotNull(processInstance.getId());



        final String pid = processInstance.getId();


        System.out.println("id " + processInstance.getId() + " "
                + processInstance.getProcessDefinitionId());


        System.out.println("Waiting in usertask1");
        for (String activityIds : runtimeService.getActiveActivityIds(pid)) {

            System.out.println("'" + activityIds);
        }


        System.out.println("proceed");
        
        runtimeService.signal(pid);

        for (String activityIds : runtimeService.getActiveActivityIds(pid)) {

            System.out.println("'" + activityIds);
        }


        System.out.println("proceed");

        runtimeService.signal(pid);


        System.out.println("active processes: "+runtimeService.createProcessInstanceQuery().processInstanceId(pid).list().size());


        testbean.addInfo("Process finished");



    }

    /**
     * @return the myProcess
     */
    public ProcessInstance getMyProcess() {

        System.out.println("*******");
        return myProcess;
    }

    /**
     * @param myProcess the myProcess to set
     */
    public void setMyProcess(ProcessInstance myProcess) {
        this.myProcess = myProcess;
    }
}