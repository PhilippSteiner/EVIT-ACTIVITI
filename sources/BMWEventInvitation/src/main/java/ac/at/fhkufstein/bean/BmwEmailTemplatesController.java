package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.BmwEmailTemplates;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwEmailHistoryFacade;
import ac.at.fhkufstein.session.BmwEmailTemplatesFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "bmwEmailTemplatesController")
@ViewScoped
public class BmwEmailTemplatesController extends AbstractController<BmwEmailTemplates> implements Serializable {

    @EJB
    private BmwEmailTemplatesFacade ejbFacade;

    public BmwEmailTemplatesController() {
        super(BmwEmailTemplates.class);
    }
    
    public BmwEmailTemplatesController(Boolean jndiLookup) {
        super(BmwEmailTemplates.class);
        
        if(jndiLookup == true) {
            super.setFacade(PersistenceService.getFacadeJndiLookup(BmwEmailTemplatesFacade.class));
        }
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
