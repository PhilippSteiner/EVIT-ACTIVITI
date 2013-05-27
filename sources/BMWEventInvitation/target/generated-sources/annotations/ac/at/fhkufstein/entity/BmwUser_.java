package ac.at.fhkufstein.entity;

import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.entity.Personen;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-26T21:58:11")
@StaticMetamodel(BmwUser.class)
public class BmwUser_ { 

    public static volatile SingularAttribute<BmwUser, Integer> uid;
    public static volatile SingularAttribute<BmwUser, String> pwd;
    public static volatile SingularAttribute<BmwUser, String> seatingPriority;
    public static volatile SingularAttribute<BmwUser, Personen> personenID;
    public static volatile SingularAttribute<BmwUser, Integer> rating;
    public static volatile CollectionAttribute<BmwUser, BmwParticipants> bmwParticipantsCollection;

}