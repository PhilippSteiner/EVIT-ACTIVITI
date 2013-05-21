package ac.at.fhkufstein.entity;

import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwTravel;
import ac.at.fhkufstein.entity.BmwUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-21T15:10:51")
@StaticMetamodel(BmwParticipants.class)
public class BmwParticipants_ { 

    public static volatile SingularAttribute<BmwParticipants, Integer> id;
    public static volatile SingularAttribute<BmwParticipants, String> pState;
    public static volatile SingularAttribute<BmwParticipants, BmwEvent> eventId;
    public static volatile SingularAttribute<BmwParticipants, Integer> repId;
    public static volatile SingularAttribute<BmwParticipants, BmwUser> userId;
    public static volatile SingularAttribute<BmwParticipants, Integer> bmwAuth;
    public static volatile SingularAttribute<BmwParticipants, BmwTravel> travelId;

}