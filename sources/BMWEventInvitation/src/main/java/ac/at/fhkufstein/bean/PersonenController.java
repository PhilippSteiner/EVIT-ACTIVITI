package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.Personen;
import ac.at.fhkufstein.service.PersistenceService;
import ac.at.fhkufstein.session.BmwTravelFacade;
import ac.at.fhkufstein.session.PersonenFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "personenController")
@ViewScoped
public class PersonenController extends AbstractController<Personen> implements Serializable {

    @EJB
    private PersonenFacade ejbFacade;

    public PersonenController() {
        super(Personen.class);
    }
    
    public PersonenController(Boolean jndiLookup) {
        super(Personen.class);
        
        if(jndiLookup == true) {
            super.setFacade(PersistenceService.getFacadeJndiLookup(PersonenFacade.class));
        }
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
