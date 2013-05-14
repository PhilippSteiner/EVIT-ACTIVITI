package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.Event;
import ac.at.fhkufstein.session.EventFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "eventController")
@ViewScoped
public class EventController extends AbstractController<Event> implements Serializable {

    @EJB
    private EventFacade ejbFacade;

    public EventController() {
        super(Event.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
