/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.session;

import ac.at.fhkufstein.entity.BmwFlight;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Philipp
 */
@Stateless
public class BmwFlightFacade extends AbstractFacade<BmwFlight> {
    @PersistenceContext(unitName = "ac.at.fh-kufstein_BMWEventInvitation_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public BmwFlightFacade() {
        super(BmwFlight.class);
    }
    
}
