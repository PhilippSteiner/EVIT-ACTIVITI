package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.BmwParticipants;
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

@ManagedBean(name = "bmwParticipantsController")
@ViewScoped
public class BmwParticipantsController extends AbstractController<BmwParticipants> implements Serializable {

    @EJB
    private BmwParticipantsFacade ejbFacade;

    public BmwParticipantsController() {
        super(BmwParticipants.class);
    }
    
    public BmwParticipantsController(Boolean jndiLookup) {
        super(BmwParticipants.class);
        
        if(jndiLookup = true) {
            super.setFacade(PersistenceService.getFacadeJndiLookup(BmwParticipantsFacade.class));
        }
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
