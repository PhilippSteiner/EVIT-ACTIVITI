/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.BMWJournalist;

/**
 *
 * @author Alex
 */
import java.io.InputStream;  
import javax.faces.context.FacesContext;  
import javax.servlet.ServletContext;  
  
import org.primefaces.model.DefaultStreamedContent;  
import org.primefaces.model.StreamedContent;  
  
public class FileDownloadController {  
  
    private StreamedContent file;  
      
    public FileDownloadController() {          
        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/BMW_Folder/8/test.png");  
        file = new DefaultStreamedContent(stream, "image/png", "test.png");  
    }  
  
    public StreamedContent getFile() {  
        return file;  
    }    
}  