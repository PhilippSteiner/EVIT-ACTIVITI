package bean;

import entity.Event;
import session.EventFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import javax.enterprise.context.SessionScoped;

@Named(value = "eventController")
@SessionScoped
public class EventController extends AbstractController<Event> implements Serializable {

    @Inject
    private EventFacade ejbFacade;

    public EventController() {
        super(Event.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
}
