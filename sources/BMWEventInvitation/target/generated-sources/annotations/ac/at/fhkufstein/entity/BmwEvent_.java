package ac.at.fhkufstein.entity;

import ac.at.fhkufstein.entity.BmwFlight;
import ac.at.fhkufstein.entity.BmwParticipants;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-26T21:25:40")
@StaticMetamodel(BmwEvent.class)
public class BmwEvent_ { 

    public static volatile SingularAttribute<BmwEvent, Integer> progress;
    public static volatile SingularAttribute<BmwEvent, Integer> processId;
    public static volatile SingularAttribute<BmwEvent, String> location;
    public static volatile SingularAttribute<BmwEvent, Date> eventCreated;
    public static volatile SingularAttribute<BmwEvent, Integer> userUpdated;
    public static volatile CollectionAttribute<BmwEvent, BmwParticipants> bmwParticipantsCollection;
    public static volatile CollectionAttribute<BmwEvent, BmwFlight> bmwFlightCollection;
    public static volatile SingularAttribute<BmwEvent, Integer> id;
    public static volatile SingularAttribute<BmwEvent, Date> startEventdate;
    public static volatile SingularAttribute<BmwEvent, Date> endEventdate;
    public static volatile SingularAttribute<BmwEvent, Integer> userCreated;
    public static volatile SingularAttribute<BmwEvent, Integer> maxParticipants;
    public static volatile SingularAttribute<BmwEvent, String> description;
    public static volatile SingularAttribute<BmwEvent, String> name;
    public static volatile SingularAttribute<BmwEvent, Integer> urgencyDayLimit;
    public static volatile SingularAttribute<BmwEvent, Date> eventUpdated;

}