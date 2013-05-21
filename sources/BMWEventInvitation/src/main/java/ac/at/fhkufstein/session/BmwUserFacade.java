/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.session;

import ac.at.fhkufstein.entity.BmwUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Philipp
 */
@Stateless
public class BmwUserFacade extends AbstractFacade<BmwUser> {
    @PersistenceContext(unitName = "ac.at.fh-kufstein_BMWEventInvitation_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BmwUserFacade() {
        super(BmwUser.class);
    }
    
}
