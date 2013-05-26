package ac.at.fhkufstein.entity;

import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwTravel;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-26T21:25:40")
@StaticMetamodel(BmwFlight.class)
public class BmwFlight_ { 

    public static volatile SingularAttribute<BmwFlight, Integer> id;
    public static volatile CollectionAttribute<BmwFlight, BmwTravel> bmwTravelCollection;
    public static volatile SingularAttribute<BmwFlight, Date> arrivalTime;
    public static volatile SingularAttribute<BmwFlight, BmwEvent> eventId;
    public static volatile SingularAttribute<BmwFlight, Date> departureTime;
    public static volatile SingularAttribute<BmwFlight, String> departureLocation;
    public static volatile SingularAttribute<BmwFlight, String> flightNumber;
    public static volatile SingularAttribute<BmwFlight, String> arrivalLocation;

}