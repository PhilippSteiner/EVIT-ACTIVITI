package bean;

import entity.Event;
import session.EventFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;

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

    @Override
    public void saveNew(ActionEvent event) {
        super.saveNew(event);

        Event invitationEvent = getSelected();

        System.out.println("new id: " + invitationEvent.getId());
    }
}
