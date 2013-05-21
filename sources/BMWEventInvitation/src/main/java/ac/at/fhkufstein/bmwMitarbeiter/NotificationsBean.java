/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Alex
 */
@ManagedBean(name = "NotifBean")
@RequestScoped
public final class NotificationsBean {

    /**
     * Creates a new instance of NotificationsBean
     */

    private List<Notif> notifs;

    private final static String[] upnames;
    private final static String[] names;
    private final static String[] verts;

    static {

        upnames = new String[8];
        upnames[0] = "X5 Österreich Premiere";
        upnames[1] = "Vorstellung 3er Touring";
        upnames[2] = "X1 Powder Ride";
        upnames[3] = "6er Cabrio Premiere";
        upnames[4] = "X1 Powder Ride";
        upnames[5] = "6er Cabrio Premiere";
        upnames[6] = "X1 Powder Ride";
        upnames[7] = "X1 Powder Ride";


        names = new String[8];
        names[0] = "Hans Meier";
        names[1] = "Susi Huber";
        names[2] = "Klaus Richter";
        names[3] = "Toni Berg";
        names[4] = "Peter Meier";
        names[5] = "Anna Klaus";
        names[6] = "Klaus Wald";
        names[7] = "Toni Müller";
        
        
        verts = new String[8];
        verts[3] = "Hans Meier";
        verts[7] = "Susi Huber";
        verts[4] = "Klaus Richter";
        verts[5] = "Toni Berg";
        verts[1] = "Peter Meier";
        verts[0] = "Anna Klaus";
        verts[2] = "Klaus Wald";
        verts[6] = "Toni Müller";



    }

    public NotificationsBean() {
        
         notifs = new ArrayList<Notif>();
         
         this.makeNotifs();
    }
    
    public void makeNotifs(){
    
        for (int i = 0; i <= 7; i++) {

           notifs.add(new Notif(upnames[i], names[i], verts[i]));
        
        }
    
    }

    public List<Notif> getNotifs() {
        return notifs;
    }

    public void setNotifs(List<Notif> notifs) {
        this.notifs = notifs;
    }
    
    

    
}
