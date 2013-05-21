package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.session.BmwParticipantsFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "bmwParticipantsController")
@ViewScoped
public class BmwParticipantsController extends AbstractController<BmwParticipants> implements Serializable {

    @EJB
    private BmwParticipantsFacade ejbFacade;

    public BmwParticipantsController() {
        super(BmwParticipants.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
