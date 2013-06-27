/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.BMWJournalist;

/**
 *
 * @author wolfgangteves
 */
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.login.doLogin;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/*
 * This Class handles Fileuploads: It saves files in a specific folder with subfolders named after the event id
 */
@ManagedBean(name = "JournalistFiles")
@ViewScoped
public class JournalistFiles {

    String dir;

    //This is how you set a property in java
    //System.setProperty("event_upl_path", "/Volumes/Macintosh HD/Uploads");

    /*String getPropertyStringRaw = System.getProperty("event_upl_path");
     String getPropertyString = getPropertyStringRaw.replace("\\", "\\\\");*/
    public JournalistFiles() {

        BmwParticipantsController bmwParticipantsController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwParticipantsController}", BmwParticipantsController.class);

        JournalistBean currentJournalistBean = PersistenceService.getManagedBeanInstance(JournalistBean.class);

        doLogin currentlogin = PersistenceService.getManagedBeanInstance(doLogin.class);

        EntityManager em = ((BmwParticipantsFacade) bmwParticipantsController.getFacade()).getEntityManager();

        System.out.println("Parameter Event: " + currentJournalistBean.getSelectedBmwEvent().getName());
        System.out.println("Parameter UserID: " + PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()));

        BmwParticipants currentPartipantsStati = (BmwParticipants) em.createNamedQuery("BmwParticipants.findByEventIdAndUserId")
                .setParameter("id", currentJournalistBean.getSelectedBmwEvent())
                .setParameter("userId", PersistenceService.getManagedBeanInstance(BmwUserController.class).getFacade().find(currentlogin.getUid()))
                .getSingleResult();


        this.dir = System.getProperty("event_upl_path") + "/" + currentPartipantsStati.getEventId().getId().toString();//Path where the files are saved
        //String dir = getPropertyString + "/" + eventID.toString();//Path where the files are saved

    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {

        System.out.println("Saving:" + event.getFile().getFileName());

        Boolean success = (new File(dir)).mkdirs();//Create if needed
        if (success) {
            System.out.println("Folder Created");
        }


        if (new File(dir + "/", event.getFile().getFileName()).createNewFile()) {//create File
            System.out.println("File Created");
        } else {
            System.out.println("Cant Create");
        }

        InputStream input = event.getFile().getInputstream();//Get File contents
        OutputStream output = new FileOutputStream(new File(dir, event.getFile().getFileName()));//prepare write

        try {
            IOUtils.copy(input, output);//write
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
        //Send Message
        FacesContext facesContext2 = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage("Erfolg", event.getFile().getFileName() + " wurde erfolgreich hochgeladen.");
        facesContext2.addMessage(null, msg);

    }

    public List<File> getEventFolder() {

        //System.setProperty("event_upl_path", "C:\\BMW_Folder");
        //System.out.println("Property set to: "+System.getProperty("event_upl_path"));

        final File folder = new File(dir);
        return listFilesForFolder(folder);
    }

    public List<File> listFilesForFolder(final File folder) {
        List<File> files = new ArrayList<File>();
        if (folder.listFiles().length > 0) {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    files.add(fileEntry);
                    System.out.println(fileEntry.getName());
                }
            }
        } else {
            System.out.println("Keine Dateien Vorhanden");
        }
        return files;
    }

    public StreamedContent getCurrentEventFile(String name) {

        try {

            System.out.println("Opening File..." + name);

            File f = new File(dir + "/" + name);
            System.out.println("Got File" + f.getName());
            InputStream stream = new FileInputStream(dir + "/" + name);

            return new DefaultStreamedContent(stream, "application", f.getName());
        } catch (Exception e) {
            System.out.println("File nicht gefunden");
            return null;
        }
    }

}
