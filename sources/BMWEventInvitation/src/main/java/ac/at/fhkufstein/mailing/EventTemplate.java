/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.mailing;

import ac.at.fhkufstein.bean.EmailTemplatesController;
import ac.at.fhkufstein.entity.EmailTemplates;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Philipp
 */
@ManagedBean(name="eventTamplate")
@RequestScoped
public class EventTemplate {

    private EmailTemplatesController templateController;
    private EmailTemplates einladung = new EmailTemplates();
    private EmailTemplates urgenz = new EmailTemplates();
    private EmailTemplates followup = new EmailTemplates();
    
    private Integer eventID;
    
    
    
    /**
     * Creates a new instance of CreateEventTemplate
     */
    public EventTemplate() {
        
        templateController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{emailTemplateController}", EmailTemplatesController.class);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.eventID = (Integer) Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("eventID"));
    
        
        
    }
    
    public static void createTemplate(){
    
        
    
    }
    
    
}
