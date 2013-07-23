package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwEventFacade;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@ManagedBean(name = "bmwEventController")
@ViewScoped
public class BmwEventController extends AbstractController<BmwEvent> implements Serializable {

    @EJB
    private BmwEventFacade ejbFacade;

    public BmwEventController() {
        super(BmwEvent.class);
    }
    
    public BmwEventController(Boolean jndiLookup) {
        super(BmwEvent.class);
        
        if(jndiLookup == true) {
            super.setFacade(PersistenceService.getFacadeJndiLookup(BmwEventFacade.class));
        }
    }
    
    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
