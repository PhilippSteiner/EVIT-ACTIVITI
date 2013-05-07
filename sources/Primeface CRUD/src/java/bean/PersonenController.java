package bean;

import entity.Personen;
import session.PersonenFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import javax.enterprise.context.SessionScoped;

@Named(value = "personenController")
@SessionScoped
public class PersonenController extends AbstractController<Personen> implements Serializable {

    @Inject
    private PersonenFacade ejbFacade;

    public PersonenController() {
        super(Personen.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
