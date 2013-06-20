/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.EventFunc;

/**
 *
 * @author
 * wolfgangteves
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;

/*
 * This Class handles Fileuploads: It saves files in a specific folder with subfolders named after the event id
 */

@ManagedBean(name = "fileupload")
@ViewScoped
public class fileupload {

	FacesContext facesContext = FacesContext.getCurrentInstance();
	public Integer eventID =(Integer) Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("eventID"));
	
	public void handleFileUpload(FileUploadEvent event) throws IOException {

		System.out.println("Saving:"+event.getFile().getFileName());

		//This is how you set a property in java
		//System.setProperty("event_upl_path", "/Volumes/Macintosh HD/Uploads");
		
		String sys_path=System.getProperty("event_upl_path"); //Read Property where to save the files
		String dir = sys_path + "/" + eventID.toString();//Building path where to save
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

	public Integer getEventID() {
		
		return eventID;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
		System.out.println("Set:"+eventID);
	}
}
