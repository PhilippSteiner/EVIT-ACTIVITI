/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.EventFunc;

import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.EmailTemplatesController;
import ac.at.fhkufstein.entity.EmailTemplates;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Philipp
 */
@ManagedBean(name="emailTemplateBean")
@RequestScoped
public class emailTemplatebean {

    private EmailTemplatesController emailController;
    private BmwEventController eventController;
    private Integer eventID;
    private List<EmailTemplates> list;
    
    /**
     * Creates a new instance of emailTemplatebean
     */
    public emailTemplatebean() {
        
        emailController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{emailTemplatesController}", EmailTemplatesController.class);
        eventController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwEventController}", BmwEventController.class);
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.eventID = (Integer) Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("eventID"));

    }
    
    public List<EmailTemplates> getEmails(){
    
        return emailController.getItems();
    }
    
    public List<EmailTemplates> getEventEmails(){
        
        list = new ArrayList<EmailTemplates>();
        
        for(EmailTemplates e : emailController.getItems()){
            if(eventID == e.getEid().getId()){
                list.add(e);
            }
        }
        
      
        
        return list;
    }
}
