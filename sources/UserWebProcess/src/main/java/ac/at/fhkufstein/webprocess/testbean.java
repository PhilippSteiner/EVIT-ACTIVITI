/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.webprocess;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.activiti.designer.myprocess.ProcessMyProcess;
import org.primefaces.context.RequestContext;

/**
 *
 * @author mike
 */
@ManagedBean
public class testbean {

    private static String info = "";
    private static boolean finished = false;


    public boolean start() {


                try {
//                    FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{MyProcess}", ProcessMyProcess.class).startProcess();
                    new ProcessMyProcess().startProcess();
                } catch (Exception ex) {
                    Logger.getLogger(testbean.class.getName()).log(Level.SEVERE, null, ex);
                }

        return true;
    }

    public void refresh() {
        if(isFinished()) {
            RequestContext.getCurrentInstance().addCallbackParam("stopPolling", true);
        }
    }
    public synchronized static void reset() {
        info = "";
        setFinished(false);
        RequestContext.getCurrentInstance().addCallbackParam("stopPolling", false);
    }

    public synchronized String getInfo() {
        return info;
    }

    public synchronized static void addInfo(String _info) {
        info += _info + "<br />";
    }

    public synchronized static void setFinished(boolean _finished) {
        finished = _finished;
    }

    public synchronized boolean isFinished() {
        return finished;
    }
}
