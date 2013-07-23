/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.activiti;

import static ac.at.fhkufstein.activiti.InvitationProcess.ACTIVITI_SIGNAL_VARIABLES_DEFINED;
import static ac.at.fhkufstein.activiti.InvitationProcess.signalEvent;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.activiti.engine.runtime.ProcessInstance;

/**
 *
 * @author mike
 */
public class SignalProcessThread extends Thread {

    private final int MAX_TRIES = 10;
    private final int BREAK_DURATION = 15000;
    private String signalName;
    private ProcessInstance process;

    public SignalProcessThread(ProcessInstance process, String signalName) {
        this.signalName = signalName;
        this.process = process;

    }

    @Override
    public void run() {

        int tries = 0;
        while (tries < MAX_TRIES) {
            tries++;

            if (signalEvent(process, signalName)) {

                System.out.println("Signal found and executed");

                break;

            } else {

                System.err.println("Signal \"" + signalName + "\" could not be found");

                try {
                    sleep(BREAK_DURATION);
                } catch (InterruptedException ex) {
                    Logger.getLogger(InvitationProcess.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        }

    }
}
