package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.EmailTemplates;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwTravelFacade;
import ac.at.fhkufstein.session.EmailTemplatesFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "emailTemplatesController")
@ViewScoped
public class EmailTemplatesController extends AbstractController<EmailTemplates> implements Serializable {

    @EJB
    private EmailTemplatesFacade ejbFacade;

    public EmailTemplatesController() {
        super(EmailTemplates.class);
    }
    
    public EmailTemplatesController(Boolean jndiLookup) {
        super(EmailTemplates.class);
        
        if(jndiLookup == true) {
            super.setFacade(PersistenceService.getFacadeJndiLookup(EmailTemplatesFacade.class));
        }
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
