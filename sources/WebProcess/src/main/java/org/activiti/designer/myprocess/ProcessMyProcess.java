package org.activiti.designer.myprocess;

import ac.at.fhkufstein.webprocess.testbean;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;


public class ProcessMyProcess {

    private String filename = "diagram/MyProcess.bpmn";

    public void startProcess() throws Exception {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment().addInputStream("myProcess.bpmn20.xml",
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)
                ).deploy();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("name", "Activiti");

        testbean.addInfo("start Process");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variableMap);
        assertNotNull(processInstance.getId());
        System.out.println("id " + processInstance.getId() + " "
                + processInstance.getProcessDefinitionId());

        testbean.addInfo("Process finished");


        testbean.setFinished(true);
    }
}