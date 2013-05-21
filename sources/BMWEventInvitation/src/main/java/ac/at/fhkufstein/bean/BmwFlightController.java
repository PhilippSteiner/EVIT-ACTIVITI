package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.BmwFlight;
import ac.at.fhkufstein.session.BmwFlightFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "bmwFlightController")
@ViewScoped
public class BmwFlightController extends AbstractController<BmwFlight> implements Serializable {

    @EJB
    private BmwFlightFacade ejbFacade;

    public BmwFlightController() {
        super(BmwFlight.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
