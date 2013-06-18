package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.BmwEmailHistory;
import ac.at.fhkufstein.session.BmwEmailHistoryFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "bmwEmailHistoryController")
@ViewScoped
public class BmwEmailHistoryController extends AbstractController<BmwEmailHistory> implements Serializable {

    @EJB
    private BmwEmailHistoryFacade ejbFacade;

    public BmwEmailHistoryController() {
        super(BmwEmailHistory.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
