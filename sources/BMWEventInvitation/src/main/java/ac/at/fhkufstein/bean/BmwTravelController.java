package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.BmwTravel;
import ac.at.fhkufstein.session.BmwTravelFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "bmwTravelController")
@ViewScoped
public class BmwTravelController extends AbstractController<BmwTravel> implements Serializable {

    @EJB
    private BmwTravelFacade ejbFacade;

    public BmwTravelController() {
        super(BmwTravel.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
