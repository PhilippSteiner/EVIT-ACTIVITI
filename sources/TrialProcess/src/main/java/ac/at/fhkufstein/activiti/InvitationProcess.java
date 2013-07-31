package ac.at.fhkufstein.activiti;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.activiti.engine.runtime.ProcessInstance;
import java.util.Iterator;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.activiti.engine.runtime.Execution;

public class InvitationProcess {

    

    

    public static int getDaysInMilliSeconds(int days) {
        return days * 24 * 3600 * 1000;
    }

    public static String formatActivitiDate(Long time) {
        return new SimpleDateFormat("yyyyy-MM-dd HH:mm:ss").format(new Date(time)).replace(" ", "T"); // example: "2011-03-11T12:13:14"
    }

}