package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwTravelFacade;
import ac.at.fhkufstein.session.BmwUserFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "bmwUserController")
@ViewScoped
public class BmwUserController extends AbstractController<BmwUser> implements Serializable {

    @EJB
    private BmwUserFacade ejbFacade;

    public BmwUserController() {
        super(BmwUser.class);
    }
    
    public BmwUserController(Boolean jndiLookup) {
        super(BmwUser.class);
        
        if(jndiLookup == true) {
            super.setFacade(PersistenceService.getFacadeJndiLookup(BmwUserFacade.class));
        }
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
		
    }
}
