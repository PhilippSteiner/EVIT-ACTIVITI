/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.bean;

import ac.at.fhkufstein.entity.BmwUser;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author mike
 */
@ManagedBean(name = "bmwTravelagencyController")
@ViewScoped
public class BmwTravelagencyController extends BmwUserController {

    public BmwTravelagencyController() {
        super();
    }

    @Override
    public List<BmwUser> getItems() {

        return getFacade().getEntityManager().createNamedQuery("BmwUser.findAllReisebuero").getResultList();

    }
}
