package org.activiti.designer.test;

import ac.at.fhkufstein.activiti.InvitationProcess;
import static ac.at.fhkufstein.activiti.InvitationProcess.formatActivitiDate;
import static ac.at.fhkufstein.activiti.InvitationProcess.getDaysInMilliSeconds;
import ac.at.fhkufstein.activiti.Services;
import static ac.at.fhkufstein.activiti.Services.getRepositoryService;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestMyProcess {

    final static public String DATABASE_EVENTID = "eventID";
    final static public String DATABASE_PARTICIPANTID = "participantId";
    final static public String ACTIVITI_CANCEL_INVITATION_TIME = "cancelInvitationTime";
    final static public String ACTIVITI_FOLLOW_UP_TIME = "followUpTime";
    final static public String ACTIVITI_INVITATION_SENT = "invitationSent";
    final static public String ACTIVITI_REMINDER_SENT = "reminderSent";
    final static public String ACTIVITI_EVENT_IS_OPEN = "eventIsOpen";
    final static public String ACTIVITI_INVITATION_ACCEPTED = "invitationAccepted";
    final static public String ACTIVITI_WILL_BE_SUBSTITUTED = "willBeSubstituted";
    final static public String ACTIVITI_TAKES_FLIGHT = "takesFlight";
    final static public String ACTIVITI_TAKES_PREDEFINED_FLIGHT = "takesPredefinedFlight";
    final static public String ACTIVITI_SIGNAL_VARIABLES_DEFINED = "variablesDefined";
    final static public String ACTIVITI_INVITATION_STARTED = "invitationStarted";
    final static public String DATABASE_NEXT_PARTICIPANTID = "nextParticipantId";
    final static public String[] PROCESSES = {"InvitationProcess"};
    final static public String PROCESS_FILE_LOCATION = "diagrams/";
    final static public String SUFFIX = ".bpmn";
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    public static void main(String[] args) throws Exception {
        new ProcessTestMyProcess().startProcess();
    }

    public void startProcess() throws Exception {



        for (Deployment deploy : Services.getRepositoryService().createDeploymentQuery().list()) {
            System.out.println(deploy.getId() + " - " + deploy.getName() + " - " + deploy.getDeploymentTime());
        }
        ProcessInstance process = Services.getRuntimeService().startProcessInstanceByKey(PROCESSES[0]);




//        processHolder.setProcessId(Integer.valueOf(getProcessInstance().getProcessInstanceId()));
        //Progress wird verwendet um den Status des Prozesses zu kontrollieren
//        processHolder.setProgress(10);
        // nur bei Hauptprozess

        Services.getRuntimeService().setVariable(process.getProcessInstanceId(), ACTIVITI_INVITATION_STARTED, false);



        Services.getRuntimeService().setVariable(process.getProcessInstanceId(), ACTIVITI_FOLLOW_UP_TIME, formatActivitiDate(new Date().getTime() + 10000));


        System.out.println("Proccess Instance # " + process.getProcessInstanceId() + " started");

        getCurrentActivity(process.getProcessInstanceId());

        resumeProcess(process.getProcessInstanceId());

        getCurrentActivity(process.getProcessInstanceId());

        resumeProcess(process.getProcessInstanceId());

        getCurrentActivity(process.getProcessInstanceId());
        
       ProcessInstance p2 =  Services.getRuntimeService().createProcessInstanceQuery().processInstanceId(process.getProcessInstanceId()).singleResult();
       
       System.out.println("process bei processid von datenbank: "+p2.getProcessInstanceId());

    }

    public static void setVariable(String id, String name, Object value) {
        Services.getRuntimeService().setVariable(id, name, value);
    }

    public static Object getVariable(String id, String name) {
        return Services.getRuntimeService().getVariable(id, name);
    }

    public void resumeProcess(String id) {
        System.out.println("Resume Process with Id " + id);
        Services.getRuntimeService().signal(id);
    }

    /**
     * @return the currentActivity
     */
    public String getCurrentActivity(String id) {
        try {
            String currentActivity = Services.getRuntimeService().createProcessInstanceQuery().processInstanceId(String.valueOf(id)).singleResult().getActivityId();
            System.out.println("Current Activity: " + currentActivity);
            return currentActivity;

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}