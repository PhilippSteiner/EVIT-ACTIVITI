package ac.at.fhkufstein.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-21T08:19:58")
@StaticMetamodel(Event.class)
public class Event_ { 

    public static volatile SingularAttribute<Event, Integer> progress;
    public static volatile SingularAttribute<Event, Integer> processId;
    public static volatile SingularAttribute<Event, String> location;
    public static volatile SingularAttribute<Event, Date> eventCreated;
    public static volatile SingularAttribute<Event, Integer> userUpdated;
    public static volatile SingularAttribute<Event, Integer> id;
    public static volatile SingularAttribute<Event, Date> startEventdate;
    public static volatile SingularAttribute<Event, Date> endEventdate;
    public static volatile SingularAttribute<Event, Integer> userCreated;
    public static volatile SingularAttribute<Event, Integer> maxParticipants;
    public static volatile SingularAttribute<Event, String> description;
    public static volatile SingularAttribute<Event, String> name;
    public static volatile SingularAttribute<Event, Integer> urgencyDayLimit;
    public static volatile SingularAttribute<Event, Date> eventUpdated;

}