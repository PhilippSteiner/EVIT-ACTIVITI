/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Alex
 */
@ManagedBean(name = "EventBean")
@RequestScoped
public final class EventBean implements Serializable{


    private List<Event> upcoming;
    private List<Event> post;
    
    private final static String[] upnames;
    private final static String[] upvon;
    private final static String[] upbis;
    private final static String[] uport;
    private final static int[] upkap;
    private final static int[] upzus;
    //
    private final static String[] postnames;
    private final static String[] postvon;
    private final static String[] postbis;
    private final static String[] postort;

    static {

        upnames = new String[4];
        upnames[0] = "X5 Österreich Premiere";
        upnames[1] = "Vorstellung 3er Touring";
        upnames[2] = "X1 Powder Ride";
        upnames[3] = "6er Cabrio Premiere";

        upvon = new String[4];
        upvon[0] = "12.05.13";
        upvon[1] = "20.05.13";
        upvon[2] = "25.05.13";
        upvon[3] = "23.06.13";

        upbis = new String[4];
        upbis[0] = "14.05.13";
        upbis[1] = "23.05.13";
        upbis[2] = "26.05.13";
        upbis[3] = "24.06.13";

        uport = new String[4];
        uport[0] = "Salzburg";
        uport[1] = "Wien";
        uport[2] = "Kufstein";
        uport[3] = "Wien";

        upkap = new int[4];
        upkap[0] = 20;
        upkap[1] = 25;
        upkap[2] = 15;
        upkap[3] = 30;

        upzus = new int[4];
        upzus[0] = 10;
        upzus[1] = 22;
        upzus[2] = 9;
        upzus[3] = 27;



        //Post

        postnames = new String[4];
        postnames[3] = "X5 Österreich Premiere";
        postnames[2] = "Vorstellung 3er Touring";
        postnames[1] = "X1 Powder Ride";
        postnames[0] = "6er Cabrio Premiere";

        postvon = new String[4];
        postvon[0] = "12.05.13";
        postvon[1] = "20.05.13";
        postvon[2] = "25.05.13";
        postvon[3] = "23.06.13";

        postbis = new String[4];
        postbis[0] = "14.05.13";
        postbis[1] = "23.05.13";
        postbis[2] = "26.05.13";
        postbis[3] = "24.06.13";

        postort = new String[4];
        postort[0] = "Salzburg";
        postort[1] = "Wien";
        postort[2] = "Kufstein";
        postort[3] = "Wien";


    }

    public EventBean(List<Event> upcoming, List<Event> post) {
        this.upcoming = upcoming;
        this.post = post;
    }

    
    

    public EventBean() {

        upcoming = new ArrayList<Event>();
        post = new ArrayList<Event>();

        this.makeArrays();
    }
    
     public void makeArrays(){
     
        for (int i = 0; i <= 3; i++) {

            upcoming.add(new Event(upnames[i], upvon[i], upbis[i], uport[i], upkap[i], upzus[i]));
        
        }

        for (int i = 0; i <= 3; i++) {

            post.add(new Event(postnames[i], postvon[i], postbis[i], postort[i]));
        
        } 
     
     }

    public List<Event> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<Event> upcoming) {
        this.upcoming = upcoming;
    }

    public List<Event> getPost() {
        return post;
    }

    public void setPost(List<Event> post) {
        this.post = post;
    }
    
    public String detailview(){
        
        return "event";
        
    }
    
    public void saveEvent(){
    
        System.out.println("Event Saved");
    }
    

     public void handleFileUpload(FileUploadEvent event) {

        System.out.println("UPLOAD PERFORMED with FILE");

        FacesMessage msg = new FacesMessage("File Größe ", ""+event.getFile().getFileName()+ " ist "+event.getFile().getSize()+" KB groß");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        try {
            File targetFolder = new File("C:\\inputStream");
            InputStream inputStream = event.getFile().getInputstream();
            OutputStream out = new FileOutputStream(new File(targetFolder,
                    event.getFile().getFileName()));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            inputStream.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}