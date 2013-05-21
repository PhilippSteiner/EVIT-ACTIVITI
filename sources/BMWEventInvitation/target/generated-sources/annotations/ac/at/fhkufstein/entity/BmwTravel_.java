package ac.at.fhkufstein.entity;

import ac.at.fhkufstein.entity.BmwFlight;
import ac.at.fhkufstein.entity.BmwParticipants;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-21T15:10:51")
@StaticMetamodel(BmwTravel.class)
public class BmwTravel_ { 

    public static volatile SingularAttribute<BmwTravel, Integer> id;
    public static volatile SingularAttribute<BmwTravel, BmwFlight> flightId;
    public static volatile SingularAttribute<BmwTravel, String> pdfTicketUrl;
    public static volatile SingularAttribute<BmwTravel, Date> arrivalDatetime;
    public static volatile CollectionAttribute<BmwTravel, BmwParticipants> bmwParticipantsCollection;
    public static volatile SingularAttribute<BmwTravel, String> type;

}